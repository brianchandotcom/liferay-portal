/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.util;

import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Calendar;

/**
 * @author Brian Wing Shun Chan
 */
public class CreditCard {

	public static String hide(String number) {
		return hide(number, StringPool.STAR);
	}

	public static String hide(String number, String x) {
		if (number == null) {
			return number;
		}

		int numberLen = number.length();

		if (numberLen > 4) {
			StringBundler sb = new StringBundler(numberLen - 3);

			for (int i = 0; i < numberLen - 4; i++) {
				sb.append(x);
			}

			sb.append(number.substring(numberLen - 4, numberLen));

			number = sb.toString();
		}

		return number;
	}

	public static boolean isValidExpirationDate(
		int expirationMonth, int expirationYear) {

		Calendar calendar = CalendarFactoryUtil.getCalendar(
			TimeZoneUtil.getDefault(), LocaleUtil.getDefault());

		if (CalendarUtil.isFuture(expirationMonth, expirationYear)) {
			return true;
		}
		else if ((expirationMonth == calendar.get(Calendar.MONTH)) &&
				 (expirationYear == calendar.get(Calendar.YEAR))) {

			return true;
		}

		return false;
	}

	public static boolean isValidNumber(String number, String type) {
		number = StringUtil.extractDigits(number);

		if (type.equals("visa")) {
			if (!number.startsWith("4")) {
				return false;
			}

			if ((number.length() != 13) && (number.length() != 16)) {
				return false;
			}
		}
		else if (type.equals("mastercard")) {
			if (!number.startsWith("51") &&
				!number.startsWith("52") &&
				!number.startsWith("53") &&
				!number.startsWith("54") &&
				!number.startsWith("55")) {

				return false;
			}

			if (number.length() != 16) {
				return false;
			}
		}
		else if (type.equals("discover")) {
			if (!number.startsWith("6011")) {
				return false;
			}

			if (number.length() != 16) {
				return false;
			}
		}
		else if (type.equals("amex")) {
			if (!number.startsWith("34") &&
				!number.startsWith("35") &&
				!number.startsWith("36") &&
				!number.startsWith("37")) {

				return false;
			}

			if (number.length() != 15) {
				return false;
			}
		}

		return Validator.isLUHN(number);
	}

}