/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)UserInfo.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月21日
 */
package org.demo.netty.ch6;

import java.io.Serializable;
import java.nio.ByteBuffer;

import org.msgpack.annotation.Message;

/** 
 * 这个例子说明 java序列化后的码流比较大
 * <p>
 * <a href="UserInfo.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
@Message
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;
 
    private int userId;
    
    private String userName;

    public UserInfo buildUserName(String name) {
        this.userName = name;
        return this;
    }
    
    public UserInfo buildUserId(int id) {
        this.userId = id;
        return this;
    }
    
    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public byte[] codeC() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte[] value = this.userName.getBytes();
        buffer.putInt(value.length);
        buffer.put(value);
        buffer.putInt(this.userId);
        buffer.flip();
        value = null;
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);
        return result;
    }
    
    public byte[] codeC(ByteBuffer buffer) {
        buffer.clear();
        byte[] value = this.userName.getBytes();
        buffer.putInt(value.length);
        buffer.put(value);
        buffer.putInt(this.userId);
        buffer.flip();
        value = null;
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);
        return result;
        
    }
}
