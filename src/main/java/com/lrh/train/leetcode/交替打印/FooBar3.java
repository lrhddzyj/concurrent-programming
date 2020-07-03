package com.lrh.train.leetcode.交替打印;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * @description:
 * @author: lrh
 * @date: 2020/7/3 17:44
 */
public class FooBar3 {

  private volatile int n;

  public FooBar3(int n) {
    this.n = n;
  }

  private CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

  private CountDownLatch countDownLatch = new CountDownLatch(0);

  public void foo(Runnable runnable) {
    while (true) {
      if(n > 0){
        //打印
        runnable.run();
        countDownLatch.countDown();
        try {
          cyclicBarrier.await();
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (BrokenBarrierException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public void bar(Runnable runnable) {
    while (true) {
      if(n > 0){

        try {
          countDownLatch.await();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        runnable.run();
        int newN = n - 1;
        if (newN > 0) {
          countDownLatch = new CountDownLatch(1);
        }
        n = newN;
        try {
          cyclicBarrier.await();
        } catch (InterruptedException e) {
          e.printStackTrace();
        } catch (BrokenBarrierException e) {
          e.printStackTrace();
        }
      }

    }
  }


  public static void main(String[] args) {
    final FooBar3 fooBar3 = new FooBar3(5);
    Runnable r1 = () ->{
      System.out.print("foo");
    };
    Runnable r2 = () ->{
      System.out.print("bar");
      System.out.println();
    };

    Thread t1 = new Thread(() -> {
      fooBar3.foo(r1);
    });
    Thread t2 = new Thread(() -> {
      fooBar3.bar(r2);
    });

    t1.start();
    t2.start();

  }








}
