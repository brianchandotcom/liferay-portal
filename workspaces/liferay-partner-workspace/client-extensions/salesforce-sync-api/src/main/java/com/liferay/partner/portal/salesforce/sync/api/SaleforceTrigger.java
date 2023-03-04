package com.liferay.partner.portal.salesforce.sync.api;

import org.json.JSONObject;

import org.springframework.http.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
public class SaleforceTrigger {

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/")
	public ResponseEntity<String> trigger(@AuthenticationPrincipal Jwt jwt) {
		System.out.println("JWT ID: " + jwt.getId());
		System.out.println("JWT SUBJECT: " + jwt.getSubject());
		System.out.println("JWT CLAIMS: " + jwt.getClaims());
		System.out.println("JWT TOKEN VALUE: " + jwt.getTokenValue());

		getObjectEntries("employee", jwt);
		createObjectEntry("employee", "1", jwt);
		updateObjectEntry("employee", 55943, "00", jwt);
		getObjectEntries("employee", jwt);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	private void createObjectEntry(String objectName, String age, Jwt jwt) {
		JSONObject requestBody = new JSONObject();

		requestBody.put("age", age);

		WebClient _webClient = WebClient.builder(
		).baseUrl(
			"https://".concat("dxp.lfr.dev")
		).defaultHeader(
			HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE
		).defaultHeader(
			HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
		).build();

		_webClient.post(
		).uri(
			"/o/c/" + objectName + "/"
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).bodyValue(
			requestBody
		).exchangeToMono(
			r -> {
				if (r.statusCode(
					).equals(
						HttpStatus.OK
					)) {

					return r.bodyToMono(String.class);
				}
				else if (r.statusCode(
						).is4xxClientError()) {

					return Mono.just("Error response");
				}

				return r.createException(
				).flatMap(
					Mono::error
				);
			}
		).doOnNext(
			System.out::println
		).subscribe();
	}

	private void getObjectEntries(String objectName, Jwt jwt) {
		WebClient _webClient = WebClient.builder(
		).baseUrl(
			"https://".concat("dxp.lfr.dev")
		).defaultHeader(
			HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE
		).defaultHeader(
			HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
		).build();

		_webClient.get(
		).uri(
			"/o/c/" + objectName + "/"
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).exchangeToMono(
			r -> {
				if (r.statusCode(
					).equals(
						HttpStatus.OK
					)) {

					return r.bodyToMono(String.class);
				}
				else if (r.statusCode(
						).is4xxClientError()) {

					return Mono.just("Error response");
				}

				return r.createException(
				).flatMap(
					Mono::error
				);
			}
		).doOnNext(
			System.out::println
		).subscribe();
	}

	private void updateObjectEntry(
		String objectName, int objectEntryId, String age, Jwt jwt) {

		JSONObject requestBody = new JSONObject();

		requestBody.put("age", age);

		WebClient _webClient = WebClient.builder(
		).baseUrl(
			"https://".concat("dxp.lfr.dev")
		).defaultHeader(
			HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE
		).defaultHeader(
			HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
		).build();

		_webClient.patch(
		).uri(
			"/o/c/" + objectName + "/" + objectEntryId
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).bodyValue(
			requestBody
		).exchangeToMono(
			r -> {
				if (r.statusCode(
					).equals(
						HttpStatus.OK
					)) {

					return r.bodyToMono(String.class);
				}
				else if (r.statusCode(
						).is4xxClientError()) {

					return Mono.just("Error response");
				}

				return r.createException(
				).flatMap(
					Mono::error
				);
			}
		).doOnNext(
			System.out::println
		).subscribe();
	}

}