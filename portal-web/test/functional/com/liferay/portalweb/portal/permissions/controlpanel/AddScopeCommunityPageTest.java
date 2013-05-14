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

package com.liferay.portalweb.portal.permissions.controlpanel;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddScopeCommunityPageTest extends BaseTestCase {
	public void testAddScopeCommunityPage() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.waitForElementPresent("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Communities",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("_134_name");
				selenium.type("_134_name",
					RuntimeVariables.replace("Scope Community"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);
				selenium.waitForVisible("//strong/a");
				selenium.clickAt("//strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForElementPresent(
					"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a");
				assertEquals(RuntimeVariables.replace("Manage Pages"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Public Pages"),
					selenium.getText("//li[1]/span/span/a"));
				assertEquals(RuntimeVariables.replace("Pages"),
					selenium.getText("//ul[2]/li[1]/span/span/a"));
				selenium.waitForText("//div/div[3]/a", "Scope Community");
				selenium.waitForVisible("_134_name_en_US");
				selenium.type("_134_name_en_US",
					RuntimeVariables.replace("Scope Test Page"));
				selenium.clickAt("//input[@value='Add Page']",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent(
						"Your request completed successfully."));

				boolean publicPagePresent = selenium.isElementPresent(
						"//li/ul/li/div/div[3]/a");

				if (publicPagePresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("//li/div/div[1]", RuntimeVariables.replace(""));

			case 2:
				selenium.click("//li/div/div[1]");
				selenium.waitForVisible("//li/ul/li/div/div[3]/a");
				assertTrue(selenium.isVisible("//li/ul/li/div/div[3]/a"));
				assertEquals(RuntimeVariables.replace("Scope Test Page"),
					selenium.getText("//li/ul/li/div/div[3]/a"));

			case 100:
				label = -1;
			}
		}
	}
}