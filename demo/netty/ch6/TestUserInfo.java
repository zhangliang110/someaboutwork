/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)TestUserInfo.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月21日
 */
package org.demo.netty.ch6;

import java.io.IOException;
import java.io.ObjectOutputStream;

import org.apache.commons.io.output.ByteArrayOutputStream;

/** 
 * 测试java序列化与普通的二进制编码的码流大小比较
 * <p>
 * <a href="TestUserInfo.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class TestUserInfo {
    public static void main(String[] args) throws IOException {
        UserInfo user = new UserInfo().buildUserId(12).buildUserName("windzhang");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(user);
        oos.flush();
        oos.close();
        byte[] bytes = bos.toByteArray();
        System.out.println("java serializable size " + bytes.length);
        System.out.println("the byte array serializable length is " + user.codeC().length);
    }
}
