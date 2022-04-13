package com.liferay.custom.rest.service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/random")
public class RandomRestController {

	@Value("${DXP_SERVICE_URI}")
	String dxpServiceUri;

	private static final Logger logger = LoggerFactory.getLogger(RandomRestController.class);

	@GetMapping(value = {"/number", "/number/{count}"})
	public Long randomLong(@PathVariable Optional<Integer> count) {
		return _randomLong(count.orElse(2));
	}

	@GetMapping(value = {"/alpha", "/alpha/{count}"})
	public String randomAlpha(@PathVariable Optional<Integer> count) {
		return _randomAlpha(count.orElse(2));
	}

	private String _randomAlpha(int count) {
		return randomAlphabetic(count);
	}

	private Long _randomLong(int count) {
		return Long.parseLong(randomNumeric(count));
	}

	@PutMapping(value = "/user/{id}")
	public void updateUser(@PathVariable Long id, BearerTokenAuthentication authentication) {
		OAuth2AccessToken authToken = authentication.getToken();
		String accessToken = authToken.getTokenValue();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + accessToken);

		HttpEntity<String> entity = new HttpEntity<String>("{\"givenName\":\"" + _randomAlpha(4) + "\"}", headers);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		String result = restTemplate.patchForObject(
			dxpServiceUri + "/o/headless-admin-user/v1.0/user-accounts/" + id, entity, String.class);
		logger.info("User patched " + result);
	}

}