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

package com.liferay.portalweb.portlet.wiki.wikipage.addfrontpagechildpagenameduplicate;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddFrontPageChildPageTest extends BaseTestCase {
	public void testAddFrontPageChildPage() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Test Page",
			RuntimeVariables.replace("Wiki Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Add Child Page"),
			selenium.getText(
				"//div[@class='article-actions']/span[contains(.,'Add Child Page')]/a"));
		selenium.clickAt("//div[@class='article-actions']/span[contains(.,'Add Child Page')]/a",
			RuntimeVariables.replace("Add Child Page"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_36_title']",
			RuntimeVariables.replace("Wiki Front Page Child Page Title"));
		selenium.waitForVisible(
			"//a[contains(@class,'cke_button cke_button__cut') and contains(@class,'cke_button_disabled')]");
		selenium.waitForVisible("//iframe[contains(@title,'Rich Text Editor')]");
		selenium.typeFrame("//iframe[contains(@title,'Rich Text Editor')]",
			RuntimeVariables.replace("Wiki Front Page Child Page Content"));
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace(
				"Wiki Front Page Child Page Title"),
			selenium.getText(
				"//div[@class='child-pages']/ul/li[contains(.,'Wiki Front Page Child Page Title')]/a"));
		selenium.clickAt("//div[@class='child-pages']/ul/li[contains(.,'Wiki Front Page Child Page Title')]/a",
			RuntimeVariables.replace("Wiki Front Page Child Page Title"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Wiki Front Page Child Page Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"Wiki Front Page Child Page Content"),
			selenium.getText("//div[@class='wiki-body']/p"));
	}
}