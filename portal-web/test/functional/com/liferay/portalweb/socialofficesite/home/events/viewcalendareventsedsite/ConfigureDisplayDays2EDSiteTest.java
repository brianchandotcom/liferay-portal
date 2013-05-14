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

package com.liferay.portalweb.socialofficesite.home.events.viewcalendareventsedsite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigureDisplayDays2EDSiteTest extends BaseTestCase {
	public void testConfigureDisplayDays2EDSite() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/user/joebloggs/so/dashboard/");
				assertEquals(RuntimeVariables.replace("Sites"),
					selenium.getText("//div[@id='so-sidebar']/h3"));
				assertTrue(selenium.isVisible("//input[@class='search-input']"));
				selenium.type("//input[@class='search-input']",
					RuntimeVariables.replace("Open"));
				Thread.sleep(1000);
				assertEquals(RuntimeVariables.replace("Open Site Name"),
					selenium.getText(
						"//li[contains(@class, 'social-office-enabled')]/span[2]/a"));
				selenium.clickAt("//li[contains(@class, 'social-office-enabled')]/span[2]/a",
					RuntimeVariables.replace("Open Site Name"));
				selenium.waitForPageToLoad("30000");

				boolean configurationVisible = selenium.isVisible(
						"//strong/a[contains(@id,'eventsdisplayportlet')]");

				if (configurationVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
				selenium.clickAt("//li[@class='toggle-controls']/a",
					RuntimeVariables.replace("Edit Controls"));
				selenium.waitForVisible(
					"//strong/a[contains(@id,'eventsdisplayportlet')]");

			case 2:
				Thread.sleep(1000);
				assertEquals(RuntimeVariables.replace("Options"),
					selenium.getText(
						"//strong/a[contains(@id,'eventsdisplayportlet')]"));
				selenium.clickAt("//strong/a[contains(@id,'eventsdisplayportlet')]",
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
				selenium.waitForVisible(
					"//label[contains(@for,'maxDaysDisplayed')]");
				assertEquals(RuntimeVariables.replace(
						"How many days to display?"),
					selenium.getText(
						"//label[contains(@for,'maxDaysDisplayed')]"));
				selenium.select("//select[@id='_86_maxDaysDisplayed']",
					RuntimeVariables.replace("2"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"You have successfully updated the setup."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.selectFrame("relative=top");

			case 100:
				label = -1;
			}
		}
	}
}