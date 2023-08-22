/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.delectable.bonsai;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author Raymond Augé
 * @author Gregory Amerson
 * @author Brian Wing Shun Chan
 */
@RequestMapping("/object/action/accountsetup")
@RestController
public class ObjectActionAccountSetupRestController extends BaseRestController {

	@PostMapping
	public ResponseEntity<String> post(
		@AuthenticationPrincipal Jwt jwt, @RequestBody String json) {

		log(jwt, _log, json);

		JSONObject payload = new JSONObject(json);

		JSONObject jsonApplicationDTO = payload.getJSONObject("objectEntryDTODistributorApplication");

		JSONObject jsonProperties = jsonApplicationDTO.getJSONObject("properties");

		String accountName = jsonProperties.getString("businessName");

		String accountERC = "ACCOUNT_" + accountName.toUpperCase().replace(" ", "_");

		String email = jsonProperties.getString("applicantEmail");

		try {
			WebClient.Builder builder = WebClient.builder();

			WebClient webClient = builder.baseUrl(
				lxcDXPServerProtocol + "://" + lxcDXPMainDomain
			).defaultHeader(
				HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE
			).defaultHeader(
				HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
			).build();

			createBusinessAccount(
				webClient, jwt, accountERC, accountName
			).doOnSuccess(
				responseEntity -> logResponse(responseEntity, "Account Created")
			).then(
				associateUserWithAccount(webClient, jwt, accountERC, email)
			).doOnSuccess(
				responseEntity -> logResponse(responseEntity, "User Assigned")
			).then(
				getRoleId(webClient, jwt, accountERC)
			).flatMap(
				accountRoleId -> {
					return assignAccountRoleToUser(
						webClient, jwt, accountERC, accountRoleId, email
					).doOnSuccess(
						responseEntity -> logResponse(responseEntity, "Role Assigned")
					);
				}
			).subscribe();

		} catch (Exception exception) {
			_log.error("JSON: " + json, exception);

			return new ResponseEntity<>(json, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		return new ResponseEntity<>(json, HttpStatus.OK);
	}

	private Mono<ResponseEntity<String>> createBusinessAccount(
		WebClient webClient, Jwt jwt, String accountERC, String accountName) {

		return webClient.post(
		).uri(
			"o/headless-admin-user/v1.0/accounts"
		).bodyValue(
			"{\"externalReferenceCode\": \"" + accountERC + "\", \"name\": \"" + accountName + "\", \"type\": \"business\"}"
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).retrieve(
		).toEntity(
			String.class
		).flatMap(
			firstResponseEntity -> {
				if (firstResponseEntity.getStatusCode().is2xxSuccessful()) {
					return Mono.just(firstResponseEntity);
				}
				else {
					String firstPostErrorMessage = "Failed to create business account: " + firstResponseEntity.getBody();
					return Mono.error(new RuntimeException(firstPostErrorMessage));
				}
			}
		);
	}
	
	private Mono<ResponseEntity<String>> associateUserWithAccount(
		WebClient webClient, Jwt jwt, String accountERC, String email) {

		return webClient.post(
		).uri(
			"o/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/user-accounts/by-email-address/{emailAddress}", accountERC, email
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).retrieve(
		).toEntity(
			String.class
		).flatMap(
			secondResponseEntity -> {
				if (secondResponseEntity.getStatusCode().is2xxSuccessful()) {
					return Mono.just(secondResponseEntity);
				}
				else {
					String secondPostErrorMessage = "Failed to associate user with account: " + secondResponseEntity.getBody();
					return Mono.error(new RuntimeException(secondPostErrorMessage));
				}
			}
		);
	}
	
	private Mono<ResponseEntity<String>> assignAccountRoleToUser(
		WebClient webClient, Jwt jwt, String accountERC, Integer accountRoleId, String email) {

		return webClient.post(
		).uri(
			"o/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/account-roles/{accountRoleId}/user-accounts/by-email-address/{emailAddress}", accountERC, accountRoleId, email
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).retrieve(
		).toEntity(
			String.class
		).flatMap(
			thirdResponseEntity -> {
				if (thirdResponseEntity.getStatusCode().is2xxSuccessful()) {
					return Mono.just(thirdResponseEntity);
				}
				else {
					String thirdPostErrorMessage = "Failed to associate user with account: " + thirdResponseEntity.getBody();
					return Mono.error(new RuntimeException(thirdPostErrorMessage));
				}
			}
		);
	}

	private Mono<Integer> getRoleId(WebClient webClient, Jwt jwt, String accountERC) {
		return webClient.get(
		).uri(
			uriBuilder -> uriBuilder.path(
				"o/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/account-roles"
			).queryParam(
				"filter", "name eq 'Account Administrator'"
			).build(
				accountERC
			)
		).header(
			HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue()
		).retrieve(
		).bodyToMono(
			String.class
		).map(
			json -> new JSONObject(
				json
			).getJSONArray(
				"items"
			).getJSONObject(
				0
			).getInt(
				"id"
			)
		);
	}

	private void logResponse(
		ResponseEntity<String> responseEntity, String requestName) {
		
		HttpStatus statusCode = responseEntity.getStatusCode();

		String responseBody = responseEntity.getBody();
	
		_log.info("Output: " + requestName + " - HTTP Status: " + statusCode);

		_log.info("Response: " + responseBody);
	}

	private static final Log _log = LogFactory.getLog(
		ObjectActionAccountSetupRestController.class);

}