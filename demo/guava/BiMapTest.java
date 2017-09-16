/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)BiMapTest.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月2日
 */
package org.demo.guava;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/** 
 * 保证不存在重复的  value 和 key
 * <p>
 * <a href="BiMapTest.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class BiMapTest {
    public static void main(String[] args) {
        BiMap<Integer, String> biMap = HashBiMap.create();
        biMap.put(Integer.valueOf(1), "zl");
        biMap.forcePut(Integer.valueOf(2), "zl");
        biMap.put(Integer.valueOf(1), "wz");
        biMap.put(Integer.valueOf(1), "wz");
        biMap.put(Integer.valueOf(1), "wz");
        System.out.println(biMap.inverse());
    }
}
