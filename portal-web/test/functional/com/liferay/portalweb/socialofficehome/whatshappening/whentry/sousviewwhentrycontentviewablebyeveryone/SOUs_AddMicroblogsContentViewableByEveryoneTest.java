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
public class SOUs_AddMicroblogsContentViewableByEveryoneTest
	extends BaseTestCase {
	public void testSOUs_AddMicroblogsContentViewableByEveryone()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/socialoffice01/so/dashboard/");
		selenium.clickAt("//nav/ul/li[contains(.,'Microblogs')]/a/span",
			RuntimeVariables.replace("Microblogs"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//div[contains(@id,'_1_WAR_microblogsportlet_autocompleteContent')]"));
		selenium.clickAt("//div[contains(@id,'_1_WAR_microblogsportlet_autocompleteContent')]",
			RuntimeVariables.replace("Update your status..."));
		selenium.waitForElementPresent("//textarea");
		selenium.clickAt("//textarea", RuntimeVariables.replace("Text area"));
		selenium.sendKeys("//textarea",
			RuntimeVariables.replace("Microblogs Post"));
		selenium.waitForText("//span[@class='microblogs-countdown']", "135");
		assertEquals(RuntimeVariables.replace("135"),
			selenium.getText("//span[@class='microblogs-countdown']"));
		selenium.select("//select[@id='_1_WAR_microblogsportlet_socialRelationType']",
			RuntimeVariables.replace("Everyone"));
		selenium.clickAt("//input[@value='Post']",
			RuntimeVariables.replace("Post"));
		Thread.sleep(1000);
		selenium.waitForText("xpath=(//div[@class='user-name']/span/a)[1]",
			"Social01 Office01 User01");
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
	}
}