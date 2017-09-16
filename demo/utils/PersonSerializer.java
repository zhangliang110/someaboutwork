/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)PersonSerializer.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月4日
 */
package org.demo.utils;

import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.gradle.Person;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;

/** 
 * 功能描述
 * 
 * <p>
 * <a href="PersonSerializer.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class PersonSerializer implements Encoder<Person> {

    public PersonSerializer() {
        // constructor
    }

    @SuppressWarnings("PMD.UnusedFormalParameter")
    public PersonSerializer(VerifiableProperties properties) {
        // constructor
    }
    
    /* (non-Javadoc)
     * @see kafka.serializer.Encoder#toBytes(java.lang.Object)
     */
    @Override
    public byte[] toBytes(Person person) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (person != null) {
                return  mapper.writeValueAsBytes(person);
            }
        } catch (JsonProcessingException e) {
            System.out.println("序列化UserNewsAction失败. userNewsAction = {}");
        }
        return null;
    }
}
