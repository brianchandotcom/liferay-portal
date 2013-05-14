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

package com.liferay.portalweb.portal.controlpanel.sites.site.removememberssite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RemoveMembersSitesTest extends BaseTestCase {
	public void testRemoveMembersSites() throws Exception {
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
				selenium.clickAt("link=Sites", RuntimeVariables.replace("Sites"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@name='_134_keywords']",
					RuntimeVariables.replace("Site Name"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Memberships')]");
				assertEquals(RuntimeVariables.replace("Manage Memberships"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Memberships')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Memberships')]",
					RuntimeVariables.replace("Manage Memberships"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Users"),
					selenium.getText(
						"//ul[@class='tabview-list']/li/span/a[contains(.,'Users')]"));
				selenium.clickAt("//ul[@class='tabview-list']/li/span/a[contains(.,'Users')]",
					RuntimeVariables.replace("Users"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForElementPresent("//a[.='\u00ab Basic']");

				boolean basicVisible = selenium.isVisible(
						"//a[.='\u00ab Basic']");

				if (!basicVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//a[.='\u00ab Basic']",
					RuntimeVariables.replace("\u00ab Basic"));

			case 2:
				selenium.type("//input[@name='_174_keywords']",
					RuntimeVariables.replace("userfn"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Remove Membership')]");
				assertEquals(RuntimeVariables.replace("Remove Membership"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Remove Membership')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Remove Membership')]",
					RuntimeVariables.replace("Remove Membership"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace(
						"No user was found that is a direct member of this site."),
					selenium.getText("//div[@class='portlet-msg-info']"));

			case 100:
				label = -1;
			}
		}
	}
}