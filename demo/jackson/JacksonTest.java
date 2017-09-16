/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)JacksonTest.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年8月31日
 */
package org.demo.jackson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

/** 
 * 功能描述
 * 
 * <p>
 * <a href="JacksonTest.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class JacksonTest {
    public static void main(String[] args) {
        JsonFactory factory = new JsonFactory();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            JsonGenerator gen = factory.createGenerator(out, JsonEncoding.UTF8);
            gen.writeStartObject();
            gen.writeObjectField("a", 1);
            gen.writeEndObject();
            gen.flush();
            System.out.println(new String(out.toByteArray(), Charset.forName("UTF-8")));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
