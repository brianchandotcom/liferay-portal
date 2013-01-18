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

package com.liferay.portalweb.portlet.webcontentdisplay.webcontent.selectportletscopepage2wcwebcontentwcd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SelectPortletScopePage2WCWebContentWCDTest extends BaseTestCase {
	public void testSelectPortletScopePage2WCWebContentWCD()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Web Content Display Test Page3",
			RuntimeVariables.replace("Web Content Display Test Page3"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Select existing web content or add some web content to be displayed in this portlet."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		selenium.waitForVisible(
			"//span[@class='icon-action icon-action-configuration']/a/span");
		assertEquals(RuntimeVariables.replace("Select Web Content"),
			selenium.getText(
				"//span[@class='icon-action icon-action-configuration']/a/span"));
		selenium.clickAt("//span[@class='icon-action icon-action-configuration']/a/span",
			RuntimeVariables.replace("Select Web Content"));
		selenium.waitForElementPresent(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.selectFrame(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible(
			"//tr[contains(.,'WC WebContent Title')]/td[2]/a");
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText("//tr[contains(.,'WC WebContent Title')]/td[2]/a"));
		selenium.clickAt("//tr[contains(.,'WC WebContent Title')]/td[2]/a",
			RuntimeVariables.replace("WC WebContent Title"));
		selenium.waitForPartialText("//form[@id='_86_fm1']/div[1]/span[2]",
			"Displaying Content: WC WebContent Title");
		assertTrue(selenium.isPartialText(
				"//form[@id='_86_fm1']/div[1]/span[2]",
				"Displaying Content: WC WebContent Title"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.selectFrame("relative=top");
	}
}