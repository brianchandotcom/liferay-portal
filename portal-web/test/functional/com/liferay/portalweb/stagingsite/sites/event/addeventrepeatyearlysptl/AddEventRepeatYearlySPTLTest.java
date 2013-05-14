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

package com.liferay.portalweb.stagingsite.sites.event.addeventrepeatyearlysptl;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddEventRepeatYearlySPTLTest extends BaseTestCase {
	public void testAddEventRepeatYearlySPTL() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
				assertEquals(RuntimeVariables.replace("Go to"),
					selenium.getText("//li[@id='_145_mySites']/a/span"));
				selenium.mouseOver("//li[@id='_145_mySites']/a/span");
				selenium.waitForVisible("link=Site Name");
				selenium.clickAt("link=Site Name",
					RuntimeVariables.replace("Site Name"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//body[contains(@class,'live-view')]"));
				assertTrue(selenium.isElementNotPresent(
						"//body[contains(@class,'local-staging')]"));
				assertTrue(selenium.isPartialText(
						"//span/a[contains(.,'Staging')]", "Staging"));
				selenium.clickAt("//span/a[contains(.,'Staging')]",
					RuntimeVariables.replace("Staging"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementPresent(
						"//body[contains(@class,'local-staging')]"));
				assertTrue(selenium.isElementNotPresent(
						"//body[contains(@class,'live-view')]"));
				Thread.sleep(1000);
				selenium.clickAt("//span[@class='staging-icon-menu-container']/span/ul/li/strong/a",
					RuntimeVariables.replace("Staging Drop Down"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Schedule Publication to Live')]");
				assertEquals(RuntimeVariables.replace(
						"Schedule Publication to Live"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Schedule Publication to Live')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Schedule Publication to Live')]",
					RuntimeVariables.replace("Schedule Publication to Live"));
				selenium.waitForVisible("//div[2]/div[1]/a");

				boolean startDateMonthVisible = selenium.isVisible(
						"_88_schedulerStartDateMonth");

				if (startDateMonthVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[3]/div[1]/a",
					RuntimeVariables.replace("Plus"));

			case 2:
				Thread.sleep(1000);
				selenium.waitForElementPresent(
					"//input[@id='_88_recurrenceTypeYearly']");
				selenium.clickAt("//input[@id='_88_recurrenceTypeYearly']",
					RuntimeVariables.replace("Yearly"));
				selenium.waitForVisible("//input[@value='Add Event']");
				selenium.clickAt("//input[@value='Add Event']",
					RuntimeVariables.replace("Add Event"));
				selenium.waitForVisible(
					"//tr[@class='portlet-section-header results-header']/th[2]");
				assertEquals(RuntimeVariables.replace("No End Date"),
					selenium.getText("//tr[contains(.,'No End Date')]/td[3]"));

			case 100:
				label = -1;
			}
		}
	}
}