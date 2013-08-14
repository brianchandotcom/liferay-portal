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

import com.liferay.portalweb.portal.util.SendEmail;

import org.junit.Test;

/**
 * @author Kwang Lee
 */
public class ReplyEmailTestCase extends BaseSeleniumTestCase {

	@Test
	public void testReplyEmail() throws Exception {
		SendEmail email = new SendEmail();
		email.send(
			"kwanglee.test@gmail.com", "l33kw4ng", "kwanglee.test1@gmail.com",
			"Email Test", "This is a test message");
		selenium.pause("1500");
		selenium.connect("kwanglee.test1@gmail.com", "l33kw4ng");
		selenium.replyEmail("kwanglee.test@gmail.com", "This is a reply");
		selenium.deleteEmails();
		selenium.connect("kwanglee.test@gmail.com", "l33kw4ng");
		selenium.getContent("1");
		assertEquals(selenium.getContent("1"), "This is a reply");
		selenium.deleteEmails();
	}

}