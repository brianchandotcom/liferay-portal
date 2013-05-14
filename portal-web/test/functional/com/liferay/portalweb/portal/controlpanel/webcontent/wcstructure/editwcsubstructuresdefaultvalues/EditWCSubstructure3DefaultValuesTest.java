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
public class EditWCSubstructure3DefaultValuesTest extends BaseTestCase {
	public void testEditWCSubstructure3DefaultValues()
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
		assertEquals(RuntimeVariables.replace("Text"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[1]/div/label"));
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[1]",
			RuntimeVariables.replace("Text Field"));
		selenium.waitForVisible("//button[@id='editEvent']");
		selenium.clickAt("//button[@id='editEvent']",
			RuntimeVariables.replace("Edit Event"));
		selenium.waitForElementPresent(
			"//li[contains(@class,'aui-state-active aui-tab-active')]/span/a[contains(.,'Settings')]");
		selenium.waitForVisible("//table[@class='yui3-datatable-table']");
		assertEquals(RuntimeVariables.replace("Field Label"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[2]/td[1]"));
		assertEquals(RuntimeVariables.replace("Text"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[2]/td[2]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[2]/td[2]",
			RuntimeVariables.replace("Field Label Value"));
		selenium.waitForVisible(
			"//form[contains(@class,'aui-textcelleditor-content')]/div/input");
		selenium.type("//form[contains(@class,'aui-textcelleditor-content')]/div/input",
			RuntimeVariables.replace("Text Edited"));
		selenium.clickAt("//button[@type='submit']",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Predefined Value"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[6]/td[1]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[6]/td[2]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[6]/td[2]",
			RuntimeVariables.replace("Predefined Value"));
		selenium.waitForVisible(
			"//form[contains(@class,'aui-textcelleditor-content')]/div/input");
		selenium.type("//form[contains(@class,'aui-textcelleditor-content')]/div/input",
			RuntimeVariables.replace("Predefined Value Edited"));
		selenium.clickAt("//button[@type='submit']",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Tip"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[7]/td[1]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[7]/td[2]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[7]/td[2]",
			RuntimeVariables.replace("Tip Value"));
		selenium.waitForVisible(
			"//form[contains(@class,'aui-textareacelleditor-content')]/div/textarea");
		selenium.type("//form[contains(@class,'aui-textareacelleditor-content')]/div/textarea",
			RuntimeVariables.replace("Tip Edited"));
		selenium.clickAt("//button[@type='submit']",
			RuntimeVariables.replace("Save"));
		selenium.waitForText("//tbody[@class='yui3-datatable-data']/tr[6]/td[2]",
			"Predefined Value Edited");
		assertEquals(RuntimeVariables.replace("Text Edited"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[2]/td[2]"));
		assertEquals(RuntimeVariables.replace("Predefined Value Edited"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[6]/td[2]"));
		assertEquals(RuntimeVariables.replace("Tip Edited"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[7]/td[2]"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}