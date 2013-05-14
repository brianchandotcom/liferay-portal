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

package com.liferay.portalweb.portal.permissions.imagegallery.assertactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownMediaGalleryTest extends BaseTestCase {
	public void testTearDownMediaGallery() throws Exception {
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
				selenium.clickAt("link=Documents and Media",
					RuntimeVariables.replace("Documents and Media"));
				selenium.waitForPageToLoad("30000");

				boolean dmMediaNotDeleted = selenium.isElementPresent(
						"//div[@class='entry-thumbnail']");

				if (!dmMediaNotDeleted) {
					label = 2;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[contains(@id,'allRowIdsCheckbox')]"));
				selenium.clickAt("//input[contains(@id,'allRowIdsCheckbox')]",
					RuntimeVariables.replace("All Entries Check Box"));
				assertTrue(selenium.isChecked(
						"//input[contains(@id,'allRowIdsCheckbox')]"));
				selenium.waitForVisible(
					"//span[@title='Actions']/ul/li/strong/a/span");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Move to the Recycle Bin')]/a");
				assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Move to the Recycle Bin')]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Move to the Recycle Bin')]/a"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isPartialText(
						"//div[@class='portlet-msg-success taglib-trash-undo']",
						"moved to the Recycle Bin. Undo"));

			case 2:
				assertEquals(RuntimeVariables.replace(
						"There are no documents or media files in this folder."),
					selenium.getText(
						"//div[@class='entries-empty portlet-msg-info']"));
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
				selenium.clickAt("link=Recycle Bin",
					RuntimeVariables.replace("Recycle Bin"));
				selenium.waitForPageToLoad("30000");

				boolean dmMediaNotEmptied = selenium.isElementPresent(
						"//a[@class='trash-empty-link']");

				if (!dmMediaNotEmptied) {
					label = 3;

					continue;
				}

				selenium.clickAt("//a[@class='trash-empty-link']",
					RuntimeVariables.replace("Empty the Recycle Bin"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to empty the Recycle Bin?");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 3:
				assertEquals(RuntimeVariables.replace(
						"The Recycle Bin is empty."),
					selenium.getText("//div[@class='portlet-msg-info']"));

			case 100:
				label = -1;
			}
		}
	}
}