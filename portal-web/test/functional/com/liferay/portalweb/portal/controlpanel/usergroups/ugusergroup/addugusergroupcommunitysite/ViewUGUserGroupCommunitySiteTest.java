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

package com.liferay.portalweb.portal.controlpanel.usergroups.ugusergroup.addugusergroupcommunitysite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewUGUserGroupCommunitySiteTest extends BaseTestCase {
	public void testViewUGUserGroupCommunitySite() throws Exception {
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
		selenium.clickAt("link=User Groups",
			RuntimeVariables.replace("User Groups"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("User Groups"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'UG UserGroup Name')]/td[1]/input"));
		assertEquals(RuntimeVariables.replace("UG UserGroup Name"),
			selenium.getText("//tr[contains(.,'UG UserGroup Name')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText("//tr[contains(.,'UG UserGroup Name')]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'UG UserGroup Name')]/td[4]/span[@title='Actions']/ul/li/strong/a/span"));
		assertEquals(RuntimeVariables.replace("Showing 1 result."),
			selenium.getText("//div[@class='search-results']"));
		selenium.clickAt("//tr[contains(.,'UG UserGroup Name')]/td[2]/a",
			RuntimeVariables.replace("UG UserGroup Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("View All"),
			selenium.getText(
				"//div[@class='lfr-portlet-toolbar']/span[contains(.,'View All')]/a"));
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText(
				"//div[@class='lfr-portlet-toolbar']/span[contains(.,'Add')]/a"));
		assertEquals(RuntimeVariables.replace("UG UserGroup Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("//a[@id='_127_TabsBack']"));
		assertEquals(RuntimeVariables.replace("Old Name UG UserGroup Name"),
			selenium.getText("//div[@class='field-wrapper-content']"));
		assertEquals(RuntimeVariables.replace("New Name (Required)"),
			selenium.getText("//span[@class='field-content']/label"));
		assertTrue(selenium.isVisible("//input[@id='_127_name']"));
		assertEquals("UG UserGroup Name",
			selenium.getValue("//input[@id='_127_name']"));
		assertTrue(selenium.isVisible("//textarea[@id='_127_description']"));
		assertTrue(selenium.isPartialText("//span[@class='legend']",
				"User Group Site"));
		assertEquals(RuntimeVariables.replace("Public Pages"),
			selenium.getText("//label[contains(.,'Public Pages')]"));
		assertEquals(RuntimeVariables.replace("Open Pages"),
			selenium.getText("//span/a[@title='(Opens New Window)']/span"));
		assertFalse(selenium.isChecked(
				"//input[@id='_127_publicLayoutSetPrototypeLinkEnabledCheckbox']"));
		assertEquals(RuntimeVariables.replace(
				"Enable propagation of changes from the site template Community Site."),
			selenium.getText(
				"//label[@for='_127_publicLayoutSetPrototypeLinkEnabledCheckbox']"));
		assertEquals(RuntimeVariables.replace("Private Pages"),
			selenium.getText("//label[contains(.,'Private Pages')]"));
		assertEquals("None",
			selenium.getSelectedLabel(
				"//select[@id='_127_privateLayoutSetPrototypeId']"));
		assertTrue(selenium.isVisible("//input[@value='Save']"));
		assertTrue(selenium.isVisible("//input[@value='Cancel']"));

		String openPagesLink = selenium.getAttribute(
				"//span[contains(.,'Open Pages')]/a@href");
		RuntimeVariables.setValue("openPagesLink", openPagesLink);
		selenium.open(RuntimeVariables.getValue("openPagesLink"));
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText("link=Home"));
		assertEquals(RuntimeVariables.replace("Calendar"),
			selenium.getText("link=Calendar"));
		assertEquals(RuntimeVariables.replace("Wiki"),
			selenium.getText("link=Wiki"));
	}
}