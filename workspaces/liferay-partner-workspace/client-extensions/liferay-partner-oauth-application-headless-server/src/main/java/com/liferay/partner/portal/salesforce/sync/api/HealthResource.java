package com.liferay.partner.portal.salesforce.sync.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
public class HealthResource {

	@GetMapping("/")
	public String ready() throws Exception {
		return "READY";
	}

}