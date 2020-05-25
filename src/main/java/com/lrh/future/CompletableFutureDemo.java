package com.lrh.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 *  实现烧茶的流水
 * @description:
 * @author: lrh
 * @date: 2020/5/22 19:25
 */
public class CompletableFutureDemo {

	public static void main(String[] args) {
		CompletableFuture A = CompletableFuture.runAsync(() -> {
			System.out.println("烧水，等B任务完成");
			sleep(1);
		});

		CompletableFuture B = CompletableFuture.supplyAsync(() -> {
			sleep(1);
			System.out.println("完成任务： 洗茶壶");
			sleep(1);
			System.out.println("完成任务： 洗茶杯");
			return "龙井";
		});

		CompletableFuture C = A.thenCombine(B, (v,e) -> {
//			System.out.println(v);
//			System.out.println(e);
			return "上茶：" + e;
		});


		System.out.println(C.join());

	}

	public static void sleep(long l) {
		try {
			TimeUnit.SECONDS.sleep(l);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
