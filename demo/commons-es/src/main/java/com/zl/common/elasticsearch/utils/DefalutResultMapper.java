package com.zl.common.elasticsearch.utils;


import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.zl.common.elasticsearch.constant.ElasticRouteConstant;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.highlight.HighlightField;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by zhangliang on 2018/7/3.
 */
public class DefalutResultMapper {
    public  static final EntityMapper entityMapper = new DefaultEntityMapper();

    public <T> SearchEntityPage<T> mapResults(SearchResponse response, Class<T> clazz, final Set<String> appendHighLights) {
        SearchEntityPage<T> entityPage = new SearchEntityPage<>();
        List<T> results = new ArrayList<>();
        entityPage.setTook((float) response.getTookInMillis());
        final SearchHits hits = response.getHits();
        entityPage.setMaxScore(hits.getMaxScore());
        entityPage.setTotal(hits.getTotalHits());
        for (SearchHit hit : hits) {
            if (hit != null ) {
                T result = mapEntity(hit, clazz);
                results.add(result);
            }
        }
        entityPage.setConent(results);
        return entityPage;
    }

    public <T> T mapEntity(final SearchHit hit ,final Class<T> clazz) {
        T result = null;
        if (!isBlank(hit.sourceAsString())) {
            result = mapEntity(hit.getSourceAsString(), clazz);
        } else {
            result = mapEntity(hit.getFields().values(), clazz);
        }
        //请求总设置了高亮属性，则需要替换
        Map<String, HighlightField> highlightFields = hit.getHighlightFields();
        if (highlightFields != null && !highlightFields.isEmpty()) {
            for (HighlightField field : highlightFields.values()) {
                //属性对应的对象名
                String name = field.getName();
                //属性对应的高亮显示的值
                Text[] values = field.fragments();
                try {
                    // 内联对象name为XXX.XXX格式
                    int index = name.indexOf(".");
                    if (index > 0) {
                        int length = BeanUtils.getArrayProperty(result, name.substring(0, index)).length;
                        for (int i = 0; i < length; i++) {
                            for (Text value : values) {
                                String val = value.string();
                                String pureVal = val.replace(ElasticRouteConstant.sKeywordStart, "").replace(ElasticRouteConstant.getsKeywordEnd, "");
                                String indexName = name.replace(".", "[" + i + "].");
                                if (pureVal.equals(BeanUtils.getProperty(result, indexName))) {
                                    BeanUtils.setProperty(result, indexName, value);
                                }
                            }
                        }
                    } else {
                        Object pVal = PropertyUtils.getProperty(result, name);
                        if (pVal instanceof Collection) {
                            String[] proVals = BeanUtils.getArrayProperty(result, name);
                            Collection<String> newValues;
                            if (pVal instanceof Set) {
                                newValues = new HashSet<>();
                            } else {
                                newValues = new ArrayList<>();
                            }
                            for (int i = 0; i < proVals.length; i++) {
                                boolean heightLight = false;
                                for (Text value : values) {
                                    String heightLightval = value.string();
                                    String pureVal = heightLightval.replace(ElasticRouteConstant.sKeywordStart, "").replace(ElasticRouteConstant.getsKeywordEnd, "");
                                    if (pureVal.equals(proVals[i])) {
                                        heightLight = true;
                                        newValues.add(heightLightval);
                                        break;
                                    }
                                }
                                if (!heightLight) {
                                    newValues.add(proVals[i]);
                                }
                                BeanUtils.setProperty(result, name, newValues);
                            }
                        } else if(pVal.getClass().isArray()) {
                            String[] proVals = BeanUtils.getArrayProperty(result, name);
                            for (int i = 0; i < proVals.length; i++) {
                                for (Text value : values) {
                                    String val = value.string();
                                    String indexName = name.concat("[" + i + "]");
                                    String pureVal = val.replace(ElasticRouteConstant.sKeywordStart, "").replace(ElasticRouteConstant.getsKeywordEnd, "");
                                    if (pureVal.equals(BeanUtils.getProperty(result, indexName))) {
                                        BeanUtils.setProperty(result, indexName, value);
                                    }
                                }
                            }
                        } else if (pVal instanceof Map) {
                            //do nothing
                        } else {
                            BeanUtils.setProperty(result, name, values[0].string());
                        }
                    }
                } catch (Exception e) {
                    System.out.println("解析高亮报错");
                }
            }
        }
        return result;
    }

    /**
     * 实体解析
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T mapEntity(final String source, final Class<T> clazz) {
        T result = null;
        if (isBlank(source)) {
            return null;
        }
        try {
            return entityMapper.stringToObject(source, clazz);
        }catch (IOException e) {
            throw  new ElasticsearchException("failed to map source[" + source + "]" + "to class" + clazz.getSimpleName(), e);
        }
    }

    public boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    private <T> T mapEntity(Collection<SearchHitField> values, Class<T> clazz) {
        return mapEntity(buildJSONFromFields(values), clazz);
    }

    private String buildJSONFromFields(Collection<SearchHitField> values) {
        JsonFactory nodeFactory = new JsonFactory();
        try{
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            JsonGenerator generator = nodeFactory.createGenerator(stream, JsonEncoding.UTF8);
            generator.writeStartObject();
            for (SearchHitField field : values) {
                if (field.getValues().size() > 1) {
                    generator.writeArrayFieldStart(field.getName());
                    for (Object val : field.getValues()) {
                        generator.writeObject(val);
                    }
                    generator.writeEndArray();
                } else {
                    generator.writeObjectField(field.getName(), field.getValue());
                }
            }
            generator.writeEndObject();
            generator.flush();
            return new String(stream.toByteArray(), Charset.forName("UTF-8"));
        }catch (IOException e) {
            return null;
        }
    }
}
