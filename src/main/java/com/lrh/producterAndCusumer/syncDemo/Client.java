package com.lrh.producterAndCusumer.syncDemo;

import com.lrh.producterAndCusumer.Goods;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @description:
 * @author: lrh
 * @date: 2020/5/19 10:48
 */
public class Client {

	private static int maxSize = 5;

	private static Queue<Goods> queue = new LinkedList<Goods>();


	public static void main(String[] args) {
		Thread p1 = new Producter("p1", maxSize, queue);
		Thread p2 = new Producter("p2", maxSize, queue);

		Thread c1 = new Cusumer("c1", maxSize, queue);
		Thread c2 = new Cusumer("c2", maxSize, queue);
//		Thread c3 = new Cusumer("c3", maxSize, queue);

		p1.start();
		p2.start();

		c1.start();
		c2.start();
//		c3.start();

		while (true) {

		}

	}
}
