/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.permissions.webcontent.assertactions.entry.delete;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DefineMemberRoleTest extends BaseTestCase {
	public void testDefineMemberRole() throws Exception {
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
					RuntimeVariables.replace("Member"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Member"),
					selenium.getText("//tr[contains(.,'Member')]/td[1]/a"));
				selenium.clickAt("//tr[contains(.,'Member')]/td[1]/a",
					RuntimeVariables.replace("Member"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Define Permissions",
					RuntimeVariables.replace("Define Permissions"));
				selenium.waitForPageToLoad("30000");
				selenium.select("//select[@id='_128_add-permissions']",
					RuntimeVariables.replace("index=112"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Web Content"),
					selenium.getText("//h3[contains(.,'Web Content')]"));

				boolean webContentControlPanelAccess = selenium.isChecked(
						"//input[@value='15ACCESS_IN_CONTROL_PANEL']");

				if (webContentControlPanelAccess) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@value='15ACCESS_IN_CONTROL_PANEL']",
					RuntimeVariables.replace("Access in Control Panel "));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@value='15ACCESS_IN_CONTROL_PANEL']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"The role permissions were updated."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace("Web Content"),
					selenium.getText("//tr[contains(.,'Web Content')]/td[1]/a"));
				assertEquals(RuntimeVariables.replace(""),
					selenium.getText("//tr[contains(.,'Web Content')]/td[2]"));
				assertEquals(RuntimeVariables.replace("Access in Control Panel"),
					selenium.getText("//tr[contains(.,'Web Content')]/td[3]"));
				assertEquals(RuntimeVariables.replace("Portal"),
					selenium.getText("//tr[contains(.,'Web Content')]/td[4]"));
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//tr[contains(.,'Web Content')]/td[5]/span/a"));

			case 100:
				label = -1;
			}
		}
	}
}