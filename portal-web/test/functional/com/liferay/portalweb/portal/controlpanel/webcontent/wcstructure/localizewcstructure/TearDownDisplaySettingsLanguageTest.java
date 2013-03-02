/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.localizewcstructure;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownDisplaySettingsLanguageTest extends BaseTestCase {
	public void testTearDownDisplaySettingsLanguage() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/es/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Ir a"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Panel de control");
		selenium.clickAt("link=Panel de control",
			RuntimeVariables.replace("Panel de control"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Mi cuenta", RuntimeVariables.replace("Mi cuenta"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//a[@id='_2_displaySettingsLink']",
				"Preferencias de presentaci\u00f3n"));
		selenium.clickAt("//a[@id='_2_displaySettingsLink']",
			RuntimeVariables.replace("Preferencias de presentaci\u00f3n"));
		selenium.waitForVisible("//select[@id='_2_languageId']");
		selenium.select("//select[@id='_2_languageId']",
			RuntimeVariables.replace("English (United States)"));
		selenium.waitForVisible(
			"//a[contains(.,'Preferencias de presentaci\u00f3n')]/span[@class='modified-notice']");
		selenium.clickAt("//input[@value='Guardar']",
			RuntimeVariables.replace("Guardar"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals("English (United States)",
			selenium.getSelectedLabel("//select[@id='_2_languageId']"));
	}
}