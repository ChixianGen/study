/**
 * 认识future
 */
package com.cxg.study.thread.mashibing.c_026;

import java.util.concurrent.*;

public class T06_Future {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
//		test_1();
//		test_2();
        test_3();
    }

    private static void test_3() {
//        FutureTask<Void> task = new FutureTask<>(new Callable<Void>() {
//            @Override
//            public Void call() throws Exception {
//                TimeUnit.SECONDS.sleep(2);
//                System.out.println("test_3");
//                TimeUnit.SECONDS.sleep(2);
//                return null;
//            }
//        });
        FutureTask<Void> task = new FutureTask<>(() -> {
            TimeUnit.SECONDS.sleep(2);
            System.out.println("test_3_lambda");
            TimeUnit.SECONDS.sleep(2);
            return null;
        });
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(task);
        executorService.shutdown();
    }

    private static void test_2() throws ExecutionException, InterruptedException {
        String param = "param";
        FutureTask stringFutureTask = new FutureTask<String>(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        }, handle(param));
        new Thread(stringFutureTask).start();
        String o = (String) stringFutureTask.get();
        System.out.println("test_2 result --> " + o);
    }

    private static String handle(String param) {
        return "hello test_2";
    }

    private static void test_1() throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(() -> {
            TimeUnit.MILLISECONDS.sleep(500);
            return 1000;
        }); //new Callable () { Integer call();}

        new Thread(task).start();
        System.out.println(task.get()); // 阻塞方法，等待返回值

        //*******************************
        ExecutorService service = Executors.newFixedThreadPool(5);
        Future<Integer> f = service.submit(() -> {
            TimeUnit.MILLISECONDS.sleep(500);
            return 1;
        });
        System.out.println(f.get());// 阻塞方法，等待返回值
        System.out.println(f.isDone());
    }
}
