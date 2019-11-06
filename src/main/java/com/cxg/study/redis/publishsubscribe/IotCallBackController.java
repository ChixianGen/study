package com.cxg.study.redis.publishsubscribe;   // Administrator 于 2019/8/29 创建;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/iot")
public class IotCallBackController {

    //引入Redis客户端操作对象
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping(value = "/unLockCallBack", method = RequestMethod.POST)
    public boolean unLockCallBack(@RequestParam(value = "thingName") String thingName,
                                  @RequestParam(value = "requestId") String requestId) {
        //生成监听频道Key
        String key = "IOT_" + thingName + "_" + requestId;
        //模拟实现消息回调
        stringRedisTemplate.convertAndSend(key, "这是redis的返回消息");
        return true;
    }
}
