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

package com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatestructure;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWCTemplateStructure2Test extends BaseTestCase {
	public void testAddWCTemplateStructure2() throws Exception {
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
			RuntimeVariables.replace("WC Structure2 Name"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible(
			"//tr[contains(.,'WC Structure2 Name')]/td[3]/a");
		assertEquals(RuntimeVariables.replace("WC Structure2 Name"),
			selenium.getText("//tr[contains(.,'WC Structure2 Name')]/td[3]/a"));
		selenium.clickAt("//tr[contains(.,'WC Structure2 Name')]/td[3]/a",
			RuntimeVariables.replace("WC Structure2 Name"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible(
			"//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_boolean')]");
		assertEquals(RuntimeVariables.replace("Boolean"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_boolean')]/div/label"));
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_boolean')]",
			RuntimeVariables.replace("Boolean Field"));
		selenium.waitForVisible(
			"//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_boolean')]/div[3]/span/span/button[@title='Edit']");
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_boolean')]/div[3]/span/span/button[@title='Edit']",
			RuntimeVariables.replace("Edit Boolean Settings"));
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[4]/td[1]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[4]/td[2]",
			RuntimeVariables.replace("Name"));
		selenium.waitForVisible(
			"//form[contains(@class,'aui-textcelleditor-content')]/div/input");
		selenium.type("//form[contains(@class,'aui-textcelleditor-content')]/div/input",
			RuntimeVariables.replace("boolean"));
		selenium.clickAt("//button[@type='submit']",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Date"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_date')]/div/label"));
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_date')]",
			RuntimeVariables.replace("Date Field"));
		selenium.waitForVisible(
			"//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_date')]/div[3]/span/span/button[@title='Edit']");
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_date')]/div[3]/span/span/button[@title='Edit']",
			RuntimeVariables.replace("Edit Date Settings"));
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[5]/td[1]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[5]/td[2]",
			RuntimeVariables.replace("Name"));
		selenium.waitForVisible(
			"//form[contains(@class,'aui-textcelleditor-content')]/div/input");
		selenium.type("//form[contains(@class,'aui-textcelleditor-content')]/div/input",
			RuntimeVariables.replace("date"));
		selenium.clickAt("//button[@type='submit']",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Decimal"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_decimal')]/div/label"));
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_decimal')]",
			RuntimeVariables.replace("Decimal Field"));
		selenium.waitForVisible(
			"//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_decimal')]/div[3]/span/span/button[@title='Edit']");
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_decimal')]/div[3]/span/span/button[@title='Edit']",
			RuntimeVariables.replace("Edit Decimal Settings"));
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[5]/td[1]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[5]/td[2]",
			RuntimeVariables.replace("Name"));
		selenium.waitForVisible(
			"//form[contains(@class,'aui-textcelleditor-content')]/div/input");
		selenium.type("//form[contains(@class,'aui-textcelleditor-content')]/div/input",
			RuntimeVariables.replace("decimal"));
		selenium.clickAt("//button[@type='submit']",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Documents and Media"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_documents_and_media')]/div/label"));
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_documents_and_media')]",
			RuntimeVariables.replace("Documents and Media Field"));
		selenium.waitForVisible(
			"//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_documents_and_media')]/div[3]/span/span/button[@title='Edit']");
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_documents_and_media')]/div[3]/span/span/button[@title='Edit']",
			RuntimeVariables.replace("Edit Documents and Media Settings"));
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[5]/td[1]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[5]/td[2]",
			RuntimeVariables.replace("Name"));
		selenium.waitForVisible(
			"//form[contains(@class,'aui-textcelleditor-content')]/div/input");
		selenium.type("//form[contains(@class,'aui-textcelleditor-content')]/div/input",
			RuntimeVariables.replace("dm"));
		selenium.clickAt("//button[@type='submit']",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("File Upload"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_file_upload')]/div/label"));
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_file_upload')]",
			RuntimeVariables.replace("File Upload Field"));
		selenium.waitForVisible(
			"//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_file_upload')]/div[3]/span/span/button[@title='Edit']");
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_file_upload')]/div[3]/span/span/button[@title='Edit']",
			RuntimeVariables.replace("Edit File Upload Settings"));
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[5]/td[1]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[5]/td[2]",
			RuntimeVariables.replace("Name"));
		selenium.waitForVisible(
			"//form[contains(@class,'aui-textcelleditor-content')]/div/input");
		selenium.type("//form[contains(@class,'aui-textcelleditor-content')]/div/input",
			RuntimeVariables.replace("fileupload"));
		selenium.clickAt("//button[@type='submit']",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("HTML"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_html')]/div/label"));
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_html')]",
			RuntimeVariables.replace("HTML Field"));
		selenium.waitForVisible(
			"//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_html')]/div[3]/span/span/button[@title='Edit']");
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_html')]/div[3]/span/span/button[@title='Edit']",
			RuntimeVariables.replace("Edit HTML Settings"));
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[5]/td[1]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[5]/td[2]",
			RuntimeVariables.replace("Name"));
		selenium.waitForVisible(
			"//form[contains(@class,'aui-textcelleditor-content')]/div/input");
		selenium.type("//form[contains(@class,'aui-textcelleditor-content')]/div/input",
			RuntimeVariables.replace("html"));
		selenium.clickAt("//button[@type='submit']",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Image"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[contains(@id,'image')]/div/label"));
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[contains(@id,'image')]",
			RuntimeVariables.replace("Edit Image Settings"));
		selenium.waitForVisible(
			"//div[@class='diagram-builder-drop-container']/div[contains(@id,'image')]/div[3]/span/span/button[@title='Edit']");
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[contains(@id,'image')]/div[3]/span/span/button[@title='Edit']",
			RuntimeVariables.replace("Edit Image Settings"));
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[5]/td[1]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[5]/td[2]",
			RuntimeVariables.replace("Name"));
		selenium.waitForVisible(
			"//form[contains(@class,'aui-textcelleditor-content')]/div/input");
		selenium.type("//form[contains(@class,'aui-textcelleditor-content')]/div/input",
			RuntimeVariables.replace("image"));
		selenium.clickAt("//button[@type='submit']",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Integer"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_integer')]/div/label"));
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_integer')]",
			RuntimeVariables.replace("Edit Integer Settings"));
		selenium.waitForVisible(
			"//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_integer')]/div[3]/span/span/button[@title='Edit']");
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_integer')]/div[3]/span/span/button[@title='Edit']",
			RuntimeVariables.replace("Edit Integer Settings"));
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[5]/td[1]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[5]/td[2]",
			RuntimeVariables.replace("Name"));
		selenium.waitForVisible(
			"//form[contains(@class,'aui-textcelleditor-content')]/div/input");
		selenium.type("//form[contains(@class,'aui-textcelleditor-content')]/div/input",
			RuntimeVariables.replace("integer"));
		selenium.clickAt("//button[@type='submit']",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Link to Page"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_link_to_page')]/div/label"));
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_link_to_page')]",
			RuntimeVariables.replace("Link to Page Field"));
		selenium.waitForVisible(
			"//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_link_to_page')]/div[3]/span/span/button[@title='Edit']");
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_link_to_page')]/div[3]/span/span/button[@title='Edit']",
			RuntimeVariables.replace("Edit Link to Page Settings"));
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[5]/td[1]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[5]/td[2]",
			RuntimeVariables.replace("Name"));
		selenium.waitForVisible(
			"//form[contains(@class,'aui-textcelleditor-content')]/div/input");
		selenium.type("//form[contains(@class,'aui-textcelleditor-content')]/div/input",
			RuntimeVariables.replace("link"));
		selenium.clickAt("//button[@type='submit']",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Number"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_number')]/div/label"));
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_number')]",
			RuntimeVariables.replace("Number Field"));
		selenium.waitForVisible(
			"//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_number')]/div[3]/span/span/button[@title='Edit']");
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_number')]/div[3]/span/span/button[@title='Edit']",
			RuntimeVariables.replace("Edit Number Settings"));
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[5]/td[1]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[5]/td[2]",
			RuntimeVariables.replace("Name"));
		selenium.waitForVisible(
			"//form[contains(@class,'aui-textcelleditor-content')]/div/input");
		selenium.type("//form[contains(@class,'aui-textcelleditor-content')]/div/input",
			RuntimeVariables.replace("number"));
		selenium.clickAt("//button[@type='submit']",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Radio"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_radio')]/div/label"));
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_radio')]",
			RuntimeVariables.replace("Radio Field"));
		selenium.waitForVisible(
			"//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_radio')]/div[2]/span/span/button[@title='Edit']");
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_radio')]/div[2]/span/span/button[@title='Edit']",
			RuntimeVariables.replace("Edit Radio Settings"));
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[5]/td[1]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[5]/td[2]",
			RuntimeVariables.replace("Name"));
		selenium.waitForVisible(
			"//form[contains(@class,'aui-textcelleditor-content')]/div/input");
		selenium.type("//form[contains(@class,'aui-textcelleditor-content')]/div/input",
			RuntimeVariables.replace("radio"));
		selenium.clickAt("//button[@type='submit']",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_select')]/div/label"));
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_select')]",
			RuntimeVariables.replace("Select Field"));
		selenium.waitForVisible(
			"//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_select')]/div[2]/span/span/button[@title='Edit']");
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_select')]/div[2]/span/span/button[@title='Edit']",
			RuntimeVariables.replace("Edit Select Settings"));
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[5]/td[1]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[5]/td[2]",
			RuntimeVariables.replace("Name"));
		selenium.waitForVisible(
			"//form[contains(@class,'aui-textcelleditor-content')]/div/input");
		selenium.type("//form[contains(@class,'aui-textcelleditor-content')]/div/input",
			RuntimeVariables.replace("select"));
		selenium.clickAt("//button[@type='submit']",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Text"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_text')]/div/label"));
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_text')]",
			RuntimeVariables.replace("Text Field"));
		selenium.waitForVisible(
			"//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_text')]/div[3]/span/span/button[@title='Edit']");
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_text')]/div[3]/span/span/button[@title='Edit']",
			RuntimeVariables.replace("Edit Text Settings"));
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[5]/td[1]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[5]/td[2]",
			RuntimeVariables.replace("Name"));
		selenium.waitForVisible(
			"//form[contains(@class,'aui-textcelleditor-content')]/div/input");
		selenium.type("//form[contains(@class,'aui-textcelleditor-content')]/div/input",
			RuntimeVariables.replace("text"));
		selenium.clickAt("//button[@type='submit']",
			RuntimeVariables.replace("Save"));
		assertEquals(RuntimeVariables.replace("Text Box"),
			selenium.getText(
				"//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_text_box')]/div/label"));
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_text_box')]",
			RuntimeVariables.replace("Text Box Field"));
		selenium.waitForVisible(
			"//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_text_box')]/div[3]/span/span/button[@title='Edit']");
		selenium.clickAt("//div[@class='diagram-builder-drop-container']/div[contains(@id,'field_text_box')]/div[3]/span/span/button[@title='Edit']",
			RuntimeVariables.replace("Edit Text Box Settings"));
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText(
				"//tbody[@class='yui3-datatable-data']/tr[5]/td[1]"));
		selenium.doubleClickAt("//tbody[@class='yui3-datatable-data']/tr[5]/td[2]",
			RuntimeVariables.replace("Name"));
		selenium.waitForVisible(
			"//form[contains(@class,'aui-textcelleditor-content')]/div/input");
		selenium.type("//form[contains(@class,'aui-textcelleditor-content')]/div/input",
			RuntimeVariables.replace("textbox"));
		selenium.clickAt("//button[@type='submit']",
			RuntimeVariables.replace("Save"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
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
			RuntimeVariables.replace("WC Structure2 Name"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible(
			"//tr[contains(.,'WC Structure2 Name')]/td[3]/a");
		assertEquals(RuntimeVariables.replace("WC Structure2 Name"),
			selenium.getText("//tr[contains(.,'WC Structure2 Name')]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'WC Structure2 Name')]/td[6]/span[@title='Actions']/ul/li/strong/a"));
		selenium.clickAt("//tr[contains(.,'WC Structure2 Name')]/td[6]/span[@title='Actions']/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Templates')]");
		assertEquals(RuntimeVariables.replace("Manage Templates"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Templates')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Templates')]",
			RuntimeVariables.replace("Manage Templates"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText(
				"//span[@class='lfr-toolbar-button add-template ']/a"));
		selenium.clickAt("//span[@class='lfr-toolbar-button add-template ']/a",
			RuntimeVariables.replace("Add"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		selenium.waitForVisible("//input[@id='_166_name_en_US']");
		selenium.type("//input[@id='_166_name_en_US']",
			RuntimeVariables.replace("WC Template Structure2 Name"));
		selenium.type("//textarea[@id='_166_description_en_US']",
			RuntimeVariables.replace("WC Template Structure2 Description"));
		selenium.select("//select[@id='_166_editorType']",
			RuntimeVariables.replace("value=rich"));
		selenium.sendKeys("//div[@id=\"_166_richEditor\"]/textarea",
			RuntimeVariables.replace(
				"<p>$boolean.getData()</p><p>$date.getData()</p><p>$decimal.getData()</p><p><img src=\"$dm.getData()\"></img></p><p>$fileupload.getData()</p><p>$html.getData()</p><p><img src=\"$image.getData()\"></img></p><p>$integer.getData()</p><p><a href=\"$link.getData()\">Test Link</a></p><p>$number.getData()</p><p>$radio.getData()</p><p>$select.getData()</p><p>$text.getData()</p><p>$textbox.getData()</p>"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("WC Template Structure2 Name"),
			selenium.getText(
				"//tr[contains(.,'WC Template Structure2 Name')]/td[3]/a"));
		selenium.selectFrame("relative=top");
	}
}