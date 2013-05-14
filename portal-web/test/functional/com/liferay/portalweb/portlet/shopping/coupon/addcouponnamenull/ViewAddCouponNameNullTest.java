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

package com.liferay.portalweb.portlet.shopping.coupon.addcouponnamenull;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewAddCouponNameNullTest extends BaseTestCase {
	public void testViewAddCouponNameNull() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Shopping Test Page",
			RuntimeVariables.replace("Shopping Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Coupons", RuntimeVariables.replace("Coupons"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Categories"),
			selenium.getText("//ul[@class='tabview-list']/li[1]"));
		assertEquals(RuntimeVariables.replace("Cart"),
			selenium.getText("//ul[@class='tabview-list']/li[2]"));
		assertEquals(RuntimeVariables.replace("Orders"),
			selenium.getText("//ul[@class='tabview-list']/li[3]"));
		assertEquals(RuntimeVariables.replace("Coupons"),
			selenium.getText("//ul[@class='tabview-list']/li[4]"));
		assertEquals(RuntimeVariables.replace("Code"),
			selenium.getText(
				"//div[@class='fieldset-content ']/div[1]/div/span[1]/span/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='fieldset-content ']/div[1]/div/span[1]/span/span/input"));
		assertTrue(selenium.isVisible(
				"//div[@class='fieldset-content ']/div[1]/div/span[2]/span/span/select"));
		assertEquals(RuntimeVariables.replace("Discount Type"),
			selenium.getText(
				"//div[@class='fieldset-content ']/div[2]/div/span/span/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='fieldset-content ']/div[2]/div/span/span/span/select"));
		assertEquals(RuntimeVariables.replace("Active"),
			selenium.getText(
				"//div[@class='fieldset-content ']/div[3]/div/span/span/label"));
		assertTrue(selenium.isVisible(
				"//div[@class='fieldset-content ']/div[3]/div/span/span/span/select"));
		assertEquals("Search",
			selenium.getValue(
				"xPath=(//div[@class='button-holder ']/span[1]/span/input)[1]"));
		assertEquals("Add Coupon",
			selenium.getValue(
				"xPath=(//div[@class='button-holder ']/span[2]/span/input)[1]"));
		assertEquals("Delete",
			selenium.getValue(
				"xPath=(//div[@class='button-holder ']/span[1]/span/input)[2]"));
		selenium.waitForVisible("//div[@class='portlet-msg-info']");
		assertEquals(RuntimeVariables.replace("No coupons were found."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertFalse(selenium.isTextPresent("Shopping Coupon Description"));
	}
}