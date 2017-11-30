/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)NumSpliterator.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年11月29日
 */
package org.demo.concurrent.spliterator;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/** 
 * 功能描述
 * 
 * <p>
 * <a href="NumSpliterator.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class NumSpliterator implements Spliterator<Character> {
    private String str;
    private int currentChar = 0;
    private boolean canSplit = true;
    
    public NumSpliterator (int currentChar, String str, boolean canSplit) {
        this.str = str;
        this.currentChar = currentChar;
        this.canSplit = canSplit;
    }
    
    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public int getCurrentChar() {
        return currentChar;
    }

    public void setCurrentChar(int currentChar) {
        this.currentChar = currentChar;
    }

    public boolean isCanSplit() {
        return canSplit;
    }

    public void setCanSplit(boolean canSplit) {
        this.canSplit = canSplit;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Character> action) {
        if ("".equals(str)) {
            return false;
        } 
        action.accept(str.charAt(currentChar++));
        return currentChar < str.length();
    }

    @Override
    public Spliterator<Character> trySplit() {
        for (int i = currentChar;canSplit && i < str.length() ; ++i) {
            //第一个不是数字的pos，进行分割
            if (!Character.isDigit(str.charAt(i))) {
                String str1 = str;
                this.str = str1.substring(currentChar, i);
                canSplit = false;
                if (i + 1 < str1.length()) {
                    return new NumSpliterator(0, str1.substring(i + 1, str1.length()), true);
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
        return str.length() - currentChar;
    }

    @Override
    public int characteristics() {
        return ORDERED | SIZED | SUBSIZED | NONNULL | IMMUTABLE;
    }

    @Override
    public void forEachRemaining(Consumer<? super Character> action) {
        do {
        } while (tryAdvance(action));
    }
    
    public static void main(String[] args) {
        String arr = "12%3 21sdas s34d dfsdz45   R3 jo34 sjkf8 3$1P 213ikflsd fdg55 kfd";
        Stream<Character> stream = IntStream.range(0, arr.length()).mapToObj(arr::charAt);
        System.out.println("ordered total: " + countNum(stream));

        Spliterator<Character> spliterator = new NumSpliterator(0,arr,true);
        // 传入true表示是并行流
        Stream<Character> parallelStream = StreamSupport.stream(spliterator, true);
        parallelStream.sorted().forEach(System.out::println);
        System.out.println("parallel total: " + countNum(parallelStream));
    }
    
    private static int countNum(Stream<Character> stream){
        NumCounter numCounter = stream.reduce(new NumCounter(0, 0, false), NumCounter::accumulate, NumCounter::combine);
        return numCounter.getSum();
    }
}
