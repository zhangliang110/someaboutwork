/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)Header.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月23日
 */
package org.demo.netty.ch12.struct;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/** 
 * 消息头的定义
 * 
 * <p>
 * <a href="Header.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public final class Header {
    private int crcCode = 0xabef0101;
    private int length; //消息长度
    private long sessionId; //会话id
    private byte type;  //消息类型
    private byte priority;  //消息优先级
    private Map<String,Object> attachment = new HashMap<>();    //附件
    
    /**
     * @return the crcCode
     */
    public int getCrcCode() {
        return crcCode;
    }
    /**
     * @param crcCode the crcCode to set
     */
    public void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }
    /**
     * @return the length
     */
    public int getLength() {
        return length;
    }
    /**
     * @param length the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }
    /**
     * @return the sessionId
     */
    public long getSessionId() {
        return sessionId;
    }
    /**
     * @param sessionId the sessionId to set
     */
    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }
    /**
     * @return the type
     */
    public byte getType() {
        return type;
    }
    /**
     * @param type the type to set
     */
    public void setType(byte type) {
        this.type = type;
    }
    /**
     * @return the priority
     */
    public byte getPriority() {
        return priority;
    }
    /**
     * @param priority the priority to set
     */
    public void setPriority(byte priority) {
        this.priority = priority;
    }
    /**
     * @return the attachment
     */
    public Map<String, Object> getAttachment() {
        return attachment;
    }
    /**
     * @param attachment the attachment to set
     */
    public void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }
    
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
