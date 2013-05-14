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

package com.liferay.portalweb.socialoffice.users.teams.addteamsite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssignSOUser1TeamSiteTest extends BaseTestCase {
	public void testAssignSOUser1TeamSite() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Sites", RuntimeVariables.replace("Sites"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_134_name']",
			RuntimeVariables.replace("Open Site "));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Open Site Name"),
			selenium.getText("//tr[3]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Manage Memberships')]/a");
		assertEquals(RuntimeVariables.replace("Manage Memberships"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Manage Memberships')]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Manage Memberships')]/a"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("View Teams"),
			selenium.getText("//span[contains(@class,'teams-button')]/a"));
		selenium.clickAt("//span[contains(@class,'teams-button')]/a",
			RuntimeVariables.replace("View Teams"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_174_name']",
			RuntimeVariables.replace("Open Site "));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Open Site Team Name"),
			selenium.getText("//tr[3]/td[1]/a"));
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Assign Members')]/a");
		assertEquals(RuntimeVariables.replace("Assign Members"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Assign Members')]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Assign Members')]/a"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Users"),
			selenium.getText("//ul/li[contains(.,'Users')]/span/a"));
		selenium.clickAt("//ul/li[contains(.,'Users')]/span/a",
			RuntimeVariables.replace("Users"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Available"),
			selenium.getText("link=Available"));
		selenium.clickAt("link=Available", RuntimeVariables.replace("Available"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='_174_keywords']",
			RuntimeVariables.replace("socialoffice01"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText(
				"//td[@id='_174_usersSearchContainer_col-name_row-socialoffice01']"));
		assertFalse(selenium.isChecked(
				"//td[@id='_174_usersSearchContainer_col-rowChecker_row-socialoffice01']/input"));
		selenium.clickAt("//td[@id='_174_usersSearchContainer_col-rowChecker_row-socialoffice01']/input",
			RuntimeVariables.replace("Social01 Office01 User01 checkbox"));
		assertTrue(selenium.isChecked(
				"//td[@id='_174_usersSearchContainer_col-rowChecker_row-socialoffice01']/input"));
		selenium.clickAt("//input[@value='Update Associations']",
			RuntimeVariables.replace("Update Associations"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText(
				"//td[@id='_174_usersSearchContainer_col-name_row-socialoffice01']"));
	}
}