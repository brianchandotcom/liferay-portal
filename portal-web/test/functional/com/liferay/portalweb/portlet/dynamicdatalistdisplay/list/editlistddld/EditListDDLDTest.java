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

package com.liferay.portalweb.portlet.dynamicdatalistdisplay.list.editlistddld;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditListDDLDTest extends BaseTestCase {
	public void testEditListDDLD() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Dynamic Data List Display Test Page",
			RuntimeVariables.replace("Dynamic Data List Display Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		selenium.clickAt("//div[@class='icon-actions']/span/a/img[@title='Select List']",
			RuntimeVariables.replace("Select List"));
		selenium.waitForVisible(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.selectFrame(
			"//iframe[contains(@id,'configurationIframeDialog')]");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible("//input[@name='_86_keywords']");
		selenium.type("//input[@name='_86_keywords']",
			RuntimeVariables.replace("List Name"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("List Name"),
			selenium.getText("//tr[contains(.,'List Name')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("List Description"),
			selenium.getText("//tr[contains(.,'List Name')]/td[3]/a"));
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]",
			RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_167_name_en_US']",
			RuntimeVariables.replace("List Name Edited"));
		selenium.type("//textarea[@id='_167_description_en_US']",
			RuntimeVariables.replace("List Description Edited"));
		Thread.sleep(1000);
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("List Name Edited"),
			selenium.getText("//tr[contains(.,'List Name Edited')]/td[2]/a"));
		selenium.clickAt("//tr[contains(.,'List Name Edited')]/td[2]/a",
			RuntimeVariables.replace("List Name Edited"));
		selenium.waitForText("//div[@class='portlet-msg-info']/span[2]",
			"Displaying List: List Name Edited (Modified)");
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'List Name Edited')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("List Name Edited"),
			selenium.getText("//tr[contains(.,'List Name Edited')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("List Description Edited"),
			selenium.getText("//tr[contains(.,'List Name Edited')]/td[3]/a"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'List Name Edited')]/td[4]/a"));
		selenium.selectFrame("relative=top");
	}
}