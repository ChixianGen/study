package com.cxg.study.thread.mashibing.c_025;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 一个对象数组 + 一把锁 + 两个条件
 * 入队与出队都用同一把锁
 * 在只有入队高并发或出队高并发的情况下，因为操作数组，且不需要扩容，性能很高
 * 采用了数组，必须指定大小，即容量有限
 */
public class T06_ArrayBlockingQueue {

	static BlockingQueue<String> strs = new ArrayBlockingQueue<>(10);

	static Random r = new Random();

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			strs.put("a" + i);
		}
		
//		strs.put("aaa"); //满了就会等待，程序阻塞
//		strs.add("aaa"); // 满了就会抛出异常
		strs.offer("aaa"); // 满了不会添加，返回 false
		//strs.offer("aaa", 1, TimeUnit.SECONDS);
		
		System.out.println(strs);
	}
}
