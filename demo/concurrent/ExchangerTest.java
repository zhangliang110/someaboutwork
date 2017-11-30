/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)ExchangerTest.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年11月17日
 */
package org.demo.concurrent;

import java.util.concurrent.Exchanger;

/** 
 * 线程数据交换器类
 * 
 * <p>
 * <a href="ExchangerTest.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class ExchangerTest {
    public static void main(String[] args) {
        final Exchanger<Integer> exchanger = new Exchanger<>();
        for (int i = 0; i < 10; i++) {
            final Integer num = i;
            new Thread() {
                public void run() {
                    System.out.println("线程" + this.getName() + "原先的数据为" + num);
                    try {
                        Integer exchangeNum = exchanger.exchange(num);
                        Thread.sleep(1000);
                        System.out.println("线程" + this.getName() + "原来的数据为" + num + "交换后的数据为" + exchangeNum);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                };
            }.start();
        }
    }
}
