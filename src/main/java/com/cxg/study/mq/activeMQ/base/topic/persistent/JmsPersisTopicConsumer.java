package com.cxg.study.mq.activeMQ.base.topic.persistent;   // Administrator 于 2019/7/31 创建;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Optional;

/**
 * 模拟持久化的主题消费者
 */
public class JmsPersisTopicConsumer {

    private static final String DEFAULT_ACTIVEMQ_URL = "tcp://192.168.0.22:61616";
    private static final String TOPIC_NAME = "topic_persistent";

    public static void main(String[] args) throws JMSException {
//        System.out.println("A-消费者");
        System.out.println("B-消费者"); // 启动2次，模拟2个消费者

        // 1.创建连接工程；用户名和密码默认；
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(DEFAULT_ACTIVEMQ_URL);
        // 2.通过连接工厂获取连接；
        Connection connection = factory.createConnection();
        connection.setClientID("张三");

        // 3.创建连接会话；
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 4.创建目的地；
        Topic topic = session.createTopic(TOPIC_NAME);
        TopicSubscriber subscriber = session.createDurableSubscriber(topic, "demo");

        connection.start();

         Message message = subscriber.receive();
         while (null != message) {
             TextMessage textMessage = (TextMessage) message;
             String text = textMessage.getText();
             Optional.of("收到的持久化的订阅消息：" +  text).ifPresent(System.out::println);
             message = subscriber.receive();
         }
        session.close();
        connection.close();
        System.out.println("=========持久化的主题消息接收处理完成==========");
    }
}
