/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)OptionalTest.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月2日
 */
package org.demo.guava;

import com.google.common.base.Optional;

/** 
 * guava学习 optional 辅助类
 * 和 jdk 提供的optinal无大差异
 * <p>
 * <a href="OptionalTest.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class OptionalTest {
    public static void main(String[] args) {
        OptionalTest test = new OptionalTest();
        Optional<Object> op1 = Optional.fromNullable(null);
        System.out.println(op1.isPresent());
        java.util.Optional<Object> op2 = java.util.Optional.ofNullable(2);
        System.out.println(op2.filter((x)-> {
            return x.equals(2);
        }).get());
    }
    
}
