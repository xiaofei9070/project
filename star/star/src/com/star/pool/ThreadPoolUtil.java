package com.star.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtil {
	
	public static int i = 0;
	
	public static void main(String[] args) {
		BlockingQueue<Runnable> bq = new ArrayBlockingQueue<Runnable>(20);
		
		ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 15, 2, TimeUnit.MILLISECONDS, bq,new ThreadPoolExecutor.DiscardOldestPolicy());
		
        for(int j =0;j<10000;j++){
        	Thread t1 = new MyThread();
        	pool.execute(t1);
        }
	}
	
	
}
class MyThread extends Thread {
	
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());
        System.out.println(ThreadPoolUtil.i);
		ThreadPoolUtil.i ++;
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
