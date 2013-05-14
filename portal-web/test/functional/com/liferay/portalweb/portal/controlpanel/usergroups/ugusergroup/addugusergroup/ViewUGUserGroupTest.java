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

package com.liferay.portalweb.portal.controlpanel.usergroups.ugusergroup.addugusergroup;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewUGUserGroupTest extends BaseTestCase {
	public void testViewUGUserGroup() throws Exception {
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
		assertEquals(RuntimeVariables.replace(
				"User groups provide a way to group users independently of the organizations to which they belong. Administrators can define a user group and assign the user group as a member of a site to make all of its users members automatically. Or disable for all portlets."),
			selenium.getText("//div[@id='show-portlet-description-127']/div"));
		assertEquals(RuntimeVariables.replace("View All"),
			selenium.getText("//div[@class='lfr-portlet-toolbar']/span[1]/a"));
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//div[@class='lfr-portlet-toolbar']/span[2]/a"));
		assertEquals(RuntimeVariables.replace("User Groups"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertTrue(selenium.isVisible("//input[@id='_127_keywords']"));
		assertTrue(selenium.isVisible("//input[@value='Search']"));
		assertTrue(selenium.isVisible("//input[@value='Delete']"));
		assertTrue(selenium.isVisible("//tr[1]/th[1]/input"));
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText("//tr[1]/th[2]/span/a"));
		assertEquals(RuntimeVariables.replace("Description"),
			selenium.getText("//tr[1]/th[3]/span/a"));
		assertTrue(selenium.isVisible("//tr[3]/td[1]/input"));
		assertEquals(RuntimeVariables.replace("UG UserGroup Name"),
			selenium.getText("//tr[3]/td[2]/a"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText("//tr[3]/td[3]"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[3]/td[4]/span[@title='Actions']/ul/li/strong/a/span"));
		assertEquals(RuntimeVariables.replace("Showing 1 result."),
			selenium.getText("//div[@class='search-results']"));
		selenium.clickAt("//tr[3]/td[2]/a",
			RuntimeVariables.replace("UG UserGroup Name"));
		selenium.waitForPageToLoad("30000");
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
		assertEquals(RuntimeVariables.replace(
				"User Group Site The site of a user group cannot be accessed directly by end users. The pages of a user group will be shown automatically as part of the public or private pages of the personal site of each user who belongs to the user group. To allow users to make changes, enable the customization options of each page."),
			selenium.getText("//span[@class='legend']"));
		assertEquals(RuntimeVariables.replace("Public Pages"),
			selenium.getText("//fieldset[2]/div/span[1]/span/label"));
		assertTrue(selenium.isVisible(
				"//select[@id='_127_publicLayoutSetPrototypeId']"));
		assertEquals(RuntimeVariables.replace("None"),
			selenium.getText(
				"//select[@id='_127_publicLayoutSetPrototypeId']/option[1]"));
		assertEquals(RuntimeVariables.replace("Community Site"),
			selenium.getText(
				"//select[@id='_127_publicLayoutSetPrototypeId']/option[2]"));
		assertEquals(RuntimeVariables.replace("Intranet Site"),
			selenium.getText(
				"//select[@id='_127_publicLayoutSetPrototypeId']/option[3]"));
		assertEquals(RuntimeVariables.replace("Private Pages"),
			selenium.getText("//fieldset[2]/div/span[2]/span/label"));
		assertTrue(selenium.isVisible(
				"//select[@id='_127_privateLayoutSetPrototypeId']"));
		assertTrue(selenium.isVisible(
				"//select[@id='_127_privateLayoutSetPrototypeId']/option[1]"));
		assertTrue(selenium.isVisible(
				"//select[@id='_127_privateLayoutSetPrototypeId']/option[2]"));
		assertTrue(selenium.isVisible(
				"//select[@id='_127_privateLayoutSetPrototypeId']/option[3]"));
		assertTrue(selenium.isVisible("//input[@value='Save']"));
		assertTrue(selenium.isVisible("//input[@value='Cancel']"));
	}
}