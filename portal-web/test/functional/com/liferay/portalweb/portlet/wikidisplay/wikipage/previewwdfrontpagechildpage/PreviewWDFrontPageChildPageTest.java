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

package com.liferay.portalweb.portlet.wikidisplay.wikipage.previewwdfrontpagechildpage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class PreviewWDFrontPageChildPageTest extends BaseTestCase {
	public void testPreviewWDFrontPageChildPage() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Display Test Page",
			RuntimeVariables.replace("Wiki Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Add Child Page"),
			selenium.getText(
				"//div[@class='article-actions']/span/a[contains(.,'Add Child Page')]"));
		selenium.clickAt("//div[@class='article-actions']/span/a[contains(.,'Add Child Page')]",
			RuntimeVariables.replace("Add Child Page"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[contains(@id,'_title')]",
			RuntimeVariables.replace("Wiki FrontPage ChildPage Title"));
		selenium.waitForVisible(
			"//a[contains(@class,'cke_button cke_button__cut') and contains(@class,'cke_button_disabled')]	");
		selenium.waitForVisible("//iframe[contains(@title,'Rich Text Editor')]");
		selenium.typeFrame("//iframe[contains(@title,'Rich Text Editor')]",
			RuntimeVariables.replace("Wiki FrontPage ChildPage Content"));
		selenium.clickAt("//input[@value='Preview']",
			RuntimeVariables.replace("Preview"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent("//div[@id='cke_1_contents']/iframe");
		assertFalse(selenium.isTextPresent(
				"Your request completed successfully."));
		assertEquals(RuntimeVariables.replace("Wiki FrontPage ChildPage Title"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"Wiki FrontPage ChildPage Content"),
			selenium.getText(
				"//div[@class='preview']/div[@class='wiki-body']/p"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Display Test Page",
			RuntimeVariables.replace("Wiki Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent("Wiki FrontPage ChildPage Title"));
		assertFalse(selenium.isTextPresent("Wiki FrontPage ChildPage Content"));
	}
}