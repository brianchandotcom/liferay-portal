/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.announcements.announcementsentry.addannouncementsentrypriorityimportant;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewAnnouncementsEntryPriorityImportantTest extends BaseTestCase {
	public void testViewAnnouncementsEntryPriorityImportant()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Announcements Test Page",
			RuntimeVariables.replace("Announcements Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Announcements Entry Title Priority Important"),
			selenium.getText(
				"//div[contains(.,'Important')]/h3[@class='entry-title']/a"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("//td[@class='edit-entry']/span/a/span"));
		selenium.clickAt("//td[@class='edit-entry']/span/a/span",
			RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		assertEquals("Announcements Entry Title Priority Important",
			selenium.getValue("//input[@id='_84_title']"));
		selenium.waitForElementPresent(
			"//textarea[@id='_84_editor' and @style='display: none;']");
		selenium.waitForVisible("//td[@id='cke_contents__84_editor']/iframe");
		selenium.selectFrame("//td[@id='cke_contents__84_editor']/iframe");
		selenium.waitForText("//body",
			"Announcements Entry Content Priority Important");
		assertEquals(RuntimeVariables.replace(
				"Announcements Entry Content Priority Important"),
			selenium.getText("//body"));
		selenium.selectFrame("relative=top");
		assertEquals("Important",
			selenium.getSelectedLabel("//select[@id='_84_priority']"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Announcements Test Page",
			RuntimeVariables.replace("Announcements Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Entries", RuntimeVariables.replace("Entries"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("link=Manage Entries"));
		assertEquals(RuntimeVariables.replace(
				"Announcements Entry Title Priority Important"),
			selenium.getText(
				"//div[contains(.,'Important')]/h3[@class='entry-title']/a"));
		assertEquals(RuntimeVariables.replace("General"),
			selenium.getText(
				"//div[contains(.,'Important')]/div/span[@class='entry-scope']"));
		assertEquals(RuntimeVariables.replace(
				"Announcements Entry Content Priority Important"),
			selenium.getText(
				"//div[contains(.,'Important')]/div[contains(@class,'entry-content')]/p"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("//td[@class='edit-entry']/span/a/span"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText("//td[@class='delete-entry']/span/a/span"));
		assertEquals(RuntimeVariables.replace("Mark as Read"),
			selenium.getText("//td[@class='control-entry']/a"));
		assertEquals(RuntimeVariables.replace("General"),
			selenium.getText("//span[@class='entry-scope']"));
		assertEquals(RuntimeVariables.replace(
				"Announcements Entry Content Priority Important"),
			selenium.getText(
				"//div[@class=' entry-content entry-type-general']/p"));
		selenium.clickAt("link=Manage Entries",
			RuntimeVariables.replace("Manage Entries"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[@id='_84_distributionScope']",
			RuntimeVariables.replace("General"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("//input[@value='Add Entry']"));
		assertEquals(RuntimeVariables.replace(
				"Announcements Entry Title Priority Important"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace("General"),
			selenium.getText("//td[2]/a"));
		assertTrue(selenium.isVisible("//td[3]/a"));
		assertTrue(selenium.isVisible("//td[4]/a"));
		assertTrue(selenium.isVisible("//td[5]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//td[6]/span[@title='Actions']/ul/li/strong/a/span"));
		assertEquals(RuntimeVariables.replace("Showing 1 result."),
			selenium.getText("//div[@class='search-results']"));
	}
}