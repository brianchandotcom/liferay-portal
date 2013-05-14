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

package com.liferay.portalweb.socialofficesite.home.events.viewcalendareventsedsite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewCalendarEventDays2EDSiteTest extends BaseTestCase {
	public void testViewCalendarEventDays2EDSite() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Events"),
			selenium.getText(
				"xPath=(//span[@class='portlet-title-text'])[contains(.,'Events')]"));
		assertTrue(selenium.isPartialText("//h2[contains(.,'Events')]", "Events"));
		assertEquals(RuntimeVariables.replace("Today's Events"),
			selenium.getText(
				"//section[contains(@id,'eventsdisplayportlet')]/div/div/div/h2[1]"));
		assertEquals(RuntimeVariables.replace("Upcoming Events"),
			selenium.getText(
				"//section[contains(@id,'eventsdisplayportlet')]/div/div/div/h2[2]"));
		assertEquals(RuntimeVariables.replace("Calendar Event Title"),
			selenium.getText("xPath=(//span[@class='event-name']/a)[1]"));
		assertEquals(RuntimeVariables.replace(
				"Calendar Future Day1 Event Title"),
			selenium.getText("xPath=(//span[@class='event-name']/a)[2]"));
		assertTrue(selenium.isElementNotPresent(
				"xPath=(//span[@class='event-name']/a)[3]"));
		selenium.clickAt("xPath=(//span[@class='event-name']/a)[2]",
			RuntimeVariables.replace("Calendar Future Day1 Event Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Calendar Future Day1 Event Title"),
			selenium.getText("//h1[@class='header-title']"));
		assertEquals(RuntimeVariables.replace("Start Date:"),
			selenium.getText("//dl[@class='property-list']/dt[1]"));
		assertEquals(RuntimeVariables.replace("End Date:"),
			selenium.getText("//dl[@class='property-list']/dt[2]"));
		assertEquals(RuntimeVariables.replace("Duration:"),
			selenium.getText("//dl[@class='property-list']/dt[3]"));
		assertEquals(RuntimeVariables.replace("Type:"),
			selenium.getText("//dl[@class='property-list']/dt[4]"));
		assertEquals(RuntimeVariables.replace(
				"Calendar Future Day1 Event Description"),
			selenium.getText(
				"//p[contains(.,'Calendar Future Day1 Event Description')]"));
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