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

package com.liferay.portalweb.socialofficehome.sites.publicrestrictedsite.sousfavoritesite1typepublicrestricted;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_FavoriteSite1TypePublicRestrictedTest extends BaseTestCase {
	public void testSOUs_FavoriteSite1TypePublicRestricted()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/socialoffice01/so/dashboard/");
		assertEquals(RuntimeVariables.replace("Sites"),
			selenium.getText("//div[@id='so-sidebar']/h3"));
		assertTrue(selenium.isVisible("//input[@class='search-input']"));
		selenium.type("//input[@class='search-input']",
			RuntimeVariables.replace("Public Restricted"));
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Public Restricted Site1 Name"),
			selenium.getText(
				"//li[contains(@class, 'social-office-enabled')]/span[2]/a"));
		assertTrue(selenium.isVisible(
				"//li[contains(@class, 'social-office-enabled')]/span[@class='action favorite']/a"));
		selenium.clickAt("//li[contains(@class, 'social-office-enabled')]/span[@class='action favorite']/a",
			RuntimeVariables.replace("Favorite"));
		selenium.waitForElementPresent(
			"//li[contains(@class, 'social-office-enabled')]/span[@class='action unfavorite']/a");
		assertTrue(selenium.isElementPresent(
				"//li[contains(@class, 'social-office-enabled')]/span[@class='action unfavorite']/a"));
	}
}