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

package com.liferay.portalweb.portal.controlpanel.recyclebin.blogs.restoreblogentriesrecyclebin;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewRestoreBlogEntriesRecycleBinTest extends BaseTestCase {
	public void testViewRestoreBlogEntriesRecycleBin()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Blogs Test Page",
			RuntimeVariables.replace("Blogs Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("//input[@value='Search']"));
		assertTrue(selenium.isVisible("//input[@value='Add Blog Entry']"));
		assertTrue(selenium.isVisible("//input[@value='Permissions']"));
		assertEquals(RuntimeVariables.replace("Blogs Entry3 Title"),
			selenium.getText("xpath=(//div[@class='entry-title']/h2/a)[1]"));
		assertTrue(selenium.isVisible("xpath=(//div[@class='entry-date'])[1]"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("xpath=(//td[contains(.,'Edit')]/span/a/span)[1]"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"xpath=(//td[contains(.,'Permissions')]/span/a/span)[1]"));
		assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
			selenium.getText(
				"xpath=(//td[contains(.,'Move to the Recycle Bin')]/span/a/span)[1]"));
		assertEquals(RuntimeVariables.replace("Blogs Entry3 Content"),
			selenium.getText("xpath=(//div[@class='entry-body']/div)[1]"));
		assertEquals(RuntimeVariables.replace("By Joe Bloggs"),
			selenium.getText("xpath=(//div[@class='entry-author'])[1]"));
		assertEquals(RuntimeVariables.replace("0 Comments"),
			selenium.getText("xpath=(//span[@class='comments']/a)[1]"));
		assertEquals(RuntimeVariables.replace("Flag"),
			selenium.getText(
				"xpath=(//div[@class='taglib-flags']/span/a/span)[1]"));
		assertTrue(selenium.isVisible(
				"xpath=(//li[@class='taglib-social-bookmark-twitter'])[1]"));
		assertTrue(selenium.isVisible(
				"xpath=(//li[@class='taglib-social-bookmark-facebook'])[1]"));
		assertTrue(selenium.isVisible(
				"xpath=(//li[@class='taglib-social-bookmark-plusone'])[1]"));
		assertTrue(selenium.isPartialText(
				"xpath=(//div[contains(@id,'ratingStar')]/div)[1]",
				"Your Rating"));
		assertEquals(RuntimeVariables.replace("Average (0 Votes)"),
			selenium.getText(
				"xpath=(//div[contains(@id,'ratingScore')]/div	)[1]"));
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Title"),
			selenium.getText("xpath=(//div[@class='entry-title']/h2/a)[2]"));
		assertTrue(selenium.isVisible("xpath=(//div[@class='entry-date'])[2]"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("xpath=(//td[contains(.,'Edit')]/span/a/span)[2]"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"xpath=(//td[contains(.,'Permissions')]/span/a/span)[2]"));
		assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
			selenium.getText(
				"xpath=(//td[contains(.,'Move to the Recycle Bin')]/span/a/span)[2]"));
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Content"),
			selenium.getText("xpath=(//div[@class='entry-body']/div)[3]"));
		assertEquals(RuntimeVariables.replace("By Joe Bloggs"),
			selenium.getText("xpath=(//div[@class='entry-author'])[2]"));
		assertEquals(RuntimeVariables.replace("0 Comments"),
			selenium.getText("xpath=(//span[@class='comments']/a)[2]"));
		assertEquals(RuntimeVariables.replace("Flag"),
			selenium.getText(
				"xpath=(//div[@class='taglib-flags']/span/a/span)[2]"));
		assertTrue(selenium.isVisible(
				"xpath=(//li[@class='taglib-social-bookmark-twitter'])[2]"));
		assertTrue(selenium.isVisible(
				"xpath=(//li[@class='taglib-social-bookmark-facebook'])[2]"));
		assertTrue(selenium.isVisible(
				"xpath=(//li[@class='taglib-social-bookmark-plusone'])[2]"));
		assertTrue(selenium.isPartialText(
				"xpath=(//div[contains(@id,'ratingStar')]/div)[2]",
				"Your Rating"));
		assertEquals(RuntimeVariables.replace("Average (0 Votes)"),
			selenium.getText(
				"xpath=(//div[contains(@id,'ratingScore')]/div	)[2]"));
		assertEquals(RuntimeVariables.replace("Blogs Entry1 Title"),
			selenium.getText("xpath=(//div[@class='entry-title']/h2/a)[3]"));
		assertTrue(selenium.isVisible("xpath=(//div[@class='entry-date'])[3]"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("xpath=(//td[contains(.,'Edit')]/span/a/span)[3]"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"xpath=(//td[contains(.,'Permissions')]/span/a/span)[3]"));
		assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
			selenium.getText(
				"xpath=(//td[contains(.,'Move to the Recycle Bin')]/span/a/span)[3]"));
		assertEquals(RuntimeVariables.replace("Blogs Entry1 Content"),
			selenium.getText("xpath=(//div[@class='entry-body']/div)[5]"));
		assertEquals(RuntimeVariables.replace("By Joe Bloggs"),
			selenium.getText("xpath=(//div[@class='entry-author'])[3]"));
		assertEquals(RuntimeVariables.replace("0 Comments"),
			selenium.getText("xpath=(//span[@class='comments']/a)[3]"));
		assertEquals(RuntimeVariables.replace("Flag"),
			selenium.getText(
				"xpath=(//div[@class='taglib-flags']/span/a/span)[3]"));
		assertTrue(selenium.isVisible(
				"xpath=(//li[@class='taglib-social-bookmark-twitter'])[3]"));
		assertTrue(selenium.isVisible(
				"xpath=(//li[@class='taglib-social-bookmark-facebook'])[3]"));
		assertTrue(selenium.isVisible(
				"xpath=(//li[@class='taglib-social-bookmark-plusone'])[3]"));
		assertTrue(selenium.isPartialText(
				"xpath=(//div[contains(@id,'ratingStar')]/div)[3]",
				"Your Rating"));
		assertEquals(RuntimeVariables.replace("Average (0 Votes)"),
			selenium.getText(
				"xpath=(//div[contains(@id,'ratingScore')]/div	)[3]"));
		assertTrue(selenium.isPartialText(
				"//div[@class='subscribe']/span/a[contains(@href,'rss')]	",
				"RSS"));
		assertTrue(selenium.isPartialText(
				"//div[@class='subscribe']/span/a[contains(@href,'subscribe')]	",
				"Subscribe"));
		assertEquals(RuntimeVariables.replace("Showing 3 results."),
			selenium.getText("//div[@class='search-results']	"));
	}
}