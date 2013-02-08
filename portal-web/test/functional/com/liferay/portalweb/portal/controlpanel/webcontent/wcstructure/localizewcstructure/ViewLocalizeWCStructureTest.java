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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.localizewcstructure;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewLocalizeWCStructureTest extends BaseTestCase {
	public void testViewLocalizeWCStructure() throws Exception {
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
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Structures')]");
		assertEquals(RuntimeVariables.replace("Structures"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Structures')]"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Structures')]",
			RuntimeVariables.replace("Structures"));
		selenium.waitForVisible("//iframe[contains(@src,'Structures')]");
		selenium.selectFrame("//iframe[contains(@src,'Structures')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/store.js')]");
		assertEquals(RuntimeVariables.replace("WC Structure Name"),
			selenium.getText("//tr[contains(.,'WC Structure Name')]/td[3]/a"));
		selenium.clickAt("//tr[contains(.,'WC Structure Name')]/td[3]/a",
			RuntimeVariables.replace("WC Structure Name"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Other Languages (1)"),
			selenium.getText(
				"xPath=(//a[contains(.,'Other Languages (1)')])[1]"));
		selenium.clickAt("xPath=(//a[contains(.,'Other Languages (1)')])[1]",
			RuntimeVariables.replace("Other Languages (1)"));
		selenium.waitForVisible("xPath=(//select[@id='_166_languageId0'])[2]");
		assertEquals("Spanish (Spain)",
			selenium.getSelectedLabel(
				"xPath=(//select[@id='_166_languageId0'])[2]"));
		assertTrue(selenium.isVisible(
				"xPath=(//img[contains(@src,'es_ES')])[3]"));
		assertEquals("WC Structure Name Spanish",
			selenium.getValue("//input[@id='_166_name_es_ES']"));
		assertEquals(RuntimeVariables.replace("Other Languages (1)"),
			selenium.getText(
				"xPath=(//a[contains(.,'Other Languages (1)')])[2]"));
		selenium.clickAt("xPath=(//a[contains(.,'Other Languages (1)')])[2]",
			RuntimeVariables.replace("Other Languages (1)"));
		selenium.waitForVisible("xPath=(//select[@id='_166_languageId0'])[1]");
		assertEquals("Spanish (Spain)",
			selenium.getSelectedLabel(
				"xPath=(//select[@id='_166_languageId0'])[1]"));
		assertTrue(selenium.isVisible(
				"xPath=(//img[contains(@src,'es_ES')])[2]"));
		assertEquals("WC Structure Description Spanish",
			selenium.getValue("//textarea[@id='_166_description_es_ES']"));
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
		selenium.clickAt("link=My Account",
			RuntimeVariables.replace("My Account"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText(
				"//a[contains(.,'Display Settings')]", "Display Settings"));
		selenium.clickAt("//a[contains(.,'Display Settings')]",
			RuntimeVariables.replace("Display Settings"));
		selenium.waitForVisible("//select[@id='_2_languageId']");
		selenium.select("//select[@id='_2_languageId']",
			RuntimeVariables.replace("espa\u00f1ol (Espa\u00f1a)"));
		selenium.waitForVisible(
			"//a[contains(.,'Display Settings')]/span[@class='modified-notice']");
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Su petici\u00f3n ha terminado con \u00e9xito."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.open("/es/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Ir a"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Panel de control");
		selenium.clickAt("link=Panel de control",
			RuntimeVariables.replace("Panel de control"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Contenido Web",
			RuntimeVariables.replace("Contenido Web"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Administrar"),
			selenium.getText("//span[@title='Administrar']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Administrar']/ul/li/strong/a",
			RuntimeVariables.replace("Administrar"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Estructuras')]");
		assertEquals(RuntimeVariables.replace("Estructuras"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Estructuras')]"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Estructuras')]",
			RuntimeVariables.replace("Estructuras"));
		selenium.waitForVisible("//iframe[contains(@src,'Estructuras')]");
		selenium.selectFrame("//iframe[contains(@src,'Estructuras')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/store.js')]");
		assertEquals(RuntimeVariables.replace("Ver todos"),
			selenium.getText("//div[@class='lfr-portlet-toolbar']/span[1]/a"));
		assertEquals(RuntimeVariables.replace("A\u00f1adir"),
			selenium.getText("//div[@class='lfr-portlet-toolbar']/span[2]/a"));
		assertTrue(selenium.isVisible("//input[@name='_166_keywords']"));
		assertTrue(selenium.isVisible("//input[@value='Buscar']"));
		assertTrue(selenium.isVisible(
				"//tr[@class='portlet-section-header results-header']/th[1]"));
		assertEquals(RuntimeVariables.replace("Identificador"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[2]"));
		assertEquals(RuntimeVariables.replace("Nombre"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[3]"));
		assertEquals(RuntimeVariables.replace("Fecha de modificaci\u00f3n"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[4]"));
		assertTrue(selenium.isVisible(
				"//tr[@class='portlet-section-header results-header']/th[5]"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC Structure Name Spanish')]/td[1]"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC Structure Name Spanish')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("WC Structure Name Spanish"),
			selenium.getText(
				"//tr[contains(.,'WC Structure Name Spanish')]/td[3]/a"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC Structure Name Spanish')]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("Acciones"),
			selenium.getText(
				"//tr[contains(.,'WC Structure Name Spanish')]/td[5]/span/ul/li/strong/a"));
		assertEquals(RuntimeVariables.replace("Mostrando 1 resultado."),
			selenium.getText("//div[@class='search-results']"));
		selenium.selectFrame("relative=top");
		selenium.open("/es/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Ir a"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Panel de control");
		selenium.clickAt("link=Panel de control",
			RuntimeVariables.replace("Panel de control"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Mi cuenta", RuntimeVariables.replace("Mi cuenta"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//a[@id='_2_displaySettingsLink']",
				"Preferencias de presentaci\u00f3n"));
		selenium.clickAt("//a[@id='_2_displaySettingsLink']",
			RuntimeVariables.replace("Preferencias de presentaci\u00f3n"));
		selenium.waitForVisible("//select[@id='_2_languageId']");
		selenium.select("//select[@id='_2_languageId']",
			RuntimeVariables.replace("English (United States)"));
		selenium.waitForVisible(
			"//a[contains(.,'Preferencias de presentaci\u00f3n')]/span[@class='modified-notice']");
		selenium.clickAt("//input[@value='Guardar']",
			RuntimeVariables.replace("Guardar"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("English (United States)",
			selenium.getSelectedLabel("//select[@id='_2_languageId']"));
	}
}