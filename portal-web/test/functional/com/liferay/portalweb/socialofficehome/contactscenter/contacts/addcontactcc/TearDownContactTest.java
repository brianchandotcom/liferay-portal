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

package com.liferay.portalweb.socialofficehome.contactscenter.contacts.addcontactcc;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownContactTest extends BaseTestCase {
	public void testTearDownContact() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/user/joebloggs/so/dashboard/");
				selenium.waitForVisible(
					"//nav/ul/li[contains(.,'Contacts Center')]/a/span");
				selenium.clickAt("//nav/ul/li[contains(.,'Contacts Center')]/a/span",
					RuntimeVariables.replace("Contacts Center"));
				selenium.waitForPageToLoad("30000");
				selenium.select("//select[@id='_1_WAR_contactsportlet_filterBy']",
					RuntimeVariables.replace("My Contacts"));
				Thread.sleep(1000);

				boolean contact1Present = selenium.isElementPresent(
						"//div[@class='lfr-contact-info']");

				if (!contact1Present) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@class='lfr-contact-info']",
					RuntimeVariables.replace("Contact"));

				String contact1Name = selenium.getText(
						"//div[@class='lfr-contact-name']/a");
				RuntimeVariables.setValue("contact1Name", contact1Name);
				selenium.waitForVisible("//button[contains(.,'Delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//button[contains(.,'Delete')]"));
				selenium.click(RuntimeVariables.replace(
						"//button[contains(.,'Delete')]"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete ${contact1Name} from your contacts[\\s\\S]$"));

			case 2:
				selenium.select("//select[@id='_1_WAR_contactsportlet_filterBy']",
					RuntimeVariables.replace("My Contacts"));
				Thread.sleep(1000);

				boolean contact2Present = selenium.isElementPresent(
						"//div[@class='lfr-contact-info']");

				if (!contact2Present) {
					label = 3;

					continue;
				}

				selenium.clickAt("//div[@class='lfr-contact-info']",
					RuntimeVariables.replace("Contact"));

				String contact2Name = selenium.getText(
						"//div[@class='lfr-contact-name']/a");
				RuntimeVariables.setValue("contact2Name", contact2Name);
				selenium.waitForVisible("//button[contains(.,'Delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//button[contains(.,'Delete')]"));
				selenium.click(RuntimeVariables.replace(
						"//button[contains(.,'Delete')]"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete ${contact2Name} from your contacts[\\s\\S]$"));

			case 3:
				selenium.select("//select[@id='_1_WAR_contactsportlet_filterBy']",
					RuntimeVariables.replace("My Contacts"));
				Thread.sleep(1000);

				boolean contact3Present = selenium.isElementPresent(
						"//div[@class='lfr-contact-info']");

				if (!contact3Present) {
					label = 4;

					continue;
				}

				selenium.clickAt("//div[@class='lfr-contact-info']",
					RuntimeVariables.replace("Contact"));

				String contact3Name = selenium.getText(
						"//div[@class='lfr-contact-name']/a");
				RuntimeVariables.setValue("contact3Name", contact3Name);
				selenium.waitForVisible("//button[contains(.,'Delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//button[contains(.,'Delete')]"));
				selenium.click(RuntimeVariables.replace(
						"//button[contains(.,'Delete')]"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete ${contact3Name} from your contacts[\\s\\S]$"));

			case 4:
				selenium.select("//select[@id='_1_WAR_contactsportlet_filterBy']",
					RuntimeVariables.replace("My Contacts"));
				Thread.sleep(1000);

				boolean contact4Present = selenium.isElementPresent(
						"//div[@class='lfr-contact-info']");

				if (!contact4Present) {
					label = 5;

					continue;
				}

				selenium.clickAt("//div[@class='lfr-contact-info']",
					RuntimeVariables.replace("Contact"));

				String contact4Name = selenium.getText(
						"//div[@class='lfr-contact-name']/a");
				RuntimeVariables.setValue("contact4Name", contact4Name);
				selenium.waitForVisible("//button[contains(.,'Delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//button[contains(.,'Delete')]"));
				selenium.click(RuntimeVariables.replace(
						"//button[contains(.,'Delete')]"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete ${contact4Name} from your contacts[\\s\\S]$"));

			case 5:
				selenium.select("//select[@id='_1_WAR_contactsportlet_filterBy']",
					RuntimeVariables.replace("My Contacts"));
				Thread.sleep(1000);

				boolean contact5Present = selenium.isElementPresent(
						"//div[@class='lfr-contact-info']");

				if (!contact5Present) {
					label = 6;

					continue;
				}

				selenium.clickAt("//div[@class='lfr-contact-info']",
					RuntimeVariables.replace("Contact"));

				String contact5Name = selenium.getText(
						"//div[@class='lfr-contact-name']/a");
				RuntimeVariables.setValue("contact5Name", contact5Name);
				selenium.waitForVisible("//button[contains(.,'Delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//button[contains(.,'Delete')]"));
				selenium.click(RuntimeVariables.replace(
						"//button[contains(.,'Delete')]"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete ${contact5Name} from your contacts[\\s\\S]$"));

			case 6:
			case 100:
				label = -1;
			}
		}
	}
}