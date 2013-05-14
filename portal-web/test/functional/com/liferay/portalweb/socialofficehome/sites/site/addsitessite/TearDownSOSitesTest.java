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

package com.liferay.portalweb.socialofficehome.sites.site.addsitessite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownSOSitesTest extends BaseTestCase {
	public void testTearDownSOSites() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.waitForElementPresent("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Sites", RuntimeVariables.replace("Sites"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_134_name']",
					RuntimeVariables.replace("Name"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");

				boolean community1Present = selenium.isElementPresent(
						"//td[7]/span/ul/li/strong/a/span");

				if (!community1Present) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//td[7]/span/ul/li/strong/a/span"));
				selenium.clickAt("//td[7]/span/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[@class='last']/a");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[@class='last']/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li[@class='last']/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 2:

				boolean community2Present = selenium.isElementPresent(
						"//td[7]/span/ul/li/strong/a/span");

				if (!community2Present) {
					label = 3;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//td[7]/span/ul/li/strong/a/span"));
				selenium.clickAt("//td[7]/span/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[@class='last']/a");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[@class='last']/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li[@class='last']/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 3:

				boolean community3Present = selenium.isElementPresent(
						"//td[7]/span/ul/li/strong/a/span");

				if (!community3Present) {
					label = 4;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//td[7]/span/ul/li/strong/a/span"));
				selenium.clickAt("//td[7]/span/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[@class='last']/a");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[@class='last']/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li[@class='last']/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 4:

				boolean community4Present = selenium.isElementPresent(
						"//td[7]/span/ul/li/strong/a/span");

				if (!community4Present) {
					label = 5;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//td[7]/span/ul/li/strong/a/span"));
				selenium.clickAt("//td[7]/span/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[@class='last']/a");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[@class='last']/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li[@class='last']/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 5:

				boolean community5Present = selenium.isElementPresent(
						"//td[7]/span/ul/li/strong/a/span");

				if (!community5Present) {
					label = 6;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//td[7]/span/ul/li/strong/a/span"));
				selenium.clickAt("//td[7]/span/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[@class='last']/a");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[@class='last']/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li[@class='last']/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

			case 6:
			case 100:
				label = -1;
			}
		}
	}
}