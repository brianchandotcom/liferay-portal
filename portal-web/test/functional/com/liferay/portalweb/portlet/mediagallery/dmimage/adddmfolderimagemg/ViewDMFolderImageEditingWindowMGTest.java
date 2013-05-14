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

package com.liferay.portalweb.portlet.mediagallery.dmimage.adddmfolderimagemg;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDMFolderImageEditingWindowMGTest extends BaseTestCase {
	public void testViewDMFolderImageEditingWindowMG()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Media Gallery Test Page",
			RuntimeVariables.replace("Media Gallery Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder Name"),
			selenium.getText("//span[@class='image-title']"));
		selenium.clickAt("//span[@class='image-title']",
			RuntimeVariables.replace("DM Folder Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("DM Folder Image Title"),
			selenium.getText("//span[@class='image-title']"));
		selenium.clickAt("//span[@class='image-title']",
			RuntimeVariables.replace("DM Folder Image Title"));
		selenium.waitForVisible("//a[@class='image-viewer-close']");
		selenium.waitForVisible("//img");
		assertTrue(selenium.isVisible("//img"));
		selenium.waitForVisible("//img[@alt='Download (13k)']");
		assertTrue(selenium.isVisible("//img[@alt='Download (13k)']"));
		assertTrue(selenium.isVisible("//img[@alt='View']"));
		assertTrue(selenium.isVisible("//img[@alt='Edit']"));
		assertTrue(selenium.isVisible("//img[@alt='Permissions']"));
		assertTrue(selenium.isVisible("//img[@alt='Move to the Recycle Bin']"));
		selenium.click("//a[@class='image-viewer-close']");
	}
}