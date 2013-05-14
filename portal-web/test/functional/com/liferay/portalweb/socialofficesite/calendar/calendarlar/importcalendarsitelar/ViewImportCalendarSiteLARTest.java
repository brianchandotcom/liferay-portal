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

package com.liferay.portalweb.socialofficesite.calendar.calendarlar.importcalendarsitelar;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewImportCalendarSiteLARTest extends BaseTestCase {
	public void testViewImportCalendarSiteLAR() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Calendar"),
			selenium.getText("//nav/ul/li[contains(.,'Calendar')]/a/span"));
		selenium.clickAt("//nav/ul/li[contains(.,'Calendar')]/a/span",
			RuntimeVariables.replace("Calendar"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Events", RuntimeVariables.replace("Events"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//td[@id='_8_ocerSearchContainer_col-time_row-1']"));
		assertEquals(RuntimeVariables.replace("Calendar Event1 Title Edit"),
			selenium.getText(
				"//td[@id='_8_ocerSearchContainer_col-title_row-1']"));
		assertEquals(RuntimeVariables.replace("Anniversary"),
			selenium.getText(
				"//td[@id='_8_ocerSearchContainer_col-type_row-1']"));
		selenium.clickAt("//td[@id='_8_ocerSearchContainer_col-title_row-1']/a",
			RuntimeVariables.replace("Calendar Event1 Title Edit"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Calendar Event1 Title Edit"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Start Date:"),
			selenium.getText("//dl[@class='property-list']/dt[1]"));
		assertEquals(RuntimeVariables.replace("End Date:"),
			selenium.getText("//dl[@class='property-list']/dt[2]"));
		assertEquals(RuntimeVariables.replace("Duration:"),
			selenium.getText("//dl[@class='property-list']/dt[3]"));
		assertEquals(RuntimeVariables.replace("Type:"),
			selenium.getText("//dl[@class='property-list']/dt[4]"));
		assertEquals(RuntimeVariables.replace(
				"Calendar Event1 Description Edit"),
			selenium.getText("//p[contains(.,'Calendar Event1 Description')]"));
		assertEquals(RuntimeVariables.replace("Your Rating"),
			selenium.getText("xPath=(//div[@class='rating-label-element'])[1]"));
		assertEquals(RuntimeVariables.replace("Average (1 Vote)"),
			selenium.getText("xPath=(//div[@class='rating-label-element'])[2]"));
		assertTrue(selenium.isElementPresent(
				"//img[@alt='The average rating is 4.0 stars out of 5.']"));
		assertEquals(RuntimeVariables.replace(
				"Add Comment Subscribe to Comments"),
			selenium.getText("//fieldset[@class='fieldset add-comment ']/div"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("xPath=(//span[@class='user-name'])[1]"));
		assertEquals(RuntimeVariables.replace("Calendar Event1 Comment1 Body"),
			selenium.getText(
				"xPath=(//div[@class='lfr-discussion-message'])[1]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("xPath=(//span[@class='user-name'])[3]"));
		assertEquals(RuntimeVariables.replace("Calendar Event1 Comment2 Body"),
			selenium.getText(
				"xPath=(//div[@class='lfr-discussion-message'])[2]"));
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
		assertEquals(RuntimeVariables.replace("Calendar"),
			selenium.getText("//nav/ul/li[contains(.,'Calendar')]/a/span"));
		selenium.clickAt("//nav/ul/li[contains(.,'Calendar')]/a/span",
			RuntimeVariables.replace("Calendar"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Events", RuntimeVariables.replace("Events"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//td[@id='_8_ocerSearchContainer_col-time_row-2']"));
		assertEquals(RuntimeVariables.replace("Calendar Event2 Title"),
			selenium.getText(
				"//td[@id='_8_ocerSearchContainer_col-title_row-2']"));
		assertEquals(RuntimeVariables.replace("Anniversary"),
			selenium.getText(
				"//td[@id='_8_ocerSearchContainer_col-type_row-2']"));
		selenium.clickAt("//td[@id='_8_ocerSearchContainer_col-title_row-2']/a",
			RuntimeVariables.replace("Calendar Event2 Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Calendar Event2 Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Start Date:"),
			selenium.getText("//dl[@class='property-list']/dt[1]"));
		assertEquals(RuntimeVariables.replace("End Date:"),
			selenium.getText("//dl[@class='property-list']/dt[2]"));
		assertEquals(RuntimeVariables.replace("Duration:"),
			selenium.getText("//dl[@class='property-list']/dt[3]"));
		assertEquals(RuntimeVariables.replace("Type:"),
			selenium.getText("//dl[@class='property-list']/dt[4]"));
		assertEquals(RuntimeVariables.replace("Calendar Event2 Description"),
			selenium.getText("//p[contains(.,'Calendar Event2 Description')]"));
		assertEquals(RuntimeVariables.replace("tag1"),
			selenium.getText("xPath=(//span[@class='tag'])[1]"));
		assertEquals(RuntimeVariables.replace("tag2"),
			selenium.getText("xPath=(//span[@class='tag'])[2]"));
		assertEquals(RuntimeVariables.replace("Your Rating"),
			selenium.getText("xPath=(//div[@class='rating-label-element'])[1]"));
		assertEquals(RuntimeVariables.replace("Average (0 Votes)"),
			selenium.getText("xPath=(//div[@class='rating-label-element'])[2]"));
		assertEquals(RuntimeVariables.replace(
				"No comments yet. Be the first. Subscribe to Comments"),
			selenium.getText("//fieldset[@class='fieldset add-comment ']/div"));
		assertEquals(RuntimeVariables.replace("Be the first."),
			selenium.getText("//fieldset[@class='fieldset add-comment ']/div/a"));
		assertEquals(RuntimeVariables.replace("Subscribe to Comments"),
			selenium.getText("//span[@class='subscribe-link']/a/span"));
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
		assertEquals(RuntimeVariables.replace("Calendar"),
			selenium.getText("//nav/ul/li[contains(.,'Calendar')]/a/span"));
		selenium.clickAt("//nav/ul/li[contains(.,'Calendar')]/a/span",
			RuntimeVariables.replace("Calendar"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Events", RuntimeVariables.replace("Events"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//td[@id='_8_ocerSearchContainer_col-time_row-2']"));
		assertEquals(RuntimeVariables.replace("Calendar Event2 Title"),
			selenium.getText(
				"//td[@id='_8_ocerSearchContainer_col-title_row-2']"));
		assertEquals(RuntimeVariables.replace("Anniversary"),
			selenium.getText(
				"//td[@id='_8_ocerSearchContainer_col-type_row-2']"));
		Thread.sleep(1000);
		selenium.waitForVisible(
			"xPath=(//td[contains(.,'Actions')]/span/ul/li/strong/a)[2]");
		selenium.clickAt("xPath=(//td[contains(.,'Actions')]/span/ul/li/strong/a)[2]",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Permissions')]");
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Permissions')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Permissions')]",
			RuntimeVariables.replace("Permissions"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//input[@id='guest_ACTION_VIEW']");
		assertFalse(selenium.isChecked("//input[@id='guest_ACTION_VIEW']"));
	}
}