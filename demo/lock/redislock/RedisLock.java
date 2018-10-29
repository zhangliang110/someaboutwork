/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)RedisLock.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2018年8月29日
 */
package com.chinatime.datacenter.test.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinatime.common.redis.JedisFactory;
import com.google.common.collect.Lists;

import redis.clients.jedis.JedisCluster;

/** 
 * redis分布式锁
 * 
 * <p>
 * <a href="RedisLock.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class RedisLock {
    private static Logger LOGGER = LoggerFactory.getLogger(RedisLock.class);
    
    private static String LOCK_SUCCESS = "OK";
    
    private static String UN_LOCK_MSG = "1";
    
    private static String SET_IF_NOT_EXIST = "NX";
    
    private static String SET_WITH_EXPIRE_TIME = "PX";
    
    private String lockPrefix;
    
    private long sleepTime;
    
    private JedisCluster jedisClient;
    
    private static final Long DEFALUT_EXPIRE_TIME = 20000L;
    
    static BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(100);
    
    private RedisLock(Builder builder) {
        this.jedisClient = builder.jedisClient;
        this.lockPrefix = builder.lockPrefix;
        this.sleepTime = builder.blockTime;
    }
    
    /**
     * blocking lock 获取不到锁会一直阻塞
     * @param key
     * @param request
     * @throws InterruptedException
    */
    public void lock(String key, String request) throws InterruptedException {
        String result;
        for(;;) {
            result = jedisClient.set(lockPrefix + key, request, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 10 * sleepTime);
            if (LOCK_SUCCESS.equals(result)) {
                break;
            }
            Thread.sleep(sleepTime);
        }
    }
    
    /**
     * 获取不到锁会在一定时间内进行重试
     * @param key
     * @param request
     * @param customTime
     * @return
     * @throws InterruptedException
    */
    public boolean lock(String key, String request, long customTime) throws InterruptedException {
        String result;
        while(customTime > 0) {
            result = jedisClient.set(lockPrefix + key, request, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 10 * sleepTime);
            if (LOCK_SUCCESS.equals(result)) {
                return true;
            }
            customTime -= sleepTime;
            Thread.sleep(sleepTime);
        }
        return false;
    }
    
    /**
     * 直接获取锁，不阻塞，不重试
     * @param key
     * @param request
     * @return
    */
    public boolean tryLock(String key, String request) {
        String result;
        result = jedisClient.set(lockPrefix + key, request, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 10 * sleepTime);
        if (LOCK_SUCCESS.equals(result)) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 解锁
     * @param key
     * @param request
     * @return
    */
    public boolean unlock(String key, String request) {
        Object result;
        result = jedisClient.eval("if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end", Lists.newArrayList(lockPrefix + key), Lists.newArrayList(request));
        if (UN_LOCK_MSG.equals(result)) {
            return true;
        } else {
            return false;
        }
    }
    
    
    
    
    
    /** 
     * 内部构建器
     * 
     * <p>
     * <a href="RedisLock.java"><i>View Source</i></a>
     * </p>
     * @author zl
     * @version 3.0
     * @since 1.0 
    */
    public static class Builder {
        private static final String DEFAULT_LOCK_PREFIX = "lock_";
        
        private static final long DEFAULT_BLOCK_TIME = 100L;
        
        private String lockPrefix = DEFAULT_LOCK_PREFIX;
        private long blockTime = DEFAULT_BLOCK_TIME;
        private JedisCluster jedisClient = JedisFactory.getPersistenceCluster();
        
        public Builder lockPrefix(String prefix) {
            this.lockPrefix = prefix;
            return this;
        }
        
        public Builder blockTime(long blockTime) {
            this.blockTime = blockTime;
            return this;
        }
        
        public RedisLock build() {
            return new RedisLock(this);
        }
    }
    
    
    public static void main(String[] args) {
        final String lockVal = "123";
        ExecutorService exeService = Executors.newFixedThreadPool(101);
        exeService.execute(() ->{
            while(true) {
                try{
                    String el = blockingQueue.take();
                    System.out.println(el);
                }catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
        final String id ="1";
        final String writeLock = "1234";
        RedisLock lock = new RedisLock.Builder().blockTime(1000L).build();
        for(int i = 0 ; i < 100 ; i++) {
            exeService.execute(() ->{
                while (true) {
                    try {
                        lock.lock(id, lockVal);
                        System.out.println("thread:" + Thread.currentThread().getName() + " get lock");
                        lock.unlock(id,lockVal);
                        System.out.println("thread:" + Thread.currentThread().getName() + " release lock");
                        break;
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });
        }
    
    }
}
