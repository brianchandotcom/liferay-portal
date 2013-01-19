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

package com.liferay.portalweb.socialofficehome.activities.activitiesblockedsouser.sousviewactivitiesblockedsouser3;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs3_AddMicroblogsContent2ViewableByEveryoneTest
	extends BaseTestCase {
	public void testSOUs3_AddMicroblogsContent2ViewableByEveryone()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/socialoffice03/so/dashboard");
		selenium.clickAt("//nav/ul/li[contains(.,'Microblogs')]/a/span",
			RuntimeVariables.replace("Microblogs"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//div[contains(@id,'_1_WAR_microblogsportlet_autocompleteContent')]"));
		assertEquals(RuntimeVariables.replace("Microblogs Post1"),
			selenium.getText("//div[@class='content']"));
		selenium.clickAt("//div[contains(@id,'_1_WAR_microblogsportlet_autocompleteContent')]",
			RuntimeVariables.replace("Update your status..."));
		selenium.waitForElementPresent("//textarea");
		selenium.clickAt("//textarea", RuntimeVariables.replace("Text area"));
		selenium.sendKeys("//textarea",
			RuntimeVariables.replace("Microblogs Post2"));
		selenium.waitForText("//span[@class='microblogs-countdown']", "134");
		assertEquals(RuntimeVariables.replace("134"),
			selenium.getText("//span[@class='microblogs-countdown']"));
		selenium.select("//select[@id='_1_WAR_microblogsportlet_socialRelationType']",
			RuntimeVariables.replace("Everyone"));
		selenium.clickAt("//input[@value='Post']",
			RuntimeVariables.replace("Post"));
		selenium.waitForVisible(
			"xPath=(//div[@class='content'])[contains(.,'Microblogs Post2')]");
		assertEquals(RuntimeVariables.replace("Microblogs Post2"),
			selenium.getText("xPath=(//div[@class='content'])[1]"));
		assertEquals(RuntimeVariables.replace("Comment"),
			selenium.getText("xPath=(//span[@class='action comment']/a)[1]"));
		assertEquals(RuntimeVariables.replace("Microblogs Post1"),
			selenium.getText("xPath=(//div[@class='content'])[2]"));
		assertEquals(RuntimeVariables.replace("Comment"),
			selenium.getText("xPath=(//span[@class='action comment']/a)[2]"));
		assertTrue(selenium.isElementNotPresent(
				"//span[@class='action repost']/a"));
	}
}