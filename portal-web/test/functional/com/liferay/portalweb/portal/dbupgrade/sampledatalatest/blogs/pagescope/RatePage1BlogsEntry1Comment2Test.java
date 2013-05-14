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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.blogs.pagescope;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RatePage1BlogsEntry1Comment2Test extends BaseTestCase {
	public void testRatePage1BlogsEntry1Comment2() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/blogs-page-scope-community/");
		selenium.waitForVisible("link=Blogs Test Page1");
		selenium.clickAt("link=Blogs Test Page1",
			RuntimeVariables.replace("Blogs Test Page1"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//span[@class='portlet-title-text']", "Blogs");
		assertEquals(RuntimeVariables.replace("Blogs"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertEquals(RuntimeVariables.replace("Blogs Entry1 Title"),
			selenium.getText("//div[@class='entry-title']/h2/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry1 Content"),
			selenium.getText("//div[@class='entry-body']/p"));
		selenium.waitForText("//span[@class='comments']/a", "2 Comments");
		assertEquals(RuntimeVariables.replace("2 Comments"),
			selenium.getText("//span[@class='comments']/a"));
		selenium.clickAt("//span[@class='comments']/a",
			RuntimeVariables.replace("2 Comments"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible(
			"xPath=(//div[@class='lfr-discussion-message'])[2]");
		assertEquals(RuntimeVariables.replace("Blogs Entry1 Comment2 Body"),
			selenium.getText(
				"xPath=(//div[@class='lfr-discussion-message'])[2]"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='rating-label-element'])[4]", "0 Votes"));
		assertTrue(selenium.isVisible(
				"xPath=(//a[contains(@class,'aui-rating-thumb-up')])[2]"));
		assertTrue(selenium.isVisible(
				"xPath=(//a[contains(@class,'aui-rating-thumb-down')])[2]"));
		selenium.clickAt("xPath=(//a[contains(@class,'aui-rating-thumb-down')])[2]",
			RuntimeVariables.replace("Thumbs Down"));
		selenium.waitForText("xPath=(//div[@class='rating-label-element'])[4]",
			"-1 (1 Vote)");
		assertEquals(RuntimeVariables.replace("-1 (1 Vote)"),
			selenium.getText("xPath=(//div[@class='rating-label-element'])[4]"));
	}
}