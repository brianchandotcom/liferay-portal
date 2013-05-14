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

package com.liferay.portalweb.portal.permissions.documentsandmedia.portlet.userviewdmportletscopesite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DefinePermissionsRoleViewPortletSiteOffTest extends BaseTestCase {
	public void testDefinePermissionsRoleViewPortletSiteOff()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Roles", RuntimeVariables.replace("Roles"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_128_keywords']",
			RuntimeVariables.replace("RegularRole Name"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("RegularRole Name"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace("Regular"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace(""), selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//td[4]/span/ul/li/strong/a/span"));
		selenium.clickAt("//td[4]/span/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a");
		assertEquals(RuntimeVariables.replace("Define Permissions"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_128_add-permissions']",
			RuntimeVariables.replace("Documents and Media"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Document Library"),
			selenium.getText("//h3"));
		assertFalse(selenium.isChecked(
				"//input[@value='com.liferay.portlet.documentlibraryVIEW']"));
		selenium.clickAt("//input[@value='com.liferay.portlet.documentlibraryVIEW']",
			RuntimeVariables.replace("View"));
		assertTrue(selenium.isChecked(
				"//input[@value='com.liferay.portlet.documentlibraryVIEW']"));
		assertEquals(RuntimeVariables.replace("View"),
			selenium.getText("//tr[11]/td[2]"));
		assertEquals(RuntimeVariables.replace("Portal"),
			selenium.getText("//tr[11]/td[3]"));
		assertEquals(RuntimeVariables.replace("Limit Scope"),
			selenium.getText("//tr[11]/td[4]/span/a/span"));
		selenium.clickAt("//tr[11]/td[4]/span/a/span",
			RuntimeVariables.replace("Limit Scope"));
		selenium.waitForPopUp("site", RuntimeVariables.replace("30000"));
		selenium.selectWindow("title=Roles");
		Thread.sleep(5000);
		selenium.waitForVisible("//tr[3]/td[1]/a");
		assertEquals(RuntimeVariables.replace("Global"),
			selenium.getText("//tr[3]/td[1]/a"));
		selenium.click("//tr[3]/td[1]/a");
		selenium.selectWindow("null");
		Thread.sleep(5000);
		selenium.waitForText("//tr[11]/td[3]/div/span/span/span", "Global");
		assertEquals(RuntimeVariables.replace("Global"),
			selenium.getText("//tr[11]/td[3]/div/span/span/span"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"The role permissions were updated."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}