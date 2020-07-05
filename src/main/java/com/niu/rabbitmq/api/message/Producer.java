package com.niu.rabbitmq.api.message;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 生产者
 * @Description: TODO
 * @Author nza
 * @Date 2020/7/3
 **/
public class Producer {

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

        // 不知道exchange会走默认的AMQP DEFAULT
        String queueName = "test.msg";

        System.out.println("开始发送");


        Map<String, Object> headers = new HashMap<>();
        headers.put("my1", "111");
        headers.put("my2", "222");


        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)            // 1: 非持久化 2: 持久化
                .contentEncoding("UTF-8")   // 字符集
                .expiration("10000")        // 过期时间
                .headers(headers)
                .build();
        // 4.通过channel发送数据
        for (int i = 0; i < 5; i++) {

            String msg = "Hello rabbitmq " + i;
            // 1.exchange 2.routingKey
            channel.basicPublish("", queueName, properties, msg.getBytes());
        }

        // 5.关闭连接
        channel.close();
        conn.close();

        System.out.println("发送完毕");
    }
}
