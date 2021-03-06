package com.myapp.kafka.clients.producers;

import com.myapp.kafka.clients.domain.Employee;

public interface Producer {
	
	public void sendMessage(String key, String value);
	
	
	public default void sendMessage(String key, Employee value) {
		
	}
	
	public default void sendMessage(Integer key, String value) {
		this.sendMessage(String.valueOf(key), value);
	}
	
	public void closeProducer();
	
	
	public default void addDelay() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
