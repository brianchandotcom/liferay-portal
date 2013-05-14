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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.webcontent.journalfeed;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWCJournalFeedURLTest extends BaseTestCase {
	public void testViewWCJournalFeedURL() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/wc-journal-feed-community/");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC Journal Feed Community"),
			selenium.getText("//strong/a/span"));
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Feeds", RuntimeVariables.replace("Feeds"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//span[@title='Actions']/ul/li/strong/a/span");
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Edit')]/a");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Edit')]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Edit')]/a"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Feed Name"),
			selenium.getText("//div[1]/h1/span"));

		String URL = selenium.getValue("//div/input");
		RuntimeVariables.setValue("URL", URL);
		selenium.open(RuntimeVariables.getValue("URL"));
		assertEquals(RuntimeVariables.replace("Feed Name"),
			selenium.getText("//x:h1"));
		assertEquals(RuntimeVariables.replace("Feed Description"),
			selenium.getText("//x:h2"));
		assertEquals(RuntimeVariables.replace("Web Content Name"),
			selenium.getText("//x:h3/x:a"));
	}
}