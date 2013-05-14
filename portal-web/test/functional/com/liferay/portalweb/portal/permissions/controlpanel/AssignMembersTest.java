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
public class AssignMembersTest extends BaseTestCase {
	public void testAssignMembers() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.waitForElementPresent("link=Communities");
		selenium.clickAt("link=Communities", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//strong/a", RuntimeVariables.replace(""));
		selenium.waitForElementPresent(
			"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a");
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li[5]/a"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("link=Available");
		selenium.clickAt("link=Available", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.typeKeys("_134_keywords", RuntimeVariables.replace("ca"));
		selenium.type("_134_keywords", RuntimeVariables.replace("ca"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("_134_allRowIds", RuntimeVariables.replace(""));
		selenium.clickAt("//input[@value='Update Associations']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request completed successfully."));
		selenium.typeKeys("_134_keywords", RuntimeVariables.replace("member"));
		selenium.type("_134_keywords", RuntimeVariables.replace("member"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("_134_allRowIds", RuntimeVariables.replace(""));
		selenium.clickAt("//input[@value='Update Associations']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request completed successfully."));
		selenium.typeKeys("_134_keywords", RuntimeVariables.replace("publisher"));
		selenium.type("_134_keywords", RuntimeVariables.replace("publisher"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("_134_allRowIds", RuntimeVariables.replace(""));
		selenium.clickAt("//input[@value='Update Associations']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request completed successfully."));
		selenium.typeKeys("_134_keywords", RuntimeVariables.replace("writer"));
		selenium.type("_134_keywords", RuntimeVariables.replace("writer"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("_134_allRowIds", RuntimeVariables.replace(""));
		selenium.clickAt("//input[@value='Update Associations']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent(
				"Your request completed successfully."));
	}
}