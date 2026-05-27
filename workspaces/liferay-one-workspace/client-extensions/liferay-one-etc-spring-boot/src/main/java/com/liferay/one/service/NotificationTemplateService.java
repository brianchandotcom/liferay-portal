/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.one.service;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.notification.rest.client.dto.v1_0.NotificationTemplate;
import com.liferay.notification.rest.client.resource.v1_0.NotificationTemplateResource;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * @author Amos Fong
 */
@Component
public class NotificationTemplateService extends BaseService {

	public JSONObject getAndProcessTemplateJSONObject(
			String externalReferenceCode, String languageId,
			Map<String, String> placeholders)
		throws Exception {

		NotificationTemplateResource notificationTemplateResource =
			NotificationTemplateResource.builder(
			).endpoint(
				lxcDXPMainDomain, lxcDXPServerProtocol
			).header(
				HttpHeaders.AUTHORIZATION, _getAuthorization()
			).build();

		NotificationTemplate notificationTemplate =
			notificationTemplateResource.
				getNotificationTemplateByExternalReferenceCode(
					externalReferenceCode);

		String body = _getLocalizedValue(
			(Map<String, String>)notificationTemplate.getBody(), languageId);
		String subject = _getLocalizedValue(
			(Map<String, String>)notificationTemplate.getSubject(), languageId);

		for (Map.Entry<String, String> entry : placeholders.entrySet()) {
			String placeholder = "[%" + entry.getKey() + "%]";

			body = StringUtil.replace(body, placeholder, entry.getValue());
			subject = StringUtil.replace(
				subject, placeholder, entry.getValue());
		}

		return new JSONObject(
		).put(
			"body", body
		).put(
			"subject", subject
		);
	}

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-one-etc-spring-boot-oahs");
	}

	private String _getLocalizedValue(
		Map<String, String> valueMap, String languageId) {

		String value = valueMap.get(languageId);

		if (value != null) {
			return value;
		}

		return valueMap.get(_DEFAULT_LANGUAGE_ID);
	}

	private static final String _DEFAULT_LANGUAGE_ID = "en_US";

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}