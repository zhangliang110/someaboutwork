package org.demo.kafaka;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.gradle.Person;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;


public class ConsumerMsgTask implements Runnable{
	private KafkaStream<byte[], byte[]> m_stream;
	private int m_threadNumber;
	
	public ConsumerMsgTask(KafkaStream<byte[], byte[]> stream, int threadNumber) {
		m_threadNumber = threadNumber;
		m_stream = stream;
	}
	
    public void run() {
        ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
        
        while (it.hasNext()) {
            MessageAndMetadata<byte[], byte[]> next = it.next();
            byte[] byteMsg = next.message();
            ByteArrayInputStream bis = new ByteArrayInputStream(byteMsg);
            ObjectInputStream ois;
            Person p = null;
            try {
                ois = new ObjectInputStream(bis);
                p  = (Person) ois.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (p != null) {
                System.out.println("Thread" + m_threadNumber + "send Message:" + p.getName() + "partitions" + next.partition());
            }
           
        }
        System.out.println("Shutting down Thread: " + m_threadNumber);

    }
}
