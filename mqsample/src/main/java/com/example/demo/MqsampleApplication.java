package com.example.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class MqsampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MqsampleApplication.class, args);
		System.out.println("xxx");
		startReceiver();
		startSender();
	}

	static void startSender() {
		String tcpurl = System.getenv("AMQ_SAMPLE01_AMQ_TCP_PORT");
		// queue01
		// amquser01
		// amqpass01
		Sender sender = new Sender(tcpurl, "amquser01", "amqpass01", "queue01");
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(() -> {
			try {
				sender.send("testest");
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}, 1, 2, TimeUnit.SECONDS);

	}

	static void startReceiver() {
		String tcpurl = System.getenv("AMQ_SAMPLE01_AMQ_TCP_PORT");
		Receiver receiver = new Receiver(tcpurl, "amquser01", "amqpass01", "queue01");
		ExecutorService service = Executors.newSingleThreadExecutor();
		service.execute(() -> {
			try {
				receiver.receive(text -> {
					System.out.println("受信しました。->" + text);
				});
			} catch (JMSException e) {
				e.printStackTrace();
			}
		});
	}

}
