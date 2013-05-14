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

package com.liferay.portalweb.portal.controlpanel.sites.siteteam.deletesiteteam;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DeleteSiteTeamTest extends BaseTestCase {
	public void testDeleteSiteTeam() throws Exception {
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
		assertTrue(selenium.isVisible(
				"//a[contains(@id,'groupSelectorButton')]/span"));
		selenium.clickAt("//a[contains(@id,'groupSelectorButton')]/span",
			RuntimeVariables.replace("Site Selector"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Site Name')]");
		assertEquals(RuntimeVariables.replace("Site Name"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Site Name')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Site Name')]",
			RuntimeVariables.replace("Site Name"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Site Memberships",
			RuntimeVariables.replace("Site Memberships"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Site Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		selenium.clickAt("link=View Teams",
			RuntimeVariables.replace("View Teams"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Site Name: Manage Memberships"),
			selenium.getText("//h1[@class='header-title']/span"));
		selenium.type("//input[@id='_174_name']",
			RuntimeVariables.replace("Site Team Name"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Site Team Name"),
			selenium.getText("//tr[contains(.,'Site Team Name')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Site Team Description"),
			selenium.getText("//tr[contains(.,'Site Team Name')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'Site Team Name')]/td[3]/span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//tr[contains(.,'Site Team Name')]/td[3]/span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]");
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]",
			RuntimeVariables.replace("Delete"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertFalse(selenium.isTextPresent("Site Team Name"));
		assertEquals(RuntimeVariables.replace("No teams were found."),
			selenium.getText("//div[@class='portlet-msg-info']"));
	}
}