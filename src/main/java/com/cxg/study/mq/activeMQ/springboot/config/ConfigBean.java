package com.cxg.study.mq.activeMQ.springboot.config;   // Administrator 于 2019/7/31 创建;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import javax.jms.Queue;

//@Component
public class ConfigBean {

    @Value("${myqueue}")
    private String myqueue;

    public String getMyqueue() {
        return myqueue;
    }

    @Bean
    public Queue queue() {
        return new ActiveMQQueue(myqueue);
    }
}
