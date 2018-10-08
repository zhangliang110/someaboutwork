package com.zl.common.elasticsearch.factory;

import com.google.common.io.ByteSource;
import com.google.common.io.Resources;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Created by zhangliang on 2018/7/4.
 */
public abstract class BaseConfigProperties {
    private Properties properties;

    protected BaseConfigProperties() {
        try {
            properties = load();
        } catch (IOException e) {

        }
    }

    private Properties load() throws IOException {
        final URL url = getPath();
        ByteSource byteSource = Resources.asByteSource(url);
        Properties properties = new Properties();
        InputStream inputStream = null;
        try{
            inputStream = byteSource.openBufferedStream();
            properties.load(inputStream);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * 给子类提供的一个获取配置文件路径的方法
     * @return
     */
    protected abstract URL getPath();

    protected String getString(String key) {
        return properties.getProperty(key);
    }
}
