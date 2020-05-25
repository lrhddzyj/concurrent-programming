package com.lrh.future;

import com.sun.xml.internal.ws.util.CompletedFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * CompletableFuture 实现了 CompletionStage
 *
 * CompletionStage 描述了任务之间的时序关系
 * @description:
 * @author: lrh
 * @date: 2020/5/25 10:04
 */
public class CompletableFutureCompletionStageDemo {

	public static void main(String[] args) throws ExecutionException, InterruptedException {

		// 串行执行
//		useForSerial();

		//描述 And 关系的执行
//		useWithAndConvergenceRelationship();

		//描述 Or 关系的执行
//		useWithOrConvergenceRelationship();


		//异常处理
		runWithException();


	}


	/**
	 * 串行调用示例
	 * 描述串行关系的接口，主要是以下
	 * thenApply thenAccept thenRun thenCompose 系列接口
	 */
	public static void useForSerial() throws ExecutionException, InterruptedException {
		// 步骤1 到 步骤3 串行执行
		CompletableFuture<String> f0 = CompletableFuture
				//步骤1
				.supplyAsync(() -> {
					sleepHundred();
					System.out.println("执行步骤1");
					return "Hello World";
				})
				//步骤2
				.thenApply(s -> {
					sleepHundred();
					System.out.println("执行步骤2");
					return s + " ZZ";
				})
				//步骤3
				.thenApply(s -> {
					sleepHundred();
					System.out.println("执行步骤3");
					return s.toUpperCase();
				});

		System.out.println("=====wait======");
		System.out.println(f0.get());
	}

	/**
	 * 描述AND 组合关系 示例
	 * 主要接口
	 * thenCombine thenAcceptBoth runAfterBoth
	 *
	 */
	public static void useWithAndConvergenceRelationship() throws ExecutionException, InterruptedException {
		//分支1和分支2 必须都执行后才能返回
		CompletableFuture f0 = CompletableFuture
				.runAsync(() -> {
					System.out.println("执行分支1");
//					return "Hello world1";
				})
				.thenCombine(CompletableFuture.supplyAsync(() -> {
					System.out.println("执行分支2");
					return "Hello World2";
				}),(k,v) ->{
					System.out.println("k=" + k );
					return v;
		     });
		System.out.println("=====wait======");
		System.out.println(f0.get());
	}



	/**
	 * 描述OR 关系 示例
	 * 主要接口
	 * applyToEither acceptEither runAfterEither
	 *
	 */
	public static  void  useWithOrConvergenceRelationship() throws ExecutionException, InterruptedException {

		//分支1和分支2 其中一个执行后就可以返回
		CompletableFuture f0 = CompletableFuture
				.runAsync(() -> {
					sleepHundred();
					System.out.println("执行分支1");
//					return "Hello world1";
				})
				.applyToEither(CompletableFuture.runAsync(() -> {
					for (int i = 0; i < 10 ; i++) {
						sleepHundred();
					}
					System.out.println("执行分支2");
				}), aVoid -> "ABC");
		System.out.println("=====wait======");
		System.out.println(f0.get());


	}


	/**
	 *  异常处理 示例
	 *  接口
	 *   exceptionally -> 类似于try()catch{}处理
	 *   whenComplete -> 类似try()finally{}处理
	 *   handle -> 类似try()finally{}处理 但是支持返回结果
	 *
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public static void runWithException() throws ExecutionException, InterruptedException {

		CompletableFuture f0 = CompletableFuture.supplyAsync(() -> {
			if (true) {
				throw new RuntimeException("测试异常");
			}
			return "hello world";
		}).exceptionally(e -> {
			System.out.println("类似于try()catch{}处理");
			return e.getMessage();
		}).whenComplete((t, u) -> {
			System.out.println("类似于try()finally{}处理");
			System.out.println(t);
			System.out.println(u);
		}).handle((t, u) -> {
			System.out.println("类似于try()finally{}处理 区别在于有返回");
			System.out.println(t);
			System.out.println(u);
			return "finally 返回";
		});

		System.out.println(f0.get());

	}



	private static void sleepHundred() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();

		}

	}


}
