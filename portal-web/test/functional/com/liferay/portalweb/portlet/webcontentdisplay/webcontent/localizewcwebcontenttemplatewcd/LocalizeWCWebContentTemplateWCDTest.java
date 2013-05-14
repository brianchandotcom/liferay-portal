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

package com.liferay.portalweb.portlet.webcontentdisplay.webcontent.localizewcwebcontenttemplatewcd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class LocalizeWCWebContentTemplateWCDTest extends BaseTestCase {
	public void testLocalizeWCWebContentTemplateWCD() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Web Content Display Test Page",
			RuntimeVariables.replace("Web Content Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//span[@class='icon-action icon-action-edit']/a/span"));
		selenium.clickAt("//span[@class='icon-action icon-action-edit']/a/span",
			RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Hello World Localized Article"),
			selenium.getText("//h1[@class='header-title']"));
		assertEquals(RuntimeVariables.replace("WC Structure Name"),
			selenium.getText("//span[@class='structure-name-label']"));
		assertEquals(RuntimeVariables.replace("WC Template Name"),
			selenium.getText("//span[@class='template-name-label']"));
		assertEquals(RuntimeVariables.replace("Add Translation"),
			selenium.getText("//strong/a/span"));
		selenium.clickAt("//strong/a/span",
			RuntimeVariables.replace("Add Translation"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Chinese (China)')]");
		assertEquals(RuntimeVariables.replace("Chinese (China)"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Chinese (China)')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Chinese (China)')]",
			RuntimeVariables.replace("Chinese (China)"));
		selenium.waitForElementPresent("//iframe[@id='_15_zh_CN']");
		selenium.selectFrame("//iframe[@id='_15_zh_CN']");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible(
			"//div[@id='_15_availableTranslationContainer']");
		assertEquals(RuntimeVariables.replace(
				"Translating Web Content to Chinese (China)"),
			selenium.getText("//div[@id='_15_availableTranslationContainer']"));
		selenium.type("//input[@id='_15_page_name']",
			RuntimeVariables.replace("\u4e16\u754c\u60a8\u597d Page Name"));
		selenium.type("//input[@id='_15_page_description']",
			RuntimeVariables.replace(
				"\u4e16\u754c\u60a8\u597d Page Description"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.selectFrame("relative=top");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Chinese (China)"),
			selenium.getText(
				"//a[@class='lfr-token journal-article-translation-zh_CN']"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Hello World Page Name"),
			selenium.getText("//td[@class='page_name']"));
		assertEquals(RuntimeVariables.replace("Hello World Page Description"),
			selenium.getText("//td[@class='page_description']"));
	}
}