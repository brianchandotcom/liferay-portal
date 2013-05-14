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

package com.liferay.portalweb.portlet.rss.webcontent.selectwcwebcontentheaderrss;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SelectWCWebContentHeaderRSSTest extends BaseTestCase {
	public void testSelectWCWebContentHeaderRSS() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=RSS Test Page");
		selenium.clickAt("link=RSS Test Page",
			RuntimeVariables.replace("RSS Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//strong/a"));
		selenium.clickAt("//strong/a", RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a");
		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a"));
		selenium.click("//div[@class='lfr-menu-list unstyled']/ul/li[3]/a");
		selenium.waitForVisible("xPath=(//input[@id='_86_selectButton'])[1]");
		Thread.sleep(5000);
		selenium.clickAt("xPath=(//input[@id='_86_selectButton'])[1]",
			RuntimeVariables.replace("Select"));
		selenium.waitForVisible("//td[2]/a");
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//td[2]/a"));
		selenium.clickAt("//td[2]/a",
			RuntimeVariables.replace("WC WebContent Title"));
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace(
				"Header Web Content WC WebContent Title"),
			selenium.getText("//div[2]/div[2]/fieldset/div/div[1]/div"));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=RSS Test Page");
		selenium.clickAt("link=RSS Test Page",
			RuntimeVariables.replace("RSS Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-content']/div/div/p");
		assertEquals(RuntimeVariables.replace("WC WebContent Content"),
			selenium.getText("//div[@class='portlet-content']/div/div/p"));
	}
}