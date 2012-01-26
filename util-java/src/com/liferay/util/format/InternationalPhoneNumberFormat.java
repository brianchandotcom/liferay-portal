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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Brian Wing Shun Chan
 * @author Manuel de la Peña
 */
public class InternationalPhoneNumberFormat implements PhoneNumberFormat {

	public String format(String phoneNumber) {
		return phoneNumber;
	}

	public String strip(String phoneNumber) {
		if (Validator.isNull(phoneNumber)) {
			return phoneNumber;
		}

		StringBuilder sb = new StringBuilder(phoneNumber);
		String temporaryPhoneNumber = sb.toString();

		// Remove valid characters

		for (int i = 0;i < VALID_CHARACTERS.length;i++) {
			temporaryPhoneNumber = temporaryPhoneNumber.replace(
				VALID_CHARACTERS[i], "");
		}

		return temporaryPhoneNumber;
	}

	public boolean validate(String phoneNumber) {
		if (Validator.isNull(phoneNumber)) {
			return false;
		}

		int plusPosition = phoneNumber.indexOf(StringPool.PLUS);

		if (plusPosition > 0) {
			return false;
		}
		else {
			int start = plusPosition + 1;

			String strippedPhoneNumber = strip(phoneNumber.substring(start));

			if (Validator.isNull(strippedPhoneNumber)) {
				return false;
			}

			for (int i = start;i < strippedPhoneNumber.length(); i++) {
				if (!Character.isDigit(strippedPhoneNumber.charAt(i))) {
					return false;
				}
			}
		}

		return true;
	}

	private static final String VALID_CHARACTERS[] = {
		StringPool.CLOSE_PARENTHESIS,
		StringPool.DASH,
		StringPool.OPEN_PARENTHESIS,
		StringPool.PERIOD,
		StringPool.SPACE};

}