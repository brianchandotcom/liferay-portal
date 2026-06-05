/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.contacts.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Shinn Lok
 */
public class InvalidContactsCardTemplateSizeException extends PortalException {

	public InvalidContactsCardTemplateSizeException() {
	}

	public InvalidContactsCardTemplateSizeException(String msg) {
		super(msg);
	}

	public InvalidContactsCardTemplateSizeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public InvalidContactsCardTemplateSizeException(Throwable throwable) {
		super(throwable);
	}

}