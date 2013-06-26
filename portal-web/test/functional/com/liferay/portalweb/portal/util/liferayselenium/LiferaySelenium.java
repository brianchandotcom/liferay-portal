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

package com.liferay.portalweb.portal.util.liferayselenium;

import com.thoughtworks.selenium.Selenium;

/**
 * @author Brian Wing Shun Chan
 */
public interface LiferaySelenium extends Selenium {

	public void assertAlert(String pattern) throws Exception;

	public void assertChecked(String pattern) throws Exception;

	public void assertConfirmation(String pattern) throws Exception;

	public void assertElementNotPresent(String locator) throws Exception;

	public void assertElementPresent(String locator) throws Exception;

	public void assertLocation(String pattern);

	public void assertNotAlert(String pattern);

	public void assertNotChecked(String locator) throws Exception;

	public void assertNotLocation(String pattern) throws Exception;

	public void assertNotPartialText(String locator, String pattern)
		throws Exception;

	public void assertNotSelectedLabel(String selectLocator, String pattern)
		throws Exception;

	public void assertNotText(String locator, String pattern) throws Exception;

	public void assertNotValue(String locator, String pattern) throws Exception;

	public void assertNotVisible(String locator) throws Exception;

	public void assertPartialText(String locator, String pattern)
		throws Exception;

	public void assertSelectedLabel(String selectLocator, String pattern)
			throws Exception;

	public void assertText(String locator, String pattern) throws Exception;

	public void assertTextNotPresent(String pattern) throws Exception;

	public void assertTextPresent(String pattern) throws Exception;

	public void assertValue(String locator, String pattern) throws Exception;

	public void assertVisible(String locator) throws Exception;

	public void clickAndWait(String locator);

	public void clickAtAndWait(String locator, String coordString);

	public void copyText(String locator);

	public void copyValue(String locator);

	public void echo(String message);

	public void fail(String message);

	public String getCurrentDay();

	public String getCurrentMonth();

	public String getCurrentYear();

	public String getFirstNumber(String locator);

	public String getFirstNumberIncrement(String locator);

	public String getNumberDecrement(String value);

	public String getNumberIncrement(String value);

	public String getPrimaryTestSuiteName();

	public String getProjectDir();

	public void goBackAndWait();

	public boolean isConfirmation(String pattern);

	public boolean isElementNotPresent(String locator);

	public boolean isNotChecked(String locator);

	public boolean isNotPartialText(String locator, String value);

	public boolean isNotSelectedLabel(String selectLocator, String pattern);

	public boolean isNotText(String locator, String value);

	public boolean isNotValue(String locator, String value);

	public boolean isNotVisible(String locator);

	public boolean isPartialText(String locator, String value);

	public boolean isSelectedLabel(String selectLocator, String pattern);

	public boolean isText(String locator, String value);

	public boolean isTextNotPresent(String pattern);

	public boolean isValue(String locator, String value);

	public void keyDownAndWait(String locator, String keySequence);

	public void keyPressAndWait(String locator, String keySequence);

	public void keyUpAndWait(String locator, String keySequence);

	public void makeVisible(String locator);

	public void paste(String locator);

	public void pause(String waitTime) throws Exception;

	public void refreshAndWait();

	public void saveScreenShotAndSource() throws Exception;

	public void selectAndWait(String selectLocator, String optionLocator);

	public void sendKeys(String locator, String value);

	public void sendLogger(String id, String status);

	public void setDefaultTimeout();

	public void setDefaultTimeoutImplicit();

	public void setPrimaryTestSuiteName(String primaryTestSuiteName);

	public void setTimeoutImplicit(String timeout);

	public void startLogger();

	public void stopLogger();

	public void typeFrame(String locator, String value);

	public void uploadCommonFile(String locator, String value);

	public void uploadFile(String locator, String value);

	public void uploadTempFile(String locator, String value);

	public void waitForConfirmation(String pattern) throws Exception;

	public void waitForElementNotPresent(String locator) throws Exception;

	public void waitForElementPresent(String locator) throws Exception;

	public void waitForNotPartialText(String locator, String value)
		throws Exception;

	public void waitForNotSelectedLabel(String selectLocator, String pattern)
		throws Exception;

	public void waitForNotText(String locator, String value) throws Exception;

	public void waitForNotValue(String locator, String value) throws Exception;

	public void waitForNotVisible(String locator) throws Exception;

	public void waitForPartialText(String locator, String value)
		throws Exception;

	public void waitForSelectedLabel(String selectLocator, String pattern)
		throws Exception;

	public void waitForText(String locator, String value) throws Exception;

	public void waitForTextNotPresent(String value) throws Exception;

	public void waitForTextPresent(String value) throws Exception;

	public void waitForValue(String locator, String value) throws Exception;

	public void waitForVisible(String locator) throws Exception;

	public void windowMaximizeAndWait();

}