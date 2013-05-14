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

package com.liferay.portalweb.portlet.documentsandmedia.dmdocumenttype.adddmdocumenttype;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddDMDocumentType2Test extends BaseTestCase {
	public void testAddDMDocumentType2() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Manage"),
			selenium.getText("//span[@title='Manage']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Manage']/ul/li/strong/a",
			RuntimeVariables.replace("Manage"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Document Types')]");
		assertEquals(RuntimeVariables.replace("Document Types"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Document Types')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Document Types')]",
			RuntimeVariables.replace("Document Types"));
		selenium.waitForVisible("//iframe[@id='_20_openFileEntryTypeView']");
		selenium.selectFrame("//iframe[@id='_20_openFileEntryTypeView']");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible("//span[contains(@class,'add-button')]/a");
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[contains(@class,'add-button')]/a"));
		selenium.clickAt("//span[contains(@class,'add-button')]/a",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible("//input[@id='_20_name']");
		selenium.type("//input[@id='_20_name']",
			RuntimeVariables.replace("DM DocumentType2 Name"));
		selenium.waitForVisible(
			"xPath=(//div[@class='diagram-builder-field-label'])[13]");
		selenium.dragAndDropToObject("xPath=(//div[@class='diagram-builder-field-label'])[13]",
			"xPath=(//div[@class='diagram-builder-field-label'])[13]");
		selenium.waitForVisible(
			"//div[contains(@class,'aui-form-builder-text-field-content')]/label");
		assertEquals(RuntimeVariables.replace("Text Box"),
			selenium.getText(
				"//div[contains(@class,'aui-form-builder-text-field-content')]/label"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("DM DocumentType2 Name"),
			selenium.getText("//tr[contains(.,'DM DocumentType2 Name')]/td[1]"));
		selenium.selectFrame("relative=top");
	}
}