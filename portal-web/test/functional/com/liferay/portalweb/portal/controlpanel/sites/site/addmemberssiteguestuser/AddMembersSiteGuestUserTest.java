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

package com.liferay.portalweb.portal.controlpanel.sites.site.addmemberssiteguestuser;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddMembersSiteGuestUserTest extends BaseTestCase {
	public void testAddMembersSiteGuestUser() throws Exception {
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
				selenium.clickAt("link=Sites", RuntimeVariables.replace("Sites"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@name='_134_keywords']",
					RuntimeVariables.replace("guest"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Liferay"),
					selenium.getText("//tr[contains(.,'Liferay')]/td[2]/a"));
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Memberships')]");
				assertEquals(RuntimeVariables.replace("Manage Memberships"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Memberships')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Memberships')]",
					RuntimeVariables.replace("Manage Memberships"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Add Members"),
					selenium.getText(
						"//span[@title='Add Members']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Add Members']/ul/li/strong/a/span",
					RuntimeVariables.replace("Add Members"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'User')]");
				assertEquals(RuntimeVariables.replace("User"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'User')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'User')]",
					RuntimeVariables.replace("User"));
				selenium.waitForPageToLoad("30000");

				boolean basicVisible = selenium.isVisible(
						"//a[.='\u00ab Basic']");

				if (!basicVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//a[.='\u00ab Basic']",
					RuntimeVariables.replace("\u00ab Basic"));

			case 2:
				selenium.type("//input[@name='_174_keywords']",
					RuntimeVariables.replace("userfn"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("userfn userln"),
					selenium.getText("//tr[contains(.,'userfn userln')]/td[2]"));

				boolean entryCheckbox = selenium.isChecked(
						"//input[@name='_174_rowIds']");

				if (entryCheckbox) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@name='_174_rowIds']",
					RuntimeVariables.replace("Entry Check Box"));

			case 3:
				assertTrue(selenium.isChecked("//input[@name='_174_rowIds']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.type("//input[@name='_174_keywords']",
					RuntimeVariables.replace("userfn"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("userfn userln"),
					selenium.getText("//tr[contains(.,'userfn userln')]/td[1]"));

			case 100:
				label = -1;
			}
		}
	}
}