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

package com.liferay.util.format;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.SystemProperties;

/**
 * @author Brian Wing Shun Chan
 */
public class PhoneNumberUtil {

	public static String format(String phoneNumber) {
		return _format.format(phoneNumber);
	}

	public static String strip(String phoneNumber) {
		return _format.strip(phoneNumber);
	}

	private static PhoneNumberFormat _format =
		(PhoneNumberFormat)InstancePool.get(PhoneNumberUtil._formatClass);

	private static String _formatClass = GetterUtil.getString(
		SystemProperties.get(PhoneNumberFormat.class.getName()),
		USAPhoneNumberFormat.class.getName());

}