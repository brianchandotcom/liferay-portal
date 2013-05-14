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

package com.liferay.portalweb.socialoffice.users.usergroups.assignrolesousergroup;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RemoveRoleSOUserGroupTest extends BaseTestCase {
	public void testRemoveRoleSOUserGroup() throws Exception {
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
		selenium.clickAt("link=Roles", RuntimeVariables.replace("Roles"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Roles"),
			selenium.getText("//h1[@id='cpPortletTitle']/span"));
		selenium.type("//input[@id='_128_keywords']",
			RuntimeVariables.replace("Social Office"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Social Office User"),
			selenium.getText(
				"//td[@id='_128_ocerSearchContainer_col-name_row-1']"));
		assertEquals(RuntimeVariables.replace("Regular"),
			selenium.getText(
				"//td[@id='_128_ocerSearchContainer_col-type_row-1']"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Assign Members')]/a");
		assertEquals(RuntimeVariables.replace("Assign Members"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Assign Members')]/a"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Assign Members')]/a",
			RuntimeVariables.replace("Assign Members"));
		selenium.waitForVisible("//h1[@class='header-title']");
		assertEquals(RuntimeVariables.replace("Social Office User"),
			selenium.getText("//h1[@class='header-title']"));
		selenium.waitForVisible(
			"//form/ul/li[contains(.,'User Groups')]/span/a");
		assertEquals(RuntimeVariables.replace("User Groups"),
			selenium.getText("//form/ul/li[contains(.,'User Groups')]/span/a"));
		selenium.clickAt("//form/ul/li[contains(.,'User Groups')]/span/a",
			RuntimeVariables.replace("User Groups"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Available", RuntimeVariables.replace("Available"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//tr[3]/td[2]");
		assertEquals(RuntimeVariables.replace("UG UserGroup Name"),
			selenium.getText("//tr[3]/td[2]"));
		assertTrue(selenium.isChecked("//td/input"));
		selenium.uncheck("//td/input");
		assertFalse(selenium.isChecked("//td/input"));
		selenium.clickAt("//input[@value='Update Associations']",
			RuntimeVariables.replace("Update Associations"));
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertFalse(selenium.isChecked("//td/input"));
	}
}