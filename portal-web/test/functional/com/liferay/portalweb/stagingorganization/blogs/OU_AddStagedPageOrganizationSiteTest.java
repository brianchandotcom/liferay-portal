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

package com.liferay.portalweb.stagingorganization.blogs;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class OU_AddStagedPageOrganizationSiteTest extends BaseTestCase {
	public void testOU_AddStagedPageOrganizationSite()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/organization-name/home/");
		assertTrue(selenium.isElementPresent("//div[@class='staging-bar']"));
		assertEquals(RuntimeVariables.replace("Live"),
			selenium.getText("//li[1]/span/span"));
		assertEquals(RuntimeVariables.replace(
				"You are viewing the live version of Organization Name and cannot make changes here. Make your changes in staging and publish them to Live afterwards to make them public."),
			selenium.getText("//span[@class='staging-live-help']"));
		assertEquals(RuntimeVariables.replace("Staging"),
			selenium.getText("//li[2]/span/a"));
		selenium.clickAt("//li[2]/span/a", RuntimeVariables.replace("Staging"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You are viewing the staged version of Organization Name. You can make changes here and publish them to Live afterwards to make them public."),
			selenium.getText("//span[@class='staging-live-help']"));
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/dockbar_underlay.js')]");
		selenium.waitForVisible(
			"//a[@class='menu-button']/span[contains(.,'Add')]");
		selenium.clickAt("//a[@class='menu-button']/span[contains(.,'Add')]	",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible("//a[@id='addPage']");
		assertEquals(RuntimeVariables.replace("Page"),
			selenium.getText("//a[@id='addPage']"));
		selenium.clickAt("//a[@id='addPage']", RuntimeVariables.replace("Page"));
		selenium.waitForVisible("//input[@type='text']");
		Thread.sleep(5000);
		selenium.type("//input[@type='text']",
			RuntimeVariables.replace("Blogs Test Page"));
		selenium.clickAt("//button[contains(@id,'Save')]",
			RuntimeVariables.replace("Save"));
		selenium.waitForVisible("link=Blogs Test Page");
		selenium.clickAt("link=Blogs Test Page",
			RuntimeVariables.replace("Blogs Test Page"));
		selenium.waitForPageToLoad("30000");
	}
}