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

package com.liferay.portalweb.stagingsite.blogs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ActivateStagingTest extends BaseTestCase {
	public void testActivateStaging() throws Exception {
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
				selenium.clickAt("link=Sites", RuntimeVariables.replace("Sites"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"xPath=(//span[@title='Actions']/ul/li/strong/a/span)[2]"));
				selenium.click(
					"xPath=(//span[@title='Actions']/ul/li/strong/a/span)[2]");
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit Settings')]");
				assertEquals(RuntimeVariables.replace("Edit Settings"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit Settings')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit Settings')]",
					RuntimeVariables.replace("Edit Settings"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isPartialText(
						"//a[@id='_165_stagingLink']", "Staging"));
				selenium.clickAt("//a[@id='_165_stagingLink']",
					RuntimeVariables.replace("Staging"));
				selenium.waitForVisible("//input[@id='_165_local']");
				selenium.clickAt("//input[@id='_165_local']",
					RuntimeVariables.replace("Local Live"));
				selenium.waitForVisible(
					"//span/span[contains(.,'Blogs')]/span/input[2]");

				boolean blogsChecked = selenium.isChecked(
						"//span/span[contains(.,'Blogs')]/span/input[2]");

				if (blogsChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Blogs')]/span/input[2]",
					RuntimeVariables.replace("Blogs"));

			case 2:

				boolean bookmarksChecked = selenium.isChecked(
						"//span/span[contains(.,'Bookmarks')]/span/input[2]");

				if (bookmarksChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Bookmarks')]/span/input[2]",
					RuntimeVariables.replace("Bookmarks"));

			case 3:

				boolean calendarChecked = selenium.isChecked(
						"//span/span[contains(.,'Calendar')]/span/input[2]");

				if (calendarChecked) {
					label = 4;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Calendar')]/span/input[2]",
					RuntimeVariables.replace("Calendar"));

			case 4:

				boolean documentLibraryChecked = selenium.isChecked(
						"//span/span[contains(.,'Documents and Media')]/span/input[2]");

				if (documentLibraryChecked) {
					label = 5;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Documents and Media')]/span/input[2]",
					RuntimeVariables.replace("DocumentLibrary"));

			case 5:

				boolean documentLibraryDisplayChecked = selenium.isChecked(
						"//span/span[contains(.,'Documents and Media Display')]/span/input[2]");

				if (documentLibraryDisplayChecked) {
					label = 6;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Documents and Media Display')]/span/input[2]",
					RuntimeVariables.replace("DocumentLibraryDisplay"));

			case 6:

				boolean dynamicDataMappingChecked = selenium.isChecked(
						"//span/span[contains(.,'Dynamic Data Mapping')]/span/input[2]");

				if (dynamicDataMappingChecked) {
					label = 7;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Dynamic Data Mapping')]/span/input[2]",
					RuntimeVariables.replace("Dynamic Data Mapping"));

			case 7:

				boolean messageBoardsChecked = selenium.isChecked(
						"//span/span[contains(.,'Message Boards')]/span/input[2]");

				if (messageBoardsChecked) {
					label = 8;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Message Boards')]/span/input[2]",
					RuntimeVariables.replace("Message Boards"));

			case 8:

				boolean pageCommentsChecked = selenium.isChecked(
						"//span/span[contains(.,'Page Comments')]/span/input[2]");

				if (pageCommentsChecked) {
					label = 9;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Page Comments')]/span/input[2]",
					RuntimeVariables.replace("Page Comments"));

			case 9:

				boolean pageRatingsChecked = selenium.isChecked(
						"//span/span[contains(.,'Page Ratings')]/span/input[2]");

				if (pageRatingsChecked) {
					label = 10;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Page Ratings')]/span/input[2]",
					RuntimeVariables.replace("Page Ratings"));

			case 10:

				boolean pollsChecked = selenium.isChecked(
						"//span/span[contains(.,'Polls')]/span/input[2]");

				if (pollsChecked) {
					label = 11;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Polls')]/span/input[2]",
					RuntimeVariables.replace("Polls"));

			case 11:

				boolean pollsDisplayChecked = selenium.isChecked(
						"//span/span[contains(.,'Polls Display')]/span/input[2]");

				if (pollsDisplayChecked) {
					label = 12;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Polls Display')]/span/input[2]",
					RuntimeVariables.replace("Polls Display"));

			case 12:

				boolean webContentChecked = selenium.isChecked(
						"//span/span[contains(.,'Web Content')]/span/input[2]");

				if (webContentChecked) {
					label = 13;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Web Content')]/span/input[2]",
					RuntimeVariables.replace("Web Content"));

			case 13:

				boolean webContentDisplayChecked = selenium.isChecked(
						"//span/span[contains(.,'Web Content Display')]/span/input[2]");

				if (webContentDisplayChecked) {
					label = 14;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Web Content Display')]/span/input[2]",
					RuntimeVariables.replace("Web Content Display"));

			case 14:

				boolean wikiChecked = selenium.isChecked(
						"//span/span[contains(.,'Wiki')]/span/input[2]");

				if (wikiChecked) {
					label = 15;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Wiki')]/span/input[2]",
					RuntimeVariables.replace("Wiki"));

			case 15:

				boolean wikiDisplayChecked = selenium.isChecked(
						"//span/span[contains(.,'Wiki Display')]/span/input[2]");

				if (wikiDisplayChecked) {
					label = 16;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Wiki Display')]/span/input[2]",
					RuntimeVariables.replace("Wiki Display Checked"));

			case 16:
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Liferay (Staging)"),
					selenium.getText("//h1[@class='header-title']/span"));

			case 100:
				label = -1;
			}
		}
	}
}