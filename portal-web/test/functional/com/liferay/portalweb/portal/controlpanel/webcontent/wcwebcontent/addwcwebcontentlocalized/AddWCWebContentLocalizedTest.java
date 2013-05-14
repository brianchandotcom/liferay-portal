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
public class AddWCWebContentLocalizedTest extends BaseTestCase {
	public void testAddWCWebContentLocalized() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[@title='Add']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Add']/ul/li/strong/a/span",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Basic Web Content')]");
		assertEquals(RuntimeVariables.replace("Basic Web Content"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Basic Web Content')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Basic Web Content')]"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/html/js/editor/ckeditor/plugins/restore/plugin.js')]");
		selenium.type("//input[@id='_15_title_en_US']",
			RuntimeVariables.replace("WC WebContent Title"));
		selenium.waitForText("//span[@class='cke_toolbar']/span[2]/a/span",
			"Normal");
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForVisible(
			"//a[@class='cke_button cke_button__source cke_button_on']");
		selenium.waitForVisible("//div[@id='cke_1_contents']/textarea");
		selenium.type("//div[@id='cke_1_contents']/textarea",
			RuntimeVariables.replace("WC WebContent Content"));
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForVisible(
			"//a[@class='cke_button cke_button__source cke_button_off']");
		selenium.waitForVisible("//div[@id='cke_1_contents']/iframe");
		selenium.selectFrame("//div[@id='cke_1_contents']/iframe");
		selenium.waitForText("//body", "WC WebContent Content");
		selenium.selectFrame("relative=top");
		selenium.clickAt("//input[@value='Save as Draft']",
			RuntimeVariables.replace("Save as Draft"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/html/js/editor/ckeditor/plugins/restore/plugin.js')]");
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("English (United States)"),
			selenium.getText("//span[@id='_15_textLanguageId']"));
		assertEquals(RuntimeVariables.replace("Add Translation"),
			selenium.getText(
				"//span[@title='Add Translation']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Add Translation']/ul/li/strong/a/span",
			RuntimeVariables.replace("Add Translation"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Chinese (China)')]");
		assertEquals(RuntimeVariables.replace("Chinese (China)"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Chinese (China)')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Chinese (China)')]",
			RuntimeVariables.replace("Chinese (China)"));
		selenium.waitForVisible("//iframe[@id='_15_zh_CN']");
		selenium.selectFrame("//iframe[@id='_15_zh_CN']");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/html/js/editor/ckeditor/plugins/restore/plugin.js')]");
		selenium.waitForVisible("//h1[@class='header-title']/span");
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		Thread.sleep(1000);
		selenium.waitForVisible("//input[@id='_15_title_zh_CN']");
		selenium.type("//input[@id='_15_title_zh_CN']",
			RuntimeVariables.replace(
				"WC WebContent Title \u4e16\u754c\u60a8\u597d"));
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForVisible(
			"//a[@class='cke_button cke_button__source cke_button_on']");
		selenium.waitForVisible("//div[@id='cke_1_contents']/textarea");
		selenium.type("//div[@id='cke_1_contents']/textarea",
			RuntimeVariables.replace(
				"WC WebContent Content \u4e16\u754c\u60a8\u597d"));
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[.='Source']"));
		selenium.clickAt("//span[.='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForVisible(
			"//a[@class='cke_button cke_button__source cke_button_off']");
		selenium.waitForVisible("//div[@id='cke_1_contents']/iframe");
		selenium.selectFrame("//div[@id='cke_1_contents']/iframe");
		selenium.waitForText("//body",
			"WC WebContent Content \u4e16\u754c\u60a8\u597d");
		selenium.selectFrame("relative=top");
		selenium.waitForVisible("//iframe[@id='_15_zh_CN']");
		selenium.selectFrame("//iframe[@id='_15_zh_CN']");
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.selectFrame("relative=top");
		selenium.waitForText("//a[contains(@class,'journal-article-translation-zh_CN')]",
			"Chinese (China)");
		assertEquals(RuntimeVariables.replace("Chinese (China)"),
			selenium.getText(
				"//a[contains(@class,'journal-article-translation-zh_CN')]"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isVisible(
				"//a[contains(@title,'WC WebContent Title')]/div/img"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText(
				"//a[@class='entry-link']/span[contains(.,'WC WebContent Title')]"));
	}
}