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

package com.liferay.portalweb.portal.controlpanel.dynamicdatalists.record.addrecord;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewRecordTest extends BaseTestCase {
	public void testViewRecord() throws Exception {
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
		selenium.clickAt("link=Dynamic Data Lists",
			RuntimeVariables.replace("Dynamic Data Lists"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='_167_keywords']",
			RuntimeVariables.replace("List Name"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("List Name"),
			selenium.getText("//tr[3]/td[2]/a"));
		selenium.clickAt("//tr[3]/td[2]/a",
			RuntimeVariables.replace("List Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Boolean"),
			selenium.getText("//tr[1]/th[1]"));
		assertEquals(RuntimeVariables.replace("true"),
			selenium.getText("//tr[3]/td[1]"));
		assertEquals(RuntimeVariables.replace("Date"),
			selenium.getText("//tr[1]/th[2]"));
		assertEquals(RuntimeVariables.replace("1/2/03"),
			selenium.getText("//tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("Decimal"),
			selenium.getText("//tr[1]/th[3]"));
		assertEquals(RuntimeVariables.replace("1.23"),
			selenium.getText("//tr[3]/td[3]"));
		assertEquals(RuntimeVariables.replace("Documents and Media"),
			selenium.getText("//tr[1]/th[4]"));
		assertEquals(RuntimeVariables.replace("Document_1.txt"),
			selenium.getText("//tr[3]/td[4]"));
		assertEquals(RuntimeVariables.replace("File Upload"),
			selenium.getText("//tr[1]/th[5]"));
		assertTrue(selenium.isPartialText("//tr[3]/td[5]", "Document_2.txt"));
		assertEquals(RuntimeVariables.replace("Integer"),
			selenium.getText("//tr[1]/th[6]"));
		assertEquals(RuntimeVariables.replace("123"),
			selenium.getText("//tr[3]/td[6]"));
		assertEquals(RuntimeVariables.replace("Number"),
			selenium.getText("//tr[1]/th[7]"));
		assertEquals(RuntimeVariables.replace("456"),
			selenium.getText("//tr[3]/td[7]"));
		assertEquals(RuntimeVariables.replace("Radio"),
			selenium.getText("//tr[1]/th[8]"));
		assertEquals(RuntimeVariables.replace("option 2"),
			selenium.getText("//tr[3]/td[8]"));
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText("//tr[1]/th[9]"));
		assertEquals(RuntimeVariables.replace("option 3"),
			selenium.getText("//tr[3]/td[9]"));
		assertEquals(RuntimeVariables.replace("Text"),
			selenium.getText("//tr[1]/th[10]"));
		assertEquals(RuntimeVariables.replace("Text Field"),
			selenium.getText("//tr[3]/td[10]"));
		assertEquals(RuntimeVariables.replace("Text Box"),
			selenium.getText("//tr[1]/th[11]"));
		assertEquals(RuntimeVariables.replace("Text Box"),
			selenium.getText("//tr[3]/td[11]"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'View')]/a");
		assertEquals(RuntimeVariables.replace("View"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'View')]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'View')]/a"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Boolean true"),
			selenium.getText("//div[@class='fieldset-content ']/div[1]"));
		assertEquals(RuntimeVariables.replace("Date 1/2/03"),
			selenium.getText("//div[@class='fieldset-content ']/div[2]"));
		assertEquals(RuntimeVariables.replace("Decimal 1.23"),
			selenium.getText("//div[@class='fieldset-content ']/div[3]"));
		assertEquals(RuntimeVariables.replace(
				"Documents and Media Document_1.txt"),
			selenium.getText("//div[@class='fieldset-content ']/div[4]"));
		assertTrue(selenium.isPartialText(
				"//div[@class='fieldset-content ']/div[5]",
				"File Upload Document_2.txt"));
		assertEquals(RuntimeVariables.replace("Integer 123"),
			selenium.getText("//div[@class='fieldset-content ']/div[6]"));
		assertEquals(RuntimeVariables.replace("Number 456"),
			selenium.getText("//div[@class='fieldset-content ']/div[7]"));
		assertEquals(RuntimeVariables.replace("Radio option 2"),
			selenium.getText("//div[@class='fieldset-content ']/div[8]"));
		assertEquals(RuntimeVariables.replace("Select option 3"),
			selenium.getText("//div[@class='fieldset-content ']/div[9]"));
		assertEquals(RuntimeVariables.replace("Text Text Field"),
			selenium.getText("//div[@class='fieldset-content ']/div[10]"));
		assertEquals(RuntimeVariables.replace("Text Box Text Box"),
			selenium.getText("//div[@class='fieldset-content ']/div[11]"));
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
		selenium.clickAt("link=Dynamic Data Lists",
			RuntimeVariables.replace("Dynamic Data Lists"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='_167_keywords']",
			RuntimeVariables.replace("List Name"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("List Name"),
			selenium.getText("//tr[3]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Spreadsheet View')]/a");
		assertEquals(RuntimeVariables.replace("Spreadsheet View"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Spreadsheet View')]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Spreadsheet View')]/a"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("True"),
			selenium.getText("//td[1]/div"));
		assertEquals(RuntimeVariables.replace("1903-01-02"),
			selenium.getText("//td[2]/div"));
		assertEquals(RuntimeVariables.replace("1.23"),
			selenium.getText("//td[3]/div"));
		assertEquals(RuntimeVariables.replace("Document_1.txt"),
			selenium.getText("//td[4]/div"));
		assertEquals(RuntimeVariables.replace("Document_2.txt"),
			selenium.getText("//td[5]/div"));
		assertEquals(RuntimeVariables.replace("123"),
			selenium.getText("//td[6]/div"));
		assertEquals(RuntimeVariables.replace("456"),
			selenium.getText("//td[7]/div"));
		assertEquals(RuntimeVariables.replace("option 2"),
			selenium.getText("//td[8]/div"));
		assertEquals(RuntimeVariables.replace("option 3"),
			selenium.getText("//td[9]/div"));
		assertEquals(RuntimeVariables.replace("Text Field"),
			selenium.getText("//td[10]/div"));
		assertEquals(RuntimeVariables.replace("Text Box"),
			selenium.getText("//td[11]/div"));
	}
}