/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)TimeServer.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月18日
 */
package org.demo.netty.ch1.nio;

/** 
 * nio的时间处理服务器源码
 * <p>
 * <a href="TimeServer.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class TimeServer {
    public static void main(String[] args) {
        int port = 9099;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                //使用默认的配置端口
                e.printStackTrace();
            }
        }
        new Thread(new MultiplexerTimeServer(port), "TimeServer001").start();
    }
}
