package com.rule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * className: MyRule
 *
 * @author k
 * @version 1.0
 * @description
 * @date 2020-10-28 02:20
 **/
@SuppressWarnings("SpringFacetCodeInspection")
@Configuration
public class MyRule {

    @Bean
    public IRule iRule() {
        return new RandomRule();
    }

}
