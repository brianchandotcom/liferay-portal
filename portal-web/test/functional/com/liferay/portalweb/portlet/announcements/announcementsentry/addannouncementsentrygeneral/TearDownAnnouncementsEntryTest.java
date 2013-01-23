/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.announcements.announcementsentry.addannouncementsentrygeneral;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownAnnouncementsEntryTest extends BaseTestCase {
	public void testTearDownAnnouncementsEntry() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Announcements Test Page",
					RuntimeVariables.replace("Announcements Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Entries",
					RuntimeVariables.replace("Entries"));
				selenium.waitForPageToLoad("30000");

				boolean entry1Present = selenium.isElementPresent(
						"//td[@class='delete-entry']/span/a/span");

				if (!entry1Present) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//td[@class='delete-entry']/span/a/span"));
				selenium.click(RuntimeVariables.replace(
						"//td[@class='delete-entry']/span/a/span"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));

				boolean entry2Present = selenium.isElementPresent(
						"//td[@class='delete-entry']/span/a/span");

				if (!entry2Present) {
					label = 3;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//td[@class='delete-entry']/span/a/span"));
				selenium.click(RuntimeVariables.replace(
						"//td[@class='delete-entry']/span/a/span"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));

				boolean entry3Present = selenium.isElementPresent(
						"//td[@class='delete-entry']/span/a/span");

				if (!entry3Present) {
					label = 4;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//td[@class='delete-entry']/span/a/span"));
				selenium.click(RuntimeVariables.replace(
						"//td[@class='delete-entry']/span/a/span"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));

				boolean entry4Present = selenium.isElementPresent(
						"//td[@class='delete-entry']/span/a/span");

				if (!entry4Present) {
					label = 5;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//td[@class='delete-entry']/span/a/span"));
				selenium.click(RuntimeVariables.replace(
						"//td[@class='delete-entry']/span/a/span"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));

				boolean entry5Present = selenium.isElementPresent(
						"//td[@class='delete-entry']/span/a/span");

				if (!entry5Present) {
					label = 6;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//td[@class='delete-entry']/span/a/span"));
				selenium.click(RuntimeVariables.replace(
						"//td[@class='delete-entry']/span/a/span"));
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