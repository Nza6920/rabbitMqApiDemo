package com.niu.rabbitmq.api.ttl;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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


        String exchangeName = "test_ttl_exchange";
        String routingKey = "ttl.#";
        String queueName = "test_ttl_queue";


        // 设置超过5秒未消费的消息直接删除
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-message-ttl", 5000);

        channel.exchangeDeclare(exchangeName, "topic", true, false, null);
        channel.queueDeclare(queueName, true, false, false, arguments);
        channel.queueBind(queueName, exchangeName, routingKey);

        // 手工签收 autoAck 必须设置为 false
        channel.basicConsume(queueName, false, new MyConsumer(channel));
    }
}
