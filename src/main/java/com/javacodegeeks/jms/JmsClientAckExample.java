package com.javacodegeeks.jms;

import java.net.URISyntaxException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsClientAckExample {
	public static void main(String[] args) throws URISyntaxException, Exception {
		Connection connection = null;
		try {
			// Producer
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					"vm://localhost?broker.persistent=false");
			connection = connectionFactory.createConnection();
			connection.start();
			Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
	        Queue queue = session.createQueue("SomeQueue");
	        MessageProducer producer = session.createProducer(queue);
	        producer.send(session.createTextMessage("Hello"));

	        MessageConsumer consumer = session.createConsumer(queue);
	        TextMessage msg = (TextMessage) consumer.receive(1000);
	        System.out.println("Consume: " + msg.getText());
	        
	        // Reset the session.
	        session.close();
	        session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

	        // Attempt to Consume the message...
	        consumer = session.createConsumer(queue);
	        msg = (TextMessage) consumer.receive(1000);
	        System.out.println("Attempt to consume again. Is message received? " + (msg != null));
	        
	        //acknowledge
	        msg.acknowledge();

			// Reset the session.
	        session.close();
	        session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

	        // Attempt to Consume the message...
	        consumer = session.createConsumer(queue);
	        msg = (TextMessage) consumer.receive(1000);
	        System.out.println("Attempt to consume again. Is message received? " + (msg != null));
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}
}
