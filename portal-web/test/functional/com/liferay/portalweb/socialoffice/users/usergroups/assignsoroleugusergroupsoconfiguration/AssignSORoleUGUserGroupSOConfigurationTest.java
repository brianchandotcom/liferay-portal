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

package com.liferay.portalweb.socialoffice.users.usergroups.assignsoroleugusergroupsoconfiguration;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssignSORoleUGUserGroupSOConfigurationTest extends BaseTestCase {
	public void testAssignSORoleUGUserGroupSOConfiguration()
		throws Exception {
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
		selenium.clickAt("link=Social Office Configurations",
			RuntimeVariables.replace("Social Office Configurations"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//li/span/a[contains(.,'User Groups')]",
			RuntimeVariables.replace("User Groups"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible(
			"//input[contains(@id,'soconfigurationsportlet_keywords')]");
		selenium.type("//input[contains(@id,'soconfigurationsportlet_keywords')]",
			RuntimeVariables.replace("UG UserGroup Name"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("UG UserGroup Name"),
			selenium.getText(
				"//td[contains(@id,'soconfigurationsportlet_userGroupsSearchContainer_col-name_row-1')]"));
		assertFalse(selenium.isChecked(
				"//input[contains(@name,'soconfigurationsportlet_allRowIds')]"));
		selenium.clickAt("//input[contains(@name,'soconfigurationsportlet_allRowIds')]",
			RuntimeVariables.replace("Select All"));
		assertTrue(selenium.isChecked(
				"//input[contains(@name,'soconfigurationsportlet_allRowIds')]"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}