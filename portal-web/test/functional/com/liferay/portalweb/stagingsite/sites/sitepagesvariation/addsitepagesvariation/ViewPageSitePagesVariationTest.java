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

package com.liferay.portalweb.stagingsite.sites.sitepagesvariation.addsitepagesvariation;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPageSitePagesVariationTest extends BaseTestCase {
	public void testViewPageSitePagesVariation() throws Exception {
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
		selenium.waitForVisible("link=Site Name");
		selenium.clickAt("link=Site Name", RuntimeVariables.replace("Site Name"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//body[contains(@class,'live-view')]"));
		assertTrue(selenium.isElementNotPresent(
				"//body[contains(@class,'local-staging')]"));
		assertTrue(selenium.isPartialText("//span/a[contains(.,'Staging')]",
				"Staging"));
		selenium.clickAt("//span/a[contains(.,'Staging')]",
			RuntimeVariables.replace("Staging"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Site Pages Variation Name"),
			selenium.getText(
				"//ul[@class='tabview-list site-variations-tabview-list']/li[contains(.,'Site Pages Variation Name')]/span/span/span"));
		assertTrue(selenium.isVisible("link=Test Page"));
		assertEquals(RuntimeVariables.replace("Main Variation"),
			selenium.getText(
				"//ul[@class='tabview-list site-variations-tabview-list']/li[contains(.,'Main Variation')]/span/span/a"));
		selenium.clickAt("//ul[@class='tabview-list site-variations-tabview-list']/li[contains(.,'Main Variation')]/span/span/a",
			RuntimeVariables.replace("Main Variation"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementNotPresent("link=Test Page"));
		assertEquals(RuntimeVariables.replace("Site Pages Variation Name"),
			selenium.getText(
				"//ul[@class='tabview-list site-variations-tabview-list']/li[contains(.,'Site Pages Variation Name')]/span/span/a"));
		selenium.clickAt("//ul[@class='tabview-list site-variations-tabview-list']/li[contains(.,'Site Pages Variation Name')]/span/span/a",
			RuntimeVariables.replace("Site Pages Variation Name"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Test Page", RuntimeVariables.replace("Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Main Variation"),
			selenium.getText(
				"//ul[@class='tabview-list site-variations-tabview-list']/li[contains(.,'Main Variation')]/span/span/a"));
		selenium.clickAt("//ul[@class='tabview-list site-variations-tabview-list']/li[contains(.,'Main Variation')]/span/span/a",
			RuntimeVariables.replace("Main Variation"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText(
				"//div[@id='_170_layoutRevisionDetails']",
				"The page Test Page is not enabled in Main Variation, but is available for other pages variations."));
	}
}