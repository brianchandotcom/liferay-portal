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

package com.liferay.portalweb.portlet.wikidisplay.comment.addwdfrontpagecommentmultiple;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWDFrontPageCommentMultipleTest extends BaseTestCase {
	public void testViewWDFrontPageCommentMultiple() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Display Test Page",
			RuntimeVariables.replace("Wiki Display Test Page"));
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
		assertEquals(RuntimeVariables.replace("FrontPage"),
			selenium.getText("//h1[@class='header-title']"));
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
				"//fieldset[@class='fieldset add-comment ']/div", "Add Comment"));
		assertEquals(RuntimeVariables.replace("Unsubscribe from Comments"),
			selenium.getText("//span[@class='subscribe-link']/a/span"));
		assertTrue(selenium.isVisible(
				"xpath=(//span[@class='user-profile-image']/img)[1]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("xpath=(//span[@class='user-name'])[1]"));
		assertEquals(RuntimeVariables.replace("Wiki FrontPage Comment1"),
			selenium.getText(
				"xpath=(//div[@class='lfr-discussion-message'])[1]"));
		assertTrue(selenium.isVisible(
				"xpath=(//div[contains(@id,'ratingThumbContent')])[1]"));
		assertTrue(selenium.isVisible(
				"xpath=(//div[@class='lfr-discussion-posted-on'])[1]"));
		assertTrue(selenium.isElementPresent(
				"xpath=(//span[@class='user-profile-image']/img)[3]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("xpath=(//span[@class='user-name'])[3]"));
		assertEquals(RuntimeVariables.replace("Wiki FrontPage Comment2"),
			selenium.getText(
				"xpath=(//div[@class='lfr-discussion-message'])[2]"));
		assertTrue(selenium.isVisible(
				"xpath=(//div[contains(@id,'ratingThumbContent')])[2]"));
		assertTrue(selenium.isVisible(
				"xpath=(//div[@class='lfr-discussion-posted-on'])[2]"));
		assertTrue(selenium.isElementPresent(
				"xpath=(//span[@class='user-profile-image']/img)[5]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("xpath=(//span[@class='user-name'])[5]"));
		assertEquals(RuntimeVariables.replace("Wiki FrontPage Comment3"),
			selenium.getText(
				"xpath=(//div[@class='lfr-discussion-message'])[3]"));
		assertTrue(selenium.isVisible(
				"xpath=(//div[contains(@id,'ratingThumbContent')])[3]"));
		assertTrue(selenium.isVisible(
				"xpath=(//div[@class='lfr-discussion-posted-on'])[3]"));
	}
}