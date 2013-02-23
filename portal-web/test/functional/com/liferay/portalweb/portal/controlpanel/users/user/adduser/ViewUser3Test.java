/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.controlpanel.users.user.adduser;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewUser3Test extends BaseTestCase {
	public void testViewUser3() throws Exception {
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
				selenium.waitForElementPresent("//a[.='\u00ab Basic']");

				boolean basicVisible = selenium.isVisible(
						"//a[.='\u00ab Basic']");

				if (!basicVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//a[.='\u00ab Basic']",
					RuntimeVariables.replace("Basic"));
				selenium.waitForVisible("//input[@name='_125_keywords']");

			case 2:
				selenium.type("//input[@name='_125_keywords']",
					RuntimeVariables.replace("usersn3"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("userfn3"),
					selenium.getText("//tr[contains(.,'userfn3')]/td[2]/a"));
				assertEquals(RuntimeVariables.replace("userln3"),
					selenium.getText("//tr[contains(.,'userfn3')]/td[3]/a"));
				assertEquals(RuntimeVariables.replace("usersn3"),
					selenium.getText("//tr[contains(.,'userfn3')]/td[4]/a"));
				selenium.clickAt("//tr[contains(.,'userfn3')]/td[2]/a",
					RuntimeVariables.replace("userfn3"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("userfn3 userln3"),
					selenium.getText("//h1[@class='header-title']"));
				assertEquals(RuntimeVariables.replace("\u00ab Back"),
					selenium.getText("//a[@id='_125_TabsBack']"));
				assertEquals(RuntimeVariables.replace("Details"),
					selenium.getText("//div[@id='_125_details']/h3"));
				assertEquals("",
					selenium.getValue("//select[@id='_125_prefixId']"));
				assertEquals("usersn3",
					selenium.getValue("//input[@id='_125_screenName']"));
				assertEquals("userea3@liferay.com",
					selenium.getValue("//input[@id='_125_emailAddress']"));
				assertEquals("userfn3",
					selenium.getValue("//input[@id='_125_firstName']"));
				assertEquals("",
					selenium.getValue("//input[@id='_125_middleName']"));
				assertEquals("userln3",
					selenium.getValue("//input[@id='_125_lastName']"));
				assertTrue(selenium.isVisible("//img[@class='avatar']"));
				assertEquals(RuntimeVariables.replace("Change"),
					selenium.getText("//span[@class='edit-logo-link']/a/span"));
				assertEquals("January",
					selenium.getSelectedLabel(
						"//select[@name='_125_birthdayMonth']"));
				assertEquals("1",
					selenium.getSelectedLabel(
						"//select[@name='_125_birthdayDay']"));
				assertEquals("1970",
					selenium.getSelectedLabel(
						"//select[@name='_125_birthdayYear']"));
				assertEquals("Male",
					selenium.getSelectedLabel("//select[@id='_125_male']"));
				assertEquals("",
					selenium.getValue("//input[@id='_125_jobTitle']"));
				assertEquals(RuntimeVariables.replace("userfn3 userln3"),
					selenium.getText("//span[@class='user-name']"));

			case 100:
				label = -1;
			}
		}
	}
}