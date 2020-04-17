package com.cxg.study.redis.lua;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: cxg
 * @Date: 2020/4/13 16:18
 * @Description: 限流测试 .
 * @Copyright: All rights reserved.
 */
@RestController
@RequestMapping("lua")
public class LimiterController {
    private static final AtomicInteger ATOMIC_INTEGER_1 = new AtomicInteger();
    private static final AtomicInteger ATOMIC_INTEGER_2 = new AtomicInteger();
    private static final AtomicInteger ATOMIC_INTEGER_3 = new AtomicInteger();

    private DefaultRedisScript<Boolean> setExScript;
    private DefaultRedisScript<Boolean> limitScript;
    private RedisTemplate<String, Serializable> limitRedisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/limit")
    public ResponseEntity limit() {
        List<String> keys = Arrays.asList("limit-lua");
        DefaultRedisScript<Boolean> setEx = LuaRedisScriptFactory.initScript(Boolean.class, "limit");
        Boolean execute = limitRedisTemplate.execute(setEx, keys, 10, 5);
        return ResponseEntity.ok(execute);
    }

    /**
     * 这里参数解析问题参考：https://www.jianshu.com/p/a555facfd6c8下的个人评论
     * 参考：https://blog.csdn.net/tyyh08/article/details/80267261
     * @return
     */
    @GetMapping("/setEx")
    public ResponseEntity setEx() {
        List<String> keys = Arrays.asList("test-lua", "hello lua, this is lua script value");
//        DefaultRedisScript<Boolean> setEx = LuaRedisScriptFactory.redisScript(Boolean.class, "setEx");
        Boolean execute = limitRedisTemplate.execute(LuaRedisScriptFactory.initScript(Boolean.class, "setEx"), keys, 20);
        return ResponseEntity.ok(execute);
    }

    @Limit(key = "limitTest", period = 10, count = 3)
    @GetMapping("/limitTest1")
    public int testLimiter1() {
        return ATOMIC_INTEGER_1.incrementAndGet();
    }

    @Limit(key = "customer_limit_test", period = 10, count = 3, limitType = LimitType.CUSTOMER)
    @GetMapping("/limitTest2")
    public int testLimiter2() {
        return ATOMIC_INTEGER_2.incrementAndGet();
    }

    @Limit(key = "ip_limit_test", period = 10, count = 3, limitType = LimitType.IP)
    @GetMapping("/limitTest3")
    public int testLimiter3() {
        return ATOMIC_INTEGER_3.incrementAndGet();
    }

//    @Autowired
//    @Qualifier("setEx-lua")
//    public void setSetExScript(DefaultRedisScript<Boolean> setExScript) {
//        this.setExScript = setExScript;
//    }
//    @Autowired
//    @Qualifier("limit-lua")
//    public void setLimitScript(DefaultRedisScript<Boolean> limitScript) {
//        this.limitScript = limitScript;
//    }

    @Autowired
    @Qualifier("myRedisTemplate")
    public void setLimitRedisTemplate(RedisTemplate<String, Serializable> limitRedisTemplate) {
        this.limitRedisTemplate = limitRedisTemplate;
    }
}
