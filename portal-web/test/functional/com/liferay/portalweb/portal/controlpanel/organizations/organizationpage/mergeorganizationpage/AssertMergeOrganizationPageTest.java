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

package com.liferay.portalweb.portal.controlpanel.organizations.organizationpage.mergeorganizationpage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssertMergeOrganizationPageTest extends BaseTestCase {
	public void testAssertMergeOrganizationPage() throws Exception {
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
			RuntimeVariables.replace("Organization Name"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Liferay"),
			selenium.getText("//li[@class='lfr-trigger']/strong/a"));
		selenium.clickAt("//li[@class='lfr-trigger']/strong/a",
			RuntimeVariables.replace("Liferay"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Organization Name')]");
		assertEquals(RuntimeVariables.replace("Organization Name"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Organization Name')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Organization Name')]"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("link=Site Pages");
		selenium.clickAt("link=Site Pages", RuntimeVariables.replace("Pages"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//div[2]/span/button[2]",
			RuntimeVariables.replace("View Pages"));
		selenium.waitForPopUp("null", RuntimeVariables.replace("5000"));
		selenium.selectPopUp("null");
		selenium.waitForVisible("link=Welcome");
		assertTrue(selenium.isElementPresent("link=Welcome"));
		assertTrue(selenium.isElementPresent("link=Control Panel"));
		assertTrue(selenium.isElementPresent("link=Selenium Test Home Page"));
		selenium.close();
		selenium.selectWindow("null");
		selenium.open("/web/guest/home/");
		assertTrue(selenium.isElementPresent("link=Welcome"));
		assertTrue(selenium.isElementPresent("link=Control Panel"));
		assertTrue(selenium.isElementPresent("link=Selenium Test Home Page"));
	}
}