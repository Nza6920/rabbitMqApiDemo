package com.niu.rabbitmq.api.exchange.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Direct Exchange: 生产者
 *
 * @Description: TODO
 * @Author nza
 * @Date 2020/7/3
 **/
public class Producer4DirectExchange {

    public static void main(String[] args) throws Exception {

        // 消费端与生产端的 routing key 必须完全一致


        // 1 创建ConnectionFactory
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("106.15.121.10");
        factory.setPort(5672);
        factory.setVirtualHost("/");

        // 2 创建连接
        Connection conn = factory.newConnection();

        // 3 创建channel
        Channel channel = conn.createChannel();

        // 4 声明
        String exchangeName = "test_direct_exchange";
        String routingKey = "test.direct";

        // 5 发送
        String msg = "Hello World RabbitMQ 4 Direct Exchange Message...";
        channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());

        channel.close();
        conn.close();
    }
}
