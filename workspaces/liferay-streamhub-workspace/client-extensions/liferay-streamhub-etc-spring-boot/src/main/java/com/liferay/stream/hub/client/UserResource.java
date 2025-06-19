/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.stream.hub.client;

import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.headless.admin.user.client.dto.v1_0.RoleBrief;
import com.liferay.headless.admin.user.client.dto.v1_0.UserAccount;
import com.liferay.headless.admin.user.client.resource.v1_0.UserAccountResource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author Mahmoud Hussein Tayem
 */
@Service
public class UserResource {

	@Cacheable("userRoles")
	public List<String> getUserAccountRoles(Long userId) throws Exception {
		UserAccountResource userAccountResource = UserAccountResource.builder(
		).header(
			"Authorization",
			_liferayOAuth2AccessTokenManager.getAuthorization(
				_mainOauthServerApplicationName)
		).endpoint(
			_lxcDXPMainDomain, _lxcDXPServerProtocol
		).build();

		UserAccount userAccount = userAccountResource.getUserAccount(userId);

		List<String> userRoles = new ArrayList<>();

		for (RoleBrief roleBrief : userAccount.getRoleBriefs()) {
			userRoles.add(roleBrief.getName());
		}

		return Collections.unmodifiableList(userRoles);
	}

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

	@Value("${main.liferay.server.oauth.application}")
	private String _mainOauthServerApplicationName;

}