/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)MarshallingEncoder.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月23日
 */
package org.demo.netty.ch12;

import java.io.IOException;

import org.jboss.marshalling.Marshaller;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;

/** 
 * 功能描述
 * 
 * <p>
 * <a href="MarshallingEncoder.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
@Sharable
public class MarshallingEncoder {
    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
    
    Marshaller marshaller;
    
    public MarshallingEncoder() throws IOException{
        marshaller = MarshallingCodecFactory.buildMarshalling();
    }
    
    protected void encode(Object msg, ByteBuf out) throws IOException {
        try {
            int lengthPos = out.writerIndex();
            out.writeBytes(LENGTH_PLACEHOLDER);
            ChannelBufferByteOutput output = new ChannelBufferByteOutput(out);
            marshaller.start(output);
            marshaller.writeObject(msg);
            marshaller.finish();
            out.setInt(lengthPos, out.writerIndex() - lengthPos - 4);
        } finally {
            marshaller.close();
        }
    }
}
