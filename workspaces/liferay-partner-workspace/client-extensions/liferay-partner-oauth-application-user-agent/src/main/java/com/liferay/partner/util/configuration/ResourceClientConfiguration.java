/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.partner.util.configuration;

import com.liferay.object.admin.rest.client.resource.v1_0.ObjectDefinitionResource;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;

/**
 * @author José Abelenda
 */
@Configuration
public class ResourceClientConfiguration {

	public ObjectDefinitionResource getObjectDefinitionResource()
		throws MalformedURLException {

		URL url = new URL(_liferayPortalURL);

		return _objectDefinitionResourceBuilder.header(
			"Authorization", _getBearerToken()
		).endpoint(
			url.getHost(), url.getPort(), url.getProtocol()
		).build();
	}

	private String _getBearerToken() {
		SecurityContext securityContext = SecurityContextHolder.getContext();

		Authentication authentication = securityContext.getAuthentication();

		AbstractOAuth2Token token =
			(AbstractOAuth2Token)authentication.getCredentials();

		return "Bearer " + token.getTokenValue();
	}

	@Value("${liferay.portal.url}")
	private String _liferayPortalURL;

	private final ObjectDefinitionResource.Builder
		_objectDefinitionResourceBuilder = ObjectDefinitionResource.builder();

}