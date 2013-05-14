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

package com.liferay.portalweb.portal.controlpanel.pagetemplates.pagetemplate.viewsiteroleinlineblogsviewptpersistsite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DefineSiteRoleInlineBlogsViewPTTest extends BaseTestCase {
	public void testDefineSiteRoleInlineBlogsViewPT() throws Exception {
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
				selenium.clickAt("link=Page Templates",
					RuntimeVariables.replace("Page Templates"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Page Template Name"),
					selenium.getText("//td[contains(.,'Page Template Name')]/a"));
				selenium.clickAt("//td[contains(.,'Page Template Name')]/a",
					RuntimeVariables.replace("Page Template Name"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isPartialText(
						"//div[contains(.,'Configuration')]/span/a",
						"Open Page Template"));

				String pageTemplate = selenium.getAttribute(
						"//div[contains(.,'Configuration')]/span/a@href");
				RuntimeVariables.setValue("pageTemplate", pageTemplate);
				selenium.open(RuntimeVariables.getValue("pageTemplate"));
				assertEquals(RuntimeVariables.replace("Page Template Name"),
					selenium.getText(
						"//span[@title='Go to Page Template Name']"));
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Options"),
					selenium.getText("//span[@title='Options']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
					RuntimeVariables.replace("Options"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]");
				assertEquals(RuntimeVariables.replace("Configuration"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]",
					RuntimeVariables.replace("Configuration"));
				selenium.waitForVisible(
					"//iframe[@id='_33_configurationIframeDialog']");
				selenium.selectFrame(
					"//iframe[@id='_33_configurationIframeDialog']");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/navigation_interaction.js')]");
				selenium.waitForVisible("link=Permissions");
				selenium.clickAt("link=Permissions",
					RuntimeVariables.replace("Permissions"));
				selenium.waitForPageToLoad("30000");

				boolean rolesSiteroleNameViewCheckbox = selenium.isChecked(
						"//input[@id='roles-siterole-name_ACTION_VIEW']");

				if (rolesSiteroleNameViewCheckbox) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='roles-siterole-name_ACTION_VIEW']",
					RuntimeVariables.replace("Blogs View"));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@id='roles-siterole-name_ACTION_VIEW']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForText("//div[@class='portlet-msg-success']",
					"Your request completed successfully.");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertTrue(selenium.isChecked(
						"//input[@id='roles-siterole-name_ACTION_VIEW']"));
				selenium.selectFrame("relative=top");

			case 100:
				label = -1;
			}
		}
	}
}