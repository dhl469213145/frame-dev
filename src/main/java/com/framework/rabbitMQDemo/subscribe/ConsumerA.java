package com.framework.rabbitMQDemo.subscribe;

import com.framework.rabbitMQDemo.common.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.util.concurrent.TimeUnit;

public class ConsumerA {
    private final static String EXCHANGE_NAME = "q_test_exchange_subscribe";
    private final static String QUEUE_NAME = "q_test_queue_subscribeA";

    public static void main(String[] args) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 绑定到交换机
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

        channel.basicQos(1);

        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, false, queueingConsumer);

        while(true) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("consumerA  print  :" + message);

            TimeUnit.MILLISECONDS.sleep(100);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }
}
