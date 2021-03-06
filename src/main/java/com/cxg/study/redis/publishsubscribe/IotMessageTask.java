package com.cxg.study.redis.publishsubscribe;   // Administrator 于 2019/8/29 创建;

import org.springframework.data.redis.connection.MessageListener;

import java.util.concurrent.CompletableFuture;

public class IotMessageTask<T> {

    //声明线程异步阻塞对象(JDK 1.8新提供Api)
    private CompletableFuture<T> iotMessageFuture = new CompletableFuture<>();

    //声明消息监听对象
    private MessageListener messageListener;

    //声明超时时间
    private boolean isTimeout;

    public IotMessageTask() {
    }
    public CompletableFuture<T> getIotMessageFuture() {
        return iotMessageFuture;
    }
    public void setIotMessageFuture(CompletableFuture<T> iotMessageFuture) {
        this.iotMessageFuture = iotMessageFuture;
    }
    public MessageListener getMessageListener() {
        return messageListener;
    }
    public void setMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }
    public boolean isTimeout() {
        return isTimeout;
    }
    public void setTimeout(boolean timeout) {
        isTimeout = timeout;
    }
}
