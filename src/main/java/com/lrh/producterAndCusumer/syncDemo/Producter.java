package com.lrh.producterAndCusumer.syncDemo;

import com.lrh.producterAndCusumer.Goods;

import java.util.Queue;
import java.util.Random;

/**
 * @description:
 * @author: lrh
 * @date: 2020/5/19 10:25
 */
public class Producter extends Thread {

	private String threadName;

	private int maxSize;

	private Queue<Goods> queue;

	public Producter(String threadName, int maxSize, Queue<Goods> queue) {
		this.threadName = threadName;
		this.maxSize = maxSize;
		this.queue = queue;

		super.setName(threadName);
	}

	@Override
	public void run() {
		while (true) {
			Goods goods = new Goods(String.valueOf(new Random().nextLong()));
			//模拟生产耗时
			try {
				Thread.sleep(new Random().nextInt(500));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (queue) {
				while (maxSize == queue.size()) {
					System.out.println(String.format("队列已满，线程 【%s】挂起", threadName));
					try {
						queue.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				queue.add(goods);
				System.out.println(String.format("线程 【%s】生产了一个商品 %s", threadName, goods));
				queue.notifyAll();
			}
		}
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public Queue<Goods> getQueue() {
		return queue;
	}

	public void setQueue(Queue<Goods> queue) {
		this.queue = queue;
	}
}
