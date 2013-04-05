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

package com.liferay.portalweb.portal.controlpanel.customfields.user.addcustomfieldusertypeboolean;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddCustomFieldUserTypeBooleanTest extends BaseTestCase {
	public void testAddCustomFieldUserTypeBoolean() throws Exception {
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
		selenium.clickAt("link=Custom Fields",
			RuntimeVariables.replace("Custom Fields"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("User"),
			selenium.getText("//tr[contains(.,'User')]/td[1]/a/strong"));
		selenium.clickAt("//tr[contains(.,'User')]/td[1]/a/strong",
			RuntimeVariables.replace("User"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("//input[@value='Add Custom Field']"));
		selenium.clickAt("//input[@value='Add Custom Field']",
			RuntimeVariables.replace("Add Custom Field"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_139_name']",
			RuntimeVariables.replace("Boolean"));
		selenium.select("//select[@id='_139_type']",
			RuntimeVariables.replace("True/False"));
		assertEquals("True/False",
			selenium.getSelectedLabel("//select[@id='_139_type']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("User"),
			selenium.getText("//h1[@class='header-title']"));
		assertEquals(RuntimeVariables.replace("Boolean"),
			selenium.getText("//tr[contains(.,'Boolean')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("True/False"),
			selenium.getText("//tr[contains(.,'Boolean')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("False"),
			selenium.getText("//tr[contains(.,'Boolean')]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("Not Searchable"),
			selenium.getText("//tr[contains(.,'Boolean')]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'Boolean')]/td[5]/span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@class='header-back-to']/a",
			RuntimeVariables.replace("\u00ab Back"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("User"),
			selenium.getText("//tr[contains(.,'User')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Boolean"),
			selenium.getText("//tr[contains(.,'User')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("//tr[contains(.,'User')]/td[3]/span/a/span"));
	}
}