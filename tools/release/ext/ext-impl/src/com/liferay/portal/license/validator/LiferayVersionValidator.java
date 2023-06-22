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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.license.License;
import com.liferay.portal.license.LicenseConstants;

/**
 * @author Amos Fong
 */
public class LiferayVersionValidator extends LicenseValidator {

	@Override
	public void doValidateVersion(License license) throws Exception {
		String productId = license.getProductId();

		if (!productId.equals(LicenseConstants.PRODUCT_ID_PORTAL)) {
			return;
		}

		String productVersion = license.getProductVersion();

		if (Validator.isNull(productVersion) ||
			!productVersion.startsWith("7.")) {

			throw new Exception(
				"License with product version " + productVersion +
					" is not suppported.");
		}
	}

}