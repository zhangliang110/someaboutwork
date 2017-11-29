/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)NumSpliterator2.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年11月29日
 */
package org.demo.concurrent.spliterator;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/** 
 * 功能描述
 * 
 * <p>
 * <a href="NumSpliterator2.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class NumSpliterator2 implements Spliterator<Character> {
    private char[] str;
    private int currentChar = 0;
    private int end = Integer.MAX_VALUE;
    private boolean canSplit = true;
    
    public NumSpliterator2(int currentChar, int end, char[] str, boolean canSplit) {
        this.str = str;
        this.currentChar = currentChar;
        this.end = end;
        this.canSplit = canSplit;
    }
    
    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        action.accept(str[currentChar++]);
        return currentChar < end;
    }

    @Override
    public Spliterator<Character> trySplit() {
        int i = currentChar;
        int currentCharOld = currentChar;
        for (; canSplit && currentChar < end; ++i) {
            if(!Character.isDigit(str[i])) {
                int splitBeforeEnd = end;
                canSplit = false;
                if (i + 1 < splitBeforeEnd) {
                    currentChar = i + 1;
                    return new NumSpliterator2(currentCharOld, i, str, true);
                } else {
                    return null;
                }
            }
        }
        canSplit = false;
        return null;
    }

    @Override
    public long estimateSize() {
        return end - currentChar;
    }

    @Override
    public int characteristics() {
        return ORDERED | SIZED | SUBSIZED | NONNULL | IMMUTABLE;
    }
    
    public static void main(String[] args) {
        String arr = "12%3 21sdas s34d dfsdz45   R3 jo34 sjkf8 3$1P 213ikflsd fdg55 kfd";

        Spliterator<Character> spliterator = new NumSpliterator2(0,arr.length(),arr.toCharArray(),true);
        // 传入true表示是并行流
        Stream<Character> parallelStream = StreamSupport.stream(spliterator, true);
        System.out.println("parallel total: " + countNum(parallelStream));
    }

    private static int countNum(Stream<Character> stream){
        NumCounter numCounter = stream.reduce(new NumCounter(0, 0, false), NumCounter::accumulate, NumCounter::combine);
        return numCounter.getSum();
    }
}
