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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.wiki.usecase;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RateWikiFrontPageChildPageComment1Test extends BaseTestCase {
	public void testRateWikiFrontPageChildPageComment1()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/wiki-use-case-community/");
		selenium.waitForVisible("link=Wiki Test Page");
		selenium.clickAt("link=Wiki Test Page",
			RuntimeVariables.replace("Wiki Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Wiki FrontPage ChildPage Title"),
			selenium.getText("//div[@class='child-pages']/ul/li/a"));
		selenium.clickAt("//div[@class='child-pages']/ul/li/a",
			RuntimeVariables.replace("Wiki FrontPage ChildPage Title"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible(
			"xPath=(//div[@class='rating-label-element'])[3]");
		assertEquals(RuntimeVariables.replace("0 (0 Votes)"),
			selenium.getText("xPath=(//div[@class='rating-label-element'])[3]"));
		assertTrue(selenium.isElementNotPresent(
				"//a[contains(@class,'aui-rating-element-off aui-rating-thumb-up aui-rating-element-on')]"));
		selenium.clickAt("//a[contains(@class,'aui-rating-element-off aui-rating-thumb-up')]",
			RuntimeVariables.replace("Thumbs Up"));
		selenium.waitForText("xPath=(//div[@class='rating-label-element'])[3]",
			"+1 (1 Vote)");
		assertEquals(RuntimeVariables.replace("+1 (1 Vote)"),
			selenium.getText("xPath=(//div[@class='rating-label-element'])[3]"));
		assertTrue(selenium.isVisible(
				"//a[contains(@class,'aui-rating-element-off aui-rating-thumb-up aui-rating-element-on')]"));
	}
}