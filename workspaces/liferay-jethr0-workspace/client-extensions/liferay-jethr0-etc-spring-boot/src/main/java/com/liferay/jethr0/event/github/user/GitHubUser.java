/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github.user;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class GitHubUser {

	public GitHubUser(JSONObject jsonObject) {
		_jsonObject = jsonObject;
	}

	public String getName() {
		return _jsonObject.getString("login");
	}

	private final JSONObject _jsonObject;

}