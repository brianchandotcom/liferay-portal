package com.liferay.partner.portal.salesforce.sync.api.config;

import java.util.Objects;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

public class ClientIdValidator implements OAuth2TokenValidator<Jwt> {

	public ClientIdValidator(String clientId) {
		this.clientId = clientId;
	}

	@Override
	public OAuth2TokenValidatorResult validate(Jwt jwt) {
		if (Objects.equals(jwt.getClaimAsString("client_id"), clientId)) {
			return OAuth2TokenValidatorResult.success();
		}

		return OAuth2TokenValidatorResult.failure(NO_CLIENT_ID_MATCH);
	}

	private static final OAuth2Error NO_CLIENT_ID_MATCH = new OAuth2Error(
		"invalid_token", "The client_id does not match", null);

	private final String clientId;

}