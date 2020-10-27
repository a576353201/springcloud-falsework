package com.fans.core.configuration;

import com.fans.core.interceptors.PermissionInterceptor;
import com.fans.properties.InterceptorProperty;
import com.fans.properties.bean.InterceptorBean;
import com.fans.utils.ApplicationContextHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import java.util.Map;

/**
 * className: WebConfig
 *
 * @author k
 * @version 1.0
 * @description 类似于web.xml配置
 * @date 2018-12-20 14:14
 **/
@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    @Resource(name = "interceptorProperty")
    private InterceptorProperty interceptorProperty;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        Map<String, InterceptorBean> beanMap = interceptorProperty.getBeans();
        if (beanMap != null && !beanMap.isEmpty()) {
            beanMap.forEach((beanName, interceptorBean) -> {
                Object interceptor = ApplicationContextHelper.popBean(beanName);
                if (interceptor != null) {
                    registry.addInterceptor((HandlerInterceptor) interceptor)
                            .addPathPatterns(interceptorBean.getAddPathPatterns())
                            .excludePathPatterns(interceptorBean.getExcludePathPatterns());
                }
            });
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //映射所有资源
        registry.addResourceHandler("/**")
                //映射swagger2
                .addResourceLocations("classpath:/META-INF/resources/");
    }

    /**
     * description: 跨域调用开放
     *
     * @return org.springframework.boot.web.servlet.FilterRegistrationBean<org.springframework.web.filter.CorsFilter>
     * @author k
     * @date 2019/03/22 12:48
     **/
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        // 1.添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        // 放行哪些原始域
        config.addAllowedOrigin("*");
        // 是否发送Cookie信息
        config.setAllowCredentials(true);
        // 放行哪些原始域(请求方式)
        config.addAllowedMethod("*");
        // 放行哪些原始域(头部信息)
        config.addAllowedHeader("*");
        // 2.添加映射路径
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);
        // 3.返回新的CorsFilter
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(configSource));
        bean.setOrder(0);
        return bean;
    }


    @Bean
    public HandlerInterceptorAdapter permissionInterceptor() {
        return new PermissionInterceptor();
    }
}
