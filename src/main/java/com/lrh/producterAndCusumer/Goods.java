package com.lrh.producterAndCusumer;

/**
 * @description:
 * @author: lrh
 * @date: 2020/5/19 10:25
 */
public class Goods {

	private String name;

	public Goods(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Goods{" +
				"name='" + name + '\'' +
				'}';
	}
}
