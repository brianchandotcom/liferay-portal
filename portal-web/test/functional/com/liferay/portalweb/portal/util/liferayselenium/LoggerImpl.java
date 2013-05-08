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

import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class LoggerImpl implements LiferaySelenium {

	public LoggerImpl(String projectDir, LiferaySelenium liferaySelenium) {
		_liferaySelenium = liferaySelenium;

		_logger = new Logger(projectDir);
	}

	public void addCustomRequestHeader(String key, String value) {
		try {
			_liferaySelenium.addCustomRequestHeader(key, value);
		}
		catch (Exception e) {
		}
	}

	public void addLocationStrategy(
		String strategyName, String functionDefinition) {

		try {
			_liferaySelenium.addLocationStrategy(
				strategyName, functionDefinition);
		}
		catch (Exception e) {
		}
	}

	public void addScript(String scriptContent, String scriptTagId) {
		try {
			_liferaySelenium.addScript(scriptContent, scriptTagId);
		}
		catch (Exception e) {
		}
	}

	public void addSelection(String locator, String optionLocator) {
		try {
			_liferaySelenium.addSelection(locator, optionLocator);
		}
		catch (Exception e) {
		}
	}

	public void allowNativeXpath(String allow) {
		try {
			_liferaySelenium.allowNativeXpath(allow);
		}
		catch (Exception e) {
		}
	}

	public void altKeyDown() {
		try {
			_liferaySelenium.altKeyDown();
		}
		catch (Exception e) {
		}
	}

	public void altKeyUp() {
		try {
			_liferaySelenium.altKeyUp();
		}
		catch (Exception e) {
		}
	}

	public void answerOnNextPrompt(String answer) {
		try {
			_liferaySelenium.answerOnNextPrompt(answer);
		}
		catch (Exception e) {
		}
	}

	public void assertAlert(String pattern) {
		try {
			_liferaySelenium.assertAlert(pattern);
		}
		catch (Exception e) {
		}
	}

	public void assertChecked(String pattern) {
		try {
			_liferaySelenium.assertChecked(pattern);
		}
		catch (Exception e) {
		}
	}

	public void assertConfirmation(String pattern) {
		try {
			_liferaySelenium.assertConfirmation(pattern);
		}
		catch (Exception e) {
		}
	}

	public void assertElementNotPresent(String locator) {
		try {
			_liferaySelenium.assertElementNotPresent(locator);
		}
		catch (Exception e) {
		}
	}

	public void assertElementPresent(String locator) {
		try {
			_liferaySelenium.assertElementPresent(locator);
		}
		catch (Exception e) {
		}
	}

	public void assertLocation(String pattern) {
		try {
			_liferaySelenium.assertLocation(pattern);
		}
		catch (Exception e) {
		}
	}

	public void assertNotAlert(String pattern) {
		try {
			_liferaySelenium.assertNotAlert(pattern);
		}
		catch (Exception e) {
		}
	}

	public void assertNotChecked(String locator) {
		try {
			_liferaySelenium.assertNotChecked(locator);
		}
		catch (Exception e) {
		}
	}

	public void assertNotLocation(String pattern) {
		try {
			_liferaySelenium.assertNotLocation(pattern);
		}
		catch (Exception e) {
		}
	}

	public void assertNotPartialText(String locator, String pattern) {
		try {
			_liferaySelenium.assertNotPartialText(locator, pattern);
		}
		catch (Exception e) {
		}
	}

	public void assertNotSelectedLabel(String selectLocator, String pattern) {
		try {
			_liferaySelenium.assertNotSelectedLabel(selectLocator, pattern);
		}
		catch (Exception e) {
		}
	}

	public void assertNotText(String locator, String pattern) {
		try {
			_liferaySelenium.assertNotText(locator, pattern);
		}
		catch (Exception e) {
		}
	}

	public void assertNotValue(String locator, String pattern) {
		try {
			_liferaySelenium.assertNotValue(locator, pattern);
		}
		catch (Exception e) {
		}
	}

	public void assertNotVisible(String locator) {
		try {
			_liferaySelenium.assertNotVisible(locator);
		}
		catch (Exception e) {
		}
	}

	public void assertPartialText(String locator, String pattern) {
		try {
			_liferaySelenium.assertPartialText(locator, pattern);
		}
		catch (Exception e) {
		}
	}

	public void assertSelectedLabel(String selectLocator, String pattern) {
		try {
			_liferaySelenium.assertSelectedLabel(selectLocator, pattern);
		}
		catch (Exception e) {
		}
	}

	public void assertText(String locator, String pattern) {
		try {
			_liferaySelenium.assertText(locator, pattern);
		}
		catch (Exception e) {
		}
	}

	public void assertTextNotPresent(String pattern) {
		try {
			_liferaySelenium.assertTextNotPresent(pattern);
		}
		catch (Exception e) {
		}
	}

	public void assertTextPresent(String pattern) {
		try {
			_liferaySelenium.assertTextPresent(pattern);
		}
		catch (Exception e) {
		}
	}

	public void assertValue(String locator, String pattern) {
		try {
			_liferaySelenium.assertValue(locator, pattern);
		}
		catch (Exception e) {
		}
	}

	public void assertVisible(String locator) {
		try {
			_liferaySelenium.assertVisible(locator);
		}
		catch (Exception e) {
		}
	}

	public void assignId(String locator, String identifier) {
		try {
			_liferaySelenium.assignId(locator, identifier);
		}
		catch (Exception e) {
		}
	}

	public void attachFile(String fieldLocator, String fileLocator) {
		try {
			_liferaySelenium.attachFile(fieldLocator, fileLocator);
		}
		catch (Exception e) {
		}
	}

	public void captureEntirePageScreenshot(String fileName, String kwargs) {
		try {
			_liferaySelenium.captureEntirePageScreenshot(fileName, kwargs);
		}
		catch (Exception e) {
		}
	}

	public String captureEntirePageScreenshotToString(String kwargs) {
		try {
			return _liferaySelenium.captureEntirePageScreenshotToString(kwargs);
		}
		catch (Exception e) {

			return null;
		}
	}

	public String captureNetworkTraffic(String type) {
		try {
			return _liferaySelenium.captureNetworkTraffic(type);
		}
		catch (Exception e) {

			return null;
		}
	}

	public void captureScreenshot(String fileName) {
		try {
			_liferaySelenium.captureScreenshot(fileName);
		}
		catch (Exception e) {
		}
	}

	public String captureScreenshotToString() {
		try {
			return _liferaySelenium.captureScreenshotToString();
		}
		catch (Exception e) {

			return null;
		}
	}

	public void check(String locator) {
		try {
			_liferaySelenium.check(locator);
		}
		catch (Exception e) {
		}
	}

	public void chooseCancelOnNextConfirmation() {
		try {
			_liferaySelenium.chooseCancelOnNextConfirmation();
		}
		catch (Exception e) {
		}
	}

	public void chooseOkOnNextConfirmation() {
		try {
			_liferaySelenium.chooseOkOnNextConfirmation();
		}
		catch (Exception e) {
		}
	}

	public void click(String locator) {
		try {
			_liferaySelenium.click(locator);
		}
		catch (Exception e) {
		}
	}

	public void clickAndWait(String locator) {
		try {
			_liferaySelenium.clickAndWait(locator);
		}
		catch (Exception e) {
		}
	}

	public void clickAt(String locator, String coordString) {
		try {
			_liferaySelenium.clickAt(locator, coordString);
		}
		catch (Exception e) {
		}
	}

	public void clickAtAndWait(String locator, String coordString) {
		try {
			_liferaySelenium.clickAtAndWait(locator, coordString);
		}
		catch (Exception e) {
		}
	}

	public void close() {
		try {
			_liferaySelenium.close();
		}
		catch (Exception e) {
		}
	}

	public void contextMenu(String locator) {
		try {
			_liferaySelenium.contextMenu(locator);
		}
		catch (Exception e) {
		}
	}

	public void contextMenuAt(String locator, String coordString) {
		try {
			_liferaySelenium.contextMenuAt(locator, coordString);
		}
		catch (Exception e) {
		}
	}

	public void controlKeyDown() {
		try {
			_liferaySelenium.controlKeyDown();
		}
		catch (Exception e) {
		}
	}

	public void controlKeyUp() {
		try {
			_liferaySelenium.controlKeyUp();
		}
		catch (Exception e) {
		}
	}

	public void copyText(String locator) {
		try {
			_liferaySelenium.copyText(locator);
		}
		catch (Exception e) {
		}
	}

	public void copyValue(String locator) {
		try {
			_liferaySelenium.copyValue(locator);
		}
		catch (Exception e) {
		}
	}

	public void createCookie(String nameValuePair, String optionsString) {
		try {
			_liferaySelenium.createCookie(nameValuePair, optionsString);
		}
		catch (Exception e) {
		}
	}

	public void deleteAllVisibleCookies() {
		try {
			_liferaySelenium.deleteAllVisibleCookies();
		}
		catch (Exception e) {
		}
	}

	public void deleteCookie(String name, String optionsString) {
		try {
			_liferaySelenium.deleteCookie(name, optionsString);
		}
		catch (Exception e) {
		}
	}

	public void deselectPopUp() {
		try {
			_liferaySelenium.deselectPopUp();
		}
		catch (Exception e) {
		}
	}

	public void doubleClick(String locator) {
		try {
			_liferaySelenium.doubleClick(locator);
		}
		catch (Exception e) {
		}
	}

	public void doubleClickAt(String locator, String coordString) {
		try {
			_liferaySelenium.doubleClickAt(locator, coordString);
		}
		catch (Exception e) {
		}
	}

	public void dragAndDrop(String locator, String movementsString) {
		try {
			_liferaySelenium.dragAndDrop(locator, movementsString);
		}
		catch (Exception e) {
		}
	}

	public void dragAndDropToObject(
		String locatorOfObjectToBeDragged,
		String locatorOfDragDestinationObject) {

		String[] params = {
			locatorOfObjectToBeDragged, locatorOfDragDestinationObject};

		try {
			_liferaySelenium.dragAndDropToObject(
				locatorOfObjectToBeDragged, locatorOfDragDestinationObject);
		}
		catch (Exception e) {
		}
	}

	public void dragdrop(String locator, String movementsString) {
		try {
			_liferaySelenium.dragdrop(locator, movementsString);
		}
		catch (Exception e) {
		}
	}

	public void echo(String message) {
		try {
			_liferaySelenium.echo(message);
		}
		catch (Exception e) {
		}
	}

	public void fail(String message) {
		try {
			_liferaySelenium.fail(message);
		}
		catch (Exception e) {
		}
	}

	public void fireEvent(String locator, String eventName) {
		try {
			_liferaySelenium.fireEvent(locator, eventName);
		}
		catch (Exception e) {
		}
	}

	public void focus(String locator) {
		try {
			_liferaySelenium.focus(locator);
		}
		catch (Exception e) {
		}
	}

	public String getAlert() {
		try {
			return _liferaySelenium.getAlert();
		}
		catch (Exception e) {

			return null;
		}
	}

	public String[] getAllButtons() {
		try {
			return _liferaySelenium.getAllButtons();
		}
		catch (Exception e) {

			return null;
		}
	}

	public String[] getAllFields() {
		try {
			return _liferaySelenium.getAllFields();
		}
		catch (Exception e) {

			return null;
		}
	}

	public String[] getAllLinks() {
		try {
			return _liferaySelenium.getAllLinks();
		}
		catch (Exception e) {

			return null;
		}
	}

	public String[] getAllWindowIds() {
		try {
			return _liferaySelenium.getAllWindowIds();
		}
		catch (Exception e) {

			return null;
		}
	}

	public String[] getAllWindowNames() {
		try {
			return _liferaySelenium.getAllWindowNames();
		}
		catch (Exception e) {

			return null;
		}
	}

	public String[] getAllWindowTitles() {
		try {
			return _liferaySelenium.getAllWindowTitles();
		}
		catch (Exception e) {

			return null;
		}
	}

	public String getAttribute(String attributeLocator) {
		try {
			return _liferaySelenium.getAttribute(attributeLocator);
		}
		catch (Exception e) {

			return null;
		}
	}

	public String[] getAttributeFromAllWindows(String attributeName) {
		try {
			return _liferaySelenium.getAttributeFromAllWindows(attributeName);
		}
		catch (Exception e) {

			return null;
		}
	}

	public String getBodyText() {
		try {
			return _liferaySelenium.getBodyText();
		}
		catch (Exception e) {

			return null;
		}
	}

	public String getConfirmation() {
		try {
			return _liferaySelenium.getConfirmation();
		}
		catch (Exception e) {

			return null;
		}
	}

	public String getCookie() {
		try {
			return _liferaySelenium.getCookie();
		}
		catch (Exception e) {

			return null;
		}
	}

	public String getCookieByName(String name) {
		try {
			return _liferaySelenium.getCookieByName(name);
		}
		catch (Exception e) {

			return null;
		}
	}

	public Number getCssCount(String css) {
		try {
			return _liferaySelenium.getCssCount(css);
		}
		catch (Exception e) {

			return null;
		}
	}

	public String getCurrentDay() {
		try {
			return _liferaySelenium.getCurrentDay();
		}
		catch (Exception e) {

			return null;
		}
	}

	public String getCurrentMonth() {
		try {
			return _liferaySelenium.getCurrentMonth();
		}
		catch (Exception e) {

			return null;
		}
	}

	public String getCurrentYear() {
		try {
			return _liferaySelenium.getCurrentYear();
		}
		catch (Exception e) {

			return null;
		}
	}

	public Number getCursorPosition(String locator) {
		try {
			return _liferaySelenium.getCursorPosition(locator);
		}
		catch (Exception e) {

			return null;
		}
	}

	public Number getElementHeight(String locator) {
		try {
			return _liferaySelenium.getElementHeight(locator);
		}
		catch (Exception e) {

			return null;
		}
	}

	public Number getElementIndex(String locator) {
		try {
			return _liferaySelenium.getElementIndex(locator);
		}
		catch (Exception e) {

			return null;
		}
	}

	public Number getElementPositionLeft(String locator) {
		try {
			return _liferaySelenium.getElementPositionLeft(locator);
		}
		catch (Exception e) {

			return null;
		}
	}

	public Number getElementPositionTop(String locator) {
		try {
			return _liferaySelenium.getElementPositionTop(locator);
		}
		catch (Exception e) {

			return null;
		}
	}

	public Number getElementWidth(String locator) {
		try {
			return _liferaySelenium.getElementWidth(locator);
		}
		catch (Exception e) {

			return null;
		}
	}

	public String getEval(String script) {
		try {
			return _liferaySelenium.getEval(script);
		}
		catch (Exception e) {

			return null;
		}
	}

	public String getExpression(String expression) {
		try {
			return _liferaySelenium.getExpression(expression);
		}
		catch (Exception e) {

			return null;
		}
	}

	public String getFirstNumber(String locator) {
		try {
			return _liferaySelenium.getFirstNumber(locator);
		}
		catch (Exception e) {

			return null;
		}
	}

	public String getFirstNumberIncrement(String locator) {
		try {
			return _liferaySelenium.getFirstNumberIncrement(locator);
		}
		catch (Exception e) {

			return null;
		}
	}

	public String getHtmlSource() {
		try {
			return _liferaySelenium.getHtmlSource();
		}
		catch (Exception e) {

			return null;
		}
	}

	public String getLocation() {
		try {
			return _liferaySelenium.getLocation();
		}
		catch (Exception e) {

			return null;
		}
	}

	public String getLog() {
		try {
			return _liferaySelenium.getLog();
		}
		catch (Exception e) {

			return null;
		}
	}

	public Number getMouseSpeed() {
		try {
			return _liferaySelenium.getMouseSpeed();
		}
		catch (Exception e) {

			return null;
		}
	}

	public String getNumberDecrement(String value) {
		try {
			return _liferaySelenium.getNumberDecrement(value);
		}
		catch (Exception e) {

			return null;
		}
	}

	public String getNumberIncrement(String value) {
		try {
			return _liferaySelenium.getNumberIncrement(value);
		}
		catch (Exception e) {

			return null;
		}
	}

	public String getPrompt() {
		try {
			return _liferaySelenium.getPrompt();
		}
		catch (Exception e) {

			return null;
		}
	}

	public String getSelectedId(String selectLocator) {
		try {
			return _liferaySelenium.getSelectedId(selectLocator);
		}
		catch (Exception e) {

			return null;
		}
	}

	public String[] getSelectedIds(String selectLocator) {
		try {
			return _liferaySelenium.getSelectedIds(selectLocator);
		}
		catch (Exception e) {

			return null;
		}
	}

	public String getSelectedIndex(String selectLocator) {
		try {
			return _liferaySelenium.getSelectedIndex(selectLocator);
		}
		catch (Exception e) {

			return null;
		}
	}

	public String[] getSelectedIndexes(String selectLocator) {
		try {
			return _liferaySelenium.getSelectedIndexes(selectLocator);
		}
		catch (Exception e) {

			return null;
		}
	}

	public String getSelectedLabel(String selectLocator) {
		try {
			return _liferaySelenium.getSelectedLabel(selectLocator);
		}
		catch (Exception e) {

			return null;
		}
	}

	public String[] getSelectedLabels(String selectLocator) {
		try {
			return _liferaySelenium.getSelectedLabels(selectLocator);
		}
		catch (Exception e) {

			return null;
		}
	}

	public String getSelectedValue(String selectLocator) {
		try {
			return _liferaySelenium.getSelectedValue(selectLocator);
		}
		catch (Exception e) {

			return null;
		}
	}

	public String[] getSelectedValues(String selectLocator) {
		try {
			return _liferaySelenium.getSelectedValues(selectLocator);
		}
		catch (Exception e) {

			return null;
		}
	}

	public String[] getSelectOptions(String selectLocator) {
		try {
			return _liferaySelenium.getSelectOptions(selectLocator);
		}
		catch (Exception e) {

			return null;
		}
	}

	public String getSpeed() {
		try {
			return _liferaySelenium.getSpeed();
		}
		catch (Exception e) {

			return null;
		}
	}

	public String getTable(String tableCellAddress) {
		try {
			return _liferaySelenium.getTable(tableCellAddress);
		}
		catch (Exception e) {

			return null;
		}
	}

	public String getText(String locator) {
		try {
			return _liferaySelenium.getText(locator);
		}
		catch (Exception e) {

			return null;
		}
	}

	public String getTitle() {
		try {
			return _liferaySelenium.getTitle();
		}
		catch (Exception e) {

			return null;
		}
	}

	public String getValue(String locator) {
		try {
			return _liferaySelenium.getValue(locator);
		}
		catch (Exception e) {
			return null;
		}
	}

	public boolean getWhetherThisFrameMatchFrameExpression(
		String currentFrameString, String target) {

		try {
			return _liferaySelenium.getWhetherThisFrameMatchFrameExpression(
				currentFrameString, target);
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean getWhetherThisWindowMatchWindowExpression(
		String currentFrameString, String target) {

		try {
			return _liferaySelenium.getWhetherThisWindowMatchWindowExpression(
				currentFrameString, target);
		}
		catch (Exception e) {
			return false;
		}
	}

	public Number getXpathCount(String xpath) {
		try {
			return _liferaySelenium.getXpathCount(xpath);
		}
		catch (Exception e) {

			return null;
		}
	}

	public void goBack() {
		try {
			_liferaySelenium.goBack();
		}
		catch (Exception e) {
		}
	}

	public void goBackAndWait() {
		try {
			_liferaySelenium.goBackAndWait();
		}
		catch (Exception e) {
		}
	}

	public void highlight(String locator) {
		try {
			_liferaySelenium.highlight(locator);
		}
		catch (Exception e) {
		}
	}

	public void ignoreAttributesWithoutValue(String ignore) {
		try {
			_liferaySelenium.ignoreAttributesWithoutValue(ignore);
		}
		catch (Exception e) {
		}
	}

	public boolean isAlertPresent() {
		try {
			return _liferaySelenium.isAlertPresent();
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean isChecked(String locator) {
		try {
			return _liferaySelenium.isChecked(locator);
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean isConfirmation(String pattern) {
		try {
			return _liferaySelenium.isConfirmation(pattern);
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean isConfirmationPresent() {
		try {
			return _liferaySelenium.isConfirmationPresent();
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean isCookiePresent(String name) {
		try {
			return _liferaySelenium.isCookiePresent(name);
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean isEditable(String locator) {
		try {
			return _liferaySelenium.isEditable(locator);
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean isElementNotPresent(String locator) {
		try {
			return _liferaySelenium.isElementNotPresent(locator);
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean isElementPresent(String locator) {
		try {
			return _liferaySelenium.isElementPresent(locator);
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean isNotChecked(String locator) {
		try {
			return _liferaySelenium.isNotChecked(locator);
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean isNotPartialText(String locator, String value) {
		try {
			return _liferaySelenium.isNotPartialText(locator, value);
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean isNotSelectedLabel(String selectLocator, String pattern) {
		try {
			return _liferaySelenium.isNotSelectedLabel(selectLocator, pattern);
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean isNotText(String locator, String value) {
		try {
			return _liferaySelenium.isNotText(locator, value);
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean isNotValue(String locator, String value) {
		try {
			return _liferaySelenium.isNotValue(locator, value);
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean isNotVisible(String locator) {
		try {
			return _liferaySelenium.isNotVisible(locator);
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean isOrdered(String locator1, String locator2) {
		try {
			return _liferaySelenium.isOrdered(locator1, locator2);
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean isPartialText(String locator, String value) {
		try {
			return _liferaySelenium.isPartialText(locator, value);
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean isPromptPresent() {
		try {
			return _liferaySelenium.isPromptPresent();
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean isSelectedLabel(String selectLocator, String pattern) {
		try {
			return _liferaySelenium.isSelectedLabel(selectLocator, pattern);
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean isSomethingSelected(String selectLocator) {
		try {
			return _liferaySelenium.isSomethingSelected(selectLocator);
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean isText(String locator, String value) {
		try {
			return _liferaySelenium.isText(locator, value);
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean isTextNotPresent(String pattern) {
		try {
			return _liferaySelenium.isTextNotPresent(pattern);
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean isTextPresent(String pattern) {
		try {
			return _liferaySelenium.isTextPresent(pattern);
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean isValue(String locator, String value) {
		try {
			return _liferaySelenium.isValue(locator, value);
		}
		catch (Exception e) {
			return false;
		}
	}

	public boolean isVisible(String locator) {
		try {
			return _liferaySelenium.isVisible(locator);
		}
		catch (Exception e) {
			return false;
		}
	}

	public void keyDown(String locator, String keySequence) {
		try {
			_liferaySelenium.keyDown(locator, keySequence);
		}
		catch (Exception e) {
		}
	}

	public void keyDownAndWait(String locator, String keySequence) {
		try {
			_liferaySelenium.keyDownAndWait(locator, keySequence);
		}
		catch (Exception e) {
		}
	}

	public void keyDownNative(String keycode) {
		try {
			_liferaySelenium.keyDownNative(keycode);
		}
		catch (Exception e) {
		}
	}

	public void keyPress(String locator, String keySequence) {
		try {
			_liferaySelenium.keyPress(locator, keySequence);
		}
		catch (Exception e) {
		}
	}

	public void keyPressAndWait(String locator, String keySequence) {
		try {
			_liferaySelenium.keyPressAndWait(locator, keySequence);
		}
		catch (Exception e) {
		}
	}

	public void keyPressNative(String keycode) {
		try {
			_liferaySelenium.keyPressNative(keycode);
		}
		catch (Exception e) {
		}
	}

	public void keyUp(String locator, String keySequence) {
		try {
			_liferaySelenium.keyUp(locator, keySequence);
		}
		catch (Exception e) {
		}
	}

	public void keyUpAndWait(String locator, String keySequence) {
		try {
			_liferaySelenium.keyUpAndWait(locator, keySequence);
		}
		catch (Exception e) {
		}
	}

	public void keyUpNative(String keycode) {
		try {
			_liferaySelenium.keyUpNative(keycode);
		}
		catch (Exception e) {
		}
	}

	public void makeVisible(String locator) {
		try {
			_liferaySelenium.makeVisible(locator);
		}
		catch (Exception e) {
		}
	}

	public void metaKeyDown() {
		try {
			_liferaySelenium.metaKeyDown();
		}
		catch (Exception e) {
		}
	}

	public void metaKeyUp() {
		try {
			_liferaySelenium.metaKeyUp();
		}
		catch (Exception e) {
		}
	}

	public void mouseDown(String locator) {
		try {
			_liferaySelenium.mouseDown(locator);
		}
		catch (Exception e) {
		}
	}

	public void mouseDownAt(String locator, String coordString) {
		try {
			_liferaySelenium.mouseDownAt(locator, coordString);
		}
		catch (Exception e) {
		}
	}

	public void mouseDownRight(String locator) {
		try {
			_liferaySelenium.mouseDownRight(locator);
		}
		catch (Exception e) {
		}
	}

	public void mouseDownRightAt(String locator, String coordString) {
		try {
			_liferaySelenium.mouseDownRightAt(locator, coordString);
		}
		catch (Exception e) {
		}
	}

	public void mouseMove(String locator) {
		try {
			_liferaySelenium.mouseMove(locator);
		}
		catch (Exception e) {
		}
	}

	public void mouseMoveAt(String locator, String coordString) {
		try {
			_liferaySelenium.mouseMoveAt(locator, coordString);
		}
		catch (Exception e) {
		}
	}

	public void mouseOut(String locator) {
		try {
			_liferaySelenium.mouseOut(locator);
		}
		catch (Exception e) {
		}
	}

	public void mouseOver(String locator) {
		try {
			_liferaySelenium.mouseOver(locator);
		}
		catch (Exception e) {
		}
	}

	public void mouseUp(String locator) {
		try {
			_liferaySelenium.mouseUp(locator);
		}
		catch (Exception e) {
		}
	}

	public void mouseUpAt(String locator, String coordString) {
		try {
			_liferaySelenium.mouseUpAt(locator, coordString);
		}
		catch (Exception e) {
		}
	}

	public void mouseUpRight(String locator) {
		try {
			_liferaySelenium.mouseUpRight(locator);
		}
		catch (Exception e) {
		}
	}

	public void mouseUpRightAt(String locator, String coordString) {
		try {
			_liferaySelenium.mouseUpRightAt(locator, coordString);
		}
		catch (Exception e) {
		}
	}

	public void open(String url) {
		url = RuntimeVariables.replace(url);

		try {
			_liferaySelenium.open(url);
		}
		catch (Exception e) {
		}
	}

	public void open(String url, String ignoreResponseCode) {
		try {
			_liferaySelenium.open(url, ignoreResponseCode);
		}
		catch (Exception e) {
		}
	}

	public void openWindow(String url, String windowID) {
		try {
			_liferaySelenium.openWindow(url, windowID);
		}
		catch (Exception e) {
		}
	}

	public void paste(String locator) {
		try {
			_liferaySelenium.paste(locator);
		}
		catch (Exception e) {
		}
	}

	public void pause(String waitTime) throws Exception {
		try {
			_liferaySelenium.pause(waitTime);
		}
		catch (Exception e) {
		}
	}

	public void refresh() {
		try {
			_liferaySelenium.refresh();
		}
		catch (Exception e) {
		}
	}

	public void refreshAndWait() {
		try {
			_liferaySelenium.refreshAndWait();
		}
		catch (Exception e) {
		}
	}

	public void removeAllSelections(String locator) {
		try {
			_liferaySelenium.removeAllSelections(locator);
		}
		catch (Exception e) {
		}
	}

	public void removeScript(String scriptTagId) {
		try {
			_liferaySelenium.removeScript(scriptTagId);
		}
		catch (Exception e) {
		}
	}

	public void removeSelection(String locator, String optionLocator) {
		try {
			_liferaySelenium.removeSelection(locator, optionLocator);
		}
		catch (Exception e) {
		}
	}

	public String retrieveLastRemoteControlLogs() {
		try {
			return _liferaySelenium.retrieveLastRemoteControlLogs();
		}
		catch (Exception e) {

			return null;
		}
	}

	public void rollup(String rollupName, String kwargs) {
		try {
			_liferaySelenium.rollup(rollupName, kwargs);
		}
		catch (Exception e) {
		}
	}

	public void runScript(String script) {
		try {
			_liferaySelenium.runScript(script);
		}
		catch (Exception e) {
		}
	}

	public void saveScreenShotAndSource() throws Exception {
		try {
			_liferaySelenium.saveScreenShotAndSource();
		}
		catch (Exception e) {
		}
	}

	public void select(String selectLocator, String optionLocator) {
		try {
			_liferaySelenium.select(selectLocator, optionLocator);
		}
		catch (Exception e) {
		}
	}

	public void selectAndWait(String selectLocator, String optionLocator) {
		try {
			_liferaySelenium.selectAndWait(selectLocator, optionLocator);
		}
		catch (Exception e) {
		}
	}

	public void selectFrame(String locator) {
		try {
			_liferaySelenium.selectFrame(locator);
		}
		catch (Exception e) {
		}
	}

	public void selectPopUp(String windowID) {
		try {
			_liferaySelenium.selectPopUp(windowID);
		}
		catch (Exception e) {
		}
	}

	public void selectWindow(String windowID) {
		try {
			_liferaySelenium.selectWindow(windowID);
		}
		catch (Exception e) {
		}
	}

	public void sendKeys(String locator, String value) {
		try {
			_liferaySelenium.sendKeys(locator, value);
		}
		catch (Exception e) {
		}
	}

	public void setBrowserLogLevel(String logLevel) {
		try {
			_liferaySelenium.setBrowserLogLevel(logLevel);
		}
		catch (Exception e) {
		}
	}

	public void setContext(String context) {
		try {
			_liferaySelenium.setContext(context);
		}
		catch (Exception e) {
		}
	}

	public void setCursorPosition(String locator, String position) {
		try {
			_liferaySelenium.setCursorPosition(locator, position);
		}
		catch (Exception e) {
		}
	}

	public void setDefaultTimeout() {
		try {
			_liferaySelenium.setDefaultTimeout();
		}
		catch (Exception e) {
		}
	}

	public void setDefaultTimeoutImplicit() {
		try {
			_liferaySelenium.setDefaultTimeoutImplicit();
		}
		catch (Exception e) {
		}
	}

	public void setExtensionJs(String extensionJs) {
		try {
			_liferaySelenium.setExtensionJs(extensionJs);
		}
		catch (Exception e) {
		}
	}

	public void setMouseSpeed(String pixels) {
		try {
			_liferaySelenium.setMouseSpeed(pixels);
		}
		catch (Exception e) {
		}
	}

	public void setSpeed(String value) {
		try {
			_liferaySelenium.setSpeed(value);
		}
		catch (Exception e) {
		}
	}

	public void setTimeout(String timeout) {
		try {
			_liferaySelenium.setTimeout(timeout);
		}
		catch (Exception e) {
		}
	}

	public void setTimeoutImplicit(String timeout) {
		try {
			_liferaySelenium.setTimeoutImplicit(timeout);
		}
		catch (Exception e) {
		}
	}

	public void shiftKeyDown() {
		try {
			_liferaySelenium.shiftKeyDown();
		}
		catch (Exception e) {
		}
	}

	public void shiftKeyUp() {
		try {
			_liferaySelenium.shiftKeyUp();
		}
		catch (Exception e) {
		}
	}

	public void showContextualBanner(String className, String methodName) {
		try {
			_liferaySelenium.showContextualBanner(className, methodName);
		}
		catch (Exception e) {
		}
	}

	public void showContextualBanner() {
		try {
			_liferaySelenium.showContextualBanner();
		}
		catch (Exception e) {
		}
	}

	public void shutDownSeleniumServer() {
		try {
			_liferaySelenium.shutDownSeleniumServer();
		}
		catch (Exception e) {
		}
	}

	public void start(Object optionsObject) {
		try {
			_liferaySelenium.start(optionsObject);
		}
		catch (Exception e) {
		}
	}

	public void start(String optionsString) {
		try {
			_liferaySelenium.start(optionsString);
		}
		catch (Exception e) {
		}
	}

	public void start() {
		try {
			_liferaySelenium.start();
		}
		catch (Exception e) {
		}
	}

	public void stop() {
		try {
			_liferaySelenium.stop();

			_logger.stop();
		}
		catch (Exception e) {
		}
	}

	public void submit(String formLocator) {
		try {
			_liferaySelenium.submit(formLocator);
		}
		catch (Exception e) {
		}
	}

	public void type(String locator, String value) {
		try {
			_liferaySelenium.type(locator, value);
		}
		catch (Exception e) {
		}
	}

	public void typeFrame(String locator, String value) {
		try {
			_liferaySelenium.typeFrame(locator, value);
		}
		catch (Exception e) {
		}
	}

	public void typeKeys(String locator, String value) {
		try {
			_liferaySelenium.typeKeys(locator, value);
		}
		catch (Exception e) {
		}
	}

	public void uncheck(String locator) {
		try {
			_liferaySelenium.uncheck(locator);
		}
		catch (Exception e) {
		}
	}

	public void uploadCommonFile(String locator, String value) {
		try {
			_liferaySelenium.uploadCommonFile(locator, value);
		}
		catch (Exception e) {
		}
	}

	public void uploadFile(String locator, String value) {
		try {
			_liferaySelenium.uploadFile(locator, value);
		}
		catch (Exception e) {
		}
	}

	public void uploadTempFile(String locator, String value) {
		try {
			_liferaySelenium.uploadTempFile(locator, value);
		}
		catch (Exception e) {
		}
	}

	public void useXpathLibrary(String libraryName) {
		try {
			_liferaySelenium.useXpathLibrary(libraryName);
		}
		catch (Exception e) {
		}
	}

	public void waitForCondition(String script, String timeout) {
		try {
			_liferaySelenium.waitForCondition(script, timeout);
		}
		catch (Exception e) {
		}
	}

	public void waitForConfirmation(String pattern) throws Exception {
		try {
			_liferaySelenium.waitForConfirmation(pattern);
		}
		catch (Exception e) {
		}
	}

	public void waitForElementNotPresent(String locator) throws Exception {
		try {
			_liferaySelenium.waitForElementNotPresent(locator);
		}
		catch (Exception e) {
		}
	}

	public void waitForElementPresent(String locator) throws Exception {
		try {
			_liferaySelenium.waitForElementPresent(locator);
		}
		catch (Exception e) {
		}
	}

	public void waitForFrameToLoad(String frameAddress, String timeout) {
		try {
			_liferaySelenium.waitForFrameToLoad(frameAddress, timeout);
		}
		catch (Exception e) {
		}
	}

	public void waitForNotPartialText(String locator, String value)
		throws Exception {

		try {
			_liferaySelenium.waitForNotPartialText(locator, value);
		}
		catch (Exception e) {
		}
	}

	public void waitForNotSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		try {
			_liferaySelenium.waitForNotSelectedLabel(selectLocator, pattern);
		}
		catch (Exception e) {
		}
	}

	public void waitForNotText(String locator, String value) throws Exception {
		try {
			_liferaySelenium.waitForNotText(locator, value);
		}
		catch (Exception e) {
		}
	}

	public void waitForNotValue(String locator, String value) throws Exception {
		try {
			_liferaySelenium.waitForNotValue(locator, value);
		}
		catch (Exception e) {
		}
	}

	public void waitForNotVisible(String locator) throws Exception {
		try {
			_liferaySelenium.waitForNotVisible(locator);
		}
		catch (Exception e) {
		}
	}

	public void waitForPageToLoad(String timeout) {
		try {
			_liferaySelenium.waitForPageToLoad(timeout);
		}
		catch (Exception e) {
		}
	}

	public void waitForPartialText(String locator, String value)
		throws Exception {

		try {
			_liferaySelenium.waitForPartialText(locator, value);
		}
		catch (Exception e) {
		}
	}

	public void waitForPopUp(String windowID, String timeout) {
		try {
			_liferaySelenium.waitForPopUp(windowID, timeout);
		}
		catch (Exception e) {
		}
	}

	public void waitForSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		try {
			_liferaySelenium.waitForSelectedLabel(selectLocator, pattern);
		}
		catch (Exception e) {
		}
	}

	public void waitForText(String locator, String value) throws Exception {
		try {
			_liferaySelenium.waitForText(locator, value);
		}
		catch (Exception e) {
		}
	}

	public void waitForTextNotPresent(String value) throws Exception {
		try {
			_liferaySelenium.waitForTextNotPresent(value);
		}
		catch (Exception e) {
		}
	}

	public void waitForTextPresent(String value) throws Exception {
		try {
			_liferaySelenium.waitForTextPresent(value);
		}
		catch (Exception e) {
		}
	}

	public void waitForValue(String locator, String value) throws Exception {
		try {
			_liferaySelenium.waitForValue(locator, value);
		}
		catch (Exception e) {
		}
	}

	public void waitForVisible(String locator) throws Exception {
		try {
			_liferaySelenium.waitForVisible(locator);
		}
		catch (Exception e) {
		}
	}

	public void windowFocus() {
		try {
			_liferaySelenium.windowFocus();
		}
		catch (Exception e) {
		}
	}

	public void windowMaximize() {
		try {
			_liferaySelenium.windowMaximize();
		}
		catch (Exception e) {
		}
	}

	public void windowMaximizeAndWait() {
		try {
			_liferaySelenium.windowMaximizeAndWait();
		}
		catch (Exception e) {
		}
	}

	private LiferaySelenium _liferaySelenium;
	private Logger _logger;

}