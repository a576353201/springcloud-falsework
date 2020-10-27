package com.fans.core.conditionals;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * className: DynamicDataConditional
 *
 * @author k
 * @version 1.0
 * @description
 * @date 2020-10-27 20:56
 **/
public class DynamicDataConditional implements Condition {


    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Environment environment = conditionContext.getEnvironment();
        String mailHost = environment.getProperty("spring.mail.host");
        return StringUtils.isNotBlank(mailHost);
    }
}
