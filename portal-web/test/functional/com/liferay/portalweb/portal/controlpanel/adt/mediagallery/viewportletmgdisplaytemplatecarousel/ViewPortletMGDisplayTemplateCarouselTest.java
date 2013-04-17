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

package com.liferay.portalweb.portal.controlpanel.adt.mediagallery.viewportletmgdisplaytemplatecarousel;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletMGDisplayTemplateCarouselTest extends BaseTestCase {
	public void testViewPortletMGDisplayTemplateCarousel()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Media Gallery Test Page",
			RuntimeVariables.replace("Media Gallery Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='search-results']"));
		assertTrue(selenium.isElementNotPresent("//span[@class='image-title']"));
		assertTrue(selenium.isElementNotPresent(
				"//input[@title='Search Documents']"));
		assertTrue(selenium.isElementNotPresent("//input[@value='Search']"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-asset-name']/h4"));
		assertTrue(selenium.isElementNotPresent(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[1]/a[contains(.,'Permissions')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[2]/a[contains(.,'Add Folder')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[3]/a[contains(.,'Add Repository')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[4]/a[contains(.,'Multiple Media')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[5]/a[contains(.,'Add Media')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[6]/a[contains(.,'Add Shortcut')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li[7]/a[contains(.,'Access from Desktop')]"));
		assertTrue(selenium.isVisible(
				"//div[@class='yui3-widget aui-component aui-carousel']/div/div"));
		assertTrue(selenium.isVisible(
				"//div[@class='yui3-widget aui-component aui-carousel']/div/menu"));
	}
}