/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)Bubble.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2018年5月26日
 */
package org.demo.sort;

import org.apache.commons.lang.ArrayUtils;

/** 
 * 冒泡排序
 * 
 * <p>
 * <a href="Bubble.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class Bubble {
    public static void sort(int[] arr) {
        int length = arr.length;
        boolean sorted = false;
        for (int i = 0; i < length && !sorted; i++) {
            sorted = true;
            for (int j = 0; j < length - i - 1; j++) {
               if (arr[j + 1] < arr[j]) {
                   sorted = false;
                   int temp = arr[j + 1];
                   arr[j + 1] = arr[j];
                   arr[j] = temp;
               }
            }
        }
    }
    
    public static void main(String[] args) {
        int[] arr = new int[] {1,1,1,1,1,1,1,1,1,1,1,1,1,1};
        sort(arr);
        System.out.println(ArrayUtils.toString(arr));
    }
}
