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

package com.liferay.portalweb.portal.permissions.documentsandmedia.document.useradddmdocumentinline;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class PermissionsUserAddDocumentOffTest extends BaseTestCase {
	public void testPermissionsUserAddDocumentOff() throws Exception {
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
				assertEquals(RuntimeVariables.replace("Home"),
					selenium.getText("//span[@class='entry-title']"));
				selenium.clickAt("//span/span/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a");
				assertEquals(RuntimeVariables.replace("Permissions"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("RegularRole Name"),
					selenium.getText("//tr[6]/td[1]/a"));
				assertEquals(RuntimeVariables.replace("Add Document"),
					selenium.getText("//tr[1]/th[2]"));

				boolean addDocumentPermissionsChecked = selenium.isChecked(
						"//tr[6]/td[2]/input");

				if (!addDocumentPermissionsChecked) {
					label = 2;

					continue;
				}

				assertTrue(selenium.isChecked("//tr[6]/td[2]/input"));
				selenium.clickAt("//tr[6]/td[2]/input",
					RuntimeVariables.replace("Add Document Permission"));

			case 2:
				assertFalse(selenium.isChecked("//tr[6]/td[2]/input"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertFalse(selenium.isChecked("//tr[6]/td[2]/input"));

			case 100:
				label = -1;
			}
		}
	}
}