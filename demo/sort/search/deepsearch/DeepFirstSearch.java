/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)DeepFirstSearch.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2018年11月2日
 */
package org.demo.sort.search.deepsearch;

import java.util.ArrayList;
import java.util.List;

/** 
 * 深度优先搜索，查找二维数组中的最优解
 * 
 * <p>
 * <a href="DeepFirstSearch.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class DeepFirstSearch {
    private int[][] best = null;    //存储最优的
    private int row = 0;    
    private int col = 0;    //行与列
    
    //定义方向常量
    private int RIGHT = 1;
    private int DOWN = 2;   //二维数组中查找的方向
    private List<Integer> curPath = null;
    private Integer[] bestPath = null;
    
    //当前搜索点
    private int curRow = 0;
    private int curCol = 0;
    
    private int curVal = 0;     //当前累积的查询值
    private int maxVal = 0;      //存储最大的值
    
    
    //往某个方向查询
    private void goDir(int dir, int[][] matrix) {
        if (dir == DOWN) {
            curRow++;
            curVal += matrix[curRow][curCol];
        }
        if (dir == RIGHT) {
            curCol++;
            curVal += matrix[curRow][curCol];
        }
        curPath.add(dir);
    }
    
    //往某个方向回退
    private void goBackDir(int dir, int[][] matrix) {
        if (dir == DOWN) {
            curVal -= matrix[curRow][curCol];
            curRow--;
            
        }
        if (dir == RIGHT) {
            curVal -= matrix[curRow][curCol];
            curCol--;
        }
        curPath.remove(curPath.size() - 1);
    }
    
    //深度搜索
    private void search(int dir, int[][] matrix) {
        //往某个方向进行搜索
        goDir(dir, matrix);
        //如果达到了终点
        if (curRow == row - 1 && curCol == col - 1) {
            if(curVal >= maxVal) {
                //记录最大值和最大路径
                maxVal = curVal;
                bestPath = curPath.toArray(new Integer[curPath.size()]);
            }
        } else if(curVal < best[curRow][curCol]) {
            //小于之前的maxVal，则肯定不是最佳路径，回退
        } else {
            //更新best(i,j)
            best[curRow][curCol] = curVal;
            //往下一个方向进行搜索
            if (curRow < row - 1) {
                search(DOWN, matrix);
            }
            if (curCol < col - 1) {
                search(RIGHT, matrix);
            }
        }
        //往某方向回退
        goBackDir(dir, matrix);
    }
    
    //获取最大的val
    public int getMaxVal(int[][] matrix) {
        //初始化
        curVal = matrix[0][0];
        row = matrix.length;
        col = matrix[0].length;
        best = new int[row][col];
        curPath = new ArrayList<>();
        //开始搜索
        if (row > 1) {
            search(DOWN, matrix);
        }
        if (col > 1) {
            search(RIGHT, matrix);
        }
        return maxVal;
    }
    
    public void printMaxPath() {
        for (int i = 0; i < bestPath.length; i++) {
            int dir = bestPath[i];
            if (dir == DOWN) {
                System.out.println("下");
            }
            if (dir == RIGHT) {
                System.out.println("右");
            }
        }
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

        DeepFirstSearch dp = new DeepFirstSearch();

        System.out.println(dp.getMaxVal(matrix1));
        dp.printMaxPath();

        System.out.println(dp.getMaxVal(matrix2));
        dp.printMaxPath();
    }
    
}
