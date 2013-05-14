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
public class ViewEditUserRolesOrgOwnerOrg3Test extends BaseTestCase {
	public void testViewEditUserRolesOrgOwnerOrg3() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
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

				boolean basicVisible = selenium.isVisible(
						"//a[.='\u00ab Basic']");

				if (!basicVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//a[.='\u00ab Basic']",
					RuntimeVariables.replace("Basic"));
				selenium.waitForVisible("//input[@name='_125_keywords']");

			case 2:
				selenium.type("//input[@name='_125_keywords']",
					RuntimeVariables.replace("usersn"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]");
				assertEquals(RuntimeVariables.replace("Edit"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isPartialText("//a[@id='_125_rolesLink']",
						"Roles"));
				selenium.clickAt("//a[@id='_125_rolesLink']",
					RuntimeVariables.replace("Roles"));
				selenium.waitForVisible("//h1[@class='header-title']/span");
				assertEquals(RuntimeVariables.replace("userfn userln"),
					selenium.getText("//h1[@class='header-title']/span"));
				assertEquals(RuntimeVariables.replace("\u00ab Back"),
					selenium.getText("//a[@id='_125_TabsBack']"));
				assertEquals(RuntimeVariables.replace("Regular Roles"),
					selenium.getText("//div[@id='_125_roles']/h3"));
				assertEquals(RuntimeVariables.replace("Organization Roles"),
					selenium.getText("//div[@id='_125_roles']/h3[3]"));
				assertEquals(RuntimeVariables.replace("Title"),
					selenium.getText(
						"//th[@id='_125_organizationRolesSearchContainer_col-title']"));
				assertEquals(RuntimeVariables.replace("Organization"),
					selenium.getText(
						"//th[@id='_125_organizationRolesSearchContainer_col-organization']"));
				assertEquals(RuntimeVariables.replace("Organization Owner"),
					selenium.getText(
						"//td[@id='_125_organizationRolesSearchContainer_col-title_row-3']"));
				assertEquals(RuntimeVariables.replace("Organization3 Name"),
					selenium.getText(
						"//td[@id='_125_organizationRolesSearchContainer_col-organization_row-3']"));
				assertEquals(RuntimeVariables.replace("Remove"),
					selenium.getText(
						"//td[@id='_125_organizationRolesSearchContainer_col-3_row-3']"));

			case 100:
				label = -1;
			}
		}
	}
}