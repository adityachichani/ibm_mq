package com.javacodegeeks.jms;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;

public class BrokerLauncher {
	public static void main(String[] args) throws URISyntaxException, Exception {
		URI uri = new URI(
				"broker:(tcp://localhost:61616)");
		System.out.println(uri);
		BrokerService broker = BrokerFactory.createBroker(uri,true);
		//broker.start();		
	}
}
