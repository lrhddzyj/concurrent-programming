package com.lrh.train.leetcode.交替打印;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

/**
 * 优化版本
 * @description:
 * @author: lrh
 * @date: 2020/7/3 09:56
 */
public class FooBar2 {

  public static int curr = 0;

  private static Semaphore fooSemaphore = new Semaphore(0);

  private Semaphore barSemaphore = new Semaphore(0);


  public void foo(Runnable runnable) throws InterruptedException {
    while (true) {
      fooSemaphore.acquire();
      runnable.run();
      barSemaphore.release();
    }
  }

  public void bar(Runnable runnable) throws InterruptedException {
    while (true) {
      barSemaphore.acquire();
      runnable.run();
      --curr;
      if (curr > 0) {
        fooSemaphore.release();
      }
    }
  }

  public static void main(String[] args) throws InterruptedException {
    final FooBar2 fooBar2 = new FooBar2();
    Thread t1 = new Thread(() -> {
      try {
        fooBar2.foo(() -> {
          System.out.print("foo");
        });
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    });

    Thread t2 = new Thread(() -> {
      try {
        fooBar2.bar(() -> {
          System.out.print("bar");
          System.out.println();
        });
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    });

    t1.start();
    t2.start();

    Scanner scanner = new Scanner(System.in);
    System.out.println("输入循环次数：");
    while (scanner.hasNext()) {
      String next = scanner.next();
      try {
        int inputNum = Integer.valueOf(next);
        curr = inputNum;
        if (curr > 0) {
          fooSemaphore.release();
        }
      } catch (NumberFormatException e) {

      }

    }

  }

}
