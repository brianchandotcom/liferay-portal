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

package com.liferay.portalweb.socialofficesite.members.member.viewsousermultiplejoinsite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SendMemberInviteSOUserMultipleSiteTest extends BaseTestCase {
	public void testSendMemberInviteSOUserMultipleSite()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/so/dashboard/");
		assertEquals(RuntimeVariables.replace("Sites"),
			selenium.getText("//div[@id='so-sidebar']/h3"));
		assertTrue(selenium.isVisible("//input[@class='search-input']"));
		selenium.type("//input[@class='search-input']",
			RuntimeVariables.replace("Open"));
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Open Site Name"),
			selenium.getText(
				"//li[contains(@class, 'social-office-enabled')]/span[2]/a"));
		selenium.clickAt("//li[contains(@class, 'social-office-enabled')]/span[2]/a",
			RuntimeVariables.replace("Open Site Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Open Site Name"),
			selenium.getText("//div[@class='community-title']/a/span"));
		assertEquals(RuntimeVariables.replace("Members"),
			selenium.getText("//nav/ul/li[contains(.,'Members')]/a/span"));
		selenium.clickAt("//nav/ul/li[contains(.,'Members')]/a/span",
			RuntimeVariables.replace("Members"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Invite members to this site."),
			selenium.getText(
				"//a[contains(text(),'Invite members to this site.')]"));
		selenium.clickAt("//a[contains(text(),'Invite members to this site.')]",
			RuntimeVariables.replace("Invite members to this site."));
		selenium.waitForVisible("//div[@class='search']");
		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText(
				"xPath=(//div[contains(@class, 'user')]/span)[contains(.,'Social01 Office01 User01')]"));
		selenium.clickAt("xPath=(//div[contains(@class, 'user')]/span)[contains(.,'Social01 Office01 User01')]",
			RuntimeVariables.replace("Social01 Office01 User01"));
		selenium.waitForVisible(
			"xPath=(//div[@class='user-invited']/div/div)[contains(.,'Social01 Office01 User01')]");
		assertEquals(RuntimeVariables.replace(
				"Social01 Office01 User01 socialoffice01@liferay.com"),
			selenium.getText(
				"xPath=(//div[@class='user-invited']/div/div)[contains(.,'Social01 Office01 User01')]"));
		assertEquals(RuntimeVariables.replace("Social02 Office02 User02"),
			selenium.getText(
				"xPath=(//div[contains(@class, 'user')]/span)[contains(.,'Social02 Office02 User02')]"));
		selenium.clickAt("xPath=(//div[contains(@class, 'user')]/span)[contains(.,'Social02 Office02 User02')]",
			RuntimeVariables.replace("Social02 Office02 User02"));
		selenium.waitForVisible(
			"xPath=(//div[@class='user-invited']/div/div)[contains(.,'Social02 Office02 User02')]");
		assertEquals(RuntimeVariables.replace(
				"Social02 Office02 User02 socialoffice02@liferay.com"),
			selenium.getText(
				"xPath=(//div[@class='user-invited']/div/div)[contains(.,'Social02 Office02 User02')]"));
		assertEquals(RuntimeVariables.replace("Social03 Office03 User03"),
			selenium.getText(
				"xPath=(//div[contains(@class, 'user')]/span)[contains(.,'Social03 Office03 User03')]"));
		selenium.clickAt("xPath=(//div[contains(@class, 'user')]/span)[contains(.,'Social03 Office03 User03')]",
			RuntimeVariables.replace("Social03 Office03 User03"));
		selenium.waitForVisible(
			"xPath=(//div[@class='user-invited']/div/div)[contains(.,'Social03 Office03 User03')]");
		assertEquals(RuntimeVariables.replace(
				"Social03 Office03 User03 socialoffice03@liferay.com"),
			selenium.getText(
				"xPath=(//div[@class='user-invited']/div/div)[contains(.,'Social03 Office03 User03')]"));
		assertEquals(RuntimeVariables.replace("Social04 Office04 User04"),
			selenium.getText(
				"xPath=(//div[contains(@class, 'user')]/span)[contains(.,'Social04 Office04 User04')]"));
		selenium.clickAt("xPath=(//div[contains(@class, 'user')]/span)[contains(.,'Social04 Office04 User04')]",
			RuntimeVariables.replace("Social04 Office04 User04"));
		selenium.waitForVisible(
			"xPath=(//div[@class='user-invited']/div/div)[contains(.,'Social04 Office04 User04')]");
		assertEquals(RuntimeVariables.replace(
				"Social04 Office04 User04 socialoffice04@liferay.com"),
			selenium.getText(
				"xPath=(//div[@class='user-invited']/div/div)[contains(.,'Social04 Office04 User04')]"));
		assertEquals("Send Invitations",
			selenium.getValue("//input[@value='Send Invitations']"));
		selenium.clickAt("//input[@value='Send Invitations']",
			RuntimeVariables.replace("Send Invitations"));
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}