/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.util;

/**
 * @author Ivica Cardic
 */
public class AccessTokenExpiresInUtil {

	public static long getExpiresIn() {
		return _expiresIn;
	}

	public static void removeExpiresIn() {
		_expiresIn = 0;
	}

	public static void setExpiresIn(long expiresIn) {
		_expiresIn = expiresIn;
	}

	private static long _expiresIn;

}