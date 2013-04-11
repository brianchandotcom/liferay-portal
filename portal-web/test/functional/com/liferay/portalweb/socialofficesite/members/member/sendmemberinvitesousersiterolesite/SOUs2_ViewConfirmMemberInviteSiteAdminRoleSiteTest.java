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

package com.liferay.portalweb.socialofficesite.members.member.sendmemberinvitesousersiterolesite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs2_ViewConfirmMemberInviteSiteAdminRoleSiteTest
	extends BaseTestCase {
	public void testSOUs2_ViewConfirmMemberInviteSiteAdminRoleSite()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/socialoffice02/so/dashboard/");
		selenium.waitForVisible("//li[contains(@class, 'selected')]/a/span");
		assertEquals(RuntimeVariables.replace("Dashboard"),
			selenium.getText("//li[contains(@class, 'selected')]/a/span"));
		assertEquals(RuntimeVariables.replace("Sites"),
			selenium.getText("//div[@id='so-sidebar']/h3"));
		assertTrue(selenium.isPartialText(
				"//select[@id='_5_WAR_soportlet_tabs1']", "My Sites"));
		selenium.select("//select[@id='_5_WAR_soportlet_tabs1']",
			RuntimeVariables.replace("My Sites"));
		selenium.waitForNotPartialText("//ul[contains(@class, 'site-list')]/",
			"liferay.com");
		assertFalse(selenium.isPartialText(
				"//ul[contains(@class, 'site-list')]/", "liferay.com"));
		assertEquals(RuntimeVariables.replace("Open Site Name"),
			selenium.getText(
				"//li[contains(@class, 'social-office-enabled')]/span[2]/a"));
		assertEquals(RuntimeVariables.replace("Sites Directory"),
			selenium.getText("//button[contains(.,'Sites Directory')]/span[2]"));
		selenium.clickAt("//button[contains(.,'Sites Directory')]/span[2]",
			RuntimeVariables.replace("Sites Directory"));
		selenium.waitForVisible("xPath=(//h1[@class='header-title']/span)[1]");
		assertEquals(RuntimeVariables.replace("Directory"),
			selenium.getText("xPath=(//h1[@class='header-title']/span)[1]"));
		selenium.waitForVisible(
			"xPath=(//span[@class='name']/a)[contains(.,'Open Site Name')]");
		assertTrue(selenium.isVisible(
				"xPath=(//span[@class='name']/a)[contains(.,'Open Site Name')]"));
		selenium.select("//span[@class='sites-tabs']/span/span/span/select",
			RuntimeVariables.replace("My Sites"));
		selenium.type("//input[@id='_5_WAR_soportlet_dialogKeywords']",
			RuntimeVariables.replace("Open Site Name"));
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Open Site Name"),
			selenium.getText(
				"xPath=(//span[@class='name']/a)[contains(.,'Open Site Name')]"));
		assertEquals(RuntimeVariables.replace("Open Site Description"),
			selenium.getText(
				"xPath=(//span[@class='description'])[contains(.,'Open Site Description')]"));
		assertTrue(selenium.isVisible("//span[@class='action leave']"));
		selenium.clickAt("xPath=(//span[@class='name']/a)[contains(.,'Open Site Name')]",
			RuntimeVariables.replace("Open Site Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Open Site Name"),
			selenium.getText("//div[@class='community-title']/a/span"));
		assertTrue(selenium.isElementNotPresent(
				"//span[@class='action request']"));
		Thread.sleep(1000);
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//li[@id='_145_addContent']/a/span"));
		assertEquals(RuntimeVariables.replace("Manage"),
			selenium.getText("//li[@id='_145_manageContent']/a/span"));
		assertEquals(RuntimeVariables.replace("Edit Controls"),
			selenium.getText("//li[@id='_145_toggleControls']/a"));
		selenium.clickAt("//li[@id='_145_toggleControls']/a",
			RuntimeVariables.replace("Edit Controls"));
		assertTrue(selenium.isVisible(
				"xPath=(//span[@title='Options']/ul/li/strong/a)[1]"));
		assertTrue(selenium.isVisible(
				"xPath=(//span[contains(@class,'portlet-close-icon')]/a)[1]"));
		assertTrue(selenium.isVisible(
				"xPath=(//span[@title='Options']/ul/li/strong/a)[2]"));
		assertTrue(selenium.isVisible(
				"xPath=(//span[contains(@class,'portlet-close-icon')]/a)[2]"));
		assertTrue(selenium.isVisible(
				"xPath=(//span[@title='Options']/ul/li/strong/a)[3]"));
		assertTrue(selenium.isVisible(
				"xPath=(//span[contains(@class,'portlet-close-icon')]/a)[3]"));
		assertTrue(selenium.isVisible(
				"xPath=(//span[@title='Options']/ul/li/strong/a)[4]"));
		assertTrue(selenium.isVisible(
				"xPath=(//span[contains(@class,'portlet-close-icon')]/a)[4]"));
		assertTrue(selenium.isVisible(
				"xPath=(//span[@title='Options']/ul/li/strong/a)[5]"));
		assertTrue(selenium.isVisible(
				"xPath=(//span[contains(@class,'portlet-close-icon')]/a)[5]"));
		assertTrue(selenium.isVisible(
				"xPath=(//span[@title='Options']/ul/li/strong/a)[6]"));
		assertTrue(selenium.isVisible(
				"xPath=(//span[contains(@class,'portlet-close-icon')]/a)[6]"));
		assertTrue(selenium.isVisible(
				"xPath=(//span[@title='Options']/ul/li/strong/a)[7]"));
		assertTrue(selenium.isVisible(
				"xPath=(//span[contains(@class,'portlet-close-icon')]/a)[7]"));
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText("//nav/ul/li[contains(.,'Home')]/a/span"));
		assertEquals(RuntimeVariables.replace("Calendar"),
			selenium.getText("//nav/ul/li[contains(.,'Calendar')]/a/span"));
		assertEquals(RuntimeVariables.replace("Documents"),
			selenium.getText("//nav/ul/li[contains(.,'Documents')]/a/span"));
		assertEquals(RuntimeVariables.replace("Forums"),
			selenium.getText("//nav/ul/li[contains(.,'Forums')]/a/span"));
		assertEquals(RuntimeVariables.replace("Blogs"),
			selenium.getText("//nav/ul/li[contains(.,'Blogs')]/a/span"));
		assertEquals(RuntimeVariables.replace("Wiki"),
			selenium.getText("//nav/ul/li[contains(.,'Wiki')]/a/span"));
		assertEquals(RuntimeVariables.replace("Members"),
			selenium.getText("//nav/ul/li[contains(.,'Members')]/a/span"));
		selenium.clickAt("//nav/ul/li[contains(.,'Members')]/a/span",
			RuntimeVariables.replace("Members"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_4_WAR_contactsportlet_name']",
			RuntimeVariables.replace("socialoffice02@liferay.com"));
		selenium.waitForText("//div[contains(@class, 'lfr-contact-name')]/a",
			"User02, Social02");
		assertEquals(RuntimeVariables.replace("User02, Social02"),
			selenium.getText("//div[contains(@class, 'lfr-contact-name')]/a"));
		assertEquals(RuntimeVariables.replace("socialoffice02@liferay.com"),
			selenium.getText("//div[contains(@class, 'lfr-contact-extra')]"));
		Thread.sleep(1000);
		selenium.clickAt("//div[contains(@class, 'lfr-contact-name')]/a",
			RuntimeVariables.replace("User02, Social02"));
		selenium.waitForVisible("//div[contains(@class, 'contacts-profile')]");
		assertEquals(RuntimeVariables.replace("Social02 Office02 User02"),
			selenium.getText(
				"//div[contains(@class, 'contacts-profile')]/div/div[2]/div/a"));
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Manage"),
			selenium.getText("//li[@id='_145_manageContent']/a/span"));
		selenium.mouseOver("//li[@id='_145_manageContent']/a/span");
		selenium.waitForVisible("link=Site Memberships");
		selenium.clickAt("link=Site Memberships",
			RuntimeVariables.replace("Site Memberships"));
		selenium.waitForVisible("//iframe[@id='manageContentDialog']");
		selenium.selectFrame("//iframe[@id='manageContentDialog']");
		selenium.waitForVisible(
			"//td[@id='_174_usersSearchContainer_col-name_row-socialoffice01']");
		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText(
				"//td[@id='_174_usersSearchContainer_col-name_row-socialoffice01']"));
		assertEquals(RuntimeVariables.replace("socialoffice01"),
			selenium.getText(
				"//td[@id='_174_usersSearchContainer_col-screen-name_row-socialoffice01']"));
		assertEquals(RuntimeVariables.replace("Site Owner"),
			selenium.getText(
				"//td[@id='_174_usersSearchContainer_col-site-roles_row-socialoffice01']"));
		assertEquals(RuntimeVariables.replace("Social02 Office02 User02"),
			selenium.getText(
				"//td[@id='_174_usersSearchContainer_col-name_row-socialoffice02']"));
		assertEquals(RuntimeVariables.replace("socialoffice02"),
			selenium.getText(
				"//td[@id='_174_usersSearchContainer_col-screen-name_row-socialoffice02']"));
		assertEquals(RuntimeVariables.replace("Site Administrator"),
			selenium.getText(
				"//td[@id='_174_usersSearchContainer_col-site-roles_row-socialoffice02']"));
		selenium.selectFrame("relative=top");
	}
}