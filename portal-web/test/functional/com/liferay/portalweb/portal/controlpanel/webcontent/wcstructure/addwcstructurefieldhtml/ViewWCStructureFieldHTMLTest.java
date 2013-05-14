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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.addwcstructurefieldhtml;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWCStructureFieldHTMLTest extends BaseTestCase {
	public void testViewWCStructureFieldHTML() throws Exception {
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
			RuntimeVariables.replace("WC Structure HTML Name"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		Thread.sleep(1000);
		selenium.waitForVisible(
			"//tr[contains(.,'WC Structure HTML Name')]/td[3]/a");
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC Structure HTML Name')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("WC Structure HTML Name"),
			selenium.getText(
				"//tr[contains(.,'WC Structure HTML Name')]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("WC Structure HTML Description"),
			selenium.getText(
				"//tr[contains(.,'WC Structure HTML Name')]/td[4]/a"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC Structure HTML Name')]/td[5]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'WC Structure HTML Name')]/td[6]/span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//tr[contains(.,'WC Structure HTML Name')]/td[3]/a",
			RuntimeVariables.replace("WC Structure HTML Name"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//input[@id='_166_name_en_US']");
		assertEquals("WC Structure HTML Name",
			selenium.getValue("//input[@id='_166_name_en_US']"));
		assertEquals("WC Structure HTML Description",
			selenium.getValue("//textarea[@id='_166_description_en_US']"));
		assertEquals(RuntimeVariables.replace("Parent Structure"),
			selenium.getText(
				"//div[@id='structureDetailsSectionPanel']/div[2]/div[2]/div/label"));
		assertEquals("Select", selenium.getValue("//input[@value='Select']"));
		assertEquals("Remove", selenium.getValue("//input[@value='Remove']"));
		assertEquals(RuntimeVariables.replace("URL"),
			selenium.getText(
				"//div[@id='structureDetailsSectionPanel']/div[2]/div[3]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@id='structureDetailsSectionPanel']/div[2]/div[3]/div/input"));
		assertEquals(RuntimeVariables.replace("WebDAV URL"),
			selenium.getText(
				"//div[@id='structureDetailsSectionPanel']/div[2]/div[4]/div/label"));
		assertTrue(selenium.isVisible(
				"//div[@id='structureDetailsSectionPanel']/div[2]/div[4]/div/input"));
		selenium.waitForVisible(
			"//div[@class='diagram-builder-drop-container']/div[1]");
		assertEquals(RuntimeVariables.replace("HTML"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[1]/div/label"));
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[1]",
			RuntimeVariables.replace("HTML Field"));
		selenium.waitForVisible("//button[@id='editEvent']");
		assertTrue(selenium.isVisible("//button[@id='duplicateEvent']"));
		assertTrue(selenium.isVisible("//button[@id='deleteEvent']"));
		selenium.clickAt("//button[@id='editEvent']",
			RuntimeVariables.replace("Edit Event"));
		selenium.waitForElementPresent(
			"//li[contains(@class,'aui-state-active aui-tab-active')]/span/a[contains(.,'Settings')]");
		selenium.waitForVisible("//table[@class='yui3-datatable-table']");
		assertEquals(RuntimeVariables.replace("Property Name"),
			selenium.getText(
				"//thead[@class='yui3-datatable-columns']/tr/th[1]"));
		assertEquals(RuntimeVariables.replace("Value"),
			selenium.getText(
				"//thead[@class='yui3-datatable-columns']/tr/th[2]"));
		assertEquals(RuntimeVariables.replace("Type"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[1]/td[1]"));
		assertEquals(RuntimeVariables.replace("ddm-text-html"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[1]/td[2]"));
		assertEquals(RuntimeVariables.replace("Field Label"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[2]/td[1]"));
		assertEquals(RuntimeVariables.replace("HTML"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[2]/td[2]"));
		assertEquals(RuntimeVariables.replace("Show Label"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[3]/td[1]"));
		assertEquals(RuntimeVariables.replace("Yes"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("Required"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[4]/td[1]"));
		assertEquals(RuntimeVariables.replace("No"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[4]/td[2]"));
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[5]/td[1]"));
		assertTrue(selenium.isVisible(
				"//tbody[@class='yui3-datatable-data']/tr[5]/td[2]"));
		assertEquals(RuntimeVariables.replace("Predefined Value"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[6]/td[1]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[6]/td[2]"));
		assertEquals(RuntimeVariables.replace("Tip"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[7]/td[1]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[7]/td[2]"));
		assertEquals(RuntimeVariables.replace("Indexable"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[8]/td[1]"));
		assertEquals(RuntimeVariables.replace("Yes"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[8]/td[2]"));
		assertEquals(RuntimeVariables.replace("Repeatable"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[9]/td[1]"));
		assertEquals(RuntimeVariables.replace("No"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[9]/td[2]"));
		assertEquals(RuntimeVariables.replace("Width"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[10]/td[1]"));
		assertEquals(RuntimeVariables.replace("Small"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[10]/td[2]"));
		assertEquals(RuntimeVariables.replace("Cancel"),
			selenium.getText(
				"//span[@class='toolbar-content']/button[contains(.,'Cancel')]"));
		assertTrue(selenium.isVisible("//input[@value='Save']"));
		assertTrue(selenium.isVisible("//input[@value='Cancel']"));
		selenium.selectFrame("relative=top");
	}
}