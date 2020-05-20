package com.lrh.semaphoreDemo;

import java.util.Random;

/**
 * @description:
 * @author: lrh
 * @date: 2020/5/19 19:30
 */
public class RandomGood {
	private String id;

	public RandomGood() {
		this.id = Integer.toString(new Random().nextInt(10000));
	}

	@Override
	public String toString() {
		return "RandomGood{" +
				"id='" + id + '\'' +
				'}';
	}
}
