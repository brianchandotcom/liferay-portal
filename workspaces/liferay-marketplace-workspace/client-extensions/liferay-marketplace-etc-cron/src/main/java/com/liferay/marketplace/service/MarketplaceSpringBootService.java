/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.service;

import com.liferay.client.extension.util.spring.boot2.client.LiferayOAuth2AccessTokenManager;
import com.liferay.client.extension.util.spring.boot2.service.BaseService;

import java.net.URL;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Keven Leone
 */
@Component
public class MarketplaceSpringBootService extends BaseService {

	public JSONObject getAvailabilityJSONObject() throws Exception {
		return new JSONObject(
			get(
				_liferayOAuth2AccessTokenManager.getAuthorization(
					_liferayOAuthApplicationExternalReferenceCodes),
				"/trial/availability"));
	}

	public void postMarketplaceProjectsKPI(String body) {
		post(
			_liferayOAuth2AccessTokenManager.getAuthorization(
				_liferayOAuthApplicationExternalReferenceCodes),
			body, "/marketplace/projects/kpi");
	}

	@Override
	protected String getWebClientBaseURL() {
		return _liferayMarketplaceEtcSpringBootURL.toString();
	}

	@Value("${liferay.marketplace.etc.spring.boot.url}")
	private URL _liferayMarketplaceEtcSpringBootURL;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Value("${liferay.oauth.application.external.reference.codes}")
	private String _liferayOAuthApplicationExternalReferenceCodes;

}