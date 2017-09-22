/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)TimeClient.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月18日
 */
package org.demo.netty.ch1.aio;

/** 
 * 异步编程client客户端源码
 * <p>
 * <a href="TimeClient.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class TimeClient {
    public static void main(String[] args) {
        int port = 8088;
        if(args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                //使用默认端口
                e.printStackTrace();
            }
        }
        new Thread(new AsyncTimeClientHandler("127.0.0.1", port), "AsyncTimeClientHandler-001").start();
    }
}
