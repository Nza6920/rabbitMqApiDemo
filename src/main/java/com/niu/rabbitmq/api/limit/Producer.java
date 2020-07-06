package com.niu.rabbitmq.api.limit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

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

        String exchangeName = "test_qos_exchange";
        String routingKey = "qos.save";

        String msg = "Hello rabbitmq QOS msg!";

        for (int i = 5; i > 0; i--) {
            channel.basicPublish(exchangeName, routingKey, true, null, msg.getBytes());
        }
    }
}
