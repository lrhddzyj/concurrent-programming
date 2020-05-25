package com.lrh.future;

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


		completionService.submit(() -> {
			 sleep(1);
			return "任务[1]完成";
		});

		completionService.submit(() -> {
			sleep(6);
			return "任务[2]完成";
		});

		completionService.submit(() -> {
			sleep(2);
			return "任务[3]完成";
		});

		for (int i = 0; i < 3; i++) {
			String rt = completionService.take().get();
			threadPoolExecutor.execute(() ->{
				System.out.println("处理返回数据：" + rt);
				sleep(1);
			});
		}


	}

	private static void sleep(int second) {
		try {
			Thread.sleep(second * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();

		}

	}

}
