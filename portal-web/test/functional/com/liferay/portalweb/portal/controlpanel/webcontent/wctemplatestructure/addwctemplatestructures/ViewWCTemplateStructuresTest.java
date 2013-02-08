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

package com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatestructures;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWCTemplateStructuresTest extends BaseTestCase {
	public void testViewWCTemplateStructures() throws Exception {
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
					"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Structures')]");
				assertEquals(RuntimeVariables.replace("Structures"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Structures')]"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Structures')]",
					RuntimeVariables.replace("Structures"));
				selenium.waitForVisible("//iframe[contains(@src,'Structures')]");
				selenium.selectFrame("//iframe[contains(@src,'Structures')]");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/store.js')]");
				Thread.sleep(1000);
				selenium.waitForVisible("//input[@name='_166_keywords']");
				selenium.type("//input[@name='_166_keywords']",
					RuntimeVariables.replace("WC Structure1 Name"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForVisible(
					"//tr[contains(.,'WC Structure1 Name')]/td[3]/a");
				assertEquals(RuntimeVariables.replace("WC Structure1 Name"),
					selenium.getText(
						"//tr[contains(.,'WC Structure1 Name')]/td[3]/a"));
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//tr[contains(.,'WC Structure1 Name')]/td[5]/span/ul/li/strong/a"));
				selenium.clickAt("//tr[contains(.,'WC Structure1 Name')]/td[5]/span/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Manage Templates')]");
				assertEquals(RuntimeVariables.replace("Manage Templates"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Manage Templates')]"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Manage Templates')]",
					RuntimeVariables.replace("Manage Templates"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//input[@name='_166_keywords']");
				selenium.type("//input[@name='_166_keywords']",
					RuntimeVariables.replace("WC Template Structure1 Name"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForVisible(
					"//tr[contains(.,'WC Template Structure1 Name')]/td[3]/a");
				assertEquals(RuntimeVariables.replace(
						"WC Template Structure1 Name"),
					selenium.getText(
						"//tr[contains(.,'WC Template Structure1 Name')]/td[3]/a"));
				selenium.clickAt("//tr[contains(.,'WC Template Structure1 Name')]/td[3]/a",
					RuntimeVariables.replace("WC Template Structure1 Name"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);
				selenium.waitForVisible("//input[@id='_166_name_en_US']");
				assertEquals("WC Template Structure1 Name",
					selenium.getValue("//input[@id='_166_name_en_US']"));
				assertEquals("WC Template Structure1 Description",
					selenium.getValue(
						"//textarea[@id='_166_description_en_US']"));

				boolean plainEditor1NotVisible = selenium.isVisible(
						"//textarea[@id='_166_plainEditorField']");

				if (plainEditor1NotVisible) {
					label = 2;

					continue;
				}

				selenium.select("//select[@id='_166_editorType']",
					RuntimeVariables.replace("Plain"));

			case 2:
				assertEquals("Plain",
					selenium.getSelectedLabel("//select[@id='_166_editorType']"));
				assertEquals("<p>$text.getData()</p>",
					selenium.getValue("//textarea[@id='_166_plainEditorField']"));
				selenium.clickAt("//a[contains(.,'\u00ab Back')]",
					RuntimeVariables.replace("\u00ab Back"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//input[@name='_166_keywords']");
				selenium.type("//input[@name='_166_keywords']",
					RuntimeVariables.replace("WC Template Structure2 Name"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForVisible(
					"//tr[contains(.,'WC Template Structure2 Name')]/td[3]/a");
				assertEquals(RuntimeVariables.replace(
						"WC Template Structure2 Name"),
					selenium.getText(
						"//tr[contains(.,'WC Template Structure2 Name')]/td[3]/a"));
				selenium.clickAt("//tr[contains(.,'WC Template Structure2 Name')]/td[3]/a",
					RuntimeVariables.replace("WC Template Structure2 Name"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);
				selenium.waitForVisible("//input[@id='_166_name_en_US']");
				assertEquals("WC Template Structure2 Name",
					selenium.getValue("//input[@id='_166_name_en_US']"));
				assertEquals("WC Template Structure2 Description",
					selenium.getValue(
						"//textarea[@id='_166_description_en_US']"));

				boolean plainEditor2NotVisible = selenium.isVisible(
						"//textarea[@id='_166_plainEditorField']");

				if (plainEditor2NotVisible) {
					label = 3;

					continue;
				}

				selenium.select("//select[@id='_166_editorType']",
					RuntimeVariables.replace("Plain"));

			case 3:
				assertEquals("Plain",
					selenium.getSelectedLabel("//select[@id='_166_editorType']"));
				assertEquals("<p>$text.getData()</p>",
					selenium.getValue("//textarea[@id='_166_plainEditorField']"));
				selenium.clickAt("//a[contains(.,'\u00ab Back')]",
					RuntimeVariables.replace("\u00ab Back"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//input[@name='_166_keywords']");
				selenium.type("//input[@name='_166_keywords']",
					RuntimeVariables.replace("WC Template Structure3 Name"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForVisible(
					"//tr[contains(.,'WC Template Structure3 Name')]/td[3]/a");
				assertEquals(RuntimeVariables.replace(
						"WC Template Structure3 Name"),
					selenium.getText(
						"//tr[contains(.,'WC Template Structure3 Name')]/td[3]/a"));
				selenium.clickAt("//tr[contains(.,'WC Template Structure3 Name')]/td[3]/a",
					RuntimeVariables.replace("WC Template Structure3 Name"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);
				selenium.waitForVisible("//input[@id='_166_name_en_US']");
				assertEquals("WC Template Structure3 Name",
					selenium.getValue("//input[@id='_166_name_en_US']"));
				assertEquals("WC Template Structure3 Description",
					selenium.getValue(
						"//textarea[@id='_166_description_en_US']"));

				boolean plainEditor3NotVisible = selenium.isVisible(
						"//textarea[@id='_166_plainEditorField']");

				if (plainEditor3NotVisible) {
					label = 4;

					continue;
				}

				selenium.select("//select[@id='_166_editorType']",
					RuntimeVariables.replace("Plain"));

			case 4:
				assertEquals("Plain",
					selenium.getSelectedLabel("//select[@id='_166_editorType']"));
				assertEquals("<p>$text.getData()</p>",
					selenium.getValue("//textarea[@id='_166_plainEditorField']"));
				selenium.selectFrame("relative=top");

			case 100:
				label = -1;
			}
		}
	}
}