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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.SystemProperties;

/**
 * @author Brian Wing Shun Chan
 * @author Manuel de la Peña
 */
public class PhoneNumberUtil {

	public static String format(String phoneNumber) {
		PhoneNumberFormat phoneNumberFormat = _getPhoneNumberFormat();

		return phoneNumberFormat.format(phoneNumber);
	}

	public static String strip(String phoneNumber) {
		PhoneNumberFormat phoneNumberFormat = _getPhoneNumberFormat();

		return phoneNumberFormat.strip(phoneNumber);
	}

	public static boolean validate(String phoneNumber) {
		PhoneNumberFormat phoneNumberFormat = _getPhoneNumberFormat();

		return phoneNumberFormat.validate(phoneNumber);
	}

	private static PhoneNumberFormat _getPhoneNumberFormat() {
		if (_phoneNumberFormat == null) {
			try {
				String className = GetterUtil.getString(
					SystemProperties.get(PhoneNumberFormat.class.getName()),
					USAPhoneNumberFormat.class.getName());

				_phoneNumberFormat =
					(PhoneNumberFormat) InstanceFactory.newInstance(className);
			}
			catch (Exception e) {
				_log.error(e, e);

				_phoneNumberFormat = new USAPhoneNumberFormat();
			}
		}

		return _phoneNumberFormat;
	}

	private static Log _log = LogFactoryUtil.getLog(PhoneNumberUtil.class);

	private static PhoneNumberFormat _phoneNumberFormat;

}