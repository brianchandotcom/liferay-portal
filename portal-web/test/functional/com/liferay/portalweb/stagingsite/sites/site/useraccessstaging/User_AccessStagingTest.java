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

package com.liferay.portalweb.stagingsite.sites.site.useraccessstaging;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_AccessStagingTest extends BaseTestCase {
	public void testUser_AccessStaging() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/site-name-staging");
		selenium.waitForVisible("//div[@class='staging-bar']");
		assertEquals(RuntimeVariables.replace("Staging"),
			selenium.getText("//div[@class='staging-bar']/ul/li/span/span"));
		assertEquals(RuntimeVariables.replace("Site Name (Staging)"),
			selenium.getText("//nav[@id='breadcrumbs']/ul/li[1]/span/a"));
		assertEquals(RuntimeVariables.replace("Page Name"),
			selenium.getText("//nav[@id='breadcrumbs']/ul/li[2]/span/a"));
	}
}