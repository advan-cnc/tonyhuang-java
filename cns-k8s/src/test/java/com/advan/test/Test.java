package com.advan.test;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Test {
    public static void main(String[] args) throws Exception {

    }
    private static void testMQ() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
// "guest"/"guest" by default, limited to localhost connections
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setVirtualHost("/");
        factory.setHost("localhost");
        factory.setPort(5672);
        Connection conn = factory.newConnection();
        System.out.println(conn);
        final Channel channel = conn.createChannel();
        System.out.println(channel);
        channel.exchangeDeclare("myexchange","direct",true);
        channel.queueDeclare("myqueue", true, false, false, null);
        channel.queueBind("myqueue", "myexchange", "aaa");
        channel.basicPublish("myexchange","aaa",null,"hi".getBytes());
    }
}
