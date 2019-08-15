package com.cxg.study.thread.mashibing.c_025;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 一个单向链表+两把锁+两个条件
 * 两把锁，一把用于入队，一把用于出队，有效的避免了入队与出队时使用一把锁带来的竞争。
 * 在入队与出队都高并发的情况下，性能比ArrayBlockingQueue高很多
 * 采用了链表，最大容量为整数最大值，可看做容量无限
 */
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
