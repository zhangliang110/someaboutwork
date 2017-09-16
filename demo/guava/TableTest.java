/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)TableTest.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月2日
 */
package org.demo.guava;

import com.google.common.collect.HashBasedTable;

/** 
 * 创建映射的映射，
 * row ，column ，value 三元组
 * 
 * <p>
 * <a href="TableTest.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class TableTest {
    public static void main(String[] args) {
        HashBasedTable<String, String, String> table = HashBasedTable.create();
        table.put("FK", "zl", "121");
        table.put("FK", "xm", "122");
        table.put("FK", "xh", "123");
        table.put("IK", "eglish", "121");
        System.out.println(table.row("FK").keySet());
    }
}
