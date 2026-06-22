/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.http.client;

import com.liferay.osb.faro.engine.client.constants.OSBAsahHeaderConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * @author Marcellus Tavares
 */
public class AuthorClientHttpRequestInterceptor
	implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(
			HttpRequest httpRequest, byte[] bytes,
			ClientHttpRequestExecution clientHttpRequestExecution)
		throws IOException {

		_addAuthorHeaders(httpRequest);

		return clientHttpRequestExecution.execute(httpRequest, bytes);
	}

	private void _addAuthorHeaders(HttpRequest httpRequest) {
		User user = _getUser();

		if (user == null) {
			return;
		}

		HttpHeaders httpHeaders = httpRequest.getHeaders();

		httpHeaders.add(
			OSBAsahHeaderConstants.AUTHOR_USER_ID,
			String.valueOf(user.getUserId()));
		httpHeaders.add(
			OSBAsahHeaderConstants.AUTHOR_USER_NAME, user.getFullName());
	}

	private User _getUser() {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker == null) {
			return null;
		}

		return permissionChecker.getUser();
	}

}