/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)WebSocketServerHandler.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月22日
 */
package org.demo.netty.ch11;


import java.util.Date;

import org.apache.log4j.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;

/** 
 * 
 * <p>
 * <a href="WebSocketServerHandler.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {
    
    private WebSocketServerHandshaker handShaker;
    
    private static final Logger logger = Logger.getLogger(WebSocketServerHandler.class);
    
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
        //传统的Http接入
        if (msg instanceof FullHttpRequest) {
            this.handleHttpRequest(ctx, (FullHttpRequest)msg);
        } else if (msg instanceof WebSocketFrame) {
            //websocket接入
            this.handleWebSocketFrame(ctx, (WebSocketFrame)msg);
        }
    }
    
    /**
     * 函数的目的/功能
     * @param ctx
     * @param msg
    */
    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        //判断是否关闭链路的指令
        if (frame instanceof CloseWebSocketFrame) {
            handShaker.close(ctx.channel(), (CloseWebSocketFrame)frame);
        }
        //判断是否为ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PingWebSocketFrame(frame.content().retain()));
        }
        //只支持文本消息
        if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass().getName()));
        }
        
        //返回应答消息
        String request = ((TextWebSocketFrame)frame).text();
        if (logger.isInfoEnabled()) {
            logger.info(String.format("%s received %s", ctx.channel(), request));
        }
        ctx.channel().write(new TextWebSocketFrame(request + "欢迎使用netty 服务， 现在时刻" + new Date().toString()));
    }

    /**
     * 第一次握手请求由Http协议承载，
     * @param ctx
     * @param req
    */
    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        //如果http解码失败，返回Http异常
        if (req.getDecoderResult().isFailure() || (!"websocket".equals(req.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        
        //构造握手响应返回，测试
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://localhost:8088/websocket", null, false);
        handShaker = wsFactory.newHandshaker(req);
        if (handShaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        } else {
            handShaker.handshake(ctx.channel(), req);
        }
        
    }

    /**
     * 函数的目的/功能
     * @param ctx
     * @param req
     * @param defaultFullHttpResponse
    */
    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req,
            DefaultFullHttpResponse res) {
        //返回应答给客户端
        if (res.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            HttpHeaders.setContentLength(res, res.content().readableBytes());
        }
        
        //如果是非 keep-Alive 连接，关闭连接
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpHeaders.isKeepAlive(req) || res.getStatus().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
    }

}
