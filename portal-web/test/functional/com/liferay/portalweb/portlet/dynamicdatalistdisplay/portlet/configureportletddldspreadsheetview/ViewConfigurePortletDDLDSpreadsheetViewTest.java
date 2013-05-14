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

package com.liferay.portalweb.portlet.dynamicdatalistdisplay.portlet.configureportletddldspreadsheetview;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewConfigurePortletDDLDSpreadsheetViewTest extends BaseTestCase {
	public void testViewConfigurePortletDDLDSpreadsheetView()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Dynamic Data List Display Test Page",
			RuntimeVariables.replace("Dynamic Data List Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("List Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertTrue(selenium.isVisible(
				"//div[@class='lfr-spreadsheet-container']/div/div/div/table"));
		assertEquals(RuntimeVariables.replace("Boolean"),
			selenium.getText("//tr[contains(.,'Boolean')]/th[1]/div"));
		assertEquals(RuntimeVariables.replace("Date"),
			selenium.getText("//tr[contains(.,'Date')]/th[2]/div"));
		assertEquals(RuntimeVariables.replace("Decimal"),
			selenium.getText("//tr[contains(.,'Decimal')]/th[3]/div"));
		assertEquals(RuntimeVariables.replace("Documents and Media"),
			selenium.getText(
				"//tr[contains(.,'Documents and Media')]/th[4]/div"));
		assertEquals(RuntimeVariables.replace("File Upload"),
			selenium.getText("//tr[contains(.,'File Upload')]/th[5]/div"));
		assertEquals(RuntimeVariables.replace("HTML"),
			selenium.getText("//tr[contains(.,'HTML')]/th[6]/div"));
		assertEquals(RuntimeVariables.replace("Integer"),
			selenium.getText("//tr[contains(.,'Integer')]/th[7]/div"));
		assertEquals(RuntimeVariables.replace("Link to Page"),
			selenium.getText("//tr[contains(.,'Link to Page')]/th[8]/div"));
		assertEquals(RuntimeVariables.replace("Number"),
			selenium.getText("//tr[contains(.,'Number')]/th[9]/div"));
		assertEquals(RuntimeVariables.replace("Radio"),
			selenium.getText("//tr[contains(.,'Radio')]/th[10]/div"));
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText("//tr[contains(.,'Select')]/th[11]/div"));
		assertEquals(RuntimeVariables.replace("Text"),
			selenium.getText("//tr[contains(.,'Text')]/th[12]/div"));
		assertEquals(RuntimeVariables.replace("Text Box"),
			selenium.getText("//tr[contains(.,'Text Box')]/th[13]/div"));
		assertTrue(selenium.isVisible("//input[@value='Add']"));
		assertEquals(RuntimeVariables.replace("More rows at bottom."),
			selenium.getText("//label[contains(@for,'numberOfRecords')]"));
		assertTrue(selenium.isVisible(
				"//select[contains(@id,'numberOfRecords')]"));
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//span[@title='Options']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
			RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]");
		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]",
			RuntimeVariables.replace("Configuration"));
		selenium.waitForVisible(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.selectFrame(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'liferay/navigation_interaction.js')]");
		selenium.waitForVisible("//input[@id='_86_spreadsheetCheckbox']");
		assertTrue(selenium.isChecked("//input[@id='_86_spreadsheetCheckbox']"));
		selenium.selectFrame("relative=top");
		assertTrue(selenium.isVisible(
				"//div[@class='lfr-spreadsheet-container']/div/div/div/table"));
	}
}