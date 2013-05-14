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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.editwcsubstructuresdefaultvalues;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewEditWCSubstucture3DefaultValueTest extends BaseTestCase {
	public void testViewEditWCSubstucture3DefaultValue()
		throws Exception {
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
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Manage"),
			selenium.getText("//span[@title='Manage']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Manage']/ul/li/strong/a",
			RuntimeVariables.replace("Manage"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Structures')]");
		assertEquals(RuntimeVariables.replace("Structures"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Structures')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Structures')]",
			RuntimeVariables.replace("Structures"));
		selenium.waitForVisible("//iframe[contains(@src,'Structures')]");
		selenium.selectFrame("//iframe[contains(@src,'Structures')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/store.js')]");
		selenium.waitForVisible("//input[@name='_166_keywords']");
		selenium.type("//input[@name='_166_keywords']",
			RuntimeVariables.replace("Substructure3"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		Thread.sleep(1000);
		selenium.waitForVisible(
			"//tr[contains(.,'WC Substructure3 Name')]/td[2]/a");
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC Substructure3 Name')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("WC Substructure3 Name"),
			selenium.getText(
				"//tr[contains(.,'WC Substructure3 Name')]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("WC Substructure3 Description"),
			selenium.getText(
				"//tr[contains(.,'WC Substructure3 Name')]/td[4]/a"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC Substructure3 Name')]/td[5]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'WC Substructure3 Name')]/td[6]/span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//tr[contains(.,'WC Substructure3 Name')]/td[6]/span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]",
			RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//input[@id='_166_name_en_US']");
		assertEquals("WC Substructure3 Name",
			selenium.getValue("//input[@id='_166_name_en_US']"));
		selenium.waitForVisible(
			"//div[@class='diagram-builder-drop-container']/div[1]");
		assertEquals(RuntimeVariables.replace("Text Edited"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[1]/div/label"));
		selenium.mouseOver(
			"//div[@class='diagram-builder-drop-container']/div[1]/div/a[contains(@class,'icon-tip')]");
		selenium.waitForVisible(
			"//div[@role='dialog' and @aria-hidden='false']/div/div[contains(.,'Tip Edited')]");
		assertEquals(RuntimeVariables.replace("Tip Edited"),
			selenium.getText(
				"//div[@role='dialog' and @aria-hidden='false']/div/div[contains(.,'Tip Edited')]"));
		assertEquals("Predefined Value Edited",
			selenium.getValue(
				"//div[@class='diagram-builder-drop-container']/div[1]/div/input"));
	}
}