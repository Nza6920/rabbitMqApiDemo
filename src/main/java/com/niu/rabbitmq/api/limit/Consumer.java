package com.niu.rabbitmq.api.limit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 自定义 consumer
 * @Description: TODO
 * @Author nza
 * @Date 2020/7/5
 **/
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("106.15.121.10");
        factory.setPort(5672);
        factory.setVirtualHost("/");

        Connection conn = factory.newConnection();

        Channel channel = conn.createChannel();


        String exchangeName = "test_qos_exchange";
        String routingKey = "qos.#";
        String queueName = "test_qos_queue";


        channel.exchangeDeclare(exchangeName, "topic", true, false, null);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);

        // 消息大小限制 / 一次最多三条消息 / 是否设置为channel级别
        channel.basicQos(0, 1, false);
        // 限流消息 autoAck 必须为 false
        channel.basicConsume(queueName, false, new MyConsumer(channel));
    }
}
