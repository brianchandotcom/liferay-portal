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

package com.liferay.portalweb.portlet.webcontentdisplay.webcontent.localizewcwebcontentwcd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class LocalizeWCWebContentWCDTest extends BaseTestCase {
	public void testLocalizeWCWebContentWCD() throws Exception {
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
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//h1[@class='header-title']"));
		selenium.waitForVisible(
			"//a[contains(@class,'cke_button cke_button__cut') and contains(@class,'cke_button_disabled')]");
		selenium.waitForVisible("//iframe[contains(@title,'Rich Text Editor')]");
		selenium.selectFrame("//iframe[contains(@title,'Rich Text Editor')]");
		assertEquals(RuntimeVariables.replace("WC WebContent Content"),
			selenium.getText("//body"));
		selenium.selectFrame("relative=top");
		assertEquals(RuntimeVariables.replace("Add Translation"),
			selenium.getText(
				"//span[@title='Add Translation']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Add Translation']/ul/li/strong/a/span",
			RuntimeVariables.replace("Add Translation"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Chinese (China)')]/a");
		assertEquals(RuntimeVariables.replace("Chinese (China)"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Chinese (China)')]/a"));
		selenium.click(
			"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Chinese (China)')]/a");
		selenium.waitForVisible("//iframe[@id='_15_zh_CN']");
		selenium.selectFrame("//iframe[@id='_15_zh_CN']");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible(
			"//a[contains(@class,'cke_button cke_button__cut') and contains(@class,'cke_button_disabled')]");
		selenium.waitForVisible("//iframe[contains(@title,'Rich Text Editor')]");
		selenium.typeFrame("//iframe[contains(@title,'Rich Text Editor')]",
			RuntimeVariables.replace(
				"\u4e16\u754c\u60a8\u597d Page Description"));
		selenium.waitForVisible("//iframe[@id='_15_zh_CN']");
		selenium.selectFrame("//iframe[@id='_15_zh_CN']");
		selenium.clickAt("//input[@id='_15_translateButton']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.selectFrame("relative=top");
		selenium.waitForVisible(
			"//a[@class='lfr-token journal-article-translation-zh_CN']");
		assertEquals(RuntimeVariables.replace("Chinese (China)"),
			selenium.getText(
				"//a[@class='lfr-token journal-article-translation-zh_CN']"));
		selenium.waitForVisible(
			"//a[contains(@class,'cke_button cke_button__cut') and contains(@class,'cke_button_disabled')]");
		selenium.waitForVisible("//iframe[contains(@title,'Rich Text Editor')]");
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC WebContent Content"),
			selenium.getText("//div[@class='journal-content-article']/p"));
	}
}