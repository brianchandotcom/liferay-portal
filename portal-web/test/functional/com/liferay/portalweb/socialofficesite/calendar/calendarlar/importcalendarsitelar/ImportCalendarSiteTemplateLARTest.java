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

package com.liferay.portalweb.socialofficesite.calendar.calendarlar.importcalendarsitelar;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ImportCalendarSiteTemplateLARTest extends BaseTestCase {
	public void testImportCalendarSiteTemplateLAR() throws Exception {
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
		selenium.clickAt("link=Site Templates",
			RuntimeVariables.replace("Site Templates"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Default Social Office Site"),
			selenium.getText(
				"//tr[contains(.,'Default Social Office Site')]/td/a"));
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'Default Social Office Site')]/td[contains(.,'Actions')]/span/ul/li/strong/a"));
		selenium.clickAt("//tr[contains(.,'Default Social Office Site')]/td[contains(.,'Actions')]/span/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Import')]");
		assertEquals(RuntimeVariables.replace("Import"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Import')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Import')]",
			RuntimeVariables.replace("Import"));
		selenium.waitForVisible("//iframe[contains(@id,'Import')]");
		selenium.selectFrame("//iframe[contains(@id,'Import')]");
		selenium.waitForVisible("//input[@id='_149_importFileName']");
		selenium.uploadFile("//input[@id='_149_importFileName']",
			RuntimeVariables.replace(
				"L:\\portal\\build\\portal-web\\test\\functional\\com\\liferay\\portalweb\\socialofficesite\\calendar\\dependencies\\SO_Site_Template_Calendar-Selenium.lar"));
		selenium.check("//input[@id='_149_PORTLET_DATACheckbox']");
		assertTrue(selenium.isChecked(
				"//input[@id='_149_PORTLET_DATACheckbox']"));
		selenium.check("//input[@id='_149_DELETE_PORTLET_DATACheckbox']");
		assertTrue(selenium.isChecked(
				"//input[@id='_149_DELETE_PORTLET_DATACheckbox']"));
		selenium.clickAt("//input[@id='_149_PERMISSIONSCheckbox']",
			RuntimeVariables.replace("Permissions"));
		assertTrue(selenium.isChecked("//input[@id='_149_PERMISSIONSCheckbox']"));
		assertTrue(selenium.isChecked(
				"//input[@id='_149_permissionsAssignedToRolesCheckbox']"));
		selenium.check("//input[@id='_149_CATEGORIESCheckbox']");
		assertTrue(selenium.isChecked("//input[@id='_149_CATEGORIESCheckbox']"));
		selenium.clickAt("//input[@value='Import']",
			RuntimeVariables.replace("Import"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.selectFrame("relative=top");
	}
}