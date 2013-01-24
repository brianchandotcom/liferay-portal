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

package com.liferay.portalweb.portlet.mediagallery.dmimage.deletedmsubfolderimagemg;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DeleteDMSubfolderImageMGTest extends BaseTestCase {
	public void testDeleteDMSubfolderImageMG() throws Exception {
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
				assertEquals(RuntimeVariables.replace("DM Folder Name"),
					selenium.getText("//span[@class='image-title']"));
				selenium.clickAt("//span[@class='image-title']",
					RuntimeVariables.replace("DM Folder Name"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("DM Subfolder Name"),
					selenium.getText("//span[@class='image-title']"));
				selenium.clickAt("//span[@class='image-title']",
					RuntimeVariables.replace("DM Subfolder Name"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"DM Subfolder Image Title"),
					selenium.getText("//span[@class='image-title']"));
				selenium.clickAt("//span[@class='image-title']",
					RuntimeVariables.replace("DM Subfolder Image Title"));
				selenium.waitForVisible("//img");
				assertTrue(selenium.isVisible("//img"));
				selenium.waitForText("//div[contains(@class,'image-viewer-caption')]",
					"DM Subfolder Image Title - DM Subfolder Image Description");
				assertEquals(RuntimeVariables.replace(
						"DM Subfolder Image Title - DM Subfolder Image Description"),
					selenium.getText(
						"//div[contains(@class,'image-viewer-caption')]"));
				selenium.clickAt("//img[@alt='Move to the Recycle Bin']",
					RuntimeVariables.replace("Move to the Recycle Bin"));
				selenium.waitForText("//div[@class='portlet-msg-success taglib-trash-undo']",
					"The selected item was moved to the Recycle Bin. Undo");
				assertEquals(RuntimeVariables.replace(
						"The selected item was moved to the Recycle Bin. Undo"),
					selenium.getText(
						"//div[@class='portlet-msg-success taglib-trash-undo']"));
				assertEquals(RuntimeVariables.replace("DM Subfolder Name"),
					selenium.getText("//h1[@class='header-title']/span"));
				assertEquals(RuntimeVariables.replace(
						"There are no media files in this folder."),
					selenium.getText("//div[@class='portlet-msg-info']"));
				assertFalse(selenium.isTextPresent("DM Subfolder Image Title"));
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
				assertEquals(RuntimeVariables.replace(
						"DM Subfolder Image Title"),
					selenium.getText(
						"//tr[contains(.,'DM Subfolder Image Title')]/td[1]/span/a/span"));
				assertEquals(RuntimeVariables.replace("Document"),
					selenium.getText(
						"//tr[contains(.,'DM Subfolder Image Title')]/td[2]"));

				boolean recycleBinEmpty = selenium.isElementPresent(
						"//a[@class='trash-empty-link']");

				if (!recycleBinEmpty) {
					label = 2;

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

			case 2:
				assertEquals(RuntimeVariables.replace(
						"The Recycle Bin is empty."),
					selenium.getText("//div[@class='portlet-msg-info']"));

			case 100:
				label = -1;
			}
		}
	}
}