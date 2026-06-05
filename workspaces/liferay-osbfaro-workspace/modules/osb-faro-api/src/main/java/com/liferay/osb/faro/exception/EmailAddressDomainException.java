/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.exception;

import java.util.Collection;
import java.util.Collections;

/**
 * @author André Miranda
 */
public class EmailAddressDomainException extends RuntimeException {

	public EmailAddressDomainException(String message) {
		super(message);

		_invalidEmailAddressDomains = Collections.emptyList();
	}

	public EmailAddressDomainException(
		String message, Collection<String> invalidEmailAddressDomains) {

		super(message);

		_invalidEmailAddressDomains = invalidEmailAddressDomains;
	}

	public Collection<String> getInvalidEmailAddressDomains() {
		return _invalidEmailAddressDomains;
	}

	private final Collection<String> _invalidEmailAddressDomains;

}