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

package com.liferay.portalweb.portlet.wikidisplay.wikipage.copywdfrontpage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewCopyWDFrontPageTest extends BaseTestCase {
	public void testViewCopyWDFrontPage() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Display Test Page",
			RuntimeVariables.replace("Wiki Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("All Pages"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span[contains(.,'All Pages')]"));
		selenium.clickAt("//ul[@class='top-links-navigation']/li/span[contains(.,'All Pages')]/a",
			RuntimeVariables.replace("All Pages"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("FrontPage2"),
			selenium.getText("//tr[contains(.,'FrontPage2')]/td[1]/a"));
		selenium.clickAt("//tr[contains(.,'FrontPage2')]/td[1]/a",
			RuntimeVariables.replace("FrontPage2"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("FrontPage"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span[contains(.,'FrontPage')]"));
		assertEquals(RuntimeVariables.replace("Recent Changes"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span[contains(.,'Recent Changes')]"));
		assertEquals(RuntimeVariables.replace("All Pages"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span[contains(.,'All Pages')]"));
		assertEquals(RuntimeVariables.replace("Orphan Pages"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span[contains(.,'Orphan Pages')]"));
		assertEquals(RuntimeVariables.replace("Draft Pages"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span[contains(.,'Draft Pages')]"));
		assertTrue(selenium.isVisible(
				"//span[@class='search-bar']/span/span/span/input"));
		assertTrue(selenium.isVisible("//input[@title='Search Pages']"));
		assertEquals(RuntimeVariables.replace("FrontPage2"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='page-actions top-actions']/span/a[contains(.,'Edit')]"));
		assertEquals(RuntimeVariables.replace("Details"),
			selenium.getText(
				"//div[@class='page-actions top-actions']/span/a[contains(.,'Details')]"));
		assertEquals(RuntimeVariables.replace("Print"),
			selenium.getText(
				"//div[@class='page-actions top-actions']/span/a[contains(.,'Print')]"));
		assertEquals(RuntimeVariables.replace("Wiki FrontPage Content"),
			selenium.getText("//div[@class='wiki-body']/p"));
		assertEquals(RuntimeVariables.replace("Add Child Page"),
			selenium.getText(
				"//div[@class='article-actions']/span/a[contains(.,'Add Child Page')]"));
		assertEquals(RuntimeVariables.replace("0 Attachments"),
			selenium.getText(
				"//div[@class='article-actions']/span/a[contains(.,'0 Attachments')]"));
		assertTrue(selenium.isVisible(
				"//div[@class='page-actions']/div[@class='stats']"));
		assertTrue(selenium.isPartialText(
				"//div[contains(@id,'ratingStarContent')]", "Your Rating"));
		assertEquals(RuntimeVariables.replace("Average (0 Votes)"),
			selenium.getText("//div[contains(@id,'ratingScoreContent')]"));
		assertEquals(RuntimeVariables.replace("Comments"),
			selenium.getText("//div[@id='wikiCommentsPanel']/div/div/span"));
		assertTrue(selenium.isPartialText(
				"//fieldset[@class='fieldset add-comment ']/div",
				"No comments yet."));
		assertEquals(RuntimeVariables.replace("Be the first."),
			selenium.getText("//fieldset[@class='fieldset add-comment ']/div/a"));
		assertEquals(RuntimeVariables.replace("Subscribe to Comments"),
			selenium.getText("//span[@class='subscribe-link']/a/span"));
	}
}