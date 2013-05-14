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

package com.liferay.portalweb.socialofficehome.events.event.configureportletdisplaydays2;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletDisplayDays2Test extends BaseTestCase {
	public void testConfigurePortletDisplayDays2() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/so/dashboard/");
		selenium.waitForText("xPath=(//span[@class='portlet-title-text'])[4]",
			"Events");
		assertEquals(RuntimeVariables.replace("Events"),
			selenium.getText("xPath=(//span[@class='portlet-title-text'])[4]"));
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText(
				"//div[2]/div/div[2]/div/section/header/menu/span/ul/li/strong/a"));
		selenium.clickAt("//div[2]/div/div[2]/div/section/header/menu/span/ul/li/strong/a",
			RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a");
		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[2]/a",
			RuntimeVariables.replace("Configuration"));
		selenium.waitForVisible(
			"//div[@class='yui3-widget-bd panel-bd dialog-bd dialog-iframe-bd']/iframe");
		selenium.selectFrame(
			"//div[@class='yui3-widget-bd panel-bd dialog-bd dialog-iframe-bd']/iframe");
		selenium.waitForVisible("//label[contains(@for,'maxDaysDisplayed')]");
		assertEquals(RuntimeVariables.replace("How many days to display?"),
			selenium.getText("//label[contains(@for,'maxDaysDisplayed')]"));
		selenium.select("//select[@id='_86_maxDaysDisplayed']",
			RuntimeVariables.replace("2"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.selectFrame("relative=top");
	}
}