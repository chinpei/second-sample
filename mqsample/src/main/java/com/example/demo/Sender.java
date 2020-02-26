package com.example.demo;

import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

/**
 * Hello world!
 *
 */
public class Sender {
	ActiveMQConnectionFactory factory;
	ActiveMQQueue queue;

	public Sender(String url, String userName, String password, String queueName) {
		this.factory = new ActiveMQConnectionFactory();
		this.queue = new ActiveMQQueue(queueName);
		factory.setBrokerURL(url);
		factory.setUserName(userName);
		factory.setPassword(password);
	}

	public void send(String message) throws JMSException {

		QueueConnection connection = null;
		QueueSession session = null;
		QueueSender sender = null;
		
		try {
			// コネクションを作成
			connection = factory.createQueueConnection();

			// セッションを作成
			session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

			// キューセンダーを作成
			sender = session.createSender(queue);

			// メッセージ作成
			TextMessage msg = session.createTextMessage();
			msg.setText(message);

			// メッセージ送信
			connection.start(); // これは無くても動くような…
			sender.send(msg);

		} finally {
			if (sender != null) {
				try {
					sender.close();
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
