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

package com.liferay.portalweb.socialoffice.users.user.viewfootertext;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_ViewFooterTextTest extends BaseTestCase {
	public void testSOUs_ViewFooterText() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/user/socialoffice01/so/dashboard");
				assertTrue(selenium.isVisible("//input[@class='search-input']"));
				selenium.type("//input[@class='search-input']",
					RuntimeVariables.replace("Open"));
				Thread.sleep(1000);
				assertEquals(RuntimeVariables.replace("Open Site Name"),
					selenium.getText(
						"//li[contains(@class, 'social-office-enabled')]/span[2]/a"));
				selenium.clickAt("//li[contains(@class, 'social-office-enabled')]/span[2]/a",
					RuntimeVariables.replace("Open Site Name"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Documents"),
					selenium.getText(
						"//nav/ul/li[contains(.,'Documents')]/a/span"));
				selenium.clickAt("//nav/ul/li[contains(.,'Documents')]/a/span",
					RuntimeVariables.replace("Documents"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//a[contains(@class,'document-link')]/span[@class='entry-title']",
					RuntimeVariables.replace("DM Document Title"));
				selenium.waitForPageToLoad("30000");

				boolean versionNumberEEPresent = selenium.isElementPresent(
						"//span[@class='version-number']");

				if (versionNumberEEPresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/dockbar_underlay.js')]");
				assertEquals(RuntimeVariables.replace("Dashboard"),
					selenium.getText("//a[contains(.,'Dashboard')]"));
				selenium.clickAt("//a[contains(.,'Dashboard')]",
					RuntimeVariables.replace("Dashboard"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Powered By Liferay"),
					selenium.getText("//p[@class='powered-by']"));
				selenium.clickAt("//nav/ul/li[contains(.,'Contacts Center')]/a/span",
					RuntimeVariables.replace("Contacts Center"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Powered By Liferay"),
					selenium.getText("//p[@class='powered-by']"));
				selenium.clickAt("//nav/ul/li[contains(.,'Microblogs')]/a/span",
					RuntimeVariables.replace("Microblogs"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Powered By Liferay"),
					selenium.getText("//p[@class='powered-by']"));
				selenium.clickAt("//nav/ul/li[contains(.,'Messages')]/a/span",
					RuntimeVariables.replace("Messages"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Powered By Liferay"),
					selenium.getText("//p[@class='powered-by']"));
				selenium.clickAt("//nav/ul/li[contains(.,'My Documents')]/a/span",
					RuntimeVariables.replace("My Documents"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Powered By Liferay"),
					selenium.getText("//p[@class='powered-by']"));
				selenium.clickAt("//nav/ul/li[contains(.,'Tasks')]/a/span",
					RuntimeVariables.replace("Tasks"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Powered By Liferay"),
					selenium.getText("//p[@class='powered-by']"));
				selenium.clickAt("//nav/ul/li[contains(.,'Dashboard')]/a/span",
					RuntimeVariables.replace("Dashboard"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Powered By Liferay"),
					selenium.getText("//p[@class='powered-by']"));
				assertEquals(RuntimeVariables.replace("Sites"),
					selenium.getText("//div[@id='so-sidebar']/h3"));
				assertTrue(selenium.isVisible("//input[@class='search-input']"));
				selenium.type("//input[@class='search-input']",
					RuntimeVariables.replace("Open"));
				Thread.sleep(1000);
				assertEquals(RuntimeVariables.replace("Open Site Name"),
					selenium.getText(
						"//li[contains(@class, 'social-office-enabled')]/span[2]/a"));
				selenium.clickAt("//li[contains(@class, 'social-office-enabled')]/span[2]/a",
					RuntimeVariables.replace("Open Site Name"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//nav/ul/li[contains(.,'Calendar')]/a/span",
					RuntimeVariables.replace("Calendar"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Powered By Liferay"),
					selenium.getText("//p[@class='powered-by']"));
				selenium.clickAt("//nav/ul/li[contains(.,'Documents')]/a/span",
					RuntimeVariables.replace("Documents"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Powered By Liferay"),
					selenium.getText("//p[@class='powered-by']"));
				selenium.clickAt("//nav/ul/li[contains(.,'Forums')]/a/span",
					RuntimeVariables.replace("Forums"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Powered By Liferay"),
					selenium.getText("//p[@class='powered-by']"));
				selenium.clickAt("//nav/ul/li[contains(.,'Blogs')]/a/span",
					RuntimeVariables.replace("Blogs"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Powered By Liferay"),
					selenium.getText("//p[@class='powered-by']"));
				selenium.clickAt("//nav/ul/li[contains(.,'Wiki')]/a/span",
					RuntimeVariables.replace("Wiki"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Powered By Liferay"),
					selenium.getText("//p[@class='powered-by']"));
				selenium.clickAt("//nav/ul/li[contains(.,'Members')]/a/span",
					RuntimeVariables.replace("Members"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Powered By Liferay"),
					selenium.getText("//p[@class='powered-by']"));
				selenium.clickAt("//nav/ul/li[contains(.,'Home')]/a/span",
					RuntimeVariables.replace("Home"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Powered By Liferay"),
					selenium.getText("//p[@class='powered-by']"));
				selenium.open("/web/socialoffice01/so/profile");
				assertEquals(RuntimeVariables.replace("Powered By Liferay"),
					selenium.getText("//p[@class='powered-by']"));
				selenium.clickAt("//nav/ul/li[contains(.,'Contacts')]/a/span",
					RuntimeVariables.replace("Contacts"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Powered By Liferay"),
					selenium.getText("//p[@class='powered-by']"));
				selenium.clickAt("//nav/ul/li[contains(.,'Microblogs')]/a/span",
					RuntimeVariables.replace("Microblogs"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Powered By Liferay"),
					selenium.getText("//p[@class='powered-by']"));
				selenium.clickAt("//nav/ul/li[contains(.,'Profile')]/a/span",
					RuntimeVariables.replace("Profile"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Powered By Liferay"),
					selenium.getText("//p[@class='powered-by']"));

			case 2:
				selenium.open("/user/socialoffice01/so/dashboard");
				assertTrue(selenium.isVisible("//input[@class='search-input']"));
				selenium.type("//input[@class='search-input']",
					RuntimeVariables.replace("Open"));
				Thread.sleep(1000);
				assertEquals(RuntimeVariables.replace("Open Site Name"),
					selenium.getText(
						"//li[contains(@class, 'social-office-enabled')]/span[2]/a"));
				selenium.clickAt("//li[contains(@class, 'social-office-enabled')]/span[2]/a",
					RuntimeVariables.replace("Open Site Name"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Documents"),
					selenium.getText(
						"//nav/ul/li[contains(.,'Documents')]/a/span"));
				selenium.clickAt("//nav/ul/li[contains(.,'Documents')]/a/span",
					RuntimeVariables.replace("Documents"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//a[contains(@class,'document-link')]/span[@class='entry-title']",
					RuntimeVariables.replace("DM Document Title"));
				selenium.waitForPageToLoad("30000");

				boolean versionNumberCEPresent = selenium.isElementPresent(
						"//span[@class='version-number']");

				if (!versionNumberCEPresent) {
					label = 3;

					continue;
				}

				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/dockbar_underlay.js')]");
				assertEquals(RuntimeVariables.replace("Dashboard"),
					selenium.getText("//a[contains(.,'Dashboard')]"));
				selenium.clickAt("//a[contains(.,'Dashboard')]",
					RuntimeVariables.replace("Dashboard"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementNotPresent(
						"//p[@class='powered-by']"));
				assertFalse(selenium.isTextPresent("Powered By Liferay"));
				selenium.clickAt("//nav/ul/li[contains(.,'Contacts Center')]/a/span",
					RuntimeVariables.replace("Contacts Center"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementNotPresent(
						"//p[@class='powered-by']"));
				assertFalse(selenium.isTextPresent("Powered By Liferay"));
				selenium.clickAt("//nav/ul/li[contains(.,'Microblogs')]/a/span",
					RuntimeVariables.replace("Microblogs"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementNotPresent(
						"//p[@class='powered-by']"));
				assertFalse(selenium.isTextPresent("Powered By Liferay"));
				selenium.clickAt("//nav/ul/li[contains(.,'Messages')]/a/span",
					RuntimeVariables.replace("Messages"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementNotPresent(
						"//p[@class='powered-by']"));
				assertFalse(selenium.isTextPresent("Powered By Liferay"));
				selenium.clickAt("//nav/ul/li[contains(.,'My Documents')]/a/span",
					RuntimeVariables.replace("My Documents"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementNotPresent(
						"//p[@class='powered-by']"));
				assertFalse(selenium.isTextPresent("Powered By Liferay"));
				selenium.clickAt("//nav/ul/li[contains(.,'Tasks')]/a/span",
					RuntimeVariables.replace("Tasks"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementNotPresent(
						"//p[@class='powered-by']"));
				assertFalse(selenium.isTextPresent("Powered By Liferay"));
				selenium.clickAt("//nav/ul/li[contains(.,'Dashboard')]/a/span",
					RuntimeVariables.replace("Dashboard"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementNotPresent(
						"//p[@class='powered-by']"));
				assertFalse(selenium.isTextPresent("Powered By Liferay"));
				assertEquals(RuntimeVariables.replace("Sites"),
					selenium.getText("//div[@id='so-sidebar']/h3"));
				assertTrue(selenium.isVisible("//input[@class='search-input']"));
				selenium.type("//input[@class='search-input']",
					RuntimeVariables.replace("Open"));
				Thread.sleep(1000);
				assertEquals(RuntimeVariables.replace("Open Site Name"),
					selenium.getText(
						"//li[contains(@class, 'social-office-enabled')]/span[2]/a"));
				selenium.clickAt("//li[contains(@class, 'social-office-enabled')]/span[2]/a",
					RuntimeVariables.replace("Open Site Name"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//nav/ul/li[contains(.,'Calendar')]/a/span",
					RuntimeVariables.replace("Calendar"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementNotPresent(
						"//p[@class='powered-by']"));
				assertFalse(selenium.isTextPresent("Powered By Liferay"));
				selenium.clickAt("//nav/ul/li[contains(.,'Documents')]/a/span",
					RuntimeVariables.replace("Documents"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementNotPresent(
						"//p[@class='powered-by']"));
				assertFalse(selenium.isTextPresent("Powered By Liferay"));
				selenium.clickAt("//nav/ul/li[contains(.,'Forums')]/a/span",
					RuntimeVariables.replace("Forums"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementNotPresent(
						"//p[@class='powered-by']"));
				assertFalse(selenium.isTextPresent("Powered By Liferay"));
				selenium.clickAt("//nav/ul/li[contains(.,'Blogs')]/a/span",
					RuntimeVariables.replace("Blogs"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementNotPresent(
						"//p[@class='powered-by']"));
				assertFalse(selenium.isTextPresent("Powered By Liferay"));
				selenium.clickAt("//nav/ul/li[contains(.,'Wiki')]/a/span",
					RuntimeVariables.replace("Wiki"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementNotPresent(
						"//p[@class='powered-by']"));
				assertFalse(selenium.isTextPresent("Powered By Liferay"));
				selenium.clickAt("//nav/ul/li[contains(.,'Members')]/a/span",
					RuntimeVariables.replace("Members"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementNotPresent(
						"//p[@class='powered-by']"));
				assertFalse(selenium.isTextPresent("Powered By Liferay"));
				selenium.clickAt("//nav/ul/li[contains(.,'Home')]/a/span",
					RuntimeVariables.replace("Home"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementNotPresent(
						"//p[@class='powered-by']"));
				assertFalse(selenium.isTextPresent("Powered By Liferay"));
				selenium.open("/web/joebloggs/so/profile");
				assertTrue(selenium.isElementNotPresent(
						"//p[@class='powered-by']"));
				assertFalse(selenium.isTextPresent("Powered By Liferay"));
				selenium.clickAt("//nav/ul/li[contains(.,'Contacts')]/a/span",
					RuntimeVariables.replace("Contacts"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementNotPresent(
						"//p[@class='powered-by']"));
				assertFalse(selenium.isTextPresent("Powered By Liferay"));
				selenium.clickAt("//nav/ul/li[contains(.,'Microblogs')]/a/span",
					RuntimeVariables.replace("Microblogs"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementNotPresent(
						"//p[@class='powered-by']"));
				assertFalse(selenium.isTextPresent("Powered By Liferay"));
				selenium.clickAt("//nav/ul/li[contains(.,'Welcome')]/a/span",
					RuntimeVariables.replace("Welcome"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementNotPresent(
						"//p[@class='powered-by']"));
				assertFalse(selenium.isTextPresent("Powered By Liferay"));
				selenium.clickAt("//nav/ul/li[contains(.,'Profile')]/a/span",
					RuntimeVariables.replace("Profile"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isElementNotPresent(
						"//p[@class='powered-by']"));
				assertFalse(selenium.isTextPresent("Powered By Liferay"));

			case 3:
			case 100:
				label = -1;
			}
		}
	}
}