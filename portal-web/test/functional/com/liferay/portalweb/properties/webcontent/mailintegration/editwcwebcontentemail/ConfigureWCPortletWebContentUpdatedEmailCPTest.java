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

package com.liferay.portalweb.properties.webcontent.mailintegration.editwcwebcontentemail;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigureWCPortletWebContentUpdatedEmailCPTest extends BaseTestCase {
	public void testConfigureWCPortletWebContentUpdatedEmailCP()
		throws Exception {
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
				selenium.clickAt("//strong/a",
					RuntimeVariables.replace("Site Name"));
				selenium.waitForVisible("link=Site Name");
				assertEquals(RuntimeVariables.replace("Site Name"),
					selenium.getText("link=Site Name"));
				selenium.clickAt("link=Site Name",
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
				selenium.waitForVisible("link=Web Content Updated Email");
				selenium.clickAt("link=Web Content Updated Email",
					RuntimeVariables.replace("Web Content Updated Email"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(1000);

				boolean emailArticleUpdatedEnabled = selenium.isChecked(
						"//input[@id='_86_emailArticleUpdatedEnabledCheckbox']");

				if (emailArticleUpdatedEnabled) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_86_emailArticleUpdatedEnabledCheckbox']",
					RuntimeVariables.replace("Enabled"));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@id='_86_emailArticleUpdatedEnabledCheckbox']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"You have successfully updated the setup."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertTrue(selenium.isChecked(
						"//input[@id='_86_emailArticleUpdatedEnabledCheckbox']"));
				selenium.selectFrame("relative=top");

			case 100:
				label = -1;
			}
		}
	}
}