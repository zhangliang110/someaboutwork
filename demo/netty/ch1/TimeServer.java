/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)TimeServer.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月18日
 */
package org.demo.netty.ch1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/** 
 * 同步阻塞创建的T源码分析
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
                port= Integer.valueOf(args[0]);
            } catch(NumberFormatException e) {
                e.printStackTrace();
            }
        }
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("TimeServer start in port" + port);
            Socket socket = null;
            while (true) {
                socket = server.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }
        } catch(IOException e ) {
            e.printStackTrace();
        } finally {
            System.out.println("the time servier is close");
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            server = null;
        }
    }
}
