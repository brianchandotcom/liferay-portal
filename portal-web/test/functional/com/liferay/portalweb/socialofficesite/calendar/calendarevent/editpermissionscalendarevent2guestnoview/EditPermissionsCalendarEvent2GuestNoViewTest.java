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

package com.liferay.portalweb.socialofficesite.calendar.calendarevent.editpermissionscalendarevent2guestnoview;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditPermissionsCalendarEvent2GuestNoViewTest extends BaseTestCase {
	public void testEditPermissionsCalendarEvent2GuestNoView()
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
				assertEquals(RuntimeVariables.replace("Calendar"),
					selenium.getText(
						"//nav/ul/li[contains(.,'Calendar')]/a/span"));
				selenium.clickAt("//nav/ul/li[contains(.,'Calendar')]/a/span",
					RuntimeVariables.replace("Calendar"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Events",
					RuntimeVariables.replace("Events"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//td[@id='_8_ocerSearchContainer_col-time_row-2']"));
				assertEquals(RuntimeVariables.replace("Calendar Event2 Title"),
					selenium.getText(
						"//td[@id='_8_ocerSearchContainer_col-title_row-2']"));
				assertEquals(RuntimeVariables.replace("Anniversary"),
					selenium.getText(
						"//td[@id='_8_ocerSearchContainer_col-type_row-2']"));
				Thread.sleep(1000);
				selenium.waitForVisible(
					"xPath=(//td[contains(.,'Actions')]/span/ul/li/strong/a)[2]");
				selenium.clickAt("xPath=(//td[contains(.,'Actions')]/span/ul/li/strong/a)[2]",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Permissions')]");
				assertEquals(RuntimeVariables.replace("Permissions"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Permissions')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Permissions')]",
					RuntimeVariables.replace("Permissions"));
				selenium.waitForPageToLoad("30000");
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