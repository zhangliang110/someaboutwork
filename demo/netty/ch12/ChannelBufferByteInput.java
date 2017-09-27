/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)ChannelBufferByteInput.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月23日
 */
package org.demo.netty.ch12;

import java.io.IOException;

import org.jboss.marshalling.ByteInput;

import io.netty.buffer.ByteBuf;

/** 
 * 
 * <p>
 * <a href="ChannelBufferByteInput.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class ChannelBufferByteInput implements ByteInput{
    
    private final ByteBuf buffer;
    
    public ChannelBufferByteInput(ByteBuf buffer) {
        this.buffer = buffer;
    }

    @Override
    public void close() throws IOException {
        
    }

    @Override
    public int read() throws IOException {
        if (buffer.isReadable()) {
            return buffer.readByte() & 0xff;
        }
        return -1;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int readable = available();
        if (readable == 0) {
            return -1;
        }
        len = Math.min(readable, len);
        buffer.readBytes(b, off, len);
        return len;
    }

    @Override
    public int available() throws IOException {
        return buffer.readableBytes();
    }

    @Override
    public long skip(long bytes) throws IOException {
        int readable = this.buffer.readableBytes();
        if (readable < bytes) {
            bytes = readable;
        }
        buffer.readerIndex((int)(buffer.readerIndex() + bytes));    //跳过几个字节读后面的
        return bytes;
    }
    

}
