package com.example.simple_loop;

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
		int count = 0;
		while (true) {
			Thread.sleep(2000);
			System.out.println("Hello Terasawa!(" + (++count) + ")");
		}

	}
}
