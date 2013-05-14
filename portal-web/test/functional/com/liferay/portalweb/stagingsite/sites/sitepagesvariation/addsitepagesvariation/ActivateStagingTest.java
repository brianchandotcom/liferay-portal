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

package com.liferay.portalweb.stagingsite.sites.sitepagesvariation.addsitepagesvariation;

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
				selenium.type("//input[@name='_134_keywords']",
					RuntimeVariables.replace("Site Name"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.click("//span[@title='Actions']/ul/li/strong/a/span");
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
				assertTrue(selenium.isPartialText(
						"//span[contains(.,'Page Versioning')]",
						"Page Versioning"));
				selenium.waitForVisible(
					"//span/span[contains(.,'Enabled On Public Pages')]/span/input[2]");

				boolean enabledOnPublicPagesChecked = selenium.isChecked(
						"//span/span[contains(.,'Enabled On Public Pages')]/span/input[2]");

				if (enabledOnPublicPagesChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Enabled On Public Pages')]/span/input[2]",
					RuntimeVariables.replace("Enabled On Public Pages"));

			case 2:
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Enabled On Public Pages')]/span/input[2]"));

				boolean applicationDisplayTemplatesChecked = selenium.isChecked(
						"//span/span[contains(.,'Application Display Templates')]/span/input[2]");

				if (applicationDisplayTemplatesChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Application Display Templates')]/span/input[2]",
					RuntimeVariables.replace("Application Display Templates"));

			case 3:
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Application Display Templates')]/span/input[2]"));

				boolean blogsChecked = selenium.isChecked(
						"//span/span[contains(.,'Blogs')]/span/input[2]");

				if (blogsChecked) {
					label = 4;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Blogs')]/span/input[2]",
					RuntimeVariables.replace("Blogs"));

			case 4:
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Blogs')]/span/input[2]"));

				boolean bookmarksChecked = selenium.isChecked(
						"//span/span[contains(.,'Bookmarks')]/span/input[2]");

				if (bookmarksChecked) {
					label = 5;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Bookmarks')]/span/input[2]",
					RuntimeVariables.replace("Bookmarks"));

			case 5:
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Bookmarks')]/span/input[2]"));

				boolean calendarChecked = selenium.isChecked(
						"//span/span[contains(.,'Calendar')]/span/input[2]");

				if (calendarChecked) {
					label = 6;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Calendar')]/span/input[2]",
					RuntimeVariables.replace("Calendar"));

			case 6:
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Calendar')]/span/input[2]"));

				boolean documentLibraryChecked = selenium.isChecked(
						"//span/span[contains(.,'Documents and Media')]/span/input[2]");

				if (documentLibraryChecked) {
					label = 7;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Documents and Media')]/span/input[2]",
					RuntimeVariables.replace("DocumentLibrary"));

			case 7:
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Documents and Media')]/span/input[2]"));

				boolean documentLibraryDisplayChecked = selenium.isChecked(
						"//span/span[contains(.,'Documents and Media Display')]/span/input[2]");

				if (documentLibraryDisplayChecked) {
					label = 8;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Documents and Media Display')]/span/input[2]",
					RuntimeVariables.replace("DocumentLibraryDisplay"));

			case 8:
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Documents and Media Display')]/span/input[2]"));

				boolean dynamicDataListDisplayChecked = selenium.isChecked(
						"//span/span[contains(.,'Dynamic Data List Display')]/span/input[2]");

				if (dynamicDataListDisplayChecked) {
					label = 9;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Dynamic Data List Display')]/span/input[2]",
					RuntimeVariables.replace("Dynamic Data List Display"));

			case 9:
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Dynamic Data List Display')]/span/input[2]"));

				boolean dynamicDataListsChecked = selenium.isChecked(
						"//span/span[contains(.,'Dynamic Data Lists')]/span/input[2]");

				if (dynamicDataListsChecked) {
					label = 10;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Dynamic Data Lists')]/span/input[2]",
					RuntimeVariables.replace("Dynamic Data Lists"));

			case 10:
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Dynamic Data Lists')]/span/input[2]"));

				boolean dynamicDataMappingChecked = selenium.isChecked(
						"//span/span[contains(.,'Dynamic Data Mapping')]/span/input[2]");

				if (dynamicDataMappingChecked) {
					label = 11;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Dynamic Data Mapping')]/span/input[2]",
					RuntimeVariables.replace("Dynamic Data Mapping"));

			case 11:
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Dynamic Data Mapping')]/span/input[2]"));

				boolean messageBoardsChecked = selenium.isChecked(
						"//span/span[contains(.,'Message Boards')]/span/input[2]");

				if (messageBoardsChecked) {
					label = 12;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Message Boards')]/span/input[2]",
					RuntimeVariables.replace("Message Boards"));

			case 12:
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Message Boards')]/span/input[2]"));

				boolean pageCommentsChecked = selenium.isChecked(
						"//span/span[contains(.,'Page Comments')]/span/input[2]");

				if (pageCommentsChecked) {
					label = 13;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Page Comments')]/span/input[2]",
					RuntimeVariables.replace("Page Comments"));

			case 13:
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Page Comments')]/span/input[2]"));

				boolean pageRatingsChecked = selenium.isChecked(
						"//span/span[contains(.,'Page Ratings')]/span/input[2]");

				if (pageRatingsChecked) {
					label = 14;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Page Ratings')]/span/input[2]",
					RuntimeVariables.replace("Page Ratings"));

			case 14:
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Page Ratings')]/span/input[2]"));

				boolean pollsChecked = selenium.isChecked(
						"//span/span[contains(.,'Polls')]/span/input[2]");

				if (pollsChecked) {
					label = 15;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Polls')]/span/input[2]",
					RuntimeVariables.replace("Polls"));

			case 15:
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Polls')]/span/input[2]"));

				boolean pollsDisplayChecked = selenium.isChecked(
						"//span/span[contains(.,'Polls Display')]/span/input[2]");

				if (pollsDisplayChecked) {
					label = 16;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Polls Display')]/span/input[2]",
					RuntimeVariables.replace("Polls Display"));

			case 16:
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Polls Display')]/span/input[2]"));

				boolean webContentChecked = selenium.isChecked(
						"//span/span[contains(.,'Web Content')]/span/input[2]");

				if (webContentChecked) {
					label = 17;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Web Content')]/span/input[2]",
					RuntimeVariables.replace("Web Content"));

			case 17:
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Web Content')]/span/input[2]"));

				boolean webContentDisplayChecked = selenium.isChecked(
						"//span/span[contains(.,'Web Content Display')]/span/input[2]");

				if (webContentDisplayChecked) {
					label = 18;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Web Content Display')]/span/input[2]",
					RuntimeVariables.replace("Web Content Display"));

			case 18:
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Web Content Display')]/span/input[2]"));

				boolean wikiChecked = selenium.isChecked(
						"//span/span[contains(.,'Wiki')]/span/input[2]");

				if (wikiChecked) {
					label = 19;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Wiki')]/span/input[2]",
					RuntimeVariables.replace("Wiki"));

			case 19:
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Wiki')]/span/input[2]"));

				boolean wikiDisplayChecked = selenium.isChecked(
						"//span/span[contains(.,'Wiki Display')]/span/input[2]");

				if (wikiDisplayChecked) {
					label = 20;

					continue;
				}

				selenium.clickAt("//span/span[contains(.,'Wiki Display')]/span/input[2]",
					RuntimeVariables.replace("Wiki Display Checked"));

			case 20:
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Wiki Display')]/span/input[2]"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace("Site Name (Staging)"),
					selenium.getText("//h1[@class='header-title']/span"));
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Enabled On Public Pages')]/span/input[2]"));
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Application Display Templates')]/span/input[2]"));
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Blogs')]/span/input[2]"));
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Bookmarks')]/span/input[2]"));
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Calendar')]/span/input[2]"));
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Documents and Media')]/span/input[2]"));
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Documents and Media Display')]/span/input[2]"));
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Dynamic Data List Display')]/span/input[2]"));
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Dynamic Data Lists')]/span/input[2]"));
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Dynamic Data Mapping')]/span/input[2]"));
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Message Boards')]/span/input[2]"));
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Page Comments')]/span/input[2]"));
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Page Ratings')]/span/input[2]"));
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Polls')]/span/input[2]"));
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Polls Display')]/span/input[2]"));
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Web Content')]/span/input[2]"));
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Web Content Display')]/span/input[2]"));
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Wiki')]/span/input[2]"));
				assertTrue(selenium.isChecked(
						"//span/span[contains(.,'Wiki Display')]/span/input[2]"));

			case 100:
				label = -1;
			}
		}
	}
}