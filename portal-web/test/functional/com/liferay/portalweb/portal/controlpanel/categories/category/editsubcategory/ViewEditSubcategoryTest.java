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

package com.liferay.portalweb.portal.controlpanel.categories.category.editsubcategory;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewEditSubcategoryTest extends BaseTestCase {
	public void testViewEditSubcategory() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Categories",
			RuntimeVariables.replace("Categories"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible(
			"//div[@class='vocabulary-categories']/div/ul/li/div[contains(.,'Category Name')]");
		assertEquals(RuntimeVariables.replace("Category Name"),
			selenium.getText(
				"//div[@class='vocabulary-categories']/div/ul/li/div[contains(.,'Category Name')]"));
		selenium.waitForVisible("//div[@class='tree-hitarea']");
		selenium.clickAt("//div[@class='tree-hitarea']",
			RuntimeVariables.replace("Drop Down Arrow"));
		selenium.waitForVisible(
			"//div[@class='vocabulary-categories']/div/ul/li/ul/li/div/div[3][contains(.,'Subcategory Name Edit')]");
		assertEquals(RuntimeVariables.replace("Subcategory Name Edit"),
			selenium.getText(
				"//div[@class='vocabulary-categories']/div/ul/li/ul/li/div/div[3][contains(.,'Subcategory Name Edit')]"));
		selenium.clickAt("//div[@class='vocabulary-categories']/div/ul/li/ul/li/div/div[3][contains(.,'Subcategory Name Edit')]",
			RuntimeVariables.replace("Subcategory Name Edit"));
		selenium.waitForText("//div[@class='view-category']/div/h1/span",
			"Subcategory Name Edit");
		assertEquals(RuntimeVariables.replace("Subcategory Name Edit"),
			selenium.getText("//div[@class='view-category']/div/h1/span"));
		assertEquals(RuntimeVariables.replace(
				"Description: Subcategory Description Edit"),
			selenium.getText("//div[@class='view-category']/div[2]"));
	}
}