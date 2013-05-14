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

package com.liferay.portalweb.portal.controlpanel.users.user.deactivateuseractions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DeactivateUserActionsTest extends BaseTestCase {
	public void testDeactivateUserActions() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Users and Organizations",
			RuntimeVariables.replace("Users and Organizations"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_125_keywords']",
			RuntimeVariables.replace("usersn"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("userfn"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("userln"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("usersn"),
			selenium.getText("//td[4]/a"));
		assertEquals(RuntimeVariables.replace(""), selenium.getText("//td[5]/a"));
		assertEquals(RuntimeVariables.replace(""), selenium.getText("//td[6]/a"));
		assertEquals(RuntimeVariables.replace(""), selenium.getText("//td[7]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//td[8]/span[@title='Actions']/ul/li/strong/a"));
		selenium.clickAt("//td[8]/span[@title='Actions']/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Deactivate')]");
		assertEquals(RuntimeVariables.replace("Deactivate"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Deactivate')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Deactivate')]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to deactivate this[\\s\\S]$"));
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace(
				"Inactive Users (Search All Users)"),
			selenium.getText("//div[@id='usersAdminUsersPanel']/div/div/span"));
		assertTrue(selenium.isVisible("//input[@value='Delete']"));
		assertTrue(selenium.isVisible("//input[@value='Restore']"));
		assertEquals(RuntimeVariables.replace("userfn"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("userln"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("usersn"),
			selenium.getText("//td[4]/a"));
	}
}