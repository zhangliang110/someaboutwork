/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)ThreadStopExample.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年10月16日
 */
package org.demo.netty.ch20;

import java.util.concurrent.TimeUnit;

/** 
 * volatile使用
 * 
 * <p>
 * <a href="ThreadStopExample.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class ThreadStopExample {
    private static boolean stop;
    public static void main(String[] args) throws Exception {
        Thread wrokThread = new Thread(new Runnable() {
            
            @Override
            public void run() {
                int i = 0;
                while(!stop) {
                    i++;
                    try {
                        System.out.println(i);
                        TimeUnit.SECONDS.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                
            }
        });
        wrokThread.start();
        TimeUnit.SECONDS.sleep(3);
        stop = true;
    }
}
