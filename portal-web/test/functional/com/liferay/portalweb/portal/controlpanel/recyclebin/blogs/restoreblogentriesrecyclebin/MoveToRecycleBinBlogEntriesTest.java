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
public class MoveToRecycleBinBlogEntriesTest extends BaseTestCase {
	public void testMoveToRecycleBinBlogEntries() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Blogs Test Page",
			RuntimeVariables.replace("Blogs Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Blogs Entry1 Title"),
			selenium.getText(
				"//div[@class='entry-title']/h2[contains(.,'Blogs Entry1 Title')]/a	"));
		selenium.waitForVisible(
			"xPath=(//div[@class='lfr-meta-actions edit-actions entry']/table/tbody/tr[contains(.,'Move to the Recycle Bin')]/td[3]/span/a)[3]	");
		assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
			selenium.getText(
				"xPath=(//div[@class='lfr-meta-actions edit-actions entry']/table/tbody/tr[contains(.,'Move to the Recycle Bin')]/td[3]/span/a)[3]	"));
		selenium.clickAt("xPath=(//div[@class='lfr-meta-actions edit-actions entry']/table/tbody/tr[contains(.,'Move to the Recycle Bin')]/td[3]/span/a)[3]	",
			RuntimeVariables.replace("Move to the Recycle Bin"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"The selected item was moved to the Recycle Bin. Undo"),
			selenium.getText(
				"//div[@class='portlet-msg-success taglib-trash-undo']	"));
		assertFalse(selenium.isTextPresent("Blogs Entry1 Title	"));
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Title"),
			selenium.getText(
				"//div[@class='entry-title']/h2[contains(.,'Blogs Entry2 Title')]/a	"));
		selenium.waitForVisible(
			"xPath=(//div[@class='lfr-meta-actions edit-actions entry']/table/tbody/tr[contains(.,'Move to the Recycle Bin')]/td[3]/span/a)[2]	");
		assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
			selenium.getText(
				"xPath=(//div[@class='lfr-meta-actions edit-actions entry']/table/tbody/tr[contains(.,'Move to the Recycle Bin')]/td[3]/span/a)[2]	"));
		selenium.clickAt("xPath=(//div[@class='lfr-meta-actions edit-actions entry']/table/tbody/tr[contains(.,'Move to the Recycle Bin')]/td[3]/span/a)[2]	",
			RuntimeVariables.replace("Move to the Recycle Bin"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"The selected item was moved to the Recycle Bin. Undo"),
			selenium.getText(
				"//div[@class='portlet-msg-success taglib-trash-undo']	"));
		assertFalse(selenium.isTextPresent("Blogs Entry2 Title	"));
		assertEquals(RuntimeVariables.replace("Blogs Entry3 Title"),
			selenium.getText(
				"//div[@class='entry-title']/h2[contains(.,'Blogs Entry3 Title')]/a	"));
		selenium.waitForVisible(
			"xPath=(//div[@class='lfr-meta-actions edit-actions entry']/table/tbody/tr[contains(.,'Move to the Recycle Bin')]/td[3]/span/a)[1]	");
		assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
			selenium.getText(
				"xPath=(//div[@class='lfr-meta-actions edit-actions entry']/table/tbody/tr[contains(.,'Move to the Recycle Bin')]/td[3]/span/a)[1]	"));
		selenium.clickAt("xPath=(//div[@class='lfr-meta-actions edit-actions entry']/table/tbody/tr[contains(.,'Move to the Recycle Bin')]/td[3]/span/a)[1]	",
			RuntimeVariables.replace("Move to the Recycle Bin"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"The selected item was moved to the Recycle Bin. Undo"),
			selenium.getText(
				"//div[@class='portlet-msg-success taglib-trash-undo']	"));
		assertFalse(selenium.isTextPresent("Blogs Entry3 Title	"));
	}
}