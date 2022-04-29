package com.liferay.custom.rest.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CustomRestServiceApp {

	public static void main(String[] args) {
		System.getenv().entrySet().stream().sorted(
			(a, b) -> a.getKey().compareTo(b.getKey())
		).forEach(
			e -> System.out.println(e.getKey() + "=" + e.getValue())
		);

		SpringApplication.run(CustomRestServiceApp.class, args);
	}

}
