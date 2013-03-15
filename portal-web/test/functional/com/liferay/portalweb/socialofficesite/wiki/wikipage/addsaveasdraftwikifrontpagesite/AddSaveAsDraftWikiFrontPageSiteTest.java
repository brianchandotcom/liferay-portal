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

package com.liferay.portalweb.socialofficesite.wiki.wikipage.addsaveasdraftwikifrontpagesite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddSaveAsDraftWikiFrontPageSiteTest extends BaseTestCase {
	public void testAddSaveAsDraftWikiFrontPageSite() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/so/dashboard/");
		assertEquals(RuntimeVariables.replace("Sites"),
			selenium.getText("//div[@id='so-sidebar']/h3"));
		assertTrue(selenium.isVisible("//input[@class='search-input']"));
		selenium.type("//input[@class='search-input']",
			RuntimeVariables.replace("Open"));
		Thread.sleep(1000);
		selenium.clickAt("//li[contains(@class, 'social-office-enabled')]/span[2]/a",
			RuntimeVariables.replace("Open Site Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Wiki"),
			selenium.getText("//nav/ul/li[contains(.,'Wiki')]/a/span"));
		selenium.clickAt("//nav/ul/li[contains(.,'Wiki')]/a/span",
			RuntimeVariables.replace("Wiki"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"This page is empty. Edit it to add some text."),
			selenium.getText("//div[@class='wiki-body']/div/a"));
		assertTrue(selenium.isElementNotPresent("//div[@class='wiki-body']/p"));
		assertFalse(selenium.isTextPresent("Wiki FrontPage Content"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='page-actions top-actions']/span/a/span[contains(.,'Edit')]"));
		selenium.clickAt("//div[@class='page-actions top-actions']/span/a/span[contains(.,'Edit')]",
			RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Status: Draft"),
			selenium.getText("//span[@class='workflow-status']"));
		selenium.waitForElementPresent(
			"//body[@class='html-editor portlet portlet-wiki cke_show_borders']/p");
		assertEquals(RuntimeVariables.replace("Wiki FrontPage Content"),
			selenium.getText(
				"//body[@class='html-editor portlet portlet-wiki cke_show_borders']/p"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Wiki FrontPage Content"),
			selenium.getText("//div[@class='wiki-body']/p"));
	}
}