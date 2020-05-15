package com.advan.test;


public class Test {
    public static void main(String[] args) throws InterruptedException {
        int Ncpus=Runtime.getRuntime().availableProcessors();
        System.out.println(Ncpus);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("111");
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("222");
            }
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("over");
    }
}
