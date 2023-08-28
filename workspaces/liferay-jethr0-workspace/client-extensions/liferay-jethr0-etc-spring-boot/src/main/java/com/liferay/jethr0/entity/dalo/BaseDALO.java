/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.entity.dalo;

import com.liferay.client.extension.util.spring.boot.LiferayOAuth2AccessTokenManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public abstract class BaseDALO implements DALO {

	@Override
	public String getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			_liferayJethr0HeadlessServerOAuthApplicationExternalReferenceCode);
	}

	@Override
	public void refresh() {
		_liferayOAuth2AccessTokenManager.refresh(
			_liferayJethr0HeadlessServerOAuthApplicationExternalReferenceCode);
	}

	@Value(
		"${liferay.jethr0.headless.server.oauth.application.external.reference.code}"
	)
	private String
		_liferayJethr0HeadlessServerOAuthApplicationExternalReferenceCode;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}