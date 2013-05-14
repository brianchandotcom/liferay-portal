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

package com.liferay.portalweb.portal.controlpanel.blogs.entry.addblogsentryratingcp;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddBlogsEntryRatingCPTest extends BaseTestCase {
	public void testAddBlogsEntryRatingCP() throws Exception {
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
		selenium.clickAt("link=Blogs", RuntimeVariables.replace("Blogs"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs Entry Title"),
			selenium.getText("//tr[contains(.,'Blogs Entry Title')]/td[2]/a"));
		selenium.clickAt("//tr[contains(.,'Blogs Entry Title')]/td[2]/a",
			RuntimeVariables.replace("Blogs Entry Title"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='rating-label-element'])[2]", "0 Votes"));
		selenium.waitForVisible(
			"//div[@class='taglib-ratings stars']/div/div/a[5]");
		selenium.clickAt("//div[@class='taglib-ratings stars']/div/div/a[5]",
			RuntimeVariables.replace("Rate 5 Stars"));
		selenium.waitForPartialText("xPath=(//div[@class='rating-label-element'])[2]",
			"1 Vote");
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='rating-label-element'])[2]", "1 Vote"));
	}
}