/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.provisioning.client.model;

/**
 * @author Matthew Kong
 */
public class ErrorResponse {

	public String getException() {
		return _exception;
	}

	public void setException(String exception) {
		_exception = exception;
	}

	private String _exception;

}