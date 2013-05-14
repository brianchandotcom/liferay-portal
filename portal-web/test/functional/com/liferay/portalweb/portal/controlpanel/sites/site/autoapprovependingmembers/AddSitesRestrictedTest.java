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

package com.liferay.portalweb.portal.controlpanel.sites.site.autoapprovependingmembers;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddSitesRestrictedTest extends BaseTestCase {
	public void testAddSitesRestricted() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Sites", RuntimeVariables.replace("Sites"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Add", RuntimeVariables.replace("Add"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a");
		assertEquals(RuntimeVariables.replace("Blank Site"),
			selenium.getText("//div[@class='lfr-menu-list unstyled']/ul/li/a"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a",
			RuntimeVariables.replace("Blank Site"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_134_name']",
			RuntimeVariables.replace("Test Restricted Community"));
		selenium.type("//textarea[@id='_134_description']",
			RuntimeVariables.replace("This is an restricted test community!"));
		selenium.select("//select[@id='_134_type']",
			RuntimeVariables.replace("Restricted"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Test Restricted Community"),
			selenium.getText("//h1[@class='header-title']/span"));
		selenium.open("/web/guest/home/");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Sites", RuntimeVariables.replace("Sites"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test Restricted Community"),
			selenium.getText("//tr[4]/td[1]"));
		assertEquals(RuntimeVariables.replace("Restricted"),
			selenium.getText("//tr[4]/td[2]"));
		assertEquals(RuntimeVariables.replace("1 User"),
			selenium.getText("//tr[4]/td[3]"));
		assertEquals(RuntimeVariables.replace("Yes"),
			selenium.getText("//tr[4]/td[4]"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//tr[4]/td[5]"));
	}
}