package com.lrh.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * fork join 实现菲波那切数列
 * @description:
 * @author: lrh
 * @date: 2020/5/26 08:46
 */
public class FibonacciDemo {

	public static void main(String[] args) {
		ForkJoinPool forkJoinPool = new ForkJoinPool(4);
		Fibonacci fibonacci = new Fibonacci(5);
		Integer result = forkJoinPool.invoke(fibonacci);
		System.out.println(result);
	}

	static class Fibonacci extends RecursiveTask<Integer> {

		private Integer n;

		public Fibonacci(Integer n) {
			this.n = n;
		}

		@Override
		protected Integer compute() {
			if (n <= 1) {
				return n;
			}
			Fibonacci fn1 = new Fibonacci(n - 1);
			fn1.fork();
			Fibonacci fn2 = new Fibonacci(n - 2);
			int re = fn1.join() + fn2.compute();

//			System.out.println(re);
			return re;
		}
	}
}

