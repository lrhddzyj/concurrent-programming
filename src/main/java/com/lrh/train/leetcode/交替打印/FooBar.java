package com.lrh.train.leetcode.交替打印;

import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description:
 * @author: lrh
 * @date: 2020/7/2 17:10
 */
public class FooBar {

  private static Semaphore semaphoreFoo = new Semaphore(0);
  private static Semaphore semaphoreBar = new Semaphore(0);
  private static AtomicInteger printNum = new AtomicInteger(0);


  public static void main(String[] args) {
    FooBar fooBar = new FooBar();
    new Thread(() -> {
      fooBar.foo();
    }).start();

    new Thread(() -> {
      fooBar.bar();
    }).start();

    Scanner sc = new Scanner(System.in);
    System.out.println("请输入循环次数：");
    while (sc.hasNext()) {
      String str = sc.next();
      if ("q".equals(str)) {
        break;
      }
      System.out.println("循环次数:" + str);
      try {
        int newPrintNum = Integer.valueOf(str);
        printNum.compareAndSet(0, newPrintNum);
        semaphoreFoo.release();
      } catch (NumberFormatException e) {
        System.out.println("非法输入:" + str);
      }

    }
    sc.close();
  }

  public void foo() {
    do {
      try {
        semaphoreFoo.acquire();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.print("foo");
      semaphoreBar.release();
    } while (printNum.get() > 0);

  }

  public void bar() {
    int newCurr = 0, oldCurr = 0;
    while (true) {
      try {
        semaphoreBar.acquire();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      oldCurr = printNum.get();
      if(oldCurr > 0){
        newCurr = oldCurr - 1;
        System.out.print("bar");
        System.out.println();
        printNum.compareAndSet(oldCurr, newCurr);
        if(newCurr > 0){
          semaphoreFoo.release();
        }

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
