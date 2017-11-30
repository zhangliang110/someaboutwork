/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)ThreadNotifyWait.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年11月17日
 */
package org.demo.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/** 
 * 
 * 
 * <p>
 * <a href="ThreadNotifyWait.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class ThreadNotifyWait {
    private static final int THREAD_COUNT = 100;
    private static final int QUERY_MAX_LENGTH = 2;
    private static final AtomicInteger NOW_CALL_COUNT = new AtomicInteger();
    
    public static void main(String[] args) {
        Thread[] threads = new Thread[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] = new Thread(String.valueOf(i)) {
                @Override
                synchronized public void run() {
                    int nowValue = NOW_CALL_COUNT.get();
                    while(true) {
                        if (nowValue < QUERY_MAX_LENGTH && NOW_CALL_COUNT.compareAndSet(nowValue, nowValue + 1)) {
                            break;
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        nowValue = NOW_CALL_COUNT.get();    //获取数据用于对比
                    }
                    System.out.println(this.getName() + "======开始操作了");
                    try {  
                        Thread.sleep(1000);  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }
                    System.out.println(this.getName() + "=====操作结束");
                    NOW_CALL_COUNT.getAndDecrement();
                    this.notify();
                }
                
            };
        }
        
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
    }
}
