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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcwebcontentlocalized;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWCWebContentLocalizedTest extends BaseTestCase {
	public void testViewWCWebContentLocalized() throws Exception {
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
		assertTrue(selenium.isVisible(
				"//a[contains(@title,'WC WebContent Title')]/div/img"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText(
				"//a[@class='entry-link']/span[contains(.,'WC WebContent Title')]"));
		selenium.clickAt("//a[@id='_15_dhec_menuButton']",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
		assertEquals(RuntimeVariables.replace("Move"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Move')]"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Permissions')]"));
		assertEquals(RuntimeVariables.replace("View (Opens New Window)"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'View (Opens New Window)')]"));
		assertEquals(RuntimeVariables.replace("Copy"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Copy')]"));
		assertEquals(RuntimeVariables.replace("Expire"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Expire')]"));
		assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Move to the Recycle Bin')]"));
		selenium.clickAt("//a[@class='entry-link']/span[contains(.,'WC WebContent Title')]",
			RuntimeVariables.replace("WC WebContent Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertEquals(RuntimeVariables.replace(
				"Web Content can be any content you would like to add to a site, such as articles, a FAQ, or a news item. Administrators can manage content, as well as assign user roles and permissions. Users may add, edit, approve, or view content depending on their role. Or disable for all portlets."),
			selenium.getText("//div[@id='cpContextPanelTemplate']"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertTrue(selenium.isVisible("//span[@class='workflow-id']"));
		assertEquals(RuntimeVariables.replace("Version: 1.0"),
			selenium.getText("//span[@class='workflow-version']"));
		assertEquals(RuntimeVariables.replace("Status: Approved"),
			selenium.getText("//span[@class='workflow-status']"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText("//button[.='Permissions']"));
		assertEquals(RuntimeVariables.replace("View History"),
			selenium.getText("//button[.='View History']"));
		assertEquals(RuntimeVariables.replace("Default"),
			selenium.getText("//span[@id='_15_structureNameLabel']"));
		assertEquals(RuntimeVariables.replace("None"),
			selenium.getText("//span[@class='template-name-label']"));
		assertEquals(RuntimeVariables.replace("English (United States)"),
			selenium.getText("//span[@id='_15_textLanguageId']"));
		assertEquals(RuntimeVariables.replace("Available Translations:"),
			selenium.getText(
				"//span[@id='_15_availableTranslationsLinks']/label"));
		assertEquals(RuntimeVariables.replace("Chinese (China)"),
			selenium.getText("//span[@id='_15_availableTranslationsLinks']/a"));
		selenium.clickAt("//span[@id='_15_availableTranslationsLinks']/a",
			RuntimeVariables.replace("Chinese (China)"));
		selenium.waitForVisible("//iframe[@id='_15_zh_CN']");
		selenium.selectFrame("//iframe[@id='_15_zh_CN']");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/html/js/editor/ckeditor/plugins/restore/plugin.js')]");
		selenium.waitForText("//h1[@class='header-title']/span",
			"WC WebContent Title \u4e16\u754c\u60a8\u597d");
		assertEquals(RuntimeVariables.replace(
				"WC WebContent Title \u4e16\u754c\u60a8\u597d"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals("WC WebContent Title \u4e16\u754c\u60a8\u597d",
			selenium.getValue("//input[@id='_15_title_zh_CN']"));
		assertEquals(RuntimeVariables.replace(
				"Translating Web Content to Chinese (China)"),
			selenium.getText("//div[@id='_15_availableTranslationContainer']"));
		selenium.waitForVisible("//div[@id='cke_1_contents']/iframe");
		selenium.selectFrame("//div[@id='cke_1_contents']/iframe");
		selenium.waitForText("//body",
			"WC WebContent Content \u4e16\u754c\u60a8\u597d");
		assertEquals(RuntimeVariables.replace(
				"WC WebContent Content \u4e16\u754c\u60a8\u597d"),
			selenium.getText("//body"));
		selenium.selectFrame("relative=top");
		selenium.waitForVisible("//button[@title='Close dialog']");
		selenium.clickAt("//button[@title='Close dialog']",
			RuntimeVariables.replace("X"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/html/js/editor/ckeditor/plugins/restore/plugin.js')]");
		assertEquals("WC WebContent Title",
			selenium.getValue("//input[@id='_15_title_en_US']"));
		selenium.waitForVisible("//div[@id='cke_1_contents']/iframe");
		selenium.selectFrame("//div[@id='cke_1_contents']/iframe");
		selenium.waitForText("//body", "WC WebContent Content");
		assertEquals(RuntimeVariables.replace("WC WebContent Content"),
			selenium.getText("//body"));
		selenium.selectFrame("relative=top");
		assertTrue(selenium.isChecked("//input[@id='_15_indexableCheckbox']"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//span[@class='article-name']"));
		assertTrue(selenium.isPartialText("//a[@id='_15_contentLink']",
				"Content"));
		assertTrue(selenium.isPartialText("//a[@id='_15_abstractLink']",
				"Abstract"));
		assertTrue(selenium.isPartialText("//a[@id='_15_categorizationLink']",
				"Categorization"));
		assertTrue(selenium.isPartialText("//a[@id='_15_scheduleLink']",
				"Schedule"));
		assertTrue(selenium.isPartialText("//a[@id='_15_displayPageLink']",
				"Display Page"));
		assertTrue(selenium.isPartialText("//a[@id='_15_relatedAssetsLink']",
				"Related Assets"));
		assertTrue(selenium.isPartialText("//a[@id='_15_customFieldsLink']",
				"Custom Fields"));
		assertEquals(RuntimeVariables.replace(
				"A new version will be created automatically if this content is modified."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertEquals("Save as Draft",
			selenium.getValue("//input[@value='Save as Draft']"));
		assertEquals("Publish", selenium.getValue("//input[@value='Publish']"));
		assertEquals("Cancel", selenium.getValue("//input[@value='Cancel']"));
	}
}