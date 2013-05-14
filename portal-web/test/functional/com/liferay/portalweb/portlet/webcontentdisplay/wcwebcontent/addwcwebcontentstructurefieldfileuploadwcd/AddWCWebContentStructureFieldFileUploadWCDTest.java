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

package com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent.addwcwebcontentstructurefieldfileuploadwcd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWCWebContentStructureFieldFileUploadWCDTest extends BaseTestCase {
	public void testAddWCWebContentStructureFieldFileUploadWCD()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Web Content Display Test Page",
			RuntimeVariables.replace("Web Content Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText(
				"//span[@class='icon-action icon-action-add']/a/span"));
		selenium.clickAt("//span[@class='icon-action icon-action-add']/a/span",
			RuntimeVariables.replace("Add"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText(
				"//div[@class='column-content article-structure-content ']/fieldset/div/div/span[2]/a"));
		selenium.clickAt("//div[@class='column-content article-structure-content ']/fieldset/div/div/span[2]/a",
			RuntimeVariables.replace("Select"));
		selenium.waitForVisible(
			"//iframe[contains(@src,'_15_selectStructure')]");
		selenium.selectFrame("//iframe[contains(@src,'_15_selectStructure')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		Thread.sleep(1000);
		selenium.waitForVisible("//input[@name='_166_keywords']");
		selenium.type("//input[@name='_166_keywords']",
			RuntimeVariables.replace("WC Structure File Upload Name"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		Thread.sleep(1000);
		selenium.waitForVisible(
			"//tr[contains(.,'WC Structure File Upload Name')]/td[2]");
		assertEquals(RuntimeVariables.replace("WC Structure File Upload Name"),
			selenium.getText(
				"//tr[contains(.,'WC Structure File Upload Name')]/td[2]"));
		assertEquals(RuntimeVariables.replace(
				"WC Structure File Upload Description"),
			selenium.getText(
				"//tr[contains(.,'WC Structure File Upload Name')]/td[3]"));
		selenium.clickAt("//tr[contains(.,'WC Structure File Upload Name')]/td[5]/span/span/input[@value='Choose']",
			RuntimeVariables.replace("Choose"));
		selenium.waitForConfirmation(
			"Selecting a new structure will change the available input fields and available templates? Do you want to proceed?");
		selenium.selectFrame("relative=top");
		selenium.waitForText("//span[@id='_15_structureNameLabel']",
			"WC Structure File Upload Name");
		assertEquals(RuntimeVariables.replace("WC Structure File Upload Name"),
			selenium.getText("//span[@id='_15_structureNameLabel']"));
		selenium.waitForText("//span[@class='template-name-label']",
			"WC Template Structure File Upload Name");
		assertEquals(RuntimeVariables.replace(
				"WC Template Structure File Upload Name"),
			selenium.getText("//span[@class='template-name-label']"));
		Thread.sleep(1000);
		selenium.type("//input[@id='_15_title_en_US']",
			RuntimeVariables.replace(
				"WC WebContent Structure File Upload Title"));
		assertEquals(RuntimeVariables.replace("File Upload"),
			selenium.getText("//label[contains(@for,'_15_fileupload')]"));
		selenium.uploadCommonFile("//input[contains(@id,'_15_fileupload')]",
			RuntimeVariables.replace("Document_1.txt"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Document_1.txt"),
			selenium.getText("//div[@class='journal-content-article']/p"));
	}
}