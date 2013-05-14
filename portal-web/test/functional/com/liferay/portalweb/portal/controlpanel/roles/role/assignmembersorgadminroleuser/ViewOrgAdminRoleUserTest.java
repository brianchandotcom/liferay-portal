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

package com.liferay.portalweb.portal.controlpanel.roles.role.assignmembersorgadminroleuser;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewOrgAdminRoleUserTest extends BaseTestCase {
	public void testViewOrgAdminRoleUser() throws Exception {
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
				selenium.waitForVisible("//input[@name='_125_keywords']");

			case 2:
				selenium.type("//input[@name='_125_keywords']",
					RuntimeVariables.replace("usersn"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]");
				assertEquals(RuntimeVariables.replace("Edit"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]",
					RuntimeVariables.replace("Edit"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isPartialText("//a[@id='_125_rolesLink']",
						"Roles"));
				selenium.clickAt("//a[@id='_125_rolesLink']",
					RuntimeVariables.replace("Roles"));
				assertEquals(RuntimeVariables.replace("Browse"),
					selenium.getText(
						"//div[@class='lfr-portlet-toolbar']/span/a[contains(.,'Browse')]"));
				assertEquals(RuntimeVariables.replace("View Organizations"),
					selenium.getText(
						"//div[@class='lfr-portlet-toolbar']/span/a[contains(.,'View Organizations')]"));
				assertEquals(RuntimeVariables.replace("View Users"),
					selenium.getText(
						"//div[@class='lfr-portlet-toolbar']/span/a[contains(.,'View Users')]"));
				assertEquals(RuntimeVariables.replace("Add"),
					selenium.getText(
						"//div[@class='lfr-portlet-toolbar']/span/ul/li/strong/a[contains(.,'Add')]"));
				assertEquals(RuntimeVariables.replace("Export Users"),
					selenium.getText(
						"//div[@class='lfr-portlet-toolbar']/span/a[contains(.,'Export Users')]"));
				assertEquals(RuntimeVariables.replace("userfn userln"),
					selenium.getText("//h1[@class='header-title']/span"));
				assertEquals(RuntimeVariables.replace("\u00ab Back"),
					selenium.getText("//a[@id='_125_TabsBack']"));
				assertTrue(selenium.isVisible("//img[@class='user-logo']"));
				assertEquals(RuntimeVariables.replace("userfn userln"),
					selenium.getText("//span[@class='user-name']"));
				assertEquals(RuntimeVariables.replace("User Information"),
					selenium.getText("//h3[contains(.,'User Information')]"));
				assertTrue(selenium.isPartialText(
						"//a[@id='_125_detailsLink']", "Details"));
				assertTrue(selenium.isPartialText(
						"//a[@id='_125_passwordLink']", "Password"));
				assertTrue(selenium.isPartialText(
						"//a[@id='_125_organizationsLink']", "Organizations"));
				assertTrue(selenium.isPartialText("//a[@id='_125_sitesLink']",
						"Sites"));
				assertTrue(selenium.isPartialText(
						"//a[@id='_125_userGroupsLink']", "User Groups"));
				assertTrue(selenium.isPartialText("//a[@id='_125_rolesLink']",
						"Roles"));
				assertTrue(selenium.isPartialText(
						"//a[@id='_125_personalSiteLink']", "Personal site"));
				assertTrue(selenium.isPartialText(
						"//a[@id='_125_categorizationLink']", "Categorization"));
				assertEquals(RuntimeVariables.replace("Identification"),
					selenium.getText("//h3[contains(.,'Identification')]"));
				assertTrue(selenium.isPartialText(
						"//a[@id='_125_addressesLink']", "Addresses"));
				assertTrue(selenium.isPartialText(
						"//a[@id='_125_phoneNumbersLink']", "Phone Numbers"));
				assertTrue(selenium.isPartialText(
						"//a[@id='_125_additionalEmailAddressesLink']",
						"Additional Email Addresses"));
				assertTrue(selenium.isPartialText(
						"//a[@id='_125_websitesLink']", "Websites"));
				assertTrue(selenium.isPartialText(
						"//a[@id='_125_instantMessengerLink']",
						"Instant Messenger"));
				assertTrue(selenium.isPartialText(
						"//a[@id='_125_socialNetworkLink']", "Social Network"));
				assertTrue(selenium.isPartialText("//a[@id='_125_smsLink']",
						"SMS"));
				assertTrue(selenium.isPartialText(
						"//a[@id='_125_openIdLink']", "OpenID"));
				assertEquals(RuntimeVariables.replace("Miscellaneous"),
					selenium.getText("//h3[contains(.,'Miscellaneous')]"));
				assertTrue(selenium.isPartialText(
						"//a[@id='_125_announcementsLink']", "Announcements"));
				assertTrue(selenium.isPartialText(
						"//a[@id='_125_displaySettingsLink']",
						"Display Settings"));
				assertTrue(selenium.isPartialText(
						"//a[@id='_125_commentsLink']", "Comments"));
				assertTrue(selenium.isPartialText(
						"//a[@id='_125_customFieldsLink']", "Custom Fields"));
				assertTrue(selenium.isVisible("//input[@value='Save']"));
				assertTrue(selenium.isVisible("//input[@value='Cancel']"));
				assertEquals(RuntimeVariables.replace("Regular Roles"),
					selenium.getText("//h3[contains(.,'Regular Roles')]"));
				assertEquals(RuntimeVariables.replace("Power User"),
					selenium.getText("//tr[contains(.,'Power User')]/td[1]"));
				assertEquals(RuntimeVariables.replace("Remove"),
					selenium.getText(
						"//tr[contains(.,'Power User')]/td[2]/a/span/span"));
				assertEquals(RuntimeVariables.replace("Select"),
					selenium.getText("//div[6]/span[1]/a/span"));
				assertEquals(RuntimeVariables.replace("Inherited Roles"),
					selenium.getText("//h3[contains(.,'Inherited Roles')]"));
				assertEquals(RuntimeVariables.replace("Organization Roles"),
					selenium.getText("//h3[contains(.,'Organization Roles')]"));
				assertEquals(RuntimeVariables.replace(
						"Organization Administrator"),
					selenium.getText(
						"//td[@id='_125_organizationRolesSearchContainer_col-title_row-1']"));
				assertEquals(RuntimeVariables.replace(
						"Organization Administrator"),
					selenium.getText(
						"//div[contains(@id,'organizationRolesSearchContainer')]/table/tbody/tr[contains(.,'Organization Administrator')]/td[1]"));
				assertEquals(RuntimeVariables.replace("Organization1 Name"),
					selenium.getText(
						"//div[contains(@id,'organizationRolesSearchContainer')]/table/tbody/tr[contains(.,'Organization Administrator')]/td[2]"));
				assertEquals(RuntimeVariables.replace("Remove"),
					selenium.getText(
						"//div[contains(@id,'organizationRolesSearchContainer')]/table/tbody/tr[contains(.,'Organization Administrator')]/td[3]/a/span/span"));
				assertEquals(RuntimeVariables.replace("Select"),
					selenium.getText("//div[6]/span[2]/a/span"));
				assertEquals(RuntimeVariables.replace("Site Roles"),
					selenium.getText("//h3[contains(.,'Site Roles')]"));

			case 100:
				label = -1;
			}
		}
	}
}