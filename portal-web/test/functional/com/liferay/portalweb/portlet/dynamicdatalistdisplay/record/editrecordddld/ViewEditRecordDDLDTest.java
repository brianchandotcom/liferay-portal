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

package com.liferay.portalweb.portlet.dynamicdatalistdisplay.record.editrecordddld;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewEditRecordDDLDTest extends BaseTestCase {
	public void testViewEditRecordDDLD() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Dynamic Data List Display Test Page",
			RuntimeVariables.replace("Dynamic Data List Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Boolean"),
			selenium.getText("//tr[1]/th[1]"));
		assertEquals(RuntimeVariables.replace("false"),
			selenium.getText("//tr[3]/td[1]"));
		assertEquals(RuntimeVariables.replace("Date"),
			selenium.getText("//tr[1]/th[2]"));
		assertEquals(RuntimeVariables.replace("8/9/10"),
			selenium.getText("//tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("Decimal"),
			selenium.getText("//tr[1]/th[3]"));
		assertEquals(RuntimeVariables.replace("8.91"),
			selenium.getText("//tr[3]/td[3]"));
		assertEquals(RuntimeVariables.replace("Documents and Media"),
			selenium.getText("//tr[1]/th[4]"));
		assertEquals(RuntimeVariables.replace("Document_2.txt"),
			selenium.getText("//tr[3]/td[4]"));
		assertEquals(RuntimeVariables.replace("File Upload"),
			selenium.getText("//tr[1]/th[5]"));
		assertEquals(RuntimeVariables.replace("Document_1.txt"),
			selenium.getText("//tr[3]/td[5]"));
		assertEquals(RuntimeVariables.replace("HTML"),
			selenium.getText("//tr[1]/th[6]"));
		assertEquals(RuntimeVariables.replace("(Preview)"),
			selenium.getText("//tr[3]/td[6]"));
		assertEquals(RuntimeVariables.replace("Integer"),
			selenium.getText("//tr[1]/th[7]"));
		assertEquals(RuntimeVariables.replace("8910"),
			selenium.getText("//tr[3]/td[7]"));
		assertEquals(RuntimeVariables.replace("Link to Page"),
			selenium.getText("//tr[1]/th[8]"));
		assertEquals(RuntimeVariables.replace("Welcome"),
			selenium.getText("//tr[3]/td[8]"));
		assertEquals(RuntimeVariables.replace("Number"),
			selenium.getText("//tr[1]/th[9]"));
		assertEquals(RuntimeVariables.replace("111213"),
			selenium.getText("//tr[3]/td[9]"));
		assertEquals(RuntimeVariables.replace("Radio"),
			selenium.getText("//tr[1]/th[10]"));
		assertEquals(RuntimeVariables.replace("option 3"),
			selenium.getText("//tr[3]/td[10]"));
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText("//tr[1]/th[11]"));
		assertEquals(RuntimeVariables.replace("option 1"),
			selenium.getText("//tr[3]/td[11]"));
		assertEquals(RuntimeVariables.replace("Text"),
			selenium.getText("//tr[1]/th[12]"));
		assertEquals(RuntimeVariables.replace("Text Field Edited"),
			selenium.getText("//tr[3]/td[12]"));
		assertEquals(RuntimeVariables.replace("Text Box"),
			selenium.getText("//tr[1]/th[13]"));
		assertEquals(RuntimeVariables.replace("TextBoxEdited"),
			selenium.getText("//tr[3]/td[13]"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'View')]");
		assertEquals(RuntimeVariables.replace("View"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'View')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'View')]",
			RuntimeVariables.replace("View"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-ddm-container']/div[1]", "Boolean"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-ddm-container']/div[1]", "false"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-ddm-container']/div[2]", "Date"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-ddm-container']/div[2]", "8/9/10"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-ddm-container']/div[3]", "Decimal"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-ddm-container']/div[3]", "8.91"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-ddm-container']/div[4]",
				"Documents and Media"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-ddm-container']/div[4]", "Document_2.txt"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-ddm-container']/div[5]", "File Upload"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-ddm-container']/div[5]", "Document_1.txt"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-ddm-container']/div[6]", "Integer"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-ddm-container']/div[6]", "8910"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-ddm-container']/div[7]", "Link to Page"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-ddm-container']/div[7]", "Welcome"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-ddm-container']/div[8]", "Number"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-ddm-container']/div[8]", "111213"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-ddm-container']/div[9]", "Radio"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-ddm-container']/div[9]", "option 3"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-ddm-container']/div[10]", "Select"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-ddm-container']/div[10]", "option 1"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-ddm-container']/div[11]", "Text"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-ddm-container']/div[11]", "Text Field Edited"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-ddm-container']/div[12]", "Text Box"));
		assertTrue(selenium.isPartialText(
				"//div[@class='lfr-ddm-container']/div[12]", "TextBoxEdited"));
	}
}