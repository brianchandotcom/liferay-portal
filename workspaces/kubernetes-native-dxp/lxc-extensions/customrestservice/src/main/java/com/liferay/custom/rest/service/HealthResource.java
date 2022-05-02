package com.liferay.custom.rest.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HealthResource {

	@GetMapping("/")
	public String ready() {
		return "READY";
	}

}
