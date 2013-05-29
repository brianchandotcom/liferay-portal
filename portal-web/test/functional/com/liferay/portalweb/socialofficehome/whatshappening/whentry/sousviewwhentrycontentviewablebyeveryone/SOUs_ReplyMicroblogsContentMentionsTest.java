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
public class SOUs_ReplyMicroblogsContentMentionsTest extends BaseTestCase {
	public void testSOUs_ReplyMicroblogsContentMentions()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/socialoffice01/so/dashboard/");
		selenium.waitForVisible("//nav/ul/li[contains(.,'Microblogs')]/a/span");
		selenium.clickAt("//nav/ul/li[contains(.,'Microblogs')]/a/span",
			RuntimeVariables.replace("Microblogs"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Comment"),
			selenium.getText("//span[@class='action comment']/a"));
		selenium.clickAt("//span[@class='action comment']/a",
			RuntimeVariables.replace("Comment"));
		selenium.waitForVisible(
			"xPath=(//div[@class='autocomplete textarea'])[2]");
		Thread.sleep(1000);
		selenium.clickAt("xPath=(//div[@class='autocomplete textarea'])[2]",
			RuntimeVariables.replace("Leave a comment..."));
		selenium.waitForVisible("//textarea");
		selenium.clickAt("//textarea",
			RuntimeVariables.replace("Leave a comment..."));
		selenium.sendKeys("//textarea",
			RuntimeVariables.replace("Microblogs Post Comment"));
		selenium.waitForText("xPath=(//span[@class='microblogs-countdown'])[2]",
			"127");
		assertEquals(RuntimeVariables.replace("127"),
			selenium.getText("xPath=(//span[@class='microblogs-countdown'])[2]"));
		selenium.clickAt("xPath=(//input[@value='Post'])[2]",
			RuntimeVariables.replace("Post"));
		selenium.waitForVisible("xpath=(//div[@class='user-name']/span/a)[2]");
		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText("xpath=(//div[@class='user-name']/span/a)[2]"));
		assertEquals(RuntimeVariables.replace("Microblogs Post Comment"),
			selenium.getText("xpath=(//div[@class='content']/span)[2]"));
	}
}