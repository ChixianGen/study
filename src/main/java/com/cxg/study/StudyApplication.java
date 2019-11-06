package com.cxg.study;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@SpringBootApplication
@RestController
public class StudyApplication {

    @Value("${resources.logDir}")
    private String logdir;

    public static void main(String[] args) {
        SpringApplication.run(StudyApplication.class, args);
    }

    @GetMapping("hello")
    public String hello() {
        System.out.println(logdir);
        return "hello cxg ngrok new jrebel";
    }
}


