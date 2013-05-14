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

package com.liferay.portalweb.socialofficesite.calendar.calendarevent.ratecalendareventsite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RateCalendarEventSiteTest extends BaseTestCase {
	public void testRateCalendarEventSite() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Calendar Event Title"),
			selenium.getText(
				"//td[@id='_8_ocerSearchContainer_col-title_row-1']"));
		assertEquals(RuntimeVariables.replace("Anniversary"),
			selenium.getText(
				"//td[@id='_8_ocerSearchContainer_col-type_row-1']"));
		selenium.clickAt("//td[@id='_8_ocerSearchContainer_col-title_row-1']/a",
			RuntimeVariables.replace("Calendar Event Title"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible("//div[@class='entry-ratings']"));
		Thread.sleep(1000);
		assertTrue(selenium.isElementPresent(
				"//div[@class='entry-ratings']/div/div/div/a[5]"));
		selenium.clickAt("//div[@class='entry-ratings']/div/div/div/a[5]",
			RuntimeVariables.replace("5 stars out of 5"));
		selenium.waitForElementPresent(
			"//img[@alt='The average rating is 5 stars out of 5.']");
		assertTrue(selenium.isElementPresent(
				"//img[@alt='The average rating is 5 stars out of 5.']"));
		assertEquals(RuntimeVariables.replace("Average (1 Vote)"),
			selenium.getText("xPath=(//div[@class='rating-label-element'])[2]"));
	}
}