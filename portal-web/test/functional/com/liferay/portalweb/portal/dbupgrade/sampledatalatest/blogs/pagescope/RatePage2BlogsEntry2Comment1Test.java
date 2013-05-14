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
public class RatePage2BlogsEntry2Comment1Test extends BaseTestCase {
	public void testRatePage2BlogsEntry2Comment1() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/blogs-page-scope-community/");
		selenium.waitForVisible("link=Blogs Test Page2");
		selenium.clickAt("link=Blogs Test Page2",
			RuntimeVariables.replace("Blogs Test Page2"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("//span[@class='portlet-title-text']",
			"Blogs (Blogs Test Page2)");
		assertEquals(RuntimeVariables.replace("Blogs (Blogs Test Page2)"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Title"),
			selenium.getText("//div[@class='entry-title']/h2/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Content"),
			selenium.getText("//div[@class='entry-body']/p"));
		selenium.waitForText("//span[@class='comments']/a", "2 Comments");
		assertEquals(RuntimeVariables.replace("2 Comments"),
			selenium.getText("//span[@class='comments']/a"));
		selenium.clickAt("//span[@class='comments']/a",
			RuntimeVariables.replace("2 Comments"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible(
			"xPath=(//div[@class='lfr-discussion-message'])[1]");
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Comment1 Body"),
			selenium.getText(
				"xPath=(//div[@class='lfr-discussion-message'])[1]"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='rating-label-element'])[3]", "0 Votes"));
		assertTrue(selenium.isVisible(
				"//a[contains(@class,'aui-rating-thumb-up')]"));
		assertTrue(selenium.isVisible(
				"//a[contains(@class,'aui-rating-thumb-down')]"));
		selenium.clickAt("//a[contains(@class,'aui-rating-thumb-up')]",
			RuntimeVariables.replace("Thumbs Up"));
		selenium.waitForText("xPath=(//div[@class='rating-label-element'])[3]",
			"+1 (1 Vote)");
		assertEquals(RuntimeVariables.replace("+1 (1 Vote)"),
			selenium.getText("xPath=(//div[@class='rating-label-element'])[3]"));
	}
}