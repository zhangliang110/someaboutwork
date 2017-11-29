package org.demo.concurrent;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    private static final int GROUP_SIZE = 5;
    
    public static void main(String[] args) {
        group("1");
        group("2");
    }

    private static void group(String name) {
        final CountDownLatch startCountDown = new CountDownLatch(1);
        final CountDownLatch endCountDown = new CountDownLatch(GROUP_SIZE);
        for (int i = 0; i < GROUP_SIZE; i++) {
            new Thread(String.valueOf(i)){ 
                @Override
                public void run() {
                    System.out.println("group" + name + "ready to execute Thread is " + this.getName());
                    try {
                        startCountDown.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("group" + name + "finish the task Thread is " + this.getName());
                    endCountDown.countDown();
                }
            }.start();
        }
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(name + "process");
        startCountDown.countDown();
        try {
            endCountDown.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(name + "finish");
    }
    
}