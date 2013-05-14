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

package com.liferay.portalweb.portlet.calendar.event.addeventrepeatingyearlyday;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddEventRepeatingYearlyDayTest extends BaseTestCase {
	public void testAddEventRepeatingYearlyDay() throws Exception {
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
			RuntimeVariables.replace("2014"));
		selenium.type("//input[@id='_8_title']",
			RuntimeVariables.replace("Yearly Day Repeating Event"));
		selenium.waitForVisible(
			"//input[@name='_8_recurrenceType' and @value='6']");
		selenium.clickAt("//input[@name='_8_recurrenceType' and @value='6']",
			RuntimeVariables.replace("Repeat Yearly"));
		selenium.waitForVisible("//input[@name='_8_yearlyType' and @value='1']");
		selenium.clickAt("//input[@name='_8_yearlyType' and @value='1']",
			RuntimeVariables.replace("The"));
		selenium.select("//select[@id='_8_yearlyPos']",
			RuntimeVariables.replace("Second"));
		selenium.select("//select[@id='_8_yearlyDay1']",
			RuntimeVariables.replace("Monday"));
		selenium.select("//select[@id='_8_yearlyMonth1']",
			RuntimeVariables.replace("February"));
		selenium.type("//input[@id='_8_yearlyInterval1']",
			RuntimeVariables.replace("1"));
		selenium.waitForVisible(
			"//input[@name='_8_endDateType' and @value='2']");
		selenium.clickAt("//input[@name='_8_endDateType' and @value='2']",
			RuntimeVariables.replace("End by"));
		selenium.select("//select[@id='_8_enddatemonth']",
			RuntimeVariables.replace("January"));
		selenium.select("//select[@id='_8_enddateday']",
			RuntimeVariables.replace("1"));
		selenium.select("//select[@id='_8_enddateyear']",
			RuntimeVariables.replace("2016"));
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
		selenium.clickAt("link=Events", RuntimeVariables.replace("Events"));
		selenium.waitForPageToLoad("30000");
		selenium.click(RuntimeVariables.replace(
				"link=Yearly Day Repeating Event"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Yearly Day Repeating Event"),
			selenium.getText("//div[1]/h1/span"));
		assertEquals(RuntimeVariables.replace("1/1/14"),
			selenium.getText("//dl[@class='property-list']/dd[1]"));
		assertEquals(RuntimeVariables.replace("1/1/16"),
			selenium.getText("//dl[@class='property-list']/dd[2]"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Calendar Test Page",
			RuntimeVariables.replace("Calendar Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Events", RuntimeVariables.replace("Events"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		selenium.waitForVisible("//span[@title='Actions']/ul/li/strong/a/span");
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
			selenium.getSelectedLabel("//select[@id='_8_startdatemonth']"));
		assertEquals("1",
			selenium.getSelectedLabel("//select[@id='_8_startdateday']"));
		assertEquals("2014",
			selenium.getSelectedLabel("//select[@id='_8_startdateyear']"));
		assertEquals("Yearly Day Repeating Event",
			selenium.getValue("//input[@id='_8_title']"));
		selenium.waitForVisible(
			"//input[@name='_8_recurrenceType' and @value='6']");
		assertTrue(selenium.isChecked(
				"//input[@name='_8_recurrenceType' and @value='6']"));
		selenium.waitForVisible("//input[@name='_8_yearlyType' and @value='1']");
		assertTrue(selenium.isChecked(
				"//input[@name='_8_yearlyType' and @value='1']"));
		assertEquals("Second",
			selenium.getSelectedLabel("//select[@id='_8_yearlyPos']"));
		assertEquals("Monday",
			selenium.getSelectedLabel("//select[@id='_8_yearlyDay1']"));
		assertEquals("February",
			selenium.getSelectedLabel("//select[@id='_8_yearlyMonth1']"));
		assertEquals("1", selenium.getValue("//input[@id='_8_yearlyInterval1']"));
		assertTrue(selenium.isVisible(
				"//input[@name='_8_endDateType' and @value='2']"));
		assertTrue(selenium.isChecked(
				"//input[@name='_8_endDateType' and @value='2']"));
		assertEquals("January",
			selenium.getSelectedLabel("//select[@id='_8_enddatemonth']"));
		assertEquals("1",
			selenium.getSelectedLabel("//select[@id='_8_enddateday']"));
		assertEquals("2016",
			selenium.getSelectedLabel("//select[@id='_8_enddateyear']"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Calendar Test Page",
			RuntimeVariables.replace("Calendar Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Year", RuntimeVariables.replace("Year"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select", RuntimeVariables.replace("2014"));
		selenium.waitForElementPresent(
			"//a[contains(@href, 'javascript:_8_updateCalendar(1, 10, 2014);')]");
		selenium.clickAt("//a[contains(@href, 'javascript:_8_updateCalendar(1, 10, 2014);')]",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("//div[@class='event-title']/a"));
		selenium.clickAt("link=Year", RuntimeVariables.replace("Year"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select", RuntimeVariables.replace("2015"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent(
			"//a[contains(@href, 'javascript:_8_updateCalendar(1, 9, 2015);')]");
		selenium.clickAt("//a[contains(@href, 'javascript:_8_updateCalendar(1, 9, 2015);')]",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("//div[@class='event-title']/a"));
		selenium.clickAt("link=Year", RuntimeVariables.replace("Year"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select", RuntimeVariables.replace("2016"));
		selenium.waitForElementPresent(
			"//a[contains(@href, 'javascript:_8_updateCalendar(1, 9, 2016);')]");
		selenium.clickAt("//a[contains(@href, 'javascript:_8_updateCalendar(1, 9, 2016);')]",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementNotPresent("//div[@class='event-title']/a"));
	}
}