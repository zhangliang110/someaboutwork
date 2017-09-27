/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)NettyMessage.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月23日
 */
package org.demo.netty.ch12.struct;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/** 
 * 
 * <p>
 * <a href="NettyMessage.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public final class NettyMessage {
    private Header header;  //消息头
    private Object body;    //消息体

    /**
     * @return the header
     */
    public Header getHeader() {
        return header;
    }
    /**
     * @param header the header to set
     */
    public void setHeader(Header header) {
        this.header = header;
    }
    /**
     * @return the body
     */
    public Object getBody() {
        return body;
    }
    /**
     * @param body the body to set
     */
    public void setBody(Object body) {
        this.body = body;
    }
    
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
