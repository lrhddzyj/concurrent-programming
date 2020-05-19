package com.lrh.producterAndCusumer.syncDemo;

import com.lrh.producterAndCusumer.Goods;

import java.util.Queue;
import java.util.Random;

/**
 * @description:
 * @author: lrh
 * @date: 2020/5/19 10:33
 */
public class Cusumer extends Thread {

	private String threadName;

	private int maxSize;

	private Queue<Goods> queue;

	public Cusumer(String threadName, int maxSize, Queue<Goods> queue) {
		this.threadName = threadName;
		this.maxSize = maxSize;
		this.queue = queue;
		super.setName(threadName);
	}

	@Override
	public void run() {
		while (true) {
			Goods goods;
			synchronized (queue) {
				//此处必须采用while 唤起时才能重新判断条件 否则会直接进入到 -> 步骤二
				while (queue.isEmpty()) {
					System.out.println(String.format("队列为空，线程 【%s】挂起", threadName));
					try {
						queue.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				//步骤二
				goods = queue.remove();
				System.out.println(String.format("线程 【%s】从队列中获取一个商品 %s", threadName, goods));
				queue.notifyAll();
			}
			//模拟消费耗时
			try {
				Thread.sleep(new Random().nextInt(1000));
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
