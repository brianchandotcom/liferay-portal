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

package com.liferay.portalweb.properties.calendar.eventratings.viewcalendareventratingsno;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownEventTest extends BaseTestCase {
	public void testTearDownEvent() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.waitForVisible("link=Calendar Test Page");
				selenium.clickAt("link=Calendar Test Page",
					RuntimeVariables.replace("Calendar Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Events",
					RuntimeVariables.replace("Events"));
				selenium.waitForPageToLoad("30000");

				boolean event1Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a");

				if (!event1Present) {
					label = 6;

					continue;
				}

				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//span[@title='Actions']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Delete')]/a");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Delete')]/a"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Delete')]/a",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));

				boolean event2Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a");

				if (!event2Present) {
					label = 5;

					continue;
				}

				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//span[@title='Actions']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Delete')]/a");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Delete')]/a"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Delete')]/a",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));

				boolean event3Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a");

				if (!event3Present) {
					label = 4;

					continue;
				}

				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//span[@title='Actions']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Delete')]/a");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Delete')]/a"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Delete')]/a",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));

				boolean event4Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a");

				if (!event4Present) {
					label = 3;

					continue;
				}

				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//span[@title='Actions']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Delete')]/a");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Delete')]/a"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Delete')]/a",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));

				boolean event5Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a");

				if (!event5Present) {
					label = 2;

					continue;
				}

				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//span[@title='Actions']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Delete')]/a");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Delete')]/a"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Delete')]/a",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));

			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 100:
				label = -1;
			}
		}
	}
}