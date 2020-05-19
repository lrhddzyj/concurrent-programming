package com.lrh.producterAndCusumer.queueDemo;

import com.lrh.producterAndCusumer.Goods;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * 用阻塞队列实现生产消费
 * @description:
 * @author: lrh
 * @date: 2020/5/19 14:44
 */
public class Producter extends Thread {

	private String threadName;

	private BlockingQueue<Goods> queue;

	public Producter(String threadName, BlockingQueue<Goods> queue) {
		this.threadName = threadName;
		this.queue = queue;
		super.setName(threadName);
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(new Random().nextInt(1000));
				Goods goods = new Goods(String.valueOf(new Random().nextLong()));
				queue.put(goods);
				System.out.println(String.format("线程 【%s】生产了一个商品 %s", threadName, goods));
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
