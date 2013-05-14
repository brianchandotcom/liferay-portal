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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.stagingcommunity.quartz;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddSchedulerEventStagingCommunityQuartzTest extends BaseTestCase {
	public void testAddSchedulerEventStagingCommunityQuartz()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/community-staging-community-quartz-staging/");
		selenium.waitForVisible("link=Page Staging Community Quartz");
		selenium.clickAt("link=Page Staging Community Quartz",
			RuntimeVariables.replace("Page Staging Community Quartz"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//strong/a",
			RuntimeVariables.replace("Staging Drop Down"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a");
		assertEquals(RuntimeVariables.replace("Schedule Publication to Live"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a"));
		selenium.click("//div[@class='lfr-menu-list unstyled']/ul/li[2]/a");
		selenium.waitForVisible("//input[@name='_88_description']");
		selenium.type("//input[@name='_88_description']",
			RuntimeVariables.replace("Quartz Scheduler Event"));
		selenium.select("//select[@id='_88_schedulerStartDateMonth']",
			RuntimeVariables.replace("December"));
		selenium.select("//select[@id='_88_schedulerStartDateDay']",
			RuntimeVariables.replace("31"));
		selenium.select("//select[@id='_88_schedulerStartDateYear']",
			RuntimeVariables.replace("2016"));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Add Event']",
			RuntimeVariables.replace("Add Event"));
		assertEquals(RuntimeVariables.replace("View All"),
			selenium.getText("//span[1]/a"));
		selenium.clickAt("//span[1]/a", RuntimeVariables.replace("View All"));
		selenium.waitForText("//div[@id='_88_scheduledPublishEventsDiv']/div/div/table/tbody/tr[3]/td",
			"Quartz Scheduler Event");
		assertEquals(RuntimeVariables.replace("Quartz Scheduler Event"),
			selenium.getText(
				"//div[@id='_88_scheduledPublishEventsDiv']/div/div/table/tbody/tr[3]/td"));
	}
}