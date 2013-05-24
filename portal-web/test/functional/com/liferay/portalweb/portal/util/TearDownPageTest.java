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

package com.liferay.portalweb.portal.util;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownPageTest extends BaseTestCase {
	public void testTearDownPage() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("//nav[@id='navigation']",
					RuntimeVariables.replace("Navigation"));
				selenium.waitForVisible(
					"xPath=(//li[contains(@class,'lfr-nav-deletable')])[1]/a/span");

				boolean page1Present = selenium.isElementPresent(
						"xPath=(//li[contains(@class,'lfr-nav-deletable')])[2]/a/span");

				if (!page1Present) {
					label = 2;

					continue;
				}

				selenium.mouseOver(
					"xPath=(//li[contains(@class,'lfr-nav-deletable')])[2]/a/span");
				selenium.waitForVisible(
					"//li[contains(@class,'lfr-nav-deletable') and contains(@class,'lfr-nav-hover')]/span[@class='delete-tab']");
				selenium.click(
					"//li[contains(@class,'lfr-nav-deletable') and contains(@class,'lfr-nav-hover')]/span[@class='delete-tab']");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this page?");
				selenium.waitForElementNotPresent(
					"//li[contains(@class,'lfr-nav-deletable') and contains(@class,'lfr-nav-hover')]/span[@class='delete-tab']");

				boolean page2Present = selenium.isElementPresent(
						"xPath=(//li[contains(@class,'lfr-nav-deletable')])[2]/a/span");

				if (!page2Present) {
					label = 3;

					continue;
				}

				selenium.mouseOver(
					"xPath=(//li[contains(@class,'lfr-nav-deletable')])[2]/a/span");
				selenium.waitForVisible(
					"//li[contains(@class,'lfr-nav-deletable') and contains(@class,'lfr-nav-hover')]/span[@class='delete-tab']");
				selenium.click(
					"//li[contains(@class,'lfr-nav-deletable') and contains(@class,'lfr-nav-hover')]/span[@class='delete-tab']");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this page?");
				selenium.waitForElementNotPresent(
					"//li[contains(@class,'lfr-nav-deletable') and contains(@class,'lfr-nav-hover')]/span[@class='delete-tab']");

				boolean page3Present = selenium.isElementPresent(
						"xPath=(//li[contains(@class,'lfr-nav-deletable')])[2]/a/span");

				if (!page3Present) {
					label = 4;

					continue;
				}

				selenium.mouseOver(
					"xPath=(//li[contains(@class,'lfr-nav-deletable')])[2]/a/span");
				selenium.waitForVisible(
					"//li[contains(@class,'lfr-nav-deletable') and contains(@class,'lfr-nav-hover')]/span[@class='delete-tab']");
				selenium.click(
					"//li[contains(@class,'lfr-nav-deletable') and contains(@class,'lfr-nav-hover')]/span[@class='delete-tab']");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this page?");
				selenium.waitForElementNotPresent(
					"//li[contains(@class,'lfr-nav-deletable') and contains(@class,'lfr-nav-hover')]/span[@class='delete-tab']");

				boolean page4Present = selenium.isElementPresent(
						"xPath=(//li[contains(@class,'lfr-nav-deletable')])[2]/a/span");

				if (!page4Present) {
					label = 5;

					continue;
				}

				selenium.mouseOver(
					"xPath=(//li[contains(@class,'lfr-nav-deletable')])[2]/a/span");
				selenium.waitForVisible(
					"//li[contains(@class,'lfr-nav-deletable') and contains(@class,'lfr-nav-hover')]/span[@class='delete-tab']");
				selenium.click(
					"//li[contains(@class,'lfr-nav-deletable') and contains(@class,'lfr-nav-hover')]/span[@class='delete-tab']");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this page?");
				selenium.waitForElementNotPresent(
					"//li[contains(@class,'lfr-nav-deletable') and contains(@class,'lfr-nav-hover')]/span[@class='delete-tab']");

				boolean page5Present = selenium.isElementPresent(
						"xPath=(//li[contains(@class,'lfr-nav-deletable')])[2]/a/span");

				if (!page5Present) {
					label = 6;

					continue;
				}

				selenium.mouseOver(
					"xPath=(//li[contains(@class,'lfr-nav-deletable')])[2]/a/span");
				selenium.waitForVisible(
					"//li[contains(@class,'lfr-nav-deletable') and contains(@class,'lfr-nav-hover')]/span[@class='delete-tab']");
				selenium.click(
					"//li[contains(@class,'lfr-nav-deletable') and contains(@class,'lfr-nav-hover')]/span[@class='delete-tab']");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this page?");
				selenium.waitForElementNotPresent(
					"//li[contains(@class,'lfr-nav-deletable') and contains(@class,'lfr-nav-hover')]/span[@class='delete-tab']");

			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 100:
				label = -1;
			}
		}
	}
}