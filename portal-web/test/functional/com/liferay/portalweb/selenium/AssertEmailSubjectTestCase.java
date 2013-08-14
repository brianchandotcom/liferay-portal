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

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.MailFetching;
import com.liferay.portalweb.portal.util.SendEmail;

import org.junit.Test;

/**
 * @author Kwang Lee
 */
public class AssertEmailSubjectTestCase extends BaseTestCase {

	@Override
	public void tearDown() throws Exception {
	}

	@Test
	public void testAssertEmailSubject() throws Exception {
		SendEmail email = new SendEmail();
		email.send(
			"kwanglee.test@gmail.com", "l33kw4ng", "kwanglee.test1@gmail.com",
			"Email Test", "This is a test message");
		selenium.pause("1000");
		selenium.connect("kwanglee.test1@gmail.com", "l33kw4ng");
		selenium.getSubject("1");
		assertEquals(MailFetching.getSubject("1"), "Email Test");
		selenium.deleteEmails();
	}

}