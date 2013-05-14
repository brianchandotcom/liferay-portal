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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcwebcontentstructure;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWCWebContentStructureTest extends BaseTestCase {
	public void testViewWCWebContentStructure() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText("//div[@class='parent-title']/span"));
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText(
				"//div[contains(@class,'lfr-list-view-content folder-display')]/ul/li/a[contains(.,'Home')]"));
		assertEquals(RuntimeVariables.replace("Recent"),
			selenium.getText(
				"//div[contains(@class,'lfr-list-view-content folder-display')]/ul/li/a[contains(.,'Recent')]"));
		assertEquals(RuntimeVariables.replace("Mine"),
			selenium.getText(
				"//div[contains(@class,'lfr-list-view-content folder-display')]/ul/li/a[contains(.,'Mine')]"));
		assertTrue(selenium.isVisible("//input[@id='_15_allRowIdsCheckbox']"));
		assertFalse(selenium.isVisible(
				"//span[@title='Actions']/ul/li/strong/a"));
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[@title='Add']/ul/li/strong/a"));
		assertEquals(RuntimeVariables.replace("Manage"),
			selenium.getText("//span[@title='Manage']/ul/li/strong/a"));
		assertTrue(selenium.isVisible("//button[@title='Icon View']"));
		assertTrue(selenium.isVisible("//button[@title='Descriptive View']"));
		assertTrue(selenium.isVisible("//button[@title='List View']"));
		assertTrue(selenium.isVisible("//input[@id='_15_keywords']"));
		assertEquals("Search", selenium.getValue("//input[@value='Search']"));
		assertTrue(selenium.isVisible("//input[@id='_15_showAdvancedSearch']"));
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText(
				"//div[@class='journal-breadcrumb']/ul/li[@class='only']/span/a"));
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText(
				"//div[@id='_15_breadcrumbContainer']/ul/li/span/a"));
		assertTrue(selenium.isVisible(
				"//div[@data-title='WC WebContent Structure Title']/a/div/img"));
		assertEquals(RuntimeVariables.replace("WC WebContent Structure Title"),
			selenium.getText(
				"//div[@data-title='WC WebContent Structure Title']/a/span"));
		assertEquals(RuntimeVariables.replace("<<"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/a[1]"));
		assertEquals(RuntimeVariables.replace("<"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/a[2]"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/span[1]"));
		assertEquals(RuntimeVariables.replace(">"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/a[3]"));
		assertEquals(RuntimeVariables.replace(">>"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/a[4]"));
		assertEquals(RuntimeVariables.replace("1 of 1"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/span[2]"));
		assertEquals(RuntimeVariables.replace("(Total 1)"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/span[3]"));
		assertEquals("20",
			selenium.getSelectedLabel(
				"//div[contains(@class,'article-entries-paginator')]/select"));
		selenium.clickAt("//div[@data-title='WC WebContent Structure Title']/a/span",
			RuntimeVariables.replace("WC WebContent Structure Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertTrue(selenium.isVisible(
				"//span[@title='Options']/ul/li/strong/a/span"));
		assertEquals(RuntimeVariables.replace("WC WebContent Structure Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("//span[@class='header-back-to']/a"));
		assertTrue(selenium.isVisible("//span[@class='workflow-id']"));
		assertEquals(RuntimeVariables.replace("1.0"),
			selenium.getText("//span[@class='workflow-version']/strong"));
		assertEquals(RuntimeVariables.replace("Approved"),
			selenium.getText("//span[@class='workflow-status']/strong"));
		assertTrue(selenium.isVisible("//span[@class='taglib-icon-help']/img"));
		assertEquals(RuntimeVariables.replace(
				"A new version will be created automatically if this content is modified."),
			selenium.getText("//span[@class='taglib-icon-help']/span"));
		assertEquals(RuntimeVariables.replace("Preview"),
			selenium.getText(
				"//span[@class='toolbar-content']/button[contains(.,'Preview')]"));
		assertEquals(RuntimeVariables.replace("Download"),
			selenium.getText(
				"//span[@class='toolbar-content']/button[contains(.,'Download')]"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"//span[@class='toolbar-content']/button[contains(.,'Permissions')]"));
		assertEquals(RuntimeVariables.replace("View History"),
			selenium.getText(
				"//span[@class='toolbar-content']/button[contains(.,'View History')]"));
		assertEquals(RuntimeVariables.replace("Structure:"),
			selenium.getText(
				"//div[@class='column-content article-structure-content ']/label"));
		assertEquals(RuntimeVariables.replace("WC Structure Name"),
			selenium.getText(
				"//div[@class='column-content article-structure-content ']/fieldset/div/div/span"));
		assertEquals(RuntimeVariables.replace("Template:"),
			selenium.getText(
				"//div[@class='column-content article-template-content ']/label"));
		assertEquals(RuntimeVariables.replace("WC Template Structure Name"),
			selenium.getText(
				"//div[@class='column-content article-template-content ']/fieldset/div/div/span"));
		assertEquals(RuntimeVariables.replace("Default Language"),
			selenium.getText("//label[@for='_15_defaultLanguageId']"));
		assertEquals(RuntimeVariables.replace("English (United States)"),
			selenium.getText("//span[@id='_15_textLanguageId']"));
		assertEquals(RuntimeVariables.replace("Change"),
			selenium.getText("//a[@id='_15_changeLanguageId']"));
		assertEquals(RuntimeVariables.replace("Add Translation"),
			selenium.getText(
				"//span[@title='Add Translation']/ul/li/strong/a/span"));
		assertEquals("WC WebContent Structure Title",
			selenium.getValue("//input[@id='_15_title_en_US']"));
		assertTrue(selenium.isChecked(
				"//input[contains(@id,'_15_boolean') and @type='checkbox']"));
		selenium.clickAt("//div[contains(@class,'aui-datepicker-button')]/button",
			RuntimeVariables.replace("Datepicker Button"));
		assertEquals("0",
			selenium.getValue("//select[@class='datepicker-month']"));
		assertEquals("1", selenium.getValue("//select[@class='datepicker-day']"));
		assertEquals("2020",
			selenium.getValue("//select[@class='datepicker-year']"));
		assertEquals(RuntimeVariables.replace("Decimal"),
			selenium.getText("//label[contains(@for,'_15_decimal')]"));
		assertEquals("0.888",
			selenium.getValue("//input[contains(@id,'_15_decimal')]"));
		assertEquals("DM Document Title",
			selenium.getValue("//input[contains(@id,'_15_dm')]"));
		assertEquals(RuntimeVariables.replace("File Upload"),
			selenium.getText("//label[contains(@for,'_15_fileupload')]"));
		assertEquals("Document_1.txt",
			selenium.getValue("//input[contains(@id,'_15_fileupload')]"));
		assertEquals(RuntimeVariables.replace("HTML"),
			selenium.getText("//div[@data-fieldname='html']/div/label"));
		selenium.waitForVisible(
			"//a[contains(@class,'cke_button cke_button__cut') and contains(@class,'cke_button_disabled')]");
		selenium.waitForVisible("//iframe[contains(@title,'Rich Text Editor')]");
		selenium.selectFrame("//iframe[contains(@title,'Rich Text Editor')]");
		assertEquals(RuntimeVariables.replace(
				"WC WebContent Structure HTML Body"), selenium.getText("//body"));
		selenium.selectFrame("relative=top");
		assertEquals(RuntimeVariables.replace("Image"),
			selenium.getText("//label[contains(@for,'_15_image')]"));
		assertEquals("Document_1.jpg",
			selenium.getValue(
				"//input[contains(@id,'_15_image') and @type='file']"));
		assertEquals(RuntimeVariables.replace("Integer"),
			selenium.getText("//label[contains(@for,'_15_integer')]"));
		assertEquals("888",
			selenium.getValue(
				"//input[contains(@id,'_15_integer') and @type='text']"));
		assertEquals(RuntimeVariables.replace("Link to Page"),
			selenium.getText(
				"//div[@class='lfr-ddm-container']/div/div/label[contains(.,'Link to Page')]"));
		selenium.select("//div[@class='lfr-ddm-container']/div/div/select",
			RuntimeVariables.replace("index=0"));
		assertEquals(RuntimeVariables.replace("Number"),
			selenium.getText("//label[contains(@for,'_15_number')]"));
		assertEquals("12345",
			selenium.getValue("//input[contains(@id,'_15_number')]"));
		assertEquals(RuntimeVariables.replace("Radio"),
			selenium.getText(
				"//div[@class='lfr-ddm-container']/div/div/label[contains(.,'Radio')]"));
		assertTrue(selenium.isChecked(
				"//div[@data-fieldname='radio']/div/span[contains(.,'option 2')]/span/span/input"));
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText(
				"//div[@data-fieldname='select']/div/span/span/label[contains(.,'Select')]"));
		assertEquals("option 3",
			selenium.getSelectedLabel("//select[contains(@id,'_15_select')]"));
		assertEquals(RuntimeVariables.replace("Text"),
			selenium.getText("//label[contains(@for,'_15_text')]"));
		assertEquals("WC Structure Text",
			selenium.getValue("//input[contains(@id,'_15_text')]"));
		assertEquals(RuntimeVariables.replace("Text Box"),
			selenium.getText("//label[contains(@for,'_15_textbox')]"));
		assertEquals("WC Structure TextBox",
			selenium.getValue("//textarea[contains(@id,'_15_textbox')]"));
		assertTrue(selenium.isChecked("//input[@id='_15_indexableCheckbox']"));
		assertEquals(RuntimeVariables.replace("Searchable"),
			selenium.getText("//label[@for='_15_indexableCheckbox']"));
		assertEquals(RuntimeVariables.replace("Content (Modified)"),
			selenium.getText("//li[@class='selected']/a[@id='_15_contentLink']"));
		assertTrue(selenium.isPartialText("//a[@id='_15_abstractLink']",
				"Abstract"));
		assertTrue(selenium.isPartialText("//a[@id='_15_categorizationLink']",
				"Categorization"));
		assertTrue(selenium.isPartialText("//a[@id='_15_scheduleLink']",
				"Schedule"));
		assertTrue(selenium.isPartialText("//a[@id='_15_displayPageLink']",
				"Display Page"));
		assertTrue(selenium.isPartialText("//a[@id='_15_relatedAssetsLink']",
				"Related Assets"));
		assertTrue(selenium.isPartialText("//a[@id='_15_customFieldsLink']",
				"Custom Fields"));
		assertEquals(RuntimeVariables.replace(
				"A new version will be created automatically if this content is modified."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertTrue(selenium.isVisible("//input[@value='Save as Draft']"));
		assertTrue(selenium.isVisible("//input[@value='Publish']"));
		assertTrue(selenium.isVisible("//input[@value='Cancel']"));
	}
}