package com.cxg.study.mq.activeMQ.springboot.produce;   // Administrator 于 2019/7/31 创建;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import java.util.UUID;

@Component
public class QueueProducer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    public void produceMsg() {
        jmsMessagingTemplate.convertAndSend(queue, "******:" + UUID.randomUUID().toString().substring(0, 6));
        System.out.println("========队列消息生成成功========");
    }

    @Scheduled(fixedDelay = 2000, initialDelay = 2000)
    public void produceScheduled() {
        String msg = "******Scheduled:" + UUID.randomUUID().toString().substring(0, 6);
        jmsMessagingTemplate.convertAndSend(queue, msg);
        System.out.printf("========定时队列消息推送成功：【%s】========\n", msg);
    }
}
