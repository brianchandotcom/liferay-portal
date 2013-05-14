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

package com.liferay.portalweb.portal.permissions.documentlibrary.content.documentlibraryfolder.adddocument;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Member_AssertCannotAddFolderDocumentTest extends BaseTestCase {
	public void testMember_AssertCannotAddFolderDocument()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Documents and Media",
			RuntimeVariables.replace("Documents and Media"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//a[contains(@class,'document-link')]/span[@class='entry-title']",
			RuntimeVariables.replace("Folder Name"));
		selenium.waitForVisible("//div[@class='portlet-msg-info']");
		selenium.clickAt("link=Add", RuntimeVariables.replace("Add"));
		selenium.waitForVisible("//div[@class='lfr-menu-list unstyled']");
		assertFalse(selenium.isPartialText(
				"//div[@class='lfr-menu-list unstyled']", "Document"));
	}
}