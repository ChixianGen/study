package com.cxg.study.thread.mashibing.c_025;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class T05_LinkedBlockingQueue {

	static BlockingQueue<String> strs = new LinkedBlockingQueue<>(50);

	static Random r = new Random();

	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(() -> {
			for (int i = 0; i < 10; i++) {
				try {
					strs.put("a" + i); //如果满了，就会等待
					TimeUnit.MILLISECONDS.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "p1");
		thread.start();
		thread.join();
		System.out.println("thread执行完成");
		System.out.println("strs.size:" + strs.size());
		for (int i = 0, j = strs.size(); i < j; i++) {
			System.out.println("获取添加的内容：" + strs.take());
		}

//		for (int i = 0; i < 5; i++) {
//			new Thread(() -> {
//				for (;;) {
//					try {
//						System.out.println(Thread.currentThread().getName() + " take -" + strs.take()); //如果空了，就会等待
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			}, "c" + i).start();
//		}
	}
}
