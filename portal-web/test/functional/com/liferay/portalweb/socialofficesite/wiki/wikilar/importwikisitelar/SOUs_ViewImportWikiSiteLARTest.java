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

package com.liferay.portalweb.socialofficesite.wiki.wikilar.importwikisitelar;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_ViewImportWikiSiteLARTest extends BaseTestCase {
	public void testSOUs_ViewImportWikiSiteLAR() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/socialoffice01/so/dashboard/");
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
		assertEquals(RuntimeVariables.replace("Wiki"),
			selenium.getText("//nav/ul/li[contains(.,'Wiki')]/a/span"));
		selenium.clickAt("//nav/ul/li[contains(.,'Wiki')]/a/span",
			RuntimeVariables.replace("Wiki"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("FrontPage"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Wiki FrontPage Content"),
			selenium.getText("//div[@class='wiki-body']/p"));
		assertEquals(RuntimeVariables.replace("Wiki Front Page Comment Body"),
			selenium.getText("//div/div[3]/div/div[1]"));
		assertTrue(selenium.isElementPresent(
				"//img[@alt='The average rating is 4.0 stars out of 5.']"));
		assertEquals(RuntimeVariables.replace("Average (1 Vote)"),
			selenium.getText("xPath=(//div[@class='rating-label-element'])[2]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='child-pages']/ul/li/a"));
		assertFalse(selenium.isTextPresent("Wiki Front Page Child Page Title"));
		assertEquals(RuntimeVariables.replace("2 Attachments"),
			selenium.getText("//a[contains(.,'2 Attachments')]"));
		selenium.clickAt("//a[contains(.,'2 Attachments')]",
			RuntimeVariables.replace("2 Attachments"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Document_1.jpg"),
			selenium.getText("//td[contains(.,'Document_1')]/a"));
		assertEquals(RuntimeVariables.replace("Document_2.jpg"),
			selenium.getText("//td[contains(.,'Document_2')]/a"));
		assertEquals(RuntimeVariables.replace("Showing 2 results."),
			selenium.getText("//div[@class='search-results']"));
	}
}