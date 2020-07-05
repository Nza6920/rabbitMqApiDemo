package com.niu.rabbitmq.api.exchange.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Topic Exchange: 生产者
 *
 * @Description: TODO
 * @Author nza
 * @Date 2020/7/3
 **/
public class Producer4TopicExchange {

    public static void main(String[] args) throws Exception {

        // exchange 与 routing key 建立绑定规则
        // * 只能匹配一个词  *.test / test.*
        // # 可以匹配多个词  #.test / test.#


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
        String exchangeName = "test_topic_exchange";
        String routingKey1 = "user.test1";
        String routingKey2 = "user.test2";
        String routingKey3 = "user.test3";

        // 5 发送
        String msg1 = "Hello World RabbitMQ 4 Direct Exchange Message... 1";
        String msg2 = "Hello World RabbitMQ 4 Direct Exchange Message... 2";
        String msg3 = "Hello World RabbitMQ 4 Direct Exchange Message... 3";

        channel.basicPublish(exchangeName, routingKey1, null, msg1.getBytes());
        channel.basicPublish(exchangeName, routingKey2, null, msg2.getBytes());
        channel.basicPublish(exchangeName, routingKey3, null, msg3.getBytes());

        channel.close();
        conn.close();
    }
}
