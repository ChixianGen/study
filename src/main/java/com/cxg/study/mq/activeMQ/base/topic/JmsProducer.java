package com.cxg.study.mq.activeMQ.topic;   // Administrator 于 2019/7/30 创建;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProducer {

    private static final String DEFAULT_ACTIVEMQ_URL = "tcp://192.168.0.22:61616";
    private static final String TOPIC_NAME = "topic_demo";

    public static void main(String[] args) throws JMSException {
        // 1.创建连接工程；用户名和密码默认；
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(DEFAULT_ACTIVEMQ_URL);
        // 2.通过连接工厂获取连接；
        Connection connection = factory.createConnection();
        connection.start();

        // 3.创建连接会话；
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 4.创建目的地；（队列或主题）
        final Topic topic = session.createTopic(TOPIC_NAME);

        // 5.创建主题消息的生产者；
        MessageProducer producer = session.createProducer(topic);

        // 6.使用消息生产者生成消息并发送到mq中；
        for (int i = 65; i < 70; i++) {
            // 7.创建消息；
            TextMessage textMessage = session.createTextMessage("订阅文章推送消息：" + (char)i);
            // 8.发送消息；
            producer.send(textMessage);

            MapMessage mapMessage = session.createMapMessage();
            mapMessage.setBooleanProperty("vip", i % 2 == 0);
            mapMessage.setString("map_string", "map_string_" + (char)i);
            producer.send(mapMessage);
        }

        producer.close();
        session.close();
        connection.close();
        System.out.println("=========订阅消息发送完成==========");
    }
}
