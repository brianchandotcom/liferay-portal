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

package com.liferay.portalweb.portlet.shopping.category.addsubcategories;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewAddSubcategoriesTest extends BaseTestCase {
	public void testViewAddSubcategories() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Shopping Test Page",
			RuntimeVariables.replace("Shopping Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Categories",
			RuntimeVariables.replace("Categories"));
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
		assertEquals(RuntimeVariables.replace("Categories"),
			selenium.getText("//ul[@class='tabview-list']/li[1]"));
		assertEquals(RuntimeVariables.replace("Cart"),
			selenium.getText("//ul[@class='tabview-list']/li[2]"));
		assertEquals(RuntimeVariables.replace("Orders"),
			selenium.getText("//ul[@class='tabview-list']/li[3]"));
		assertEquals(RuntimeVariables.replace("Coupons"),
			selenium.getText("//ul[@class='tabview-list']/li[4]"));
		assertEquals(RuntimeVariables.replace(
				"Categories \u00bb Shopping Category Name"),
			selenium.getText("//div[@class='breadcrumbs']"));
		assertEquals(RuntimeVariables.replace("Categories"),
			selenium.getText("//div[@id='shoppingCategoriesPanel']/div[1]/div"));
		assertEquals(RuntimeVariables.replace("Search"),
			selenium.getText(
				"//div[@id='shoppingCategoriesPanel']/div[2]/fieldset/div/span/span/label"));
		assertTrue(selenium.isVisible(
				"//div[@id='shoppingCategoriesPanel']/div[2]/fieldset/div/span/span/span/input"));
		assertEquals("Search Categories",
			selenium.getValue(
				"//div[@id='shoppingCategoriesPanel']/div[2]/div[1]/span[1]/span/input"));
		assertEquals("Add Subcategory",
			selenium.getValue(
				"//div[@id='shoppingCategoriesPanel']/div[2]/div[1]/span[2]/span/input"));
		assertEquals("Permissions",
			selenium.getValue(
				"//div[@id='shoppingCategoriesPanel']/div[2]/div[1]/span[3]/span/input"));
		assertEquals(RuntimeVariables.replace("Category"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[1]"));
		assertEquals(RuntimeVariables.replace("# of Categories"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[2]"));
		assertEquals(RuntimeVariables.replace("# of Items"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[3]"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row']/td[1]",
				"Shopping Subcategory1 Name"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row']/td[1]",
				"Shopping Subcategory1 Description"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row']/td[2]"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row']/td[3]"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row']/td[4]/span/ul/li/strong/a"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-alternate results-row alt']/td[1]",
				"Shopping Subcategory2 Name"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-alternate results-row alt']/td[1]",
				"Shopping Subcategory2 Description"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText(
				"//tr[@class='portlet-section-alternate results-row alt']/td[2]"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText(
				"//tr[@class='portlet-section-alternate results-row alt']/td[3]"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[@class='portlet-section-alternate results-row alt']/td[4]/span/ul/li/strong/a"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row last']/td[1]",
				"Shopping Subcategory3 Name"));
		assertTrue(selenium.isPartialText(
				"//tr[@class='portlet-section-body results-row last']/td[1]",
				"Shopping Subcategory3 Description"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[2]"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[3]"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[4]/span/ul/li/strong/a"));
		assertEquals(RuntimeVariables.replace("Showing 3 results."),
			selenium.getText(
				"//div[@id='shoppingCategoriesPanel']/div[2]/div[2]/div[2]"));
		assertEquals(RuntimeVariables.replace("Items"),
			selenium.getText("//div[@id='shoppingItemsPanel']/div[1]/div"));
		assertEquals("Add Item",
			selenium.getValue(
				"//div[@id='shoppingItemsPanel']/div[2]/div/span/span/input"));
	}
}