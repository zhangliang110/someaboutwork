/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)Test.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年11月29日
 */
package org.demo.concurrent.spliterator;

import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/** 
 * 功能描述
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
        String arr = "111a13";
        System.out.println(arr);

        Spliterator<Character> spliterator = new NumCounterSpliterator3(0, arr.length() - 1, arr.toCharArray(), true);
        // 传入true表示是并行流
        Stream<Character> parallelStream = StreamSupport.stream(spliterator, true);
        System.out.println("parallel total: " + countNum(parallelStream));
        }

    private static int countNum(Stream<Character> stream){
        NumCounter numCounter = stream.reduce(new NumCounter(0, 0, false), NumCounter::accumulate, NumCounter::combine);
        return numCounter.getSum();
    }
}
