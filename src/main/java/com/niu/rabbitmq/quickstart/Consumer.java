package com.niu.rabbitmq.quickstart;

import com.rabbitmq.client.*;

/**
 * 消费者
 *
 * @Description: TODO
 * @Author nza
 * @Date 2020/7/3
 **/
public class Consumer {

    public static void main(String[] args) throws Exception {
        // 1.创建一个ConnectionFactory, 并进行配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("106.15.121.10");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        // 2.通过连接工厂创建连接
        Connection conn = connectionFactory.newConnection();

        // 3.通过conn创建一个Channel
        Channel channel = conn.createChannel();

        // 4.创建一个队列
        String queueName = "test001";
        channel.queueDeclare(queueName, true, false, false, null);

        // 5.创建消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        // 6.设置channel
        channel.basicConsume(queueName, true, queueingConsumer);

        // 7.获取消息
        while (true) {

            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();

            String msg = new String(delivery.getBody());
            System.out.println("消费端: " + msg);

            Envelope envelope = delivery.getEnvelope();
//            envelope.getDeliveryTag()
        }
    }
}
