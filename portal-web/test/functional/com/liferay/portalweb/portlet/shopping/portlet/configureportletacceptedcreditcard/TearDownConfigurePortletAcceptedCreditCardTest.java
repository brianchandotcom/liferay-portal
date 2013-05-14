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

package com.liferay.portalweb.portlet.shopping.portlet.configureportletacceptedcreditcard;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownConfigurePortletAcceptedCreditCardTest extends BaseTestCase {
	public void testTearDownConfigurePortletAcceptedCreditCard()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Shopping Test Page",
			RuntimeVariables.replace("Shopping Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//span[@title='Options']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
			RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]");
		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]",
			RuntimeVariables.replace("Configuration"));
		selenium.waitForElementPresent(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.selectFrame(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.clickAt("link=Payment Settings",
			RuntimeVariables.replace("Payment Settings"));
		Thread.sleep(5000);
		selenium.waitForVisible("//select[@id='_86_available_cc_types']");
		assertTrue(selenium.isPartialText(
				"//select[@id='_86_available_cc_types']", "MasterCard"));
		selenium.addSelection("//select[@id='_86_available_cc_types']",
			RuntimeVariables.replace("MasterCard"));
		selenium.waitForVisible(
			"//button[@title='Move selected items from Available to Current.']");
		selenium.clickAt("//button[@title='Move selected items from Available to Current.']",
			RuntimeVariables.replace("Left Arrow"));
		selenium.waitForPartialText("//select[@id='_86_current_cc_types']",
			"MasterCard");
		assertTrue(selenium.isPartialText(
				"//select[@id='_86_current_cc_types']", "MasterCard"));
		assertTrue(selenium.isPartialText(
				"//select[@id='_86_available_cc_types']", "American Express"));
		selenium.addSelection("//select[@id='_86_available_cc_types']",
			RuntimeVariables.replace("American Express"));
		selenium.waitForVisible(
			"//button[@title='Move selected items from Available to Current.']");
		selenium.clickAt("//button[@title='Move selected items from Available to Current.']",
			RuntimeVariables.replace("Left Arrow"));
		selenium.waitForPartialText("//select[@id='_86_current_cc_types']",
			"American Express");
		assertTrue(selenium.isPartialText(
				"//select[@id='_86_current_cc_types']", "American Express"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"You have successfully updated the setup."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isPartialText(
				"//select[@id='_86_current_cc_types']", "Discover"));
		assertTrue(selenium.isPartialText(
				"//select[@id='_86_current_cc_types']", "Visa"));
		assertTrue(selenium.isPartialText(
				"//select[@id='_86_current_cc_types']", "MasterCard"));
		assertTrue(selenium.isPartialText(
				"//select[@id='_86_current_cc_types']", "American Express"));
		selenium.selectFrame("relative=top");
	}
}