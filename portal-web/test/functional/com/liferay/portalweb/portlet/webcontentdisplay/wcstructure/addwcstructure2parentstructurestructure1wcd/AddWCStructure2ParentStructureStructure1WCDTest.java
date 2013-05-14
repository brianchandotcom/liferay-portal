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

package com.liferay.portalweb.portlet.webcontentdisplay.wcstructure.addwcstructure2parentstructurestructure1wcd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWCStructure2ParentStructureStructure1WCDTest
	extends BaseTestCase {
	public void testAddWCStructure2ParentStructureStructure1WCD()
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
				"//a[@class=' taglib-icon']/span[contains(.,'Select')]"));
		selenium.clickAt("//a[@class=' taglib-icon']/span[contains(.,'Select')]",
			RuntimeVariables.replace("Select"));
		selenium.waitForVisible("//iframe[contains(@src,'selectStructure')]");
		selenium.selectFrame("//iframe[contains(@src,'selectStructure')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible(
			"//span[@class='lfr-toolbar-button add-button ']/a");
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText(
				"//span[@class='lfr-toolbar-button add-button ']/a"));
		selenium.clickAt("//span[@class='lfr-toolbar-button add-button ']/a",
			RuntimeVariables.replace("Add"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Select']",
			RuntimeVariables.replace("Select"));
		selenium.selectFrame("relative=top");
		selenium.waitForVisible(
			"//iframe[contains(@src,'selectParentStructure')]");
		selenium.selectFrame("//iframe[contains(@src,'selectParentStructure')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible(
			"//tr[contains(.,'WC Structure1 Name')]/td[2]/a");
		assertEquals(RuntimeVariables.replace("WC Structure1 Name"),
			selenium.getText("//tr[contains(.,'WC Structure1 Name')]/td[2]/a"));
		selenium.clickAt("//tr[contains(.,'WC Structure1 Name')]/td[2]/a",
			RuntimeVariables.replace("WC Structure1 Name"));
		selenium.selectFrame("relative=top");
		selenium.waitForVisible("//iframe[contains(@src,'selectStructure')]");
		selenium.selectFrame("//iframe[contains(@src,'selectStructure')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForText("//a[@id='_166_parentStructureName']",
			"WC Structure1 Name");
		selenium.type("//input[@id='_166_name_en_US']",
			RuntimeVariables.replace("WC Structure2 Name"));
		selenium.type("//textarea[@id='_166_description_en_US']",
			RuntimeVariables.replace("WC Structure2 Description"));
		assertEquals(RuntimeVariables.replace("HTML"),
			selenium.getText(
				"//div[@class='tabview-content widget-bd']/div/ul/li/div[contains(.,'HTML')]"));
		selenium.dragAndDropToObject("//div[@class='tabview-content widget-bd']/div/ul/li/div[contains(.,'HTML')]",
			"//div[@class='tabview-content widget-bd']");
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC Structure2 Name')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("WC Structure2 Name"),
			selenium.getText("//tr[contains(.,'WC Structure2 Name')]/td[3]/a"));
		selenium.selectFrame("relative=top");
	}
}