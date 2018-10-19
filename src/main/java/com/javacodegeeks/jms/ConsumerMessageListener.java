package com.javacodegeeks.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ConsumerMessageListener implements MessageListener {
	private String consumerName;
	private JmsAsyncReceiveQueueClientExample asyncReceiveQueueClientExample;
	
	public ConsumerMessageListener(String consumerName) {
		this.consumerName = consumerName;
	}

	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		try {
			System.out.println(consumerName + " received " + textMessage.getText());
			if ("END".equals(textMessage.getText())) {
				asyncReceiveQueueClientExample.latchCountDown();
			}
		} catch (JMSException e) {			
			e.printStackTrace();
		}
	}

	public void setAsyncReceiveQueueClientExample(
			JmsAsyncReceiveQueueClientExample asyncReceiveQueueClientExample) {
		this.asyncReceiveQueueClientExample = asyncReceiveQueueClientExample;
	}	
}
