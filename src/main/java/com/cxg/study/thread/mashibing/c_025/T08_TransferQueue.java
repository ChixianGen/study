package com.cxg.study.thread.mashibing.c_025;

import java.util.concurrent.LinkedTransferQueue;

public class T08_TransferQueue {

	/**
	 * 当有新生产的消息时，会直接去找消费者，无需添加消息到消息队列，这样就加快了消息的处理，这样大大提高了服务器的处理能力，提高并发效率；
	 */
	private static LinkedTransferQueue<String> strs = new LinkedTransferQueue<>();

	public static void main(String[] args) throws InterruptedException {
//		correct();
		error();
	}

	private static void error() {
		/**
		 * 如果先启动生产者（这里的transfer()方法）；在启动消费者（这里的take()方法）；
		 * 那么transfer()会阻塞，导致程序无法往下执行
		 * 所以使用transfer方法需要先有消费者；
		 */
		try {
			strs.transfer("aaa");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		new Thread(() -> {
			try {
				System.out.printf("error：【%s】\n", strs.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}

	private static void correct() throws InterruptedException {
		new Thread(() -> {
			try {
				System.out.printf("correct：【%s】\n", strs.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();

		strs.transfer("aaa");
	}
}
