/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)DynamicProgrammingSearch.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2018年11月5日
 */
package org.demo.sort.search.deepsearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** 
 * 利用动态规划，查找二维数组中的最大值
 * 
 * <p>
 * <a href="DynamicProgrammingSearch.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class DynamicProgrammingSearch {
    //定义best[i][j]
    private int[][] best = null;
    private int row;
    private int col;
    
    //定义方向常量
    private static final int RIGHT = 1;
    private static final int DOWN = 2;
    
    //存储最好的路径
    private List<Integer> bestPath = null;
    
    //计算最优值
    private void calcDp(int[][] matrix) {
        //初始化
        row = matrix.length;
        col = matrix[0].length;
        best = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                //边界
                if (i == 0 && j == 0) {
                    best[i][j] = matrix[i][j];
                } else if (i == 0) {
                    best[i][j] = best[i][j - 1] + matrix[i][j];
                } else if (j == 0) {
                    best[i][j] = best[i - 1][j] + matrix[i][j];
                } else {
                    //状态转移
                    best[i][j] = Math.max(best[i - 1][j], best[i][j - 1]) + matrix[i][j];
                }
            }
        }
    }
    
    //计算最优路径
    private void calcBestPath(int[][] matrix) {
        bestPath = new ArrayList<>();
        //总共走 row + col - 2 步
        int curX = row - 1;
        int curY = col - 1;
        for (int i = 0; i < row + col - 2; i++) {
            if (curX == 0) {
                curY--;
                bestPath.add(RIGHT);
            } else if (curY == 0) {
                curX--;
                bestPath.add(DOWN);
            } else {
                if (best[curX - 1][curY] > best[curX][curY - 1]) {
                    curX--;
                    bestPath.add(DOWN);
                } else {
                    curY--;
                    bestPath.add(RIGHT);
                }
            }
        }
    }
    
    //打印最佳路径
    private void printBestPath() {
        Collections.reverse(bestPath);
        bestPath.forEach(dir -> System.out.println(dir == RIGHT ? "右" : "下"));
    }
    
    //查找最优解
    public int search(int[][] matrix) {
        calcDp(matrix);
        calcBestPath(matrix);
        return best[matrix.length - 1][matrix[0].length - 1];
    }
    public static void main(String[] args) {

        int[][] matrix1 = {
            {300, 500, 560, 400, 160},
            {1000, 100, 200, 340, 690},
            {600, 500, 500, 460, 320},
            {300, 400, 250, 210, 760}
        };

        int[][] matrix2 = {
            {300, 500, 2560, 400},
            {1000, 100, 200, 340},
            {600, 500, 500, 460},
            {300, 400, 250, 210},
            {860, 690, 320, 760}
        };

        DynamicProgrammingSearch dp = new DynamicProgrammingSearch();

        System.out.println(dp.search(matrix1));
        dp.printBestPath();

        System.out.println(dp.search(matrix2));
        dp.printBestPath();
    
    }
}
