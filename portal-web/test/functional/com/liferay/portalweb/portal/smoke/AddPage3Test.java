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

package com.liferay.portalweb.portal.smoke;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPage3Test extends BaseTestCase {
	public void testAddPage3() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='_145_dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable-deprecated/aui-editable-deprecated-min.js')]");
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//li[@id='_145_addContent']/a"));
		selenium.clickAt("//li[@id='_145_addContent']/a",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible("//a[@id='_145_addPage']");
		assertEquals(RuntimeVariables.replace("Page"),
			selenium.getText("//a[@id='_145_addPage']"));
		selenium.clickAt("//a[@id='_145_addPage']",
			RuntimeVariables.replace("Page"));
		selenium.waitForVisible("//input[@type='text']");
		selenium.type("//input[@type='text']",
			RuntimeVariables.replace("Test Page3"));
		selenium.clickAt("//button/i[@class='icon-ok']",
			RuntimeVariables.replace("Save"));
		selenium.waitForVisible(
			"//li[contains(@class,'lfr-nav-updateable')]/a[.='Test Page3']");
		selenium.clickAt("//li[contains(@class,'lfr-nav-updateable')]/a[.='Test Page3']",
			RuntimeVariables.replace("Test Page3"));
		selenium.waitForPageToLoad("30000");
	}
}