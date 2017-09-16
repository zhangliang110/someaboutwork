/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)PredicationsTest.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月2日
 */
package org.demo.guava;

import com.google.common.base.Preconditions;

/** 
 * Preconditions 一个检验参数的工具类
 * 
 * <p>
 * <a href="PredicationsTest.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class PreconditionsTest {
    public static void main(String[] args) {
        Preconditions.checkArgument(1==2, "false");
    }
}
