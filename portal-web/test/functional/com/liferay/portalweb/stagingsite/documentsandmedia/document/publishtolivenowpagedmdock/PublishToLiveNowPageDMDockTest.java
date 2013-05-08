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

package com.liferay.portalweb.stagingsite.documentsandmedia.document.publishtolivenowpagedmdock;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class PublishToLiveNowPageDMDockTest extends BaseTestCase {
	public void testPublishToLiveNowPageDMDock() throws Exception {
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
				selenium.waitForVisible("link=Site Name (Staging)");
				selenium.clickAt("link=Site Name (Staging)",
					RuntimeVariables.replace("Site Name (Staging)"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("link=Documents and Media Test Page");
				selenium.clickAt("link=Documents and Media Test Page",
					RuntimeVariables.replace("Documents and Media Test Page"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//body[contains(@class,'local-staging')]"));
				assertTrue(selenium.isElementNotPresent(
						"//body[contains(@class,'live-view')]"));
				assertNotEquals(RuntimeVariables.replace("Live"),
					selenium.getText("//li[1]/span/a"));
				selenium.clickAt("link=Documents and Media Test Page",
					RuntimeVariables.replace("Documents and Media Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForElementPresent(
					"//nav[@class='site-breadcrumbs aui-hide lfr-hudcrumbs']");
				assertEquals(RuntimeVariables.replace("DM Document Title"),
					selenium.getText(
						"//a[@title='DM Document Title - DM Document Description']"));
				selenium.clickAt("//strong/a",
					RuntimeVariables.replace("Staging"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Publish to Live Now')]");
				assertEquals(RuntimeVariables.replace("Publish to Live Now"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Publish to Live Now')]"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Publish to Live Now')]",
					RuntimeVariables.replace("Publish to Live Now"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/panel.js')]");
				selenium.waitForElementPresent(
					"//div[@class='yui3-widget-bd aui-panel-bd aui-dialog-bd']");
				assertEquals(RuntimeVariables.replace(
						"There are no selected pages. All pages will therefore be exported."),
					selenium.getText("//div[@class='portlet-msg-info']"));
				selenium.clickAt("//input[@value='Select Pages']",
					RuntimeVariables.replace("Select Pages"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/dataschema-xml/dataschema-xml-min.js')]");
				selenium.waitForVisible("//div[4]");

				boolean pageVisible = selenium.isVisible(
						"//li/ul/li/div/div[contains(.,'Documents and Media Test Page')]");

				if (pageVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//li/div/div",
					RuntimeVariables.replace("Drop down arrow"));

			case 2:
				selenium.clickAt("//li/ul/li/div/div[contains(.,'Documents and Media Test Page')]",
					RuntimeVariables.replace("Documents and Media Test Page"));
				selenium.waitForElementPresent(
					"//div[contains(@class,'aui-tree-node-checked')]");
				selenium.clickAt("//input[@value='Select']",
					RuntimeVariables.replace("Select"));
				selenium.waitForVisible("//div[2]/div[1]/a");

				boolean documentsAndMediaVisible = selenium.isVisible(
						"_88_PORTLET_DATA_20Checkbox");

				if (documentsAndMediaVisible) {
					label = 3;

					continue;
				}

				selenium.clickAt("//div[2]/div[1]/a",
					RuntimeVariables.replace("Plus"));

			case 3:
				selenium.waitForElementPresent("//input[@id='_88_rangeAll']");
				selenium.clickAt("//input[@id='_88_rangeAll']",
					RuntimeVariables.replace("All"));

				boolean documentsAndMediaChecked = selenium.isChecked(
						"_88_PORTLET_DATA_20Checkbox");

				if (documentsAndMediaChecked) {
					label = 4;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@id='_88_PORTLET_DATA_20Checkbox']"));
				selenium.clickAt("//input[@id='_88_PORTLET_DATA_20Checkbox']",
					RuntimeVariables.replace("Documents and Media"));
				assertTrue(selenium.isChecked(
						"//input[@id='_88_PORTLET_DATA_20Checkbox']"));

			case 4:
				selenium.waitForVisible("//input[@value='Publish']");
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to publish these pages[\\s\\S]$"));
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.open("/web/site-name-staging/");
				selenium.waitForVisible("link=Documents and Media Test Page");
				selenium.click(RuntimeVariables.replace(
						"link=Documents and Media Test Page"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//body[contains(@class,'local-staging')]"));
				assertTrue(selenium.isElementNotPresent(
						"//body[contains(@class,'live-view')]"));
				assertEquals(RuntimeVariables.replace("Live"),
					selenium.getText("//li[1]/span/a"));

			case 100:
				label = -1;
			}
		}
	}
}