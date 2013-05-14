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

package com.liferay.portalweb.properties.webcontent.mailintegration.addwcwebcontentemail;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownEmailConfigurationTest extends BaseTestCase {
	public void testTearDownEmailConfiguration() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
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
				assertEquals(RuntimeVariables.replace("Liferay"),
					selenium.getText("//a[@id='_160_groupSelectorButton']/span"));
				selenium.clickAt("//a[@id='_160_groupSelectorButton']/span",
					RuntimeVariables.replace("Liferay"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Site Name')]");
				assertEquals(RuntimeVariables.replace("Site Name"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Site Name')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Site Name')]",
					RuntimeVariables.replace("Site Name"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Web Content",
					RuntimeVariables.replace("Web Content"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(1000);
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
					"//iframe[@id='_15_configurationIframeDialog']");
				selenium.selectFrame(
					"//iframe[@id='_15_configurationIframeDialog']");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/escape/escape-min.js')]");
				selenium.waitForVisible("link=Email From");
				selenium.clickAt("link=Email From",
					RuntimeVariables.replace("Email From"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(1000);
				selenium.type("//input[@id='_86_emailFromName']",
					RuntimeVariables.replace("Joe Bloggs"));
				selenium.type("//input[@id='_86_emailFromAddress']",
					RuntimeVariables.replace("test@liferay.com"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"You have successfully updated the setup."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.clickAt("link=Web Content Added Email",
					RuntimeVariables.replace("Web Content Added Email"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//input[@id='_86_emailArticleAddedEnabledCheckbox']"));

				boolean webContentAddedChecked = selenium.isChecked(
						"_86_emailArticleAddedEnabledCheckbox");

				if (!webContentAddedChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_86_emailArticleAddedEnabledCheckbox']",
					RuntimeVariables.replace("Enabled"));
				Thread.sleep(1000);
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"You have successfully updated the setup."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 2:
				selenium.selectFrame("relative=top");

			case 100:
				label = -1;
			}
		}
	}
}