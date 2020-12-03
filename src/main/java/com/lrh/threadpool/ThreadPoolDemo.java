package com.lrh.threadpool;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description:
 * @author: lrh
 * @date: 2020/12/3 08:25
 */
public class ThreadPoolDemo {

  public static void main(String[] args) {

    ExecutorService executorService = Executors.newFixedThreadPool(3);
    executorService.submit(new MyRunner("a"));
    executorService.submit(new MyRunner("b"));
  }


  static class MyRunner implements Runnable {

    private String p;

    public MyRunner(String p) {
      this.p = p;
    }

    @Override
    public void run() {

      System.out.println("信息：" + p);

    }
  }

}

