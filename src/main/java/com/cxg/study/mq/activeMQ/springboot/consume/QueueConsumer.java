package com.cxg.study.mq.activeMQ.springboot.consume;   // Administrator 于 2019/8/1 创建;

import org.springframework.jms.annotation.JmsListener;

import javax.jms.JMSException;
import javax.jms.TextMessage;

//@Component
public class QueueConsumer {

    @JmsListener(destination = "${myqueue}")
    public void receiveMsg(TextMessage textMessage) throws JMSException, InterruptedException {
        String text = textMessage.getText();
        Thread.sleep(1000);
        System.out.printf("========接收到的消息：【%s】========\n", text);
    }
}
