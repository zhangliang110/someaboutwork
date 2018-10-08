package com.zl.common.elasticsearch.utils;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;

/**
 * Created by zhangliang on 2018/7/3.
 */
public class AbstractElasticRepository {
    private static final DefaultEntityMapper RESULT_MAPPER = new DefaultEntityMapper();

    protected final Client client;
    protected final String timeout;

    public AbstractElasticRepository(Client client) {
        this.client = client;
        this.timeout = "10s";
    }

    public void forcemerge(String indices) {
        if (null != indices && indices.length() > 0) {
            client.admin().indices().prepareForceMerge(indices).setMaxNumSegments(1).setOnlyExpungeDeletes(true).get();
        } else {
            client.admin().indices().prepareForceMerge().setMaxNumSegments(1).setOnlyExpungeDeletes(true).get();
        }
    }

    public <T> void save(final String index, final String type, final String id, final String routing, T entity, boolean refresh) {
        try {
            IndexRequestBuilder indexRequestBuilder = client.prepareIndex();
            indexRequestBuilder.setRouting(routing);
            indexRequestBuilder.setOpType(IndexRequest.OpType.INDEX);
            indexRequestBuilder.setRefresh(refresh);
        } catch (Exception e) {

        }
    }


}
