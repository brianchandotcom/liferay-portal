/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.license.validator;

import com.liferay.portal.license.License;
import com.liferay.portal.license.LicenseConstants;

/**
 * @author Amos Fong
 */
public class LimitedValidator extends LicenseValidator {

	@Override
	public void doValidateVersion(License license) throws Exception {
		if (isClustered()) {
			throw new Exception(
				"Clustering has been detected. Limited licenses do not " +
					"allow clustering.");
		}

		validateServer(license);
	}

	@Override
	public String[] getValidTypes() {
		return _VALID_TYPES;
	}

	private static final String[] _VALID_TYPES = {LicenseConstants.TYPE_LIMITED};

}