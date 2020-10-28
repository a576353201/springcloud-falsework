package com.fans.modules.user.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

/**
 * interfaceName: MyStreamChannel
 *
 * @author k
 * @version 1.0
 * @description 自定义消息通道
 * @date 2020-10-28 12:38
 **/
@Component
public interface MyStreamChannel {

    String OUTPUT = "myOutput";
    String INPUT = "myInput";

    @Output(OUTPUT)
    MessageChannel output();

    @Input(INPUT)
    SubscribableChannel input();
}
