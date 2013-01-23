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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcfolder.editwcfolder;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewEditWCFolderTest extends BaseTestCase {
	public void testViewEditWCFolder() throws Exception {
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
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText("//div[@class='body-row']/div/div/ul/li[1]/a[2]"));
		assertEquals(RuntimeVariables.replace("Recent"),
			selenium.getText("//div[@class='body-row']/div/div/ul/li[2]/a"));
		assertEquals(RuntimeVariables.replace("Mine"),
			selenium.getText("//div[@class='body-row']/div/div/ul/li[3]/a"));
		assertTrue(selenium.isVisible(
				"//div[@class='toolbar']/span[1]/span/span/input[2]"));
		assertFalse(selenium.isVisible(
				"//div[@class='toolbar']/span[2]/ul/li/strong/a"));
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText(
				"//div[@class='toolbar']/span[3]/span/ul/li/strong/a"));
		assertEquals(RuntimeVariables.replace("Manage"),
			selenium.getText(
				"//div[@class='toolbar']/span[5]/span/ul/li/strong/a"));
		assertTrue(selenium.isVisible(
				"//div[@class='display-style']/span/span/button[1]"));
		assertTrue(selenium.isVisible(
				"//div[@class='display-style']/span/span/button[2]"));
		assertTrue(selenium.isVisible(
				"//div[@class='display-style']/span/span/button[3]"));
		assertTrue(selenium.isVisible(
				"//div[contains(@class,'search-button-container')]/form/div/div/div[1]/div/span/span/span/input"));
		assertEquals("Search",
			selenium.getValue(
				"//div[contains(@class,'search-button-container')]/form/div/div/div[2]/div/span/span/input"));
		assertTrue(selenium.isVisible(
				"//div[contains(@class,'search-button-container')]/form/div/div/div[3]/div/span/span/input"));
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText(
				"//div[@id='_15_breadcrumbContainer']/ul/li/span/a"));
		assertTrue(selenium.isVisible(
				"//div/a[contains(.,'WC Folder Edited')]/div/img"));
		assertEquals(RuntimeVariables.replace("WC Folder Edited"),
			selenium.getText("//div/a[contains(.,'WC Folder Edited ')]/span"));
		assertEquals(RuntimeVariables.replace("<<"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/a[1]"));
		assertEquals(RuntimeVariables.replace("<"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/a[2]"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/span[1]"));
		assertEquals(RuntimeVariables.replace(">"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/a[3]"));
		assertEquals(RuntimeVariables.replace(">>"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/a[4]"));
		assertEquals(RuntimeVariables.replace("1 of 1"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/span[2]"));
		assertEquals(RuntimeVariables.replace("(Total 1)"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/span[3]"));
		assertEquals("20",
			selenium.getSelectedLabel(
				"//div[contains(@class,'article-entries-paginator')]/select"));
	}
}