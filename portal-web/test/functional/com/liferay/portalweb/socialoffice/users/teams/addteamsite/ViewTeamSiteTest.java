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
public class ViewTeamSiteTest extends BaseTestCase {
	public void testViewTeamSite() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Open Site Team Name"),
			selenium.getText("//tr[contains(.,'Open Site Team Name')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Open Site Team Description"),
			selenium.getText("//tr[contains(.,'Open Site Team Name')]/td[2]/a"));
		selenium.clickAt("//tr[contains(.,'Open Site Team Name')]/td[1]/a",
			RuntimeVariables.replace("Open Site Team Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Open Site Name: Open Site Team Name"),
			selenium.getText("//h1[@class='header-title']"));
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
			RuntimeVariables.replace("Site2"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Site2 Name"),
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
		assertEquals(RuntimeVariables.replace("No teams were found."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertTrue(selenium.isElementNotPresent(
				"//tr[contains(.,'Open Site Team Name')]/td[1]/a"));
		assertTrue(selenium.isElementNotPresent(
				"//tr[contains(.,'Open Site Team Name')]/td[2]/a"));
		assertFalse(selenium.isTextPresent("Open Site Team Name"));
		assertFalse(selenium.isTextPresent("Open Site Team Description"));
	}
}