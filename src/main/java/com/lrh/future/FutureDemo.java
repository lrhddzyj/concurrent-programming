package com.lrh.future;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import javafx.concurrent.Task;

import java.util.concurrent.*;
import java.util.logging.Logger;

/**
 * @description:
 * @author: lrh
 * @date: 2020/5/22 16:33
 */
public class FutureDemo {

	private static final Logger log = Logger.getLogger(FutureDemo.class.getName());

	static ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder();
	static {
		threadFactoryBuilder.setNameFormat("MY-TEST-%d");
	}

	static ThreadPoolExecutor threadPoolExecutor =
			new ThreadPoolExecutor(1,2,10,
					TimeUnit.SECONDS,new LinkedBlockingQueue<>(1), threadFactoryBuilder.build());
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Future<?> submit = threadPoolExecutor.submit(() -> {
			log.info(Thread.currentThread().getName());
		});

		Thread.sleep(100);
		System.out.println(submit.isCancelled());
		System.out.println(submit.isDone());
		Object o = submit.get();
		System.out.println(o);

		Future<String> stringFuture = threadPoolExecutor.submit(() -> {
			String result = "success";
			return result;
		});

		Thread.sleep(100);
		System.out.println(stringFuture.isCancelled());
		System.out.println(stringFuture.isDone());
		String result = stringFuture.get();
		System.out.println(result);

		ResultObj resultObj = new ResultObj();
		resultObj.setName("人民大会堂");

		Future<ResultObj> submit1 = threadPoolExecutor.submit(new Task(resultObj), resultObj);

		Thread.sleep(100);
		System.out.println(submit1.isCancelled());
		System.out.println(submit1.isDone());
		System.out.println(submit1.get());
	}

	static class ResultObj {

		private String name;

		private String addr;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAddr() {
			return addr;
		}

		public void setAddr(String addr) {
			this.addr = addr;
		}

		@Override
		public String toString() {
			return "ResultObj{" +
					"name='" + name + '\'' +
					", addr='" + addr + '\'' +
					'}';
		}
	}


	static class Task implements Runnable {

		private ResultObj resultObj;

		@Override
		public void run() {
			System.out.println(resultObj.getName());
			resultObj.setAddr("北京！！");
		}

		public Task(ResultObj resultObj) {
			this.resultObj = resultObj;
		}
	}

}
