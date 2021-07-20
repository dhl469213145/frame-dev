package com.framework.rabbitMQDemo.topic;

import com.framework.rabbitMQDemo.common.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

public class Consumer2 {
    private static final String EXCHANGE_NAME = "topic_exchange";

    private final static String QUEUE_NAME = "topic_queue_2";

    public static void main(String[] args) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();
        // 3、声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 4、绑定队列到交换机，指定路由key为select.#
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "select.#");

        channel.basicQos(1);
        // 5、定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //6、监听队列,手动返回完成状态
        channel.basicConsume(QUEUE_NAME, false, consumer);

        while(true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" 消费者2：" + message + "'");
            //消费者1接收一条消息后休眠10毫秒
            Thread.sleep(10);

            //返回确认状态
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }
}
