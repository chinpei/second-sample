package com.example.show_env;

import java.util.TreeMap;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		System.out.println("========== [env start] ==========");
		(new TreeMap<>(System.getenv())).entrySet().forEach(System.out::println);
		System.out.println("========== [env end] ==========");

	}
}
