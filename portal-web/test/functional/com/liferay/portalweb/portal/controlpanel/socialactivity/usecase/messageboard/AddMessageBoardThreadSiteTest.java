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

package com.liferay.portalweb.portal.controlpanel.socialactivity.usecase.messageboard;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddMessageBoardThreadSiteTest extends BaseTestCase {
	public void testAddMessageBoardThreadSite() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/site-name/");
				selenium.clickAt("link=Message Boards Test Page",
					RuntimeVariables.replace("Message Boards Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//input[@value='Post New Thread']",
					RuntimeVariables.replace("Post New Thread"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_19_subject']",
					RuntimeVariables.replace("MB Thread Message Subject"));
				selenium.waitForVisible(
					"//a[contains(@class,'cke_button cke_button__unlink') and contains(@class,' cke_button_disabled')]	");
				selenium.waitForVisible(
					"//iframe[contains(@title,'Rich Text Editor')]");
				selenium.typeFrame("//iframe[contains(@title,'Rich Text Editor')]",
					RuntimeVariables.replace("MB Thread Message Body"));

				boolean subscribeMeCheckbox = selenium.isChecked(
						"//input[@id='_19_subscribeCheckbox']");

				if (!subscribeMeCheckbox) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_19_subscribeCheckbox']",
					RuntimeVariables.replace("Subscribe Me Checkbox"));

			case 2:
				assertFalse(selenium.isChecked(
						"//input[@id='_19_subscribeCheckbox']"));
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace(
						"MB Thread Message Subject"),
					selenium.getText("//div[@class='subject']/a/strong"));
				assertEquals(RuntimeVariables.replace("MB Thread Message Body"),
					selenium.getText("//div[@class='thread-body']"));

			case 100:
				label = -1;
			}
		}
	}
}