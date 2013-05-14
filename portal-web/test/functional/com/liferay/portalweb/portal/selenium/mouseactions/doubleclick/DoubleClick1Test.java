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

package com.liferay.portalweb.portal.selenium.mouseactions.doubleclick;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DoubleClick1Test extends BaseTestCase {
	public void testDoubleClick1() throws Exception {
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
		selenium.clickAt("link=Documents and Media",
			RuntimeVariables.replace("Documents and Media"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Manage"),
			selenium.getText("//span[@title='Manage']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Manage']/ul/li/strong/a/span",
			RuntimeVariables.replace("Manage"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Document Types')]");
		assertEquals(RuntimeVariables.replace("Document Types"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Document Types')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Document Types')]",
			RuntimeVariables.replace("Document Types"));
		selenium.waitForVisible("//iframe[@id='_20_openFileEntryTypeView']");
		selenium.selectFrame("//iframe[@id='_20_openFileEntryTypeView']");
		selenium.waitForVisible("link=Add");
		selenium.clickAt("link=Add", RuntimeVariables.replace("Add"));
		selenium.waitForPageToLoad("30000");
		selenium.dragAndDropToObject("//ul[contains(@class,'aui-diagram-builder-fields-container')]/li/div[.='Text']",
			"//div[@class='diagram-builder-drop-container']");
		selenium.waitForVisible(
			"//div[contains(@id,'fields_field_aui')]/div/label");
		assertEquals(RuntimeVariables.replace("Text"),
			selenium.getText(
				"//div[contains(@id,'fields_field_aui')]/div/label"));
		selenium.doubleClickAt("//div[contains(@id,'fields_field_aui')]/div/label",
			RuntimeVariables.replace("Text"));
		assertEquals(RuntimeVariables.replace("Property Name"),
			selenium.getText("//th/div"));
	}
}