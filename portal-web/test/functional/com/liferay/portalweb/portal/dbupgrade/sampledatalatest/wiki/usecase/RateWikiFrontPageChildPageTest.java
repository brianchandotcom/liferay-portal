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
public class RateWikiFrontPageChildPageTest extends BaseTestCase {
	public void testRateWikiFrontPageChildPage() throws Exception {
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
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='rating-label-element'])[2]", "0 Votes"));
		selenium.clickAt("//a[4]", RuntimeVariables.replace("Rating"));
		selenium.waitForPartialText("xPath=(//div[@class='rating-label-element'])[2]",
			"1 Vote");
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='rating-label-element'])[2]", "1 Vote"));
		assertTrue(selenium.isVisible(
				"xPath=(//a[contains(@class,'aui-rating-element-on')])[1]"));
		assertTrue(selenium.isVisible(
				"xPath=(//a[contains(@class,'aui-rating-element-on')])[2]"));
		assertTrue(selenium.isVisible(
				"xPath=(//a[contains(@class,'aui-rating-element-on')])[3]"));
		assertTrue(selenium.isVisible(
				"xPath=(//a[contains(@class,'aui-rating-element-on')])[4]"));
		assertTrue(selenium.isElementNotPresent(
				"xPath=(//a[contains(@class,'aui-rating-element-on')])[5]"));
		assertTrue(selenium.isVisible(
				"xPath=(//img[contains(@class,'aui-rating-element-on')])[1]"));
		assertTrue(selenium.isVisible(
				"xPath=(//img[contains(@class,'aui-rating-element-on')])[2]"));
		assertTrue(selenium.isVisible(
				"xPath=(//img[contains(@class,'aui-rating-element-on')])[3]"));
		assertTrue(selenium.isVisible(
				"xPath=(//img[contains(@class,'aui-rating-element-on')])[4]"));
		assertTrue(selenium.isElementNotPresent(
				"xPath=(//img[contains(@class,'aui-rating-element-on')])[5]"));
	}
}