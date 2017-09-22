/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)TestUserInfo2.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月21日
 */
package org.demo.netty.ch6;

import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

import org.apache.commons.io.output.ByteArrayOutputStream;

/** 
 * 这次比较java序列化的性能与普通二进制编码的性能比较
 * <p>
 * <a href="TestUserInfo2.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class TestUserInfo2 {
    public static void main(String[] args) {
        UserInfo user = new UserInfo().buildUserId(121).buildUserName("zhangliang");
        int loop = 100_0000;
        System.out.println(loop);
        
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            try {
                bos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(bos);
                oos.writeObject(user);
                oos.flush();
                oos.close();
                byte[] b = bos.toByteArray();
                bos.close();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("the jdk serializable cost time is " + (endTime - startTime));
        
        System.out.println("----------------------------------------");
        
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            byte[] b = user.codeC(buffer);
        }
        endTime = System.currentTimeMillis();
        System.out.println("the byte array serializable cost time is " + (endTime - startTime ));
    }
}
