package com.niu.rabbitmq.api.exchange.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Fanout Exchange: 消费者
 *
 * @Description: TODO
 * @Author nza
 * @Date 2020/7/3
 **/
public class Consumer4FanoutExchange {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        // 不处理路由键, 只需要简单的将队列绑定到交换机上
        // 发送到交换机的消息都会被转发到与该交换机绑定的所有队列上
        // Fanout 交换机转发消息是最快的

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("106.15.121.10");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setAutomaticRecoveryEnabled(true);
        factory.setNetworkRecoveryInterval(3000);

        Connection conn = factory.newConnection();

        Channel channel = conn.createChannel();

        // 声明
        String exchangeName = "test_fanout_exchange";
        String exchangeType = "fanout";
        String queueName = "test_fanout_queue";
        String routingKey = ""; // 不设置路由键

        // 1.声明交换机
        channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);

        // 2.声明队列
        channel.queueDeclare(queueName, false, false, false, null);

        // 3.建立一个绑定关系
        channel.queueBind(queueName, exchangeName, routingKey);

        // 4.监听队列
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("接收: " + msg);
        }

    }
}
