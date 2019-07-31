package com.cxg.study.mq.activeMQ.topic;   // Administrator 于 2019/7/30 创建;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;
import java.util.Optional;

public class JmsConsumer {

    private static final String DEFAULT_ACTIVEMQ_URL = "tcp://192.168.0.22:61616";
    private static final String TOPIC_NAME = "topic_demo";

    public static void main(String[] args) throws JMSException, InterruptedException {

        System.out.println("A-消费者");
//        System.out.println("B-消费者"); // 启动2次，模拟2个消费者

        // 1.创建连接工程；用户名和密码默认；
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(DEFAULT_ACTIVEMQ_URL);
        // 2.通过连接工厂获取连接；
        Connection connection = factory.createConnection();
        connection.start();

        // 3.创建连接会话；
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 4.创建目的地；（队列或主题）
        final Topic topic = session.createTopic(TOPIC_NAME);

        // 5.创建消息的消费者；
        MessageConsumer consumer = session.createConsumer(topic);

        // a.同步阻塞方式处理消息；
        //  receiveMsg_1(consumer);

        // b.异步非阻塞（监听回调）方式来处理消息；
        receiveMsg_2(consumer);
        
        consumer.close();
        session.close();
        connection.close();
        System.out.println("=========主题消息接收处理完成==========");
    }

    private static void receiveMsg_2(MessageConsumer consumer) throws JMSException {
//        consumer.setMessageListener(new MessageListener() {
//            @Override
//            public void onMessage(Message message) {
//                if (null != message && message instanceof TextMessage) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                    }
//                    TextMessage textMessage = (TextMessage) message;
//                    try {
//                        Optional.of("【消息监听】接收到的消息：" + textMessage.getText()).ifPresent(System.out::println);
//                    } catch (JMSException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });

        // java8 lambda表达式；
        consumer.setMessageListener(message -> {
            if (null != message) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        Optional.of("【消息监听】接收到的订阅消息：" + textMessage.getText()).ifPresent(System.out::println);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                } else if (message instanceof MapMessage) {
                    MapMessage mapMessage = (MapMessage) message;
                    try {
                        String string = mapMessage.getString("map_string");
                        final boolean vip = mapMessage.getBooleanProperty("vip");
                        System.out.printf("【消息监听】接收到的【MapMessage】订阅消息：String：【%s】， 自定义属性：【%s】\n", string, vip);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        try {
            // 读取控制台的输入数据；可用于调试时阻止程序退出；
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void receiveMsg_1(MessageConsumer consumer) throws JMSException, InterruptedException {
        while (true) {
            Thread.sleep(1000);
            // 阻塞等待；
            TextMessage receive = (TextMessage) consumer.receive();
            
            // 指定等待一定时间，无消息就退出；
//            TextMessage receive = (TextMessage) consumer.receive(3000L);
            if (null != receive) {
                System.out.printf("=============接收到的消息：【%s】================\n", receive.getText());
            } else {
                break;
            }
        }
    }
}
