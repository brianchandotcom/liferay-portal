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

package com.liferay.portalweb.portal.permissions.messageboards;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SA_DefineMemberRoleTest extends BaseTestCase {
	public void testSA_DefineMemberRole() throws Exception {
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
			RuntimeVariables.replace("Message Boards"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='com.liferay.portlet.messageboards.model.MBCategoryADD_FILE']",
			RuntimeVariables.replace("MBCategory Add File"));
		selenium.clickAt("//input[@value='com.liferay.portlet.messageboards.model.MBCategoryADD_MESSAGE']",
			RuntimeVariables.replace("MBCategory Add Message"));
		selenium.clickAt("//input[@value='com.liferay.portlet.messageboards.model.MBCategoryREPLY_TO_MESSAGE']",
			RuntimeVariables.replace("MBCategory Reply to Message"));
		selenium.clickAt("//input[@value='com.liferay.portlet.messageboards.model.MBCategorySUBSCRIBE']",
			RuntimeVariables.replace("MBCategory Subscribe"));
		selenium.clickAt("//input[@value='com.liferay.portlet.messageboards.model.MBCategoryVIEW']",
			RuntimeVariables.replace("MBCategory View"));
		selenium.clickAt("//input[@value='com.liferay.portlet.messageboards.model.MBMessageSUBSCRIBE']",
			RuntimeVariables.replace("MBMessage Subscribe"));
		selenium.clickAt("//input[@value='com.liferay.portlet.messageboards.model.MBMessageVIEW']",
			RuntimeVariables.replace("MBMessage View"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"The role permissions were updated."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}