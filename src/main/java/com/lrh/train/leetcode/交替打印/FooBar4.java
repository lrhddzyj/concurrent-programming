package com.lrh.train.leetcode.交替打印;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description:
 * @author: lrh
 * @date: 2020/7/3 18:11
 */
public class FooBar4 {

  private volatile int n;

  private volatile boolean printNewLine = false;

  private Lock lock = new ReentrantLock();

  private Condition fooCondition = null;
  private Condition barCondition = null;


  public FooBar4(int n) {
    this.n = n;
    fooCondition = lock.newCondition();
    barCondition = lock.newCondition();
    if (n > 0) {
      printNewLine = true;
    }

  }

  public void foo(Runnable runnable) {
    while (true) {
      lock.lock();
      try {
        if(!printNewLine){
          fooCondition.await();
        }
        runnable.run();
        printNewLine = false;
        barCondition.signalAll();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
      }

    }
  }

  public void bar(Runnable runnable) {
    while (true) {
      lock.lock();
      try{
        if (n > 0) {
          runnable.run();
          --n;
          if(n > 0){
            printNewLine = true;
            fooCondition.signalAll();
            barCondition.await();
          }
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
      }
    }

  }

  public static void main(String[] args) {
    final FooBar4 fooBar4 = new FooBar4(3);
    Runnable r1 = () ->{
      System.out.print("foo");
    };
    Runnable r2 = () ->{
      System.out.print("bar");
      System.out.println();
    };

    Thread t1 = new Thread(() -> {
      fooBar4.foo(r1);
    });
    Thread t2 = new Thread(() -> {
      fooBar4.bar(r2);
    });

    t1.start();
    t2.start();

  }



}
