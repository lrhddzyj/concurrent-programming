package com.lrh.train.leetcode.按序打印;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 用{@link Semaphore} 实现按顺序执行代码
 * @description:
 * @author: lrh
 * @date: 2020/7/1 18:38
 */
public class FooWithSemaphore {

  private static Semaphore semaphore2 = new Semaphore(0);
  private static Semaphore semaphore3 = new Semaphore(0);

  public static void main(String[] args) {
    final FooWithSemaphore fooWithSemaphore = new FooWithSemaphore();
    new Thread(() -> {
      try {
        semaphore2.acquire();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      sleep(1, TimeUnit.SECONDS);
      fooWithSemaphore.print2();
      semaphore2.release();
      semaphore3.release();
    }).start();

    new Thread(() -> {
      try {
        semaphore3.acquire();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      sleep(1, TimeUnit.SECONDS);
      fooWithSemaphore.print3();

    }).start();


    new Thread(() -> {
      fooWithSemaphore.print1();
      sleep(3, TimeUnit.SECONDS);
      semaphore2.release();
    }).start();

  }


  public  void print1(){
    System.out.println("one");
  }

  public void print2(){
    System.out.println("two");
  }

  public void print3(){
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
