package com.niu.rabbitmq.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

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

        System.out.println("开始发送");

        // 4.通过channel发送数据
        for (int i = 0; i < 5; i++) {
            String msg = "Hello rabbitmq " + i;

            // 1.exchange 2.routingKey
            channel.basicPublish("", "test001", null, msg.getBytes());
        }

        // 5.关闭连接
        channel.close();
        conn.close();

        System.out.println("发送完毕");
    }
}
