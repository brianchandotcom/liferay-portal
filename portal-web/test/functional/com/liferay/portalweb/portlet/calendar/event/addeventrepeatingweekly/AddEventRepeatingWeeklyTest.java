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

package com.liferay.portalweb.portlet.calendar.event.addeventrepeatingweekly;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddEventRepeatingWeeklyTest extends BaseTestCase {
	public void testAddEventRepeatingWeekly() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Calendar Test Page",
					RuntimeVariables.replace("Calendar Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//input[@value='Add Event']",
					RuntimeVariables.replace("Add Event"));
				selenium.waitForPageToLoad("30000");
				selenium.select("//select[@id='_8_startdatemonth']",
					RuntimeVariables.replace("January"));
				selenium.select("//select[@id='_8_startdateday']",
					RuntimeVariables.replace("1"));
				selenium.select("//select[@id='_8_startdateyear']",
					RuntimeVariables.replace("2010"));
				selenium.type("//input[@id='_8_title']",
					RuntimeVariables.replace("Weekly Repeating Event"));
				selenium.clickAt("//input[@name='_8_recurrenceType' and @value='4']",
					RuntimeVariables.replace(""));
				selenium.waitForVisible("//input[@id='_8_weeklyInterval']");
				selenium.type("//input[@id='_8_weeklyInterval']",
					RuntimeVariables.replace("1"));

				boolean sundayChecked = selenium.isChecked(
						"_8_weeklyDayPos1Checkbox");

				if (!sundayChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_8_weeklyDayPos1Checkbox']",
					RuntimeVariables.replace("Sunday Checkbox"));

			case 2:

				boolean mondayChecked = selenium.isChecked(
						"_8_weeklyDayPos2Checkbox");

				if (!mondayChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@id='_8_weeklyDayPos2Checkbox']",
					RuntimeVariables.replace("Monday Checkbox"));

			case 3:

				boolean tuesdayChecked = selenium.isChecked(
						"_8_weeklyDayPos3Checkbox");

				if (!tuesdayChecked) {
					label = 4;

					continue;
				}

			case 4:

				boolean wednesdayChecked = selenium.isChecked(
						"_8_weeklyDayPos4Checkbox");

				if (!wednesdayChecked) {
					label = 5;

					continue;
				}

				selenium.clickAt("//input[@id='_8_weeklyDayPos4Checkbox']",
					RuntimeVariables.replace("Wednesday Checkbox"));

			case 5:

				boolean thursdayChecked = selenium.isChecked(
						"_8_weeklyDayPos5Checkbox");

				if (thursdayChecked) {
					label = 6;

					continue;
				}

				selenium.clickAt("//input[@id='_8_weeklyDayPos5Checkbox']",
					RuntimeVariables.replace("Thursday Checkbox"));

			case 6:

				boolean fridayChecked = selenium.isChecked(
						"_8_weeklyDayPos6Checkbox");

				if (!fridayChecked) {
					label = 7;

					continue;
				}

				selenium.clickAt("//input[@id='_8_weeklyDayPos6Checkbox']",
					RuntimeVariables.replace("Friday Checkbox"));

			case 7:

				boolean saturdayChecked = selenium.isChecked(
						"_8_weeklyDayPos7Checkbox");

				if (!saturdayChecked) {
					label = 8;

					continue;
				}

				selenium.clickAt("//input[@id='_8_weeklyDayPos7Checkbox']",
					RuntimeVariables.replace("Saturday Checkbox"));

			case 8:
				selenium.click("//input[@name='_8_endDateType' and @value='2']");
				selenium.select("//select[@id='_8_enddatemonth']",
					RuntimeVariables.replace("January"));
				selenium.select("//select[@id='_8_enddateday']",
					RuntimeVariables.replace("1"));
				selenium.select("//select[@id='_8_enddateyear']",
					RuntimeVariables.replace("2011"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Calendar Test Page",
					RuntimeVariables.replace("Calendar Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Events",
					RuntimeVariables.replace("Events"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Weekly Repeating Event",
					RuntimeVariables.replace("Weekly Repeating Event"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Weekly Repeating Event"),
					selenium.getText("//div[1]/h1/span"));
				assertEquals(RuntimeVariables.replace("1/1/10"),
					selenium.getText("//dl[@class='property-list']/dd[1]"));
				assertEquals(RuntimeVariables.replace("1/1/11"),
					selenium.getText("//dl[@class='property-list']/dd[2]"));
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Calendar Test Page",
					RuntimeVariables.replace("Calendar Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Events",
					RuntimeVariables.replace("Events"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(1000);
				selenium.waitForVisible(
					"//span[@title='Actions']/ul/li/strong/a/span");
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]");
				assertEquals(RuntimeVariables.replace("Edit"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]",
					RuntimeVariables.replace("Edit"));
				selenium.waitForPageToLoad("30000");
				assertEquals("January",
					selenium.getSelectedLabel(
						"//select[@id='_8_startdatemonth']"));
				assertEquals("1",
					selenium.getSelectedLabel("//select[@id='_8_startdateday']"));
				assertEquals("2010",
					selenium.getSelectedLabel(
						"//select[@id='_8_startdateyear']"));
				assertEquals("Weekly Repeating Event",
					selenium.getValue("//input[@id='_8_title']"));
				assertTrue(selenium.isChecked(
						"//input[@name='_8_recurrenceType' and @value='4']"));
				selenium.waitForElementPresent(
					"//input[@id='_8_weeklyInterval']");
				assertEquals("1",
					selenium.getValue("//input[@id='_8_weeklyInterval']"));
				assertTrue(selenium.isChecked(
						"//input[@id='_8_weeklyDayPos5Checkbox']"));
				assertTrue(selenium.isChecked(
						"//input[@name='_8_endDateType' and @value='2']"));
				assertEquals("January",
					selenium.getSelectedLabel("//select[@id='_8_enddatemonth']"));
				assertEquals("1",
					selenium.getSelectedLabel("//select[@id='_8_enddateday']"));
				assertEquals("2011",
					selenium.getSelectedLabel("//select[@id='_8_enddateyear']"));
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Calendar Test Page",
					RuntimeVariables.replace("Calendar Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Year", RuntimeVariables.replace("Year"));
				selenium.waitForPageToLoad("30000");
				selenium.select("//select", RuntimeVariables.replace("2010"));
				selenium.waitForElementPresent(
					"//a[contains(@href, 'javascript:_8_updateCalendar(10, 4, 2010);')]");
				selenium.clickAt("//a[contains(@href, 'javascript:_8_updateCalendar(10, 4, 2010);')]",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Weekly Repeating Event"),
					selenium.getText("//div[@class='event-title']/a"));
				selenium.clickAt("link=Year", RuntimeVariables.replace("Year"));
				selenium.waitForPageToLoad("30000");
				selenium.select("//select", RuntimeVariables.replace("2010"));
				selenium.waitForElementPresent(
					"//a[contains(@href, 'javascript:_8_updateCalendar(10, 11, 2010);')]");
				selenium.clickAt("//a[contains(@href, 'javascript:_8_updateCalendar(10, 11, 2010);')]",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Weekly Repeating Event"),
					selenium.getText("//div[@class='event-title']/a"));
				selenium.clickAt("link=Year", RuntimeVariables.replace("Year"));
				selenium.waitForPageToLoad("30000");
				selenium.select("//select", RuntimeVariables.replace("2010"));
				selenium.waitForElementPresent(
					"//a[contains(@href, 'javascript:_8_updateCalendar(10, 12, 2010);')]");
				selenium.clickAt("//a[contains(@href, 'javascript:_8_updateCalendar(10, 12, 2010);')]",
					RuntimeVariables.replace(""));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementNotPresent(
						"//div[@class='event-title']/a"));

			case 100:
				label = -1;
			}
		}
	}
}