package com.fans.user.stream;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * className: StreamConsumer
 *
 * @author k
 * @version 1.0
 * @description stream消息通道消费者
 * @date 2020-10-28 12:45
 **/
@Component
@EnableBinding(value = MyStreamChannel.class)
public class StreamConsumer {


    @StreamListener(value = MyStreamChannel.INPUT)
    public void receiveMessage(String dumpling) {
        System.out.println("我吃了第" + dumpling + "个饺子");
    }
}
