/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)HttpFileServerHandler.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月21日
 */
package org.demo.netty.ch10;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.regex.Pattern;

import javax.activation.MimetypesFileTypeMap;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

/** 
 * http协议 的 服务处理类
 * 
 * <p>
 * <a href="HttpFileServerHandler.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest>{
    private String url;
    
    private static final Pattern INSECURE_URI = Pattern.compile(".*[<&>\"].*");
    
    private static final Pattern ALLOW_FILE_NAME = Pattern.compile("[A-Za-z0-9][-_A-Za-z0-9\\.]*");
    
    public HttpFileServerHandler(String url) {
        this.url = url;
    }
    
    @Override
    protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
        if (req.getDecoderResult().isFailure()) {
            this.sendError(ctx, HttpResponseStatus.BAD_REQUEST);
            return;
        }
        if (req.getMethod() != HttpMethod.GET) {
            this.sendError(ctx, HttpResponseStatus.METHOD_NOT_ALLOWED);
            return;
        }
        final String uri = req.getUri();
        final String path = this.sanitizeUri(uri);
        if (path == null) {
            this.sendError(ctx, HttpResponseStatus.FORBIDDEN);
            return;
        }
        File file = new File(path);
        if (file.isHidden() || !file.canRead()) {
            this.sendError(ctx, HttpResponseStatus.NOT_FOUND);
            return;
        }
        if (file.isDirectory()) {
            if (uri.endsWith("/")) {
                this.sendListing(ctx, file);
            } else {
                this.sendRedirect(ctx, uri + "/");
            }
            return;
        }
        
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "r"); 
        } catch (FileNotFoundException e) {
            this.sendError(ctx, HttpResponseStatus.NOT_FOUND);
            return;
        }
        long fileLength = randomAccessFile.length();
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        //设置响应头中的doc大小
        HttpHeaders.setContentLength(response, fileLength);
        MimetypesFileTypeMap mimeType = new MimetypesFileTypeMap();
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, mimeType.getContentType(file.getPath()));
        if (HttpHeaders.isKeepAlive(req)) {
            response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        }
        ByteBuf buffer = Unpooled.copiedBuffer(Files.readAllBytes(file.toPath()));
        response.content().writeBytes(buffer);
        ctx.writeAndFlush(response);
        
        
       /* 
        * 下面代码有些问题，不能发送文件到浏览器
        * ctx.write(response);
        ChannelFuture sendFileFutre;
        sendFileFutre = ctx.write(new ChunkedFile(randomAccessFile, 0, fileLength, 8192), ctx.newProgressivePromise());
        sendFileFutre.addListener(new ChannelProgressiveFutureListener() {
            
            @Override
            public void operationComplete(ChannelProgressiveFuture future) throws Exception {
                System.out.println("Transfer complete");
            }
            
            @Override
            public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) throws Exception {
                if (total < 0) {
                    System.err.println("Transfer proggerss error" + progress);
                } else {
                    System.err.println("Transfer progress: " + progress + " / " + total);
                }
            }
        });
        
        ChannelFuture lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
        if (!HttpHeaders.isKeepAlive(req)) {
            lastContentFuture.addListener(ChannelFutureListener.CLOSE);
        }*/
    }
    
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if (ctx.channel().isActive()) {
            this.sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * 转发到新的uri
     * @param file
    */
    private void sendRedirect(ChannelHandlerContext ctx, String newUri) {
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FOUND);
        response.headers().set(HttpHeaders.Names.LOCATION, newUri);
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private String sanitizeUri(String uri) {
        try {
            uri = URLDecoder.decode(uri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            try {
                uri = URLDecoder.decode(uri, "ISO-8859-1");
            } catch (UnsupportedEncodingException e2) {
                throw new Error();
            }
        }
        if (!uri.startsWith(url)) {
            return null;
        }
        if (!uri.startsWith("/")) {
            return null;
        }
        uri = uri.replace("/", File.separator);
        if (uri.contains(File.separator + ".") || uri.contains("." + File.separator) || uri.startsWith(".")
                || uri.endsWith(".") || INSECURE_URI.matcher(uri).matches()) {
            //上述都是非法的uri
            return null;
        }
        return System.getProperty("user.dir") + File.separator + uri;
        
    }
    
    private static void sendListing(ChannelHandlerContext ctx, File file) {
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html;charset=UTF-8");
        StringBuilder sb = new StringBuilder();
        String dirPath = file.getPath();
        sb.append("<!DOCTYPE HTML>\r\n");
        sb.append("<html><head><title>");
        sb.append(dirPath);
        sb.append(" 目录 ");
        sb.append("</title></head><body>\r\n");
        sb.append("<h3>");
        sb.append(dirPath).append(" 目录：");
        sb.append("</h3>\r\n");
        sb.append("<ul>");
        sb.append("<li>链接:<a href='../'>..</a></li>\r\n");
        for (File f : file.listFiles()) {
            if (f.isHidden() || !f.canRead()) {
                continue;
            }
            String name = f.getName();
            if (!ALLOW_FILE_NAME.matcher(name).matches()) {
                continue;
            }
            sb.append("<li>链接:<a href='");
            sb.append(name);
            sb.append("'>");
            sb.append(name);
            sb.append("</a></li>\r\n");
        }
        sb.append("</ul></body></html>\r\n");
        ByteBuf buffer = Unpooled.copiedBuffer(sb, CharsetUtil.UTF_8);
        response.content().writeBytes(buffer);
        buffer.release();
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        
    }
    
    private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        DefaultHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, 
                    Unpooled.copiedBuffer("Failure:" + status.toString() + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain;charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        
    }
    
}
