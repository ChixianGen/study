package com.cxg.study.mq.activeMQ.queue;   // Administrator 于 2019/7/30 创建;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProducer {

    private static final String DEFAULT_ACTIVEMQ_URL = "tcp://192.168.0.22:61616";
    private static final String QUEUE_NAME = "queue_demo";

    public static void main(String[] args) throws JMSException {
        // 1.创建连接工程；用户名和密码默认；
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(DEFAULT_ACTIVEMQ_URL);
        // 2.通过连接工厂获取连接；
        Connection connection = factory.createConnection();
        connection.start();

        // 3.创建连接会话；
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

        // 4.创建目的地；（队列或主题）
        Queue queue = session.createQueue(QUEUE_NAME);

        // 5.创建消息的生产者；
        MessageProducer producer = session.createProducer(queue);

        // 6.使用消息生产者生成消息并发送到mq中；
        for (int i = 80; i < 90; i++) {
            // 7.创建消息；
            TextMessage textMessage = session.createTextMessage("多消费者消息：" + (char)i);
            textMessage.setBooleanProperty("vip", true); // 自定义属性，用于标识等等。。。
//             8.发送消息；
            producer.send(textMessage);

            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setString("String", "String_" + (char)i);
            producer.send(mapMessage);
        }

        producer.close();
        session.close();
        connection.close();
        System.out.println("=========消息发送完成==========");
    }
}
