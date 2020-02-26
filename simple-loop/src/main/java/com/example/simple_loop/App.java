package com.example.simple_loop;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("start infinite loop..");
//		SimpleServer server = new SimpleServer(8080);
//		ExecutorService service = Executors.newSingleThreadExecutor();
//		service.execute(server);
		Locale locale = Locale.getDefault();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		System.out.println(df.format(LocalDateTime.now()) + ", " + locale + "");
		System.out.println("========== [env start] ==========");
		System.getenv().entrySet().forEach(System.out::println);
		System.out.println("========== [env end] ==========");
		startReceiver();
		startSender();

		int count = 0;
		while (true) {
			Thread.sleep(3000);
			System.out.println(df.format(LocalDateTime.now()) + ",(" + (++count) + ")");
		}
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
