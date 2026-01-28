/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.service;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.client.extension.util.spring.boot3.service.BaseService;
import com.liferay.notification.rest.client.dto.v1_0.NotificationTemplate;
import com.liferay.notification.rest.client.resource.v1_0.NotificationTemplateResource;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * @author Ryan Schuhler
 */
@Component
public class NotificationTemplateService extends BaseService {

	public JSONObject getAndProcessTemplate(
			String externalReferenceCode, JSONObject placeholdersJSONObject)
		throws Exception {

		NotificationTemplateResource.Builder builder =
			NotificationTemplateResource.builder();

		NotificationTemplateResource notificationTemplateResource =
			builder.endpoint(
				_lxcDXPMainDomain, _lxcDXPServerProtocol
			).header(
				HttpHeaders.AUTHORIZATION, _getAuthorization()
			).build();

		NotificationTemplate notificationTemplate =
			notificationTemplateResource.
				getNotificationTemplateByExternalReferenceCode(
					externalReferenceCode);

		Map<String, String> bodyMap =
			(Map<String, String>)notificationTemplate.getBody();

		String body = bodyMap.get("en_US");

		Map<String, String> subjectMap =
			(Map<String, String>)notificationTemplate.getSubject();

		String subject = subjectMap.get("en_US");

		for (String key : placeholdersJSONObject.keySet()) {
			String placeholder = "[%" + key + "%]";

			body = StringUtil.replace(
				body, placeholder, placeholdersJSONObject.getString(key));
			subject = StringUtil.replace(
				subject, placeholder, placeholdersJSONObject.getString(key));
		}

		JSONObject processedTemplateJSONObject = new JSONObject();

		processedTemplateJSONObject.put(
			"body", body
		).put(
			"subject", subject
		);

		return processedTemplateJSONObject;
	}

	private String _getAuthorization() {
		return _liferayOAuth2AccessTokenManager.getAuthorization(
			"liferay-customer-etc-spring-boot-oahs");
	}

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

}