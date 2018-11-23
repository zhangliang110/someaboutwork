/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)HeapSort.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2018年6月11日
 */
package org.demo.sort;

import org.apache.commons.lang.ArrayUtils;

/** 
 * 堆排序
 * 
 * <p>
 * <a href="HeapSort.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class HeapSort {
    private int[] arr;
    public HeapSort(int[] arr) {
        this.arr = arr;
    }
    
    public void sort() {
        int len = arr.length - 1;
        int beginIndx = (len - 1) >> 1;
        for (int i = beginIndx; i >= 0; i--) {
            maxHeapify(i, len);
        }
        
        for (int i = len; i > 0; i--) {
            swap(0, i);
            maxHeapify(0, i - 1);
        }
    }
    
    /**
     * 调整索引为index处的数据，使其符合堆的特性
     * @param index     需要堆化处理的数据的索引
     * @param len       未排序的堆（数组）的长度
    */
    public void maxHeapify(int index, int len) {
        int li = (index << 1) + 1;  //等于index * 2 + 1
        int ri =  li + 1;
        int cMax = li;
        if (li > len) return;
        if (ri <= len && arr[ri] > arr[li]) {
            cMax = ri;
        }
        if (arr[cMax] > arr[index]) {
            swap(cMax, index);
            maxHeapify(cMax, len);
        }
    }
    
    
    private void swap(int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    public static void main(String[] args) {
        int[] arr = new int[] {1,2,3,4,5,1,2,31,23,123,4232,3,13123,123};
        new HeapSort(arr).sort();
        System.out.println(ArrayUtils.toString(arr));
    }
    
}
