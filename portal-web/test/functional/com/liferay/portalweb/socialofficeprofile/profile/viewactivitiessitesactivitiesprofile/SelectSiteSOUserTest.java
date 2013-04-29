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

package com.liferay.portalweb.socialofficeprofile.profile.viewactivitiessitesactivitiesprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SelectSiteSOUserTest extends BaseTestCase {
	public void testSelectSiteSOUser() throws Exception {
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
				selenium.waitForVisible("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Users and Organizations",
					RuntimeVariables.replace("Users and Organizations"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Search All Users",
					RuntimeVariables.replace("Search All Users"));
				selenium.waitForPageToLoad("30000");

				boolean basicVisible = selenium.isVisible(
						"//a[.='\u00ab Basic']");

				if (!basicVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//a[.='\u00ab Basic']",
					RuntimeVariables.replace("\u00ab Basic"));

			case 2:
				selenium.waitForVisible(
					"//input[@id='_125_toggle_id_users_admin_user_searchkeywords']");
				selenium.type("//input[@id='_125_toggle_id_users_admin_user_searchkeywords']",
					RuntimeVariables.replace("socialoffice01"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Social01"),
					selenium.getText("//td[contains(.,'Social01')]/a"));
				selenium.clickAt("//td[contains(.,'Social01')]/a",
					RuntimeVariables.replace("Social01"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isPartialText("//a[@id='_125_sitesLink']",
						"Sites"));
				selenium.clickAt("//a[@id='_125_sitesLink']",
					RuntimeVariables.replace("Sites"));
				selenium.waitForVisible("//div[4]/span/a/span");
				assertEquals(RuntimeVariables.replace("Select"),
					selenium.getText("//div[4]/span/a/span"));
				selenium.clickAt("//div[4]/span/a/span",
					RuntimeVariables.replace("Select"));
				Thread.sleep(1000);
				selenium.selectWindow("title=Users and Organizations");
				selenium.waitForText("//h1[@class='header-title']/span", "Sites");
				assertEquals(RuntimeVariables.replace("Sites"),
					selenium.getText("//h1[@class='header-title']/span"));
				selenium.type("//input[@id='_125_name']",
					RuntimeVariables.replace("Open Site Name"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Open Site Name"),
					selenium.getText("link=Open Site Name"));
				selenium.click("link=Open Site Name");
				selenium.selectWindow("null");
				selenium.waitForText("//tr[contains(., 'Open Site Name')]/td",
					"Open Site Name");
				assertEquals(RuntimeVariables.replace("Open Site Name"),
					selenium.getText("//tr[contains(., 'Open Site Name')]/td"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace("Open Site Name"),
					selenium.getText("//tr[contains(., 'Open Site Name')]/td"));

			case 100:
				label = -1;
			}
		}
	}
}