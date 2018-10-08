package com.zl.common.elasticsearch.utils;

import java.io.IOException;

/**
 * Created by zhangliang on 2018/7/3.
 */
public interface EntityMapper {

    /**
     * 将Object序列化为json格式文本
     * @param object
     * @return
     * @throws IOException
     */
    public String mapToString(Object object) throws IOException;

    /**
     * 将json文本转换为对应的对象
     * @param source
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public <T> T stringToObject(String source, Class<T> clazz) throws IOException;
}
