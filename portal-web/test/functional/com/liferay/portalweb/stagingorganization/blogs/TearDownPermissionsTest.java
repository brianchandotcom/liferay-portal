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

package com.liferay.portalweb.stagingorganization.blogs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownPermissionsTest extends BaseTestCase {
	public void testTearDownPermissions() throws Exception {
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
				selenium.clickAt("link=Roles", RuntimeVariables.replace("Roles"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_128_keywords']",
					RuntimeVariables.replace("Organization"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Organization User"),
					selenium.getText(
						"//tr/td[1]/a[contains(.,'Organization User')]"));
				selenium.clickAt("//tr/td[1]/a[contains(.,'Organization User')]",
					RuntimeVariables.replace("Organization User"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Define Permissions",
					RuntimeVariables.replace("Define Permissions"));
				selenium.waitForPageToLoad("30000");
				selenium.select("//select[@id='_128_add-permissions']",
					RuntimeVariables.replace("Blogs"));
				selenium.waitForVisible(
					"//input[@value='com.liferay.portlet.blogsADD_ENTRY']");

				boolean blogsAddEntryChecked = selenium.isChecked(
						"//input[@value='com.liferay.portlet.blogsADD_ENTRY']");

				if (!blogsAddEntryChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@value='com.liferay.portlet.blogsADD_ENTRY']",
					RuntimeVariables.replace("Add Entry"));

			case 2:
				assertFalse(selenium.isChecked(
						"//input[@value='com.liferay.portlet.blogsADD_ENTRY']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"The role permissions were updated."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.clickAt("link=Define Permissions",
					RuntimeVariables.replace("Define Permissions"));
				selenium.waitForPageToLoad("30000");
				selenium.select("//select[@id='_128_add-permissions']",
					RuntimeVariables.replace("Organization Administration"));
				selenium.waitForVisible(
					"//input[@value='com.liferay.portal.model.GroupMANAGE_LAYOUTS']");

				boolean siteManageLayoutsChecked = selenium.isChecked(
						"//input[@value='com.liferay.portal.model.GroupMANAGE_LAYOUTS']");

				if (!siteManageLayoutsChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@value='com.liferay.portal.model.GroupMANAGE_LAYOUTS']",
					RuntimeVariables.replace("Manage Layouts"));

			case 3:
				assertFalse(selenium.isChecked(
						"//input[@value='com.liferay.portal.model.GroupMANAGE_LAYOUTS']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"The role permissions were updated."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				Thread.sleep(1000);
				assertEquals(RuntimeVariables.replace(
						"This role does not have any permissions."),
					selenium.getText("//div[@class='portlet-msg-info']"));
				assertFalse(selenium.isTextPresent("Add Entry"));
				assertFalse(selenium.isTextPresent("Manage Pages"));

			case 100:
				label = -1;
			}
		}
	}
}