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

package com.liferay.portalweb.portal.permissions.imagegallery.assertactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Member_DeleteImageTest extends BaseTestCase {
	public void testMember_DeleteImage() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Media Gallery Test Page",
			RuntimeVariables.replace("Media Gallery Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Media Gallery Permissions Test Folder"),
			selenium.getText("xpath=(//span[@class='image-title'])[1]"));
		selenium.clickAt("xpath=(//span[@class='image-title'])[1]",
			RuntimeVariables.replace("Media Gallery Permissions Test Folder"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Permissions Image 3 Test Edited"),
			selenium.getText("xpath=(//span[@class='image-title'])[2]"));
		selenium.clickAt("xpath=(//span[@class='image-title'])[2]",
			RuntimeVariables.replace("Permissions Image 3 Test Edited"));
		Thread.sleep(1000);
		selenium.waitForVisible("//img[@title='Move to the Recycle Bin']");
		selenium.click(RuntimeVariables.replace(
				"//img[@title='Move to the Recycle Bin']"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"The selected item was moved to the Recycle Bin. Undo"),
			selenium.getText(
				"//div[@class='portlet-msg-success taglib-trash-undo']"));
		assertFalse(selenium.isTextPresent("Permissions Image 3 Test Edited"));
	}
}