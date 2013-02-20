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

package com.liferay.portalweb.socialofficehome.privatemessaging.message.addpmmessageportal;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPagePMTest extends BaseTestCase {
	public void testAddPagePM() throws Exception {
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
				assertEquals(RuntimeVariables.replace("Add"),
					selenium.getText("//li[@id='_145_addContent']/a/span"));
				selenium.mouseOver("//li[@id='_145_addContent']/a/span");
				selenium.waitForVisible("//a[@id='addPage']");
				assertEquals(RuntimeVariables.replace("Page"),
					selenium.getText("//a[@id='addPage']"));
				selenium.clickAt("//a[@id='addPage']",
					RuntimeVariables.replace("Page"));
				selenium.waitForVisible("//input[@type='text']");
				selenium.type("//input[@type='text']",
					RuntimeVariables.replace("Private Messaging Test Page"));

				boolean newSaveButtonPresent = selenium.isElementPresent(
						"//button[contains(@id,'Save')]");

				if (!newSaveButtonPresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("//button[contains(@id,'Save')]",
					RuntimeVariables.replace("Save"));

			case 2:

				boolean oldSaveButtonPresent = selenium.isElementPresent(
						"//button[contains(@id,'save')]");

				if (!oldSaveButtonPresent) {
					label = 3;

					continue;
				}

				selenium.clickAt("//button[contains(@id,'save')]",
					RuntimeVariables.replace("Save"));

			case 3:
				selenium.waitForVisible("link=Private Messaging Test Page");
				selenium.clickAt("link=Private Messaging Test Page",
					RuntimeVariables.replace("Private Messaging Test Page"));
				selenium.waitForPageToLoad("30000");

			case 100:
				label = -1;
			}
		}
	}
}