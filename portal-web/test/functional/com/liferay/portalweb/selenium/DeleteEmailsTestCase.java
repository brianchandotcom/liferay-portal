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

import org.junit.Test;

/**
 * @author Kwang Lee
 */
public class DeleteEmailsTestCase extends BaseTestCase {

	@Override
	public void tearDown() throws Exception {
	}

	@Test
	public void testDeleteMails() throws Exception {
		selenium.connect("kwanglee.test1@gmail.com", "l33kw4ng");
		selenium.deleteEmails();
		selenium.connect("kwanglee.test@gmail.com", "l33kw4ng");
		selenium.deleteEmails();
	}

}