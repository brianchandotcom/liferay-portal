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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.stagingcommunity.quartz;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ActivateStagingCommunityQuartzTest extends BaseTestCase {
	public void testActivateStagingCommunityQuartz() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				assertEquals(RuntimeVariables.replace("Go to"),
					selenium.getText("//li[@id='_145_mySites']/a/span"));
				selenium.mouseOver("//li[@id='_145_mySites']/a/span");
				selenium.waitForVisible("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Sites", RuntimeVariables.replace("Sites"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_134_name']",
					RuntimeVariables.replace(
						"Community Staging Community Quartz"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Community Staging Community Quartz"),
					selenium.getText("//tr[3]/td[1]"));
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a");
				assertEquals(RuntimeVariables.replace("Edit Settings"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isPartialText(
						"//a[@id='_165_stagingLink']", "Staging"));
				selenium.clickAt("//a[@id='_165_stagingLink']",
					RuntimeVariables.replace("Staging"));
				selenium.waitForVisible("//input[@id='_165_local']");
				selenium.clickAt("//input[@id='_165_local']",
					RuntimeVariables.replace("Local Live"));
				assertTrue(selenium.isChecked("//input[@id='_165_local']"));
				assertFalse(selenium.isChecked(
						"//input[@id='_165_branchingPublicCheckbox']"));
				selenium.clickAt("//input[@id='_165_branchingPublicCheckbox']",
					RuntimeVariables.replace("Enabled On Public Pages"));
				assertTrue(selenium.isChecked(
						"//input[@id='_165_branchingPublicCheckbox']"));
				assertFalse(selenium.isChecked(
						"//input[@id='_165_branchingPrivateCheckbox']"));
				selenium.clickAt("//input[@id='_165_branchingPrivateCheckbox']",
					RuntimeVariables.replace("Enabled On Private Pages"));
				assertTrue(selenium.isChecked(
						"//input[@id='_165_branchingPrivateCheckbox']"));

				boolean blogsChecked = selenium.isChecked(
						"_165_staged-portlet_161Checkbox");

				if (blogsChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_161Checkbox']",
					RuntimeVariables.replace("Blogs"));

			case 2:

				boolean bookmarksChecked = selenium.isChecked(
						"_165_staged-portlet_28Checkbox");

				if (bookmarksChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_28Checkbox']",
					RuntimeVariables.replace("Bookmarks"));

			case 3:

				boolean calendarChecked = selenium.isChecked(
						"_165_staged-portlet_8Checkbox");

				if (calendarChecked) {
					label = 4;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_8Checkbox']",
					RuntimeVariables.replace("Calendar"));

			case 4:

				boolean documentLibraryDisplayChecked = selenium.isChecked(
						"_165_staged-portlet_110Checkbox");

				if (documentLibraryDisplayChecked) {
					label = 5;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_110Checkbox']",
					RuntimeVariables.replace("DocumentLibraryDisplay"));

			case 5:

				boolean dynamicDataMappingChecked = selenium.isChecked(
						"_165_staged-portlet_166Checkbox");

				if (dynamicDataMappingChecked) {
					label = 6;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_166Checkbox']",
					RuntimeVariables.replace("Dynamic Data Mapping"));

			case 6:

				boolean messageBoardsChecked = selenium.isChecked(
						"_165_staged-portlet_19Checkbox");

				if (messageBoardsChecked) {
					label = 7;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_19Checkbox']",
					RuntimeVariables.replace("Message Boards"));

			case 7:

				boolean pageCommentsChecked = selenium.isChecked(
						"_165_staged-portlet_107Checkbox");

				if (pageCommentsChecked) {
					label = 8;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_107Checkbox']",
					RuntimeVariables.replace("Page Comments"));

			case 8:

				boolean pageRatingsChecked = selenium.isChecked(
						"_165_staged-portlet_108Checkbox");

				if (pageRatingsChecked) {
					label = 9;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_108Checkbox']",
					RuntimeVariables.replace("Page Ratings"));

			case 9:

				boolean pollsChecked = selenium.isChecked(
						"_165_staged-portlet_25Checkbox");

				if (pollsChecked) {
					label = 10;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_25Checkbox']",
					RuntimeVariables.replace("Polls"));

			case 10:

				boolean pollsDisplayChecked = selenium.isChecked(
						"_165_staged-portlet_59Checkbox");

				if (pollsDisplayChecked) {
					label = 11;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_59Checkbox']",
					RuntimeVariables.replace("Polls Display"));

			case 11:

				boolean webContentChecked = selenium.isChecked(
						"_165_staged-portlet_15Checkbox");

				if (webContentChecked) {
					label = 12;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_15Checkbox']",
					RuntimeVariables.replace("Web Content"));

			case 12:

				boolean webContentDisplayChecked = selenium.isChecked(
						"_165_staged-portlet_56Checkbox");

				if (webContentDisplayChecked) {
					label = 13;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_56Checkbox']",
					RuntimeVariables.replace("Web Content Display"));

			case 13:

				boolean wikiChecked = selenium.isChecked(
						"_165_staged-portlet_36Checkbox");

				if (wikiChecked) {
					label = 14;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_36Checkbox']",
					RuntimeVariables.replace("Wiki"));

			case 14:

				boolean wikiDisplayChecked = selenium.isChecked(
						"_165_staged-portlet_54Checkbox");

				if (wikiDisplayChecked) {
					label = 15;

					continue;
				}

				selenium.clickAt("//input[@id='_165_staged-portlet_54Checkbox']",
					RuntimeVariables.replace("Wiki Display Checked"));

			case 15:
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace(
						"Community Staging Community Quartz (Staging)"),
					selenium.getText("//li[4]/span"));

			case 100:
				label = -1;
			}
		}
	}
}