/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)TimeServer2.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月18日
 */
package org.demo.netty.ch1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.Channel;

/** 
 * 伪异步io Server 源码分析
 * 主要是创建了一个    任务处理的线程池。调度式的取对每个客户端的连接进行处理。
 * 这个 线程的数量N 就可以远远的小于客户端的数量M了。因为不是所有的客户端都会同时有任务进行处理。
 * 
 * 由于线程池和消息队列都是有界的，因此不会导致内存溢出
 * <p>
 * <a href="TimeServer2.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class TimeServer2 {
    public static void main(String[] args) {
        int port = 8088;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 使用默认的port
            }
        }
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("The time server is start in port" + port);
            Socket socket = null;
            TimeHandlerExecutePool pool = new TimeHandlerExecutePool(50, 10000);    //创建 I/O 任务线程池
            while (true) {
                socket = server.accept();
                pool.execute(new TimeServerHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                server = null;
            }
        }
    }
}
