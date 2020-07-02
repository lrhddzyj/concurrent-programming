package com.lrh.train.leetcode.按序打印;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 用{@link Semaphore} 实现按顺序执行代码
 * @description:
 * @author: lrh
 * @date: 2020/7/1 18:38
 */
public class FooWithCountDownLatch {

  private CountDownLatch countDownLatch2 = new CountDownLatch(1);
  private CountDownLatch countDownLatch3 = new CountDownLatch(1);

  public static void main(String[] args) {

    final FooWithCountDownLatch fooWithCountDownLatch = new FooWithCountDownLatch();
    new Thread(() -> {
      sleep(1, TimeUnit.SECONDS);
      fooWithCountDownLatch.print1();
    }).start();

    new Thread(() -> {
      fooWithCountDownLatch.print2();
    }).start();


    new Thread(() -> {
      fooWithCountDownLatch.print3();
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
    countDownLatch2.countDown();
  }

  public void print2(){
    try {
      countDownLatch2.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("two");
    countDownLatch3.countDown();
  }

  public void print3(){
    try {
      countDownLatch3.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
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
