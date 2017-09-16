/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)MultiSetTest.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月2日
 */
package org.demo.guava;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Maps;

/** 
 * multiSetTest 具体实现未  new HashMap<String, Count> 
 * multiSet 添加重复的元素可以统计其添加的数量
 * <p>
 * <a href="MultiSetTest.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class MultiSetTest {
    private static List<String> list = Arrays.asList("a", "b", "c", "a", "c", "e", "f", "e", "a", "e", "e", "d");
    
    public static void main(String[] args) {
        HashMultiset<String> multiset  = HashMultiset.create();
        multiset.add("a");
        multiset.add("a");
        multiset.add("a");
        multiset.add("a");
        multiset.add("b");
        multiset.add("c");
        multiset.add("d");
        multiset.add("a");
        multiset.add("b");
        multiset.add("c");
        multiset.add("b");
        multiset.add("b");
        multiset.add("b");
        System.out.println(multiset);
        String a = multiset.stream().sorted((x1,x2) -> {
            return multiset.count(x2) - multiset.count(x1);
        }).findFirst().get();
        System.out.println(a);
        
        HashMap<String, Integer> map = Maps.newHashMap();
        
        list.forEach((x) -> {
            if (map.containsKey(x)) {
                map.put(x, map.get(x).intValue() + 1);
            } else {
                map.put(x, 1);
            }
        });
        Entry<String, Integer> entry = map.entrySet().stream().sorted((x1,x2) -> {
            return x2.getValue() - x1.getValue();
        }).findFirst().get();
        System.out.println(map);
 
        System.out.println(map);
        System.out.println(entry);
    }
    
}
