package com.cxg.study.mq.activeMQ.topic.persistent;   // Administrator 于 2019/7/31 创建;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 模拟持久化的主题生产者
 */
public class JmsPersisTopicProducer {

    private static final String DEFAULT_ACTIVEMQ_URL = "tcp://192.168.0.22:61616";
    private static final String TOPIC_NAME = "topic_persistent";

    public static void main(String[] args) throws JMSException {
        // 1.创建连接工程；用户名和密码默认；
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(DEFAULT_ACTIVEMQ_URL);
        // 2.通过连接工厂获取连接；
        Connection connection = factory.createConnection();

        // 3.创建连接会话；
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 4.创建目的地；
        Topic topic = session.createTopic(TOPIC_NAME);

        // 5.创建主题消息的生产者；
        MessageProducer producer = session.createProducer(topic);
//        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        connection.start();
        // 6.使用消息生产者生成消息并发送到mq中；
        for (int i = 70; i < 80; i++) {
            // 7.创建消息；
            TextMessage textMessage = session.createTextMessage("持久化的文章：" + (char)i);
            // 8.发送消息；
            producer.send(textMessage);
        }

        producer.close();
        session.close();
        connection.close();
        System.out.println("=========持久化的订阅消息发送完成==========");
    }
}
