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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcstructurewebcontentlocalized;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddStructureLocalizedTest extends BaseTestCase {
	public void testAddStructureLocalized() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText(
				"//span[@class='lfr-toolbar-button add-button ']/a"));
		selenium.clickAt("//span[@class='lfr-toolbar-button add-button ']/a",
			RuntimeVariables.replace("Add"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible(
			"//div[@class='fieldset-content ']/span/span/label[contains(.,'Name (Required)')]");
		selenium.type("//span[@class='field-content']/span/span/input",
			RuntimeVariables.replace("WC Structure Name"));
		selenium.type("//textarea[@id='_166_description_en_US']",
			RuntimeVariables.replace("WC Structure Description"));
		selenium.waitForVisible(
			"//div[@class='tabview-content widget-bd']/div/ul/li[@title=\"Text\"]/div");
		assertEquals(RuntimeVariables.replace("Text"),
			selenium.getText(
				"//div[@class='tabview-content widget-bd']/div/ul/li[@title=\"Text\"]/div"));
		selenium.dragAndDropToObject("//div[@class='tabview-content widget-bd']/div/ul/li[@title=\"Text\"]/div",
			"//div[@class='tabview-content widget-bd']");
		assertEquals(RuntimeVariables.replace("Text"),
			selenium.getText(
				"//div[@class='tabview-content widget-bd']/div/ul/li[@title=\"Text\"]/div"));
		selenium.dragAndDropToObject("//div[@class='tabview-content widget-bd']/div/ul/li[@title=\"Text\"]/div",
			"//div[@class='tabview-content widget-bd']");
		selenium.waitForVisible("//label[contains(.,'Text')]");
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
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[5]/td[2]",
			RuntimeVariables.replace("Name Value"));
		selenium.waitForVisible(
			"//form[contains(@class,'aui-textcelleditor-content')]/div/input");
		selenium.type("//form[contains(@class,'aui-textcelleditor-content')]/div/input",
			RuntimeVariables.replace("page-name"));
		selenium.clickAt("//button[@type='submit']",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Text"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[2]/td[2]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[2]/td[2]",
			RuntimeVariables.replace("Field Label Value"));
		selenium.waitForVisible(
			"//form[contains(@class,'aui-textcelleditor-content')]/div/input");
		selenium.type("//form[contains(@class,'aui-textcelleditor-content')]/div/input",
			RuntimeVariables.replace("page-name"));
		selenium.clickAt("//button[@type='submit']",
			RuntimeVariables.replace("Save"));
		selenium.waitForVisible("//label[contains(.,'Text')]");
		assertTrue(selenium.isVisible("//button[@id='duplicateEvent']"));
		assertTrue(selenium.isVisible("//button[@id='deleteEvent']"));
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[2]/div[3]/span/span/button[contains(@id,'editEvent')]",
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
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[5]/td[2]",
			RuntimeVariables.replace("Name Value"));
		selenium.waitForVisible(
			"//form[contains(@class,'aui-textcelleditor-content')]/div/input");
		selenium.type("//form[contains(@class,'aui-textcelleditor-content')]/div/input",
			RuntimeVariables.replace("page-description"));
		selenium.clickAt("//button[@type='submit']",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Text"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[2]/td[2]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[2]/td[2]",
			RuntimeVariables.replace("Field Label Value"));
		selenium.waitForVisible(
			"//form[contains(@class,'aui-textcelleditor-content')]/div/input");
		selenium.type("//form[contains(@class,'aui-textcelleditor-content')]/div/input",
			RuntimeVariables.replace("page-description"));
		selenium.clickAt("//button[@type='submit']",
			RuntimeVariables.replace("Save"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isVisible(
				"//td[@id='_166_ddmStructuresSearchContainer_col-id_row-1']/a"));
		assertEquals(RuntimeVariables.replace("WC Structure Name"),
			selenium.getText("//td[contains(.,'WC Structure Name')]/a"));
		selenium.waitForVisible(
			"//span[@class='lfr-toolbar-button add-button ']/a");
	}
}