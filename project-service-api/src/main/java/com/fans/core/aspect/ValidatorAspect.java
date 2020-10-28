package com.fans.core.aspect;

import com.fans.annotations.Verify;
import com.fans.validator.ValidatorUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedHashMap;

/**
 * className: ValidatorAspect
 *
 * @author k
 * @version 1.0
 * @description 校验切面
 * @date 2020-10-10 16:49
 **/
@Aspect
@Component
@Slf4j
public class ValidatorAspect {

    @Before("execution(* com.fans.modules..*.controller..*.*(..))")
    public void verifyParam(JoinPoint joinPoint) throws NoSuchMethodException {
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Class<?>[] interfaces = joinPoint.getTarget().getClass().getInterfaces();
        for (Class<?> aClass : interfaces) {
            Method fatherMethod = aClass.getMethod(method.getName(), method.getParameterTypes());
            executeVerify(args, fatherMethod);
        }
        executeVerify(args, method);


    }

    private void executeVerify(Object[] args, Method method) {
        Annotation[][] annotations = method.getParameterAnnotations();
        for (int i = 0; i < annotations.length; i++) {
            Object param = args[i];
            Annotation[] paramAnnotation = annotations[i];
            if (param == null || paramAnnotation == null || paramAnnotation.length == 0) {
                continue;
            }
            for (Annotation annotation : paramAnnotation) {
                if (annotation.annotationType().equals(Verify.class)) {
                    //获取被代理的对象
                    InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
                    LinkedHashMap<String, Object> memberValues = getFieldValue(invocationHandler, "memberValues");
                    ValidatorUtils.check(param, (Class<?>[]) memberValues.get("groups"));
                    break;
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> LinkedHashMap<String, Object> getFieldValue(T object, String property) {
        if (object != null && property != null) {
            Class<T> currClass = (Class<T>) object.getClass();
            try {
                Field field = currClass.getDeclaredField(property);
                field.setAccessible(true);
                return (LinkedHashMap<String, Object>) field.get(object);
            } catch (NoSuchFieldException e) {
                throw new IllegalArgumentException(currClass + " has no property: " + property);
            } catch (IllegalArgumentException e) {
                throw e;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
