/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)MarshallingDecoder.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月25日
 */
package org.demo.netty.ch12;

import org.jboss.marshalling.Unmarshaller;

import io.netty.buffer.ByteBuf;

/** 
 * 
 * <p>
 * <a href="MarshallingDecoder.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class MarshallingDecoder {
    private final Unmarshaller unmarshaller;
    
    public MarshallingDecoder() throws Exception {
        unmarshaller = MarshallingCodecFactory.buildUnMarshalling();
    }
    
    protected Object decode(ByteBuf in) throws Exception {
        int objectSize = in.readInt();
        ByteBuf buf = in.slice(in.readerIndex(), objectSize);
        ChannelBufferByteInput input = new ChannelBufferByteInput(buf);
        try {
            unmarshaller.start(input);
            Object obj = unmarshaller.readObject();
            unmarshaller.finish();
            in.readerIndex(in.readerIndex() + objectSize);
            return obj;
        } finally {
            unmarshaller.close();
        }
        
    }
}
