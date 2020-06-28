package com.lrh;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteArrayList 示例
 * @description:
 * @author: lrh
 * @date: 2020/6/23 11:23
 */
public class CopyOnWriteArrayListDemo {

	public static void main(String[] args) {
		CopyOnWriteArrayList copyOnWriteArrayList = new CopyOnWriteArrayList();

		copyOnWriteArrayList.add("a");
		copyOnWriteArrayList.addIfAbsent("b");
		copyOnWriteArrayList.addIfAbsent("a");

		List list = copyOnWriteArrayList.subList(0, 1);

		//copyOnWriteArrayList 不支持增删改的意思是：不支持对原List的修改，修改其实是生成新的List
		System.out.println(copyOnWriteArrayList);
		System.out.println(list);

	}
}
