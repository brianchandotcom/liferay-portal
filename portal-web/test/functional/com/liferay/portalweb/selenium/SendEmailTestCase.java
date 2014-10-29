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
public class SendEmailTestCase extends BaseSeleniumTestCase {

	@Test
	public void testFailSendEmail() throws Exception {
		try {
			selenium.connectToEmailAccount(
				TestPropsValues.EMAIL_ADDRESS_1,
				TestPropsValues.EMAIL_PASSWORD_1);

			selenium.sendEmail(
				"ThisIsNotCorrectEmailAddress", "Email Test",
				"This is a test message.");
		}
		catch (Throwable t) {
			assertEquals(t.getMessage(), "Invalid Addresses");
		}
	}

	@Test
	public void testSendEmail() throws Exception {
		selenium.connectToEmailAccount(
			TestPropsValues.EMAIL_ADDRESS_1, TestPropsValues.EMAIL_PASSWORD_1);

		selenium.sendEmail(
			TestPropsValues.EMAIL_ADDRESS_2, "Email Test",
			"This is a test message.");

		selenium.deleteAllEmails();

		selenium.connectToEmailAccount(
			TestPropsValues.EMAIL_ADDRESS_2, TestPropsValues.EMAIL_PASSWORD_2);

		selenium.deleteAllEmails();
	}

}