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

package com.liferay.portalweb.portal.permissions.announcements;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SA_AddAATest extends BaseTestCase {
	public void testSA_AddAA() throws Exception {
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
		selenium.select("//select[@id='_125_prefixId']",
			RuntimeVariables.replace("label=Mrs."));
		selenium.type("//input[@id='_125_screenName']",
			RuntimeVariables.replace("AA"));
		selenium.type("//input[@id='_125_emailAddress']",
			RuntimeVariables.replace("AA@liferay.com"));
		selenium.type("//input[@id='_125_firstName']",
			RuntimeVariables.replace("AA"));
		selenium.type("//input[@id='_125_lastName']",
			RuntimeVariables.replace("Liferay"));
		selenium.select("//select[@id='_125_suffixId']",
			RuntimeVariables.replace("Phd."));
		selenium.select("//select[@id='_125_birthdayMonth']",
			RuntimeVariables.replace("July"));
		selenium.select("//select[@id='_125_birthdayDay']",
			RuntimeVariables.replace("26"));
		selenium.select("//select[@id='_125_birthdayYear']",
			RuntimeVariables.replace("1987"));
		selenium.select("//select[@id='_125_male']",
			RuntimeVariables.replace("Female"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//a[@id='_125_passwordLink']",
				"Password"));
		selenium.clickAt("//a[@id='_125_passwordLink']",
			RuntimeVariables.replace("Password"));
		selenium.waitForVisible("//input[@id='_125_password1']");
		selenium.type("//input[@id='_125_password1']",
			RuntimeVariables.replace("password"));
		selenium.type("//input[@id='_125_password2']",
			RuntimeVariables.replace("password"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}