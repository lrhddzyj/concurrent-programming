package com.lrh.producterAndCusumer.semaphoreDemo;

import com.lrh.producterAndCusumer.Goods;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * @description:
 * @author: lrh
 * @date: 2020/5/19 13:41
 */
public class Cusumer extends Thread {

	private String threadName;

	private Queue<Goods> queue;

	private Semaphore queueSizeSemaphore;

	private Semaphore concurrentWriteSemaphore;

	private Semaphore notEmptySemaphore;

	public Cusumer(String threadName, Queue<Goods> queue, Semaphore queueSizeSemaphore, Semaphore concurrentWriteSemaphore, Semaphore notEmptySemaphore) {
		this.threadName = threadName;
		this.queue = queue;
		this.queueSizeSemaphore = queueSizeSemaphore;
		this.concurrentWriteSemaphore = concurrentWriteSemaphore;
		this.notEmptySemaphore = notEmptySemaphore;
	}

	@Override
	public void run() {
		while (true) {
			try {
				notEmptySemaphore.acquire();
				concurrentWriteSemaphore.acquire();
				Goods goods = queue.remove();
				System.out.println(String.format("线程 【%s】从队列中获取一个商品 %s", threadName, goods));
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				concurrentWriteSemaphore.release();
				queueSizeSemaphore.release();
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

	public Queue<Goods> getQueue() {
		return queue;
	}

	public void setQueue(Queue<Goods> queue) {
		this.queue = queue;
	}

	public Semaphore getQueueSizeSemaphore() {
		return queueSizeSemaphore;
	}

	public void setQueueSizeSemaphore(Semaphore queueSizeSemaphore) {
		this.queueSizeSemaphore = queueSizeSemaphore;
	}

	public Semaphore getConcurrentWriteSemaphore() {
		return concurrentWriteSemaphore;
	}

	public void setConcurrentWriteSemaphore(Semaphore concurrentWriteSemaphore) {
		this.concurrentWriteSemaphore = concurrentWriteSemaphore;
	}

	public Semaphore getNotEmptySemaphore() {
		return notEmptySemaphore;
	}

	public void setNotEmptySemaphore(Semaphore notEmptySemaphore) {
		this.notEmptySemaphore = notEmptySemaphore;
	}
}
