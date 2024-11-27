/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.service;

import com.liferay.client.extension.util.spring.boot.BaseRestController;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

/**
 * @author Felipe Franca
 */
@Component
public class UserAccountService extends BaseRestController {

	public ArrayList<String> getUserRoles(Jwt jwt) {
		JSONObject userJSONObject = _getMyUserAccountJSONObject(jwt);

		JSONArray roleBriefsJSONArray = userJSONObject.getJSONArray(
			"roleBriefs");

		ArrayList<String> rolesNames = new ArrayList<>();

		for (int i = 0; i < roleBriefsJSONArray.length(); i++) {
			JSONObject roleBriefJSONObject = roleBriefsJSONArray.getJSONObject(
				i);

			String roleName = roleBriefJSONObject.getString("name");

			rolesNames.add(roleName);
		}

		return rolesNames;
	}

	private JSONObject _getMyUserAccountJSONObject(Jwt jwt) {
		try {
			return new JSONObject(
				get(
					"Bearer " + jwt.getTokenValue(),
					"/o/headless-admin-user/v1.0/my-user-account"));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to fetch user account", exception);
			}
		}

		return null;
	}

	private static final Log _log = LogFactory.getLog(UserAccountService.class);

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

}