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

package com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.documentlibrary.pagescope;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.BrowserCommands;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DownloadPage2DLFolder2Document2Test extends BaseTestCase {
	public void testDownloadPage2DLFolder2Document2() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/document-library-page-scope-community/");
		selenium.waitForVisible("link=DL Page2 Name");
		selenium.clickAt("link=DL Page2 Name",
			RuntimeVariables.replace("DL Page2 Name"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//span[@class='portlet-title-text']",
			"Documents and Media (DL Page2 Name)");
		assertEquals(RuntimeVariables.replace(
				"Documents and Media (DL Page2 Name)"),
			selenium.getText("//span[@class='portlet-title-text']"));
		selenium.type("//input[@id='_20_keywords']",
			RuntimeVariables.replace("Document2"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForText("//a[contains(@class,'document-link')]/span[@class='entry-title']",
			"DL Folder2 Document2 Title.xls");
		assertEquals(RuntimeVariables.replace("DL Folder2 Document2 Title.xls"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		Thread.sleep(5000);
		selenium.clickAt("//span[2]/span/ul/li/strong/a",
			RuntimeVariables.replace("Actions Arrow"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a");
		assertEquals(RuntimeVariables.replace("Download (6.5k)"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a"));
		selenium.click("//div[@class='lfr-menu-list unstyled']/ul/li[1]/a");
		BrowserCommands.downloadTempFile("DL_Folder2_Document2_Title.xls");
		Thread.sleep(5000);
		selenium.open("/web/document-library-page-scope-community/");
		selenium.waitForVisible("link=DL Page1 Name");
		selenium.clickAt("link=DL Page1 Name",
			RuntimeVariables.replace("DL Page1 Name"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//span[@class='portlet-title-text']",
			"Documents and Media");
		assertEquals(RuntimeVariables.replace("Documents and Media"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[@title='Add']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Add']/ul/li/strong/a/span",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a");
		assertEquals(RuntimeVariables.replace("Basic Document"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a"));
		selenium.waitForPageToLoad("30000");
		selenium.uploadTempFile("//input[@id='_20_file']",
			RuntimeVariables.replace("DL_Folder2_Document2_Title.xls"));
		selenium.type("//input[@id='_20_title']",
			RuntimeVariables.replace("Temp_DL_Folder2_Document2_Title.xls"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.open("/web/document-library-page-scope-community/");
		selenium.waitForVisible("link=DL Page1 Name");
		selenium.clickAt("link=DL Page1 Name",
			RuntimeVariables.replace("DL Page1 Name"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_20_keywords']",
			RuntimeVariables.replace("Temp_DL_Folder2_Document2_Title.xls"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForText("//span[@class='keywords']",
			"Searched for Temp_DL_Folder2_Document2_Title.xls everywhere.");
		assertEquals(RuntimeVariables.replace(
				"Temp_DL_Folder2_Document2_Title.xls"),
			selenium.getText(
				"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
		selenium.clickAt("//a[contains(@class,'document-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("Temp_DL_Folder2_Document2_Title.xls"));
		selenium.waitForText("//h1[@class='header-title']/span",
			"Temp_DL_Folder2_Document2_Title.xls");
		assertEquals(RuntimeVariables.replace(
				"Temp_DL_Folder2_Document2_Title.xls"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Download (6.5k)"),
			selenium.getText("//span[@class='download-document']/span/a/span"));
		assertEquals(RuntimeVariables.replace("6.5k"),
			selenium.getText("//tr[3]/td[3]"));
		selenium.open("/web/document-library-page-scope-community/");
		selenium.waitForVisible("link=DL Page1 Name");
		selenium.clickAt("link=DL Page1 Name",
			RuntimeVariables.replace("DL Page1 Name"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_20_keywords']",
			RuntimeVariables.replace("Temp_DL_Folder2_Document2_Title.xls"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForText("//span[@class='keywords']",
			"Searched for Temp_DL_Folder2_Document2_Title.xls everywhere.");
		assertFalse(selenium.isChecked("//input[@id='_20_allRowIdsCheckbox']"));
		selenium.clickAt("//input[@id='_20_allRowIdsCheckbox']",
			RuntimeVariables.replace("All Rows"));
		assertTrue(selenium.isChecked("//input[@id='_20_allRowIdsCheckbox']"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a");
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.getConfirmation()
						   .matches("^Are you sure you want to delete the selected entries[\\s\\S]$"));
	}
}