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

package com.liferay.portalweb.socialofficeprofile.profile.sousedittagprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_EditTagProfileTest extends BaseTestCase {
	public void testSOUs_EditTagProfile() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/socialoffice01/so/profile");
		selenium.waitForVisible("//div[@class='lfr-contact-name']/a");
		assertEquals(RuntimeVariables.replace("Social01 Office01 User01"),
			selenium.getText("//div[@class='lfr-contact-name']/a"));
		assertEquals(RuntimeVariables.replace("tag1"),
			selenium.getText("//div[@data-title='Tags']"));
		selenium.clickAt("//div[@data-title='Tags']",
			RuntimeVariables.replace("tag1"));
		selenium.waitForVisible("//input[contains(@id,'TagNames')]");
		selenium.type("//input[contains(@id,'TagNames')]",
			RuntimeVariables.replace("tag2"));
		selenium.clickAt("//button[@id='add']", RuntimeVariables.replace("Add"));
		Thread.sleep(1000);
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForVisible("xPath=(//ul[@class='user-tags']/li/a)[2]");
		assertEquals(RuntimeVariables.replace("Tags"),
			selenium.getText("//div[@class='user-tags-title']"));
		assertEquals(RuntimeVariables.replace("tag1"),
			selenium.getText("xPath=(//ul[@class='user-tags']/li/a)[1]"));
		assertEquals(RuntimeVariables.replace("tag2"),
			selenium.getText("xPath=(//ul[@class='user-tags']/li/a)[2]"));
	}
}