package com.niu.rabbitmq.api.returnd;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * return 机制
 *
 * @Description: TODO
 * @Author nza
 * @Date 2020/7/5
 **/
public class Producer {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("106.15.121.10");
        factory.setPort(5672);
        factory.setVirtualHost("/");

        Connection conn = factory.newConnection();

        Channel channel = conn.createChannel();

        String exchangeName = "test_return_exchange";
        String routingKey = "return.save";
        String routingKeyError = "error.save";

        String msg = "Hello rabbitmq return msg!";

        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("-----------handle return----------");
                System.out.println("replyCode: " + replyCode);
                System.out.println("replyText: " + replyText);
                System.out.println("exchange: " + exchange);
                System.out.println("routingKey: " + routingKey);
                System.out.println("properties: " + properties);
                System.out.println("body: " + new String(body));
            }
        });

//        channel.basicPublish(exchangeName, routingKey, true, null, msg.getBytes());
        channel.basicPublish(exchangeName, routingKeyError, true, null, msg.getBytes());
    }
}
