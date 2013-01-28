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

package com.liferay.portalweb.portal.permissions.imagegallery.assertactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SiteAdmin_RestoreGuestViewFolderTest extends BaseTestCase {
	public void testSiteAdmin_RestoreGuestViewFolder()
		throws Exception {
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
				assertEquals(RuntimeVariables.replace(
						"Media Gallery Permissions Test Folder"),
					selenium.getText("xpath=(//span[@class='image-title'])[1]"));
				selenium.clickAt("xpath=(//span[@class='image-title'])[1]",
					RuntimeVariables.replace(
						"Media Gallery Permissions Test Folder"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Permissions"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Permissions')]/a"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Permissions')]/a",
					RuntimeVariables.replace("Permissions"));
				selenium.waitForPageToLoad("30000");

				boolean guestViewChecked = selenium.isChecked(
						"//input[@id='guest_ACTION_VIEW']");

				if (guestViewChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='guest_ACTION_VIEW']",
					RuntimeVariables.replace("Guest View"));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@id='guest_ACTION_VIEW']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertTrue(selenium.isChecked(
						"//input[@id='guest_ACTION_VIEW']"));

			case 100:
				label = -1;
			}
		}
	}
}