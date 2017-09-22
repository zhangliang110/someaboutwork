/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)TimeHandlerExecutePool.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月18日
 */
package org.demo.netty.ch1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/** 
 * 功能描述
 * 
 * <p>
 * <a href="TimeHandlerExecutePool.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class TimeHandlerExecutePool {
    private ExecutorService executor;
    
    public TimeHandlerExecutePool(int maxPoolSize, int queueSize) {
        executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize, 120L, TimeUnit.SECONDS,
                                            new ArrayBlockingQueue<Runnable>(queueSize));
    }
    
    public void execute(Runnable task) {
        executor.submit(task);
    }
}
