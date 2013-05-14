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

package com.liferay.portalweb.portal.permissions.controlpanel;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DefineCARolesTest extends BaseTestCase {
	public void testDefineCARoles() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.waitForElementPresent("link=Roles");
		selenium.clickAt("link=Roles", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//tr[4]/td[4]/ul/li/strong/a");
		selenium.clickAt("//tr[4]/td[4]/ul/li/strong/a",
			RuntimeVariables.replace(""));
		selenium.waitForElementPresent(
			"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a");
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Community Admin"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertTrue(selenium.isPartialText("//label", "Add Permissions"));
	}
}