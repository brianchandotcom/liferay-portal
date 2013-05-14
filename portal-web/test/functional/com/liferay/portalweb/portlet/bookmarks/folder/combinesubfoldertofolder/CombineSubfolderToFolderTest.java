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

package com.liferay.portalweb.portlet.bookmarks.folder.combinesubfoldertofolder;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class CombineSubfolderToFolderTest extends BaseTestCase {
	public void testCombineSubfolderToFolder() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Bookmarks Test Page",
					RuntimeVariables.replace("Bookmarks Test Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Bookmark Folder Name"),
					selenium.getText(
						"//tr[contains(.,'Bookmark Folder Name')]/td[1]/a/strong"));
				selenium.clickAt("//tr[contains(.,'Bookmark Folder Name')]/td[1]/a/strong",
					RuntimeVariables.replace("Bookmark Folder Name"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Bookmark Subfolder Name"),
					selenium.getText(
						"//tr[contains(.,'Bookmark Subfolder Name')]/td[1]/a/strong"));
				assertFalse(selenium.isTextPresent("Bookmark Name"));
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]");
				assertEquals(RuntimeVariables.replace("Edit"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]",
					RuntimeVariables.replace("Edit"));
				selenium.waitForPageToLoad("30000");

				boolean mergeParentFolderChecked = selenium.isChecked(
						"//input[@id='_28_mergeWithParentFolderCheckbox']");

				if (mergeParentFolderChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_28_mergeWithParentFolderCheckbox']",
					RuntimeVariables.replace("Merge with Parent Folder"));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@id='_28_mergeWithParentFolderCheckbox']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace("0 Subfolders"),
					selenium.getText(
						"//div[@class='lfr-asset-icon lfr-asset-subfolders']"));
				assertEquals(RuntimeVariables.replace("Bookmark Name"),
					selenium.getText(
						"//tr[contains(.,'Bookmark Name')]/td[1]/a"));
				assertEquals(RuntimeVariables.replace("http://www.liferay.com"),
					selenium.getText(
						"//tr[contains(.,'Bookmark Name')]/td[2]/a"));
				assertFalse(selenium.isTextPresent("Bookmark Subfolder Name"));

			case 100:
				label = -1;
			}
		}
	}
}