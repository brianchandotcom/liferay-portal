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
public class ConfigurePortletDefaultTest extends BaseTestCase {
	public void testConfigurePortletDefault() throws Exception {
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
		selenium.waitForVisible("//select[@id='_86_currencyId']");
		selenium.select("//select[@id='_86_currencyId']",
			RuntimeVariables.replace("USD"));
		selenium.select("//select[@id='_86_taxState']",
			RuntimeVariables.replace("California"));
		selenium.type("//input[@id='_86_taxRate']",
			RuntimeVariables.replace("0"));
		selenium.type("//input[@id='_86_minOrder']",
			RuntimeVariables.replace("0"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Shipping Calculation",
			RuntimeVariables.replace("Shipping Calculation"));
		selenium.waitForVisible("//select[@id='_86_shippingFormula']");
		selenium.select("//select[@id='_86_shippingFormula']",
			RuntimeVariables.replace("Flat Amount"));
		selenium.type("//input[@id='_86_shipping0']",
			RuntimeVariables.replace("0"));
		selenium.type("//input[@id='_86_shipping1']",
			RuntimeVariables.replace("0"));
		selenium.type("//input[@id='_86_shipping2']",
			RuntimeVariables.replace("0"));
		selenium.type("//input[@id='_86_shipping3']",
			RuntimeVariables.replace("0"));
		selenium.type("//input[@id='_86_shipping4']",
			RuntimeVariables.replace("0"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Insurance Calculation",
			RuntimeVariables.replace("Insurance Calculation"));
		selenium.waitForVisible("//select[@id='_86_insuranceFormula']");
		selenium.select("//select[@id='_86_insuranceFormula']",
			RuntimeVariables.replace("Flat Amount"));
		selenium.type("//input[@id='_86_insurance0']",
			RuntimeVariables.replace("0"));
		selenium.type("//input[@id='_86_insurance1']",
			RuntimeVariables.replace("0"));
		selenium.type("//input[@id='_86_insurance2']",
			RuntimeVariables.replace("0"));
		selenium.type("//input[@id='_86_insurance3']",
			RuntimeVariables.replace("0"));
		selenium.type("//input[@id='_86_insurance4']",
			RuntimeVariables.replace("0"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.selectFrame("relative=top");
	}
}