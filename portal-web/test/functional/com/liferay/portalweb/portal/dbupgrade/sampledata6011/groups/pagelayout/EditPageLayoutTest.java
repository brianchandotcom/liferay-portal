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

package com.liferay.portalweb.portal.dbupgrade.sampledata6011.groups.pagelayout;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditPageLayoutTest extends BaseTestCase {
	public void testEditPageLayout() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Communities", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.type("_134_name",
			RuntimeVariables.replace("Group Page Layout Community"));
		selenium.click(RuntimeVariables.replace("//input[@value='Search']"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//td[2]/a", RuntimeVariables.replace("Open"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Page Layout Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("main-content", RuntimeVariables.replace(""));
		selenium.clickAt("dockbar", RuntimeVariables.replace(""));
		selenium.clickAt("navigation", RuntimeVariables.replace(""));
		assertTrue(selenium.isElementPresent(
				"//div[@id='column-1' and @class='w30 portlet-column portlet-column-first dd-drop']"));
		assertTrue(selenium.isElementPresent(
				"//div[@id='column-2' and @class='w70 portlet-column portlet-column-last dd-drop']"));
		selenium.clickAt("pageTemplate", RuntimeVariables.replace("Page Layout"));
		selenium.waitForVisible("//input[@id='layoutTemplateId2']");
		selenium.clickAt("//input[@id='layoutTemplateId2']",
			RuntimeVariables.replace(""));
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("main-content", RuntimeVariables.replace(""));
		selenium.clickAt("dockbar", RuntimeVariables.replace(""));
		selenium.clickAt("navigation", RuntimeVariables.replace(""));
		assertTrue(selenium.isElementPresent(
				"//div[@id='column-1' and @class='w50 portlet-column portlet-column-first dd-drop']"));
		assertTrue(selenium.isElementPresent(
				"//div[@id='column-2' and @class='w50 portlet-column portlet-column-last dd-drop']"));
		assertEquals(RuntimeVariables.replace("Breadcrumb"),
			selenium.getText(
				"//div[@id='column-1' and @class='w50 portlet-column portlet-column-first dd-drop']/div[1]/div[1]/section/header/h1/span[2]"));
		assertEquals(RuntimeVariables.replace("Navigation"),
			selenium.getText(
				"//div[@id='column-2' and @class='w50 portlet-column portlet-column-last dd-drop']/div[1]/div[1]/section/header/h1/span[2]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@id='column-1' and @class='w30 portlet-column portlet-column-first dd-drop']"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@id='column-2' and @class='w70 portlet-column portlet-column-last dd-drop']"));
	}
}