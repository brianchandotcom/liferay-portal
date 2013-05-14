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

package com.liferay.portalweb.portal.controlpanel.socialactivity.usecase.wikipage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletAddCounter3CommentsTest extends BaseTestCase {
	public void testConfigurePortletAddCounter3Comments()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/site-name/");
		selenium.clickAt("link=User Statistics Test Page",
			RuntimeVariables.replace("User Statistics Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("User Statistics"),
			selenium.getText("//span[@class='portlet-title-text']"));
		selenium.clickAt("//span[@class='portlet-title-text']",
			RuntimeVariables.replace("User Statistics"));
		selenium.waitForElementPresent("//div[@class='yui3-dd-shim']");
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//span[@title='Options']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
			RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]");
		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]"));
		selenium.click(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]");
		selenium.waitForVisible(
			"//iframe[@id='_180_configurationIframeDialog']");
		selenium.selectFrame("//iframe[@id='_180_configurationIframeDialog']");
		selenium.waitForVisible("//button[contains(@class,'add-row')]");
		selenium.clickAt("//button[contains(@class,'add-row')]",
			RuntimeVariables.replace("+"));
		selenium.waitForVisible(
			"//select[@id='_86_displayActivityCounterName3']");
		selenium.select("//select[@id='_86_displayActivityCounterName3']",
			RuntimeVariables.replace("User's Comments"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("User's Comments",
			selenium.getSelectedLabel(
				"//select[@id='_86_displayActivityCounterName3']"));
		selenium.selectFrame("relative=top");
	}
}