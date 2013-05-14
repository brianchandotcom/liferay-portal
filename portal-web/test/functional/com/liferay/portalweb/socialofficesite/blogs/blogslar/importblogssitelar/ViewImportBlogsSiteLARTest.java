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

package com.liferay.portalweb.socialofficesite.blogs.blogslar.importblogssitelar;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewImportBlogsSiteLARTest extends BaseTestCase {
	public void testViewImportBlogsSiteLAR() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/so/dashboard/");
		assertEquals(RuntimeVariables.replace("Sites"),
			selenium.getText("//div[@id='so-sidebar']/h3"));
		assertTrue(selenium.isVisible("//input[@class='search-input']"));
		selenium.type("//input[@class='search-input']",
			RuntimeVariables.replace("Open"));
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Open Site Name"),
			selenium.getText(
				"//li[contains(@class, 'social-office-enabled')]/span[2]/a"));
		selenium.clickAt("//li[contains(@class, 'social-office-enabled')]/span[2]/a",
			RuntimeVariables.replace("Open Site Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs"),
			selenium.getText("//nav/ul/li[contains(.,'Blogs')]/a/span"));
		selenium.clickAt("//nav/ul/li[contains(.,'Blogs')]/a/span",
			RuntimeVariables.replace("Blogs"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("//input[@id='_33_keywords']"));
		assertTrue(selenium.isVisible("//input[@value='Search']"));
		assertTrue(selenium.isVisible("//input[@value='Add Blog Entry']"));
		assertTrue(selenium.isVisible("//input[@value='Permissions']"));
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Title Edit"),
			selenium.getText("xPath=(//div[@class='entry-title']/h2/a)[1]"));
		assertTrue(selenium.isVisible("xPath=(//div[@class='entry-date'])[1]"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("xPath=(//td[contains(.,'Edit')]/span/a/span)[1]"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"xPath=(//td[contains(.,'Permissions')]/span/a/span)[1]"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText(
				"xPath=(//td[contains(.,'Delete')]/span/a/span)[1]"));
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Content Edit"),
			selenium.getText("xPath=(//div[@class='entry-body']/p)[1]"));
		assertEquals(RuntimeVariables.replace("By Joe Bloggs"),
			selenium.getText("xPath=(//div[@class='entry-author'])[1]"));
		assertEquals(RuntimeVariables.replace("0 Comments"),
			selenium.getText("xPath=(//span[@class='comments']/a)[1]"));
		assertEquals(RuntimeVariables.replace("Flag"),
			selenium.getText(
				"xPath=(//div[@class='taglib-flags']/span/a/span)[1]"));
		assertEquals(RuntimeVariables.replace("tag1"),
			selenium.getText("//span[@class='taglib-asset-tags-summary']/a[1]"));
		assertEquals(RuntimeVariables.replace("tag2"),
			selenium.getText("//span[@class='taglib-asset-tags-summary']/a[2]"));
		assertEquals(RuntimeVariables.replace("Vocabulary Name: Category Name"),
			selenium.getText("//span[@class='taglib-asset-categories-summary']"));
		assertTrue(selenium.isVisible(
				"xPath=(//li[@class='taglib-social-bookmark-twitter'])[1]"));
		assertTrue(selenium.isVisible(
				"xPath=(//li[@class='taglib-social-bookmark-facebook'])[1]"));
		assertTrue(selenium.isVisible(
				"xPath=(//li[@class='taglib-social-bookmark-plusone'])[1]"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[contains(@id,'ratingStar')]/div)[1]",
				"Your Rating"));
		assertEquals(RuntimeVariables.replace("Average (0 Votes)"),
			selenium.getText(
				"xPath=(//div[contains(@id,'ratingScore')]/div)[1]"));
		assertEquals(RuntimeVariables.replace("Blogs Entry1 Title"),
			selenium.getText("xPath=(//div[@class='entry-title']/h2/a)[2]"));
		assertTrue(selenium.isVisible("xPath=(//div[@class='entry-date'])[2]"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("xPath=(//td[contains(.,'Edit')]/span/a/span)[2]"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"xPath=(//td[contains(.,'Permissions')]/span/a/span)[2]"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText(
				"xPath=(//td[contains(.,'Delete')]/span/a/span)[2]"));
		assertEquals(RuntimeVariables.replace("Blogs Entry1 Content"),
			selenium.getText("xPath=(//div[@class='entry-body']/p)[2]"));
		assertEquals(RuntimeVariables.replace("By Joe Bloggs"),
			selenium.getText("xPath=(//div[@class='entry-author'])[2]"));
		assertEquals(RuntimeVariables.replace("1 Comment"),
			selenium.getText("xPath=(//span[@class='comments']/a)[2]"));
		assertEquals(RuntimeVariables.replace("Flag"),
			selenium.getText(
				"xPath=(//div[@class='taglib-flags']/span/a/span)[2]"));
		assertTrue(selenium.isVisible(
				"xPath=(//li[@class='taglib-social-bookmark-twitter'])[2]"));
		assertTrue(selenium.isVisible(
				"xPath=(//li[@class='taglib-social-bookmark-facebook'])[2]"));
		assertTrue(selenium.isVisible(
				"xPath=(//li[@class='taglib-social-bookmark-plusone'])[2]"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[contains(@id,'ratingStar')]/div)[3]",
				"Your Rating"));
		assertEquals(RuntimeVariables.replace("Average (1 Vote)"),
			selenium.getText(
				"xPath=(//div[contains(@id,'ratingScore')]/div)[3]"));
		assertTrue(selenium.isElementPresent(
				"//img[@alt='The average rating is 4.0 stars out of 5.']"));
		assertTrue(selenium.isPartialText(
				"//div[@class='subscribe']/span/a[contains(@href,'rss')]", "RSS"));
		assertTrue(selenium.isPartialText(
				"//div[@class='subscribe']/span/a[contains(@href,'subscribe')]",
				"Subscribe"));
		assertEquals(RuntimeVariables.replace("Showing 2 results."),
			selenium.getText("//div[@class='search-results']"));
		selenium.clickAt("xPath=(//div[@class='entry-title']/h2/a)[1]",
			RuntimeVariables.replace("Blogs Entry2 Title Edit"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Title Edit"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Content Edit"),
			selenium.getText("//div[@class='entry-body']"));
		assertEquals(RuntimeVariables.replace("By Joe Bloggs"),
			selenium.getText("//div[@class='entry-author']"));
		assertEquals(RuntimeVariables.replace("0 Comments"),
			selenium.getText("//span[@class='comments']"));
		assertEquals(RuntimeVariables.replace("tag1"),
			selenium.getText("//span[@class='taglib-asset-tags-summary']/a[1]"));
		assertEquals(RuntimeVariables.replace("tag2"),
			selenium.getText("//span[@class='taglib-asset-tags-summary']/a[2]"));
		assertEquals(RuntimeVariables.replace("Vocabulary Name: Category Name"),
			selenium.getText("//span[@class='taglib-asset-categories-summary']"));
		assertEquals(RuntimeVariables.replace("Your Rating"),
			selenium.getText("xPath=(//div[@class='rating-label-element'])[1]"));
		assertEquals(RuntimeVariables.replace("Average (0 Votes)"),
			selenium.getText("xPath=(//div[@class='rating-label-element'])[2]"));
		assertEquals(RuntimeVariables.replace("Comments"),
			selenium.getText("//div[@class='lfr-panel-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"No comments yet. Be the first. Subscribe to Comments"),
			selenium.getText("//fieldset[contains(@class,'add-comment')]"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText("//td[2]/span/a/span"));
		selenium.click(RuntimeVariables.replace("//td[2]/span/a/span"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//input[@id='guest_ACTION_VIEW']");
		assertFalse(selenium.isChecked("//input[@id='guest_ACTION_VIEW']"));
		selenium.open("/user/joebloggs/so/dashboard/");
		assertEquals(RuntimeVariables.replace("Sites"),
			selenium.getText("//div[@id='so-sidebar']/h3"));
		assertTrue(selenium.isVisible("//input[@class='search-input']"));
		selenium.type("//input[@class='search-input']",
			RuntimeVariables.replace("Open"));
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Open Site Name"),
			selenium.getText(
				"//li[contains(@class, 'social-office-enabled')]/span[2]/a"));
		selenium.clickAt("//li[contains(@class, 'social-office-enabled')]/span[2]/a",
			RuntimeVariables.replace("Open Site Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs"),
			selenium.getText("//nav/ul/li[contains(.,'Blogs')]/a/span"));
		selenium.clickAt("//nav/ul/li[contains(.,'Blogs')]/a/span",
			RuntimeVariables.replace("Blogs"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs Entry1 Title"),
			selenium.getText("xPath=(//div[@class='entry-title']/h2/a)[2]"));
		selenium.clickAt("xPath=(//div[@class='entry-title']/h2/a)[2]",
			RuntimeVariables.replace("Blogs Entry1 Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs Entry1 Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Blogs Entry1 Content"),
			selenium.getText("//div[@class='entry-body']"));
		assertEquals(RuntimeVariables.replace("By Joe Bloggs"),
			selenium.getText("//div[@class='entry-author']"));
		assertEquals(RuntimeVariables.replace("1 Comment"),
			selenium.getText("//span[@class='comments']"));
		assertEquals(RuntimeVariables.replace("Your Rating"),
			selenium.getText("xPath=(//div[@class='rating-label-element'])[1]"));
		selenium.mouseOver("//div[contains(@id,'ratingStarContent')]");
		assertEquals(RuntimeVariables.replace("Average (1 Vote)"),
			selenium.getText("xPath=(//div[@class='rating-label-element'])[2]"));
		assertTrue(selenium.isElementPresent(
				"//img[@alt='The average rating is 4.0 stars out of 5.']"));
		assertEquals(RuntimeVariables.replace("Comments"),
			selenium.getText("//div[@class='lfr-panel-title']/span"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//span[@class='user-name']"));
		assertEquals(RuntimeVariables.replace("Blogs Entry1 Comment Body"),
			selenium.getText("//div[@class='lfr-discussion-message']"));
	}
}