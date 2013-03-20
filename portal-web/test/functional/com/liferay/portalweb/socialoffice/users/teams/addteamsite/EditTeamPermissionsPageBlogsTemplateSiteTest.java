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

package com.liferay.portalweb.socialoffice.users.teams.addteamsite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditTeamPermissionsPageBlogsTemplateSiteTest extends BaseTestCase {
	public void testEditTeamPermissionsPageBlogsTemplateSite()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
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
				assertEquals(RuntimeVariables.replace("Blogs Test Page"),
					selenium.getText(
						"//nav/ul/li[contains(.,'Blogs Test Page')]/a/span"));
				selenium.clickAt("//nav/ul/li[contains(.,'Blogs Test Page')]/a/span",
					RuntimeVariables.replace("Blogs Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/dockbar_underlay.js')]");
				assertEquals(RuntimeVariables.replace("Manage"),
					selenium.getText("//li[@id='_145_manageContent']/a/span"));
				selenium.mouseOver("//li[@id='_145_manageContent']/a/span");
				selenium.waitForVisible("//a[@title='Manage Page']");
				assertEquals(RuntimeVariables.replace("Page"),
					selenium.getText("//a[@title='Manage Page']"));
				selenium.clickAt("//a[@title='Manage Page']",
					RuntimeVariables.replace("Page"));
				selenium.waitForVisible("//iframe[@id='manageContentDialog']");
				selenium.selectFrame("//iframe[@id='manageContentDialog']");
				Thread.sleep(1000);
				selenium.waitForVisible("//button[contains(.,'Permissions')]");
				assertEquals(RuntimeVariables.replace("Permissions"),
					selenium.getText("//button[contains(.,'Permissions')]"));
				selenium.clickAt("//button[contains(.,'Permissions')]",
					RuntimeVariables.replace("Permissions"));
				selenium.selectFrame("relative=top");
				selenium.waitForVisible(
					"//iframe[contains(@id,'page_permissions')]");
				selenium.selectFrame(
					"//iframe[contains(@id,'page_permissions')]");
				selenium.waitForVisible("//input[@id='guest_ACTION_VIEW']");

				boolean guestActionViewChecked = selenium.isChecked(
						"//input[@id='guest_ACTION_VIEW']");

				if (!guestActionViewChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='guest_ACTION_VIEW']",
					RuntimeVariables.replace("Guest View Checkbox"));

			case 2:
				assertFalse(selenium.isChecked(
						"//input[@id='guest_ACTION_VIEW']"));

				boolean siteMemberActionViewChecked = selenium.isChecked(
						"//input[@id='site-member_ACTION_VIEW']");

				if (!siteMemberActionViewChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@id='site-member_ACTION_VIEW']",
					RuntimeVariables.replace("Site Member View Checkbox"));

			case 3:
				assertFalse(selenium.isChecked(
						"//input[@id='site-member_ACTION_VIEW']"));

				boolean siteMemberUpdateDiscussionChecked = selenium.isChecked(
						"//input[@id='site-member_ACTION_UPDATE_DISCUSSION']");

				if (!siteMemberUpdateDiscussionChecked) {
					label = 4;

					continue;
				}

				selenium.clickAt("//input[@id='site-member_ACTION_UPDATE_DISCUSSION']",
					RuntimeVariables.replace(
						"Site Member Update Discussion Checkbox"));

			case 4:
				assertFalse(selenium.isChecked(
						"//input[@id='site-member_ACTION_UPDATE_DISCUSSION']"));

				boolean siteMemberUpdateChecked = selenium.isChecked(
						"//input[@id='site-member_ACTION_UPDATE']");

				if (!siteMemberUpdateChecked) {
					label = 5;

					continue;
				}

				selenium.clickAt("//input[@id='site-member_ACTION_UPDATE']",
					RuntimeVariables.replace("Site Member Update Checkbox"));

			case 5:
				assertFalse(selenium.isChecked(
						"//input[@id='site-member_ACTION_UPDATE']"));

				boolean siteMemberPermissionsChecked = selenium.isChecked(
						"//input[@id='site-member_ACTION_PERMISSIONS']");

				if (!siteMemberPermissionsChecked) {
					label = 6;

					continue;
				}

				selenium.clickAt("//input[@id='site-member_ACTION_PERMISSIONS']",
					RuntimeVariables.replace("Site Member Permissions Checkbox"));

			case 6:
				assertFalse(selenium.isChecked(
						"//input[@id='site-member_ACTION_PERMISSIONS']"));

				boolean siteMemberDeleteDiscussionChecked = selenium.isChecked(
						"//input[@id='site-member_ACTION_DELETE_DISCUSSION']");

				if (!siteMemberDeleteDiscussionChecked) {
					label = 7;

					continue;
				}

				selenium.clickAt("//input[@id='site-member_ACTION_DELETE_DISCUSSION']",
					RuntimeVariables.replace(
						"Site Member Delete Discussion Checkbox"));

			case 7:
				assertFalse(selenium.isChecked(
						"//input[@id='site-member_ACTION_DELETE_DISCUSSION']"));

				boolean siteMemberDeleteChecked = selenium.isChecked(
						"//input[@id='site-member_ACTION_DELETE']");

				if (!siteMemberDeleteChecked) {
					label = 8;

					continue;
				}

				selenium.clickAt("//input[@id='site-member_ACTION_DELETE']",
					RuntimeVariables.replace("Site Member Delete Checkbox"));

			case 8:
				assertFalse(selenium.isChecked(
						"//input[@id='site-member_ACTION_DELETE']"));

				boolean siteMemberCustomizeChecked = selenium.isChecked(
						"//input[@id='site-member_ACTION_CUSTOMIZE']");

				if (!siteMemberCustomizeChecked) {
					label = 9;

					continue;
				}

				selenium.clickAt("//input[@id='site-member_ACTION_CUSTOMIZE']",
					RuntimeVariables.replace("Site Member Customize Checkbox"));

			case 9:
				assertFalse(selenium.isChecked(
						"//input[@id='site-member_ACTION_CUSTOMIZE']"));

				boolean siteMemberConfigureApplicationsChecked = selenium.isChecked(
						"//input[@id='site-member_ACTION_CONFIGURE_PORTLETS']");

				if (!siteMemberConfigureApplicationsChecked) {
					label = 10;

					continue;
				}

				selenium.clickAt("//input[@id='site-member_ACTION_CONFIGURE_PORTLETS']",
					RuntimeVariables.replace(
						"Site Member Configure Applications Checkbox"));

			case 10:
				assertFalse(selenium.isChecked(
						"//input[@id='site-member_ACTION_CONFIGURE_PORTLETS']"));

				boolean siteMemberAddPageChecked = selenium.isChecked(
						"//input[@id='site-member_ACTION_ADD_LAYOUT']");

				if (!siteMemberAddPageChecked) {
					label = 11;

					continue;
				}

				selenium.clickAt("//input[@id='site-member_ACTION_ADD_LAYOUT']",
					RuntimeVariables.replace("Site Member Add Page Checkbox"));

			case 11:
				assertFalse(selenium.isChecked(
						"//input[@id='site-member_ACTION_ADD_LAYOUT']"));

				boolean siteMemberAddDiscussionChecked = selenium.isChecked(
						"//input[@id='site-member_ACTION_ADD_DISCUSSION']");

				if (!siteMemberAddDiscussionChecked) {
					label = 12;

					continue;
				}

				selenium.clickAt("//input[@id='site-member_ACTION_ADD_DISCUSSION']",
					RuntimeVariables.replace(
						"Site Member Add Discussion Checkbox"));

			case 12:
				assertFalse(selenium.isChecked(
						"//input[@id='site-member_ACTION_ADD_DISCUSSION']"));

				boolean openSiteTeamNameActionViewChecked = selenium.isChecked(
						"//tr[contains(@class,'role-team')]/td/input[contains(@id,'ACTION_VIEW')]");

				if (openSiteTeamNameActionViewChecked) {
					label = 13;

					continue;
				}

				selenium.clickAt("//tr[contains(@class,'role-team')]/td/input[contains(@id,'ACTION_VIEW')]",
					RuntimeVariables.replace(
						"Open Site Team Name View Checkbox"));

			case 13:
				assertTrue(selenium.isChecked(
						"//tr[contains(@class,'role-team')]/td/input[contains(@id,'ACTION_VIEW')]"));

				boolean openSiteTeamNameUpdateDiscussionChecked = selenium.isChecked(
						"//tr[contains(@class,'role-team')]/td/input[contains(@id,'ACTION_UPDATE_DISCUSSION')]");

				if (openSiteTeamNameUpdateDiscussionChecked) {
					label = 14;

					continue;
				}

				selenium.clickAt("//tr[contains(@class,'role-team')]/td/input[contains(@id,'ACTION_UPDATE_DISCUSSION')]",
					RuntimeVariables.replace(
						"Open Site Team Name Update Discussion Checkbox"));

			case 14:
				assertTrue(selenium.isChecked(
						"//tr[contains(@class,'role-team')]/td/input[contains(@id,'ACTION_UPDATE_DISCUSSION')]"));

				boolean openSiteTeamNameUpdateChecked = selenium.isChecked(
						"//tr[contains(@class,'role-team')]/td/input[contains(@id,'ACTION_UPDATE')]");

				if (openSiteTeamNameUpdateChecked) {
					label = 15;

					continue;
				}

				selenium.clickAt("//tr[contains(@class,'role-team')]/td/input[contains(@id,'ACTION_UPDATE')]",
					RuntimeVariables.replace(
						"Open Site Team Name Update Checkbox"));

			case 15:
				assertTrue(selenium.isChecked(
						"//tr[contains(@class,'role-team')]/td/input[contains(@id,'ACTION_UPDATE')]"));

				boolean openSiteTeamNamePermissionsChecked = selenium.isChecked(
						"//tr[contains(@class,'role-team')]/td/input[contains(@id,'ACTION_PERMISSIONS')]");

				if (openSiteTeamNamePermissionsChecked) {
					label = 16;

					continue;
				}

				selenium.clickAt("//tr[contains(@class,'role-team')]/td/input[contains(@id,'ACTION_PERMISSIONS')]",
					RuntimeVariables.replace(
						"Open Site Team Name Permissions Checkbox"));

			case 16:
				assertTrue(selenium.isChecked(
						"//tr[contains(@class,'role-team')]/td/input[contains(@id,'ACTION_PERMISSIONS')]"));

				boolean openSiteTeamNameDeleteDiscussionChecked = selenium.isChecked(
						"//tr[contains(@class,'role-team')]/td/input[contains(@id,'ACTION_DELETE_DISCUSSION')]");

				if (openSiteTeamNameDeleteDiscussionChecked) {
					label = 17;

					continue;
				}

				selenium.clickAt("//tr[contains(@class,'role-team')]/td/input[contains(@id,'ACTION_DELETE_DISCUSSION')]",
					RuntimeVariables.replace(
						"Open Site Team Name Delete Discussion Checkbox"));

			case 17:
				assertTrue(selenium.isChecked(
						"//tr[contains(@class,'role-team')]/td/input[contains(@id,'ACTION_DELETE_DISCUSSION')]"));

				boolean openSiteTeamNameDeleteChecked = selenium.isChecked(
						"//tr[contains(@class,'role-team')]/td/input[contains(@id,'ACTION_DELETE')]");

				if (openSiteTeamNameDeleteChecked) {
					label = 18;

					continue;
				}

				selenium.clickAt("//tr[contains(@class,'role-team')]/td/input[contains(@id,'ACTION_DELETE')]",
					RuntimeVariables.replace(
						"Open Site Team Name Delete Checkbox"));

			case 18:
				assertTrue(selenium.isChecked(
						"//tr[contains(@class,'role-team')]/td/input[contains(@id,'ACTION_DELETE')]"));

				boolean openSiteTeamNameCustomizeChecked = selenium.isChecked(
						"//tr[contains(@class,'role-team')]/td/input[contains(@id,'ACTION_CUSTOMIZE')]");

				if (openSiteTeamNameCustomizeChecked) {
					label = 19;

					continue;
				}

				selenium.clickAt("//tr[contains(@class,'role-team')]/td/input[contains(@id,'ACTION_CUSTOMIZE')]",
					RuntimeVariables.replace(
						"Open Site Team Name Customize Checkbox"));

			case 19:
				assertTrue(selenium.isChecked(
						"//tr[contains(@class,'role-team')]/td/input[contains(@id,'ACTION_CUSTOMIZE')]"));

				boolean openSiteTeamNameConfigureApplicationsChecked = selenium.isChecked(
						"//tr[contains(@class,'role-team')]/td/input[contains(@id,'ACTION_CONFIGURE_PORTLETS')]");

				if (openSiteTeamNameConfigureApplicationsChecked) {
					label = 20;

					continue;
				}

				selenium.clickAt("//tr[contains(@class,'role-team')]/td/input[contains(@id,'ACTION_CONFIGURE_PORTLETS')]",
					RuntimeVariables.replace(
						"Open Site Team Name Configure Applications Checkbox"));

			case 20:
				assertTrue(selenium.isChecked(
						"//tr[contains(@class,'role-team')]/td/input[contains(@id,'ACTION_CONFIGURE_PORTLETS')]"));

				boolean openSiteTeamNameAddPageChecked = selenium.isChecked(
						"//tr[contains(@class,'role-team')]/td/input[contains(@id,'ACTION_ADD_LAYOUT')]");

				if (openSiteTeamNameAddPageChecked) {
					label = 21;

					continue;
				}

				selenium.clickAt("//tr[contains(@class,'role-team')]/td/input[contains(@id,'ACTION_ADD_LAYOUT')]",
					RuntimeVariables.replace(
						"Open Site Team Name Add Page Checkbox"));

			case 21:
				assertTrue(selenium.isChecked(
						"//tr[contains(@class,'role-team')]/td/input[contains(@id,'ACTION_ADD_LAYOUT')]"));

				boolean openSiteTeamNameAddDiscussionChecked = selenium.isChecked(
						"//tr[contains(@class,'role-team')]/td/input[contains(@id,'ACTION_ADD_DISCUSSION')]");

				if (openSiteTeamNameAddDiscussionChecked) {
					label = 22;

					continue;
				}

				selenium.clickAt("//tr[contains(@class,'role-team')]/td/input[contains(@id,'ACTION_ADD_DISCUSSION')]",
					RuntimeVariables.replace(
						"Open Site Team Name Add Discussion Checkbox"));

			case 22:
				assertTrue(selenium.isChecked(
						"//tr[contains(@class,'role-team')]/td/input[contains(@id,'ACTION_ADD_DISCUSSION')]"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.selectFrame("relative=top");

			case 100:
				label = -1;
			}
		}
	}
}