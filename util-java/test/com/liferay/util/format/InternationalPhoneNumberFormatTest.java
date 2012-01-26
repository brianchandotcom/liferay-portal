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

import com.liferay.portal.kernel.test.TestCase;

/**
 * @author Brian Wing Shun Chan
 * @author Manuel de la Peña
 */
public class InternationalPhoneNumberFormatTest extends TestCase {

	public void testValidate() {
		String phoneNumbers[] = {"+34 91 733 63 43",
			"+55 81 3033 1405", "+49 (0) 6196 773 0680",
			"+36 (1) 786 4575", "+86 (0411) 8812-0855",
			"1-123-456-7890", "1.123.456.7890"};

		for (String phoneNumber : phoneNumbers) {
			testValidate(phoneNumber);
		}
	}

	private void testValidate(String phoneNumber) {
		InternationalPhoneNumberFormat format =
			new InternationalPhoneNumberFormat();

		if (!format.validate(phoneNumber)) {
			fail("Validation for [" + phoneNumber + "] failed");
		}
	}

}