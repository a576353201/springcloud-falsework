package com.fans.modules.threadpool.handler;

import com.fans.modules.threadpool.basic.BaseEventHandler;
import com.fans.modules.threadpool.eventBean.MessageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * className: MessageHandler
 *
 * @author k
 * @version 1.0
 * @description 消息执行器  增加 Component 是为了引用ioc管理的容器和注册中心使用
 * @date 2019-04-01 10:56
 **/
@Component("messageHandler")
@Slf4j
public class MessageHandler extends BaseEventHandler<MessageBean> {


    @Override
    public void execute(MessageBean event) {
        log.info("--->>>> 短信执行器触发，已发送成功！！！");
        log.info("--> 你好" + event.getName() + "，我今年" + event.getAge() + "岁");

    }

    @Override
    public String getDescription() {
        return "消息执行器,发送消息专用";
    }

    @Override
    public int getCorePoolSize() {
        return 10;
    }

    @Override
    public BlockingQueue<Runnable> getWorkQueue() {
        return new ArrayBlockingQueue(10);
    }
}
