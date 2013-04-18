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

package com.liferay.portalweb.portal.controlpanel.recyclebin.blogs.restoreblogentriesrecyclebin;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddBlogEntriesTest extends BaseTestCase {
	public void testAddBlogEntries() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Blogs Test Page",
			RuntimeVariables.replace("Blogs Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Blog Entry']	",
			RuntimeVariables.replace("Add Blog Entry"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_33_title']	",
			RuntimeVariables.replace("Blogs Entry1 Title"));
		selenium.waitForVisible(
			"//a[contains(@class,'cke_button cke_button__cut') and contains(@class,'cke_button_disabled')]	");
		selenium.waitForVisible(
			"//iframe[contains(@title,'Rich Text Editor')]	");
		selenium.typeFrame("//iframe[contains(@title,'Rich Text Editor')]	",
			RuntimeVariables.replace("Blogs Entry1 Content"));
		selenium.clickAt("//input[@value='Publish']	",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']	"));
		assertEquals(RuntimeVariables.replace("Blogs Entry1 Title"),
			selenium.getText("//div[@class='entry-title']/h2/a	"));
		assertEquals(RuntimeVariables.replace("Blogs Entry1 Content"),
			selenium.getText("//div[@class='entry-body']/div	"));
		selenium.clickAt("//input[@value='Add Blog Entry']	",
			RuntimeVariables.replace("Add Blog Entry"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_33_title']	",
			RuntimeVariables.replace("Blogs Entry2 Title"));
		selenium.waitForVisible(
			"//a[contains(@class,'cke_button cke_button__cut') and contains(@class,'cke_button_disabled')]	");
		selenium.waitForVisible(
			"//iframe[contains(@title,'Rich Text Editor')]	");
		selenium.typeFrame("//iframe[contains(@title,'Rich Text Editor')]	",
			RuntimeVariables.replace("Blogs Entry2 Content"));
		selenium.clickAt("//input[@value='Publish']	",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']	"));
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Title"),
			selenium.getText("//div[@class='entry-title']/h2/a	"));
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Content"),
			selenium.getText("//div[@class='entry-body']/div	"));
		selenium.clickAt("//input[@value='Add Blog Entry']	",
			RuntimeVariables.replace("Add Blog Entry"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_33_title']	",
			RuntimeVariables.replace("Blogs Entry3 Title"));
		selenium.waitForVisible(
			"//a[contains(@class,'cke_button cke_button__cut') and contains(@class,'cke_button_disabled')]	");
		selenium.waitForVisible(
			"//iframe[contains(@title,'Rich Text Editor')]	");
		selenium.typeFrame("//iframe[contains(@title,'Rich Text Editor')]	",
			RuntimeVariables.replace("Blogs Entry3 Content"));
		selenium.clickAt("//input[@value='Publish']	",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']	"));
		assertEquals(RuntimeVariables.replace("Blogs Entry3 Title"),
			selenium.getText("//div[@class='entry-title']/h2/a	"));
		assertEquals(RuntimeVariables.replace("Blogs Entry3 Content"),
			selenium.getText("//div[@class='entry-body']/div	"));
	}
}