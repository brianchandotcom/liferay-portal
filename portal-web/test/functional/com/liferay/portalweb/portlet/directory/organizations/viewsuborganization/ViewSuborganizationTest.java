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

package com.liferay.portalweb.portlet.directory.organizations.viewsuborganization;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewSuborganizationTest extends BaseTestCase {
	public void testViewSuborganization() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Directory Test Page",
					RuntimeVariables.replace("Directory Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Organizations",
					RuntimeVariables.replace("Organizations"));
				selenium.waitForPageToLoad("30000");

				boolean basicVisible = selenium.isVisible(
						"//a[.='\u00ab Basic']");

				if (!basicVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//a[.='\u00ab Basic']",
					RuntimeVariables.replace("\u00ab Basic"));

			case 2:
				selenium.type("//input[@name='_11_keywords']",
					RuntimeVariables.replace("Test Organization"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Test Suborganization"),
					selenium.getText(
						"//tr[contains(.,'Test Suborganization')]/td[1]/a"));
				assertEquals(RuntimeVariables.replace("Test Organization"),
					selenium.getText(
						"//tr[contains(.,'Test Suborganization')]/td[2]/a"));
				assertEquals(RuntimeVariables.replace("Regular Organization"),
					selenium.getText(
						"//tr[contains(.,'Test Suborganization')]/td[3]/a"));
				assertEquals(RuntimeVariables.replace("Cerritos"),
					selenium.getText(
						"//tr[contains(.,'Test Suborganization')]/td[4]/a"));
				assertEquals(RuntimeVariables.replace("Florida"),
					selenium.getText(
						"//tr[contains(.,'Test Suborganization')]/td[5]/a"));
				assertEquals(RuntimeVariables.replace("United States"),
					selenium.getText(
						"//tr[contains(.,'Test Suborganization')]/td[6]/a"));
				assertEquals(RuntimeVariables.replace("Test Organization"),
					selenium.getText("//tr[contains(.,'Diamond Bar')]/td[1]/a"));
				assertEquals(RuntimeVariables.replace(""),
					selenium.getText("//tr[contains(.,'Diamond Bar')]/td[2]/a"));
				assertEquals(RuntimeVariables.replace("Regular Organization"),
					selenium.getText("//tr[contains(.,'Diamond Bar')]/td[3]/a"));
				assertEquals(RuntimeVariables.replace("Diamond Bar"),
					selenium.getText("//tr[contains(.,'Diamond Bar')]/td[4]/a"));
				assertEquals(RuntimeVariables.replace("California"),
					selenium.getText("//tr[contains(.,'Diamond Bar')]/td[5]/a"));
				assertEquals(RuntimeVariables.replace("United States"),
					selenium.getText("//tr[contains(.,'Diamond Bar')]/td[6]/a"));
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'View Suborganizations')]");
				assertEquals(RuntimeVariables.replace("View Suborganizations"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'View Suborganizations')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'View Suborganizations')]"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Test Suborganization"),
					selenium.getText(
						"//tr[contains(.,'Test Suborganization')]/td[1]/a"));
				assertEquals(RuntimeVariables.replace("Test Organization"),
					selenium.getText(
						"//tr[contains(.,'Test Suborganization')]/td[2]/a"));
				assertEquals(RuntimeVariables.replace("Regular Organization"),
					selenium.getText(
						"//tr[contains(.,'Test Suborganization')]/td[3]/a"));
				assertEquals(RuntimeVariables.replace("Cerritos"),
					selenium.getText(
						"//tr[contains(.,'Test Suborganization')]/td[4]/a"));
				assertEquals(RuntimeVariables.replace("Florida"),
					selenium.getText(
						"//tr[contains(.,'Test Suborganization')]/td[5]/a"));
				assertEquals(RuntimeVariables.replace("United States"),
					selenium.getText(
						"//tr[contains(.,'Test Suborganization')]/td[6]/a"));
				selenium.clickAt("//tr[contains(.,'Test Suborganization')]/td[1]/a",
					RuntimeVariables.replace("Test Suborganization"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Test Suborganization"),
					selenium.getText(
						"//div[@class='section entity-details']/h2[contains(.,'Test Suborganization')]"));
				assertEquals(RuntimeVariables.replace("Type"),
					selenium.getText(
						"//dl[@class='property-list']/dt[contains(.,'Type')]"));
				assertEquals(RuntimeVariables.replace("Regular Organization"),
					selenium.getText(
						"//dl[@class='property-list']/dd[contains(.,'Regular Organization')]"));
				assertEquals(RuntimeVariables.replace("Parent Organization"),
					selenium.getText(
						"//dl[@class='property-list']/dt[contains(.,'Parent Organization')]"));
				assertEquals(RuntimeVariables.replace("Test Organization"),
					selenium.getText(
						"//dl[@class='property-list']/dd[contains(.,'Test Organization')]"));
				assertEquals(RuntimeVariables.replace("Billing"),
					selenium.getText("//li[@class='primary']/em"));
				assertTrue(selenium.isPartialText("//li[@class='primary']",
						"11111 Main Street USA"));
				assertTrue(selenium.isPartialText("//li[@class='primary']",
						"90210, Cerritos"));
				assertTrue(selenium.isPartialText("//li[@class='primary']",
						"(Mailing)"));

			case 100:
				label = -1;
			}
		}
	}
}