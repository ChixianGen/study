package com.cxg.study.thread.mashibing.c_025;

import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class T09_SynchronusQueue {
	public static void main(String[] args) throws InterruptedException {
		/**
		 * 容量为0的队列，特殊的TransferQueue，
		 * 当有新消息时，需要马上消费；
 		 */
		BlockingQueue<String> strs = new SynchronousQueue<>();

		new Thread(()->{
			try {
				System.out.println(strs.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		
		strs.put("aaa"); // 阻塞等待消费者消费，底层就是调用 transfer()方法；

//		TimeUnit.MILLISECONDS.sleep(100);
		strs.add("aaa");
		System.out.println(strs.size());
	}
}
