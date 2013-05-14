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

package com.liferay.portalweb.portlet.messageboards.mbthread.ratembcategorythread;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RateMBCategoryThreadTest extends BaseTestCase {
	public void testRateMBCategoryThread() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MB Category Name"),
			selenium.getText("//a/strong"));
		selenium.clickAt("//a/strong",
			RuntimeVariables.replace("MB Category Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message Subject"),
			selenium.getText("//td[1]/a"));
		selenium.clickAt("//td[1]/a",
			RuntimeVariables.replace("MB Category Thread Message Subject"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("0 (0 Votes)"),
			selenium.getText(
				"//div[@class='taglib-ratings thumbs']/div/div/div"));
		assertTrue(selenium.isVisible(
				"//a[@class='rating-element rating-element-off rating-thumb-up']"));
		assertTrue(selenium.isVisible(
				"//a[@class='rating-element rating-element-off rating-thumb-down']"));
		assertTrue(selenium.isElementNotPresent(
				"//a[@class='rating-element rating-element-off rating-thumb-up rating-element-on']"));
		assertTrue(selenium.isElementNotPresent(
				"//a[@class='rating-element rating-element-off rating-thumb-down rating-element-on']"));
		selenium.clickAt("//div[@class='taglib-ratings thumbs']/div/div/a[1]",
			RuntimeVariables.replace("Thumb Up"));
		selenium.waitForText("//div[@class='taglib-ratings thumbs']/div/div/div",
			"+1 (1 Vote)");
		assertEquals(RuntimeVariables.replace("+1 (1 Vote)"),
			selenium.getText(
				"//div[@class='taglib-ratings thumbs']/div/div/div"));
		assertTrue(selenium.isElementNotPresent(
				"//a[@class='rating-element rating-element-off rating-thumb-up']"));
		assertTrue(selenium.isVisible(
				"//a[@class='rating-element rating-element-off rating-thumb-down']"));
		assertTrue(selenium.isVisible(
				"//a[@class='rating-element rating-element-off rating-thumb-up rating-element-on']"));
		assertTrue(selenium.isElementNotPresent(
				"//a[@class='rating-element rating-element-off rating-thumb-down rating-element-on']"));
		selenium.clickAt("//div[@class='taglib-ratings thumbs']/div/div/a[2]",
			RuntimeVariables.replace("Thumb Down"));
		selenium.waitForText("//div[@class='taglib-ratings thumbs']/div/div/div",
			"-1 (1 Vote)");
		assertEquals(RuntimeVariables.replace("-1 (1 Vote)"),
			selenium.getText(
				"//div[@class='taglib-ratings thumbs']/div/div/div"));
		assertTrue(selenium.isVisible(
				"//a[@class='rating-element rating-element-off rating-thumb-up']"));
		assertTrue(selenium.isElementNotPresent(
				"//a[@class='rating-element rating-element-off rating-thumb-down']"));
		assertTrue(selenium.isElementNotPresent(
				"//a[@class='rating-element rating-element-off rating-thumb-up rating-element-on']"));
		assertTrue(selenium.isVisible(
				"//a[@class='rating-element rating-element-off rating-thumb-down rating-element-on']"));
		selenium.clickAt("//div[@class='taglib-ratings thumbs']/div/div/a[2]",
			RuntimeVariables.replace("Thumb Down"));
		selenium.waitForText("//div[@class='taglib-ratings thumbs']/div/div/div",
			"0 (0 Votes)");
		assertEquals(RuntimeVariables.replace("0 (0 Votes)"),
			selenium.getText(
				"//div[@class='taglib-ratings thumbs']/div/div/div"));
		assertTrue(selenium.isVisible(
				"//a[@class='rating-element rating-element-off rating-thumb-up']"));
		assertTrue(selenium.isVisible(
				"//a[@class='rating-element rating-element-off rating-thumb-down']"));
		assertTrue(selenium.isElementNotPresent(
				"//a[@class='rating-element rating-element-off rating-thumb-up rating-element-on']"));
		assertTrue(selenium.isElementNotPresent(
				"//a[@class='rating-element rating-element-off rating-thumb-down rating-element-on']"));
	}
}