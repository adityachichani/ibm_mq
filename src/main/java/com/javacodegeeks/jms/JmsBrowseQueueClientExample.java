package com.javacodegeeks.jms;

import java.net.URISyntaxException;
import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsBrowseQueueClientExample {
	public static void main(String[] args) throws URISyntaxException, Exception {
		Connection connection = null;
		try {
			// Producer
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					"tcp://localhost:61616");
			connection = connectionFactory.createConnection();
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("customerQueue");

			MessageConsumer consumer = session.createConsumer(queue);
			connection.start();
			
			System.out.println("Browse through the elements in queue");
			QueueBrowser browser = session.createBrowser(queue);
			Enumeration e = browser.getEnumeration();
			while (e.hasMoreElements()) {
				TextMessage message = (TextMessage) e.nextElement();
				System.out.println("Get [" + message.getText() + "]");
			}
			System.out.println("Done");
			browser.close();
			
			TextMessage textMsg = (TextMessage) consumer.receive();
			System.out.println(textMsg);
			System.out.println("Received: " + textMsg.getText());
			session.close();
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

}
