/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)Test.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年10月13日
 */
package org.demo.netty.ch18;

/** 
 * 
 * 执行次数判断：技巧
 * 当执行64次时 退出循环      2^n次方都行
 * 
 * <p>
 * <a href="Test.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class Test {
    public static void main(String[] args) {
        int i = 0;
        for(;;) {
            System.out.println("执行次数" + (i + 1));
            i++;
            if((i & 0x3f) == 0) break;
        }
        
    }
}
