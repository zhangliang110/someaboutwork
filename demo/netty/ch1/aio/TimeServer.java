/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)TimeServer.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月18日
 */
package org.demo.netty.ch1.aio;

/** 
 * 使用AIO 编写时间服务器。
 * AIO相比与JDK7中的nio不需要通过selector的轮询去遍历操作key，实现了真正的异步操作
 * 
 * <p>
 * <a href="TimeServer.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class TimeServer {
    public static void main(String[] args) {
        int port = 8088;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (Exception e) {
                //使用默认端口
            }
        }
        new Thread(new AsyncTimeServerHandler("127.0.0.1", port), "ASYNC_TIMEServer001").start();
    }
}
