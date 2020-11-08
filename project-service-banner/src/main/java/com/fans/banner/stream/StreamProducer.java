package com.fans.banner.stream;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * className: StreamProducer
 *
 * @author k
 * @version 1.0
 * @description 消息通道生产者
 * @date 2020-10-28 12:48
 **/
@Component
@EnableBinding(value = MyStreamChannel.class)
public class StreamProducer {
    @Resource(type = MyStreamChannel.class)
    private MyStreamChannel myStreamChannel;

    public void sendMessage(String dumpling) {
        myStreamChannel.output().send(MessageBuilder.withPayload(dumpling).build());
    }
}
