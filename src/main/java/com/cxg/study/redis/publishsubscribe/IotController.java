package com.cxg.study.redis.publishsubscribe;   // Administrator 于 2019/8/29 创建;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/iot")
public class IotController {

    private Logger log = LoggerFactory.getLogger(IotController.class);

    //注入Redis消息容器对象
    @Autowired
    private RedisMessageListenerContainer redisMessageListenerContainer;

    @RequestMapping(value = "/unLock", method = RequestMethod.POST)
    public boolean unLock(@RequestParam(value = "thingName") String thingName,
                          @RequestParam(value = "requestId") String requestId)
            throws InterruptedException, ExecutionException, TimeoutException {

        //此处实现异步消息调用处理....

        //生成监听频道Key
        String key = "IOT_" + thingName + "_" + requestId;
        //创建监听Topic
        ChannelTopic channelTopic = new ChannelTopic(key);
        //创建消息任务对象
        IotMessageTask iotMessageTask = new IotMessageTask();
        //任务对象及监听Topic添加到消息监听容器
        try {
            redisMessageListenerContainer.addMessageListener(new IotMessageListener(iotMessageTask), channelTopic);
            log.debug("启动redis订阅监听器: {}", key);
            //进入同步阻塞等待，超时时间设置为60秒
            Message message = (Message) iotMessageTask.getIotMessageFuture().get(60000, TimeUnit.MILLISECONDS);
            log.debug("收到redis返回消息: {}", message.toString());
        } finally {
            //销毁消息监听对象
            if (iotMessageTask != null) {
                redisMessageListenerContainer.removeMessageListener(iotMessageTask.getMessageListener());
            }
        }
        return true;
    }
}
