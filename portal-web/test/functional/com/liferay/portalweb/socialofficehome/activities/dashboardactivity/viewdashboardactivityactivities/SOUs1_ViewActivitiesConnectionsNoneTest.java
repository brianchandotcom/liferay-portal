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

package com.liferay.portalweb.socialofficehome.activities.dashboardactivity.viewdashboardactivityactivities;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs1_ViewActivitiesConnectionsNoneTest extends BaseTestCase {
	public void testSOUs1_ViewActivitiesConnectionsNone()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/socialoffice01/so/dashboard");
		assertEquals(RuntimeVariables.replace("Activities"),
			selenium.getText("xPath=(//h1[@class='portlet-title']/span)[2]"));
		assertEquals(RuntimeVariables.replace("Connections"),
			selenium.getText("link=Connections"));
		selenium.clickAt("link=Connections",
			RuntimeVariables.replace("Connections"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("There are no recent activities."),
			selenium.getText("xPath=(//div[@class='portlet-msg-info'])[2]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='activity-title']"));
		assertTrue(selenium.isElementNotPresent("//div[@class='activity-body']"));
		assertFalse(selenium.isTextPresent(
				"Joe Bloggs added a new task for Social01."));
		assertFalse(selenium.isTextPresent("Joe Bloggs added a new task."));
		assertFalse(selenium.isTextPresent(
				"Joe Bloggs uploaded a new document, DM Folder Document Title, in Joe Bloggs."));
		assertFalse(selenium.isTextPresent("#Microblogs Post"));
		assertFalse(selenium.isTextPresent("[@socialoffice01]"));
	}
}