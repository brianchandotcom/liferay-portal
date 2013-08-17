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

package com.liferay.portalweb.selenium;

import org.junit.Test;

/**
 * @author Kwang Lee
 */
public class SendEmailTestCase extends BaseSeleniumTestCase {

	@Test
	public void testSendEmail() throws Exception {
		String serverEmailAddress = "liferay.qa.server.trunk@gmail.com";
		String serverEmailPassword = "loveispatient";
		String userEmailAddress = "liferay.qa.testing.trunk@gmail.com";
		String userEmailPassword = "loveispatient";

		selenium.connectToEmailAccount(serverEmailAddress, serverEmailPassword);
		selenium.sendEmail(
			userEmailAddress, "Email Test", "This is a test message");
		selenium.deleteAllEmails();

		selenium.connectToEmailAccount(userEmailAddress, userEmailPassword);
		selenium.deleteAllEmails();
	}

}