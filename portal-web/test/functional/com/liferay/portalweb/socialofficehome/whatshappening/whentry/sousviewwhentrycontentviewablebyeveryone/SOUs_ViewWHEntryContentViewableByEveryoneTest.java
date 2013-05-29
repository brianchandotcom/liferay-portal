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

package com.liferay.portalweb.socialofficehome.whatshappening.whentry.sousviewwhentrycontentviewablebyeveryone;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_ViewWHEntryContentViewableByEveryoneTest extends BaseTestCase {
	public void testSOUs_ViewWHEntryContentViewableByEveryone()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/socialoffice01/so/dashboard/");
		assertEquals(RuntimeVariables.replace("Microblogs Status Update"),
			selenium.getText("//span[@class='portlet-title-default']"));
		assertTrue(selenium.isElementPresent(
				"//div[contains(@id,'_2_WAR_microblogsportlet_autocompleteContent')]"));
		assertEquals(RuntimeVariables.replace("Microblogs Post"),
			selenium.getText("//div[@class='content']"));
		assertFalse(selenium.isTextPresent("Microblogs Post Comment"));
		selenium.clickAt("//nav/ul/li[contains(.,'Microblogs')]/a/span",
			RuntimeVariables.replace("Microblogs"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//div[contains(@id,'_1_WAR_microblogsportlet_autocompleteContent')]"));
		selenium.waitForVisible("xpath=(//div[@class='user-name']/span/a)[1]");
		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText("xpath=(//div[@class='user-name']/span/a)[1]"));
		assertEquals(RuntimeVariables.replace("Microblogs Post"),
			selenium.getText("xpath=(//div[@class='content'])[1]"));
		assertEquals(RuntimeVariables.replace("Comment"),
			selenium.getText("xpath=(//span[@class='action comment']/a)[1]"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText("xpath=(//span[@class='action edit']/a)[1]"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText("xpath=(//span[@class='action delete']/a)[1]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("xpath=(//div[@class='user-name']/span/a)[2]"));
		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText("xpath=(//div[@class='content'])[2]"));
		assertEquals(RuntimeVariables.replace("1 Comment"),
			selenium.getText("xpath=(//span[@class='action comment']/a)[2]"));
		assertEquals(RuntimeVariables.replace("Repost"),
			selenium.getText("//span[@class='action repost']/a"));
	}
}