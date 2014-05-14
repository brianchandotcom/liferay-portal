/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.OSDetector;
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

	public BaseSeleniumImpl(String projectDirName, Selenium selenium) {
		super(selenium);

		_projectDirName = projectDirName;

		if (OSDetector.isWindows()) {
			_dependenciesDirName = StringUtil.replace(
				_dependenciesDirName, "//", "\\");

			_outputDirName = StringUtil.replace(_outputDirName, "//", "\\");

			_projectDirName = StringUtil.replace(_projectDirName, "//", "\\");

			_sikuliImagesDirName = StringUtil.replace(
				_sikuliImagesDirName, "//", "\\");
			_sikuliImagesDirName = StringUtil.replace(
				_sikuliImagesDirName, "linux", "windows");
		}

		initCommandProcessor();

		selenium.start();
	}

	@Override
	public void antCommand(String fileName, String target) throws Exception {
		LiferaySeleniumHelper.antCommand(this, fileName, target);
	}

	@Override
	public void assertAlert(String pattern) throws Exception {
		LiferaySeleniumHelper.assertAlert(this, pattern);
	}

	@Override
	public void assertAlertNotPresent() throws Exception {
		LiferaySeleniumHelper.assertAlertNotPresent(this);
	}

	@Override
	public void assertChecked(String locator) throws Exception {
		LiferaySeleniumHelper.assertChecked(this, locator);
	}

	@Override
	public void assertConfirmation(String pattern) throws Exception {
		LiferaySeleniumHelper.assertConfirmation(this, pattern);
	}

	@Override
	public void assertConsoleTextNotPresent(String text) throws Exception {
		LiferaySeleniumHelper.assertConsoleTextNotPresent(text);
	}

	@Override
	public void assertConsoleTextPresent(String text) throws Exception {
		LiferaySeleniumHelper.assertConsoleTextPresent(text);
	}

	@Override
	public void assertElementNotPresent(String locator) throws Exception {
		LiferaySeleniumHelper.assertElementNotPresent(this, locator);
	}

	@Override
	public void assertElementPresent(String locator) throws Exception {
		LiferaySeleniumHelper.assertElementPresent(this, locator);
	}

	@Override
	public void assertEmailBody(String index, String body) throws Exception {
		LiferaySeleniumHelper.assertEmailBody(this, index, body);
	}

	@Override
	public void assertEmailSubject(String index, String subject)
		throws Exception {

		LiferaySeleniumHelper.assertEmailSubject(this, index, subject);
	}

	@Override
	public void assertJavaScriptErrors(String ignoreJavaScriptError)
		throws Exception {
	}

	@Override
	public void assertLiferayErrors() throws Exception {
		if (!TestPropsValues.TEST_ASSERT_LIFERAY_ERRORS) {
			return;
		}

		LiferaySeleniumHelper.assertLiferayErrors();
	}

	@Override
	public void assertLocation(String pattern) {
		LiferaySeleniumHelper.assertLocation(this, pattern);
	}

	@Override
	public void assertNotAlert(String pattern) {
		LiferaySeleniumHelper.assertNotAlert(this, pattern);
	}

	@Override
	public void assertNotChecked(String locator) throws Exception {
		LiferaySeleniumHelper.assertNotChecked(this, locator);
	}

	@Override
	public void assertNotLocation(String pattern) {
		LiferaySeleniumHelper.assertNotLocation(this, pattern);
	}

	@Override
	public void assertNotPartialText(String locator, String pattern)
		throws Exception {

		LiferaySeleniumHelper.assertNotPartialText(this, locator, pattern);
	}

	@Override
	public void assertNotSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		LiferaySeleniumHelper.assertNotSelectedLabel(
			this, selectLocator, pattern);
	}

	@Override
	public void assertNotText(String locator, String pattern) throws Exception {
		LiferaySeleniumHelper.assertNotText(this, locator, pattern);
	}

	@Override
	public void assertNotValue(String locator, String pattern)
		throws Exception {

		LiferaySeleniumHelper.assertNotValue(this, locator, pattern);
	}

	@Override
	public void assertNotVisible(String locator) throws Exception {
		LiferaySeleniumHelper.assertNotVisible(this, locator);
	}

	@Override
	public void assertPartialText(String locator, String pattern)
		throws Exception {

		LiferaySeleniumHelper.assertPartialText(this, locator, pattern);
	}

	@Override
	public void assertSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		LiferaySeleniumHelper.assertSelectedLabel(this, selectLocator, pattern);
	}

	@Override
	public void assertText(String locator, String pattern) throws Exception {
		LiferaySeleniumHelper.assertText(this, locator, pattern);
	}

	@Override
	public void assertTextNotPresent(String pattern) throws Exception {
		LiferaySeleniumHelper.assertTextNotPresent(this, pattern);
	}

	@Override
	public void assertTextPresent(String pattern) throws Exception {
		LiferaySeleniumHelper.assertTextPresent(this, pattern);
	}

	@Override
	public void assertValue(String locator, String pattern) throws Exception {
		LiferaySeleniumHelper.assertValue(this, locator, pattern);
	}

	@Override
	public void assertVisible(String locator) throws Exception {
		LiferaySeleniumHelper.assertVisible(this, locator);
	}

	@Override
	public void clickAndWait(String locator) {
		super.click(locator);
		super.waitForPageToLoad("30000");
	}

	@Override
	public void clickAtAndWait(String locator, String coordString) {
		super.clickAt(locator, coordString);
		super.waitForPageToLoad("30000");
	}

	@Override
	public void connectToEmailAccount(String emailAddress, String emailPassword)
		throws Exception {

		LiferaySeleniumHelper.connectToEmailAccount(
			emailAddress, emailPassword);
	}

	@Override
	public void copyText(String locator) {
		_clipBoard = super.getText(locator);
	}

	@Override
	public void copyValue(String locator) {
		_clipBoard = super.getValue(locator);
	}

	@Override
	public void deleteAllEmails() throws Exception {
		LiferaySeleniumHelper.deleteAllEmails();
	}

	@Override
	public void echo(String message) {
		LiferaySeleniumHelper.echo(message);
	}

	@Override
	public void fail(String message) {
		LiferaySeleniumHelper.fail(message);
	}

	@Override
	public String getCurrentDay() {
		return _commandProcessor.getString("getCurrentDay", new String[0]);
	}

	@Override
	public String getCurrentMonth() {
		return _commandProcessor.getString("getCurrentMonth", new String[0]);
	}

	@Override
	public String getCurrentYear() {
		return _commandProcessor.getString("getCurrentYear", new String[0]);
	}

	@Override
	public String getDependenciesDirName() {
		return _dependenciesDirName;
	}

	@Override
	public String getEmailBody(String index) throws Exception {
		return LiferaySeleniumHelper.getEmailBody(index);
	}

	@Override
	public String getEmailSubject(String index) throws Exception {
		return LiferaySeleniumHelper.getEmailSubject(index);
	}

	@Override
	public String getFirstNumber(String locator) {
		return _commandProcessor.getString(
			"getFirstNumber", new String[] {locator,});
	}

	@Override
	public String getFirstNumberIncrement(String locator) {
		return _commandProcessor.getString(
			"getFirstNumberIncrement", new String[] {locator,});
	}

	@Override
	public String getNumberDecrement(String value) {
		return LiferaySeleniumHelper.getNumberDecrement(value);
	}

	@Override
	public String getNumberIncrement(String value) {
		return LiferaySeleniumHelper.getNumberIncrement(value);
	}

	@Override
	public String getOutputDirName() {
		return _outputDirName;
	}

	@Override
	public String getPrimaryTestSuiteName() {
		return _primaryTestSuiteName;
	}

	@Override
	public String getProjectDirName() {
		return _projectDirName;
	}

	@Override
	public String getSikuliImagesDirName() {
		return _sikuliImagesDirName;
	}

	@Override
	public void goBackAndWait() {
		super.goBack();
		super.waitForPageToLoad("30000");
	}

	@Override
	public boolean isConfirmation(String pattern) {
		return LiferaySeleniumHelper.isConfirmation(this, pattern);
	}

	@Override
	public boolean isElementNotPresent(String locator) {
		return LiferaySeleniumHelper.isElementNotPresent(this, locator);
	}

	@Override
	public boolean isElementPresentAfterWait(String locator) throws Exception {
		return LiferaySeleniumHelper.isElementPresentAfterWait(this, locator);
	}

	@Override
	public boolean isNotChecked(String locator) {
		return LiferaySeleniumHelper.isNotChecked(this, locator);
	}

	@Override
	public boolean isNotPartialText(String locator, String value) {
		return LiferaySeleniumHelper.isNotPartialText(this, locator, value);
	}

	@Override
	public boolean isNotSelectedLabel(String selectLocator, String pattern) {
		return !pattern.equals(getSelectedLabel(selectLocator));
	}

	@Override
	public boolean isNotText(String locator, String value) {
		return LiferaySeleniumHelper.isNotText(this, locator, value);
	}

	@Override
	public boolean isNotValue(String locator, String value) {
		return LiferaySeleniumHelper.isNotValue(this, locator, value);
	}

	@Override
	public boolean isNotVisible(String locator) {
		return LiferaySeleniumHelper.isNotVisible(this, locator);
	}

	@Override
	public boolean isPartialText(String locator, String value) {
		value = RuntimeVariables.replace(value);

		return _commandProcessor.getBoolean(
			"isPartialText", new String[] {locator, value,});
	}

	@Override
	public boolean isSelectedLabel(String selectLocator, String pattern) {
		return pattern.equals(getSelectedLabel(selectLocator));
	}

	@Override
	public boolean isTCatEnabled() {
		return LiferaySeleniumHelper.isTCatEnabled();
	}

	@Override
	public boolean isText(String locator, String value) {
		return value.equals(getText(locator));
	}

	@Override
	public boolean isTextNotPresent(String pattern) {
		return LiferaySeleniumHelper.isTextNotPresent(this, pattern);
	}

	@Override
	public boolean isValue(String locator, String value) {
		return value.equals(getValue(locator));
	}

	@Override
	public void keyDownAndWait(String locator, String keySequence) {
		super.keyDown(locator, keySequence);
		super.waitForPageToLoad("30000");
	}

	@Override
	public void keyPressAndWait(String locator, String keySequence) {
		super.keyPress(locator, keySequence);
		super.waitForPageToLoad("30000");
	}

	@Override
	public void keyUpAndWait(String locator, String keySequence) {
		super.keyUp(locator, keySequence);
		super.waitForPageToLoad("30000");
	}

	@Override
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

	@Override
	public void mouseRelease() {
	}

	@Override
	public void paste(String location) {
		super.type(location, _clipBoard);
	}

	@Override
	public void pause(String waitTime) throws Exception {
		LiferaySeleniumHelper.pause(waitTime);
	}

	@Override
	public void pauseLoggerCheck() throws Exception {
	}

	@Override
	public void refreshAndWait() {
		super.refresh();
		super.waitForPageToLoad("30000");
	}

	@Override
	public void replyToEmail(String to, String body) throws Exception {
		LiferaySeleniumHelper.replyToEmail(this, to, body);
	}

	@Override
	public void saveScreenshot() throws Exception {
		if (!TestPropsValues.SAVE_SCREENSHOT) {
			return;
		}

		LiferaySeleniumHelper.saveScreenshot(this);
	}

	@Override
	public void saveScreenshotAndSource() throws Exception {
		String screenshotName = null;

		screenshotName = getScreenshotFileName();

		captureEntirePageScreenshot(
			_OUTPUT_SCREENSHOTS_DIR + screenshotName + ".jpg", "");

		String content = getHtmlSource();

		screenshotName = getScreenshotFileName();

		FileUtil.write(
			_OUTPUT_SCREENSHOTS_DIR + screenshotName + ".html", content);
	}

	@Override
	public void selectAndWait(String selectLocator, String optionLocator) {
		super.select(selectLocator, optionLocator);
		super.waitForPageToLoad("30000");
	}

	@Override
	public void sendActionDescriptionLogger(String description) {
	}

	@Override
	public boolean sendActionLogger(String command, String[] params) {
		return true;
	}

	@Override
	public void sendEmail(String to, String subject, String body)
		throws Exception {

		LiferaySeleniumHelper.sendEmail(this, to, subject, body);
	}

	@Override
	public void sendKeys(String locator, String value) {
		_commandProcessor.doCommand("sendKeys", new String[] {locator, value});
	}

	@Override
	public void sendLogger(String id, String status) {
	}

	@Override
	public void sendMacroDescriptionLogger(String description) {
	}

	@Override
	public void sendTestCaseCommandLogger(String command) {
	}

	@Override
	public void sendTestCaseHeaderLogger(String command) {
	}

	@Override
	public void setDefaultTimeout() {
		int timeout = TestPropsValues.TIMEOUT_EXPLICIT_WAIT * 1000;

		setTimeout(String.valueOf(timeout));
	}

	@Override
	public void setDefaultTimeoutImplicit() {
	}

	@Override
	public void setPrimaryTestSuiteName(String primaryTestSuiteName) {
		_primaryTestSuiteName = primaryTestSuiteName;
	}

	@Override
	public void setTimeout(String timeout) {
		super.setTimeout(timeout);

		_timeout = timeout;
	}

	@Override
	public void setTimeoutImplicit(String timeout) {
	}

	@Override
	public void sikuliAssertElementNotPresent(String image) throws Exception {
		LiferaySeleniumHelper.sikuliAssertElementNotPresent(this, image);
	}

	@Override
	public void sikuliAssertElementPresent(String image) throws Exception {
		LiferaySeleniumHelper.sikuliAssertElementPresent(this, image);
	}

	@Override
	public void sikuliClick(String image) throws Exception {
		LiferaySeleniumHelper.sikuliClick(this, image);
	}

	@Override
	public void sikuliType(String image, String value) throws Exception {
		LiferaySeleniumHelper.sikuliType(this, image, value);
	}

	@Override
	public void sikuliUploadCommonFile(String image, String value)
		throws Exception {

		LiferaySeleniumHelper.sikuliUploadCommonFile(this, image, value);
	}

	@Override
	public void sikuliUploadTCatFile(String image, String value)
		throws Exception {

		LiferaySeleniumHelper.sikuliUploadTCatFile(this, image, value);
	}

	@Override
	public void sikuliUploadTempFile(String image, String value)
		throws Exception {

		LiferaySeleniumHelper.sikuliUploadTempFile(this, image, value);
	}

	@Override
	public void startLogger() {
	}

	@Override
	public void stopLogger() {
	}

	@Override
	public void typeAceEditor(String locator, String value) {
		LiferaySeleniumHelper.typeAceEditor(this, locator, value);
	}

	@Override
	public void typeFrame(String locator, String value) {
		LiferaySeleniumHelper.typeFrame(this, locator, value);
	}

	@Override
	public void typeKeys(String locator, String value) {
		typeKeys(locator, value, false);
	}

	@Override
	public void typeKeys(String locator, String value, boolean typeAceEditor) {
		sendKeys(locator, value);
	}

	@Override
	public void uploadCommonFile(String location, String value) {
		super.type(location, _projectDirName + _dependenciesDirName + value);
	}

	@Override
	public void uploadFile(String location, String value) {
		makeVisible(location);

		super.type(location, value);
	}

	@Override
	public void uploadTempFile(String location, String value) {
		super.type(location, TestPropsValues.OUTPUT_DIR_NAME + value);
	}

	@Override
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

	@Override
	public void waitForElementNotPresent(String locator) throws Exception {
		LiferaySeleniumHelper.waitForElementNotPresent(this, locator);
	}

	@Override
	public void waitForElementPresent(String locator) throws Exception {
		LiferaySeleniumHelper.waitForElementPresent(this, locator);
	}

	@Override
	public void waitForNotPartialText(String locator, String value)
		throws Exception {

		LiferaySeleniumHelper.waitForNotPartialText(this, locator, value);
	}

	@Override
	public void waitForNotSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		LiferaySeleniumHelper.waitForNotSelectedLabel(
			this, selectLocator, pattern);
	}

	@Override
	public void waitForNotText(String locator, String value) throws Exception {
		LiferaySeleniumHelper.waitForNotText(this, locator, value);
	}

	@Override
	public void waitForNotValue(String locator, String value) throws Exception {
		LiferaySeleniumHelper.waitForNotValue(this, locator, value);
	}

	@Override
	public void waitForNotVisible(String locator) throws Exception {
		LiferaySeleniumHelper.waitForNotVisible(this, locator);
	}

	@Override
	public void waitForPageToLoad(String timeout) {
		super.waitForPageToLoad(_timeout);
	}

	@Override
	public void waitForPartialText(String locator, String value)
		throws Exception {

		LiferaySeleniumHelper.waitForPartialText(this, locator, value);
	}

	@Override
	public void waitForSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		LiferaySeleniumHelper.waitForSelectedLabel(
			this, selectLocator, pattern);
	}

	@Override
	public void waitForText(String locator, String value) throws Exception {
		LiferaySeleniumHelper.waitForText(this, locator, value);
	}

	@Override
	public void waitForTextNotPresent(String value) throws Exception {
		LiferaySeleniumHelper.waitForTextNotPresent(this, value);
	}

	@Override
	public void waitForTextPresent(String value) throws Exception {
		LiferaySeleniumHelper.waitForTextPresent(this, value);
	}

	@Override
	public void waitForValue(String locator, String value) throws Exception {
		LiferaySeleniumHelper.waitForValue(this, locator, value);
	}

	@Override
	public void waitForVisible(String locator) throws Exception {
		LiferaySeleniumHelper.waitForVisible(this, locator);
	}

	@Override
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
		TestPropsValues.OUTPUT_DIR_NAME + "screenshots/";

	private String _clipBoard = "";
	private CommandProcessor _commandProcessor;
	private String _dependenciesDirName =
		"portal-web//test//functional//com//liferay//portalweb//dependencies//";
	private String _outputDirName = TestPropsValues.OUTPUT_DIR_NAME;
	private String _primaryTestSuiteName;
	private String _projectDirName;
	private String _sikuliImagesDirName =
		_dependenciesDirName + "sikuli//linux//";
	private String _timeout = "90000";

}