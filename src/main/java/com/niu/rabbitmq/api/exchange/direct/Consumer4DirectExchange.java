package com.niu.rabbitmq.api.exchange.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Direct Exchange: 消费者
 *
 * @Description: TODO
 * @Author nza
 * @Date 2020/7/3
 **/
public class Consumer4DirectExchange {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("106.15.121.10");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setAutomaticRecoveryEnabled(true);
        factory.setNetworkRecoveryInterval(3000);

        Connection conn = factory.newConnection();

        Channel channel = conn.createChannel();

        // 声明
        String exchangeName = "test_direct_exchange";
        String routingKey = "test.direct";
        String exchangeType = "direct";
        String queueName = "test_direct_queue";

        // 声明交换机
        channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);

        // 声明队列
        channel.queueDeclare(queueName, false, false, false, null);

        // 建立一个绑定关系
        channel.queueBind(queueName, exchangeName, routingKey);

        // 是否持久化消息
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("接收: " + msg);
        }

    }
}
