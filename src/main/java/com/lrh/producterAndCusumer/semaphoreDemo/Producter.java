package com.lrh.producterAndCusumer.semaphoreDemo;

import com.lrh.producterAndCusumer.Goods;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * @description:
 * @author: lrh
 * @date: 2020/5/19 13:40
 */
public class Producter extends Thread {

	private String threadName;

	private Queue<Goods> queue;

	private Semaphore queueSizeSemaphore;

	private Semaphore concurrentWriteSemaphore;

	private Semaphore notEmptySemaphore;

	public Producter(String threadName, Queue<Goods> queue, Semaphore queueSizeSemaphore, Semaphore concurrentWriteSemaphore, Semaphore notEmptySemaphore) {
		this.threadName = threadName;
		this.queue = queue;
		this.queueSizeSemaphore = queueSizeSemaphore;
		this.concurrentWriteSemaphore = concurrentWriteSemaphore;
		this.notEmptySemaphore = notEmptySemaphore;
		super.setName(threadName);

	}

	@Override
	public void run() {
		while (true) {
			Goods goods = new Goods(String.valueOf(new Random().nextLong()));

			try {
				Thread.sleep(new Random().nextInt(1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			try {
				queueSizeSemaphore.acquire();
				concurrentWriteSemaphore.acquire();
				queue.add(goods);
				System.out.println(String.format("线程 【%s】生产了一个商品 %s", threadName, goods));
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				concurrentWriteSemaphore.release();
				notEmptySemaphore.release();
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
