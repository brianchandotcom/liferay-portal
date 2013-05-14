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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcwebcontent.expirewcwebcontentactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ExpireWCWebContentActionsTest extends BaseTestCase {
	public void testExpireWCWebContentActions() throws Exception {
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
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//button[contains(@title,'List View')]");
		selenium.clickAt("//button[contains(@title,'List View')]",
			RuntimeVariables.replace("List View"));
		selenium.waitForVisible(
			"//tr[contains(.,'WC WebContent Title')]/td[1]/input[@type='checkbox']");

		String webContentID = selenium.getText(
				"//tr[contains(.,'WC WebContent Title')]/td[2]");
		RuntimeVariables.setValue("webContentID", webContentID);
		assertEquals(RuntimeVariables.replace("${webContentID}"),
			selenium.getText("//tr[contains(.,'WC WebContent Title')]/td[2]"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText(
				"//tr[contains(.,'WC WebContent Title')]/td[3]/span/a"));
		assertEquals(RuntimeVariables.replace("Approved"),
			selenium.getText("//tr[contains(.,'WC WebContent Title')]/td[4]"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC WebContent Title')]/td[5]"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC WebContent Title')]/td[6]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//tr[contains(.,'WC WebContent Title')]/td[7]"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC WebContent Title')]/td[8]/span/span/ul/li/strong/a"));
		selenium.clickAt("//tr[contains(.,'WC WebContent Title')]/td[8]/span/span/ul/li/strong/a",
			RuntimeVariables.replace("Menu Button"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Expire')]");
		assertEquals(RuntimeVariables.replace("Expire"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Expire')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Expire')]"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC WebContent Title')]/td[1]"));
		assertEquals(RuntimeVariables.replace("${webContentID}"),
			selenium.getText("//tr[contains(.,'WC WebContent Title')]/td[2]"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText(
				"//tr[contains(.,'WC WebContent Title')]/td/span/a"));
		assertEquals(RuntimeVariables.replace("Expired"),
			selenium.getText("//tr[contains(.,'WC WebContent Title')]/td[4]"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC WebContent Title')]/td[5]"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC WebContent Title')]/td[6]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//tr[contains(.,'WC WebContent Title')]/td[7]"));
	}
}