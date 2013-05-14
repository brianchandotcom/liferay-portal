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

package com.liferay.portalweb.socialoffice.users.user.addsouser;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddSOUser3Test extends BaseTestCase {
	public void testAddSOUser3() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[@title='Add']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Add']/ul/li/strong/a/span",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'User')]");
		assertEquals(RuntimeVariables.replace("User"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'User')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'User')]"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_125_screenName']",
			RuntimeVariables.replace("socialoffice03"));
		selenium.type("//input[@id='_125_emailAddress']",
			RuntimeVariables.replace("socialoffice03@liferay.com"));
		selenium.type("//input[@id='_125_firstName']",
			RuntimeVariables.replace("Social03"));
		selenium.type("//input[@id='_125_middleName']",
			RuntimeVariables.replace("Office03"));
		selenium.type("//input[@id='_125_lastName']",
			RuntimeVariables.replace("User03"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("socialoffice03",
			selenium.getValue("//input[@id='_125_screenName']"));
		assertEquals("socialoffice03@liferay.com",
			selenium.getValue("//input[@id='_125_emailAddress']"));
		assertEquals("Social03",
			selenium.getValue("//input[@id='_125_firstName']"));
		assertEquals("Office03",
			selenium.getValue("//input[@id='_125_middleName']"));
		assertEquals("User03", selenium.getValue("//input[@id='_125_lastName']"));
	}
}