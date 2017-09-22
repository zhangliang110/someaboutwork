/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)EchoClientHandler.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月21日
 */
package org.demo.netty.ch7;

import org.demo.netty.ch6.UserInfo;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/** 
 * 使用msgpack进行编解码的客户端处理类
 * <p>
 * <a href="EchoClientHandler.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class EchoClientHandler extends ChannelHandlerAdapter{
    private int sendNumber;
    
    public EchoClientHandler(int sendNumber) {
        this.sendNumber = sendNumber;
    }
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        UserInfo[] users = this.userInfo();
        for (UserInfo userInfo : users) {
            ctx.write(userInfo);
        }
        ctx.flush();
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("client receive the msgpack message :" + msg);
        ctx.write(msg);
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
    
    private UserInfo[] userInfo() {
        UserInfo[] userInfos = new UserInfo[sendNumber];
        UserInfo userInfo = null;
        for (int i = 0; i < sendNumber; i++) {
            userInfo = new UserInfo().buildUserId(123).buildUserName("zhanglin" + i);
            userInfos[i] = userInfo;
        }
        return userInfos;
    }

}
