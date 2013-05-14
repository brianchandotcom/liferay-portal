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

package com.liferay.portalweb.portlet.calendar.event.addeventrepeatingyearlydate;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddEventRepeatingYearlyDateTest extends BaseTestCase {
	public void testAddEventRepeatingYearlyDate() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Calendar Test Page", RuntimeVariables.replace(""));
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
			RuntimeVariables.replace("Yearly Date Repeating Event"));
		selenium.waitForVisible(
			"//input[@name='_8_recurrenceType' and @value='6']");
		selenium.clickAt("//input[@name='_8_recurrenceType' and @value='6']",
			RuntimeVariables.replace("Repeat Yearly"));
		selenium.waitForVisible("//input[@name='_8_yearlyType']");
		selenium.clickAt("//input[@name='_8_yearlyType']",
			RuntimeVariables.replace("Every"));
		selenium.select("//select[@id='_8_yearlyMonth0']",
			RuntimeVariables.replace("February"));
		selenium.type("//input[@id='_8_yearlyDay0']",
			RuntimeVariables.replace("4"));
		selenium.type("//input[@id='_8_yearlyInterval0']",
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
			RuntimeVariables.replace("2013"));
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
		selenium.clickAt("link=Yearly Date Repeating Event",
			RuntimeVariables.replace("Yearly Date Repeating Event"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Yearly Date Repeating Event"),
			selenium.getText("//div[1]/h1/span"));
		assertEquals(RuntimeVariables.replace("1/1/10"),
			selenium.getText("//dl[@class='property-list']/dd[1]"));
		assertEquals(RuntimeVariables.replace("1/1/13"),
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
		assertEquals("Yearly Date Repeating Event",
			selenium.getValue("//input[@id='_8_title']"));
		selenium.waitForVisible(
			"//input[@name='_8_recurrenceType' and @value='6']");
		assertTrue(selenium.isChecked(
				"//input[@name='_8_recurrenceType' and @value='6']"));
		selenium.waitForElementPresent("//input[@name='_8_yearlyType']");
		assertTrue(selenium.isChecked("//input[@name='_8_yearlyType']"));
		assertEquals("February",
			selenium.getSelectedLabel("//select[@id='_8_yearlyMonth1']"));
		assertEquals("4", selenium.getValue("//input[@id='_8_yearlyDay0']"));
		assertEquals("1", selenium.getValue("//input[@id='_8_yearlyInterval0']"));
		selenium.waitForVisible(
			"//input[@name='_8_endDateType' and @value='2']");
		assertTrue(selenium.isChecked(
				"//input[@name='_8_endDateType' and @value='2']"));
		assertEquals("January",
			selenium.getSelectedLabel("//select[@id='_8_enddatemonth']"));
		assertEquals("1",
			selenium.getSelectedLabel("//select[@id='_8_enddateday']"));
		assertEquals("2013",
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
		selenium.select("//select", RuntimeVariables.replace("2011"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent(
			"//a[contains(@href, 'javascript:_8_updateCalendar(1, 4, 2011);')]");
		selenium.clickAt("//a[contains(@href, 'javascript:_8_updateCalendar(1, 4, 2011);')]",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent("//div[@class='event-title']/a"));
		selenium.clickAt("link=Year", RuntimeVariables.replace("Year"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select", RuntimeVariables.replace("2011"));
		selenium.waitForElementPresent(
			"//a[contains(@href, 'javascript:_8_updateCalendar(2, 4, 2011);')]");
		selenium.clickAt("//a[contains(@href, 'javascript:_8_updateCalendar(2, 4, 2011);')]",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementNotPresent("//div[@class='event-title']/a"));
	}
}