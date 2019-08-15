package com.cxg.study.thread.mashibing.c_026;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class T10_ScheduledPool {
	public static void main(String[] args) {
		ScheduledExecutorService service = Executors.newScheduledThreadPool(4);
		service.scheduleAtFixedRate(()->{
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " --> " + LocalDateTime.now());
		}, 0, 500, TimeUnit.MILLISECONDS);
		
	}
}
