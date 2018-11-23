/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)ShellSort.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2018年5月29日
 */
package org.demo.sort;

import org.apache.commons.lang.ArrayUtils;

/** 
 * 希尔排序 最好的时间复杂度为（O nlogn^2）
 * 
 * <p>
 * <a href="ShellSort.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class ShellSort {
    
    public static void sort(int[] data) {
        int length = data.length;
        int h = 1;
        while (h < length / 3) {
            h = 3 * h + 1;
        }
        while (h > 0) {
            for (int i = h; i < length; i++) {
                for (int j = i; j >= h; j -= h) {
                    if (data[j] < data[j - h]) {
                        int temp = data[j];
                        data[j] = data[j -h];
                        data[j - h] = temp;
                    }
                }
            }
            h = h / 3;
        }
    }
    
    public static void main(String[] args) {
        int[] arr = new int[] {1,2,3,4,5,1,2,31,23,123,4232,3,13123,123,12,44,1,444444,111122,33333,4444,2333,4444,555};
        sort(arr);
        System.out.println(ArrayUtils.toString(arr));
    }
}
