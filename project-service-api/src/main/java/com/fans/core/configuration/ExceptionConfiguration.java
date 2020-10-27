package com.fans.core.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * className: ExceptionConfiguration
 *
 * @author k
 * @version 1.0
 * @description 异常信息
 * @date 2020-05-25 21:21
 **/
@Component
@ConfigurationProperties(prefix = "kapok")
@PropertySource(value = "classpath:config/exception-code.properties")
@Setter
@Getter
public class ExceptionConfiguration {

    private Map<Integer, String> codes;

    public String getMessage(int code) {
        return codes.get(code);
    }

}
