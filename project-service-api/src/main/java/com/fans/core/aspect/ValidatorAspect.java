package com.fans.core.aspect;

import com.fans.modules.annotations.Verify;
import com.fans.modules.validator.ValidatorUtils;
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
    public void verifyParam(JoinPoint joinPoint) throws NoSuchMethodException, IllegalAccessException, InstantiationException {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //获取被切的controller
        Class<?> declaringType = joinPoint.getSignature().getDeclaringType();
        Method method = methodSignature.getMethod();
        //获取方法中所有参数名称
        String[] parameterNames = methodSignature.getParameterNames();
        //获取方法中所有参数类型
        Class<?>[] parameterTypes = methodSignature.getParameterTypes();
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        Class<?>[] interfaces = joinPoint.getTarget().getClass().getInterfaces();
        for (Class<?> aClass : interfaces) {
            Method fatherMethod = aClass.getMethod(method.getName(), method.getParameterTypes());
            executeVerify(parameterTypes, parameterNames, args, declaringType.newInstance(), fatherMethod);
        }
        executeVerify(parameterTypes, parameterNames, args, declaringType.newInstance(), method);
    }

    private void executeVerify(Class<?>[] parameterTypes, String[] parameterNames, Object[] args, Object declaringObj, Method method) {
        Annotation[][] annotations = method.getParameterAnnotations();
        for (int i = 0; i < annotations.length; i++) {
            Object param = args[i];
            Class<?> paramType = parameterTypes[i];
            String paramName = parameterNames[i];
            Annotation[] paramAnnotation = annotations[i];
            if (paramAnnotation == null || paramAnnotation.length == 0) {
                continue;
            }
            for (Annotation annotation : paramAnnotation) {
                if (annotation.annotationType().equals(Verify.class)) {
                    //获取被代理的对象
                    InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
                    LinkedHashMap<String, Object> memberValues = getFieldValue(invocationHandler, "memberValues");
                    Object groups = memberValues.get("groups");
                    if (paramType != null && paramType.getClassLoader() == null) {
                        ValidatorUtils.checkParam(paramName,declaringObj, method, args, (Class<?>[]) groups);
                    } else {
                        ValidatorUtils.check(param, (Class<?>[]) groups);
                    }
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
