package com.clarity.solution;

import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Represents an account creation request.
 *
 * @author dnebing
 */
public class AccountCreationRequest {

	public AccountCreationRequest(String accountJSON, Jwt jwt) {
		_accountJSON = accountJSON;
		_jwt = jwt;
	}

	public Jwt getJwt() {
		return _jwt;
	}

	public String getAccountJSON() {
		return _accountJSON;
	}

	private final Jwt _jwt;
	private final String _accountJSON;

}