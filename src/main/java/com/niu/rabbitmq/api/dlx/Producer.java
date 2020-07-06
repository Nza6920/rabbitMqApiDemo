package com.niu.rabbitmq.api.dlx;

import com.rabbitmq.client.AMQP;
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

        String exchangeName = "test_dlx_exchange";
        String routingKey = "dlx.save";
        String msg = "Hello rabbitmq DLX msg!";

        for (int i = 1; i > 0; i--) {
            AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                    .deliveryMode(2)
                    .contentEncoding("UTF-8")
                    .expiration("10000")
                    .build();
            channel.basicPublish(exchangeName, routingKey, true, properties, msg.getBytes());
        }
    }
}
