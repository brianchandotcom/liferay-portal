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

package com.liferay.portalweb.portlet.calendar.event.addeventdateendinvalid;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddEventDateEndInvalidTest extends BaseTestCase {
	public void testAddEventDateEndInvalid() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Calendar Test Page",
			RuntimeVariables.replace("Calendar Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Event']",
			RuntimeVariables.replace("Add Event"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_8_title']",
			RuntimeVariables.replace("Invalid End Date Test Event"));
		selenium.clickAt("//input[@name='_8_recurrenceType' and @value='3']	",
			RuntimeVariables.replace("Repeat Daily"));
		selenium.waitForVisible("//input[@id='_8_dailyInterval']");
		selenium.type("//input[@id='_8_dailyInterval']",
			RuntimeVariables.replace("1"));
		selenium.clickAt("//select[@id='_8_enddatemonth']",
			RuntimeVariables.replace("End Date Month"));
		selenium.select("//select[@id='_8_enddatemonth']",
			RuntimeVariables.replace("February"));
		assertFalse(selenium.isPartialText("//select[@id='_8_enddateday']", "30"));
	}
}