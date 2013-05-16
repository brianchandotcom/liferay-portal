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

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portalweb.portal.util.RuntimeVariables;
import com.liferay.portalweb.portal.util.TestPropsValues;

import com.thoughtworks.selenium.CommandProcessor;
import com.thoughtworks.selenium.Selenium;

import java.lang.reflect.Field;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseSeleniumImpl
	extends SeleniumWrapper implements LiferaySelenium {

	public BaseSeleniumImpl(String projectDir, Selenium selenium) {
		super(selenium);

		_projectDir = projectDir;

		initCommandProcessor();

		selenium.start();
	}

	public void assertAlert(String pattern) throws Exception {
		LiferaySeleniumHelper.assertAlert(this, pattern);
	}

	public void assertChecked(String locator) throws Exception {
		LiferaySeleniumHelper.assertChecked(this, locator);
	}

	public void assertConfirmation(String pattern) throws Exception {
		LiferaySeleniumHelper.assertConfirmation(this, pattern);
	}

	public void assertElementNotPresent(String locator) throws Exception {
		LiferaySeleniumHelper.assertElementNotPresent(this, locator);
	}

	public void assertElementPresent(String locator) throws Exception {
		LiferaySeleniumHelper.assertElementPresent(this, locator);
	}

	public void assertLocation(String pattern) {
		LiferaySeleniumHelper.assertLocation(this, pattern);
	}

	public void assertNotAlert(String pattern) {
		LiferaySeleniumHelper.assertNotAlert(this, pattern);
	}

	public void assertNotChecked(String locator) throws Exception {
		LiferaySeleniumHelper.assertNotChecked(this, locator);
	}

	public void assertNotLocation(String pattern) {
		LiferaySeleniumHelper.assertNotLocation(this, pattern);
	}

	public void assertNotPartialText(String locator, String pattern)
		throws Exception {

		LiferaySeleniumHelper.assertNotPartialText(this, locator, pattern);
	}

	public void assertNotSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		LiferaySeleniumHelper.assertNotSelectedLabel(
			this, selectLocator, pattern);
	}

	public void assertNotText(String locator, String pattern) throws Exception {
		LiferaySeleniumHelper.assertNotText(this, locator, pattern);
	}

	public void assertNotValue(String locator, String pattern)
		throws Exception {

		LiferaySeleniumHelper.assertNotValue(this, locator, pattern);
	}

	public void assertNotVisible(String locator) throws Exception {
		LiferaySeleniumHelper.assertNotVisible(this, locator);
	}

	public void assertPartialText(String locator, String pattern)
		throws Exception {

		LiferaySeleniumHelper.assertPartialText(this, locator, pattern);
	}

	public void assertSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		LiferaySeleniumHelper.assertSelectedLabel(this, selectLocator, pattern);
	}

	public void assertText(String locator, String pattern) throws Exception {
		LiferaySeleniumHelper.assertText(this, locator, pattern);
	}

	public void assertTextNotPresent(String pattern) throws Exception {
		LiferaySeleniumHelper.assertTextNotPresent(this, pattern);
	}

	public void assertTextPresent(String pattern) throws Exception {
		LiferaySeleniumHelper.assertTextPresent(this, pattern);
	}

	public void assertValue(String locator, String pattern) throws Exception {
		LiferaySeleniumHelper.assertValue(this, locator, pattern);
	}

	public void assertVisible(String locator) throws Exception {
		LiferaySeleniumHelper.assertVisible(this, locator);
	}

	public void clickAndWait(String locator) {
		super.click(locator);
		super.waitForPageToLoad("30000");
	}

	public void clickAtAndWait(String locator, String coordString) {
		super.clickAt(locator, coordString);
		super.waitForPageToLoad("30000");
	}

	public void copyText(String locator) {
		_clipBoard = super.getText(locator);
	}

	public void copyValue(String locator) {
		_clipBoard = super.getValue(locator);
	}

	public void echo(String message) {
		LiferaySeleniumHelper.echo(message);
	}

	public void fail(String message) {
		LiferaySeleniumHelper.fail(message);
	}

	public String getCurrentDay() {
		return _commandProcessor.getString("getCurrentDay", new String[0]);
	}

	public String getCurrentMonth() {
		return _commandProcessor.getString("getCurrentMonth", new String[0]);
	}

	public String getCurrentYear() {
		return _commandProcessor.getString("getCurrentYear", new String[0]);
	}

	public String getFirstNumber(String locator) {
		return _commandProcessor.getString(
			"getFirstNumber", new String[] {locator,});
	}

	public String getFirstNumberIncrement(String locator) {
		return _commandProcessor.getString(
			"getFirstNumberIncrement", new String[] {locator,});
	}

	public String getNumberDecrement(String value) {
		return LiferaySeleniumHelper.getNumberDecrement(value);
	}

	public String getNumberIncrement(String value) {
		return LiferaySeleniumHelper.getNumberIncrement(value);
	}

	public String getPrimaryTestSuiteName() {
		return _primaryTestSuiteName;
	}

	public String getProjectDir() {
		return _projectDir;
	}

	public void goBackAndWait() {
		super.goBack();
		super.waitForPageToLoad("30000");
	}

	public boolean isConfirmation(String pattern) {
		return LiferaySeleniumHelper.isConfirmation(this, pattern);
	}

	public boolean isElementNotPresent(String locator) {
		return LiferaySeleniumHelper.isElementNotPresent(this, locator);
	}

	public boolean isNotChecked(String locator) {
		return LiferaySeleniumHelper.isNotChecked(this, locator);
	}

	public boolean isNotPartialText(String locator, String value) {
		return LiferaySeleniumHelper.isNotPartialText(this, locator, value);
	}

	public boolean isNotSelectedLabel(String selectLocator, String pattern) {
		return !pattern.equals(getSelectedLabel(selectLocator));
	}

	public boolean isNotText(String locator, String value) {
		return LiferaySeleniumHelper.isNotText(this, locator, value);
	}

	public boolean isNotValue(String locator, String value) {
		return LiferaySeleniumHelper.isNotValue(this, locator, value);
	}

	public boolean isNotVisible(String locator) {
		return LiferaySeleniumHelper.isNotVisible(this, locator);
	}

	public boolean isPartialText(String locator, String value) {
		value = RuntimeVariables.replace(value);

		return _commandProcessor.getBoolean(
			"isPartialText", new String[] {locator, value,});
	}

	public boolean isSelectedLabel(String selectLocator, String pattern) {
		return pattern.equals(getSelectedLabel(selectLocator));
	}

	public boolean isText(String locator, String value) {
		return value.equals(getText(locator));
	}

	public boolean isTextNotPresent(String pattern) {
		return LiferaySeleniumHelper.isTextNotPresent(this, pattern);
	}

	public boolean isValue(String locator, String value) {
		return value.equals(getValue(locator));
	}

	public void keyDownAndWait(String locator, String keySequence) {
		super.keyDown(locator, keySequence);
		super.waitForPageToLoad("30000");
	}

	public void keyPressAndWait(String locator, String keySequence) {
		super.keyPress(locator, keySequence);
		super.waitForPageToLoad("30000");
	}

	public void keyUpAndWait(String locator, String keySequence) {
		super.keyUp(locator, keySequence);
		super.waitForPageToLoad("30000");
	}

	public void makeVisible(String locator) {
		StringBundler sb = new StringBundler(10);

		sb.append("var xpathResult = document.evaluate(");
		sb.append(locator);
		sb.append(", document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, ");
		sb.append("null);");

		sb.append("if (xpathResult.singleNodeValue) {");
		sb.append("var element = xpathResult.singleNodeValue;");
		sb.append("element.style.cssText = 'display:inline !important';");
		sb.append("element.style.overflow = 'visible';");
		sb.append("element.style.minHeight = '1px';");
		sb.append("element.style.minWidth = '1px';");
		sb.append("element.style.opacity = '1';");
		sb.append("element.style.visibility = 'visible';");
		sb.append("}");

		super.runScript(sb.toString());
	}

	public void paste(String location) {
		super.type(location, _clipBoard);
	}

	public void pause(String waitTime) throws Exception {
		LiferaySeleniumHelper.pause(waitTime);
	}

	public void refreshAndWait() {
		super.refresh();
		super.waitForPageToLoad("30000");
	}

	public void saveScreenShotAndSource() throws Exception {
		String screenShotName = null;

		if (TestPropsValues.SAVE_SCREENSHOT) {
			screenShotName = getScreenshotFileName();

			captureEntirePageScreenshot(
				_OUTPUT_SCREENSHOTS_DIR + screenShotName + ".jpg", "");
		}

		if (TestPropsValues.SAVE_SOURCE) {
			String content = getHtmlSource();

			screenShotName = getScreenshotFileName();

			FileUtil.write(
				_OUTPUT_SCREENSHOTS_DIR + screenShotName + ".html", content);
		}
	}

	public void selectAndWait(String selectLocator, String optionLocator) {
		super.select(selectLocator, optionLocator);
		super.waitForPageToLoad("30000");
	}

	public void sendKeys(String locator, String value) {
		_commandProcessor.doCommand("sendKeys", new String[] {locator, value});
	}

	public void setDefaultTimeout() {
		int timeout = TestPropsValues.TIMEOUT_EXPLICIT_WAIT * 1000;

		setTimeout(String.valueOf(timeout));
	}

	public void setDefaultTimeoutImplicit() {
	}

	public void setPrimaryTestSuiteName(String primaryTestSuiteName) {
		_primaryTestSuiteName = primaryTestSuiteName;
	}

	@Override
	public void setTimeout(String timeout) {
		super.setTimeout(timeout);

		_timeout = timeout;
	}

	public void setTimeoutImplicit(String timeout) {
	}

	public void stopLogger() {
	}

	public void typeFrame(String locator, String value) {
		LiferaySeleniumHelper.typeFrame(this, locator, value);
	}

	@Override
	public void typeKeys(String locator, String value) {
		sendKeys(locator, value);
	}

	public void uploadCommonFile(String location, String value) {
		super.type(
			location,
			_projectDir + "portal-web\\test\\functional\\com\\liferay\\" +
				"portalweb\\dependencies\\" + value);
	}

	public void uploadFile(String location, String value) {
		makeVisible(location);

		super.type(location, value);
	}

	public void uploadTempFile(String location, String value) {
		super.type(location, TestPropsValues.OUTPUT_DIR + value);
	}

	public void waitForConfirmation(String pattern) throws Exception {
		for (int second = 0;; second++) {
			if (second >= TestPropsValues.TIMEOUT_EXPLICIT_WAIT) {
				assertConfirmation(pattern);
			}

			try {
				if (isConfirmation(pattern)) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}
	}

	public void waitForElementNotPresent(String locator) throws Exception {
		LiferaySeleniumHelper.waitForElementNotPresent(this, locator);
	}

	public void waitForElementPresent(String locator) throws Exception {
		LiferaySeleniumHelper.waitForElementPresent(this, locator);
	}

	public void waitForNotPartialText(String locator, String value)
		throws Exception {

		LiferaySeleniumHelper.waitForNotPartialText(this, locator, value);
	}

	public void waitForNotSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		LiferaySeleniumHelper.waitForNotSelectedLabel(
			this, selectLocator, pattern);
	}

	public void waitForNotText(String locator, String value) throws Exception {
		LiferaySeleniumHelper.waitForNotText(this, locator, value);
	}

	public void waitForNotValue(String locator, String value) throws Exception {
		LiferaySeleniumHelper.waitForNotValue(this, locator, value);
	}

	public void waitForNotVisible(String locator) throws Exception {
		LiferaySeleniumHelper.waitForNotVisible(this, locator);
	}

	@Override
	public void waitForPageToLoad(String timeout) {
		super.waitForPageToLoad(_timeout);
	}

	public void waitForPartialText(String locator, String value)
		throws Exception {

		LiferaySeleniumHelper.waitForPartialText(this, locator, value);
	}

	public void waitForSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		LiferaySeleniumHelper.waitForSelectedLabel(
			this, selectLocator, pattern);
	}

	public void waitForText(String locator, String value) throws Exception {
		LiferaySeleniumHelper.waitForText(this, locator, value);
	}

	public void waitForTextNotPresent(String value) throws Exception {
		LiferaySeleniumHelper.waitForTextNotPresent(this, value);
	}

	public void waitForTextPresent(String value) throws Exception {
		LiferaySeleniumHelper.waitForTextPresent(this, value);
	}

	public void waitForValue(String locator, String value) throws Exception {
		LiferaySeleniumHelper.waitForValue(this, locator, value);
	}

	public void waitForVisible(String locator) throws Exception {
		LiferaySeleniumHelper.waitForVisible(this, locator);
	}

	public void windowMaximizeAndWait() {
		super.windowMaximize();
		super.waitForPageToLoad("30000");
	}

	protected String getScreenshotFileName() {
		Thread currentThread = Thread.currentThread();

		StackTraceElement[] stackTraceElements = currentThread.getStackTrace();

		for (int i = 1; i < stackTraceElements.length; i++) {
			StackTraceElement stackTraceElement = stackTraceElements[i];

			String className = stackTraceElement.getClassName();

			if ((className.startsWith("com.liferay.portalweb.plugins") ||
				 className.startsWith("com.liferay.portalweb.portal") ||
				 className.startsWith("com.liferay.portalweb.portlet") ||
				 className.startsWith("com.liferay.portalweb.properties")) &&
				className.endsWith("Test")) {

				String dirName = className.substring(22);

				dirName = StringUtil.replace(dirName, ".", "/") + "/";

				String fileName = stackTraceElement.getFileName();
				int lineNumber = stackTraceElement.getLineNumber();

				FileUtil.mkdirs(_OUTPUT_SCREENSHOTS_DIR + dirName);

				return dirName + fileName + "-" + lineNumber;
			}
		}

		throw new RuntimeException("Unable to find screenshot file name");
	}

	protected void initCommandProcessor() {
		try {
			Selenium selenium = getWrappedSelenium();

			Class<?> clazz = selenium.getClass();

			Field field = clazz.getDeclaredField("commandProcessor");

			field.setAccessible(true);

			_commandProcessor = (CommandProcessor)field.get(selenium);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static final String _OUTPUT_SCREENSHOTS_DIR =
		TestPropsValues.OUTPUT_DIR + "screenshots/";

	private String _clipBoard = "";
	private CommandProcessor _commandProcessor;
	private String _primaryTestSuiteName;
	private String _projectDir;
	private String _timeout = "90000";

}