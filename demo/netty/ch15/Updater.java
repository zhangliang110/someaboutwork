/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)Updater.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年10月10日
 */
package org.demo.netty.ch15;

/** 
 * 
 * <p>
 * <a href="Updater.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class Updater {
    
    public volatile int count;

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }
    
}
