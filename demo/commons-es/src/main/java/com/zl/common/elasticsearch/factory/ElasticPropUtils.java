package com.zl.common.elasticsearch.factory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangliang on 2018/7/4.
 */
public class ElasticPropUtils extends BaseConfigProperties {
    public static final String COMMA = ";";

    private static class ElasticPropUtilsBuilder {
        public static ElasticPropUtils INSTANCE = new ElasticPropUtils();
    }

    public static ElasticPropUtils getInstance() {
        return ElasticPropUtilsBuilder.INSTANCE;
    }

    private ElasticPropUtils() {}


    public static  String datacenterClusterName() {
        return getInstance().getString("datacenter_clusterName");
    }

    public static List<String> datacenterClusterNodes() {
        final String value = getInstance().getString("datacenter_clusterNodes");
        final String[] values = value.split(COMMA);
        List<String> nodes = new ArrayList<>();
        if (values != null && values.length > 0 ) {
            for (String val : values) {
                nodes.add(val);
            }
            return nodes;
        } else {
            System.out.println("数据有问题");
        }
        return nodes;
    }

    @Override
    protected URL getPath() {
        return this.getClass().getResource("/elastic.properties");
    }
}
