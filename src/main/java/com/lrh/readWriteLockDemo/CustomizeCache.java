package com.lrh.readWriteLockDemo;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @description:
 * @author: lrh
 * @date: 2020/5/20 09:02
 */
public class CustomizeCache<K, V> {
	final Map<K, V> data = new HashMap();
	final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	final Lock readLock = readWriteLock.readLock();
	final Lock writeLock = readWriteLock.writeLock();

	public V get(K key) {
		//1.直接从缓存中获取
		V v = null;
		readLock.lock();
		try {
			v = data.get(key);
		} finally {
			readLock.unlock();
		}

		//2.判断缓存中是否存在
		if (v == null) {
			//3.从数据库中获取
			v = getVFromDatabase(key);
			//4.写入缓存
			write(key, v);
		}
		return v;
	}


	/**
	 * 写数据
	 * @param key
	 * @param v
	 */
	public void write(K key, V v) {
		//3.从数据库中获取 并写入缓存
		writeLock.lock();
		try {
			data.put(key, v);
		} finally {
			writeLock.unlock();
		}

	}

	/**
	 * 锁升级(读锁 -> 写锁)（不允许）
	 * 读锁没有释放的前提下 写锁lock的时候一直等待读锁的释放，会造成死锁
	 * @param key
	 * @return
	 */
	public V lockUpgrade(K key) {
		//1.直接从缓存中获取
		V v = null;
		readLock.lock();
		try {
			v = data.get(key);
			//2.判断缓存中是否存在
			if (v == null) {
				//3.从数据库中获取
				v = getVFromDatabase(key);
				//4.写入缓存
				writeLock.lock(); // 此处会造成死锁！！！！
				try {
					data.put(key, v);
				} finally {
					writeLock.unlock();
				}
			}
		} finally {
			readLock.unlock();
		}
		return v;
	}


	/**
	 * 锁降级（写锁 -> 读锁）
	 */
	public void lockDowngrade(){
		writeLock.lock();
		try {
			System.out.println("do something");
			//do something
			readLock.lock(); //锁降级 是允许的
		} finally {
			writeLock.unlock();
		}

		//此时任然持有读锁
		try {
			System.out.println("do something");
			//do something
		} finally {
			readLock.unlock();
		}
	}






	/**
	 * 从数据库中获取数据
	 * @param key
	 * @return
	 */
	private V getVFromDatabase(K key) {
		return null;
	}


}
