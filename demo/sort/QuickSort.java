/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)QuickSort.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2018年5月30日
 */
package org.demo.sort;

import org.apache.commons.lang.ArrayUtils;

/** 
 * 快速排序
 * 
 * <p>
 * <a href="QuickSort.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class QuickSort {
    public static void sort(int[] data, int l , int h) {
        if (l >= h) {
            return ;
        }
        int low = l ;
        int high = h ;
        int povit = data[l];
        while (low < high) {
            while(low < high && data[high] >= povit) {
                high--;
            }
            while(low < high && data[low] <= povit) {
                low++;
            }
            if (low < high) {
                int temp = data[low];
                data[low] = data[high];
                data[high] = temp;
            }
        }
        //将基准数归位
        data[l] = data[low];
        data[low] = povit;
        
        sort(data, l, low - 1);
        sort(data, low + 1, h);
    }
    public static void main(String[] args) {
        int[] arr = new int[] { 1, 41, 4, 41, 32, 31, 221, 19, 1 };
        int low = 0;
        int high = arr.length - 1;
        sort2(arr, low, high);
        System.out.println(ArrayUtils.toString(arr));
    }
    
    public static void sort2(int[] data, int left , int right) {
        if (left >= right) {
            return ;
        }
        int low = left;
        int high = right;
        int key = data[low];
        while (low < high) {
            while(low < high && data[high] >= key) {
                high -- ;
            }
            data[low] = data[high];
            while(low < high && data[low] <= key) {
                low++;
            }
            data[high] = data[low];
        }
        data[low] = key;
        sort2(data, left, low - 1);
        sort2(data, low + 1, right);
        
        
    }
}
