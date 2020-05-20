package com.lrh.stampedLockDemo;

import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock  的使用示例
 *  StampedLock的实现类似数据库中增加的version
 * @description:
 * @author: lrh
 * @date: 2020/5/20 10:29
 */
public class Point {
	private int x, y;
	private StampedLock stampedLock = new StampedLock();

	double distanceFromOrigin() {
		//乐观读
		long stamp = stampedLock.tryOptimisticRead();

		//读取的时候数据可能被修改
		int curX = x, curY = y;

		//validate 如果操作期间被修改返回false
		if (!stampedLock.validate(stamp)) {
			//升级到悲观锁
			stamp = stampedLock.readLock();

			//再次读取
			try {
				curX = x;
				curY = y;
			} finally {
				//释放悲观锁
				stampedLock.unlockRead(stamp);
			}
		}

		return Math.sqrt(curX * curX + curY * curY);
	}


	/**
	 * StampedLock 读锁的使用模板
	 */
	public void templateOfReadLock() {
		long stamp = stampedLock.tryOptimisticRead();
		//1.读入数据到方法的局部变量
		System.out.println("读入方法的局部变量");

		//2.检测乐观读期间是否数据有变更
		if (!stampedLock.validate(stamp)) {
			//3.升级到悲观锁
			stamp = stampedLock.readLock();

			try {
				System.out.println("再次读入数据到局部变量");
			} finally {
				//4.释放悲观锁
				stampedLock.unlockRead(stamp);
			}
		}

		//5.使用局部变量处理业务
		System.out.println("使用局部变量处理业务");
	}


	/**
	 * StampedLock 写锁的使用模板
	 */
	public void templateOfWriteLock() {
		long stamp = stampedLock.writeLock();
		try {
			System.out.println("写入数据");
		} finally {
			stampedLock.unlockWrite(stamp);
		}
	}

}
