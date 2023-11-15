/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.zendesk.model;

import org.json.JSONObject;

/**
 * @author Amos Fong
 */
public class ZendeskUser {

	public static final String ROLE_END_USER = "end-user";

	public ZendeskUser(JSONObject jsonObject) {
		_email = jsonObject.getString("email");
		_role = jsonObject.getString("role");
		_zendeskUserId = jsonObject.getLong("id");
	}

	public String getEmail() {
		return _email;
	}

	public String getRole() {
		return _role;
	}

	public long getZendeskUserId() {
		return _zendeskUserId;
	}

	public boolean isEndUser() {
		if (_role.equals(ROLE_END_USER)) {
			return true;
		}

		return false;
	}

	private final String _email;
	private final String _role;
	private final long _zendeskUserId;

}