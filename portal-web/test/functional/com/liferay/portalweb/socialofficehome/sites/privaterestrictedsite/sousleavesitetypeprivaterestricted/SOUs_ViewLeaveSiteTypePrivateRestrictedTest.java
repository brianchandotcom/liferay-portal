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

package com.liferay.portalweb.socialofficehome.sites.privaterestrictedsite.sousleavesitetypeprivaterestricted;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_ViewLeaveSiteTypePrivateRestrictedTest extends BaseTestCase {
	public void testSOUs_ViewLeaveSiteTypePrivateRestricted()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/socialoffice01/so/dashboard/");
		selenium.waitForVisible("//li[contains(@class, 'selected')]/a/span");
		assertEquals(RuntimeVariables.replace("Dashboard"),
			selenium.getText("//li[contains(@class, 'selected')]/a/span"));
		assertEquals(RuntimeVariables.replace("Sites"),
			selenium.getText("//div[@id='so-sidebar']/h3"));
		assertTrue(selenium.isPartialText(
				"//select[@id='_5_WAR_soportlet_tabs1']", "My Sites"));
		selenium.select("//select[@id='_5_WAR_soportlet_tabs1']",
			RuntimeVariables.replace("My Sites"));
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("There are no results."),
			selenium.getText("//ul[@class='site-list']"));
		assertFalse(selenium.isTextPresent("Private Restricted Site Name"));
		assertEquals(RuntimeVariables.replace("Sites Directory"),
			selenium.getText("//button[contains(.,'Sites Directory')]/span[2]"));
		selenium.clickAt("//button[contains(.,'Sites Directory')]/span[2]",
			RuntimeVariables.replace("Sites Directory"));
		selenium.waitForVisible("xPath=(//h1[@class='header-title']/span)[1]");
		assertEquals(RuntimeVariables.replace("Directory"),
			selenium.getText("xPath=(//h1[@class='header-title']/span)[1]"));
		selenium.select("//span[@class='sites-tabs']/span/span/span/select",
			RuntimeVariables.replace("My Sites"));
		Thread.sleep(1000);
		assertFalse(selenium.isTextPresent("Private Restricted Site Name"));
		selenium.select("//span[@class='sites-tabs']/span/span/span/select",
			RuntimeVariables.replace("All Sites"));
		selenium.type("//input[@id='_5_WAR_soportlet_dialogKeywords']",
			RuntimeVariables.replace("Private Restricted Site Name"));
		Thread.sleep(1000);
		selenium.waitForVisible(
			"xPath=(//span[@class='name'])[contains(.,'Private Restricted Site Name')]");
		assertTrue(selenium.isVisible(
				"xPath=(//span[@class='name'])[contains(.,'Private Restricted Site Name')]"));
		assertEquals(RuntimeVariables.replace("Private Restricted Site Name"),
			selenium.getText(
				"xPath=(//span[@class='name'])[contains(.,'Private Restricted Site Name')]"));
		assertEquals(RuntimeVariables.replace(
				"Private Restricted Site Description"),
			selenium.getText(
				"xPath=(//span[@class='description'])[contains(.,'Private Restricted Site Description')]"));
		assertTrue(selenium.isElementNotPresent(
				"xPath=(//span[@class='name']/a)[contains(.,'Private Restricted Site Name')]"));
		assertTrue(selenium.isVisible("//a[@class='request-site']"));
	}
}