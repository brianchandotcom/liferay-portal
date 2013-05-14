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

package com.liferay.portalweb.portlet.documentsandmedia.dmfolder.adddmfolder;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownDMFolderTest extends BaseTestCase {
	public void testTearDownDMFolder() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Documents and Media Test Page",
					RuntimeVariables.replace("Documents and Media Test Page"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(1000);
				selenium.waitForVisible("//button[@title='Icon View']");
				selenium.clickAt("//button[@title='Icon View']",
					RuntimeVariables.replace("Icon View"));
				Thread.sleep(1000);
				selenium.waitForVisible(
					"//button[contains(@class,'aui-state-active') and @title='Icon View']");
				assertTrue(selenium.isVisible(
						"//button[contains(@class,'aui-state-active') and @title='Icon View']"));

				boolean dmFolderNotRecycled = selenium.isElementPresent(
						"//a[contains(@class,'entry-link')]/span[@class='entry-title']");

				if (!dmFolderNotRecycled) {
					label = 2;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@id='_20_allRowIdsCheckbox']"));
				selenium.clickAt("//input[@id='_20_allRowIdsCheckbox']",
					RuntimeVariables.replace("All Entries Check Box"));
				assertTrue(selenium.isChecked(
						"//input[@id='_20_allRowIdsCheckbox']"));
				selenium.waitForVisible(
					"//div[@id='_20_entries']/div[contains(@class,'selected')]");
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

				boolean recycleBinPresent = selenium.isElementPresent(
						"//form[@id='_182_emptyForm']/a");

				if (!recycleBinPresent) {
					label = 3;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Empty the Recycle Bin"),
					selenium.getText("//form[@id='_182_emptyForm']/a"));
				selenium.clickAt("//form[@id='_182_emptyForm']/a",
					RuntimeVariables.replace("Empty the Recycle Bin"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to empty the Recycle Bin[\\s\\S]$"));

			case 3:
			case 100:
				label = -1;
			}
		}
	}
}