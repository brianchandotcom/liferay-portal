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

package com.liferay.portalweb.portlet.messageboards.mbthread.splitthreadmbcategorythreadreplyexplanation;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SplitThreadMBCategoryThreadReplyExplanationTest
	extends BaseTestCase {
	public void testSplitThreadMBCategoryThreadReplyExplanation()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MB Category Name"),
			selenium.getText("//td[1]/a/strong"));
		selenium.clickAt("//td[1]/a/strong",
			RuntimeVariables.replace("MB Category Name"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent(
				"RE: MB Category Thread Message Subject"));
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message Subject"),
			selenium.getText("//td[1]/a"));
		selenium.clickAt("//td[1]/a",
			RuntimeVariables.replace("MB Category Thread Message Subject"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"RE: MB Category Thread Message Subject"),
			selenium.getText(
				"//div[@id='toggle_id_message_boards_view_message_thread']/table/tbody/tr[2]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText(
				"//div[@id='toggle_id_message_boards_view_message_thread']/table/tbody/tr[2]/td[2]/a"));
		assertTrue(selenium.isElementPresent(
				"//div[@id='toggle_id_message_boards_view_message_thread']/table/tbody/tr[2]/td[3]/a"));
		assertEquals(RuntimeVariables.replace(
				"RE: MB Category Thread Message Subject"),
			selenium.getText("xPath=(//div[@class='subject']/a/strong)[2]"));
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message Reply Body"),
			selenium.getText("xPath=(//div[@class='thread-body'])[2]"));
		assertEquals(RuntimeVariables.replace("Split Thread"),
			selenium.getText("//a/span[.='Split Thread']"));
		selenium.clickAt("//a/span[.='Split Thread']",
			RuntimeVariables.replace("Split Thread"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isChecked(
				"//input[@id='_19_addExplanationPostCheckbox']"));
		selenium.clickAt("//input[@id='_19_addExplanationPostCheckbox']",
			RuntimeVariables.replace(
				"Add explanation post to the source thread."));
		assertTrue(selenium.isChecked(
				"//input[@id='_19_addExplanationPostCheckbox']"));
		selenium.waitForVisible("//input[@id='_19_subject']");
		selenium.type("//input[@id='_19_subject']",
			RuntimeVariables.replace(
				"MB Category Thread Message Explanation Subject"));
		selenium.waitForVisible(
			"//a[contains(@class,'cke_button cke_button__unlink') and contains(@class,' cke_button_disabled')]");
		selenium.waitForVisible("//iframe[contains(@title,'Rich Text Editor')]");
		selenium.selectFrame("//iframe[contains(@title,'Rich Text Editor')]");
		assertEquals(RuntimeVariables.replace(
				"The new thread can be found at ${newThreadURL}."),
			selenium.getText("//body"));
		selenium.selectFrame("relative=top");
		selenium.clickAt("//input[@value='OK']", RuntimeVariables.replace("OK"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"RE: MB Category Thread Message Subject"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"RE: MB Category Thread Message Subject"),
			selenium.getText("//div[@class='subject']/a/strong"));
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message Reply Body"),
			selenium.getText("//div[@class='thread-body']"));
	}
}