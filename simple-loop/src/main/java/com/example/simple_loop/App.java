package com.example.simple_loop;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("start infinite loop..");
//		SimpleServer server = new SimpleServer(8080);
//		
//		ExecutorService service = Executors.newSingleThreadExecutor();
//		service.execute(server);
		Locale locale = Locale.getDefault();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		System.out.println(df.format(LocalDateTime.now()) + ", " + locale + "");
		System.out.println("========== [env start] ==========");
		System.getenv().entrySet().forEach(System.out::println);
		System.out.println("========== [env end] ==========");
		
		
		int count = 0;
		while (true) {
			Thread.sleep(3000);
			System.out.println(df.format(LocalDateTime.now()) + ",(" + (++count) + ")");
		}

	}
}
