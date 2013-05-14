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

package com.liferay.portalweb.portal.controlpanel.organizations.organizationteam.editorganizationteam;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditOrganizationTeamTest extends BaseTestCase {
	public void testEditOrganizationTeam() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Liferay"),
			selenium.getText("//a[contains(@id,'groupSelectorButton')]/span"));
		selenium.clickAt("//a[contains(@id,'groupSelectorButton')]/span",
			RuntimeVariables.replace("Site Selector"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Organization Name')]");
		assertEquals(RuntimeVariables.replace("Organization Name"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Organization Name')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Organization Name')]"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Site Memberships",
			RuntimeVariables.replace("Site Memberships"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Organization Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		selenium.clickAt("link=View Teams",
			RuntimeVariables.replace("View Teams"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Organization Name: Manage Memberships"),
			selenium.getText("//h1[@class='header-title']/span"));
		selenium.type("//input[@id='_174_name']",
			RuntimeVariables.replace("Organization Team Name"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Organization Team Name"),
			selenium.getText("//tr[contains(.,'Organization Team Name')]/td[1]"));
		assertEquals(RuntimeVariables.replace("Organization Team Description"),
			selenium.getText("//tr[contains(.,'Organization Team Name')]/td[2]"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'Organization Team Name')]/td[3]/span[@title='Actions']/ul/li/strong/a"));
		selenium.clickAt("//tr[contains(.,'Organization Team Name')]/td[3]/span[@title='Actions']/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_174_name']",
			RuntimeVariables.replace("Organization Team Name Edit"));
		selenium.type("//textarea[@id='_174_description']",
			RuntimeVariables.replace("Organization Team Description Edit"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.type("//input[@id='_174_name']",
			RuntimeVariables.replace("Organization Team Name Edit"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Organization Team Name Edit"),
			selenium.getText(
				"//tr[contains(.,'Organization Team Name Edit')]/td[1]"));
		assertEquals(RuntimeVariables.replace(
				"Organization Team Description Edit"),
			selenium.getText(
				"//tr[contains(.,'Organization Team Name Edit')]/td[2]"));
	}
}