package com.lrh.future;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @description:
 * @author: lrh
 * @date: 2020/5/22 17:35
 */
public class FutureTaskDemo {

	static ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder();
	static {
		threadFactoryBuilder.setNameFormat("MY-TEST-%d");
	}

	//这里线程池大小设置很重要 不然可能造成任务A在等任务B完成，任务B又拿不到线程，最后一直等待

	static ThreadPoolExecutor threadPoolExecutor =
			new ThreadPoolExecutor(2,2,10,
					TimeUnit.SECONDS,new LinkedBlockingQueue<>(1), threadFactoryBuilder.build());

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		FutureTask<String> ftB = new FutureTask<>(new TaskB());
		FutureTask<String> ftA = new FutureTask<>(new TaskA(ftB));

		threadPoolExecutor.submit(ftA);
		threadPoolExecutor.submit(ftB);
		System.out.println(ftA.get());
		System.out.println(ftB.get());
	}

	static class TaskA implements Callable<String> {

		FutureTask<String> bTask;

		public TaskA(FutureTask<String> bTask) {
			this.bTask = bTask;
		}

		@Override
		public String call() throws Exception {
			System.out.println("烧水，等B任务完成");
			Thread.sleep(10);
			String s = bTask.get();
			System.out.println("B任务完成 并送来： " + s);
			return "上茶: " + s;
		}
	}

	static class TaskB implements Callable<String> {

		@Override
		public String call() throws Exception {
			Thread.sleep(100);
			System.out.println("完成任务： 洗茶壶");
			Thread.sleep(100);
			System.out.println("完成任务： 洗茶杯");


			System.out.println("送茶： " );
			return "龙井";
		}
	}




}
