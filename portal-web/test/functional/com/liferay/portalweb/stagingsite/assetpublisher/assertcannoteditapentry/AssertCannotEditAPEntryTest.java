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

package com.liferay.portalweb.stagingsite.assetpublisher.assertcannoteditapentry;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssertCannotEditAPEntryTest extends BaseTestCase {
	public void testAssertCannotEditAPEntry() throws Exception {
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
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Live"),
			selenium.getText(
				"//div[@class='staging-bar']/ul/li/span/span[contains(.,'Live')]"));
		assertEquals(RuntimeVariables.replace("Site Name"),
			selenium.getText("//h1[@class='site-title']/span"));
		assertTrue(selenium.isElementNotPresent(
				"//span[@title='Options']/ul/li/strong/a"));
		assertTrue(selenium.isElementNotPresent("//img[@title='Minimize']"));
		assertTrue(selenium.isElementNotPresent("//img[@title='Maximize']"));
		assertTrue(selenium.isElementNotPresent("//img[@title='Remove']"));
		assertTrue(selenium.isElementNotPresent(
				"//span[@title='Add New']/ul/li/strong/a/span"));
		assertEquals(RuntimeVariables.replace("Blogs Entry Title"),
			selenium.getText("//h3[@class='asset-title']/a"));
		assertTrue(selenium.isElementNotPresent(
				"//div[contains(.,'Edit')]/span/a/span"));
		assertEquals(RuntimeVariables.replace("Blogs Entry Content"),
			selenium.getText("//div[@class='asset-summary']"));
	}
}