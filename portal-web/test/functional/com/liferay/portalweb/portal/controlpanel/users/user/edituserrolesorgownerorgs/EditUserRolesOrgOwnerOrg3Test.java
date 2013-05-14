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

package com.liferay.portalweb.portal.controlpanel.users.user.edituserrolesorgownerorgs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditUserRolesOrgOwnerOrg3Test extends BaseTestCase {
	public void testEditUserRolesOrgOwnerOrg3() throws Exception {
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
		selenium.clickAt("link=Search All Users",
			RuntimeVariables.replace("Search All Users"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("usersn"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("userfn"),
			selenium.getText(
				"//td[@id='_125_usersSearchContainer_col-first-name_row-usersn']/a"));
		assertEquals(RuntimeVariables.replace("userln"),
			selenium.getText(
				"//td[@id='_125_usersSearchContainer_col-last-name_row-usersn']/a"));
		assertEquals(RuntimeVariables.replace("usersn"),
			selenium.getText(
				"//td[@id='_125_usersSearchContainer_col-screen-name_row-usersn']/a"));
		assertEquals(RuntimeVariables.replace(
				"Organization1 Name, Organization2 Name, Organization3 Name"),
			selenium.getText(
				"//td[@id='_125_usersSearchContainer_col-organizations_row-usersn']/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//span[@title='Actions']/ul[@id='_125_usersSearchContainer_usersn_menu']/li/strong/a"));
		selenium.clickAt("//span[@title='Actions']/ul[@id='_125_usersSearchContainer_usersn_menu']/li/strong/a",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//a[@id='_125_rolesLink']", "Roles"));
		selenium.clickAt("//a[@id='_125_rolesLink']",
			RuntimeVariables.replace("Roles"));
		selenium.waitForVisible(
			"//span[contains(.,'Select')]/a[contains(@href,'openOrganizationRoleSelector')]");
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText(
				"//span[contains(.,'Select')]/a[contains(@href,'openOrganizationRoleSelector')]"));
		selenium.clickAt("//span[contains(.,'Select')]/a[contains(@href,'openOrganizationRoleSelector')]",
			RuntimeVariables.replace("Select"));
		Thread.sleep(5000);
		selenium.selectWindow("title=Users and Organizations");
		selenium.waitForVisible(
			"//td[@id='_125_organizationsSearchContainer_col-name_row-3']/a");
		assertEquals(RuntimeVariables.replace("Organization3 Name"),
			selenium.getText(
				"//td[@id='_125_organizationsSearchContainer_col-name_row-3']/a"));
		selenium.clickAt("//td[@id='_125_organizationsSearchContainer_col-name_row-3']/a",
			RuntimeVariables.replace("Organization3 Name"));
		selenium.waitForVisible(
			"//tr[contains(.,'Organization Owner')]/td[@headers='_125_rolesSearchContainer_col-title']/a");
		assertEquals(RuntimeVariables.replace("Organization Owner"),
			selenium.getText(
				"//tr[contains(.,'Organization Owner')]/td[@headers='_125_rolesSearchContainer_col-title']/a"));
		selenium.clickAt("//tr[contains(.,'Organization Owner')]/td[@headers='_125_rolesSearchContainer_col-title']/a",
			RuntimeVariables.replace("Organization Owner"));
		selenium.selectWindow("null");
		selenium.waitForPartialText("//div[@id='_125_organizationRolesSearchContainer']",
			"Organization Owner");
		assertTrue(selenium.isPartialText(
				"//div[@id='_125_organizationRolesSearchContainer']",
				"Organization Owner"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Organization Owner"),
			selenium.getText(
				"//td[@id='_125_organizationRolesSearchContainer_col-title_row-3']"));
		assertEquals(RuntimeVariables.replace("Organization3 Name"),
			selenium.getText(
				"//td[@id='_125_organizationRolesSearchContainer_col-organization_row-3']"));
	}
}