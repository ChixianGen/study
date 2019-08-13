/**
 * 有N张火车票，每张票都有一个编号
 * 同时有10个窗口对外售票
 * 请写一个模拟程序
 * 
 * 分析下面的程序可能会产生哪些问题？
 *  
 * 使用Vector或者Collections.synchronizedXXX
 * 分析一下，这样能解决问题吗？
 * 
 * @author 马士兵
 */
package com.cxg.study.thread.mashibing.c_024;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

public class TicketSeller2 {
	/**
	 * Vector类中的操作都是同步的（线程安全）；
	 */
	static Vector<String> tickets = new Vector<>();
	
	static {
		for(int i=0; i<1000; i++) tickets.add("票 编号：" + i);
	}
	
	public static void main(String[] args) {
		
		for(int i=0; i<10; i++) {
			new Thread(()->{
				/**
				 * 虽然 size()和remove(i)方法每个都是原子操作（同步且线程安全），
				 * 但是2个方法组合在一起完成一件事，这个过程不是原子操作；（非线程安全）
				 */
				while(tickets.size() > 0) {
					try {
						// 模拟其他业务；
						TimeUnit.MILLISECONDS.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("销售了--" + tickets.remove(0));
				}
			}).start();
		}
	}
}
