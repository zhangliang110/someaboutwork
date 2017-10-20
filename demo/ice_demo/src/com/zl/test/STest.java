/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)STest.java
 * Encoding UTF-8
 * Author: gongqiao
 * Version: 3.0
 * Date: 2017年8月25日
 */
package com.zl.test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/** 
 * 功能描述
 * 
 * <p>
 * <a href="STest.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class STest {
    public static void main(String[] args) {/*
        List<Integer> a = Arrays.asList(1,23,4,5,6,23,2,1);
        Set<Integer> noRepeat = a.stream().map(Integer::valueOf).collect(Collectors.toSet());
        System.out.println(noRepeat);
    */
        StringBuilder sb = new StringBuilder();
        sb.delete(0, 2);
        System.out.println(sb);
    }
}
