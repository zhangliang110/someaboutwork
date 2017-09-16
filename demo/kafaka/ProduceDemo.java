package org.demo.kafaka;

import java.util.Properties;

import org.demo.utils.PersonSerializer;
import org.gradle.Person;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import scala.util.Random;

public class ProduceDemo {
    public static void main(String[] args) {
        Random rand = new Random();
        int events = 100;
        
        //配置文件
        Properties prop = new Properties();
        prop.setProperty("metadata.broker.list", "192.168.126.129:9092,192.168.126.129:9093,192.168.126.129:9094");
        prop.put("serializer.class", PersonSerializer.class.getName());
        prop.setProperty("key.serializer.class", "kafka.serializer.StringEncoder");
        
        prop.put("partitioner.class", "org.demo.kafaka.PartitionerDemo");
        
        prop.put("request.required.acks", "1");
        ProducerConfig config = new ProducerConfig(prop);
        
        //创建Producer
        Producer<String, Person> producer = new Producer<>(config);
        long start = System.currentTimeMillis();
        for (int i = 0; i< events; i++) {
            String ip = "192.168.126.129." + i;    //rnd.nextInt(255);
            String msg = "hello kafaka";
            Person p = new Person(msg);
            KeyedMessage<String, Person> keyedMessage = new KeyedMessage<String, Person>("page_visits", ip, p);
            producer.send(keyedMessage);
        }
        System.out.println("耗时" + (System.currentTimeMillis() - start));
        
        producer.close();
    }
}
