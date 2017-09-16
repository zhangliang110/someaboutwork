/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)OrderingTest.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月2日
 */
package org.demo.guava;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Ordering;


/** 
 * guava 排序工具类
 * <p>
 * <a href="OrderingTest.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class OrderingTest {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3,11,423,12,32,4,5,32123,423,76,56);
        List<Integer> sorted = Ordering.natural().sortedCopy(list);
        System.out.println();
    }
}
