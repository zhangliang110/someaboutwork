package com.zl.common.elasticsearch.utils;

import org.elasticsearch.search.aggregations.Aggregation;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangliang on 2018/7/3.
 */
public class SearchEntityPage<T> implements Serializable{
    private float took;
    private long total;
    private float maxScore;
    private List<T> conent;

    private Aggregation aggregations;

    public float getTook() {
        return took;
    }

    public void setTook(float took) {
        this.took = took;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public float getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(float maxScore) {
        this.maxScore = maxScore;
    }

    public List<T> getConent() {
        return conent;
    }

    public void setConent(List<T> conent) {
        this.conent = conent;
    }

    public Aggregation getAggregations() {
        return aggregations;
    }

    public void setAggregations(Aggregation aggregations) {
        this.aggregations = aggregations;
    }

    @Override
    public String toString() {
        return "SearchEntityPage{" +
                "took=" + took +
                ", total=" + total +
                ", maxScore=" + maxScore +
                ", conent=" + conent +
                ", aggregations=" + aggregations +
                '}';
    }
}
