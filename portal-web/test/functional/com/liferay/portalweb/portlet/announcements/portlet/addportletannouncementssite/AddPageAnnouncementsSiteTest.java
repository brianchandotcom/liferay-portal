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

package com.liferay.portalweb.portlet.announcements.portlet.addportletannouncementssite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPageAnnouncementsSiteTest extends BaseTestCase {
	public void testAddPageAnnouncementsSite() throws Exception {
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
		selenium.clickAt("link=Sites", RuntimeVariables.replace("Sites"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='_134_keywords']",
			RuntimeVariables.replace("Site"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Manage Pages')]/a");
		assertEquals(RuntimeVariables.replace("Manage Pages"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Manage Pages')]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Manage Pages')]/a"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("link=Public Pages");
		selenium.clickAt("link=Public Pages",
			RuntimeVariables.replace("Public Pages"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Add Page"),
			selenium.getText(
				"//div[@id='_156_layoutSetToolbar']/span/button[contains(.,'Add Page')]"));
		selenium.clickAt("//div[@id='_156_layoutSetToolbar']/span/button[contains(.,'Add Page')]",
			RuntimeVariables.replace("Add Page"));
		selenium.waitForVisible("//input[@name='_156_name_en_US']");
		selenium.type("//input[@name='_156_name_en_US']",
			RuntimeVariables.replace("Announcements Test Page"));
		selenium.clickAt("//input[@value='Add Page']",
			RuntimeVariables.replace("Add Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.waitForVisible(
			"//ul[@class='tree-container']/li/div/div/a[contains(.,'Announcements Test Page')]");
		assertEquals(RuntimeVariables.replace("Announcements Test Page"),
			selenium.getText(
				"//ul[@class='tree-container']/li/div/div/a[contains(.,'Announcements Test Page')]"));
	}
}