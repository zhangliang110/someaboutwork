package com.zl.common.elasticsearch.factory;

import com.zl.common.elasticsearch.utils.AbstractElasticRepository;
import org.elasticsearch.client.Client;

/**
 * Created by zhangliang on 2018/7/4.
 */
public class DatacenterElasticRepository extends AbstractElasticRepository{

    private final static class DatacenterElasticRepositoryBuilder {
        public static DatacenterElasticRepository INSTANCE = new DatacenterElasticRepository(ElasticClientFactory.ElasticClientBuilder.DATACENTER);
    }


    private DatacenterElasticRepository(Client client) {
        super(client);
    }

    static DatacenterElasticRepository getInstance() {
        return DatacenterElasticRepositoryBuilder.INSTANCE;
    }
}
