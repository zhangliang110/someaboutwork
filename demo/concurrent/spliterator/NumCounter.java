/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)NumCounter.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年11月29日
 */
package org.demo.concurrent.spliterator;

/** 
 * 使用并行流统计 asd1231sda123sad123asd12 形如此种字符串种的数字之和
 * 
 * <p>
 * <a href="NumCounter.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class NumCounter {
    private int num;
    private int sum;
    
    //是否当前是数字
    private boolean isWholeNum;
    
    //指定创建容器的策略
    public NumCounter(int num, int sum , boolean isWholeNum) {
        this.sum = sum;
        this.num = num;
        this.isWholeNum = isWholeNum;
    }
    
    //指定如何加入容器的策略
    public NumCounter accumulate(Character c) {
        System.out.println(Thread.currentThread().getName());
        if (Character.isDigit(c)) {
            return new NumCounter(Integer.parseInt("" + c), sum + Integer.parseInt("" + c), isWholeNum);
        } else {
            return new NumCounter(0, sum, isWholeNum);
        }
        /*if (Character.isDigit(c)) {
            return isWholeNum ? new NumCounter(Integer.parseInt(c + ""), sum, false) : new NumCounter(Integer.parseInt("" + num + c ), sum, false);  
        } else {
            return new NumCounter(0, sum + num, true);
        }*/
    }
    
    //指定当出现多个NumCounter之后的合并策略
    public NumCounter combine(NumCounter numCounter) {
        return new NumCounter(0, this.getSum() + numCounter.getSum(), numCounter.isWholeNum);
    }
    
    
    /**
     * @return the num
     */
    public int getNum() {
        return num;
    }

    /**
     * @param num the num to set
     */
    public void setNum(int num) {
        this.num = num;
    }

    /**
     * @return the sum
     */
    public int getSum() {
        return sum;
    }

    /**
     * @param sum the sum to set
     */
    public void setSum(int sum) {
        this.sum = sum;
    }

    /**
     * @return the isWholeNum
     */
    public boolean isWholeNum() {
        return isWholeNum;
    }

    /**
     * @param isWholeNum the isWholeNum to set
     */
    public void setWholeNum(boolean isWholeNum) {
        this.isWholeNum = isWholeNum;
    }
}
