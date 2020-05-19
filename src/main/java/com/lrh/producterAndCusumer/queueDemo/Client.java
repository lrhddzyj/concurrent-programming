package com.lrh.producterAndCusumer.queueDemo;

import com.lrh.producterAndCusumer.Goods;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @description:
 * @author: lrh
 * @date: 2020/5/19 15:00
 */
public class Client {

	private static int maxSize = 5;

	/**
	 * 如果使用LinkedBlockingQueue作为队列实现，
	 * 则可以实现：在同一时刻，既可以放入又可以取出，因为LinkedBlockingQueue内部使用了两个重入锁，分别控制取出和放入。
	 *
	 * 如果使用ArrayBlockingQueue作为队列实现，
	 * 则在同一时刻只能放入或取出，因为ArrayBlockingQueue内部只使用了一个重入锁来控制并发修改操作
	 *
	 */
	private static BlockingQueue<Goods> blockingQueue = new LinkedBlockingDeque(maxSize);

	public static void main(String[] args) {
		Thread p1 = new Producter("p1", blockingQueue);
		Thread p2 = new Producter("p2", blockingQueue);

		Thread c1 = new Customer("c1", blockingQueue);
		Thread c2 = new Customer("c2", blockingQueue);

		p1.start();
		p2.start();

		c1.start();
		c2.start();

	}
}
