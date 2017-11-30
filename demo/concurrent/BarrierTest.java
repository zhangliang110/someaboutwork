/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)BarrierTest.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年11月17日
 */
package org.demo.concurrent;

import java.util.concurrent.CyclicBarrier;

/** 
 * CyclicBarrier 测试
 * <p>
 * <a href="BarrierTest.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class BarrierTest {
    private static final int THREAD_COUNT = 10;
    
    private static final CyclicBarrier CYCLIC_BARRIER = new CyclicBarrier(THREAD_COUNT, new Runnable() {
        
        @Override
        public void run() {
            System.out.println("========> 我是导游，本次点名结束，准备走下一个环节!");
        }
    });
    
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(String.valueOf(i)) {
                
                public void run() {
                    try {
                        System.out.println("hello 1");
                        CYCLIC_BARRIER.await();
                        System.out.println("hello 2");
                        CYCLIC_BARRIER.await();
                        System.out.println("hello 3");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
            }.start();
        }
    }
}
