package com.cxg.study.thread.designpattern.observe.thread_observe;   // Administrator 于 2019/7/23 创建;

import java.util.List;
import java.util.Optional;

public class ThreadLifeCycleObserver implements LifeCycleLister {

    private static final byte[] LOCK = new byte[0];

    public void concurrentQuery(List<String> ids) {
        if (null == ids || ids.isEmpty()) {
            return;
        }

        ids.stream().forEach(id -> {
            new Thread(new ObserverRunnable(this) {
                @Override
                public void run() {
                    try {
                        notifyChange(new RunnableEvent(RunnableState.RUNNING, Thread.currentThread(), null));
                        Optional.of("当前查询的线程：" + id).ifPresent(System.out::println);
                        Thread.sleep(2000);
//                        int i = 2 / 0; // 模拟异常；
                        notifyChange(new RunnableEvent(RunnableState.DONE, Thread.currentThread(), null));
                    } catch (Exception e) {
                        notifyChange(new RunnableEvent(RunnableState.ERROR, Thread.currentThread(), e));
                    }
                }
            }, id).start();
        });
    }

    @Override
    public void onEvent(ObserverRunnable.RunnableEvent event) {
        synchronized (LOCK) {
            Optional.of("线程【" + Thread.currentThread().getName() + "】进入onEvent事件回调，当前工作状态：【" + event.getRunnableState() + "】")
                    .ifPresent(System.out::println);
            if (null != event.getCause()) {
                Optional.of("线程【" + Thread.currentThread().getName() + "】，异常信息：" + event.getCause())
                        .ifPresent(System.out::println);
            }
        }
    }
}
