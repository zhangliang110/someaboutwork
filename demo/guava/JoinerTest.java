/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)JoinerTest.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月2日
 */
package org.demo.guava;

import java.util.Arrays;
import java.util.regex.Pattern;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

/** 
 * 字符串拼接工具类
 * <p>
 * <a href="JoinerTest.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class JoinerTest {
    public static void main(String[] args) {
        String str = Joiner.on("-").useForNull("xx").join(Arrays.asList("222", null, "121").iterator());
        System.out.println(str);
        JoinerTest a = new JoinerTest();
        a.testSplitter();
    }
    
    public void testSplitter() {
        Splitter sp = Splitter.on(Pattern.compile("^(\\d)(!\\1)"));
        Iterable<String> split = sp.split("11232233");
        System.out.println(split);
    }
}
