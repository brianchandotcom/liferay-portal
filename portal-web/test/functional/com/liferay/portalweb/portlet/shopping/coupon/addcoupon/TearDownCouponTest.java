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

package com.liferay.portalweb.portlet.shopping.coupon.addcoupon;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownCouponTest extends BaseTestCase {
	public void testTearDownCoupon() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Shopping Test Page",
					RuntimeVariables.replace("Shopping Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Coupons",
					RuntimeVariables.replace("Coupons"));
				selenium.waitForPageToLoad("30000");

				boolean shoppingCoupon1Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!shoppingCoupon1Present) {
					label = 2;

					continue;
				}

				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//span[@title='Actions']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this? It will be deleted immediately.");

				boolean shoppingCoupon2Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!shoppingCoupon2Present) {
					label = 3;

					continue;
				}

				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//span[@title='Actions']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this? It will be deleted immediately.");

				boolean shoppingCoupon3Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!shoppingCoupon3Present) {
					label = 4;

					continue;
				}

				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//span[@title='Actions']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this? It will be deleted immediately.");

				boolean shoppingCoupon4Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!shoppingCoupon4Present) {
					label = 5;

					continue;
				}

				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//span[@title='Actions']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this? It will be deleted immediately.");

				boolean shoppingCoupon5Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!shoppingCoupon5Present) {
					label = 6;

					continue;
				}

				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText("//span[@title='Actions']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this? It will be deleted immediately.");

			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
				selenium.waitForVisible("//div[@class='portlet-msg-info']");
				assertEquals(RuntimeVariables.replace("No coupons were found."),
					selenium.getText("//div[@class='portlet-msg-info']"));

			case 100:
				label = -1;
			}
		}
	}
}