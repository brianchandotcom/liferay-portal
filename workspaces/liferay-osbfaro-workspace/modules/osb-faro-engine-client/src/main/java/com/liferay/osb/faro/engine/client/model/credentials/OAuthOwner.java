/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model.credentials;

/**
 * @author Shinn Lok
 */
public class OAuthOwner {

	public String getEmailAddress() {
		return _emailAddress;
	}

	public String getName() {
		return _name;
	}

	public void setEmailAddress(String emailAddress) {
		_emailAddress = emailAddress;
	}

	public void setName(String name) {
		_name = name;
	}

	private String _emailAddress;
	private String _name;

}