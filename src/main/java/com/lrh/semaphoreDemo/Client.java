package com.lrh.semaphoreDemo;

/**
 * 池化demo
 * @description:
 * @author: lrh
 * @date: 2020/5/19 19:27
 */
public class Client {

	public static void main(String[] args) {
		ObjPool<RandomGood, String> objPool = new ObjPool(10, RandomGood.class);

		for (int i = 0; i < 20; i++) {
			new Thread(() -> {
				while (true) {
					objPool.exec(t -> {
						System.out.println(t);
						return t.toString();
					});
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}).start();
		}

	}

}
