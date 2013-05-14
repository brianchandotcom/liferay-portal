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

package com.liferay.portalweb.portal.controlpanel.dynamicdatalists.record.addrecordspreadsheetview;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddRecordSpreadsheetViewTest extends BaseTestCase {
	public void testAddRecordSpreadsheetView() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Boolean"),
			selenium.getText(
				"//thead[@class='yui3-datatable-columns']/tr/th[1]/div/a"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[1]/td[1]",
			RuntimeVariables.replace("column 1 row 1"));
		selenium.waitForVisible(
			"//div[contains(@class,'aui-checkboxcelleditor')]");
		selenium.clickAt("//div[contains(@class,'aui-checkboxcelleditor')]",
			RuntimeVariables.replace("Checkbox Cell Editor"));
		assertEquals(RuntimeVariables.replace("True"),
			selenium.getText(
				"//div[contains(@class,'aui-checkboxcelleditor-focused')]/form/div/div/label"));
		selenium.clickAt("//div[contains(@class,'aui-checkboxcelleditor-focused')]/form/div/div/label/input",
			RuntimeVariables.replace("checkbox"));
		assertEquals(RuntimeVariables.replace("Save"),
			selenium.getText(
				"//div[contains(@class,'aui-checkboxcelleditor-focused')]/form/div[2]/span/span/button[1]"));
		selenium.clickAt("//div[contains(@class,'aui-checkboxcelleditor-focused')]/form/div[2]/span/span/button[1]",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Date"),
			selenium.getText(
				"//thead[@class='yui3-datatable-columns']/tr/th[2]/div/a"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[1]/td[2]",
			RuntimeVariables.replace("column 2 row 1"));
		selenium.waitForVisible("//div[contains(@class,'aui-datecelleditor')]");
		selenium.clickAt("//div[contains(@class,'aui-datecelleditor')]",
			RuntimeVariables.replace("Date Cell Editor"));
		assertEquals(RuntimeVariables.replace("10"),
			selenium.getText(
				"//div[contains(@class,'aui-datecelleditor-focused')]/form/div/div/div/div[2]/div/div[2]/a[10]"));
		selenium.clickAt("//div[contains(@class,'aui-datecelleditor-focused')]/form/div/div/div/div[2]/div/div[2]/a[10]",
			RuntimeVariables.replace("10"));
		assertEquals(RuntimeVariables.replace("Save"),
			selenium.getText(
				"//div[contains(@class,'aui-datecelleditor-focused')]/form/div[2]/span/span/button[1]"));
		selenium.clickAt("//div[contains(@class,'aui-datecelleditor-focused')]/form/div[2]/span/span/button[1]",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Decimal"),
			selenium.getText(
				"//thead[@class='yui3-datatable-columns']/tr/th[3]/div/a"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[1]/td[3]",
			RuntimeVariables.replace("column 3 row 1"));
		selenium.waitForVisible("//div[contains(@class,'aui-textcelleditor')]");
		selenium.clickAt("//div[contains(@class,'aui-textcelleditor')]",
			RuntimeVariables.replace("Decimal"));
		selenium.type("//div[contains(@class,'aui-textcelleditor-focused')]/form/div/input",
			RuntimeVariables.replace("1.23"));
		assertEquals(RuntimeVariables.replace("Save"),
			selenium.getText(
				"//div[contains(@class,'aui-textcelleditor-focused')]/form/div[2]/span/span/button[1]"));
		selenium.clickAt("//div[contains(@class,'aui-textcelleditor-focused')]/form/div[2]/span/span/button[1]",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Documents and Media"),
			selenium.getText(
				"//thead[@class='yui3-datatable-columns']/tr/th[4]/div/a"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[1]/td[4]",
			RuntimeVariables.replace("column 4 row 1"));
		selenium.waitForVisible(
			"//div[contains(@class,'aui-document-library-file-entry-cell-editor')]");
		selenium.clickAt("//div[contains(@class,'aui-document-library-file-entry-cell-editor')]",
			RuntimeVariables.replace("Document Library File Entry Cell Editor"));
		assertEquals(RuntimeVariables.replace("Choose"),
			selenium.getText(
				"//div[contains(@class,'aui-document-library-file-entry-cell-editor-focused')]/form/div[2]/span/span/button[2]"));
		selenium.clickAt("//div[contains(@class,'aui-document-library-file-entry-cell-editor-focused')]/form/div[2]/span/span/button[2]",
			RuntimeVariables.replace("Choose"));
		selenium.waitForVisible("//iframe");
		selenium.selectFrame("//iframe");
		selenium.waitForVisible("//tr[3]/td[1]/a");
		assertEquals(RuntimeVariables.replace("Document_1.txt"),
			selenium.getText("//tr[3]/td[1]/a"));
		selenium.clickAt("//input[@value='Choose']",
			RuntimeVariables.replace("Choose"));
		selenium.selectFrame("relative=top");
		assertEquals(RuntimeVariables.replace("Save"),
			selenium.getText(
				"//div[contains(@class,'aui-document-library-file-entry-cell-editor-focused')]/form/div[2]/span/span/button[1]"));
		selenium.clickAt("//div[contains(@class,'aui-document-library-file-entry-cell-editor-focused')]/form/div[2]/span/span/button[1]",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("File Upload"),
			selenium.getText(
				"//thead[@class='yui3-datatable-columns']/tr/th[5]/div/a"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[1]/td[5]",
			RuntimeVariables.replace("column 5 row 1"));
		selenium.waitForVisible(
			"//div[contains(@class,'aui-fileupload-cell-editor')]");
		selenium.clickAt("//div[contains(@class,'aui-fileupload-cell-editor')]",
			RuntimeVariables.replace("File Upload Cell Editor"));
		selenium.uploadCommonFile("//div[contains(@class,'aui-fileupload-cell-editor-focused')]/form/div[1]/input",
			RuntimeVariables.replace("Document_2.txt"));
		assertEquals(RuntimeVariables.replace("Save"),
			selenium.getText(
				"//div[contains(@class,'aui-fileupload-cell-editor-focused')]/form/div[2]/span/span/button[1]"));
		selenium.clickAt("//div[contains(@class,'aui-fileupload-cell-editor-focused')]/form/div[2]/span/span/button[1]",
			RuntimeVariables.replace("Save"));
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Integer"),
			selenium.getText(
				"//thead[@class='yui3-datatable-columns']/tr/th[6]/div/a"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[1]/td[6]",
			RuntimeVariables.replace("column 6 row 1"));
		selenium.waitForVisible("//div[contains(@class,'aui-textcelleditor')]");
		selenium.clickAt("//div[contains(@class,'aui-textcelleditor')]",
			RuntimeVariables.replace("Text Cell Editor"));
		selenium.type("//div[contains(@class,'aui-textcelleditor-focused')]/form/div[1]/input",
			RuntimeVariables.replace("123"));
		assertEquals(RuntimeVariables.replace("Save"),
			selenium.getText(
				"//div[contains(@class,'aui-textcelleditor-focused')]/form/div[2]/span/span/button[1]"));
		selenium.clickAt("//div[contains(@class,'aui-textcelleditor-focused')]/form/div[2]/span/span/button[1]",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Number"),
			selenium.getText(
				"//thead[@class='yui3-datatable-columns']/tr/th[7]/div/a"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[1]/td[7]",
			RuntimeVariables.replace("column 7 row 1"));
		selenium.waitForVisible("//div[contains(@class,'aui-textcelleditor')]");
		selenium.clickAt("//div[contains(@class,'aui-textcelleditor')]",
			RuntimeVariables.replace("Text Cell Editor"));
		selenium.type("//div[contains(@class,'aui-textcelleditor-focused')]/form/div[1]/input",
			RuntimeVariables.replace("456"));
		assertEquals(RuntimeVariables.replace("Save"),
			selenium.getText(
				"//div[contains(@class,'aui-textcelleditor-focused')]/form/div[2]/span/span/button[1]"));
		selenium.clickAt("//div[contains(@class,'aui-textcelleditor-focused')]/form/div[2]/span/span/button[1]",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Radio"),
			selenium.getText(
				"//thead[@class='yui3-datatable-columns']/tr/th[8]/div/a"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[1]/td[8]",
			RuntimeVariables.replace("column 9 row 1"));
		selenium.waitForVisible("//div[contains(@class,'aui-radiocelleditor')]");
		selenium.clickAt("//div[contains(@class,'aui-radiocelleditor')]",
			RuntimeVariables.replace("Radio Cell Editor"));
		assertEquals(RuntimeVariables.replace("option 1"),
			selenium.getText(
				"//div[contains(@class,'aui-radiocelleditor-focused')]/form/div[1]/div/label[1]"));
		assertEquals(RuntimeVariables.replace("option 2"),
			selenium.getText(
				"//div[contains(@class,'aui-radiocelleditor-focused')]/form/div[1]/div/label[2]"));
		assertEquals(RuntimeVariables.replace("option 3"),
			selenium.getText(
				"//div[contains(@class,'aui-radiocelleditor-focused')]/form/div[1]/div/label[3]"));
		selenium.clickAt("//div[contains(@class,'aui-radiocelleditor-focused')]/form/div[1]/div/label[2]",
			RuntimeVariables.replace("option 2"));
		assertEquals(RuntimeVariables.replace("Save"),
			selenium.getText(
				"//div[contains(@class,'aui-radiocelleditor-focused')]/form/div[2]/span/span/button[1]"));
		selenium.clickAt("//div[contains(@class,'aui-radiocelleditor-focused')]/form/div[2]/span/span/button[1]",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText(
				"//thead[@class='yui3-datatable-columns']/tr/th[9]/div/a"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[1]/td[9]",
			RuntimeVariables.replace("column 10 row 1"));
		selenium.waitForVisible(
			"//div[contains(@class,'aui-dropdowncelleditor')]");
		selenium.clickAt("//div[contains(@class,'aui-dropdowncelleditor')]",
			RuntimeVariables.replace("Radio Cell Editor"));
		assertEquals(RuntimeVariables.replace("option 1"),
			selenium.getText(
				"//div[contains(@class,'aui-dropdowncelleditor-focused')]/form/div[1]/select/option[1]"));
		assertEquals(RuntimeVariables.replace("option 2"),
			selenium.getText(
				"//div[contains(@class,'aui-dropdowncelleditor-focused')]/form/div[1]/select/option[2]"));
		assertEquals(RuntimeVariables.replace("option 3"),
			selenium.getText(
				"//div[contains(@class,'aui-dropdowncelleditor-focused')]/form/div[1]/select/option[3]"));
		selenium.select("//div[contains(@class,'aui-dropdowncelleditor-focused')]/form/div[1]/select",
			RuntimeVariables.replace("label=option 3"));
		assertEquals(RuntimeVariables.replace("Save"),
			selenium.getText(
				"//div[contains(@class,'aui-dropdowncelleditor-focused')]/form/div[2]/span/span/button[1]"));
		selenium.clickAt("//div[contains(@class,'aui-dropdowncelleditor-focused')]/form/div[2]/span/span/button[1]",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Text"),
			selenium.getText(
				"//thead[@class='yui3-datatable-columns']/tr/th[10]/div/a"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[1]/td[10]",
			RuntimeVariables.replace("column 11 row 1"));
		selenium.waitForVisible("//div[contains(@class,'aui-textcelleditor')]");
		selenium.clickAt("//div[contains(@class,'aui-textcelleditor')]",
			RuntimeVariables.replace("Text Cell Editor"));
		selenium.type("//div[contains(@class,'aui-textcelleditor-focused')]/form/div[1]/input",
			RuntimeVariables.replace("Text Field"));
		assertEquals(RuntimeVariables.replace("Save"),
			selenium.getText(
				"//div[contains(@class,'aui-textcelleditor-focused')]/form/div[2]/span/span/button[1]"));
		selenium.clickAt("//div[contains(@class,'aui-textcelleditor-focused')]/form/div[2]/span/span/button[1]",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Text Box"),
			selenium.getText(
				"//thead[@class='yui3-datatable-columns']/tr/th[11]/div/a"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[1]/td[11]",
			RuntimeVariables.replace("column 12 row 1"));
		selenium.waitForVisible(
			"//div[contains(@class,'aui-textareacelleditor')]");
		selenium.clickAt("//div[contains(@class,'aui-textareacelleditor')]",
			RuntimeVariables.replace("Text Cell Editor"));
		selenium.type("//div[contains(@class,'aui-textareacelleditor-focused')]/form/div[1]/textarea",
			RuntimeVariables.replace("Text\nBox"));
		assertEquals(RuntimeVariables.replace("Save"),
			selenium.getText(
				"//div[contains(@class,'aui-textareacelleditor-focused')]/form/div[2]/span/span/button[1]"));
		selenium.clickAt("//div[contains(@class,'aui-textareacelleditor-focused')]/form/div[2]/span/span/button[1]",
			RuntimeVariables.replace("Save"));
	}
}