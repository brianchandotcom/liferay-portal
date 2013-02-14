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

package com.liferay.portalweb.portal.controlpanel.recyclebin.wiki.restorewikinoderecyclebin;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewRestoreWikiNodeRecycleBinTest extends BaseTestCase {
	public void testViewRestoreWikiNodeRecycleBin() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Wiki"),
			selenium.getText(
				"//ul[@class='category-portlets']/li[contains(.,'Wiki')]/a"));
		selenium.clickAt("//ul[@class='category-portlets']/li[contains(.,'Wiki')]/a",
			RuntimeVariables.replace("Wiki"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Wiki"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertTrue(selenium.isPartialText(
				"//div[@id='show-portlet-description-154']",
				"Wiki is a social collaborative encyclopedia that allows users to come together and share knowledge in an area. Administrators can add and edit wiki pages, change permissions and set advanced options."));
		assertTrue(selenium.isVisible("//input[@value='Add Wiki']"));
		assertTrue(selenium.isVisible("//input[@value='Permissions']"));
		assertEquals(RuntimeVariables.replace("Main"),
			selenium.getText("//tr[contains(.,'Main')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText("//tr[contains(.,'Main')]/td[2]/a"));
		assertTrue(selenium.isVisible("//tr[contains(.,'Main')]/td[3]"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'Main')]/td[4]/span/ul/li/strong/a/span"));
		assertEquals(RuntimeVariables.replace("Wiki Node Name"),
			selenium.getText("//tr[contains(.,'Wiki Node Name')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//tr[contains(.,'Wiki Node Name')]/td[2]/a"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'Wiki Node Name')]/td[3]"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'Wiki Node Name')]/td[4]/span/ul/li/strong/a/span"));
		assertEquals(RuntimeVariables.replace("Showing 2 results."),
			selenium.getText("//div[@class='search-results']"));
	}
}