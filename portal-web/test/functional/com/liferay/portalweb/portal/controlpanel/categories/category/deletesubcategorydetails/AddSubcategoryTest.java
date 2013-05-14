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

package com.liferay.portalweb.portal.controlpanel.categories.category.deletesubcategorydetails;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddSubcategoryTest extends BaseTestCase {
	public void testAddSubcategory() throws Exception {
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
		selenium.clickAt("//div[@class='vocabulary-categories']/div/ul/li/div[contains(.,'Category Name')]",
			RuntimeVariables.replace("Category Name"));
		selenium.waitForVisible("//input[@value='Add Subcategory']");
		selenium.clickAt("//input[@value='Add Subcategory']",
			RuntimeVariables.replace("Add Subcategory"));
		selenium.waitForVisible("//input[@id='_147_title_en_US']");
		selenium.type("//input[@id='_147_title_en_US']",
			RuntimeVariables.replace("Subcategory Name"));
		selenium.type("//textarea[@id='_147_description_en_US']",
			RuntimeVariables.replace("Subcategory Description"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForVisible(
			"//div[@class='lfr-message-response portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText(
				"//div[@class='lfr-message-response portlet-msg-success']"));
		selenium.waitForVisible("//div[@class='tree-hitarea']");
		selenium.clickAt("//div[@class='tree-hitarea']",
			RuntimeVariables.replace("Drop Down Arrow"));
		selenium.waitForVisible(
			"//div[@class='vocabulary-categories']/div/ul/li/ul/li/div/div[3][contains(.,'Subcategory Name')]");
		assertEquals(RuntimeVariables.replace("Subcategory Name"),
			selenium.getText(
				"//div[@class='vocabulary-categories']/div/ul/li/ul/li/div/div[3][contains(.,'Subcategory Name')]"));
	}
}