package com.lrh.producterAndCusumer.queueDemo;

import com.lrh.producterAndCusumer.Goods;

import java.util.concurrent.BlockingQueue;

/**
 * @description:
 * @author: lrh
 * @date: 2020/5/19 14:58
 */
public class Customer extends Thread {
	private String threadName;

	private BlockingQueue<Goods> queue;

	public Customer(String threadName, BlockingQueue<Goods> queue) {
		this.threadName = threadName;
		this.queue = queue;
	}


	@Override
	public void run() {
		while (true) {
			try {
				Goods goods = queue.take();
				System.out.println(String.format("线程 【%s】从队列中获取一个商品 %s", threadName, goods));
			} catch (InterruptedException e) {
				e.printStackTrace();

			}

		}
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public BlockingQueue<Goods> getQueue() {
		return queue;
	}

	public void setQueue(BlockingQueue<Goods> queue) {
		this.queue = queue;
	}
}
