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

package com.liferay.portalweb.portal.permissions.messageboards;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Guest_AssertActionsTest extends BaseTestCase {
	public void testGuest_AssertActions() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/site-name/");
		selenium.clickAt("link=Message Boards Permissions Page",
			RuntimeVariables.replace("Message Boards Permissions Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementNotPresent(
				"//input[@value='Add Category']"));
		assertTrue(selenium.isElementNotPresent("//img[@title='Edit']"));
		assertTrue(selenium.isElementNotPresent("//img[@title='Permissions']"));
		assertTrue(selenium.isElementNotPresent("link=Subscribe"));
		assertTrue(selenium.isElementNotPresent("link=Banned Users"));
		assertTrue(selenium.isElementNotPresent("link=Delete"));
		assertEquals(RuntimeVariables.replace("Category Name"),
			selenium.getText("//tr[contains(.,'Category Name')]/td[1]/a"));
		selenium.clickAt("//tr[contains(.,'Category Name')]/td[1]/a",
			RuntimeVariables.replace("Category Name"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementNotPresent(
				"//input[@value='Add Subcategory']"));
		assertTrue(selenium.isElementNotPresent("//img[@alt='Edit']"));
		assertTrue(selenium.isElementNotPresent("//img[@alt='Permissions']"));
		assertTrue(selenium.isElementNotPresent("link=Subscribe"));
		assertTrue(selenium.isElementNotPresent("link=Delete"));
		assertTrue(selenium.isElementNotPresent(
				"//input[@value='Post New Thread']"));
		assertEquals(RuntimeVariables.replace("Thread Subject"),
			selenium.getText("//tr[contains(.,'Thread Subject')]/td[1]/a"));
		selenium.clickAt("//tr[contains(.,'Thread Subject')]/td[1]/a",
			RuntimeVariables.replace("Thread Subject"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Thread Body"),
			selenium.getText("//div[@class='thread-body']"));
		assertTrue(selenium.isTextPresent("Thread Body Reply"));
		assertTrue(selenium.isElementNotPresent("link=Edit"));
		assertTrue(selenium.isElementNotPresent("link=Permissions"));
		assertTrue(selenium.isElementNotPresent("link=Delete"));
		assertTrue(selenium.isElementPresent("link=Sign in to vote."));
		assertTrue(selenium.isElementNotPresent("link=Split Thread"));
	}
}