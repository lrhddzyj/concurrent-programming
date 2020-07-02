package com.lrh.train.leetcode.按序打印;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @description:
 * @author: lrh
 * @date: 2020/7/2 09:37
 */
public class FooWithSynchronized {
  private AtomicBoolean p2 = new AtomicBoolean(false);
  private AtomicBoolean p3 = new AtomicBoolean(false);

  public static void main(String[] args) {
    final FooWithSynchronized fooWithSynchronized = new FooWithSynchronized();
    new Thread(() ->{

      fooWithSynchronized.print1();

    }).start();

    new Thread(() ->{
      fooWithSynchronized.print2();
    }).start();

    new Thread(() ->{
      fooWithSynchronized.print3();


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
    sleep(1,TimeUnit.SECONDS);
    p2.compareAndSet(false, true);
  }

  public void print2(){
    while (!p2.get()){
    }
    System.out.println("two");
    p3.compareAndSet(false, true);
  }

  public void print3(){
    while (!p3.get()){
    }
    System.out.println("three");
  }


  public static  void sleep(long timeout, TimeUnit timeUnit) {
    try {
      timeUnit.sleep(timeout);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }



}
