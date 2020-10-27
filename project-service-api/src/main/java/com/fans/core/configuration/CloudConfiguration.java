package com.fans.core.configuration;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * className: CloudConfig
 *
 * @author k
 * @version 1.0
 * @description cloud相关配置
 * @date 2020-09-28 12:57
 **/
@Configuration
public class CloudConfiguration {


    /**
     * 分布式调用负载均衡开启
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }
}
