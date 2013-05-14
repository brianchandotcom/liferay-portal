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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.addwcwebcontentsearchable;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWCWebContentSearchableTest extends BaseTestCase {
	public void testAddWCWebContentSearchable() throws Exception {
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
				assertEquals(RuntimeVariables.replace("Add"),
					selenium.getText("//span[@title='Add']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Add']/ul/li/strong/a/span",
					RuntimeVariables.replace("Add"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Basic Web Content')]");
				assertEquals(RuntimeVariables.replace("Basic Web Content"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Basic Web Content')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Basic Web Content')]"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_15_title_en_US']",
					RuntimeVariables.replace("WC WebContent Title"));
				selenium.waitForVisible(
					"//a[contains(@class,'cke_button cke_button__cut') and contains(@class,'cke_button_disabled')]");
				selenium.waitForVisible(
					"//iframe[contains(@title,'Rich Text Editor')]");
				selenium.typeFrame("//iframe[contains(@title,'Rich Text Editor')]",
					RuntimeVariables.replace("WC WebContent Content"));

				boolean webContentSearchableIsChecked = selenium.isChecked(
						"//input[@id='_15_indexableCheckbox']");

				if (webContentSearchableIsChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_15_indexableCheckbox']",
					RuntimeVariables.replace("Web Content Searchable Checkbox"));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@id='_15_indexableCheckbox']"));
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertTrue(selenium.isVisible(
						"//a[contains(@title,'WC WebContent Title')]/div/img"));
				assertEquals(RuntimeVariables.replace("WC WebContent Title"),
					selenium.getText(
						"//a[@class='entry-link']/span[contains(.,'WC WebContent Title')]"));

			case 100:
				label = -1;
			}
		}
	}
}