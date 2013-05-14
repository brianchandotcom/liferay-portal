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

package com.liferay.portalweb.portal.controlpanel.socialactivity.usecase.messageboard;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_VoteMessageBoardThreadSiteTest extends BaseTestCase {
	public void testUser_VoteMessageBoardThreadSite() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/site-name/");
		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MB Thread Message Subject"),
			selenium.getText(
				"//td[@id='_19_mbThreadsSearchContainer_col-thread_row-1']/a"));
		selenium.clickAt("//td[@id='_19_mbThreadsSearchContainer_col-thread_row-1']/a",
			RuntimeVariables.replace("MB Thread Message Subject"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementPresent(
				"//a[contains(@class,'aui-rating-thumb-up')]"));
		selenium.clickAt("//a[contains(@class,'aui-rating-thumb-up')]",
			RuntimeVariables.replace("Rate this as good."));
		selenium.waitForText("//div[@class='rating-label-element']",
			"+1 (1 Vote)");
		assertEquals(RuntimeVariables.replace("+1 (1 Vote)"),
			selenium.getText("//div[@class='rating-label-element']"));
	}
}