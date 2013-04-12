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

package com.liferay.portalweb.portal.controlpanel.usergroups.ugusergroup.addugusergroupcommunitysite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddUGUserGroupCommunitySiteTest extends BaseTestCase {
	public void testAddUGUserGroupCommunitySite() throws Exception {
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
				selenium.clickAt("link=User Groups",
					RuntimeVariables.replace("User Groups"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("User Groups"),
					selenium.getText("//h1[@class='header-title']/span"));
				assertEquals(RuntimeVariables.replace("Add"),
					selenium.getText(
						"//div[@class='lfr-portlet-toolbar']/span[contains(.,'Add')]/a"));
				selenium.clickAt("//div[@class='lfr-portlet-toolbar']/span[contains(.,'Add')]/a",
					RuntimeVariables.replace("Add"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("New User Group"),
					selenium.getText("//h1[@class='header-title']/span"));
				selenium.type("//input[@id='_127_name']",
					RuntimeVariables.replace("UG UserGroup Name"));
				assertEquals(RuntimeVariables.replace("Public Pages"),
					selenium.getText(
						"//label[@for='_127_publicLayoutSetPrototypeId']"));
				selenium.select("//select[@id='_127_publicLayoutSetPrototypeId']",
					RuntimeVariables.replace("Community Site"));

				boolean propagationEnabledChecked = selenium.isChecked(
						"//input[@id='_127_publicLayoutSetPrototypeLinkEnabledCheckbox']");

				if (!propagationEnabledChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_127_publicLayoutSetPrototypeLinkEnabledCheckbox']",
					RuntimeVariables.replace("Enable Propagation"));

			case 2:
				assertFalse(selenium.isChecked(
						"//input[@id='_127_publicLayoutSetPrototypeLinkEnabledCheckbox']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace("UG UserGroup Name"),
					selenium.getText(
						"//tr[contains(.,'UG UserGroup Name')]/td[2]/a"));

			case 100:
				label = -1;
			}
		}
	}
}