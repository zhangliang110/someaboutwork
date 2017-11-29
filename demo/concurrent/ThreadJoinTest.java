/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)ThreadJoinTest.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年11月17日
 */
package org.demo.concurrent;

/** 
 * join
 * 
 * <p>
 * <a href="ThreadJoinTest.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class ThreadJoinTest {
    private static final int GROUP_SIZE = 5;
    
    public static void main(String[] args) throws Exception {
        Thread[] threadGroup1 = new Thread[5];
        Thread[] threadGroup2 = new Thread[5];
        for (int i = 0; i < GROUP_SIZE; i++) {
            final int num = i;
            threadGroup1[i] = new Thread() {
                public void run() {
                    int j = 0;
                    while (j++ < 10) {
                        System.out.println("1号组线程" + num + "这是第" + j + "次执行");
                    }
                };
            };
            threadGroup2[i] = new Thread() {
                public void run() {
                    int j = 0;
                    while (j++ < 10) {
                        System.out.println("2号组线程" + num + "这是第" + j + "次执行");
                    }
                };
            };
            threadGroup1[i].start();
        }
        for (int i = 0; i < threadGroup1.length; i++) {
            threadGroup1[i].join();
        }
        System.out.println("线程组1执行结束");
        for (int i = 0; i < threadGroup2.length; i++) {
            threadGroup2[i].start();
            threadGroup2[i].join();
        }
        System.out.println("全部结束");
    }
}
