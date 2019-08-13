/**
 * reentrantlock用于替代synchronized
 * 由于m1锁定this,只有m1执行完毕的时候,m2才能执行
 * 这里是复习synchronized最原始的语义
 * 
 * 使用reentrantlock可以完成同样的功能
 * 需要注意的是，必须要必须要必须要手动释放锁（重要的事情说三遍）
 * 使用syn锁定的话如果遇到异常，jvm会自动释放锁，但是lock必须手动释放锁，因此经常在finally中进行锁的释放
 * 
 * 使用reentrantlock可以进行“尝试锁定”tryLock；如果无法锁定，或者在指定时间内无法锁定，线程可以决定是否继续等待
 * 
 * 使用ReentrantLock还可以调用lockInterruptibly方法，可以对线程interrupt方法做出响应，
 * 在一个线程等待锁的过程中，可以被打断
 * 
 * @author mashibing
 */
package com.cxg.study.thread.mashibing.c_020;

import com.cxg.study.socket.base.tcp.SimpleServerDemo;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLock4 {
		
	public static void main(String[] args) throws InterruptedException {
		Lock lock = new ReentrantLock();

		Thread t1 = new Thread(()->{
			try {
				lock.lock();
				System.out.println("t1 start");
//				TimeUnit.SECONDS.sleep(2);
//				TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);

				// 模拟阻塞，下面调用t1.interrupt()也无法让t1线程响应中断信号；
				SimpleServerDemo.baseTest1();

				System.out.println("t1 end");
			} catch (IOException e) {
				System.out.println("t1 --> interrupted!");
			} finally {
				lock.unlock();
			}
		});
		t1.start();
		TimeUnit.SECONDS.sleep(1);

		System.out.println("t1-->interrupt begin");
		t1.interrupt(); // t1线程无法响应中断信号；
		System.out.println("t1-->interrupt end");

		Thread t2 = new Thread(()->{
			boolean lc = false;
			try {
				boolean b = lock.tryLock();
				System.out.println("t2->tryLock:" + b);

				/**
				 * 如果锁由另一个线程持有，那么当前线程将被禁用以进行线程调度，并处于【休眠】状态，
				 * 直到发生以下两种情况之一：
				 * 1、锁由当前线程获取；
				 * 2、其他线程中断当前线程。interrupt
 				 */
				lock.lockInterruptibly();
				lc = true;
				System.out.println("t2 start");
				TimeUnit.SECONDS.sleep(5);
				System.out.println("t2 end");
			} catch (InterruptedException e) {
				System.out.println("t2 --> interrupted!");
			} finally {
				System.out.println("lc:" + lc);
				if (lc) {
					lock.unlock();
				}
			}
		});
		t2.start();
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t2.interrupt(); //打断线程2的等待
		
	}
}
