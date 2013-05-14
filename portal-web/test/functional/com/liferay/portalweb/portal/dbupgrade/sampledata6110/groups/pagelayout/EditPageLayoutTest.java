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

package com.liferay.portalweb.portal.dbupgrade.sampledata6110.groups.pagelayout;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditPageLayoutTest extends BaseTestCase {
	public void testEditPageLayout() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/group-page-layout-community/");
		selenium.waitForVisible("link=Page Layout Page");
		selenium.clickAt("link=Page Layout Page",
			RuntimeVariables.replace("Page Layout Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//div[@id='layout-column_column-1' and @class='portlet-dropzone portlet-column-content portlet-column-content-first']/div[1]/div[1]/section/header/h1/span[2]"));
		assertTrue(selenium.isElementPresent(
				"//div[@id='layout-column_column-2' and @class='portlet-dropzone portlet-column-content portlet-column-content-last']/div[1]/div[1]/section/header/h1/span[2]"));
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Manage"),
			selenium.getText("//li[@id='_145_manageContent']"));
		selenium.mouseOver("//li[@id='_145_manageContent']");
		selenium.waitForVisible("link=Page Layout");
		selenium.clickAt("link=Page Layout",
			RuntimeVariables.replace("Page Layout"));
		Thread.sleep(5000);
		selenium.waitForVisible("//input[@id='_88_layoutTemplateId2']");
		selenium.clickAt("//input[@id='_88_layoutTemplateId2']",
			RuntimeVariables.replace("2 Columns"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully. The page will be refreshed when you close this dialog. Alternatively you can hide this dialog."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.open("/web/group-page-layout-community/");
		selenium.waitForVisible("link=Page Layout Page");
		selenium.clickAt("link=Page Layout Page",
			RuntimeVariables.replace("Page Layout Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//div[@id='column-1' and @class='w50 portlet-column portlet-column-first']"));
		assertTrue(selenium.isElementPresent(
				"//div[@id='column-2' and @class='w50 portlet-column portlet-column-last']"));
		assertEquals(RuntimeVariables.replace("Breadcrumb"),
			selenium.getText(
				"//div[@id='layout-column_column-1' and @class='portlet-dropzone portlet-column-content portlet-column-content-first']/div[1]/div[1]/section/header/h1/span[2]"));
		assertEquals(RuntimeVariables.replace("Navigation"),
			selenium.getText(
				"//div[@id='layout-column_column-2' and @class='portlet-dropzone portlet-column-content portlet-column-content-last']/div[1]/div[1]/section/header/h1/span[2]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@id='column-1' and @class='w30 portlet-column portlet-column-first']"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@id='column-2' and @class='w70 portlet-column portlet-column-last']"));
	}
}