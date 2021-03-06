package com.niu.rabbitmq.api.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 自定义 consumer
 *
 * @Description: TODO
 * @Author nza
 * @Date 2020/7/5
 **/
public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("106.15.121.10");
        factory.setPort(5672);
        factory.setVirtualHost("/");

        Connection conn = factory.newConnection();

        Channel channel = conn.createChannel();

        String exchangeName = "test_consumer_exchange";
        String routingKey = "consumer.save";

        String msg = "Hello rabbitmq consumer msg!";

        for (int i = 5; i > 0; i--) {
            channel.basicPublish(exchangeName, routingKey, true, null, msg.getBytes());
        }
    }
}
