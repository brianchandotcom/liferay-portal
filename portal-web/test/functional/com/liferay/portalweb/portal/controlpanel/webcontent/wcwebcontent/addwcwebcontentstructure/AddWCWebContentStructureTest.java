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
public class AddWCWebContentStructureTest extends BaseTestCase {
	public void testAddWCWebContentStructure() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
				selenium.mouseOver("//li[@id='_145_mySites']/a/span");
				assertEquals(RuntimeVariables.replace("Go to"),
					selenium.getText("//li[@id='_145_mySites']/a/span"));
				selenium.waitForVisible("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Web Content",
					RuntimeVariables.replace("Web Content"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Add"),
					selenium.getText("//span[@title='Add']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Add']/ul/li/strong/a/span",
					RuntimeVariables.replace("Add"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Basic Web Content')]");
				assertEquals(RuntimeVariables.replace("Basic Web Content"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Basic Web Content')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Basic Web Content')]",
					RuntimeVariables.replace("Basic Web Content"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/html/js/editor/ckeditor/plugins/restore/plugin.js')]");
				selenium.waitForVisible(
					"//fieldset[@class='fieldset article-structure-toolbar ']/div/div/span[2]/a");
				assertEquals(RuntimeVariables.replace("Select"),
					selenium.getText(
						"//fieldset[@class='fieldset article-structure-toolbar ']/div/div/span[2]/a"));
				selenium.clickAt("//fieldset[@class='fieldset article-structure-toolbar ']/div/div/span[2]/a",
					RuntimeVariables.replace("Select"));
				selenium.waitForVisible(
					"//iframe[contains(@src,'_15_selectStructure')]");
				selenium.selectFrame(
					"//iframe[contains(@src,'_15_selectStructure')]");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/store.js')]");
				selenium.waitForVisible("//input[@name='_166_keywords']");
				selenium.type("//input[@name='_166_keywords']",
					RuntimeVariables.replace("WC Structure Name"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				Thread.sleep(1000);
				selenium.waitForVisible(
					"//tr[contains(.,'WC Structure Name')]/td[2]");
				assertEquals(RuntimeVariables.replace("WC Structure Name"),
					selenium.getText(
						"//tr[contains(.,'WC Structure Name')]/td[2]"));
				assertEquals(RuntimeVariables.replace(
						"WC Structure Description"),
					selenium.getText(
						"//tr[contains(.,'WC Structure Name')]/td[3]"));
				selenium.clickAt("//tr[contains(.,'WC Structure Name')]/td[5]/span/span/input[@value='Choose']",
					RuntimeVariables.replace("Choose"));
				selenium.waitForConfirmation(
					"Selecting a new structure will change the available input fields and available templates? Do you want to proceed?");
				selenium.selectFrame("relative=top");
				Thread.sleep(1000);
				selenium.waitForText("//span[@id='_15_structureNameLabel']",
					"WC Structure Name");
				assertEquals(RuntimeVariables.replace("WC Structure Name"),
					selenium.getText("//span[@id='_15_structureNameLabel']"));
				selenium.waitForText("//span[@class='template-name-label']",
					"WC Template Structure Name");
				assertEquals(RuntimeVariables.replace(
						"WC Template Structure Name"),
					selenium.getText("//span[@class='template-name-label']"));
				selenium.type("//input[@id='_15_title_en_US']",
					RuntimeVariables.replace("WC WebContent Structure Title"));
				assertEquals(RuntimeVariables.replace("Boolean"),
					selenium.getText("//label[contains(@for,'_15_boolean')]"));

				boolean booleanChecked = selenium.isChecked(
						"//input[contains(@id,'_15_boolean') and @type='checkbox']");

				if (booleanChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[contains(@id,'_15_boolean') and @type='checkbox']",
					RuntimeVariables.replace("Boolean"));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[contains(@id,'_15_boolean') and @type='checkbox']"));
				selenium.clickAt("//div[contains(@class,'aui-datepicker-button')]/button",
					RuntimeVariables.replace("Datepicker Button"));
				selenium.select("//select[@class='datepicker-month']",
					RuntimeVariables.replace("value=0"));
				selenium.select("//select[@class='datepicker-day']",
					RuntimeVariables.replace("value=1"));
				selenium.select("//select[@class='datepicker-year']",
					RuntimeVariables.replace("value=2020"));
				assertEquals(RuntimeVariables.replace("Decimal"),
					selenium.getText("//label[contains(@for,'_15_decimal')]"));
				selenium.type("//input[contains(@id,'_15_decimal')]",
					RuntimeVariables.replace("0.888"));
				assertEquals(RuntimeVariables.replace("Documents and Media"),
					selenium.getText("//label[contains(@for,'_15_dm')]"));
				selenium.clickAt("//input[@value='Select']",
					RuntimeVariables.replace("Documents and Media"));
				selenium.waitForVisible(
					"//iframe[contains(@id,'selectDocumentLibrary')]");
				selenium.selectFrame(
					"//iframe[contains(@id,'selectDocumentLibrary')]");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/store.js')]");
				Thread.sleep(1000);
				selenium.waitForVisible(
					"//tr[contains(.,'DM Document Title')]/td[1]/a");
				assertEquals(RuntimeVariables.replace("DM Document Title"),
					selenium.getText(
						"//tr[contains(.,'DM Document Title')]/td[1]/a"));
				selenium.clickAt("//tr[contains(.,'DM Document Title')]/td[5]/input[@value='Choose']",
					RuntimeVariables.replace("Choose"));
				selenium.selectFrame("relative=top");
				selenium.waitForValue("//input[contains(@id,'_15_dm')]",
					"DM Document Title");
				assertEquals("DM Document Title",
					selenium.getValue("//input[contains(@id,'_15_dm')]"));
				assertEquals(RuntimeVariables.replace("File Upload"),
					selenium.getText("//label[contains(@for,'_15_fileupload')]"));
				selenium.uploadCommonFile("//input[contains(@id,'_15_fileupload')]",
					RuntimeVariables.replace("Document_1.txt"));
				assertEquals(RuntimeVariables.replace("HTML"),
					selenium.getText("//div[@data-fieldname='html']/div/label"));
				selenium.waitForVisible(
					"//a[contains(@class,'cke_button cke_button__cut') and contains(@class,'cke_button_disabled')]");
				selenium.waitForVisible(
					"//iframe[contains(@title,'Rich Text Editor')]");
				selenium.typeFrame("//iframe[contains(@title,'Rich Text Editor')]",
					RuntimeVariables.replace(
						"WC WebContent Structure HTML Body"));
				assertEquals(RuntimeVariables.replace("Image"),
					selenium.getText("//label[contains(@for,'_15_image')]"));
				selenium.uploadCommonFile("//input[contains(@id,'_15_image') and @type='file']",
					RuntimeVariables.replace("Document_1.jpg"));
				assertEquals(RuntimeVariables.replace("Integer"),
					selenium.getText("//label[contains(@for,'_15_integer')]"));
				selenium.type("//input[contains(@id,'_15_integer') and @type='text']",
					RuntimeVariables.replace("888"));
				assertEquals(RuntimeVariables.replace("Link to Page"),
					selenium.getText(
						"//div[@class='lfr-ddm-container']/div/div/label[contains(.,'Link to Page')]"));
				selenium.select("//div[@class='lfr-ddm-container']/div/div/select[contains(@name,'link')]",
					RuntimeVariables.replace("index=0"));
				assertEquals(RuntimeVariables.replace("Number"),
					selenium.getText("//label[contains(@for,'_15_number')]"));
				selenium.type("//input[contains(@id,'_15_number')]",
					RuntimeVariables.replace("12345"));
				assertEquals(RuntimeVariables.replace("Radio"),
					selenium.getText(
						"//div[@class='lfr-ddm-container']/div/div/label[contains(.,'Radio')]"));
				selenium.check(
					"//div[@data-fieldname='radio']/div/span[contains(.,'option 2')]/span/span/input");
				assertTrue(selenium.isChecked(
						"//div[@data-fieldname='radio']/div/span[contains(.,'option 2')]/span/span/input"));
				assertEquals(RuntimeVariables.replace("Select"),
					selenium.getText(
						"//div[@data-fieldname='select']/div/span/span/label[contains(.,'Select')]"));
				selenium.select("//select[contains(@id,'_15_select')]",
					RuntimeVariables.replace("label=option 3"));
				assertEquals("option 3",
					selenium.getSelectedLabel(
						"//select[contains(@id,'_15_select')]"));
				assertEquals(RuntimeVariables.replace("Text"),
					selenium.getText("//label[contains(@for,'_15_text')]"));
				selenium.type("//input[contains(@id,'_15_text')]",
					RuntimeVariables.replace("WC Structure Text"));
				assertEquals(RuntimeVariables.replace("Text Box"),
					selenium.getText("//label[contains(@for,'_15_textbox')]"));
				selenium.type("//textarea[contains(@id,'_15_textbox')]",
					RuntimeVariables.replace("WC Structure TextBox"));
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertTrue(selenium.isVisible(
						"//div[@data-title='WC WebContent Structure Title']/a/div/img"));
				assertEquals(RuntimeVariables.replace(
						"WC WebContent Structure Title"),
					selenium.getText(
						"//div[@data-title='WC WebContent Structure Title']/a/span"));

			case 100:
				label = -1;
			}
		}
	}
}