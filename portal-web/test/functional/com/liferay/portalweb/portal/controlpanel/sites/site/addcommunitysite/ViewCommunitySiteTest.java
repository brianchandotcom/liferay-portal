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

package com.liferay.portalweb.portal.controlpanel.sites.site.addcommunitysite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewCommunitySiteTest extends BaseTestCase {
	public void testViewCommunitySite() throws Exception {
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
		selenium.clickAt("link=Sites", RuntimeVariables.replace("Sites"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='_134_keywords']",
			RuntimeVariables.replace("Community"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'Community Site Name')]/td[1]/input[@type='checkbox']"));
		assertEquals(RuntimeVariables.replace("Community Site Name"),
			selenium.getText("//tr[contains(.,'Community Site Name')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Open"),
			selenium.getText("//tr[contains(.,'Community Site Name')]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("1 User"),
			selenium.getText("//tr[contains(.,'Community Site Name')]/td[4]"));
		assertEquals(RuntimeVariables.replace("Yes"),
			selenium.getText("//tr[contains(.,'Community Site Name')]/td[5]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText("//tr[contains(.,'Community Site Name')]/td[6]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText("//tr[contains(.,'Community Site Name')]/td[7]"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'Community Site Name')]/td[8]/span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//tr[contains(.,'Community Site Name')]/td[2]/a",
			RuntimeVariables.replace("Community Site Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Sites"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertEquals(RuntimeVariables.replace("Browse"),
			selenium.getText(
				"//div[@class='lfr-portlet-toolbar']/span/a[contains(.,'Browse')]"));
		assertEquals(RuntimeVariables.replace("View All"),
			selenium.getText(
				"//div[@class='lfr-portlet-toolbar']/span/a[contains(.,'View All')]"));
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText(
				"//div[@class='lfr-portlet-toolbar']/span[@title='Add']/ul/li/strong/a/span"));
		assertEquals(RuntimeVariables.replace("Community Site Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("\u00ab Back to Sites Home"),
			selenium.getText("//span[@class='header-back-to']/a"));
		assertEquals(RuntimeVariables.replace("Members:"),
			selenium.getText(
				"//div[@class='membership-info']/dl/dt[@class='first']"));
		assertEquals(RuntimeVariables.replace("1 User"),
			selenium.getText(
				"//div[@class='membership-info']/dl/dd[@class='members-info']/span/a"));
		assertEquals(RuntimeVariables.replace("Membership Type:"),
			selenium.getText("//div[@class='membership-info']/dl/dt/span/span"));
		assertEquals(RuntimeVariables.replace("Open"),
			selenium.getText(
				"//div[@class='membership-info']/dl/dd[@class='last']"));
		assertTrue(selenium.isVisible("//div[@class='lfr-asset-summary']/img"));
		assertEquals(RuntimeVariables.replace("Community Site Name"),
			selenium.getText("//div[@class='lfr-asset-name']/h4"));
		assertEquals(RuntimeVariables.replace("Edit Settings"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Edit Settings')]"));
		assertEquals(RuntimeVariables.replace("Manage Pages"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Manage Pages')]"));
		assertEquals(RuntimeVariables.replace("Manage Memberships"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Manage Memberships')]"));
		assertTrue(selenium.isPartialText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Go to Public Pages')]",
				"Go to Public Pages"));
		assertEquals(RuntimeVariables.replace("Leave"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Leave')]"));
		assertEquals(RuntimeVariables.replace("Deactivate"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Deactivate')]"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Delete')]"));
		assertEquals(RuntimeVariables.replace("Add Blank Site"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Add Blank Site')]"));
		assertEquals(RuntimeVariables.replace("Add Community Site"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Add Community Site')]"));
		assertEquals(RuntimeVariables.replace("Add Intranet Site"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Add Intranet Site')]"));
	}
}