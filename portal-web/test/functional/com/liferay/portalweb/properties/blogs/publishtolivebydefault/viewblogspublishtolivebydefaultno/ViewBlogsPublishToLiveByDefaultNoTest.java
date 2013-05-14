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

package com.liferay.portalweb.properties.blogs.publishtolivebydefault.viewblogspublishtolivebydefaultno;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewBlogsPublishToLiveByDefaultNoTest extends BaseTestCase {
	public void testViewBlogsPublishToLiveByDefaultNo()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Sites", RuntimeVariables.replace("Sites"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_134_name']",
			RuntimeVariables.replace("Site"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForText("//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Edit Settings')]/a",
			"Edit Settings");
		assertEquals(RuntimeVariables.replace("Edit Settings"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Edit Settings')]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Edit Settings')]/a"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Staging (Modified)"),
			selenium.getText("//a[@id='_165_stagingLink']"));
		selenium.clickAt("//a[@id='_165_stagingLink']",
			RuntimeVariables.replace("Staging (Modified)"));
		selenium.waitForVisible("//input[@id='_165_local']");
		selenium.clickAt("//input[@id='_165_local']",
			RuntimeVariables.replace("Local Live"));
		assertTrue(selenium.isChecked("//input[@id='_165_local']"));
		selenium.waitForVisible(
			"//input[@id='_165_staged-portlet_161Checkbox']");
		assertTrue(selenium.isChecked(
				"//input[@id='_165_staged-portlet_161Checkbox']"));
	}
}