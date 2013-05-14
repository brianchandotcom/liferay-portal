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

package com.liferay.portalweb.portlet.webcontentdisplay.webcontent.viewwcwebcontentscopeglobalwcd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletWCDWCWebContentScopeGlobalTest extends BaseTestCase {
	public void testConfigurePortletWCDWCWebContentScopeGlobal()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Web Content Display Test Page",
			RuntimeVariables.replace("Web Content Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Select existing web content or add some web content to be displayed in this portlet."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//span[@title='Options']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
			RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Configuration')]/a");
		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Configuration')]/a"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Configuration')]/a",
			RuntimeVariables.replace("Configuration"));
		selenium.waitForVisible(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.selectFrame(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForText("//div[@class='portlet-msg-info']/span",
			"Please select web content from the list below.");
		assertEquals(RuntimeVariables.replace(
				"Please select web content from the list below."),
			selenium.getText("//div[@class='portlet-msg-info']/span"));
		assertEquals(RuntimeVariables.replace("WC WebContent Global Title"),
			selenium.getText(
				"//td[@id='_86_ocerSearchContainer_col-title_row-1']/a"));
		selenium.clickAt("//td[@id='_86_ocerSearchContainer_col-title_row-1']/a",
			RuntimeVariables.replace("WC WebContent Global Title"));
		selenium.waitForText("//div[@class='portlet-msg-info']/span/span",
			"WC WebContent Global Title (Modified)");
		assertEquals(RuntimeVariables.replace(
				"WC WebContent Global Title (Modified)"),
			selenium.getText("//div[@class='portlet-msg-info']/span/span"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace(
				"Displaying Content: WC WebContent Global Title"),
			selenium.getText(
				"//div[@class='portlet-msg-info']/span[@class='displaying-article-id-holder ']"));
		selenium.selectFrame("relative=top");
	}
}