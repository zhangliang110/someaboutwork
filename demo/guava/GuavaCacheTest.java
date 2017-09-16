/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)GuavaCacheTest.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月2日
 */
package org.demo.guava;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;

/** 
 * 功能描述
 * 
 * <p>
 * <a href="GuavaCacheTest.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class GuavaCacheTest {
    public static void main(String[] args) throws Exception {
        LoadingCache<String, Student> builder = CacheBuilder.newBuilder().maximumSize(100).expireAfterWrite(10, TimeUnit.MINUTES).build(new CacheLoader<String, Student>() {

            @Override
            public Student load(String key) throws Exception {
                return getStudentByName(key);
            }
        });
        System.out.println(builder.get("zl"));
        System.out.println(builder.get("zl"));
        ConcurrentMap<String, Student> map = builder.asMap();
    }
    
    
    private static Student getStudentByName(String name) {
        Student s1 = new Student("zl", 12);
        Student s2 = new Student("wk", 132);
        Student s3 = new Student("zz", 112);
        Student s4 = new Student("xk", 32);
        
        Map<String, Student> data = Maps.newHashMap();
        data.put(s1.getName(), s1);
        data.put(s2.getName(), s2);
        data.put(s3.getName(), s3);
        data.put(s4.getName(), s4);
        System.out.println("direct into the data");
        return data.get(name);
    }
    
    public static class Student {
        private String name;
        private Integer age;
        
        public Student(String name, Integer age) {
            this.name = name;
            this.age = age;
        }
        /**
         * @return the name
         */
        public String getName() {
            return name;
        }
        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }
        /**
         * @return the age
         */
        public Integer getAge() {
            return age;
        }
        /**
         * @param age the age to set
         */
        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
