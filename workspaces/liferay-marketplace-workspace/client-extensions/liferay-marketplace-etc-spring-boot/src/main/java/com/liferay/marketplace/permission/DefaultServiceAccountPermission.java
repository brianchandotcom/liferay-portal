/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.permission;

import com.liferay.portal.kernel.security.auth.PrincipalException;

import java.util.Objects;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

/**
 * @author Keven Leone
 */
@Component
public class DefaultServiceAccountPermission {

	public void check(Jwt jwt) throws Exception {
		if (!contains(jwt)) {
			throw new PrincipalException("Unauthorized");
		}
	}

	public boolean contains(Jwt jwt) {
		return Objects.equals(
			jwt.getClaim("username"), "default-service-account");
	}

}