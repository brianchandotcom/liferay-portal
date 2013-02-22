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

package com.liferay.portalweb.portal.controlpanel.socialactivity.usecase.blogsentry;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_ViewUserStatisticsVoteBlogsEntry2Test extends BaseTestCase {
	public void testUser_ViewUserStatisticsVoteBlogsEntry2()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/site-name/");
		selenium.clickAt("link=User Statistics Test Page",
			RuntimeVariables.replace("User Statistics Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForText("xPath=(//span[@class='user-name'])[1]",
			"Joe Bloggs");
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("xPath=(//span[@class='user-name'])[1]"));
		assertEquals(RuntimeVariables.replace("Rank: 1"),
			selenium.getText("xPath=(//div[@class='user-rank'])[1]"));
		assertEquals(RuntimeVariables.replace("Contribution Score: 2"),
			selenium.getText("xPath=(//div[@class='contribution-score'])[1]"));
		assertEquals(RuntimeVariables.replace("Participation Score: 5"),
			selenium.getText("xPath=(//div[@class='participation-score'])[1]"));
		assertEquals(RuntimeVariables.replace("User's Blog Entries: 2"),
			selenium.getText("//div[@class='social-counter-user.blogs']"));
		assertEquals(RuntimeVariables.replace("userfn userln"),
			selenium.getText("xPath=(//span[@class='user-name'])[2]"));
		assertEquals(RuntimeVariables.replace("Rank: 2"),
			selenium.getText("xPath=(//div[@class='user-rank'])[2]"));
		assertEquals(RuntimeVariables.replace("Contribution Score: 0"),
			selenium.getText("xPath=(//div[@class='contribution-score'])[2]"));
		assertEquals(RuntimeVariables.replace("Participation Score: 7"),
			selenium.getText("xPath=(//div[@class='participation-score'])[2]"));
		assertTrue(selenium.isElementPresent(
				"//div[@class='social-counter-user.comments']"));
		assertTrue(selenium.isElementPresent(
				"//div[@class='social-counter-user.votes']"));
		assertEquals(RuntimeVariables.replace("User's Subscriptions: 1"),
			selenium.getText(
				"//div[@class='social-counter-user.subscriptions']"));
	}
}