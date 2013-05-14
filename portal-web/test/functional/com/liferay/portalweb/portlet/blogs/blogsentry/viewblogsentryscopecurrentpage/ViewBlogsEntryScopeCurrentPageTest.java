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

package com.liferay.portalweb.portlet.blogs.blogsentry.viewblogsentryscopecurrentpage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewBlogsEntryScopeCurrentPageTest extends BaseTestCase {
	public void testViewBlogsEntryScopeCurrentPage() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Blogs Test Page",
			RuntimeVariables.replace("Blogs Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs (Blogs Test Page)"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertTrue(selenium.isVisible("//input[@title='Search Entries']"));
		assertTrue(selenium.isVisible("//input[@value='Search']"));
		assertTrue(selenium.isVisible("//input[@value='Add Blog Entry']"));
		assertTrue(selenium.isVisible("//input[@value='Permissions']"));
		assertEquals(RuntimeVariables.replace("Blogs Entry Title"),
			selenium.getText("//div[@class='entry-title']"));
		assertTrue(selenium.isVisible("//div[@class='entry-date']"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-meta-actions edit-actions entry']/table/tbody/tr/td[1]"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"//div[@class='lfr-meta-actions edit-actions entry']/table/tbody/tr/td[2]"));
		assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
			selenium.getText(
				"//div[@class='lfr-meta-actions edit-actions entry']/table/tbody/tr/td[3]"));
		assertEquals(RuntimeVariables.replace("Blogs Entry Content"),
			selenium.getText("//div[@class='entry-body']"));
		assertEquals(RuntimeVariables.replace("By Joe Bloggs"),
			selenium.getText("//div[@class='entry-footer']/div[1]"));
		assertEquals(RuntimeVariables.replace("0 Comments"),
			selenium.getText("//div[@class='entry-footer']/div[2]"));
		assertEquals(RuntimeVariables.replace("Flag"),
			selenium.getText("//div[@class='entry-footer']/div[3]"));
		assertTrue(selenium.isVisible("//div[@class='entry-footer']/div[5]"));
		assertTrue(selenium.isVisible(
				"//div[@class='entry-footer']/div[6]/div[1]"));
		assertEquals(RuntimeVariables.replace("Your Rating"),
			selenium.getText("xPath=(//div[@class='rating-label-element'])[1]"));
		assertTrue(selenium.isVisible(
				"//div[@class='entry-footer']/div[6]/div[2]"));
		assertEquals(RuntimeVariables.replace("Average (0 Votes)"),
			selenium.getText("xPath=(//div[@class='rating-label-element'])[2]"));
		assertEquals(RuntimeVariables.replace("RSS (Opens New Window)"),
			selenium.getText("//div[@class='subscribe']/span[1]"));
		assertEquals(RuntimeVariables.replace("Subscribe"),
			selenium.getText("//div[@class='subscribe']/span[2]"));
		assertEquals(RuntimeVariables.replace("Showing 1 result."),
			selenium.getText("//div[@class='taglib-page-iterator']"));
	}
}