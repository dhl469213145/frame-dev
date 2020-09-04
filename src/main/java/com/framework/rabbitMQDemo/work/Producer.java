package com.framework.rabbitMQDemo.work;

import com.framework.rabbitMQDemo.common.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Producer {
    private final static String QUEUE_NAME = "q_test_work";

    public static void main(String[] args) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        for(int i = 0; i < 10; i++) {
            String message = "work mode test:" + i;

            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

            System.out.println(" [x] Sent '" + message + "'");

            Thread.sleep(i * 10);
        }
        channel.close();
        connection.close();
    }
}
