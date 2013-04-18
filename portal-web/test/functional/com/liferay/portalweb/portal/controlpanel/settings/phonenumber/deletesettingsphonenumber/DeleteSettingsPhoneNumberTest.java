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

package com.liferay.portalweb.portal.controlpanel.settings.phonenumber.deletesettingsphonenumber;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DeleteSettingsPhoneNumberTest extends BaseTestCase {
	public void testDeleteSettingsPhoneNumber() throws Exception {
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
		assertTrue(selenium.isPartialText("//a[@id='_130_phoneNumbersLink']",
				"Phone Numbers"));
		selenium.clickAt("//a[@id='_130_phoneNumbersLink']",
			RuntimeVariables.replace("Phone Numbers"));
		selenium.waitForVisible("//input[@id='_130_phoneNumber0']");
		assertEquals("123-123-1234",
			selenium.getValue("//input[@id='_130_phoneNumber0']"));
		selenium.waitForVisible(
			"xpath=(//div[@id='_130_phoneNumbers']/fieldset/div[2]/div/span/span/button[2])[1]");
		selenium.clickAt("xpath=(//div[@id='_130_phoneNumbers']/fieldset/div[2]/div/span/span/button[2])[1]",
			RuntimeVariables.replace("Delete"));
		selenium.waitForVisible("//a[@class='lfr-action-undo']");
		assertTrue(selenium.isPartialText("//a[@class='lfr-action-undo']",
				"Undo"));
		assertFalse(selenium.isTextPresent("1231231234"));
	}
}