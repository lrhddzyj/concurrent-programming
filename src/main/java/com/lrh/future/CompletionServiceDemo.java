package com.lrh.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @description: 批量异步处理任务
 * @author: lrh
 * @date: 2020/5/25 11:47
 */
public class CompletionServiceDemo {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ThreadPoolExecutor threadPoolExecutor =
				new ThreadPoolExecutor(3, 3, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

		CompletionService<String> completionService = new ExecutorCompletionService<>(threadPoolExecutor);

		List<Future<String>> futureList = new ArrayList();

		futureList.add(completionService.submit(() -> {
			System.out.println("任务[1]在执行");
			sleep(10);
			return "任务[1]完成";
		}));

		futureList.add(completionService.submit(() -> {
			System.out.println("任务[1-1]在执行");
			sleep(30);
			return "任务[1-1]完成";
		}));

		futureList.add(completionService.submit(() -> {
			System.out.println("任务[1-2]在执行");
			sleep(30);
			return "任务[1-2]完成";
		}));

		futureList.add(completionService.submit(() -> {
			System.out.println("任务[2]在执行");
			sleep(60);
			return "任务[2]完成";
		}));


		futureList.add(completionService.submit(() -> {
			System.out.println("任务[3]在执行");
			sleep(20);
			return "任务[3]完成";
		}));

//		for (Future<String> stringFuture : futureList) {
//			System.out.println(stringFuture.get());
//
//			System.out.println("========================");
//		}

		for (int i = 0; i < 5 ; i++) {
			System.out.println(completionService.take().get());
		}
//		System.out.println("abc");
//		threadPoolExecutor.shutdown();

//		for (int i = 0; i < 100; i++) {
//			String rt = completionService.take().get();
//			threadPoolExecutor.execute(() ->{
//				System.out.println("处理返回数据：" + rt);
//				sleep(1);
//			});
//		}


	}

	private static void sleep(int second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();

		}

	}

}
