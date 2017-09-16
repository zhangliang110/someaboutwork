/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)TestHbaseTemplate.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月15日
 */
package org.demo.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.demo.hbase.utils.HbaseTableFactory;
import org.demo.hbase.utils.HbaseTemplate;

/** 
 * 功能描述
 * 
 * <p>
 * <a href="TestHbaseTemplate.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class TestHbaseTemplate extends AbstractHbaseTemplate{

    @Override
    public HbaseTemplate getHbaseTemplate() {
        return HbaseTemplateBuilder.INSTANCE;
    }
    
    private static final class TestHbaseTemplateBuilder {
        private static final TestHbaseTemplate INSTANCE = new TestHbaseTemplate();
    }
    
    public static TestHbaseTemplate getInstance() {
        return TestHbaseTemplateBuilder.INSTANCE;
    }
    
    private static final class HbaseTemplateBuilder {
        private static final HbaseTemplate INSTANCE = new HbaseTemplate();
    }
    
    
    private TestHbaseTemplate() {
        Configuration config = new Configuration();
        config.set("hbase.zookeeper.quorum", "192.168.0.9");
        config.set("hbase.zookeeper.property.clientPort", "2181");
        try {
            //connection 必须是单例
            Connection connection = ConnectionFactory.createConnection(config);
            HbaseTableFactory hbaseFactory = new HbaseTableFactory();
            HbaseTemplate template = HbaseTemplateBuilder.INSTANCE;
            template.setConnection(connection);
            template.setTableFactory(hbaseFactory);
            
            //线程结束时，关闭调此资源
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    if (!connection.isClosed()) {
                        if (connection != null) {
                            try {
                                connection.close();
                            } catch (IOException e) {
                                //DO noting
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
