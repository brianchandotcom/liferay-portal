/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.util;

/**
 * @author Ivica Cardic
 */
public class AccessTokenExpiresInUtil {

	public static long getExpiresIn() {
		Long expiresIn = _expiresIn.get();

		if (expiresIn == null) {
			return 0;
		}

		return expiresIn;
	}

	public static void removeExpiresIn() {
		_expiresIn.remove();
	}

	public static void setExpiresIn(long expiresIn) {
		_expiresIn.set(expiresIn);
	}

	private static final ThreadLocal<Long> _expiresIn = new ThreadLocal<>();

}