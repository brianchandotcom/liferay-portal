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

package com.liferay.portalweb.portal.controlpanel.settings.phonenumber.addsettingsphonenumber;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownSettingsPhoneNumberTest extends BaseTestCase {
	public void testTearDownSettingsPhoneNumber() throws Exception {
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
				selenium.clickAt("link=Portal Settings",
					RuntimeVariables.replace("Portal Settings"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isPartialText(
						"//a[@id='_130_phoneNumbersLink']", "Phone Numbers"));
				selenium.clickAt("//a[@id='_130_phoneNumbersLink']",
					RuntimeVariables.replace("Phone Numbers"));
				selenium.waitForVisible("//input[@id='_130_phoneNumber0']");

				boolean phoneNumber1Presesnt = selenium.isElementPresent(
						"//input[@id='_130_phoneNumber0']");

				if (!phoneNumber1Presesnt) {
					label = 2;

					continue;
				}

				selenium.clickAt("xpath=(//div[@id='_130_phoneNumbers']/fieldset/div[2]/div/span/span/button[2])[1]",
					RuntimeVariables.replace("Delete Row"));

				boolean phoneNumber2Presesnt = selenium.isElementPresent(
						"//input[@id='_130_phoneNumber1']");

				if (!phoneNumber2Presesnt) {
					label = 3;

					continue;
				}

				selenium.clickAt("xpath=(//div[@id='_130_phoneNumbers']/fieldset/div[2]/div/span/span/button[2])[2]",
					RuntimeVariables.replace("Delete Row"));

				boolean phoneNumber3Presesnt = selenium.isElementPresent(
						"//input[@id='_130_phoneNumber2']");

				if (!phoneNumber3Presesnt) {
					label = 4;

					continue;
				}

				selenium.clickAt("xpath=(//div[@id='_130_phoneNumbers']/fieldset/div[2]/div/span/span/button[2])[3]",
					RuntimeVariables.replace("Delete Row"));

				boolean phoneNumber4Presesnt = selenium.isElementPresent(
						"//input[@id='_130_phoneNumber3']");

				if (!phoneNumber4Presesnt) {
					label = 5;

					continue;
				}

				selenium.clickAt("xpath=(//div[@id='_130_phoneNumbers']/fieldset/div[2]/div/span/span/button[2])[4]",
					RuntimeVariables.replace("Delete Row"));

				boolean phoneNumber5Presesnt = selenium.isElementPresent(
						"//input[@id='_130_phoneNumber4']");

				if (!phoneNumber5Presesnt) {
					label = 6;

					continue;
				}

				selenium.clickAt("xpath=(//div[@id='_130_phoneNumbers']/fieldset/div[2]/div/span/span/button[2])[5]",
					RuntimeVariables.replace("Delete Row"));

			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
				selenium.waitForVisible(
					"//div[@id='_130_phoneNumbers']/div[@class='portlet-msg-info']");
				assertTrue(selenium.isPartialText(
						"//div[@id='_130_phoneNumbers']/div[@class='portlet-msg-info']",
						"Phone number and type are required fields."));
				assertTrue(selenium.isPartialText(
						"//div[@id='_130_phoneNumbers']/fieldset/div/div/a[@class='lfr-action-undo']",
						"Undo"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals("",
					selenium.getValue("//input[@id='_130_phoneNumber0']"));

			case 100:
				label = -1;
			}
		}
	}
}