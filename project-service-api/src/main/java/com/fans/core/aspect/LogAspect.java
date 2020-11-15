package com.fans.core.aspect;

import com.fans.common.utils.JsonUtils;
import com.fans.common.utils.WebInitialize;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * className: LogAspect
 *
 * @author k
 * @version 1.0
 * @description 请求日志
 * @date 2020-11-09 16:13
 **/
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("execution(* com.fans..*.controller..*.*(..))" )
    public void controllerPointcut() {

    }

    /**
     * 在什么时候起作用 在那些方法起作用 表达式
     * Before 执行之前
     * After 执行之后
     * AfterThrowing 执行之后 包括抛出异常
     * Around 包含之前的三种 常用
     */
    @Before(value = "controllerPointcut()" )
    public void before(JoinPoint joinPoint) {
        //开始打印日志请求
        WebInitialize webInitialize = new WebInitialize();
        HttpServletRequest request = webInitialize.getRequest();
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        String controllerName = joinPoint.getSignature().getDeclaringType().getSimpleName();
        // 打印请求信息
        log.info("------------- 【{}】-【{}】方法开始 -------------" , controllerName, methodName);
        log.info("--> 请求服务地址: 【{}】-【{}】" , request.getMethod(), request.getRequestURL().toString());
        log.info("--> 请求来源: 【{}】" , webInitialize.getOrigin());
        log.info("--> 远程IP: 【{}】" , request.getRemoteAddr());
        // 打印请求参数
        Object[] args = joinPoint.getArgs();
        Object[] arguments = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest
                    || args[i] instanceof ServletResponse
                    || args[i] instanceof MultipartFile) {
                continue;
            }
            arguments[i] = args[i];
        }
        // 排除字段，敏感字段或太长的字段不显示
        String[] excludeProperties = {"shard"};
        // 为空的会不打印，但是像图片等长字段也会打印
        log.info("--> 请求参数: {}" , JsonUtils.obj2String(arguments, excludeProperties));
    }

    @Around("controllerPointcut()" )
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        // 排除字段，敏感字段或太长的字段不显示
        String[] excludeProperties = {"password" , "shard"};
        log.info("--> 返回结果: {}" , JsonUtils.obj2String(result, excludeProperties));
        log.info("------------- 结束 耗时：{} ms -------------" , System.currentTimeMillis() - startTime);
        return result;
    }
}
