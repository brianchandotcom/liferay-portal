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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcfolder.deletewcfolder;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DeleteWCFolderTest extends BaseTestCase {
	public void testDeleteWCFolder() throws Exception {
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
				selenium.clickAt("link=Web Content",
					RuntimeVariables.replace("Web Content"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isVisible(
						"//div[@data-title='WC Folder Name']/a/div[@class='entry-thumbnail']/img"));
				assertEquals(RuntimeVariables.replace("WC Folder Name"),
					selenium.getText(
						"//div[@data-title='WC Folder Name']/a/span[@class='entry-title']"));

				boolean wcFolderChecked = selenium.isChecked(
						"//input[@id='_15_rowIdsJournalFolderCheckbox']");

				if (wcFolderChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_15_rowIdsJournalFolderCheckbox']",
					RuntimeVariables.replace("WC Folder Name"));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@id='_15_rowIdsJournalFolderCheckbox']"));
				selenium.waitForElementPresent(
					"//div[contains(@class,'display-style display-icon selectable hover selected') and @data-title='WC Folder Name']");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//span[@title='Actions']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Move to the Recycle Bin')]");
				assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Move to the Recycle Bin')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Move to the Recycle Bin')]",
					RuntimeVariables.replace("Move to the Recycle Bin"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible(
					"//div[@class='portlet-msg-success taglib-trash-undo']");
				assertTrue(selenium.isPartialText(
						"//div[@class='portlet-msg-success taglib-trash-undo']",
						"moved to the Recycle Bin. Undo"));
				assertEquals(RuntimeVariables.replace(
						"No Web Content was found."),
					selenium.getText(
						"//div[@class='entries-empty portlet-msg-info']"));
				assertTrue(selenium.isElementNotPresent(
						"//div[@data-title='WC Folder Name']/a/div[@class='entry-thumbnail']/img"));
				assertFalse(selenium.isTextPresent("WC Folder Name"));
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
				assertEquals(RuntimeVariables.replace("WC Folder Name"),
					selenium.getText(
						"//tr[contains(.,'WC Folder Name')]/td[1]/span/a/span"));
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
				assertEquals(RuntimeVariables.replace(
						"The Recycle Bin is empty."),
					selenium.getText("//div[@class='portlet-msg-info']"));

			case 100:
				label = -1;
			}
		}
	}
}