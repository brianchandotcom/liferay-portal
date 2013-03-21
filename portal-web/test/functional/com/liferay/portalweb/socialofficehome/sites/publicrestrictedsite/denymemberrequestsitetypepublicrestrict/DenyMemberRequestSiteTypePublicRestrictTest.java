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

package com.liferay.portalweb.socialofficehome.sites.publicrestrictedsite.denymemberrequestsitetypepublicrestrict;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DenyMemberRequestSiteTypePublicRestrictTest extends BaseTestCase {
	public void testDenyMemberRequestSiteTypePublicRestrict()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/so/dashboard/");
		assertEquals(RuntimeVariables.replace("Sites"),
			selenium.getText("//div[@id='so-sidebar']/h3"));
		assertTrue(selenium.isVisible("//input[@class='search-input']"));
		selenium.type("//input[@class='search-input']",
			RuntimeVariables.replace("Public Restricted"));
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Public Restricted Site Name"),
			selenium.getText(
				"//li[contains(@class, 'social-office-enabled')]/span[2]/a"));
		selenium.clickAt("//li[contains(@class, 'social-office-enabled')]/span[2]/a",
			RuntimeVariables.replace("Public Restricted Site Name"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Manage"),
			selenium.getText("//li[@id='_145_manageContent']/a/span"));
		selenium.mouseOver("//li[@id='_145_manageContent']/a/span");
		selenium.waitForVisible("//li[contains(.,'Site Memberships')]/a");
		assertEquals(RuntimeVariables.replace("Site Memberships"),
			selenium.getText("//li[contains(.,'Site Memberships')]/a"));
		selenium.clickAt("//li[contains(.,'Site Memberships')]/a",
			RuntimeVariables.replace("Site Memberships"));
		selenium.waitForVisible("//iframe[@id='manageContentDialog']");
		selenium.selectFrame("//iframe[@id='manageContentDialog']");
		selenium.waitForVisible(
			"//div[@class='site-membership-type']/span[3]/a");
		assertEquals(RuntimeVariables.replace(
				"There are 1 membership requests pending."),
			selenium.getText("//div[@class='site-membership-type']/span[3]/a"));
		selenium.clickAt("//div[@class='site-membership-type']/span[3]/a",
			RuntimeVariables.replace("There are 1 membership requests pending."));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Pending"),
			selenium.getText("link=Pending"));
		selenium.clickAt("link=Pending", RuntimeVariables.replace("Pending"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText(
				"//td[contains(@id,'col-user_row-1')]",
				"Social01 Office01 User01"));
		assertTrue(selenium.isPartialText(
				"//td[contains(@id,'col-user_row-1')]",
				"(socialoffice01@liferay.com)"));
		assertEquals(RuntimeVariables.replace(
				"Social01 Office01 User01 wishes to join Public Restricted Site Name."),
			selenium.getText("//td[contains(@id,'col-user-comments_row-1')]"));
		assertEquals(RuntimeVariables.replace("Reply"),
			selenium.getText("//a[contains(@id,'menu_reply')]"));
		selenium.clickAt("//a[contains(@id,'menu_reply')]",
			RuntimeVariables.replace("Reply"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Reply Membership Request for Public Restricted Site Name"),
			selenium.getText("//h1[@class='header-title']"));
		selenium.select("//select[@id='_174_statusId']",
			RuntimeVariables.replace("Deny"));
		selenium.type("//textarea[@id='_174_replyComments']",
			RuntimeVariables.replace(
				"Public Restricted Site Name Membership Denied"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("xPath=(//div[@class='portlet-msg-success'])[1]"));
		assertEquals(RuntimeVariables.replace(
				"Your reply will be sent to the user by email."),
			selenium.getText("xPath=(//div[@class='portlet-msg-success'])[2]"));
		assertEquals(RuntimeVariables.replace("Denied"),
			selenium.getText("link=Denied"));
		selenium.clickAt("link=Denied", RuntimeVariables.replace("Denied"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText(
				"//td[contains(@id,'col-user_row-1')]",
				"Social01 Office01 User01"));
		assertTrue(selenium.isPartialText(
				"//td[contains(@id,'col-user_row-1')]",
				"(socialoffice01@liferay.com)"));
		assertEquals(RuntimeVariables.replace(
				"Social01 Office01 User01 wishes to join Public Restricted Site Name."),
			selenium.getText("//td[contains(@id,'col-user-comments_row-1')]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//td[contains(@id,'col-replier_row-1')]"));
		assertEquals(RuntimeVariables.replace(
				"Public Restricted Site Name Membership Denied"),
			selenium.getText("//td[contains(@id,'col-reply-comments_row-1')]"));
		selenium.selectFrame("relative=top");
	}
}