/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)ContentTypeEnum.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年10月31日
 */
package org.demo.netty.sinademo.constant;

/** 
 * 
 * <p>
 * <a href="ContentTypeEnum.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public enum ContentTypeEnum {
    NORMALTEXT(1),NORMALAUDIO(2);
    
    private int type;
    
    private ContentTypeEnum(int type) {
        this.type = type;
    }
    
    public int getType() {
        return type;
    }
    
}
