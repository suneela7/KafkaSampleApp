package com.myapp.kafka.clients.consumer;

import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myapp.kafka.clients.domain.Employee;
import com.myapp.kafka.clients.serialize.CustomDeserializer;

public class EmployeeConsumer {
	private static final Logger logger = LoggerFactory.getLogger(EmployeeConsumer.class);
	
	public Properties configProps() {
		
		Properties props = new Properties();
		
		props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomDeserializer.class.getName());
		
		props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "accountGroup");
		props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		return props;
	}
	
	
	public void consumeMessage() {
		
		KafkaConsumer<String, Employee> kafkaConsumer = new KafkaConsumer<>(this.configProps());
		
		kafkaConsumer.subscribe(Collections.singletonList("accountTopic"));
		
		ConsumerRecords<String, Employee>  consumerRecords = kafkaConsumer.poll(1000);
		
		for(ConsumerRecord<String, Employee> consumerRecord : consumerRecords) {
			
			logger.info(" MyConsumer Record Topic: "+consumerRecord.topic() + 
					"  Key: "+consumerRecord.key() +
					"  offset: "+consumerRecord.offset() +
					"  partition: "+consumerRecord.partition() +
					"  value: "+consumerRecord.value() 
					);	
		}
		
		kafkaConsumer.close();
		
		
	}
	
	
}
