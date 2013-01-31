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

package com.liferay.portalweb.portlet.dynamicdatalistdisplay.record.addrecordddld;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddRecordDDLDTest extends BaseTestCase {
	public void testAddRecordDDLD() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Dynamic Data List Display Test Page",
			RuntimeVariables.replace("Dynamic Data List Display Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Record']",
			RuntimeVariables.replace("Add Record"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Boolean"),
			selenium.getText(
				"//div[@class='lfr-ddm-container']/div[1]/div/span/span/label"));
		selenium.clickAt("//div[@class='lfr-ddm-container']/div[1]/div/span/span/span/input[2]",
			RuntimeVariables.replace("checkbox"));
		assertEquals(RuntimeVariables.replace("Date"),
			selenium.getText(
				"//div[@class='lfr-ddm-container']/div[2]/div/label"));
		selenium.select("//div[@class='lfr-ddm-container']/div[2]/div/div/div/div/select[1]",
			RuntimeVariables.replace("January"));
		selenium.select("//div[@class='lfr-ddm-container']/div[2]/div/div/div/div/select[2]",
			RuntimeVariables.replace("2"));
		selenium.select("//div[@class='lfr-ddm-container']/div[2]/div/div/div/div/select[3]",
			RuntimeVariables.replace("2003"));
		assertEquals(RuntimeVariables.replace("Decimal"),
			selenium.getText(
				"//div[@class='lfr-ddm-container']/div[3]/div/span/span/label"));
		selenium.type("//div[@class='lfr-ddm-container']/div[3]/div/span/span/span/input",
			RuntimeVariables.replace("1.23"));
		assertEquals(RuntimeVariables.replace("Documents and Media"),
			selenium.getText(
				"//div[@class='lfr-ddm-container']/div[4]/div/span/span/label"));
		selenium.clickAt("//input[@value='Select']",
			RuntimeVariables.replace("Select"));
		selenium.waitForVisible(
			"//iframe[contains(@id,'selectDocumentLibrary')]");
		selenium.selectFrame("//iframe[contains(@id,'selectDocumentLibrary')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/event-tap/event-tap-min.js')]");
		selenium.waitForVisible("//tr[3]/td[1]/a");
		assertEquals(RuntimeVariables.replace("Document_1.txt"),
			selenium.getText("//tr[3]/td[1]/a"));
		selenium.waitForVisible("//input[@value='Choose']");
		selenium.clickAt("//input[@value='Choose']",
			RuntimeVariables.replace("Choose"));
		selenium.selectFrame("relative=top");
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-ddm-container']/div[5]/div/span/span/label",
				"File Upload"));
		selenium.uploadCommonFile("//div[@class='lfr-ddm-container']/div[5]/div/span/span/span/input",
			RuntimeVariables.replace("Document_3.txt"));
		assertEquals(RuntimeVariables.replace("HTML"),
			selenium.getText(
				"//div[@class='lfr-ddm-container']/div[6]/div/label"));
		assertEquals(RuntimeVariables.replace("Integer"),
			selenium.getText(
				"//div[@class='lfr-ddm-container']/div[7]/div/span/span/label"));
		selenium.type("//div[@class='lfr-ddm-container']/div[7]/div/span/span/span/input",
			RuntimeVariables.replace("123"));
		assertEquals(RuntimeVariables.replace("Link to Page"),
			selenium.getText(
				"//div[@class='lfr-ddm-container']/div[8]/div/label"));
		selenium.select("//div[@class='lfr-ddm-container']/div[8]/div/select",
			RuntimeVariables.replace("index=1"));
		assertEquals(RuntimeVariables.replace("Number"),
			selenium.getText(
				"//div[@class='lfr-ddm-container']/div[9]/div/span/span/label"));
		selenium.type("//div[@class='lfr-ddm-container']/div[9]/div/span/span/span/input",
			RuntimeVariables.replace("456"));
		assertEquals(RuntimeVariables.replace("option 1"),
			selenium.getText(
				"//div[@class='lfr-ddm-container']/div[10]/div/span[1]/span"));
		assertEquals(RuntimeVariables.replace("option 2"),
			selenium.getText(
				"//div[@class='lfr-ddm-container']/div[10]/div/span[2]/span"));
		assertEquals(RuntimeVariables.replace("option 3"),
			selenium.getText(
				"//div[@class='lfr-ddm-container']/div[10]/div/span[3]/span"));
		selenium.clickAt("//div[@class='lfr-ddm-container']/div[10]/div/span[2]/span/span/input",
			RuntimeVariables.replace("option 2"));
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText(
				"//div[@class='lfr-ddm-container']/div[11]/div/span/span/label"));
		assertEquals(RuntimeVariables.replace("option 1"),
			selenium.getText(
				"//div[@class='lfr-ddm-container']/div[11]/div/span/span/span/select/option[1]"));
		assertEquals(RuntimeVariables.replace("option 2"),
			selenium.getText(
				"//div[@class='lfr-ddm-container']/div[11]/div/span/span/span/select/option[2]"));
		assertEquals(RuntimeVariables.replace("option 3"),
			selenium.getText(
				"//div[@class='lfr-ddm-container']/div[11]/div/span/span/span/select/option[3]"));
		selenium.select("//div[@class='lfr-ddm-container']/div[11]/div/span/span/span/select",
			RuntimeVariables.replace("label=option 3"));
		assertEquals(RuntimeVariables.replace("Text"),
			selenium.getText(
				"//div[@class='lfr-ddm-container']/div[12]/div/span/span/label"));
		selenium.type("//div[@class='lfr-ddm-container']/div[12]/div/span/span/span/input",
			RuntimeVariables.replace("Text Field"));
		assertEquals(RuntimeVariables.replace("Text Box"),
			selenium.getText(
				"//div[@class='lfr-ddm-container']/div[13]/div/span/span/label"));
		selenium.type("//div[@class='lfr-ddm-container']/div[13]/div/span/span/span/textarea",
			RuntimeVariables.replace("TextBox"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}