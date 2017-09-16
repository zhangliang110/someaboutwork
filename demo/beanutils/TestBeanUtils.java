/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)TestBeanUtils.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月13日
 */
package org.demo.beanutils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.gradle.Person;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/** 
 * 功能描述
 * 
 * <p>
 * <a href="TestBeanUtils.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class TestBeanUtils {
    public static void main(String[] args) {
        Person p = new Person("zl");
        try {
            BeanUtils.copyProperty(p, "age", 22);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        try {
            Object property = org.apache.commons.beanutils.PropertyUtils.getProperty(p, "name");
            System.out.println(property);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        }
        
        Person p2 = new Person("wk");
        p2.setHands(Lists.newArrayList(1,2,3));
        
        try {
            BeanUtils.copyProperty(p2, "hands", Sets.newHashSet(1,2,3));
            System.out.println(p2.getHands());
        } catch (IllegalAccessException | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
