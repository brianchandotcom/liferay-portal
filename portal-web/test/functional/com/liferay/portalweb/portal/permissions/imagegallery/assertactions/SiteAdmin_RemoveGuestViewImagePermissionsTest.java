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
public class SiteAdmin_RemoveGuestViewImagePermissionsTest extends BaseTestCase {
	public void testSiteAdmin_RemoveGuestViewImagePermissions()
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
						"Media Gallery Permissions Test Folder 2 Edited"),
					selenium.getText("xpath=(//span[@class='image-title'])[2]"));
				selenium.clickAt("xpath=(//span[@class='image-title'])[2]",
					RuntimeVariables.replace(
						"Media Gallery Permissions Test Folder 2 Edited"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Media Gallery Permissions Test Subfolder 2"),
					selenium.getText("//span[@class='image-title']"));
				selenium.clickAt("//span[@class='image-title']",
					RuntimeVariables.replace(
						"Media Gallery Permissions Test Subfolder 2"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Permissions Image Test Edited"),
					selenium.getText("//span[@class='image-title']"));
				selenium.clickAt("//span[@class='image-title']",
					RuntimeVariables.replace("Permissions Image Test Edited"));
				Thread.sleep(1000);
				selenium.waitForVisible("//img[@title='Permissions']");
				selenium.clickAt("//img[@title='Permissions']",
					RuntimeVariables.replace("Permissions"));
				selenium.waitForVisible("//input[@id='guest_ACTION_VIEW']");

				boolean guestViewNotChecked = selenium.isChecked(
						"//input[@id='guest_ACTION_VIEW']");

				if (!guestViewNotChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='guest_ACTION_VIEW']",
					RuntimeVariables.replace("Guest View"));

			case 2:
				assertFalse(selenium.isChecked(
						"//input[@id='guest_ACTION_VIEW']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertFalse(selenium.isChecked(
						"//input[@id='guest_ACTION_VIEW']"));

			case 100:
				label = -1;
			}
		}
	}
}