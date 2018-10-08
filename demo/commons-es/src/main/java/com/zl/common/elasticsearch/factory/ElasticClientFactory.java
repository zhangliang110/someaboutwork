package com.zl.common.elasticsearch.factory;

import org.elasticsearch.client.Client;

/**
 * Created by zhangliang on 2018/7/4.
 */
public class ElasticClientFactory {
    private final static ElasticClientFactoryBean DATACENTER_INSTANCE = new ElasticClientFactoryBean();
    static {
        if (ElasticPropUtils.datacenterClusterName() != null && !ElasticPropUtils.datacenterClusterName().trim().isEmpty()) {
            DATACENTER_INSTANCE.setClusterName(ElasticPropUtils.datacenterClusterName());
            DATACENTER_INSTANCE.setClusterNodes(ElasticPropUtils.datacenterClusterNodes());
        }
    }

    final static class ElasticClientBuilder {
        final static  Client DATACENTER = getObject(DATACENTER_INSTANCE);
    }

    private  static Client getObject(ElasticClientFactoryBean bean) {
        try {
            return bean.getObject();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
