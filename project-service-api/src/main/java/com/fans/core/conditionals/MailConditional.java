package com.fans.core.conditionals;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * className: MailConditional
 *
 * @author k
 * @version 1.0
 * @description 邮箱注入条件
 * @date 2020-10-27 09:41
 **/
public class MailConditional implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        Environment environment = conditionContext.getEnvironment();
        String mailHost = environment.getProperty("spring.mail.host" );
        return StringUtils.isNotBlank(mailHost);
    }
}
