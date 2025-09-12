/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

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

	public String getAccountJSON() {
		return _accountJSON;
	}

	public Jwt getJwt() {
		return _jwt;
	}

	private final String _accountJSON;
	private final Jwt _jwt;

}