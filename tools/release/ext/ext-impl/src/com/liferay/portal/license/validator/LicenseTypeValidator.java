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
 * @author Tina Tian
 * @author Amos Fong
 */
public class LicenseTypeValidator extends LicenseValidator {

	@Override
	public void doValidateVersion(License license) throws Exception {
		String licenseEntryType = license.getLicenseEntryType();

		if (licenseEntryType.equals(LicenseConstants.TYPE_DEVELOPER) ||
			licenseEntryType.equals(LicenseConstants.TYPE_DEVELOPER_CLUSTER) ||
			licenseEntryType.equals(LicenseConstants.TYPE_ENTERPRISE) ||
			licenseEntryType.equals(LicenseConstants.TYPE_LIMITED) ||
			licenseEntryType.equals(LicenseConstants.TYPE_OEM) ||
			licenseEntryType.equals(LicenseConstants.TYPE_PER_USER) ||
			licenseEntryType.equals(LicenseConstants.TYPE_PRODUCTION) ||
			licenseEntryType.equals(LicenseConstants.TYPE_VIRTUAL_CLUSTER)) {

			return;
		}
		else {
			throw new Exception("Unknown license type " + licenseEntryType);
		}
	}

}