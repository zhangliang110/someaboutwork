package com.zl.common.elasticsearch.factory;

/**
 * Created by zhangliang on 2018/7/4.
 */
public class ElasticFactory {
    public static DatacenterElasticRepository getDatacenterRepository() {
        return DatacenterElasticRepository.getInstance();
    }
}
