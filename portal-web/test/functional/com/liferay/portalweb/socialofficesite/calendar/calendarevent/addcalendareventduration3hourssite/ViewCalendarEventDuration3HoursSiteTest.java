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

package com.liferay.portalweb.socialofficesite.calendar.calendarevent.addcalendareventduration3hourssite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewCalendarEventDuration3HoursSiteTest extends BaseTestCase {
	public void testViewCalendarEventDuration3HoursSite()
		throws Exception {
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
		selenium.clickAt("link=Day", RuntimeVariables.replace("Day"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("12:00 PM - 3:00 PM"),
			selenium.getText("//div[@class='event-time']"));
		assertEquals(RuntimeVariables.replace("Calendar Event Title"),
			selenium.getText("//div[@class='event-title']"));
		assertEquals(RuntimeVariables.replace("Calendar Event Description"),
			selenium.getText("//div[@class='event-description']"));
		selenium.clickAt("link=Week", RuntimeVariables.replace("Week"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText(
				"//table[2]/tbody/tr/td[contains(.,'12')]", "12:00 PM"));
		assertEquals(RuntimeVariables.replace("Calendar Event Title"),
			selenium.getText("//a[contains(.,'Calendar Event Title')]"));
		selenium.clickAt("link=Month", RuntimeVariables.replace("Month"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//tr[2]/td[contains(.,'12')]",
				"12:00 PM"));
		assertEquals(RuntimeVariables.replace("Calendar Event Title"),
			selenium.getText("//a[contains(.,'Calendar Event Title')]"));
		selenium.clickAt("link=Events", RuntimeVariables.replace("Events"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("12:00 PM \u2013 3:00 PM"),
			selenium.getText(
				"//td[@id='_8_ocerSearchContainer_col-time_row-1']"));
		assertEquals(RuntimeVariables.replace("Calendar Event Title"),
			selenium.getText(
				"//td[@id='_8_ocerSearchContainer_col-title_row-1']"));
		assertEquals(RuntimeVariables.replace("Anniversary"),
			selenium.getText(
				"//td[@id='_8_ocerSearchContainer_col-type_row-1']"));
		selenium.clickAt("//td[@id='_8_ocerSearchContainer_col-title_row-1']/a",
			RuntimeVariables.replace("Calendar Event Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Calendar Event Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Start Date:"),
			selenium.getText("//dl[@class='property-list']/dt[1]"));
		assertEquals(RuntimeVariables.replace("End Date:"),
			selenium.getText("//dl[@class='property-list']/dt[2]"));
		assertEquals(RuntimeVariables.replace("Duration:"),
			selenium.getText("//dl[@class='property-list']/dt[3]"));
		assertEquals(RuntimeVariables.replace(
				"12:00 PM \u2013 3:00 PM (Time Zone Sensitive)"),
			selenium.getText("//dd[contains(.,'12')]"));
		assertEquals(RuntimeVariables.replace("Type:"),
			selenium.getText("//dl[@class='property-list']/dt[4]"));
		assertEquals(RuntimeVariables.replace("Calendar Event Description"),
			selenium.getText("//p[contains(.,'Calendar Event Description')]"));
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
	}
}