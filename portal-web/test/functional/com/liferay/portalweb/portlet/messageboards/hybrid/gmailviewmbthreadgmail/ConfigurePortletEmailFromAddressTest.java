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

package com.liferay.portalweb.portlet.messageboards.hybrid.gmailviewmbthreadgmail;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletEmailFromAddressTest extends BaseTestCase {
	public void testConfigurePortletEmailFromAddress()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/site-name/");
		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
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
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[2]/a",
			RuntimeVariables.replace("Configuration"));
		selenium.waitForVisible("//iframe[@id='_19_configurationIframeDialog']");
		selenium.selectFrame("//iframe[@id='_19_configurationIframeDialog']");
		selenium.waitForVisible("link=Email From");
		selenium.clickAt("link=Email From",
			RuntimeVariables.replace("Email From"));
		selenium.waitForVisible("//input[@id='_86_emailFromAddress']");
		selenium.type("//input[@id='_86_emailFromAddress']",
			RuntimeVariables.replace("liferay.qa.server.trunk@gmail.com"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("liferay.qa.server.trunk@gmail.com",
			selenium.getValue("//input[@id='_86_emailFromAddress']"));
		selenium.selectFrame("relative=top");
	}
}