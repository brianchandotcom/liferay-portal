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

package com.liferay.portalweb.portal.controlpanel.sites.site.addcommunitysite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddCommunitySiteTest extends BaseTestCase {
	public void testAddCommunitySite() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[@title='Add']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Add']/ul/li/strong/a/span",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Community Site')]");
		assertEquals(RuntimeVariables.replace("Community Site"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Community Site')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Community Site')]",
			RuntimeVariables.replace("Community Site"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_134_name']",
			RuntimeVariables.replace("Community Site Name"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Community Site Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		selenium.clickAt("link=Sites", RuntimeVariables.replace("Sites"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='_134_keywords']",
			RuntimeVariables.replace("Community"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'Community Site Name')]/td[1]/input[@type='checkbox']"));
		assertEquals(RuntimeVariables.replace("Community Site Name"),
			selenium.getText("//tr[contains(.,'Community Site Name')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Open"),
			selenium.getText("//tr[contains(.,'Community Site Name')]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("1 User"),
			selenium.getText("//tr[contains(.,'Community Site Name')]/td[4]"));
		assertEquals(RuntimeVariables.replace("Yes"),
			selenium.getText("//tr[contains(.,'Community Site Name')]/td[5]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText("//tr[contains(.,'Community Site Name')]/td[6]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText("//tr[contains(.,'Community Site Name')]/td[7]"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'Community Site Name')]/td[8]/span[@title='Actions']/ul/li/strong/a/span"));
	}
}