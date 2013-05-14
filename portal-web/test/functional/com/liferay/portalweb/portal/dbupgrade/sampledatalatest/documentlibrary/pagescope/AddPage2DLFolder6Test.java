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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.documentlibrary.pagescope;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPage2DLFolder6Test extends BaseTestCase {
	public void testAddPage2DLFolder6() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/document-library-page-scope-community/");
		selenium.waitForVisible("link=DL Page2 Name");
		selenium.clickAt("link=DL Page2 Name",
			RuntimeVariables.replace("DL Page2 Name"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		selenium.waitForText("//span[@class='portlet-title-text']",
			"Documents and Media (DL Page2 Name)");
		assertEquals(RuntimeVariables.replace(
				"Documents and Media (DL Page2 Name)"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[@title='Add']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Add']/ul/li/strong/a/span",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a");
		assertEquals(RuntimeVariables.replace("Folder"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_20_name']",
			RuntimeVariables.replace("DL Folder6 Name"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("DL Folder2 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[1]"));
		assertEquals(RuntimeVariables.replace("DL Folder3 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[2]"));
		assertEquals(RuntimeVariables.replace("DL Folder4 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[3]"));
		assertEquals(RuntimeVariables.replace("DL Folder5 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[4]"));
		assertEquals(RuntimeVariables.replace("DL Folder6 Name"),
			selenium.getText(
				"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[5]"));
	}
}