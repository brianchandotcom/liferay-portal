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

package com.liferay.portalweb.socialofficesite.home.bookmarks.addbookmarksentrymultiplesite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewBookmarksEntryMultipleSiteTest extends BaseTestCase {
	public void testViewBookmarksEntryMultipleSite() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Bookmarks Entry1 Name"),
			selenium.getText(
				"//td[@id='_28_bookmarksEntriesSearchContainer_col-name_row-1']/a"));
		selenium.clickAt("//td[@id='_28_bookmarksEntriesSearchContainer_col-name_row-1']/a",
			RuntimeVariables.replace("Bookmarks Entry1 Name"));
		Thread.sleep(5000);
		selenium.selectWindow("title=Google");
		selenium.waitForVisible("//img[@alt='Google']");
		assertTrue(selenium.isVisible("//img[@alt='Google']"));
		selenium.close();
		selenium.selectWindow("null");
		Thread.sleep(1000);
		selenium.waitForVisible(
			"xPath=(//td[contains(.,'Actions')]/span/ul/li/strong/a)[1]");
		selenium.clickAt("xPath=(//td[contains(.,'Actions')]/span/ul/li/strong/a)[1]",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[1]/a",
			RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		assertEquals("Bookmarks Entry1 Name",
			selenium.getValue("//input[@id='_28_name']"));
		assertEquals("http://www.google.com",
			selenium.getValue("//input[@id='_28_url']"));
		assertEquals("Bookmarks Entry1 Description",
			selenium.getValue("//textarea[@id='_28_description']"));
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
		assertEquals(RuntimeVariables.replace("Bookmarks Entry2 Name"),
			selenium.getText(
				"//td[@id='_28_bookmarksEntriesSearchContainer_col-name_row-2']/a"));
		selenium.clickAt("//td[@id='_28_bookmarksEntriesSearchContainer_col-name_row-2']/a",
			RuntimeVariables.replace("Bookmarks Entry2 Name"));
		Thread.sleep(5000);
		selenium.selectWindow("title=Yahoo!");
		selenium.waitForVisible("//a[contains(.,'Yahoo')]");
		assertTrue(selenium.isVisible("//a[contains(.,'Yahoo')]"));
		selenium.close();
		selenium.selectWindow("null");
		Thread.sleep(1000);
		selenium.waitForVisible(
			"xPath=(//td[contains(.,'Actions')]/span/ul/li/strong/a)[2]");
		selenium.clickAt("xPath=(//td[contains(.,'Actions')]/span/ul/li/strong/a)[2]",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[1]/a",
			RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		assertEquals("Bookmarks Entry2 Name",
			selenium.getValue("//input[@id='_28_name']"));
		assertEquals("http://www.yahoo.com",
			selenium.getValue("//input[@id='_28_url']"));
		assertEquals("Bookmarks Entry2 Description",
			selenium.getValue("//textarea[@id='_28_description']"));
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
		assertEquals(RuntimeVariables.replace("Bookmarks Entry3 Name"),
			selenium.getText(
				"//td[@id='_28_bookmarksEntriesSearchContainer_col-name_row-3']/a"));
		selenium.clickAt("//td[@id='_28_bookmarksEntriesSearchContainer_col-name_row-3']/a",
			RuntimeVariables.replace("Bookmarks Entry3 Name"));
		Thread.sleep(5000);
		selenium.selectWindow("title=MSN.com");
		selenium.waitForVisible("//img[@alt='MSN Logo']");
		assertTrue(selenium.isVisible("//img[@alt='MSN Logo']"));
		selenium.close();
		selenium.selectWindow("null");
		Thread.sleep(1000);
		selenium.waitForVisible(
			"xPath=(//td[contains(.,'Actions')]/span/ul/li/strong/a)[3]");
		selenium.clickAt("xPath=(//td[contains(.,'Actions')]/span/ul/li/strong/a)[3]",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[1]/a",
			RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		assertEquals("Bookmarks Entry3 Name",
			selenium.getValue("//input[@id='_28_name']"));
		assertEquals("http://www.msn.com",
			selenium.getValue("//input[@id='_28_url']"));
		assertEquals("Bookmarks Entry3 Description",
			selenium.getValue("//textarea[@id='_28_description']"));
	}
}