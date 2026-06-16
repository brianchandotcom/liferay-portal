/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.service;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.client.extension.util.spring.boot3.service.BaseService;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

/**
 * @author Amos Fong
 */
public class OneBaseService extends BaseService {

	protected String getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-one-etc-spring-boot-oahs");
	}

	protected boolean isNotFound(String status) {
		return Objects.equals(HttpStatus.NOT_FOUND.name(), status);
	}

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}