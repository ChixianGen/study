package com.cxg.study.mq.activeMQ.springboot;   // Administrator 于 2019/7/31 创建;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJms
@EnableScheduling
@SpringBootApplication
public class ActivemqBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(ActivemqBootApplication.class, args);
    }
}
