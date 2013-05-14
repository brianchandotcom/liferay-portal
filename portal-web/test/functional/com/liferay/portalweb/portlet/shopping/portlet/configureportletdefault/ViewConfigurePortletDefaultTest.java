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

package com.liferay.portalweb.portlet.shopping.portlet.configureportletdefault;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewConfigurePortletDefaultTest extends BaseTestCase {
	public void testViewConfigurePortletDefault() throws Exception {
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
		selenium.waitForVisible("//select[@id='_86_current_cc_types']");
		assertTrue(selenium.isPartialText(
				"//select[@id='_86_current_cc_types']", "American Express"));
		assertTrue(selenium.isPartialText(
				"//select[@id='_86_current_cc_types']", "Discover"));
		assertTrue(selenium.isPartialText(
				"//select[@id='_86_current_cc_types']", "MasterCard"));
		assertTrue(selenium.isPartialText(
				"//select[@id='_86_current_cc_types']", "Visa"));
		assertEquals("USD",
			selenium.getSelectedLabel("//select[@id='_86_currencyId']"));
		assertEquals("California",
			selenium.getSelectedLabel("//select[@id='_86_taxState']"));
		assertEquals("0.000%", selenium.getValue("//input[@id='_86_taxRate']"));
		assertEquals("0.00", selenium.getValue("//input[@id='_86_minOrder']"));
		selenium.clickAt("link=Shipping Calculation",
			RuntimeVariables.replace("Shipping Calculation"));
		selenium.waitForVisible("//select[@id='_86_shippingFormula']");
		assertEquals("Flat Amount",
			selenium.getSelectedLabel("//select[@id='_86_shippingFormula']"));
		assertEquals("0.0", selenium.getValue("//input[@id='_86_shipping0']"));
		assertEquals("0.0", selenium.getValue("//input[@id='_86_shipping1']"));
		assertEquals("0.0", selenium.getValue("//input[@id='_86_shipping2']"));
		assertEquals("0.0", selenium.getValue("//input[@id='_86_shipping3']"));
		assertEquals("0.0", selenium.getValue("//input[@id='_86_shipping4']"));
		selenium.clickAt("link=Insurance Calculation",
			RuntimeVariables.replace("Insurance Calculation"));
		selenium.waitForVisible("//select[@id='_86_insuranceFormula']");
		assertEquals("Flat Amount",
			selenium.getSelectedLabel("//select[@id='_86_insuranceFormula']"));
		assertEquals("0.0", selenium.getValue("//input[@id='_86_insurance0']"));
		assertEquals("0.0", selenium.getValue("//input[@id='_86_insurance1']"));
		assertEquals("0.0", selenium.getValue("//input[@id='_86_insurance2']"));
		assertEquals("0.0", selenium.getValue("//input[@id='_86_insurance3']"));
		assertEquals("0.0", selenium.getValue("//input[@id='_86_insurance4']"));
		selenium.selectFrame("relative=top");
	}
}