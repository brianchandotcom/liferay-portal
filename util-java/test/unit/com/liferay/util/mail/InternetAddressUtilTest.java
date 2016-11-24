/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.util.mail;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Miguel Pastor
 * @see    com.liferay.petra.mail.InternetAddressUtilTest
 */
public class InternetAddressUtilTest {

	@Before
	public void setUp() throws AddressException {
		_internetAddresses = buildInternetAddresses(11);
	}

	@Test
	public void testContainsNullEmailAddress() {
		Assert.assertFalse(
			InternetAddressUtil.contains(_internetAddresses, null));
	}

	@Test
	public void testContainsValidEmailAddress() {
		Assert.assertTrue(
			InternetAddressUtil.contains(_internetAddresses, "1@liferay.com"));
	}

	@Test
	public void testInvalidEmailAddress() {
		Assert.assertFalse(InternetAddressUtil.isValid("miguel.pastor"));
	}

	@Test
	public void testInvalidEmailAddressWithAt() {
		Assert.assertFalse(InternetAddressUtil.isValid("miguel.pastor@"));
	}

	@Test
	public void testNotContainsValidEmailAddress() {
		Assert.assertFalse(
			InternetAddressUtil.contains(_internetAddresses, "12@liferay.com"));
	}

	@Test
	public void testRemoveExistingEmailAddress() {
		InternetAddress[] internetAddresses = InternetAddressUtil.removeEntry(
			_internetAddresses, "1@liferay.com");

		Assert.assertEquals(10, internetAddresses.length);
	}

	@Test
	public void testRemoveNonexistentEmailAddress() {
		InternetAddress[] restOfInternetAddresses =
			InternetAddressUtil.removeEntry(
				_internetAddresses, "12@liferay.com");

		Assert.assertEquals(11, restOfInternetAddresses.length);
	}

	@Test
	public void testValidEmailAddress() {
		Assert.assertTrue(
			InternetAddressUtil.isValid("miguel.pastor@liferay.com"));
	}

	protected InternetAddress[] buildInternetAddresses(int size)
		throws AddressException {

		InternetAddress[] internetAddresses = new InternetAddress[size];

		for (int i = 0; i < size; i++) {
			internetAddresses[i] = new InternetAddress(
				String.valueOf(i) + _INTERNET_ADDRESS_SUFFIX);
		}

		return internetAddresses;
	}

	private static final String _INTERNET_ADDRESS_SUFFIX = "@liferay.com";

	private InternetAddress[] _internetAddresses;

}