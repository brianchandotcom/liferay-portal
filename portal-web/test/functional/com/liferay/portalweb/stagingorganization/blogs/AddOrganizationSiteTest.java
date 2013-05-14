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
public class AddOrganizationSiteTest extends BaseTestCase {
	public void testAddOrganizationSite() throws Exception {
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
		selenium.type("//input[@id='_125_keywords']",
			RuntimeVariables.replace("Organization*"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.click("//span[@title='Actions']/ul/li/strong/a/span");
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]",
			RuntimeVariables.replace("Edit"));
		selenium.waitForVisible("//a[@id='_125_organizationSiteLink']");
		assertTrue(selenium.isPartialText(
				"//a[@id='_125_organizationSiteLink']", "Organization Site"));
		selenium.clickAt("//a[@id='_125_organizationSiteLink']",
			RuntimeVariables.replace("Organization Site"));
		assertFalse(selenium.isChecked("//input[@id='_125_siteCheckbox']"));
		selenium.clickAt("//input[@id='_125_siteCheckbox']",
			RuntimeVariables.replace("Create Site"));
		assertTrue(selenium.isChecked("//input[@id='_125_siteCheckbox']"));
		selenium.waitForVisible(
			"//select[@id='_125_publicLayoutSetPrototypeId']");
		selenium.select("//select[@id='_125_publicLayoutSetPrototypeId']",
			RuntimeVariables.replace("Community Site"));
		selenium.waitForVisible(
			"//input[@id='_125_publicLayoutSetPrototypeLinkEnabledCheckbox']");
		assertTrue(selenium.isChecked(
				"//input[@id='_125_publicLayoutSetPrototypeLinkEnabledCheckbox']"));
		selenium.clickAt("//input[@id='_125_publicLayoutSetPrototypeLinkEnabledCheckbox']",
			RuntimeVariables.replace(
				"Enable propagation of changes from the site template Community Site."));
		assertFalse(selenium.isChecked(
				"//input[@id='_125_publicLayoutSetPrototypeLinkEnabledCheckbox']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}