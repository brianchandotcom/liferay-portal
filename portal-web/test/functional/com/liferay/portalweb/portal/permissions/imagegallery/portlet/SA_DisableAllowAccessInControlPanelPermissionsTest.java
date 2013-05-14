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

package com.liferay.portalweb.portal.permissions.imagegallery.portlet;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SA_DisableAllowAccessInControlPanelPermissionsTest
	extends BaseTestCase {
	public void testSA_DisableAllowAccessInControlPanelPermissions()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent(
			"link=Image Gallery Permissions Test Page");
		selenium.clickAt("link=Image Gallery Permissions Test Page",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//strong/a", RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a");
		selenium.click("//div[@class='lfr-menu-list unstyled']/ul/li[2]/a");
		selenium.waitForVisible("//tr[7]/td[2]/input");
		assertTrue(selenium.isElementPresent(
				"//tr[7]/td[2]/input[@disabled='']"));
		assertFalse(selenium.isChecked("//tr[7]/td[2]/input[@disabled='']"));
		selenium.clickAt("//tr[7]/td[2]/input[@disabled='']",
			RuntimeVariables.replace(""));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@id='p_p_id_86_']/div/div[1]"));
		assertFalse(selenium.isChecked("//tr[7]/td[2]/input[@disabled='']"));
	}
}