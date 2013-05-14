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

package com.liferay.portalweb.portal.permissions.documentlibrary.portlet.configuration;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Member_AssertConfigurationTest extends BaseTestCase {
	public void testMember_AssertConfiguration() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Document Library Permissions Page");
		selenium.clickAt("link=Document Library Permissions Page",
			RuntimeVariables.replace("Document Library Permissions Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//span[@title='Options']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
			RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a");
		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a"));
		selenium.click("//div[@class='lfr-menu-list unstyled']/ul/li[2]/a");
		selenium.waitForVisible("//input[@value='Save']");
		assertEquals(RuntimeVariables.replace("Setup"),
			selenium.getText("//ul[@class='tabview-list']/li[1]/span/a"));
		assertEquals(RuntimeVariables.replace("Communication"),
			selenium.getText("//ul[@class='tabview-list']/li[2]/span/a"));
		assertEquals(RuntimeVariables.replace("Sharing"),
			selenium.getText("//ul[@class='tabview-list']/li[3]/span/a"));
		assertEquals(RuntimeVariables.replace("Scope"),
			selenium.getText("//ul[@class='tabview-list']/li[4]/span/a"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}