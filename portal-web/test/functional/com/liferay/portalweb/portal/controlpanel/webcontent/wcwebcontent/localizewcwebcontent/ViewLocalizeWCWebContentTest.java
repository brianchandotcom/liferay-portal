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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.localizewcwebcontent;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewLocalizeWCWebContentTest extends BaseTestCase {
	public void testViewLocalizeWCWebContent() throws Exception {
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
		selenium.type("//input[@id='_15_keywords']",
			RuntimeVariables.replace("WC WebContent Title"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForVisible(
			"//div[@data-title='WC WebContent Title']/a/span");
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//div[@data-title='WC WebContent Title']/a/span"));
		selenium.clickAt("//div[@data-title='WC WebContent Title']/a/span",
			RuntimeVariables.replace("WC WebContent Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Spanish (Spain)"),
			selenium.getText(
				"//div[@id='_15_availableTranslationContainer']/span/a"));
		selenium.clickAt("//div[@id='_15_availableTranslationContainer']/span/a",
			RuntimeVariables.replace("Spanish (Spain)"));
		selenium.waitForVisible(
			"//iframe[contains(@src,'_15_toLanguageId=es_ES')]");
		selenium.selectFrame(
			"//iframe[contains(@src,'_15_toLanguageId=es_ES')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-panel/aui-panel-min.js')]");
		Thread.sleep(1000);
		selenium.waitForVisible(
			"//div[@id='_15_availableTranslationContainer']");
		assertEquals(RuntimeVariables.replace(
				"Translating Web Content to Spanish (Spain)"),
			selenium.getText("//div[@id='_15_availableTranslationContainer']"));
		assertEquals("WC WebContent T\u00edtulo",
			selenium.getValue("//input[@id='_15_title_es_ES']"));
		selenium.waitForVisible(
			"//a[contains(@class,'cke_button cke_button__cut') and contains(@class,'cke_button_disabled')]");
		selenium.waitForVisible("//iframe[contains(@title,'Rich Text Editor')]");
		selenium.selectFrame("//iframe[contains(@title,'Rich Text Editor')]");
		assertEquals(RuntimeVariables.replace("WC WebContent Contenido"),
			selenium.getText("//body"));
		selenium.selectFrame("relative=top");
		selenium.selectFrame(
			"//iframe[contains(@src,'_15_toLanguageId=es_ES')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-panel/aui-panel-min.js')]");
		selenium.selectFrame("relative=top");
	}
}