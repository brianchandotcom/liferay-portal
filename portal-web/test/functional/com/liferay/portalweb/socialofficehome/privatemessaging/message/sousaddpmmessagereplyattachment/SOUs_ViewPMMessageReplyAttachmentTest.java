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

package com.liferay.portalweb.socialofficehome.privatemessaging.message.sousaddpmmessagereplyattachment;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_ViewPMMessageReplyAttachmentTest extends BaseTestCase {
	public void testSOUs_ViewPMMessageReplyAttachment()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/socialoffice01/so/dashboard");
		selenium.waitForVisible("//nav/ul/li[contains(.,'Messages')]/a/span");
		selenium.clickAt("//nav/ul/li[contains(.,'Messages')]/a/span",
			RuntimeVariables.replace("Messages"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Private Messaging"),
			selenium.getText("//span[@class='portlet-title-default']"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//span[@class='author-sender']"));
		assertEquals(RuntimeVariables.replace("Message Subject"),
			selenium.getText("//div[@class='subject']"));
		assertEquals(RuntimeVariables.replace("Message Subject Reply"),
			selenium.getText("//div[@class='body']"));
		assertEquals(RuntimeVariables.replace("Showing 1 result."),
			selenium.getText("//div[@class='search-results']"));
		selenium.clickAt("//div[@class='subject']",
			RuntimeVariables.replace("Message Subject"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Message Subject"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Between Joe Bloggs And you"),
			selenium.getText(
				"//div[@class='layout-content thread-info-content ']"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText(
				"xPath=(//span[@class='name'])[contains(.,'Joe Bloggs')]"));
		assertEquals(RuntimeVariables.replace("Message Body"),
			selenium.getText("xPath=(//div[@class='body'])[1]"));
		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText("xPath=(//span[@class='name'])[3]"));
		assertTrue(selenium.isPartialText("xPath=(//div[@class='body'])[2]",
				"Message Subject Reply"));
		assertTrue(selenium.isVisible("//div[@class='image-thumbnail']/a/img"));
		assertEquals(RuntimeVariables.replace("PM_Attachment.jpg"),
			selenium.getText("//td[2]/a"));
	}
}