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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.localizewcstructure;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class LocalizeWCStructureTest extends BaseTestCase {
	public void testLocalizeWCStructure() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Manage"),
			selenium.getText("//span[@title='Manage']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Manage']/ul/li/strong/a",
			RuntimeVariables.replace("Manage"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Structures')]");
		assertEquals(RuntimeVariables.replace("Structures"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Structures')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Structures')]",
			RuntimeVariables.replace("Structures"));
		selenium.waitForVisible("//iframe[contains(@src,'Structures')]");
		selenium.selectFrame("//iframe[contains(@src,'Structures')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/store.js')]");
		selenium.waitForVisible("//tr[contains(.,'WC Structure Name')]/td[3]/a");
		assertEquals(RuntimeVariables.replace("WC Structure Name"),
			selenium.getText("//tr[contains(.,'WC Structure Name')]/td[3]/a"));
		selenium.clickAt("//tr[contains(.,'WC Structure Name')]/td[3]/a",
			RuntimeVariables.replace("WC Structure Name"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible(
			"//span[@class=\"taglib-input-localized\"]/span/a[contains(@id,'languageSelectorTrigger')]");
		assertEquals(RuntimeVariables.replace("Other Languages (0)"),
			selenium.getText(
				"//span[@class=\"taglib-input-localized\"]/span/a[contains(@id,'languageSelectorTrigger')]"));
		selenium.clickAt("//span[@class=\"taglib-input-localized\"]/span/a[contains(@id,'languageSelectorTrigger')]",
			RuntimeVariables.replace("Other Languages (0)"));
		selenium.waitForVisible(
			"//div[@class='lfr-floating-container lfr-language-selector']/div/div/div/div/select[@id='_166_languageId0']");
		selenium.select("//div[@class='lfr-floating-container lfr-language-selector']/div/div/div/div/select[@id='_166_languageId0']",
			RuntimeVariables.replace("value=es_ES"));
		selenium.waitForVisible(
			"//div[@class='lfr-floating-container lfr-language-selector']/div/div/div/div/img[contains(@src,'es_ES')]");
		assertTrue(selenium.isVisible(
				"//div[@class='lfr-floating-container lfr-language-selector']/div/div/div/div/img[contains(@src,'es_ES')]"));
		selenium.type("//div[@class='lfr-floating-container lfr-language-selector']/div/div/div/div/input[@id='_166_name_es_ES']",
			RuntimeVariables.replace("WC Nombre de la estructura"));
		assertEquals(RuntimeVariables.replace("Other Languages (0)"),
			selenium.getText(
				"xPath=(//span[@class=\"taglib-input-localized\"]/span/a[contains(@id,'languageSelectorTrigger')])[2]"));
		selenium.clickAt("xPath=(//span[@class=\"taglib-input-localized\"]/span/a[contains(@id,'languageSelectorTrigger')])[2]",
			RuntimeVariables.replace("Other Languages (0)"));
		selenium.waitForVisible(
			"xPath=(//div[@class='lfr-floating-container lfr-language-selector']/div/div/div/div/select[@id='_166_languageId0'])[2]");
		selenium.select("xPath=(//div[@class='lfr-floating-container lfr-language-selector']/div/div/div/div/select[@id='_166_languageId0'])[2]",
			RuntimeVariables.replace("value=es_ES"));
		selenium.waitForVisible(
			"xPath=(//div[@class='lfr-floating-container lfr-language-selector']/div/div/div/div/img[contains(@src,'es_ES')])[2]");
		assertTrue(selenium.isVisible(
				"xPath=(//div[@class='lfr-floating-container lfr-language-selector']/div/div/div/div/img[contains(@src,'es_ES')])[2]"));
		selenium.type("xPath=(//div[@class='lfr-floating-container lfr-language-selector']/div/div/div/div/textarea[@id='_166_description_es_ES'])[1]",
			RuntimeVariables.replace("WC Descripci\u00f3n Estructura"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("WC Structure Name"),
			selenium.getText("//tr[contains(.,'WC Structure Name')]/td[3]/a"));
		selenium.selectFrame("relative=top");
	}
}