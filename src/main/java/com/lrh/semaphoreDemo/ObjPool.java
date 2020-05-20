package com.lrh.semaphoreDemo;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

/**
 * @description:
 * @author: lrh
 * @date: 2020/5/19 19:14
 */
public class ObjPool<T, R> {

	private int poolSize;

	private List<T> objList;

	private Semaphore semaphore;

	public ObjPool(int poolSize, Class<T> t) {
		this.poolSize = poolSize;
		this.initObjList(t);
		semaphore = new Semaphore(poolSize);
	}

	private void initObjList(Class<T> t){
		objList = new Vector<>(poolSize);
		for (int i = 0; i < poolSize; i++) {
			T instance = null;
			try {
				instance = t.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			if (instance == null) {
				System.out.println("出现为NULL对象");
			}else{
				objList.add(instance);
			}
		}
	}

	public R exec(Function<T, R> function) {
		T t = null;
		try {
			semaphore.acquire();
			t = objList.remove(0);
			return function.apply(t);
		} catch (InterruptedException e) {
			throw new RuntimeException();
		} finally {
			objList.add(t);
			semaphore.release();
		}
	}

}
