package com.ad.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

//    @RabbitListener(queues = {"myqueue"})
//    public void processMessage(String content){
//        System.out.println("收到消息>>>=" + new String(content.getBytes()));
//
//    }
}
