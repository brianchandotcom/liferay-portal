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

package com.liferay.portalweb.portal.dbupgrade.viewsampledatalatest.stagingorganization.webcontentdisplay;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class PublishToLiveOrganizationStagingOrganizationWCDTest
	extends BaseTestCase {
	public void testPublishToLiveOrganizationStagingOrganizationWCD()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/staging-organization-wcd/");
		selenium.waitForVisible(
			"link=Page Staging Organization Web Content Display");
		selenium.clickAt("link=Page Staging Organization Web Content Display",
			RuntimeVariables.replace(
				"Page Staging Organization Web Content Display"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Staging"),
			selenium.getText("//div[@class='staging-bar']/ul/li[2]/span/a"));
		selenium.clickAt("//div[@class='staging-bar']/ul/li[2]/span/a",
			RuntimeVariables.replace("Staging"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.clickAt("//span[@class='staging-icon-menu-container']/span/ul/li/strong/a",
			RuntimeVariables.replace("Staging Dropdown"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a");
		assertEquals(RuntimeVariables.replace("Publish to Live Now"),
			selenium.getText("//div[@class='lfr-menu-list unstyled']/ul/li/a"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a",
			RuntimeVariables.replace("Publish to Live Now"));
		selenium.waitForVisible("//div[@class='portlet-msg-info']");
		assertEquals(RuntimeVariables.replace(
				"There are no selected pages. All pages will therefore be exported."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/panel.js')]");
		selenium.waitForVisible("//input[@value='Publish']");
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForConfirmation(
			"Are you sure you want to publish these pages?");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}