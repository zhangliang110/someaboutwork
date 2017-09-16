/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)HbaseDemo.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月5日
 */
package org.demo.hbase;

import java.io.IOException;
import java.util.stream.Stream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.CellScanner;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

/** 
 * 功能描述
 * 
 * <p>
 * <a href="HbaseDemo.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public class HbaseDemo {
    
    public static void main(String[] args) {
        try {
            TestHbaseTemplate template = TestHbaseTemplate.getInstance();
            boolean tableExists = template.tableExists("zl_tst");
            System.out.println(tableExists);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
   /* public static void createTable(String tableName, String[] family) throws Exception {
        HBaseAdmin admin = new HBaseAdmin(conf);
        HTableDescriptor desc = new HTableDescriptor(tableName);
        Stream.of(family).forEach(f -> desc.addFamily(new HColumnDescriptor(f)));
        if (admin.tableExists(tableName)) {
            System.out.println("table is exists!");
        } else {
            admin.createTable(desc);
            System.out.println("create table success");
        }
        admin.close();
    }
    
    public static void put(String tableName, String row, String columnFamily, String column, String data) throws IOException {
        //向columnFamily 中列名为column添加数据
        HTable hTable = new HTable(conf, tableName);
        Put p1 = new Put(Bytes.toBytes(row));
        p1.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(data));
        hTable.put(p1);
        System.out.println("创建成功");
    }
    
    public static void get(String tableName, String row) throws IOException {
        HTable hTable = new HTable(conf, tableName);
        Get get = new Get(Bytes.toBytes(row));
        Result result = hTable.get(get);
        CellScanner cellScanner = result.cellScanner();
        while(cellScanner.advance()) {
            System.out.println(Bytes.toString(CellUtil.cloneValue(cellScanner.current())));
        }
        hTable.close();
    }
    
    public static void scan(String tableName) throws Exception {
        HTable hTable = new HTable(conf, tableName);
        Scan scan = new Scan();
        ResultScanner rs = hTable.getScanner(scan);
        for (Result r = rs.next(); r != null; r = rs.next()) {
            System.out.println(r);
        }
        hTable.close();
        
    }*/
}
