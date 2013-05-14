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

package com.liferay.portalweb.socialofficesite.home.events.vieweditcalendarfuture2dayseventedsite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditCalendarEventFuture2DaysSiteTest extends BaseTestCase {
	public void testEditCalendarEventFuture2DaysSite()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
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
					selenium.getText(
						"//nav/ul/li[contains(.,'Calendar')]/a/span"));
				selenium.clickAt("//nav/ul/li[contains(.,'Calendar')]/a/span",
					RuntimeVariables.replace("Calendar"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Events",
					RuntimeVariables.replace("Events"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//td[@id='_8_ocerSearchContainer_col-time_row-3']"));
				assertEquals(RuntimeVariables.replace(
						"Calendar Future Day2 Event Title"),
					selenium.getText(
						"//td[@id='_8_ocerSearchContainer_col-title_row-3']"));
				assertEquals(RuntimeVariables.replace("Anniversary"),
					selenium.getText(
						"//td[@id='_8_ocerSearchContainer_col-type_row-3']"));
				Thread.sleep(1000);
				selenium.waitForVisible(
					"//tr[contains(.,'Calendar Future Day2 Event Title')]/td[contains(.,'Actions')]/span/ul/li/strong/a");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//tr[contains(.,'Calendar Future Day2 Event Title')]/td[contains(.,'Actions')]/span/ul/li/strong/a"));
				selenium.clickAt("//tr[contains(.,'Calendar Future Day2 Event Title')]/td[contains(.,'Actions')]/span/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Edit')]/a");
				assertEquals(RuntimeVariables.replace("Edit"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Edit')]/a"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Edit')]/a",
					RuntimeVariables.replace("Edit"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(1000);

				boolean newStartDateHourSelectPresent = selenium.isElementPresent(
						"//select[@name='_8_startdatehour']");

				if (!newStartDateHourSelectPresent) {
					label = 2;

					continue;
				}

				selenium.select("//select[@name='_8_startdatehour']",
					RuntimeVariables.replace("12"));

			case 2:

				boolean oldStartDateHourSelectPresent = selenium.isElementPresent(
						"//select[@name='_8_startDateHour']");

				if (!oldStartDateHourSelectPresent) {
					label = 3;

					continue;
				}

				selenium.select("//select[@name='_8_startDateHour']",
					RuntimeVariables.replace("12"));

			case 3:

				boolean newStartDateMinuteSelectPresent = selenium.isElementPresent(
						"//select[@name='_8_startdateminute']");

				if (!newStartDateMinuteSelectPresent) {
					label = 4;

					continue;
				}

				selenium.select("//select[@name='_8_startdateminute']",
					RuntimeVariables.replace(":00"));

			case 4:

				boolean oldStartDateMinuteSelectPresent = selenium.isElementPresent(
						"//select[@name='_8_startDateMinute']");

				if (!oldStartDateMinuteSelectPresent) {
					label = 5;

					continue;
				}

				selenium.select("//select[@name='_8_startDateMinute']",
					RuntimeVariables.replace(":00"));

			case 5:

				boolean newStartDateAmPmSelectPresent = selenium.isElementPresent(
						"//select[@name='_8_startdateampm']");

				if (!newStartDateAmPmSelectPresent) {
					label = 6;

					continue;
				}

				selenium.select("//select[@name='_8_startdateampm']",
					RuntimeVariables.replace("PM"));

			case 6:

				boolean oldStartDateAmPmSelectPresent = selenium.isElementPresent(
						"//select[@name='_8_startDateAmPm']");

				if (!oldStartDateAmPmSelectPresent) {
					label = 7;

					continue;
				}

				selenium.select("//select[@name='_8_startDateAmPm']",
					RuntimeVariables.replace("PM"));

			case 7:

				boolean newDurationHourSelectPresent = selenium.isElementPresent(
						"//select[@name='_8_durationhour']");

				if (!newDurationHourSelectPresent) {
					label = 8;

					continue;
				}

				selenium.select("//select[@name='_8_durationhour']",
					RuntimeVariables.replace("3"));

			case 8:

				boolean oldDurationHourSelectPresent = selenium.isElementPresent(
						"//select[@name='_8_durationHour']");

				if (!oldDurationHourSelectPresent) {
					label = 9;

					continue;
				}

				selenium.select("//select[@name='_8_durationHour']",
					RuntimeVariables.replace("3"));

			case 9:
				selenium.type("//input[@id='_8_title']",
					RuntimeVariables.replace(
						"Calendar Future Day2 Event Title Edit"));
				selenium.waitForElementPresent(
					"//textarea[@id='_8_editor' and @style='display: none;']");
				selenium.waitForVisible("//span[.='Source']");
				assertEquals(RuntimeVariables.replace("Source"),
					selenium.getText("//span[.='Source']"));
				selenium.clickAt("//span[.='Source']",
					RuntimeVariables.replace("Source"));
				selenium.waitForVisible(
					"//a[@class='cke_button_source cke_on']");
				selenium.waitForVisible(
					"//td[@id='cke_contents__8_editor']/textarea");
				selenium.type("//td[@id='cke_contents__8_editor']/textarea",
					RuntimeVariables.replace(
						"Calendar Future Day2 Event Description Edit"));
				assertEquals(RuntimeVariables.replace("Source"),
					selenium.getText("//span[.='Source']"));
				selenium.clickAt("//span[.='Source']",
					RuntimeVariables.replace("Source"));
				selenium.waitForElementPresent(
					"//textarea[@id='_8_editor' and @style='display: none;']");
				selenium.waitForVisible(
					"//td[@id='cke_contents__8_editor']/iframe");
				selenium.selectFrame(
					"//td[@id='cke_contents__8_editor']/iframe");
				selenium.waitForText("//body",
					"Calendar Future Day2 Event Description Edit");
				selenium.selectFrame("relative=top");
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace("Events"),
					selenium.getText("link=Events"));
				selenium.clickAt("link=Events",
					RuntimeVariables.replace("Events"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("12:00 PM \u2013 3:00 PM"),
					selenium.getText(
						"//tr[contains(.,'Calendar Future Day2 Event Title Edit')]/td[contains(@id,'_8_ocerSearchContainer_col-time')]"));
				assertEquals(RuntimeVariables.replace(
						"Calendar Future Day2 Event Title Edit"),
					selenium.getText(
						"//tr[contains(.,'Calendar Future Day2 Event Title Edit')]/td[contains(@id,'_8_ocerSearchContainer_col-title')]"));
				assertEquals(RuntimeVariables.replace("Anniversary"),
					selenium.getText(
						"//tr[contains(.,'Calendar Future Day2 Event Title Edit')]/td[contains(@id,'_8_ocerSearchContainer_col-type')]"));

			case 100:
				label = -1;
			}
		}
	}
}