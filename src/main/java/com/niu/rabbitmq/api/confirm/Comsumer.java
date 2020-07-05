package com.niu.rabbitmq.api.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者
 *
 * @Description: TODO
 * @Author nza
 * @Date 2020/7/5
 **/
public class Comsumer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        // 1. 创建ConnectionFactory
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("106.15.121.10");
        factory.setPort(5672);
        factory.setVirtualHost("/");

        // 2. 获取连接
        Connection connection = factory.newConnection();

        // 3. 创建channel
        Channel channel = connection.createChannel();

        String exchangeName = "test_confirm_exchange";
        String routingKey = "confirm.#";
        String queueName = "test_confirm_queue";

        // 4. 声明 exchange 和 queue
        channel.exchangeDeclare(exchangeName, "topic", true);
        channel.queueDeclare(queueName, false, false, false, null);

        // 5. 绑定关系
        channel.queueBind(queueName, exchangeName, routingKey);


        // 6. 创建消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("消费:" + msg);
        }
    }
}
