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

package com.liferay.portalweb.portal.permissions.messageboards;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Member_AssertActionsTest extends BaseTestCase {
	public void testMember_AssertActions() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/site-name/");
		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("//input[@value='Search']"));
		assertEquals(RuntimeVariables.replace("RSS"),
			selenium.getText(
				"//div[@class='category-subscription-types']/span/a/span[contains(.,'RSS')]"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Subscribe')]");
		assertEquals(RuntimeVariables.replace("Subscribe"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Subscribe')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Permissions')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Move Thread')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Add Category')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Banned Users')]"));
		assertEquals(RuntimeVariables.replace("Category Name"),
			selenium.getText("//tr[contains(.,'Category Name')]/td[1]/a"));
		selenium.clickAt("//tr[contains(.,'Category Name')]/td[1]/a",
			RuntimeVariables.replace("Category Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Thread Subject"),
			selenium.getText("//tr[contains(.,'Thread Subject')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Subscribe')]");
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'RSS')]",
				"RSS"));
		assertEquals(RuntimeVariables.replace("Subscribe"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Subscribe')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Move')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
		assertTrue(selenium.isVisible("//input[@value='Post New Thread']"));
		selenium.clickAt("//tr[contains(.,'Thread Subject')]/td[1]/a",
			RuntimeVariables.replace("Thread Subject"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText(
				"//ul[@class='edit-controls lfr-component']/li[contains(.,'Reply')]/span/a",
				"Reply"));
		assertEquals(RuntimeVariables.replace("Reply with Quote"),
			selenium.getText(
				"//ul[@class='edit-controls lfr-component']/li[contains(.,'Reply with Quote')]/span/a"));
		assertTrue(selenium.isElementNotPresent(
				"//ul[@class='edit-controls lfr-component']/li[contains(.,'Delete')]/span/a"));
		assertTrue(selenium.isElementNotPresent(
				"//ul[@class='edit-controls lfr-component']/li[contains(.,'Edit')]/span/a"));
		assertTrue(selenium.isElementNotPresent(
				"//ul[@class='edit-controls lfr-component']/li[contains(.,'Permissions')]/span/a"));
	}
}