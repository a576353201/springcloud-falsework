package com.fans.properties;

import com.fans.properties.bean.InterceptorBean;
import com.fans.properties.factory.YamlConfigFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * className: InterceptorProperty
 *
 * @author k
 * @version 1.0
 * @description 拦截器url配置
 * @date 2020-10-27 13:33
 **/
@Component
@ConfigurationProperties(prefix = "interceptor")
@PropertySource(value = "classpath:config/interceptor-bean.yml", factory = YamlConfigFactory.class)
@Setter
@Getter
public class InterceptorProperty {

    private Map<String, InterceptorBean> beans;

}
