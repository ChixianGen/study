/**
 * 认识Callable，对Runnable进行了扩展
 * 对Callable的调用，可以有返回值
 */
package com.cxg.study.thread.mashibing.c_026;

import java.util.concurrent.*;

public class T03_Callable implements Callable<String> {

    @Override
    public String call() throws Exception {
        TimeUnit.SECONDS.sleep(1);
        return "hello";
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 5; i++) {
            Future<String> submit = executorService.submit(new T03_Callable());
            String s = submit.get();
            System.out.println("main->s:" + s);
            System.out.println(executorService);
        }
        executorService.shutdown();
        System.out.println(executorService);
    }
}
