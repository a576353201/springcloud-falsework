package com.fans.core.datasource.aspect;

import com.fans.core.datasource.annotation.DataSource;
import com.fans.core.datasource.config.DynamicContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * className: DataSourceAspect
 *
 * @author k
 * @version 1.0
 * @description 数据源执行sql切面 切换指定数据源执行
 * @date 2018-08-04 02:09
 **/
@Aspect
@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class DataSourceAspect {
    @Pointcut("@annotation(com.fans.core.datasource.annotation.DataSource)" +
            "|| @within(com.fans.core.datasource.annotation.DataSource)" )
    public void dataSourcePointCut() {
    }

    @Around("dataSourcePointCut()" )
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Class<?> targetClass = point.getTarget().getClass();
        Method method = signature.getMethod();
        DataSource targetDataSource = targetClass.getAnnotation(DataSource.class);
        DataSource methodDataSource = method.getAnnotation(DataSource.class);
        if (targetDataSource != null || methodDataSource != null) {
            String value;
            if (methodDataSource != null) {
                value = methodDataSource.value();
            } else {
                value = targetDataSource.value();
            }
            //存入
            DynamicContextHolder.push(value);
            log.info("--> current database is 【{}】" , value);

        }
        try {
            return point.proceed();
        } finally {
            //清理
            DynamicContextHolder.poll();
            log.info("--> finally clean databaseContextHolder" );
        }
    }
}
