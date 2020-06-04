package com.lrh.threadLocal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ThreadLocalDemoOne {

    public static void main(String[] args) {

        List<MyThread> threadList = new ArrayList();
        for (int i = 0; i < 10; i++) {
            MyThread thread = new MyThread(() -> {
                for (int j = 0; j < 2; j++) {
                    System.out.println("第" + j + "次打印： " + Thread.currentThread().getName() + ": " + ThreadLocalId.get());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }, "name-" + i);

            thread.start();
            threadList.add(thread);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("===============================================");


    }


    public static class MyThread extends Thread{

        public MyThread(Runnable target) {
            super(target);
        }

        public MyThread(Runnable target, String name) {
            super(target, name);
        }

        private  void printThreadId(){

            System.out.println("第二次打印： " + this.getName() + ":" +ThreadLocalId.get());

        }

    }



    public static class ThreadLocalId {

        private static AtomicLong nextId = new AtomicLong(0);

        static final ThreadLocal<Long> idLocal = ThreadLocal.withInitial(() -> nextId.getAndIncrement());

        static Long get(){
            return  idLocal.get();
        }


    }

}
