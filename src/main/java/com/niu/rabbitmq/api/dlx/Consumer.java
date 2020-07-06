package com.niu.rabbitmq.api.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 自定义 consumer
 *
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


        // 普通的交换机队列
        String exchangeName = "test_dlx_exchange";
        String routingKey = "dlx.#";
        String queueName = "test_dlx_queue";


        // 死信队列
        String exchangeDlxName = "dlx_exchange";
        String dlxQueue = "dlx.queue";
        String dlxRoutingKey = "#";

        // 设置死信队列
        channel.exchangeDeclare(exchangeDlxName, "topic", true, false, null);
        channel.queueDeclare(dlxQueue, true, false, false, null);
        channel.queueBind(dlxQueue, exchangeDlxName, dlxRoutingKey);


        // 设置普通队列
        // 队列参数配置
        Map<String, Object> arguments = new HashMap<>(1);
        // 设置死信队列
        arguments.put("x-dead-letter-exchange", exchangeDlxName);
        channel.exchangeDeclare(exchangeName, "topic", true, false, null);
        channel.queueDeclare(queueName, true, false, false, arguments);
        channel.queueBind(queueName, exchangeName, routingKey);

        // 消费
        channel.basicConsume(queueName, true, new MyConsumer(channel));

        // 消费死信
        channel.basicConsume(dlxQueue, true, new MyDlxConsumer(channel));
    }
}
