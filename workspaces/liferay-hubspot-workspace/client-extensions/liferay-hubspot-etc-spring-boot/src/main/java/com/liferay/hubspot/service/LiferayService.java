/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.hubspot.service;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.petra.string.StringBundler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Keven Leone
 * @author Ricardo Mariz
 */
@Component
public class LiferayService extends BaseService {

	public JSONObject getHubSpotContact(long id) {
		return new JSONObject(
			get(
				_liferayOAuth2AccessTokenManager.getAuthorization(
					"liferay-hubspot-etc-spring-boot-oahs"),
				UriComponentsBuilder.fromPath(
					"o/c/h1s4contacts/" + id
				).build(
				).toUri()));
	}

	public void patchHeadlessEntry(String body, String path) {
		patch(
			_liferayOAuth2AccessTokenManager.getAuthorization(
				"liferay-hubspot-etc-spring-boot-oahs"),
			body,
			UriComponentsBuilder.fromPath(
				path
			).build(
			).toUri());

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Headless entry updated: ", body, ", ", path));
		}
	}

	private static final Log _log = LogFactory.getLog(LiferayService.class);

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}