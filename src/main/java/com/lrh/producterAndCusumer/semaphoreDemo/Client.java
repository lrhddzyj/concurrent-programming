package com.lrh.producterAndCusumer.semaphoreDemo;

import com.lrh.producterAndCusumer.Goods;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

/**
 * @description:
 * @author: lrh
 * @date: 2020/5/19 13:41
 */
public class Client {

	private static int maxSize = 5;

	private static Queue<Goods> queue = new LinkedList<Goods>();

	private static Semaphore concurrentWriteSemaphore = new Semaphore(1);

	private static Semaphore notEmptySemaphore = new Semaphore(0);

	private static Semaphore queueSizeSemaphore = new Semaphore(maxSize);

	public static void main(String[] args) {

		Thread p1 = new Producter("p1", queue, queueSizeSemaphore, concurrentWriteSemaphore, notEmptySemaphore);
		Thread p2 = new Producter("p2", queue, queueSizeSemaphore, concurrentWriteSemaphore, notEmptySemaphore);

		Thread c1 = new Cusumer("c1", queue, queueSizeSemaphore, concurrentWriteSemaphore, notEmptySemaphore);
		Thread c2 = new Cusumer("c2", queue, queueSizeSemaphore, concurrentWriteSemaphore, notEmptySemaphore);

		p1.start();
		p2.start();
		c1.start();
		c2.start();
	}
}
