package com.star.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtil {
	
	public static int i = 0;
	
	public static void main(String[] args) {
		/*BlockingQueue<Runnable> bq = new ArrayBlockingQueue<Runnable>(2);
		
		ExecutorService pool = Executors.newCachedThreadPool();
		*/
		Thread t1 = new MyThread();
    	PoolUtils.newScheduledThreadPool(t1, 5);
	}
	
	
}
class MyThread extends Thread {
	
	@Override
	public void run() {
		try {
			for(int i=0;i<1000;i++){
				Thread.sleep(1000);
				System.out.print("-");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
