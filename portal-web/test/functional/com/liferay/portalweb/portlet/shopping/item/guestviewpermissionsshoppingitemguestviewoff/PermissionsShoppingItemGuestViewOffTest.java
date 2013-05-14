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

package com.liferay.portalweb.portlet.shopping.item.guestviewpermissionsshoppingitemguestviewoff;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class PermissionsShoppingItemGuestViewOffTest extends BaseTestCase {
	public void testPermissionsShoppingItemGuestViewOff()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Shopping Test Page",
			RuntimeVariables.replace("Shopping Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row last']/td[2]/a",
				"Shopping Item Name"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row last']/td[2]/a",
				"Shopping Item Description"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row last']/td[2]/a",
				"Shopping: Item Properties"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Permissions')]");
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Permissions')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Permissions')]",
			RuntimeVariables.replace("Permissions"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//input[@id='guest_ACTION_VIEW']");
		assertTrue(selenium.isChecked("//input[@id='guest_ACTION_VIEW']"));
		selenium.uncheck("//input[@id='guest_ACTION_VIEW']");
		assertFalse(selenium.isChecked("//input[@id='guest_ACTION_VIEW']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertFalse(selenium.isChecked("//input[@id='guest_ACTION_VIEW']"));
	}
}