/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)LoginCacheUtils.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年10月31日
 */
package org.demo.netty.sinademo.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

/** 
 * 存储用户登录相关缓存
 * 
 * <p>
 * <a href="LoginCacheUtils.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class LoginCacheUtils {
    public static Cache<Channel,ChannelId> cacheBuilder =  CacheBuilder.newBuilder().maximumSize(100000).expireAfterWrite(24, TimeUnit.HOURS).build();
    public static Map<Channel,String> userMap = new ConcurrentHashMap<>();
    
    public static void addUserCache(Channel channel) {
        cacheBuilder.put(channel,channel.id());
    }
    
    public static void addUserMap(String user, Channel channel) {
        userMap.put(channel, user);
    }
    
    public static boolean isLogin(Channel uChannel) {
        return userMap.get(uChannel) != null;
    }
    
    public static boolean removeUserChannel(Channel channel) {
        return userMap.remove(channel) != null;
    }
}
