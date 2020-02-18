package com.example.simple_loop;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("start infinite loop..");
		int count = 0;
		while (true) {
			Thread.sleep(2000);
			System.out.println("Hello Terasawa!(" + (++count) + ")");
		}

	}
}
