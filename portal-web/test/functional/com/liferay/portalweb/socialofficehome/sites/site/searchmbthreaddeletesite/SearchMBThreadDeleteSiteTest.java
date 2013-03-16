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

package com.liferay.portalweb.socialofficehome.sites.site.searchmbthreaddeletesite;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchMBThreadDeleteSiteTest extends BaseTestCase {
	public void testSearchMBThreadDeleteSite() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/so/dashboard/");
		selenium.clickAt("//nav/ul/li[contains(.,'Search Test Page')]/a/span",
			RuntimeVariables.replace("Search Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//div[@class='portlet-body']/form/input[@title='Search']",
			RuntimeVariables.replace("MB Thread Message"));
		selenium.clickAt("//div[@class='portlet-body']/form/input[contains(@src,'search')]",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"No results were found that matched the keywords: MB Thread Message."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertTrue(selenium.isElementNotPresent("//span[@class='asset-entry']"));
		assertFalse(selenium.isTextPresent("MB Thread Message Subject"));
		assertFalse(selenium.isTextPresent("MB Thread Message Body"));
	}
}