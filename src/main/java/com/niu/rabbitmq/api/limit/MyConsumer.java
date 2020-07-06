package com.niu.rabbitmq.api.limit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * 自定义消费者
 *
 * @Description: TODO
 * @Author nza
 * @Date 2020/7/5
 **/
public class MyConsumer extends DefaultConsumer {

    private Channel channel;

    /**
     * Constructs a new instance and records its association to the passed-in channel.
     *
     * @param channel the channel to which this consumer is attached
     */
    public MyConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

        System.out.println("------------consumer--------------");
        System.out.println("consumerTag: " + consumerTag);
        System.out.println("envelope: " + envelope);
        System.out.println("properties: " + properties);
        System.out.println("body: " + new String(body));

        // 签收消息
        channel.basicAck(envelope.getDeliveryTag(), false);
    }

    @Override
    public Channel getChannel() {
        return this.channel;
    }
}
