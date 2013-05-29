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

package com.liferay.portalweb.socialofficeprofile.profile.addusersoprofilepicture;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddUserSOProfilePictureTest extends BaseTestCase {
	public void testAddUserSOProfilePicture() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Users and Organizations",
			RuntimeVariables.replace("Users and Organizations"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Search All Users",
			RuntimeVariables.replace("Search All Users"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_125_toggle_id_users_admin_user_searchkeywords']",
			RuntimeVariables.replace("socialoffice01"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Social01"),
			selenium.getText("//a[contains(.,'Social01')]"));
		selenium.clickAt("//a[contains(.,'Social01')]",
			RuntimeVariables.replace("Social01"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText("//div[2]/h1/span"));
		selenium.waitForVisible("//span[@class='edit-logo-link']/a");
		assertEquals(RuntimeVariables.replace("Change"),
			selenium.getText("//span[@class='edit-logo-link']/a"));
		selenium.clickAt("//span[@class='edit-logo-link']/a",
			RuntimeVariables.replace("Change"));
		Thread.sleep(1000);
		selenium.selectWindow("title=Users and Organizations");
		selenium.waitForText("//label[@for='_125_fileName']",
			"Upload a GIF or JPEG that is 120 pixels tall and 100 pixels wide.");
		assertEquals(RuntimeVariables.replace(
				"Upload a GIF or JPEG that is 120 pixels tall and 100 pixels wide."),
			selenium.getText("//label[@for='_125_fileName']"));
		selenium.uploadCommonFile("//input[@id='_125_fileName']",
			RuntimeVariables.replace("Document_4.jpg"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.selectWindow("null");
		selenium.waitForVisible("//a[contains(@id,'deleteLogoLink')]");
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}