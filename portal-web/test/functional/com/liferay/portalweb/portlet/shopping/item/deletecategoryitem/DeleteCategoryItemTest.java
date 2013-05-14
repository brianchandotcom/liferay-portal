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

package com.liferay.portalweb.portlet.shopping.item.deletecategoryitem;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DeleteCategoryItemTest extends BaseTestCase {
	public void testDeleteCategoryItem() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Shopping Test Page",
			RuntimeVariables.replace("Shopping Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row last']/td[1]/a",
				"Shopping Category Name"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row last']/td[1]/a",
				"Shopping Category Description"));
		selenium.clickAt("//tr[@class='portlet-section-body results-row last']/td[1]/a",
			RuntimeVariables.replace(
				"Shopping Category Name Shopping Category Description"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row last']/td[2]/a",
				"Shopping Category Item Name"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row last']/td[2]/a",
				"Shopping Category Item Description"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row last']/td[2]/a",
				"Shopping: Category Item Properties"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]");
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForConfirmation(
			"Are you sure you want to delete this? It will be deleted immediately.");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertFalse(selenium.isTextPresent("Shopping Category Item Name"));
		assertFalse(selenium.isTextPresent("Shopping Category Item Description"));
		assertFalse(selenium.isTextPresent("Shopping: Category Item Properties"));
	}
}