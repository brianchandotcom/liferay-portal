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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.documentlibrary.pagescope;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePage2PortletEntriesPerPage5Test extends BaseTestCase {
	public void testConfigurePage2PortletEntriesPerPage5()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/document-library-page-scope-community/");
		selenium.waitForVisible("link=DL Page2 Name");
		selenium.clickAt("link=DL Page2 Name",
			RuntimeVariables.replace("DL Page2 Name"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		selenium.waitForText("//span[@class='portlet-title-text']",
			"Documents and Media (DL Page2 Name)");
		assertEquals(RuntimeVariables.replace(
				"Documents and Media (DL Page2 Name)"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//strong/a"));
		selenium.clickAt("//strong/a", RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a");
		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a"));
		selenium.click("//div[@class='lfr-menu-list unstyled']/ul/li[2]/a");
		selenium.waitForVisible("//label[@for='entriesPerPage']");
		assertEquals(RuntimeVariables.replace("Maximum Entries to Display"),
			selenium.getText("//label[@for='entriesPerPage']"));
		selenium.select("//select[@id='_86_entriesPerPage']",
			RuntimeVariables.replace("5"));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("5",
			selenium.getSelectedLabel("//select[@id='_86_entriesPerPage']"));
	}
}