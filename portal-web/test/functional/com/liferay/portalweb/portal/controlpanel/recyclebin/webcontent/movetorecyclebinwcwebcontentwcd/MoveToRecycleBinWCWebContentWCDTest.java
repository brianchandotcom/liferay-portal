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

package com.liferay.portalweb.portal.controlpanel.recyclebin.webcontent.movetorecyclebinwcwebcontentwcd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class MoveToRecycleBinWCWebContentWCDTest extends BaseTestCase {
	public void testMoveToRecycleBinWCWebContentWCD() throws Exception {
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
				assertEquals(RuntimeVariables.replace("WC WebContent Title"),
					selenium.getText(
						"//span[@class='entry-title']/span[contains(.,'WC WebContent Title')]"));

				boolean webContentArticleCheckbox = selenium.isChecked(
						"//input[@id='_15_rowIdsJournalArticleCheckbox']");

				if (webContentArticleCheckbox) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_15_rowIdsJournalArticleCheckbox']",
					RuntimeVariables.replace("Web Content Article Checkbox"));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@id='_15_rowIdsJournalArticleCheckbox']"));
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Move to the Recycle Bin')]");
				assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Move to the Recycle Bin')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Move to the Recycle Bin')]"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"The selected item was moved to the Recycle Bin. Undo"),
					selenium.getText(
						"//div[@class='portlet-msg-success taglib-trash-undo']"));
				assertEquals(RuntimeVariables.replace(
						"No Web Content was found."),
					selenium.getText(
						"//div[@class='entries-empty portlet-msg-info']"));
				assertFalse(selenium.isTextPresent("WC WebContent Title"));

			case 100:
				label = -1;
			}
		}
	}
}