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

package com.liferay.portalweb.portlet.mediagallery.dmfolder.adddmfoldermg;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownDMFolderMGTest extends BaseTestCase {
	public void testTearDownDMFolderMG() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Media Gallery Test Page",
					RuntimeVariables.replace("Media Gallery Test Page"));
				selenium.waitForPageToLoad("30000");

				boolean mgFolder1Present = selenium.isElementPresent(
						"//span[@class='image-thumbnail']");

				if (!mgFolder1Present) {
					label = 2;

					continue;
				}

				selenium.clickAt("//span[@class='image-thumbnail']",
					RuntimeVariables.replace("DM Folder 1"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
					selenium.getText(
						"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Move to the Recycle Bin')]"));
				selenium.clickAt("//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Move to the Recycle Bin')]",
					RuntimeVariables.replace("Move to the Recycle Bin"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"The selected item was moved to the Recycle Bin. Undo"),
					selenium.getText(
						"//div[@class='portlet-msg-success taglib-trash-undo']"));

				boolean mgFolder2Present = selenium.isElementPresent(
						"//span[@class='image-thumbnail']");

				if (!mgFolder2Present) {
					label = 3;

					continue;
				}

				Thread.sleep(1000);
				selenium.clickAt("//span[@class='image-thumbnail']",
					RuntimeVariables.replace("DM Folder 2"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
					selenium.getText(
						"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Move to the Recycle Bin')]"));
				selenium.clickAt("//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Move to the Recycle Bin')]",
					RuntimeVariables.replace("Move to the Recycle Bin"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"The selected item was moved to the Recycle Bin. Undo"),
					selenium.getText(
						"//div[@class='portlet-msg-success taglib-trash-undo']"));

				boolean mgFolder3Present = selenium.isElementPresent(
						"//span[@class='image-thumbnail']");

				if (!mgFolder3Present) {
					label = 4;

					continue;
				}

				Thread.sleep(1000);
				selenium.clickAt("//span[@class='image-thumbnail']",
					RuntimeVariables.replace("DM Folder 3"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
					selenium.getText(
						"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Move to the Recycle Bin')]"));
				selenium.clickAt("//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Move to the Recycle Bin')]",
					RuntimeVariables.replace("Move to the Recycle Bin"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"The selected item was moved to the Recycle Bin. Undo"),
					selenium.getText(
						"//div[@class='portlet-msg-success taglib-trash-undo']"));

				boolean mgFolder4Present = selenium.isElementPresent(
						"//span[@class='image-thumbnail']");

				if (!mgFolder4Present) {
					label = 5;

					continue;
				}

				Thread.sleep(1000);
				selenium.clickAt("//span[@class='image-thumbnail']",
					RuntimeVariables.replace("DM Folder 4"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
					selenium.getText(
						"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Move to the Recycle Bin')]"));
				selenium.clickAt("//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Move to the Recycle Bin')]",
					RuntimeVariables.replace("Move to the Recycle Bin"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"The selected item was moved to the Recycle Bin. Undo"),
					selenium.getText(
						"//div[@class='portlet-msg-success taglib-trash-undo']"));

				boolean mgFolder5Present = selenium.isElementPresent(
						"//span[@class='image-thumbnail']");

				if (!mgFolder5Present) {
					label = 6;

					continue;
				}

				Thread.sleep(1000);
				selenium.clickAt("//span[@class='image-thumbnail']",
					RuntimeVariables.replace("DM Folder 5"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
					selenium.getText(
						"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Move to the Recycle Bin')]"));
				selenium.clickAt("//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Move to the Recycle Bin')]",
					RuntimeVariables.replace("Move to the Recycle Bin"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"The selected item was moved to the Recycle Bin. Undo"),
					selenium.getText(
						"//div[@class='portlet-msg-success taglib-trash-undo']"));

			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
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

				boolean recycleBinEmpty = selenium.isElementPresent(
						"//a[@class='trash-empty-link']");

				if (!recycleBinEmpty) {
					label = 7;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Empty the Recycle Bin"),
					selenium.getText("//a[@class='trash-empty-link']"));
				selenium.clickAt("//a[@class='trash-empty-link']",
					RuntimeVariables.replace("Empty the Recycle Bin"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to empty the Recycle Bin?");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 7:
				assertEquals(RuntimeVariables.replace(
						"The Recycle Bin is empty."),
					selenium.getText("//div[@class='portlet-msg-info']"));

			case 100:
				label = -1;
			}
		}
	}
}