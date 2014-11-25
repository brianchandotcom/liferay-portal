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

package com.liferay.portalweb.selenium;

import com.liferay.portalweb.util.TestPropsValues;

import org.junit.Test;

/**
 * @author Kwang Lee
 */
public class AssertEmailSubjectTestCase extends BaseSeleniumTestCase {

	@Test
	public void testAssertEmailSubject() throws Exception {
		selenium.connectToEmailAccount(
			TestPropsValues.EMAIL_ADDRESS_1, TestPropsValues.EMAIL_PASSWORD_1);

		selenium.sendEmail(
			TestPropsValues.EMAIL_ADDRESS_2, "Email Test",
			"This is a test message.");

		selenium.deleteAllEmails();

		selenium.connectToEmailAccount(
			TestPropsValues.EMAIL_ADDRESS_2, TestPropsValues.EMAIL_PASSWORD_2);

		selenium.assertEmailSubject("1", "Email Test");

		selenium.deleteAllEmails();
	}

	@Test
	public void testFailAssertEmailSubject1() throws Exception {
		try {
			selenium.connectToEmailAccount(
				TestPropsValues.EMAIL_ADDRESS_1,
				TestPropsValues.EMAIL_PASSWORD_1);

			selenium.sendEmail(
				TestPropsValues.EMAIL_ADDRESS_2, "Email Test",
				"This is a test message.");

			selenium.deleteAllEmails();

			selenium.connectToEmailAccount(
				TestPropsValues.EMAIL_ADDRESS_2,
				TestPropsValues.EMAIL_PASSWORD_2);

			selenium.assertEmailSubject("A", "Email Test");
		}
		catch (Throwable t) {
			assertTrue(t.getMessage() == null);
		}
	}

	@Test
	public void testFailAssertEmailSubject2() throws Exception {
		try {
			selenium.connectToEmailAccount(
				TestPropsValues.EMAIL_ADDRESS_1,
				TestPropsValues.EMAIL_PASSWORD_1);

			selenium.sendEmail(
				TestPropsValues.EMAIL_ADDRESS_2, "Email Test",
				"This is a test message.");

			selenium.deleteAllEmails();

			selenium.connectToEmailAccount(
				TestPropsValues.EMAIL_ADDRESS_2,
				TestPropsValues.EMAIL_PASSWORD_2);

			selenium.assertEmailSubject("1", "Wrong Subject");
		}
		catch (Throwable t) {
			assertEquals(
				t.getMessage(),
				"Expected \"Wrong Subject\" but saw \"Email Test\" instead");
		}
	}

}