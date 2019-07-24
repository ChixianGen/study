package com.cxg.study.thread.designpattern.future;   // Administrator 于 2019/7/24 创建;

public class Test {
    public static void main(String[] args) throws InterruptedException {
//        test1();
        test2();
    }

    private static void test2() throws InterruptedException {
        FutureService futureService = new FutureService();
        System.out.println("开启异步任务。。。");
        futureService.submit(() -> {
            try {
                System.out.println("开始执行异步任务。。。");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步任务完成，返回结果。。。");
            return "呵呵呵呵和";
        }, Test::handleResult);
        System.out.println("执行其他任务。。。");
        Thread.sleep(1000);
        System.out.println("其他任务完成。。。等待异步返回结果。。。");
    }

    private static void handleResult(Object result) {
        System.out.printf("结果：【%s】。。。", (String) result);
    }

    private static void test1() throws InterruptedException {
        FutureService futureService = new FutureService();
        System.out.println("开启异步任务。。。");
        Future<String> future = futureService.submit(() -> {
            try {
                System.out.println("开始执行异步任务。。。");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("异步任务完成，返回结果。。。");
            return "哈哈哈哈哈";
        });
        System.out.println("执行其他任务。。。");
        Thread.sleep(1000);
        System.out.println("其他任务完成。。。等待异步返回结果。。。");
        String s = future.get();
        System.out.printf("异步返回结果：【%s】", s);
    }
}
