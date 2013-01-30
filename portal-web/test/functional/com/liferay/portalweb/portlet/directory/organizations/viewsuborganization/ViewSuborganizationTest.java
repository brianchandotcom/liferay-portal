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
				Thread.sleep(1000);
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
				assertEquals(RuntimeVariables.replace("Test Child"),
					selenium.getText(
						"xPath=(//tr[3]/td[contains(.,'Test Child')])/a"));
				assertEquals(RuntimeVariables.replace("Test Organization"),
					selenium.getText(
						"xPath=(//tr[3]/td[contains(.,'Test Organization')])/a"));
				assertEquals(RuntimeVariables.replace("Regular Organization"),
					selenium.getText(
						"xPath=(//tr[3]/td[contains(.,'Regular Organization')])/a"));
				assertEquals(RuntimeVariables.replace("Cerritos"),
					selenium.getText(
						"xPath=(//tr[3]/td[contains(.,'Cerritos')])/a"));
				assertEquals(RuntimeVariables.replace("Florida"),
					selenium.getText(
						"xPath=(//tr[3]/td[contains(.,'Florida')])/a"));
				assertEquals(RuntimeVariables.replace("United States"),
					selenium.getText(
						"xPath=(//tr[3]/td[contains(.,'United States')])/a"));
				assertEquals(RuntimeVariables.replace("Test Organization"),
					selenium.getText(
						"xPath=(//tr[4]/td[contains(.,'Test Organization')])/a"));
				assertEquals(RuntimeVariables.replace(""),
					selenium.getText("xPath=(//tr[4]/td[2])/a"));
				assertEquals(RuntimeVariables.replace("Regular Organization"),
					selenium.getText(
						"xPath=(//tr[4]/td[contains(.,'Regular Organization')])/a"));
				assertEquals(RuntimeVariables.replace("Diamond Bar"),
					selenium.getText(
						"xPath=(//tr[4]/td[contains(.,'Diamond Bar')])/a"));
				assertEquals(RuntimeVariables.replace("California"),
					selenium.getText(
						"xPath=(//tr[4]/td[contains(.,'California')])/a"));
				assertEquals(RuntimeVariables.replace("United States"),
					selenium.getText(
						"xPath=(//tr[4]/td[contains(.,'United States')])/a"));
				selenium.clickAt("xPath=(//tr[4]/td[contains(.,'Actions')])/span/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");
				assertEquals(RuntimeVariables.replace("View Suborganizations"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Test Child"),
					selenium.getText("xPath=(//td[contains(.,'Test Child')])/a"));
				assertEquals(RuntimeVariables.replace("Test Organization"),
					selenium.getText(
						"xPath=(//td[contains(.,'Test Organization')])/a"));
				assertEquals(RuntimeVariables.replace("Regular Organization"),
					selenium.getText(
						"xPath=(//td[contains(.,'Regular Organization')])/a"));
				assertEquals(RuntimeVariables.replace("Cerritos"),
					selenium.getText("xPath=(//td[contains(.,'Cerritos')])/a"));
				assertEquals(RuntimeVariables.replace("Florida"),
					selenium.getText("xPath=(//td[contains(.,'Florida')])/a"));
				assertEquals(RuntimeVariables.replace("United States"),
					selenium.getText(
						"xPath=(//td[contains(.,'United States')])/a"));
				selenium.clickAt("xPath=(//td[contains(.,'Test Child')])/a",
					RuntimeVariables.replace("Test Child"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Test Child"),
					selenium.getText(
						"xPath=(//div[@class='organization-information'])/div[1]/h2"));
				assertEquals(RuntimeVariables.replace("Type"),
					selenium.getText(
						"xPath=(//dl[@class='property-list'])/dt[1]"));
				assertEquals(RuntimeVariables.replace("Regular Organization"),
					selenium.getText(
						"xPath=(//dl[@class='property-list'])/dd[1]"));
				assertEquals(RuntimeVariables.replace("Parent Organization"),
					selenium.getText(
						"xPath=(//dl[@class='property-list'])/dt[2]"));
				assertEquals(RuntimeVariables.replace("Test Organization"),
					selenium.getText(
						"xPath=(//dl[@class='property-list'])/dd[2]"));
				assertEquals(RuntimeVariables.replace("Billing"),
					selenium.getText("xPath=(//li[@class='primary'])/em"));
				assertTrue(selenium.isPartialText(
						"xPath=(//li[@class='primary'])",
						"11111 Main Street USA"));
				assertTrue(selenium.isPartialText(
						"xPath=(//li[@class='primary'])", "90210, Cerritos"));
				assertTrue(selenium.isPartialText(
						"xPath=(//li[@class='primary'])", "(Mailing)"));

			case 100:
				label = -1;
			}
		}
	}
}