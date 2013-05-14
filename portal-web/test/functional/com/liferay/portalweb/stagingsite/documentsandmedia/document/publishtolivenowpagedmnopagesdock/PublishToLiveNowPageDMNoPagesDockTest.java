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

package com.liferay.portalweb.stagingsite.documentsandmedia.document.publishtolivenowpagedmnopagesdock;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class PublishToLiveNowPageDMNoPagesDockTest extends BaseTestCase {
	public void testPublishToLiveNowPageDMNoPagesDock()
		throws Exception {
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
				selenium.clickAt("link=Documents and Media Test Page",
					RuntimeVariables.replace("Documents and Media Test Page"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//body[contains(@class,'local-staging')]"));
				assertTrue(selenium.isElementNotPresent(
						"//body[contains(@class,'live-view')]"));
				assertNotEquals(RuntimeVariables.replace("Live"),
					selenium.getText("//li[1]/span/a"));
				assertTrue(selenium.isElementPresent(
						"//body[contains(@class,'local-staging')]"));
				assertTrue(selenium.isElementNotPresent(
						"//body[contains(@class,'live-view')]"));
				selenium.clickAt("link=Documents and Media Test Page",
					RuntimeVariables.replace("Documents and Media Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForElementPresent(
					"//nav[@class='site-breadcrumbs hide lfr-hudcrumbs']");
				assertEquals(RuntimeVariables.replace("DM Document Title"),
					selenium.getText(
						"//a[@title='DM Document Title - DM Document Description']"));
				selenium.clickAt("//strong/a",
					RuntimeVariables.replace("Staging"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Publish to Live Now')]");
				assertEquals(RuntimeVariables.replace("Publish to Live Now"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Publish to Live Now')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Publish to Live Now')]",
					RuntimeVariables.replace("Publish to Live Now"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/panel.js')]");
				selenium.waitForElementPresent(
					"//div[@class='yui3-widget-bd panel-bd dialog-bd']");
				selenium.waitForVisible("//div[@class='portlet-msg-info']");
				assertEquals(RuntimeVariables.replace(
						"There are no selected pages. All pages will therefore be exported."),
					selenium.getText("//div[@class='portlet-msg-info']"));

				boolean documentsAndMediaVisible = selenium.isVisible(
						"_88_PORTLET_DATA_20Checkbox");

				if (documentsAndMediaVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[2]/div[1]/a",
					RuntimeVariables.replace("Plus"));

			case 2:
				selenium.waitForElementPresent("//input[@id='_88_rangeAll']");
				selenium.clickAt("//input[@id='_88_rangeAll']",
					RuntimeVariables.replace("All"));

				boolean documentsAndMediaChecked = selenium.isChecked(
						"_88_PORTLET_DATA_20Checkbox");

				if (documentsAndMediaChecked) {
					label = 3;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@id='_88_PORTLET_DATA_20Checkbox']"));
				selenium.clickAt("//input[@id='_88_PORTLET_DATA_20Checkbox']",
					RuntimeVariables.replace("Documents and Media"));
				assertTrue(selenium.isChecked(
						"//input[@id='_88_PORTLET_DATA_20Checkbox']"));

			case 3:
				Thread.sleep(5000);
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
				selenium.clickAt("link=Documents and Media Test Page",
					RuntimeVariables.replace("Documents and Media Test Page"));
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