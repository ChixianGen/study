package com.cxg.study.redis.publishsubscribe;   // Administrator 于 2019/8/29 创建;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class IotMessageListener implements MessageListener {

    private Logger log = LoggerFactory.getLogger(IotMessageListener.class);

    private IotMessageTask iotMessageTask;

    public IotMessageListener(IotMessageTask iotMessageTask) {
        this.iotMessageTask = iotMessageTask;
    }

    //实现消息发布监听处理方法
    @Override
    public void onMessage(Message message, byte[] bytes) {
        log.debug("订阅redis iot任务响应: {}", message.toString());
        //线程阻塞完成
        iotMessageTask.getIotMessageFuture().complete(message);
    }
}
