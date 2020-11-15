package com.fans.core.configuration;

import com.fans.core.conditionals.RabbitMqConditional;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * className: RabbitmqConfiguration
 *
 * @author k
 * @version 1.0
 * @description 消息队列配置
 * @date 2020-09-28 12:57
 **/
@Configuration
@Conditional({RabbitMqConditional.class})
public class RabbitmqConfiguration {

    public static final String EXCHANGE_ARTICLE = "exchange_example";

    public static final String QUEUE_DOWNLOAD_HTML = "queue_example";

    public static final String TEST_A = "test.a";

    @Bean(name = EXCHANGE_ARTICLE)
    public Exchange exchange() {
        return ExchangeBuilder
                .topicExchange(EXCHANGE_ARTICLE)
                .durable(true)
                //开启延迟队列
//                .delayed()
                .build();
    }

    @Bean(name = QUEUE_DOWNLOAD_HTML)
    public Queue queue() {
        return QueueBuilder
                .durable(QUEUE_DOWNLOAD_HTML)
                .build();
    }

    @Bean
    public Binding binding(@Qualifier(value = EXCHANGE_ARTICLE) Exchange exchange,
                           @Qualifier(value = QUEUE_DOWNLOAD_HTML) Queue queue) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                // 可拼接多个 .
                //.with("test.#")
                // 只能拼接一个 .
                .with("test.*")
                .noargs();
    }
}
