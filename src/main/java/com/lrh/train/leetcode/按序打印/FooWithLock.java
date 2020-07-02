package com.lrh.train.leetcode.按序打印;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 用{@link Semaphore} 实现按顺序执行代码
 * @description:
 * @author: lrh
 * @date: 2020/7/1 18:38
 */
public class FooWithLock {

  private  Lock lock = new ReentrantLock();
  Condition condition2 = null;
  Condition condition3 = null;

  private AtomicBoolean p2 = new AtomicBoolean(false);
  private AtomicBoolean p3 = new AtomicBoolean(false);



  public FooWithLock() {
    this.condition2 = lock.newCondition();
    this.condition3 = lock.newCondition();
  }

  public static void main(String[] args) {

    final FooWithLock fooWithLock = new FooWithLock();
    new Thread(() -> {
      sleep(1, TimeUnit.SECONDS);
      fooWithLock.print1();
    }).start();

    new Thread(() -> {
      try {
        fooWithLock.print2();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();


    new Thread(() -> {
      try {
        fooWithLock.print3();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }).start();

    CountDownLatch countDownLatch = new CountDownLatch(1);
    try {
      countDownLatch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }


  public  void print1(){
    System.out.println("one");
    while (true) {
      lock.lock();
      try {
        p2.compareAndSet(false, true);
        condition2.signalAll();
        break;
      } finally {
        lock.unlock();
      }
    }
  }

  public void print2() throws InterruptedException {
    while (true) {
      lock.lock();
      try {
        while (!p2.get()) {
          condition2.await();
        }
        System.out.println("two");
        p3.compareAndSet(false, true);
        condition3.signalAll();
        break;
      } finally {
        lock.unlock();
      }
    }
  }

  public void print3() throws InterruptedException {
    while (true) {
      lock.lock();
      try {
        while (!p3.get()) {
          condition3.await();
        }
        System.out.println("three");
        break;
      } finally {
        lock.unlock();
      }
    }

  }


  public static  void sleep(long timeout, TimeUnit timeUnit) {
    try {
      timeUnit.sleep(timeout);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }




}
