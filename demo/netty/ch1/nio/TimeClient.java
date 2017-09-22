/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)TimeClient.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月18日
 */
package org.demo.netty.ch1.nio;

/** 
 * 使用nio 编写异步写，读消息的客户端
 * 重点在于通过 创建clientHandler线程来处理异步的读写操作
 * <p>
 * <a href="TimeClient.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class TimeClient {
    public static void main(String[] args) {
        int port = 9099;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                //使用默认值
                e.printStackTrace();
            }
        }
        new Thread(new TimeClientHandler("localhost", port), "TimeClient001").start();
    }
}
