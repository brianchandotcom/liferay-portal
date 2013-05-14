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
public class ViewLocalizeWCStructureTest extends BaseTestCase {
	public void testViewLocalizeWCStructure() throws Exception {
		selenium.selectWindow("null");
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
		selenium.clickAt("link=Contenido Web",
			RuntimeVariables.replace("Contenido Web"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Administrar"),
			selenium.getText("//span[@title='Administrar']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Administrar']/ul/li/strong/a",
			RuntimeVariables.replace("Administrar"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Estructuras')]");
		assertEquals(RuntimeVariables.replace("Estructuras"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Estructuras')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Estructuras')]",
			RuntimeVariables.replace("Estructuras"));
		selenium.waitForVisible("//iframe[contains(@src,'Estructuras')]");
		selenium.selectFrame("//iframe[contains(@src,'Estructuras')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/store.js')]");
		Thread.sleep(1000);
		selenium.waitForVisible("//div[@class='lfr-portlet-toolbar']/span[1]/a");
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
		assertEquals(RuntimeVariables.replace("Descripci\u00f3n"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[4]"));
		assertEquals(RuntimeVariables.replace("Fecha de modificaci\u00f3n"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[5]"));
		assertTrue(selenium.isVisible(
				"//tr[@class='portlet-section-header results-header']/th[6]"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC Nombre de la estructura')]/td[1]"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC Nombre de la estructura')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("WC Nombre de la estructura"),
			selenium.getText(
				"//tr[contains(.,'WC Nombre de la estructura')]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("WC Descripci\u00f3n Estructura"),
			selenium.getText(
				"//tr[contains(.,'WC Nombre de la estructura')]/td[4]/a"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC Nombre de la estructura')]/td[5]/a"));
		assertEquals(RuntimeVariables.replace("Acciones"),
			selenium.getText(
				"//tr[contains(.,'WC Nombre de la estructura')]/td[6]/span/ul/li/strong/a"));
		assertEquals(RuntimeVariables.replace("Mostrando 1 resultado."),
			selenium.getText("//div[@class='search-results']"));
		selenium.clickAt("//tr[contains(.,'WC Nombre de la estructura')]/td[3]/a",
			RuntimeVariables.replace("WC Nombre de la estructura"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("WC Nombre de la estructura"),
			selenium.getText("//h1[@class='header-title']/span"));
		selenium.waitForVisible(
			"//span[@class=\"taglib-input-localized\"]/span/a[contains(@id,'languageSelectorTrigger')]");
		assertEquals(RuntimeVariables.replace("Otros idiomas (1)"),
			selenium.getText(
				"//span[@class=\"taglib-input-localized\"]/span/a[contains(@id,'languageSelectorTrigger')]"));
		selenium.clickAt("//span[@class=\"taglib-input-localized\"]/span/a[contains(@id,'languageSelectorTrigger')]",
			RuntimeVariables.replace("Otros idiomas (1)"));
		selenium.waitForVisible(
			"//div[@class='lfr-floating-container lfr-language-selector']/div/div/div/div/select[@id='_166_languageId0']");
		assertEquals("espa\u00f1ol (Espa\u00f1a)",
			selenium.getSelectedLabel(
				"//div[@class='lfr-floating-container lfr-language-selector']/div/div/div/div/select[@id='_166_languageId0']"));
		selenium.waitForVisible(
			"//div[@class='lfr-floating-container lfr-language-selector']/div/div/div/div/img[contains(@src,'es_ES')]");
		assertTrue(selenium.isVisible(
				"//div[@class='lfr-floating-container lfr-language-selector']/div/div/div/div/img[contains(@src,'es_ES')]"));
		assertEquals("WC Nombre de la estructura",
			selenium.getValue(
				"//div[@class='lfr-floating-container lfr-language-selector']/div/div/div/div/input[@id='_166_name_es_ES']"));
		assertEquals(RuntimeVariables.replace("Otros idiomas (1)"),
			selenium.getText(
				"xPath=(//span[@class=\"taglib-input-localized\"]/span/a[contains(@id,'languageSelectorTrigger')])[2]"));
		selenium.clickAt("xPath=(//span[@class=\"taglib-input-localized\"]/span/a[contains(@id,'languageSelectorTrigger')])[2]",
			RuntimeVariables.replace("Otros idiomas (1)"));
		selenium.waitForVisible(
			"xPath=(//div[@class='lfr-floating-container lfr-language-selector']/div/div/div/div/select[@id='_166_languageId0'])[2]");
		assertEquals("espa\u00f1ol (Espa\u00f1a)",
			selenium.getSelectedLabel(
				"xPath=(//div[@class='lfr-floating-container lfr-language-selector']/div/div/div/div/select[@id='_166_languageId0'])[2]"));
		selenium.waitForVisible(
			"xPath=(//div[@class='lfr-floating-container lfr-language-selector']/div/div/div/div/img[contains(@src,'es_ES')])[2]");
		assertTrue(selenium.isVisible(
				"xPath=(//div[@class='lfr-floating-container lfr-language-selector']/div/div/div/div/img[contains(@src,'es_ES')])[2]"));
		assertEquals("WC Descripci\u00f3n Estructura",
			selenium.getValue(
				"xPath=(//div[@class='lfr-floating-container lfr-language-selector']/div/div/div/div/textarea[@id='_166_description_es_ES'])[1]"));
		selenium.selectFrame("relative=top");
	}
}