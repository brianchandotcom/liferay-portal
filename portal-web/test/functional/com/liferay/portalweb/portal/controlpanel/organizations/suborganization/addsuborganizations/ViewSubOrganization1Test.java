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

package com.liferay.portalweb.portal.controlpanel.organizations.suborganization.addsuborganizations;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewSubOrganization1Test extends BaseTestCase {
	public void testViewSubOrganization1() throws Exception {
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
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("Suborganization1*"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Suborganization1 Name"),
			selenium.getText(
				"//tr[contains(.,'Suborganization1 Name')]/td[2]/a/strong"));
		assertEquals(RuntimeVariables.replace("Regular Organization"),
			selenium.getText(
				"//tr[contains(.,'Suborganization1 Name')]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
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
		assertEquals(RuntimeVariables.replace("Suborganization1 Name"),
			selenium.getText("//h1[@class='header-title']"));
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
		assertEquals(RuntimeVariables.replace("Suborganization1 Name"),
			selenium.getText(
				"//h1[@class='header-title']/span[contains(.,'Suborganization1 Name')]"));
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("//span[@class='header-back-to']/a"));
		assertEquals(RuntimeVariables.replace("Suborganization1 Name"),
			selenium.getText("//span[@class='organization-name']"));
		assertEquals(RuntimeVariables.replace("Organization Information"),
			selenium.getText(
				"//div[@class='menu-group']/h3[contains(.,'Organization Information')]"));
		assertTrue(selenium.isPartialText("//a[@id='_125_detailsLink']",
				"Details"));
		assertTrue(selenium.isPartialText(
				"//a[@id='_125_organizationSiteLink']", "Organization Site"));
		assertTrue(selenium.isPartialText(
				"//a[@id='_125_categorizationLink']", "Categorization"));
		assertEquals(RuntimeVariables.replace("Identification"),
			selenium.getText(
				"//div[@class='menu-group']/h3[contains(.,'Identification')]"));
		assertTrue(selenium.isPartialText("//a[@id='_125_addressesLink']",
				"Addresses"));
		assertTrue(selenium.isPartialText("//a[@id='_125_phoneNumbersLink']",
				"Phone Numbers"));
		assertTrue(selenium.isPartialText(
				"//a[@id='_125_additionalEmailAddressesLink']",
				"Additional Email Addresses"));
		assertTrue(selenium.isPartialText("//a[@id='_125_websitesLink']",
				"Websites"));
		assertTrue(selenium.isPartialText("//a[@id='_125_servicesLink']",
				"Services"));
		assertEquals(RuntimeVariables.replace("Miscellaneous"),
			selenium.getText(
				"//div[@class='menu-group']/h3[contains(.,'Miscellaneous')]"));
		assertTrue(selenium.isPartialText("//a[@id='_125_commentsLink']",
				"Comments"));
		assertTrue(selenium.isPartialText(
				"//a[@id='_125_reminderQueriesLink']", "Reminder Queries"));
		assertTrue(selenium.isPartialText("//a[@id='_125_customFieldsLink']",
				"Custom Fields"));
		assertTrue(selenium.isVisible("//input[@value='Save']"));
		assertTrue(selenium.isVisible("//input[@value='Cancel']"));
		assertEquals(RuntimeVariables.replace("Details"),
			selenium.getText(
				"//div[@id='_125_details']/h3[contains(.,'Details')]"));
		assertEquals(RuntimeVariables.replace("Name (Required)"),
			selenium.getText("//label[contains(.,'Name (Required)')]"));
		assertEquals("Suborganization1 Name",
			selenium.getValue("//input[@id='_125_name']"));
		assertEquals(RuntimeVariables.replace("Type"),
			selenium.getText("//label[contains(.,'Type')]"));
		assertTrue(selenium.isPartialText(
				"//div[@id='_125_details']/fieldset/div/div/div",
				"Regular Organization"));
		assertEquals(RuntimeVariables.replace("Site ID"),
			selenium.getText("//label[contains(.,'Site ID')]"));
		assertTrue(selenium.isVisible("//img[@class='avatar']"));
		assertEquals(RuntimeVariables.replace("Change"),
			selenium.getText("//span[@class='edit-logo-link']/a/span"));
		assertEquals(RuntimeVariables.replace("Parent Organization"),
			selenium.getText(
				"//div[@id='_125_details']/h3[contains(.,'Parent Organization')]"));
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[1]"));
		assertEquals(RuntimeVariables.replace("Type"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[2]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[3]"));
		assertEquals(RuntimeVariables.replace("Organization Name"),
			selenium.getText("//tr[contains(.,'Organization Name')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Regular Organization"),
			selenium.getText("//tr[contains(.,'Organization Name')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Remove"),
			selenium.getText(
				"//tr[contains(.,'Organization Name')]/td[3]/a/span/span"));
		assertEquals(RuntimeVariables.replace("Select"),
			selenium.getText("//span[@class='modify-link']/a/span"));
	}
}