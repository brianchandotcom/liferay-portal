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

package com.liferay.portalweb.portlet.calendar.event.addeventrepeatingmonthlydate;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddEventRepeatingMonthlyDateTest extends BaseTestCase {
	public void testAddEventRepeatingMonthlyDate() throws Exception {
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
			RuntimeVariables.replace("Monthly Date Repeating Event"));
		selenium.clickAt("//input[@name='_8_recurrenceType' and @value='5']",
			RuntimeVariables.replace(""));
		selenium.clickAt("//input[@name='_8_monthlyType' and @value='1']",
			RuntimeVariables.replace(""));
		selenium.waitForElementPresent("//select[@id='_8_monthlyPos']");
		selenium.select("//select[@id='_8_monthlyPos']",
			RuntimeVariables.replace("First"));
		selenium.select("//select[@id='_8_monthlyDay1']",
			RuntimeVariables.replace("Thursday"));
		selenium.type("//input[@id='_8_monthlyInterval1']",
			RuntimeVariables.replace("1"));
		selenium.clickAt("//fieldset[2]/div/div/div/span[2]/span/span/input",
			RuntimeVariables.replace(""));
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
		selenium.clickAt("link=Events", RuntimeVariables.replace("Events"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Monthly Date Repeating Event",
			RuntimeVariables.replace("Monthly Date Repeating Event"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Monthly Date Repeating Event"),
			selenium.getText("//div[1]/h1/span"));
		assertEquals(RuntimeVariables.replace("1/1/10"),
			selenium.getText("//dl[@class='property-list']/dd[1]"));
		assertEquals(RuntimeVariables.replace("1/1/11"),
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
		assertEquals("2010",
			selenium.getSelectedLabel("//select[@id='_8_startdateyear']"));
		assertEquals("Monthly Date Repeating Event",
			selenium.getValue("//input[@id='_8_title']"));
		assertTrue(selenium.isChecked(
				"//input[@name='_8_recurrenceType' and @value='5']"));
		assertTrue(selenium.isChecked(
				"//input[@name='_8_monthlyType' and @value='1']"));
		selenium.waitForElementPresent("//select[@id='_8_monthlyPos']");
		assertEquals("First",
			selenium.getSelectedLabel("//select[@id='_8_monthlyPos']"));
		assertEquals("Thursday",
			selenium.getSelectedLabel("//select[@id='_8_monthlyDay1']"));
		assertEquals("1",
			selenium.getValue("//input[@id='_8_monthlyInterval1']"));
		assertTrue(selenium.isChecked(
				"//fieldset[2]/div/div/div/span[2]/span/span/input"));
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
			"//a[contains(@href, 'javascript:_8_updateCalendar(1, 4, 2010);')]");
		selenium.clickAt("//a[contains(@href, 'javascript:_8_updateCalendar(1, 4, 2010);')]",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("//div[@class='event-title']/a"));
		selenium.clickAt("link=Year", RuntimeVariables.replace("Year"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select", RuntimeVariables.replace("2010"));
		selenium.waitForElementPresent(
			"//a[contains(@href, 'javascript:_8_updateCalendar(2, 4, 2010);')]");
		selenium.clickAt("//a[contains(@href, 'javascript:_8_updateCalendar(2, 4, 2010);')]",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("//div[@class='event-title']/a"));
		selenium.clickAt("link=Year", RuntimeVariables.replace("Year"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select", RuntimeVariables.replace("2010"));
		selenium.waitForElementPresent(
			"//a[contains(@href, 'javascript:_8_updateCalendar(2, 11, 2010);')]");
		selenium.clickAt("//a[contains(@href, 'javascript:_8_updateCalendar(2, 11, 2010);')]",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementNotPresent("//div[@class='event-title']/a"));
	}
}