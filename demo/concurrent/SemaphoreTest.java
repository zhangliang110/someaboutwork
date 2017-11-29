/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)SemaphoreTest.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年11月17日
 */
package org.demo.concurrent;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.Semaphore;

import org.apache.commons.lang.time.DateFormatUtils;


/** 
 * 信号量类测试
 * 
 * <p>
 * <a href="SemaphoreTest.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class SemaphoreTest {
    private static final Semaphore MAX_SEM_PHORE = new Semaphore(10);
    
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            final int num = i;
            final Random random = new Random();
            new Thread() {
                
                @Override
                public void run() {
                    boolean acquired = false;
                    try {
                        MAX_SEM_PHORE.acquire();
                        acquired = true;
                        System.out.println("线程" + num + "获得使用权" + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                        long time = 1000 * Math.max(1, Math.abs(random.nextInt() % 10));
                        Thread.sleep(time);
                        System.out.println("线程" + num + "执行结束" + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (acquired) {
                            MAX_SEM_PHORE.release();
                        }
                    }
                }
            }.start();
        }
    }
}
