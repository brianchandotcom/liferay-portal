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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portalweb.portal.util.TestPropsValues;

import io.appium.java_client.MobileDriver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsDriver;

/**
 * @author Kenji Heigel
 */
public abstract class BaseMobileDriverImpl
	extends MobileDriverToSeleniumBridge implements LiferaySelenium {

	public BaseMobileDriverImpl(
		String projectDirName, String browserURL, MobileDriver mobileDriver) {

		super(mobileDriver);
	}

	@Override
	public void antCommand(String fileName, String target) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void assertAlert(String pattern) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void assertAlertNotPresent() throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void assertChecked(String pattern) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void assertConfirmation(String pattern) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void assertConsoleTextNotPresent(String text) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void assertConsoleTextPresent(String text) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void assertElementNotPresent(String locator) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void assertElementPresent(String locator) throws Exception {
		LiferaySeleniumHelper.assertElementPresent(this, locator);
	}

	@Override
	public void assertEmailBody(String index, String body) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void assertEmailSubject(String index, String subject)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	public void assertHTMLSourceTextNotPresent(String value) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void assertHTMLSourceTextPresent(String value) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void assertJavaScriptErrors(String ignoreJavaScriptError)
		throws Exception {

		WebDriverHelper.assertJavaScriptErrors(this, ignoreJavaScriptError);
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
		throw new UnsupportedOperationException();
	}

	@Override
	public void assertNotAlert(String pattern) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void assertNotChecked(String locator) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void assertNotLocation(String pattern) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void assertNotPartialText(String locator, String pattern)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	public void assertNotSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	public void assertNotText(String locator, String pattern) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void assertNotValue(String locator, String pattern)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	public void assertNotVisible(String locator) throws Exception {
		LiferaySeleniumHelper.assertNotVisible(this, locator);
	}

	@Override
	public void assertPartialText(String locator, String pattern)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	public void assertSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	public void assertText(String locator, String pattern) throws Exception {
		LiferaySeleniumHelper.assertText(this, locator, pattern);
	}

	@Override
	public void assertTextNotPresent(String pattern) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void assertTextPresent(String pattern) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void assertValue(String locator, String pattern) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void assertVisible(String locator) throws Exception {
		LiferaySeleniumHelper.assertVisible(this, locator);
	}

	@Override
	public void clickAndWait(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clickAtAndWait(String locator, String coordString) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void connectToEmailAccount(String emailAddress, String emailPassword)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	public void copyText(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void copyValue(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteAllEmails() throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void echo(String message) {
		LiferaySeleniumHelper.echo(message);
	}

	@Override
	public void fail(String message) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCurrentDay() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCurrentMonth() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCurrentYear() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getDependenciesDirName() {
		return _dependenciesDirName;
	}

	@Override
	public String getEmailBody(String index) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getEmailSubject(String index) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getFirstNumber(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getFirstNumberIncrement(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getNumberDecrement(String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getNumberIncrement(String value) {
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isConfirmation(String pattern) {
		throw new UnsupportedOperationException();
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
	public boolean isHTMLSourceTextPresent(String value) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isMobileDeviceEnabled() {
		return LiferaySeleniumHelper.isMobileDeviceEnabled();
	}

	@Override
	public boolean isNotChecked(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isNotPartialText(String locator, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isNotSelectedLabel(String selectLocator, String pattern) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isNotText(String locator, String value) {
		return LiferaySeleniumHelper.isNotText(this, locator, value);
	}

	@Override
	public boolean isNotValue(String locator, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isNotVisible(String locator) {
		return LiferaySeleniumHelper.isNotVisible(this, locator);
	}

	@Override
	public boolean isPartialText(String locator, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSelectedLabel(String selectLocator, String pattern) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isTCatEnabled() {
		return LiferaySeleniumHelper.isTCatEnabled();
	}

	@Override
	public boolean isText(String locator, String value) {
		return value.equals(getText(locator, "1"));
	}

	@Override
	public boolean isTextNotPresent(String pattern) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isValue(String locator, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void keyDownAndWait(String locator, String keySequence) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void keyPressAndWait(String locator, String keySequence) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void keyUpAndWait(String locator, String keySequence) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void makeVisible(String locator) {
		WebDriverHelper.makeVisible(this, locator);
	}

	@Override
	public void mouseRelease() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void paste(String locator) {
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();
	}

	@Override
	public void replyToEmail(String to, String body) throws Exception {
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();
	}

	@Override
	public void saveScreenshotBeforeAction(boolean actionFailed)
		throws Exception {

		if (!TestPropsValues.SAVE_SCREENSHOT) {
			return;
		}

		LiferaySeleniumHelper.saveScreenshotBeforeAction(this, actionFailed);
	}

	@Override
	public void scrollWebElementIntoView(String locator) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void selectAndWait(String selectLocator, String optionLocator) {
		throw new UnsupportedOperationException();
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

		throw new UnsupportedOperationException();
	}

	@Override
	public void sendKeys(String locator, String value) {
		WebDriverHelper.type(this, locator, value);
	}

	@Override
	public void sendKeysAceEditor(String locator, String value) {
		throw new UnsupportedOperationException();
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
		throw new UnsupportedOperationException();
	}

	@Override
	public void setPrimaryTestSuiteName(String primaryTestSuiteName) {
		_primaryTestSuiteName = primaryTestSuiteName;
	}

	@Override
	public void setTimeoutImplicit(String timeout) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setWindowSize(String coordString) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sikuliAssertElementNotPresent(String image) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sikuliAssertElementPresent(String image) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sikuliClick(String image) throws Exception {
		LiferaySeleniumHelper.sikuliClick(this, image);
	}

	@Override
	public void sikuliDragAndDrop(String image, String coordString)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	public void sikuliLeftMouseDown() throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sikuliLeftMouseUp() throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sikuliMouseMove(String image) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sikuliRightMouseDown() throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sikuliRightMouseUp() throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sikuliType(String image, String value) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void sikuliUploadCommonFile(String image, String value)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	public void sikuliUploadTCatFile(String image, String value)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	public void sikuliUploadTempFile(String image, String value)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	public void startLogger() {
	}

	@Override
	public void stopLogger() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void tap(String locator) {
		WebElement webElement = getWebElement("//body");

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver wrappedWebDriver = wrapsDriver.getWrappedDriver();

		JavascriptExecutor javascriptExecutor =
			(JavascriptExecutor)wrappedWebDriver;

		StringBundler sb = new StringBundler(5);

		sb.append("YUI().use('node-event-simulate', function(Y) {");
		sb.append("var node = Y.one('");

		String cssLocator = locator;

		cssLocator = cssLocator.replaceAll("\\/\\/", " ");
		cssLocator = cssLocator.replaceAll(
			"contains\\(@([^,]*),([^)]*)\\)", "$1*=$2");
		cssLocator = cssLocator.replaceAll("'", "\"");
		cssLocator = cssLocator.replaceAll("\\/", " > ");
		cssLocator = cssLocator.replaceAll("^ ", "");
		cssLocator = cssLocator.replaceAll(" and ", "][");

		sb.append(cssLocator);

		sb.append("');");
		sb.append("node.simulateGesture('tap');});");

		javascriptExecutor.executeScript(sb.toString());
	}

	@Override
	public void typeAceEditor(String locator, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void typeFrame(String locator, String value) {
		LiferaySeleniumHelper.typeFrame(this, locator, value);
	}

	@Override
	public void typeKeys(String locator, String value, boolean typeAceEditor) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void typeScreen(String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void uploadCommonFile(String locator, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void uploadFile(String locator, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void uploadTempFile(String locator, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void waitForConfirmation(String pattern) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void waitForElementNotPresent(String locator) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void waitForElementPresent(String locator) throws Exception {
		LiferaySeleniumHelper.waitForElementPresent(this, locator);
	}

	@Override
	public void waitForNotPartialText(String locator, String value)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	public void waitForNotSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	public void waitForNotText(String locator, String value) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void waitForNotValue(String locator, String value) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void waitForNotVisible(String locator) throws Exception {
		LiferaySeleniumHelper.waitForNotVisible(this, locator);
	}

	@Override
	public void waitForPartialText(String locator, String value)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	public void waitForSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	public void waitForText(String locator, String value) throws Exception {
		LiferaySeleniumHelper.waitForText(this, locator, value);
	}

	@Override
	public void waitForTextNotPresent(String value) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void waitForTextPresent(String value) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void waitForValue(String locator, String value) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void waitForVisible(String locator) throws Exception {
		LiferaySeleniumHelper.waitForVisible(this, locator);
	}

	@Override
	public void windowMaximizeAndWait() {
		throw new UnsupportedOperationException();
	}

	private String _dependenciesDirName =
		"portal-web//test//functional//com//liferay//portalweb//dependencies//";
	private String _outputDirName = TestPropsValues.OUTPUT_DIR_NAME;
	private String _primaryTestSuiteName;
	private String _projectDirName;
	private String _sikuliImagesDirName =
		_dependenciesDirName + "sikuli//linux//";

}