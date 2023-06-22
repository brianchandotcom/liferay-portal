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
import com.liferay.portal.license.LicenseManager;
import com.liferay.portal.util.LicenseUtil;

/**
 * @author Amos Fong
 */
public class InstanceSizeValidator extends LicenseValidator {

	@Override
	public void doValidateVersion(License license) throws Exception {
		String instanceSize = license.getInstanceSize();

		if (Validator.isNull(instanceSize)) {
			return;
		}

		int maxProcessorCores = license.getMaxProcessorCores();

		if (maxProcessorCores <= 0) {
			return;
		}

		int processorCores = LicenseUtil.getProcessorCores();

		if (processorCores > maxProcessorCores) {
			throw new Exception(
				"You have exceeded the maximum number of processor cores " +
					"allowed for this server: " + maxProcessorCores);
		}
	}

	@Override
	public String[] getValidTypes() {
		return _VALID_TYPES;
	}

	private static final String[] _VALID_TYPES = {
		LicenseConstants.TYPE_VIRTUAL_CLUSTER, LicenseConstants.TYPE_PRODUCTION
	};

}