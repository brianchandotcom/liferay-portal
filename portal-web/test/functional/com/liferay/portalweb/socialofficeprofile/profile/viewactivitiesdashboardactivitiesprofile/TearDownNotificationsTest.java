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

package com.liferay.portalweb.socialofficeprofile.profile.viewactivitiesdashboardactivitiesprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownNotificationsTest extends BaseTestCase {
	public void testTearDownNotifications() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/user/joebloggs/so/dashboard/");
				selenium.waitForVisible("//span[@class='notification-count']");
				selenium.mouseOver("//span[@class='notification-count']");
				assertEquals(RuntimeVariables.replace("View All\u00bb"),
					selenium.getText("link=View All\u00bb"));
				selenium.waitForElementPresent(
					"//div[contains(@class, 'user-notification-events-container')]");
				assertTrue(selenium.isVisible(
						"//div[contains(@class, 'user-notification-events-container')]"));
				selenium.waitForElementPresent("link=View All\u00bb");
				selenium.clickAt("link=View All\u00bb",
					RuntimeVariables.replace("View All\u00bb"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Notifications"),
					selenium.getText("//span[contains(.,'Notifications')]"));

				boolean notificationsPresent = selenium.isElementPresent(
						"//input[@name='_6_WAR_soportlet_allRowIds']");

				if (!notificationsPresent) {
					label = 2;

					continue;
				}

				assertTrue(selenium.isVisible(
						"//input[@name='_6_WAR_soportlet_allRowIds']"));
				selenium.clickAt("//input[@name='_6_WAR_soportlet_allRowIds']",
					RuntimeVariables.replace("Select All Checkbox"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");

			case 2:
				assertEquals(RuntimeVariables.replace(
						"You have no notifications."),
					selenium.getText("//div[@class='portlet-msg-info']"));

			case 100:
				label = -1;
			}
		}
	}
}