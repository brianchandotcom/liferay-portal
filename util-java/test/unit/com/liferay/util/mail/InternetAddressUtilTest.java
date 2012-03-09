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

package com.liferay.util.mail;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Miguel Pastor
 */
public class InternetAddressUtilTest {

	@Before
	public void setUp() throws AddressException {
		_internetAddresses = buildEmailAddresses(11);
	}

	@Test
	public void testContainsNullEmailAddress() throws AddressException {
		Assert.assertFalse(
			InternetAddressUtil.contains(_internetAddresses, null));
	}

	@Test
	public void testContainsValidEmailAddress() throws AddressException {
		Assert.assertTrue(
			InternetAddressUtil.contains(_internetAddresses, "1@liferay.com"));
	}

	@Test
	public void testNotContainsValidEmailAddress() throws AddressException {
		Assert.assertFalse(
			InternetAddressUtil.contains(_internetAddresses, "12@liferay.com"));
	}

	@Test
	public void testNoValidEmail() {
		Assert.assertFalse(InternetAddressUtil.isValid("miguel.pastor"));
	}

	@Test
	public void testNoValidEmailWithAt() {
		Assert.assertFalse(InternetAddressUtil.isValid("miguel.pastor@"));
	}

	@Test
	public void testRemoveExistingEmailAddress() throws AddressException {
		InternetAddress[] restOfInternetAddresses =
			InternetAddressUtil.removeEntry(
				_internetAddresses, "1@liferay.com");

		Assert.assertEquals(10, restOfInternetAddresses.length);
	}

	@Test
	public void testRemoveNonExistingEmailAddress() throws AddressException {
		InternetAddress[] restOfInternetAddresses =
			InternetAddressUtil.removeEntry(
				_internetAddresses, "12@liferay.com");

		Assert.assertEquals(11, restOfInternetAddresses.length);
	}

	@Test
	public void testValidEmail() {
		Assert.assertTrue(
			InternetAddressUtil.isValid("miguel.pastor@liferay.com"));
	}

	protected InternetAddress[] buildEmailAddresses(
		int numOfAddress) throws AddressException {

		InternetAddress[] internetAddresses = new InternetAddress[numOfAddress];

		for (int i=0; i < numOfAddress; i++) {
			internetAddresses[i] = new InternetAddress(
				String.valueOf(i) + _INTERNET_ADDRESS_SUFFIX);
		}

		return internetAddresses;
	}

	private static final String _INTERNET_ADDRESS_SUFFIX = "@liferay.com";

	private InternetAddress[] _internetAddresses;

}