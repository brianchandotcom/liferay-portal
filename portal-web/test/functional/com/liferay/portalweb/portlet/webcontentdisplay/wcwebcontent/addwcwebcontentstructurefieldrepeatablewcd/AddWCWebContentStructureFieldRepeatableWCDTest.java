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

package com.liferay.portalweb.portlet.webcontentdisplay.wcwebcontent.addwcwebcontentstructurefieldrepeatablewcd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWCWebContentStructureFieldRepeatableWCDTest extends BaseTestCase {
	public void testAddWCWebContentStructureFieldRepeatableWCD()
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
				"//div[@class='aui-column-content article-structure-content ']/fieldset/div/div/span[2]/a"));
		selenium.clickAt("//div[@class='aui-column-content article-structure-content ']/fieldset/div/div/span[2]/a",
			RuntimeVariables.replace("Select"));
		selenium.waitForVisible(
			"//iframe[contains(@src,'_15_selectStructure')]");
		selenium.selectFrame("//iframe[contains(@src,'_15_selectStructure')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		Thread.sleep(1000);
		selenium.waitForVisible("//input[@name='_166_keywords']");
		selenium.type("//input[@name='_166_keywords']",
			RuntimeVariables.replace("WC Structure Repeatable Name"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForVisible(
			"//tr[contains(.,'WC Structure Repeatable Name')]/td[3]/a");
		assertEquals(RuntimeVariables.replace("WC Structure Repeatable Name"),
			selenium.getText(
				"//tr[contains(.,'WC Structure Repeatable Name')]/td[3]/a"));
		selenium.clickAt("//tr[contains(.,'WC Structure Repeatable Name')]/td[3]/a",
			RuntimeVariables.replace("WC Structure Repeatable Name"));
		selenium.waitForConfirmation(
			"Selecting a new structure will change the available input fields and available templates? Do you want to proceed?");
		selenium.selectFrame("relative=top");
		Thread.sleep(5000);
		selenium.waitForVisible("//span[@id='_15_structureNameLabel']");
		assertEquals(RuntimeVariables.replace("WC Structure Repeatable Name"),
			selenium.getText("//span[@id='_15_structureNameLabel']"));
		assertEquals(RuntimeVariables.replace(
				"WC Template Structure Repeatable Name"),
			selenium.getText("//span[@class='template-name-label']"));
		selenium.type("//input[@id='_15_title_en_US']",
			RuntimeVariables.replace("WC WebContent Structure Repeatable Title"));
		selenium.type("//input[contains(@id,'_15_repeatable_')]",
			RuntimeVariables.replace("WC Structure Repeatable Text1"));
		assertTrue(selenium.isVisible(
				"//a[@class='lfr-ddm-repeatable-add-button']"));
		selenium.clickAt("//a[@class='lfr-ddm-repeatable-add-button']",
			RuntimeVariables.replace("Repeatable Button"));
		selenium.waitForVisible(
			"xPath=(//input[contains(@id,'repeatable_')])[2]");
		assertTrue(selenium.isVisible(
				"xPath=(//a[@class='lfr-ddm-repeatable-add-button'])[2]"));
		selenium.clickAt("xPath=(//a[@class='lfr-ddm-repeatable-add-button'])[2]",
			RuntimeVariables.replace("Repeatable Button"));
		selenium.waitForVisible(
			"xPath=(//input[contains(@id,'repeatable_')])[3]");
		assertTrue(selenium.isVisible(
				"xPath=(//a[@class='lfr-ddm-repeatable-add-button'])[3]"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC Structure Repeatable Text1"),
			selenium.getText("//div[@class='journal-content-article']/p"));
	}
}