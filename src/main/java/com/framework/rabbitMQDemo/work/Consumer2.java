package com.framework.rabbitMQDemo.work;

import com.framework.rabbitMQDemo.common.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

public class Consumer2 {
    private final static String QUEUE_NAME = "q_test_work";

    public static void main(String[] args) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 同一时刻服务器只会发一条消息给消费者
        channel.basicQos(1);

        // 定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 监听队列，false表示手动返回完成状态，true表示自动
        channel.basicConsume(QUEUE_NAME, false, consumer);

        while(true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [consumer2] Received '" + message + "'");
            //休眠
            Thread.sleep(1000);
            // 监听队列，false表示手动返回完成状态，true表示自动
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }
}
