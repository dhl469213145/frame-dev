package com.framework.rabbitMQDemo.work;

import com.framework.rabbitMQDemo.common.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

/**
 * ·basicQos 方法设置了当前信道最大预获取（prefetch）消息数量为1。消息从队列异步推送给消费者，消费者的 ack 也是异步发送给队列，从队列的视角去看，总是会有一批消息已推送但尚
 * 未获得 ack 确认，Qos 的 prefetchCount 参数就是用来限制这批未确认消息数量的。设为1时，队列只有在收到消费者发回的上一条消息 ack 确认后，才会向该消费者发送下一条消息。
 * prefetchCount 的默认值为0，即没有限制，队列会将所有消息尽快发给消费者
 *
 * ·轮询分发 ：使用任务队列的优点之一就是可以轻易的并行工作。如果我们积压了好多工作，我们可以通过增加工作者（消费者）来解决这一问题，使得系统的伸缩性更加容易。在默认情况下，
 * RabbitMQ将逐个发送消息到在序列中的下一个消费者(而不考虑每个任务的时长等等，且是提前一次性分配，并非一个一个分配)。平均每个消费者获得相同数量的消息。这种方式分发消息机
 * 制称为Round-Robin（轮询）。
 *
 * ·公平分发 ：虽然上面的分配法方式也还行，但是有个问题就是：比如：现在有2个消费者，所有的奇数的消息都是繁忙的，而偶数则是轻松的。按照轮询的方式，奇数的任务交给了第一个消费
 * 者，所以一直在忙个不停。偶数的任务交给另一个消费者，则立即完成任务，然后闲得不行。而RabbitMQ则是不了解这些的。这是因为当消息进入队列，RabbitMQ就会分派消息。它不看消费
 * 者为应答的数目，只是盲目的将消息发给轮询指定的消费者。
 *
 * 为了解决这个问题，我们使用basicQos( prefetchCount = 1)方法，来限制RabbitMQ只发不超过1条的消息给同一个消费者。当消息处理完毕后，有了反馈，才会进行第二次发送。
 *
 *
 * 代码修改如下：
 * 打开上述代码的注释：
 * // 同一时刻服务器只会发一条消息给消费者
 * channel.basicQos(1);
 *
 * //开启这行 表示使用手动确认模式
 * channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
 *
 * 同时改为手动确认：
 * // 监听队列，false表示手动返回完成状态，true表示自动
 * channel.basicConsume(QUEUE_NAME, false, consumer);
 *
 */
public class Consumer1 {
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
            System.out.println(" [consumer1] Received '" + message + "'");
            //休眠
            Thread.sleep(10);
            // 监听队列，false表示手动返回完成状态，true表示自动
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }
}
