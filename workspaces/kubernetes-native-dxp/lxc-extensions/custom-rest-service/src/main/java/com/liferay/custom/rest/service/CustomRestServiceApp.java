package com.liferay.custom.rest.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class CustomRestServiceApp {

	public static void main(String[] args) {
		SpringApplication.run(CustomRestServiceApp.class, args);
	}

}
