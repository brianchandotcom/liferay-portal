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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.webcontent.imageassociation;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWebContentImageAssociationTest extends BaseTestCase {
	public void testAddWebContentImageAssociation() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/web-content-image-association-community/");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[@title='Add']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Add']/ul/li/strong/a/span",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Basic Web Content')]/a");
		assertEquals(RuntimeVariables.replace("Basic Web Content"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Basic Web Content')]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Basic Web Content')]/a"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_15_title_en_US']",
			RuntimeVariables.replace("Image Web Content Test"));
		selenium.clickAt("//img[@alt='Change']",
			RuntimeVariables.replace("Change"));
		selenium.waitForVisible("//td[2]/a");
		assertEquals(RuntimeVariables.replace("Image Structure Test"),
			selenium.getText("//td[2]/a"));
		selenium.clickAt("//td[2]/a",
			RuntimeVariables.replace("Image Structure Test"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForConfirmation(
			"Selecting a new structure will change the available input fields and available templates? Do you want to proceed?");
		assertEquals(RuntimeVariables.replace("Image Structure Test"),
			selenium.getText("//span[@id='_15_structureNameLabel']"));
		selenium.type("//input[@id='text-test']",
			RuntimeVariables.replace("Text Test"));
		selenium.type("//input[@id='image-test']",
			RuntimeVariables.replace(
				"L:\\portal\\build\\portal-web\\test\\com\\liferay\\portalweb\\portal\\dbupgrade\\sampledata610\\webcontent\\imageassociation\\dependencies\\Image.jpg"));
		selenium.clickAt("xPath=(//input[@value='Select'])[2]",
			RuntimeVariables.replace("Select"));
		selenium.waitForVisible("//tr[3]/td[1]/a");
		assertEquals(RuntimeVariables.replace("Folder Test"),
			selenium.getText("//tr[3]/td[1]/a"));
		selenium.clickAt("//tr[3]/td[1]/a",
			RuntimeVariables.replace("Folder Test"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("ImageGallery.jpg"),
			selenium.getText("//tr[3]/td[1]/a"));
		selenium.click("//input[@value='Choose']");
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isVisible("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("Image Web Content Test"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("Approved"),
			selenium.getText("//td[4]/a"));
		assertTrue(selenium.isVisible("//td[5]/a"));
		assertTrue(selenium.isVisible("//td[6]/a"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//td[7]/a"));
	}
}