package com.lrh.producterAndCusumer.lockDemo;

import com.lrh.producterAndCusumer.Goods;


import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description:
 * @author: lrh
 * @date: 2020/5/19 10:48
 */
public class Client {

	private static int maxSize = 5;

	private static Queue<Goods> queue = new LinkedList<Goods>();

	private static Lock lock = new ReentrantLock();

	private static Condition notEmptyCondition = lock.newCondition();

	private static Condition notFullCondition = lock.newCondition();

	public static void main(String[] args) {
		Thread p1 = new Producter("p1", queue, maxSize, lock, notEmptyCondition, notFullCondition);
		Thread p2 = new Producter("p2", queue, maxSize, lock, notEmptyCondition, notFullCondition);
		Thread c1 = new Cusumer("c1", queue, maxSize, lock, notEmptyCondition, notFullCondition);
		Thread c2 = new Cusumer("c2", queue, maxSize, lock, notEmptyCondition, notFullCondition);

		p1.start();
		p2.start();

		c1.start();
		c2.start();
	}
}
