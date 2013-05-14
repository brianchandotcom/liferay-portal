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

package com.liferay.portalweb.portal.controlpanel.usergroups.ugusergroup.deleteugusergroupuser;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDeleteUGUserGroupUserTest extends BaseTestCase {
	public void testViewDeleteUGUserGroupUser() throws Exception {
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
		assertEquals(RuntimeVariables.replace("UG UserGroup Name"),
			selenium.getText("//tr[3]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Assign Members')]/a");
		assertEquals(RuntimeVariables.replace("Assign Members"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Assign Members')]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Assign Members')]/a"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Current"),
			selenium.getText("//ul[@class='tabview-list']/li/span/a/strong"));
		assertTrue(selenium.isChecked("//input[@name='_127_allRowIds']"));
		assertTrue(selenium.isChecked("//input[@name='_127_rowIds']"));
		assertEquals(RuntimeVariables.replace("userfn userln"),
			selenium.getText("//tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("usersn"),
			selenium.getText("//tr[3]/td[3]"));
	}
}