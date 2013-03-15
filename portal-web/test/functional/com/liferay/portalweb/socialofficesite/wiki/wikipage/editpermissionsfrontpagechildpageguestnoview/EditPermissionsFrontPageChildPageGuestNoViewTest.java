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

package com.liferay.portalweb.socialofficesite.wiki.wikipage.editpermissionsfrontpagechildpageguestnoview;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditPermissionsFrontPageChildPageGuestNoViewTest
	extends BaseTestCase {
	public void testEditPermissionsFrontPageChildPageGuestNoView()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/user/joebloggs/so/dashboard/");
				assertEquals(RuntimeVariables.replace("Sites"),
					selenium.getText("//div[@id='so-sidebar']/h3"));
				assertTrue(selenium.isVisible("//input[@class='search-input']"));
				selenium.type("//input[@class='search-input']",
					RuntimeVariables.replace("Open"));
				Thread.sleep(1000);
				assertEquals(RuntimeVariables.replace("Open Site Name"),
					selenium.getText(
						"//li[contains(@class, 'social-office-enabled')]/span[2]/a"));
				selenium.clickAt("//li[contains(@class, 'social-office-enabled')]/span[2]/a",
					RuntimeVariables.replace("Open Site Name"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Wiki"),
					selenium.getText("//nav/ul/li[contains(.,'Wiki')]/a/span"));
				selenium.clickAt("//nav/ul/li[contains(.,'Wiki')]/a/span",
					RuntimeVariables.replace("Wiki"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Wiki Front Page Child Page Title"),
					selenium.getText("//div[@class='child-pages']/ul/li/a"));
				selenium.clickAt("//div[@class='child-pages']/ul/li/a",
					RuntimeVariables.replace("Wiki Front Page Child Page Title"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Details"),
					selenium.getText(
						"//div[@class='page-actions top-actions']/span/a/span[contains(.,'Details')]"));
				selenium.clickAt("//div[@class='page-actions top-actions']/span/a/span[contains(.,'Details')]",
					RuntimeVariables.replace("Details"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Permissions"),
					selenium.getText("//span[contains(.,'Permissions')]"));
				selenium.clickAt("//span[contains(.,'Permissions')]",
					RuntimeVariables.replace("Permissions"));
				selenium.waitForVisible("//input[@id='guest_ACTION_VIEW']");

				boolean guestActionChecked = selenium.isChecked(
						"//input[@id='guest_ACTION_VIEW']");

				if (!guestActionChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='guest_ACTION_VIEW']",
					RuntimeVariables.replace("Guest Action Checkbox"));

			case 2:
				assertFalse(selenium.isChecked(
						"//input[@id='guest_ACTION_VIEW']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 100:
				label = -1;
			}
		}
	}
}