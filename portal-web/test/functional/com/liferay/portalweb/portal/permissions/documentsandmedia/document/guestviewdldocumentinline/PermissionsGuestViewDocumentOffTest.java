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

package com.liferay.portalweb.portal.permissions.documentsandmedia.document.guestviewdldocumentinline;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class PermissionsGuestViewDocumentOffTest extends BaseTestCase {
	public void testPermissionsGuestViewDocumentOff() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.waitForVisible("link=Documents and Media Test Page");
				selenium.clickAt("link=Documents and Media Test Page",
					RuntimeVariables.replace("Documents and Media Test Page"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("DM Document Title"),
					selenium.getText(
						"//a[@class='document-link']/span[@class='entry-title']"));
				selenium.clickAt("xPath=(//span[contains(@class,'document-action')])[2]/span/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a");
				assertEquals(RuntimeVariables.replace("Permissions"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Guest"),
					selenium.getText("//tr[3]/td[1]/a"));
				assertEquals(RuntimeVariables.replace("View"),
					selenium.getText("//tr[1]/th[8]"));

				boolean viewDocumentPermissionsChecked = selenium.isChecked(
						"//tr[3]/td[8]/input");

				if (!viewDocumentPermissionsChecked) {
					label = 2;

					continue;
				}

				assertTrue(selenium.isChecked("//tr[3]/td[8]/input"));
				selenium.clickAt("//tr[3]/td[8]/input",
					RuntimeVariables.replace("View Document Permission"));

			case 2:
				assertFalse(selenium.isChecked("//tr[3]/td[8]/input"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertFalse(selenium.isChecked("//tr[3]/td[8]/input"));

			case 100:
				label = -1;
			}
		}
	}
}