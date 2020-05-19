package com.lrh.producterAndCusumer.lockDemo;

import com.lrh.producterAndCusumer.Goods;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @description:
 * @author: lrh
 * @date: 2020/5/19 11:38
 */
public class Cusumer extends Thread {

	private String threadName;

	private Queue<Goods> queue;

	private int maxSize;

	private Lock lock;

	private Condition notEmptyCondition;

	private Condition notFullCondition;

	public Cusumer(String threadName, Queue<Goods> queue, int maxSize, Lock lock, Condition notEmptyCondition, Condition notFullCondition) {
		this.threadName = threadName;
		this.queue = queue;
		this.maxSize = maxSize;
		this.lock = lock;
		this.notEmptyCondition = notEmptyCondition;
		this.notFullCondition = notFullCondition;
		super.setName(threadName);
	}


	@Override
	public void run() {
		while (true) {
			Goods goods;
			lock.lock();

			try {
				while (queue.isEmpty()) {
					System.out.println(String.format("队列为空，线程 【%s】挂起", threadName));
					try {
						notEmptyCondition.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				goods = queue.remove();
				System.out.println(String.format("线程 【%s】从队列中获取一个商品 %s", threadName, goods));
				notFullCondition.signalAll();

				//模拟消费耗时
				try {
					Thread.sleep(new Random().nextInt(1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} finally {
				lock.unlock();
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

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public Lock getLock() {
		return lock;
	}

	public void setLock(Lock lock) {
		this.lock = lock;
	}

	public Condition getNotEmptyCondition() {
		return notEmptyCondition;
	}

	public void setNotEmptyCondition(Condition notEmptyCondition) {
		this.notEmptyCondition = notEmptyCondition;
	}

	public Condition getNotFullCondition() {
		return notFullCondition;
	}

	public void setNotFullCondition(Condition notFullCondition) {
		this.notFullCondition = notFullCondition;
	}
}
