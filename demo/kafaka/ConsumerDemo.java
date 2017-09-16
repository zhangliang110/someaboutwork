package org.demo.kafaka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

/**
 * @author zhangliang
 * 
 */
public class ConsumerDemo {
	private final ConsumerConnector consumer;
	private final String topic;
	private ExecutorService executor;
	
	public ConsumerDemo(String a_zookeeper, String a_groupId, String a_topic) {
		consumer = Consumer.createJavaConsumerConnector(createConsumerConfig(a_zookeeper,a_groupId));
		this.topic = a_topic;
	}
	
	private static ConsumerConfig createConsumerConfig(String a_zookeeper,
			String a_groupId) {
		Properties props = new Properties();
		props.put("zookeeper.connect", a_zookeeper);
		props.put("group.id", a_groupId);
		props.put("zookeeper.session.timeout.ms", "10000");
		props.put("zookeeper.sync.time.ms", "200");
		props.put("auto.commit.interval.ms", "1000");

		return new ConsumerConfig(props);
	}
	
	public void shutdown() {
		if (consumer != null) {
			consumer.shutdown();
		}
		if (executor != null) {
			executor.shutdown();
		}
	}
	
	public void runDemo(int numThreads) {
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, new Integer(numThreads));
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
		List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);
		
		// now launch all the threads
		executor = Executors.newFixedThreadPool(numThreads);
		
		// now create an object to consume the messages
		int threadNum = 0;
		for (final KafkaStream<byte[], byte[]> stream : streams) {
			executor.submit(new ConsumerMsgTask(stream, threadNum));
			threadNum++;
		}
	}
	
	public static void main(String[] arg) {
		String[] args = { "192.168.126.129:2181", "group-1", "page_visits", "12" };
		String zooKeeper = args[0];
		String groupId = args[1];
		String topic = args[2];
		int threads = Integer.parseInt(args[3]);

		ConsumerDemo demo = new ConsumerDemo(zooKeeper, groupId, topic);
		demo.runDemo(threads);

		try {
			Thread.sleep(10000);
		} catch (InterruptedException ie) {

		}
		demo.shutdown();
	}
}
