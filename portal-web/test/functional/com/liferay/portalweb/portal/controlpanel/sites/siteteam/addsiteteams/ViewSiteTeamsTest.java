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

package com.liferay.portalweb.portal.controlpanel.sites.siteteam.addsiteteams;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewSiteTeamsTest extends BaseTestCase {
	public void testViewSiteTeams() throws Exception {
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
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("//span[@class='header-back-to']/a"));
		assertEquals(RuntimeVariables.replace("View Members"),
			selenium.getText(
				"//div[@class='lfr-portlet-toolbar']/span[@class='lfr-toolbar-button view-button ']"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-portlet-toolbar']/span[@title='Add Members']",
				"Add Members"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-portlet-toolbar']/span[@title='Add Site Roles to']",
				"Add Site Roles to"));
		assertEquals(RuntimeVariables.replace("View Teams"),
			selenium.getText(
				"//div[@class='lfr-portlet-toolbar']/span[@class='lfr-toolbar-button teams-button current']"));
		assertTrue(selenium.isVisible("//input[@id='_174_name']"));
		assertTrue(selenium.isVisible("//input[@value='Search']"));
		assertTrue(selenium.isVisible("//input[@value='Add Team']"));
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[1]"));
		assertEquals(RuntimeVariables.replace("Description"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[2]"));
		assertEquals(RuntimeVariables.replace("Site Team1 Name"),
			selenium.getText("//tr[contains(.,'Site Team1 Name')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Site Team1 Description"),
			selenium.getText("//tr[contains(.,'Site Team1 Name')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'Site Team1 Name')]/td[3]/span[@title='Actions']/ul/li/strong/a/span"));
		assertEquals(RuntimeVariables.replace("Site Team2 Name"),
			selenium.getText("//tr[contains(.,'Site Team2 Name')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Site Team2 Description"),
			selenium.getText("//tr[contains(.,'Site Team2 Name')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'Site Team2 Name')]/td[3]/span[@title='Actions']/ul/li/strong/a/span"));
		assertEquals(RuntimeVariables.replace("Site Team3 Name"),
			selenium.getText("//tr[contains(.,'Site Team3 Name')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Site Team3 Description"),
			selenium.getText("//tr[contains(.,'Site Team3 Name')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'Site Team3 Name')]/td[3]/span[@title='Actions']/ul/li/strong/a/span"));
		assertEquals(RuntimeVariables.replace("Showing 3 results."),
			selenium.getText("//div[@class='search-results']"));
	}
}