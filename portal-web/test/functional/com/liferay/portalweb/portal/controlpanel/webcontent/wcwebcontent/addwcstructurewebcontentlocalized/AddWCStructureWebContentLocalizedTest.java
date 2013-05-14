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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcstructurewebcontentlocalized;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWCStructureWebContentLocalizedTest extends BaseTestCase {
	public void testAddWCStructureWebContentLocalized()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
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
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'WC Structure Name')]");
		assertEquals(RuntimeVariables.replace("WC Structure Name"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'WC Structure Name')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'WC Structure Name')]",
			RuntimeVariables.replace("WC Structure Name"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_15_title_en_US']",
			RuntimeVariables.replace("Hello World Localized Article"));
		assertFalse(selenium.isChecked("//span[2]/div/span/span/span/input[2]"));
		selenium.clickAt("//span[2]/div/span/span/span/input[2]",
			RuntimeVariables.replace("Localizable"));
		assertTrue(selenium.isChecked("//span[2]/div/span/span/span/input[2]"));
		assertFalse(selenium.isChecked(
				"//li[2]/span[2]/div/span/span/span/input[2]"));
		selenium.clickAt("//li[2]/span[2]/div/span/span/span/input[2]",
			RuntimeVariables.replace("Localizable"));
		assertTrue(selenium.isChecked(
				"//li[2]/span[2]/div/span/span/span/input[2]"));
		selenium.clickAt("//input[@value='Save as Draft']",
			RuntimeVariables.replace("Save as Draft"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.type("//input[@id='page-name']",
			RuntimeVariables.replace("Hello World Page Name"));
		selenium.type("//input[@id='page-description']",
			RuntimeVariables.replace("Hello World Page Description"));
		selenium.clickAt("//input[@value='Save as Draft']",
			RuntimeVariables.replace("Save as Draft"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
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
		selenium.waitForVisible("//input[@id='page-name']");
		selenium.type("//input[@id='page-name']",
			RuntimeVariables.replace("\u4e16\u754c\u60a8\u597d Page Name"));
		selenium.type("//input[@id='page-description']",
			RuntimeVariables.replace(
				"\u4e16\u754c\u60a8\u597d Page Description"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.selectFrame("relative=top");
		selenium.waitForText("//span[@id='_15_availableTranslationsLinks']/a",
			"Chinese (China)");
		assertEquals(RuntimeVariables.replace("Chinese (China)"),
			selenium.getText("//span[@id='_15_availableTranslationsLinks']/a"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isElementPresent("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("Hello World Localized Article"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("Approved"),
			selenium.getText("//td[4]/a"));
		assertTrue(selenium.isElementPresent("//td[5]/a"));
		assertTrue(selenium.isElementPresent("//td[6]/a"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//td[7]/a"));
	}
}