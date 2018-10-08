package com.zl.common.elasticsearch.factory;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.engine.Engine;

import java.net.InetAddress;
import java.util.List;

/**
 * Created by zhangliang on 2018/7/4.
 */
public class ElasticClientFactoryBean {
    private static final String COLON = ":";

    private TransportClient client;
    private String clusterName;
    private List<String> clusterNodes;

    public ElasticClientFactoryBean() {
        final Runtime run = Runtime.getRuntime();
        run.addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    if (client != null) {
                        System.out.println("elasticSearch 集群客户端开始关闭");
                        client.close();
                    }
                }catch (Exception e) {
                    System.out.println("关闭失败");
                }
            }
        });
    }
    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public List<String> getClusterNodes() {
        return clusterNodes;
    }

    public void setClusterNodes(List<String> clusterNodes) {
        this.clusterNodes = clusterNodes;
    }

    /**
     *
     * @return
     */
    public Client getObject() throws Exception{
        client = null;
        if (clusterName == null || clusterName.trim().equals("")) {
            Settings settings = Settings.builder().put("client.transport.sniff", true).put("cluster.name", clusterName).build();
            client = TransportClient.builder().settings(settings).build();
            if (clusterNodes != null && !clusterNodes.isEmpty()) {
                for (String node : clusterNodes) {
                    final String[] values = node.split(COLON);
                    final String host = values[0];
                    final String port = values[1];
                    client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), Integer.valueOf(port)));
                }
            }
            client.connectedNodes();
        }
        return client;
    }
}
