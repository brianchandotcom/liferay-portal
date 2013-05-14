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

package com.liferay.portalweb.portlet.shopping.category.addcategorynamedoublebackslash;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewAddCategoryNameDoubleBackSlashTest extends BaseTestCase {
	public void testViewAddCategoryNameDoubleBackSlash()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Shopping Test Page",
			RuntimeVariables.replace("Shopping Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Categories",
			RuntimeVariables.replace("Categories"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Categories"),
			selenium.getText("//ul[@class='tabview-list']/li[1]"));
		assertEquals(RuntimeVariables.replace("Cart"),
			selenium.getText("//ul[@class='tabview-list']/li[2]"));
		assertEquals(RuntimeVariables.replace("Orders"),
			selenium.getText("//ul[@class='tabview-list']/li[3]"));
		assertEquals(RuntimeVariables.replace("Coupons"),
			selenium.getText("//ul[@class='tabview-list']/li[4]"));
		assertEquals(RuntimeVariables.replace("Categories"),
			selenium.getText("//div[@class='breadcrumbs']"));
		assertEquals(RuntimeVariables.replace("Categories"),
			selenium.getText("//div[@id='shoppingCategoriesPanel']/div[1]/div"));
		assertEquals("Add Category",
			selenium.getValue(
				"//div[@id='shoppingCategoriesPanel']/div[2]/div[1]/span[1]/span/input"));
		assertEquals("Permissions",
			selenium.getValue(
				"//div[@id='shoppingCategoriesPanel']/div[2]/div[1]/span[2]/span/input"));
		assertEquals(RuntimeVariables.replace("Items"),
			selenium.getText("//div[@id='shoppingItemsPanel']/div[1]/div"));
		assertEquals("Add Item",
			selenium.getValue(
				"//div[@id='shoppingItemsPanel']/div[2]/div/span/span/input"));
		assertFalse(selenium.isTextPresent("Shopping\\\\ Category\\\\ Name\\\\"));
		assertFalse(selenium.isTextPresent(
				"Shopping\\\\ Category\\\\ Description\\\\"));
	}
}