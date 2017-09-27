/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)ChannelBufferByteOutput.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月23日
 */
package org.demo.netty.ch12;

import java.io.IOException;

import org.jboss.marshalling.ByteOutput;

import io.netty.buffer.ByteBuf;

/** 
 * 
 * 
 * <p>
 * <a href="ChannelBufferByteOutput.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class ChannelBufferByteOutput implements ByteOutput{

    private final ByteBuf buffer;
    
    public ChannelBufferByteOutput(ByteBuf buf) {
        this.buffer = buf;
    }
    
    @Override
    public void close() throws IOException {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see java.io.Flushable#flush()
     */
    @Override
    public void flush() throws IOException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void write(int b) throws IOException {
        this.buffer.writeByte(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        this.buffer.writeBytes(b);
        
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        buffer.writeBytes(b, off, len);
        
    }

    ByteBuf getBuffer() {
        return buffer;
    }

}
