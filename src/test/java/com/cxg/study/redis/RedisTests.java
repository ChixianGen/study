package com.cxg.study.redis;   // Administrator 于 2019/8/29 创建;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisTests {

    @Autowired
    @Qualifier(value = "myRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void setRedisTemplate() {
        redisTemplate.convertAndSend("hello", "world");
    }
}
