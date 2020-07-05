package com.niu.rabbitmq.api.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 生产者
 *
 * @Description: TODO
 * @Author nza
 * @Date 2020/7/5
 **/
public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {
        // 1. 创建ConnectionFactory
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("106.15.121.10");
        factory.setPort(5672);
        factory.setVirtualHost("/");

        // 2. 获取连接
        Connection connection = factory.newConnection();

        // 3. 创建channel
        Channel channel = connection.createChannel();

        // 4. 指定消息投递模式: 消息确认模式
        channel.confirmSelect();

        // 5. 发送消息
        String exchangeName = "test_confirm_exchange";
        String routingKey = "confirm.save";
        String msg = "Hello Send Confirm Msg";

        // 6. 添加确认监听
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                // 消息成功会进入
                System.out.println("-----------success------------");
                System.out.println(deliveryTag);
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                // 消息失败会进入
                System.out.println("-----------fail------------");
                System.out.println(deliveryTag);
            }
        });

        for (int i = 10; i > 0; i--) {
            System.out.println(channel.getNextPublishSeqNo());
            channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());
        }
    }
}
