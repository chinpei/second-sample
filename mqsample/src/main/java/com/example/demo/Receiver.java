package com.example.demo;

import java.util.function.Consumer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.QueueConnection;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class Receiver {

	ActiveMQConnectionFactory factory;
	ActiveMQQueue queue;

	public Receiver(String url, String userName, String password, String queueName) {
		this.factory = new ActiveMQConnectionFactory();
		this.queue = new ActiveMQQueue(queueName);
		factory.setBrokerURL(url);
		factory.setUserName(userName);
		factory.setPassword(password);
	}
	
	
	public void receive(Consumer<String> consumer) throws JMSException{

		QueueConnection connection = null;
		QueueSession session = null;
		QueueReceiver receiver = null;
		try {
			// コネクションを作成
			connection = factory.createQueueConnection();

			// セッションを作成
			session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

			// キューレシーバーを作成
			receiver = session.createReceiver(queue);

			// メッセージ受信開始
			connection.start();

			for (;;) {
				Message msg = receiver.receive();
				if (msg instanceof TextMessage) {
					TextMessage tm = (TextMessage) msg;
					consumer.accept(tm.getText());
				} else if (msg instanceof ObjectMessage) {
					ObjectMessage om = (ObjectMessage) msg;
					Object obj = om.getObject();
					System.out.println("Object受信：" + obj);
				}
			}

		} finally {
			if (receiver != null) {
				try {
					receiver.close();
				} catch (JMSException e) {
				}
			}
			if (session != null) {
				try {
					session.close();
				} catch (JMSException e) {
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
				}
			}
		}
	}
	
}
