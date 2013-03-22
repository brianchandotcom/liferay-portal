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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcwebcontents;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWCWebContentsTest extends BaseTestCase {
	public void testViewWCWebContents() throws Exception {
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
				"//div[@data-title='WC WebContent1 Title']/a/div[@class='entry-thumbnail']/img"));
		assertEquals(RuntimeVariables.replace("WC WebContent1 Title"),
			selenium.getText(
				"//div[@data-title='WC WebContent1 Title']/a/span[@class='entry-title']/span"));
		assertTrue(selenium.isVisible(
				"//div[@data-title='WC WebContent2 Title']/a/div[@class='entry-thumbnail']/img"));
		assertEquals(RuntimeVariables.replace("WC WebContent2 Title"),
			selenium.getText(
				"//div[@data-title='WC WebContent2 Title']/a/span[@class='entry-title']/span"));
		assertTrue(selenium.isVisible(
				"//div[@data-title='WC WebContent3 Title']/a/div[@class='entry-thumbnail']/img"));
		assertEquals(RuntimeVariables.replace("WC WebContent3 Title"),
			selenium.getText(
				"//div[@data-title='WC WebContent3 Title']/a/span[@class='entry-title']/span"));
		selenium.clickAt("//div[@data-title='WC WebContent1 Title']/a/span[@class='entry-title']/span",
			RuntimeVariables.replace("WC WebContent1 Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC WebContent1 Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		selenium.waitForVisible("//iframe[contains(@title,'Rich Text Editor')]");
		selenium.selectFrame("//iframe[contains(@title,'Rich Text Editor')]");
		selenium.waitForText("//body", "WC WebContent1 Content");
		assertEquals(RuntimeVariables.replace("WC WebContent1 Content"),
			selenium.getText("//body"));
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
		assertEquals(RuntimeVariables.replace("WC WebContent2 Title"),
			selenium.getText(
				"//div[@data-title='WC WebContent2 Title']/a/span[@class='entry-title']/span"));
		selenium.clickAt("//div[@data-title='WC WebContent2 Title']/a/span[@class='entry-title']/span",
			RuntimeVariables.replace("WC WebContent2 Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC WebContent2 Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		selenium.waitForVisible("//iframe[contains(@title,'Rich Text Editor')]");
		selenium.selectFrame("//iframe[contains(@title,'Rich Text Editor')]");
		selenium.waitForText("//body", "WC WebContent2 Content");
		assertEquals(RuntimeVariables.replace("WC WebContent2 Content"),
			selenium.getText("//body"));
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
		assertEquals(RuntimeVariables.replace("WC WebContent3 Title"),
			selenium.getText(
				"//div[@data-title='WC WebContent3 Title']/a/span[@class='entry-title']/span"));
		selenium.clickAt("//div[@data-title='WC WebContent3 Title']/a/span[@class='entry-title']/span",
			RuntimeVariables.replace("WC WebContent3 Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC WebContent3 Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		selenium.waitForVisible("//iframe[contains(@title,'Rich Text Editor')]");
		selenium.selectFrame("//iframe[contains(@title,'Rich Text Editor')]");
		selenium.waitForText("//body", "WC WebContent3 Content");
		assertEquals(RuntimeVariables.replace("WC WebContent3 Content"),
			selenium.getText("//body"));
		selenium.selectFrame("relative=top");
	}
}