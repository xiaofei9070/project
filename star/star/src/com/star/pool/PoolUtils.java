package com.star.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PoolUtils {
	//缓存线程池
	private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
	//定长线程池
	private static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
	//定时
	private static ExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
	//单例
	private static ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
	
	public static void newCachedThreadPool(Runnable runnable){
		cachedThreadPool.execute(runnable);
	}
	
	/**
	 * 定长线程
	 * @date 2016年9月18日 下午5:03:49
	 * @param runnable
	 */
	public static void newFiexedThreadPool(Runnable runnable){
		fixedThreadPool.execute(runnable);
	}
	

	/**
	 * 定时线程
	 * @date 2016年9月18日 下午5:03:37
	 * @param runnable
	 * @param m
	 */
	public static void newScheduledThreadPool(Runnable runnable,int m){
		try {
			scheduledThreadPool.awaitTermination(m, TimeUnit.SECONDS);
			scheduledThreadPool.execute(runnable);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void newSingleThreadExecutor(Runnable runnable){
		singleThreadExecutor.execute(runnable);
	}
}
