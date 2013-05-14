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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.groups.usergroup;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssignMembersUserGroupTest extends BaseTestCase {
	public void testAssignMembersUserGroup() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
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
				selenium.clickAt("link=User Groups",
					RuntimeVariables.replace("User Groups"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_127_name']",
					RuntimeVariables.replace("Test"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("User Group Sample Test"),
					selenium.getText("//td[2]/a"));
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a");
				assertEquals(RuntimeVariables.replace("Assign Members"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Available",
					RuntimeVariables.replace("Available"));
				selenium.waitForPageToLoad("30000");

				boolean basicVisible = selenium.isVisible("link=\u00ab Basic");

				if (!basicVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("link=\u00ab Basic",
					RuntimeVariables.replace(""));
				selenium.waitForVisible("//input[@name='_127_keywords']");

			case 2:
				selenium.type("//input[@name='_127_keywords']",
					RuntimeVariables.replace("Joe Bloggs"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");

				boolean userChecked = selenium.isChecked("_127_rowIds");

				if (userChecked) {
					label = 3;

					continue;
				}

				assertFalse(selenium.isChecked("//input[@name='_127_rowIds']"));
				selenium.clickAt("//input[@name='_127_rowIds']",
					RuntimeVariables.replace("Checkbox"));
				assertTrue(selenium.isChecked("//input[@name='_127_rowIds']"));

			case 3:
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace("Update Associations"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace("Joe Bloggs"),
					selenium.getText("//tr[3]/td[2]"));
				assertEquals(RuntimeVariables.replace("joebloggs"),
					selenium.getText("//tr[3]/td[3]"));

			case 100:
				label = -1;
			}
		}
	}
}