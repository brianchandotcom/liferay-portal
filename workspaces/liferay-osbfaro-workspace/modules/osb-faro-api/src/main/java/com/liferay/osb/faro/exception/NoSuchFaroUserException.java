/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Matthew Kong
 */
public class NoSuchFaroUserException extends NoSuchModelException {

	public NoSuchFaroUserException() {
	}

	public NoSuchFaroUserException(String msg) {
		super(msg);
	}

	public NoSuchFaroUserException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchFaroUserException(Throwable throwable) {
		super(throwable);
	}

}