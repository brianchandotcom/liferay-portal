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

package com.liferay.portalweb.socialofficesite.blogs.blogsentry.addblogsentrytagssite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewBlogsEntryTagsSiteTest extends BaseTestCase {
	public void testViewBlogsEntryTagsSite() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Blogs Entry Title"),
			selenium.getText("//div[@class='entry-title']/h2/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry Content"),
			selenium.getText("//div[@class='entry-body']/p"));
		assertEquals(RuntimeVariables.replace("tag1"),
			selenium.getText("//span[@class='taglib-asset-tags-summary']/a[1]"));
		assertEquals(RuntimeVariables.replace("tag2"),
			selenium.getText("//span[@class='taglib-asset-tags-summary']/a[2]"));
		selenium.clickAt("//div[@class='entry-title']/h2/a",
			RuntimeVariables.replace("Blogs Entry Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs Entry Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Blogs Entry Content"),
			selenium.getText("//div[@class='entry-body']"));
		assertEquals(RuntimeVariables.replace("By Joe Bloggs"),
			selenium.getText("//div[@class='entry-author']"));
		assertEquals(RuntimeVariables.replace("0 Comments"),
			selenium.getText("//span[@class='comments']"));
		assertEquals(RuntimeVariables.replace("tag1"),
			selenium.getText("//span[@class='taglib-asset-tags-summary']/a[1]"));
		assertEquals(RuntimeVariables.replace("tag2"),
			selenium.getText("//span[@class='taglib-asset-tags-summary']/a[2]"));
		assertEquals(RuntimeVariables.replace("Your Rating"),
			selenium.getText("xPath=(//div[@class='rating-label-element'])[1]"));
		assertEquals(RuntimeVariables.replace("Average (0 Votes)"),
			selenium.getText("xPath=(//div[@class='rating-label-element'])[2]"));
		assertEquals(RuntimeVariables.replace("Previous"),
			selenium.getText("//span[@class='previous']"));
		assertEquals(RuntimeVariables.replace("Next"),
			selenium.getText("//span[@class='next']"));
		assertEquals(RuntimeVariables.replace("Comments"),
			selenium.getText("//div[@class='lfr-panel-title']/span"));
	}
}