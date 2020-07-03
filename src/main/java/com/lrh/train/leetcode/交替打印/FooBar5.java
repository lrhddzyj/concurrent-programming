package com.lrh.train.leetcode.交替打印;

import java.util.concurrent.LinkedBlockingQueue;

/**
 *  交替打印的阻塞队列的实现
 * @description:
 * @author: lrh
 * @date: 2020/7/3 18:55
 */
public class FooBar5 {

  private volatile int n = 0;
  public LinkedBlockingQueue fooQueue = new LinkedBlockingQueue(1);
  private LinkedBlockingQueue barQueue = new LinkedBlockingQueue(1);

  public FooBar5(int n) {
    this.n = n;
  }

  public void foo(Runnable runnable) {
    while (true) {
      try {
        fooQueue.take();
        runnable.run();
        barQueue.put("");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public void bar(Runnable runnable) {
    while (true) {
      try {
        barQueue.take();
        runnable.run();
        --n;
        if (n > 0) {
          fooQueue.put("");
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

  }


  public static void main(String[] args) {
    final FooBar5 fooBar5 = new FooBar5(5);

    Runnable r1 = () ->{
      System.out.print("foo");
    };
    Runnable r2 = () ->{
      System.out.print("bar");
      System.out.println();
    };

    Thread t1 = new Thread(() -> {
      fooBar5.foo(r1);
    });
    Thread t2 = new Thread(() -> {
      fooBar5.bar(r2);
    });

    t1.start();
    t2.start();
    try {
      fooBar5.fooQueue.put("");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
