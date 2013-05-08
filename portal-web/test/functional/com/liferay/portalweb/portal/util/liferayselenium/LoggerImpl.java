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

	public LoggerImpl(LiferaySelenium liferaySelenium) {
		_liferaySelenium = liferaySelenium;

		_logger = new Logger();
	}

	public void addCustomRequestHeader(String key, String value) {
		String[] params = {key, value};

		_logger.logCommand("addCustomRequestHeader", params);

		try {
			_liferaySelenium.addCustomRequestHeader(key, value);
		}
		catch (Exception e) {
			_logger.logError("addCustomRequestHeader", params, e);
		}
	}

	public void addLocationStrategy(
		String strategyName, String functionDefinition) {

		String[] params = {strategyName, functionDefinition};

		_logger.logCommand("addLocationStrategy", params);

		try {
			_liferaySelenium.addLocationStrategy(
				strategyName, functionDefinition);
		}
		catch (Exception e) {
			_logger.logError("addLocationStrategy", params, e);
		}
	}

	public void addScript(String scriptContent, String scriptTagId) {
		String[] params = {scriptContent, scriptTagId};

		_logger.logCommand("addScript", params);

		try {
			_liferaySelenium.addScript(scriptContent, scriptTagId);
		}
		catch (Exception e) {
			_logger.logError("addScript", params, e);
		}
	}

	public void addSelection(String locator, String optionLocator) {
		String[] params = {locator, optionLocator};

		_logger.logCommand("addSelection", params);

		try {
			_liferaySelenium.addSelection(locator, optionLocator);
		}
		catch (Exception e) {
			_logger.logError("addSelection", params, e);
		}
	}

	public void allowNativeXpath(String allow) {
		String[] params = {allow};

		_logger.logCommand("allowNativeXpath", params);

		try {
			_liferaySelenium.allowNativeXpath(allow);
		}
		catch (Exception e) {
			_logger.logError("allowNativeXpath", params, e);
		}
	}

	public void altKeyDown() {
		String[] params = {};

		_logger.logCommand("altKeyDown", params);

		try {
			_liferaySelenium.altKeyDown();
		}
		catch (Exception e) {
			_logger.logError("altKeyDown", params, e);
		}
	}

	public void altKeyUp() {
		String[] params = {};

		_logger.logCommand("altKeyUp", params);

		try {
			_liferaySelenium.altKeyUp();
		}
		catch (Exception e) {
			_logger.logError("altKeyUp", params, e);
		}
	}

	public void answerOnNextPrompt(String answer) {
		String[] params = {answer};

		_logger.logCommand("answerOnNextPrompt", params);

		try {
			_liferaySelenium.answerOnNextPrompt(answer);
		}
		catch (Exception e) {
			_logger.logError("answerOnNextPrompt", params, e);
		}
	}

	public void assertAlert(String pattern) {
		String[] params = {pattern};

		_logger.logCommand("assertAlert", params);

		try {
			_liferaySelenium.assertAlert(pattern);
		}
		catch (Exception e) {
			_logger.logError("assertAlert", params, e);
		}
	}

	public void assertChecked(String pattern) {
		String[] params = {pattern};

		_logger.logCommand("assertChecked", params);

		try {
			_liferaySelenium.assertChecked(pattern);
		}
		catch (Exception e) {
			_logger.logError("assertChecked", params, e);
		}
	}

	public void assertConfirmation(String pattern) {
		String[] params = {pattern};

		_logger.logCommand("assertConfirmation", params);

		try {
			_liferaySelenium.assertConfirmation(pattern);
		}
		catch (Exception e) {
			_logger.logError("assertConfirmation", params, e);
		}
	}

	public void assertElementNotPresent(String locator) {
		String[] params = {locator};

		_logger.logCommand("assertElementNotPresent", params);

		try {
			_liferaySelenium.assertElementNotPresent(locator);
		}
		catch (Exception e) {
			_logger.logError("assertElementNotPresent", params, e);
		}
	}

	public void assertElementPresent(String locator) {
		String[] params = {locator};

		_logger.logCommand("assertElementPresent", params);

		try {
			_liferaySelenium.assertElementPresent(locator);
		}
		catch (Exception e) {
			_logger.logError("assertElementPresent", params, e);
		}
	}

	public void assertLocation(String pattern) {
		String[] params = {pattern};

		_logger.logCommand("assertLocation", params);

		try {
			_liferaySelenium.assertLocation(pattern);
		}
		catch (Exception e) {
			_logger.logError("assertLocation", params, e);
		}
	}

	public void assertNotAlert(String pattern) {
		String[] params = {pattern};

		_logger.logCommand("assertNotAlert", params);

		try {
			_liferaySelenium.assertNotAlert(pattern);
		}
		catch (Exception e) {
			_logger.logError("assertNotAlert", params, e);
		}
	}

	public void assertNotChecked(String locator) {
		String[] params = {locator};

		_logger.logCommand("assertNotChecked", params);

		try {
			_liferaySelenium.assertNotChecked(locator);
		}
		catch (Exception e) {
			_logger.logError("assertNotChecked", params, e);
		}
	}

	public void assertNotLocation(String pattern) {
		String[] params = {pattern};

		_logger.logCommand("assertNotLocation", params);

		try {
			_liferaySelenium.assertNotLocation(pattern);
		}
		catch (Exception e) {
			_logger.logError("assertNotLocation", params, e);
		}
	}

	public void assertNotPartialText(String locator, String pattern) {
		String[] params = {locator, pattern};

		_logger.logCommand("assertNotPartialText", params);

		try {
			_liferaySelenium.assertNotPartialText(locator, pattern);
		}
		catch (Exception e) {
			_logger.logError("assertNotPartialText", params, e);
		}
	}

	public void assertNotSelectedLabel(String selectLocator, String pattern) {
		String[] params = {selectLocator, pattern};

		_logger.logCommand("assertNotSelectedLabel", params);

		try {
			_liferaySelenium.assertNotSelectedLabel(selectLocator, pattern);
		}
		catch (Exception e) {
			_logger.logError("assertNotSelectedLabel", params, e);
		}
	}

	public void assertNotText(String locator, String pattern) {
		String[] params = {locator, pattern};

		_logger.logCommand("assertNotText", params);

		try {
			_liferaySelenium.assertNotText(locator, pattern);
		}
		catch (Exception e) {
			_logger.logError("assertNotText", params, e);
		}
	}

	public void assertNotValue(String locator, String pattern) {
		String[] params = {locator, pattern};

		_logger.logCommand("assertNotValue", params);

		try {
			_liferaySelenium.assertNotValue(locator, pattern);
		}
		catch (Exception e) {
			_logger.logError("assertNotValue", params, e);
		}
	}

	public void assertNotVisible(String locator) {
		String[] params = {locator};

		_logger.logCommand("assertNotVisible", params);

		try {
			_liferaySelenium.assertNotVisible(locator);
		}
		catch (Exception e) {
			_logger.logError("assertNotVisible", params, e);
		}
	}

	public void assertPartialText(String locator, String pattern) {
		String[] params = {locator, pattern};

		_logger.logCommand("assertPartialText", params);

		try {
			_liferaySelenium.assertPartialText(locator, pattern);
		}
		catch (Exception e) {
			_logger.logError("assertPartialText", params, e);
		}
	}

	public void assertSelectedLabel(String selectLocator, String pattern) {
		String[] params = {selectLocator, pattern};

		_logger.logCommand("assertSelectedLabel", params);

		try {
			_liferaySelenium.assertSelectedLabel(selectLocator, pattern);
		}
		catch (Exception e) {
			_logger.logError("assertSelectedLabel", params, e);
		}
	}

	public void assertText(String locator, String pattern) {
		String[] params = {locator, pattern};

		_logger.logCommand("assertText", params);

		try {
			_liferaySelenium.assertText(locator, pattern);
		}
		catch (Exception e) {
			_logger.logError("assertText", params, e);
		}
	}

	public void assertTextNotPresent(String pattern) {
		String[] params = {pattern};

		_logger.logCommand("assertTextNotPresent", params);

		try {
			_liferaySelenium.assertTextNotPresent(pattern);
		}
		catch (Exception e) {
			_logger.logError("assertTextNotPresent", params, e);
		}
	}

	public void assertTextPresent(String pattern) {
		String[] params = {pattern};

		_logger.logCommand("assertTextPresent", params);

		try {
			_liferaySelenium.assertTextPresent(pattern);
		}
		catch (Exception e) {
			_logger.logError("assertTextPresent", params, e);
		}
	}

	public void assertValue(String locator, String pattern) {
		String[] params = {locator, pattern};

		_logger.logCommand("assertValue", params);

		try {
			_liferaySelenium.assertValue(locator, pattern);
		}
		catch (Exception e) {
			_logger.logError("assertValue", params, e);
		}
	}

	public void assertVisible(String locator) {
		String[] params = {locator};

		_logger.logCommand("assertVisible", params);

		try {
			_liferaySelenium.assertVisible(locator);
		}
		catch (Exception e) {
			_logger.logError("assertVisible", params, e);
		}
	}

	public void assignId(String locator, String identifier) {
		String[] params = {locator, identifier};

		_logger.logCommand("assignId", params);

		try {
			_liferaySelenium.assignId(locator, identifier);
		}
		catch (Exception e) {
			_logger.logError("assignId", params, e);
		}
	}

	public void attachFile(String fieldLocator, String fileLocator) {
		String[] params = {fieldLocator, fileLocator};

		_logger.logCommand("attachFile", params);

		try {
			_liferaySelenium.attachFile(fieldLocator, fileLocator);
		}
		catch (Exception e) {
			_logger.logError("attachFile", params, e);
		}
	}

	public void captureEntirePageScreenshot(String fileName, String kwargs) {
		String[] params = {fileName, kwargs};

		_logger.logCommand("captureEntirePageScreenshot", params);

		try {
			_liferaySelenium.captureEntirePageScreenshot(fileName, kwargs);
		}
		catch (Exception e) {
			_logger.logError("captureEntirePageScreenshot", params, e);
		}
	}

	public String captureEntirePageScreenshotToString(String kwargs) {
		String[] params = {kwargs};

		_logger.logCommand("captureEntirePageScreenshotToString", params);

		try {
			return _liferaySelenium.captureEntirePageScreenshotToString(kwargs);
		}
		catch (Exception e) {
			_logger.logError("captureEntirePageScreenshotToString", params, e);

			return null;
		}
	}

	public String captureNetworkTraffic(String type) {
		String[] params = {type};

		_logger.logCommand("captureNetworkTraffic", params);

		try {
			return _liferaySelenium.captureNetworkTraffic(type);
		}
		catch (Exception e) {
			_logger.logError("captureNetworkTraffic", params, e);

			return null;
		}
	}

	public void captureScreenshot(String fileName) {
		String[] params = {fileName};

		_logger.logCommand("captureScreenshot", params);

		try {
			_liferaySelenium.captureScreenshot(fileName);
		}
		catch (Exception e) {
			_logger.logError("captureScreenshot", params, e);
		}
	}

	public String captureScreenshotToString() {
		String[] params = {};

		_logger.logCommand("captureScreenshotToString", params);

		try {
			return _liferaySelenium.captureScreenshotToString();
		}
		catch (Exception e) {
			_logger.logError("captureScreenshotToString", params, e);

			return null;
		}
	}

	public void check(String locator) {
		String[] params = {locator};

		_logger.logCommand("check", params);

		try {
			_liferaySelenium.check(locator);
		}
		catch (Exception e) {
			_logger.logError("check", params, e);
		}
	}

	public void chooseCancelOnNextConfirmation() {
		String[] params = {};

		_logger.logCommand("chooseCancelOnNextConfirmation", params);

		try {
			_liferaySelenium.chooseCancelOnNextConfirmation();
		}
		catch (Exception e) {
			_logger.logError("chooseCancelOnNextConfirmation", params, e);
		}
	}

	public void chooseOkOnNextConfirmation() {
		String[] params = {};

		_logger.logCommand("chooseOkOnNextConfirmation", params);

		try {
			_liferaySelenium.chooseOkOnNextConfirmation();
		}
		catch (Exception e) {
			_logger.logError("chooseOkOnNextConfirmation", params, e);
		}
	}

	public void click(String locator) {
		String[] params = {locator};

		_logger.logCommand("click", params);

		try {
			_liferaySelenium.click(locator);
		}
		catch (Exception e) {
			_logger.logError("click", params, e);
		}
	}

	public void clickAndWait(String locator) {
		String[] params = {locator};

		_logger.logCommand("clickAndWait", params);

		try {
			_liferaySelenium.clickAndWait(locator);
		}
		catch (Exception e) {
			_logger.logError("clickAndWait", params, e);
		}
	}

	public void clickAt(String locator, String coordString) {
		String[] params = {locator, coordString};

		_logger.logCommand("clickAt", params);

		try {
			_liferaySelenium.clickAt(locator, coordString);
		}
		catch (Exception e) {
			_logger.logError("clickAt", params, e);
		}
	}

	public void clickAtAndWait(String locator, String coordString) {
		String[] params = {locator, coordString};

		_logger.logCommand("clickAtAndWait", params);

		try {
			_liferaySelenium.clickAtAndWait(locator, coordString);
		}
		catch (Exception e) {
			_logger.logError("clickAtAndWait", params, e);
		}
	}

	public void close() {
		String[] params = {};

		_logger.logCommand("close", params);

		try {
			_liferaySelenium.close();
		}
		catch (Exception e) {
			_logger.logError("close", params, e);
		}
	}

	public void contextMenu(String locator) {
		String[] params = {locator};

		_logger.logCommand("contextMenu", params);

		try {
			_liferaySelenium.contextMenu(locator);
		}
		catch (Exception e) {
			_logger.logError("contextMenu", params, e);
		}
	}

	public void contextMenuAt(String locator, String coordString) {
		String[] params = {locator, coordString};

		_logger.logCommand("contextMenuAt", params);

		try {
			_liferaySelenium.contextMenuAt(locator, coordString);
		}
		catch (Exception e) {
			_logger.logError("contextMenuAt", params, e);
		}
	}

	public void controlKeyDown() {
		String[] params = {};

		_logger.logCommand("controlKeyDown", params);

		try {
			_liferaySelenium.controlKeyDown();
		}
		catch (Exception e) {
			_logger.logError("controlKeyDown", params, e);
		}
	}

	public void controlKeyUp() {
		String[] params = {};

		_logger.logCommand("controlKeyUp", params);

		try {
			_liferaySelenium.controlKeyUp();
		}
		catch (Exception e) {
			_logger.logError("controlKeyUp", params, e);
		}
	}

	public void copyText(String locator) {
		String[] params = {locator};

		_logger.logCommand("copyText", params);

		try {
			_liferaySelenium.copyText(locator);
		}
		catch (Exception e) {
			_logger.logError("copyText", params, e);
		}
	}

	public void copyValue(String locator) {
		String[] params = {locator};

		_logger.logCommand("copyValue", params);

		try {
			_liferaySelenium.copyValue(locator);
		}
		catch (Exception e) {
			_logger.logError("copyValue", params, e);
		}
	}

	public void createCookie(String nameValuePair, String optionsString) {
		String[] params = {nameValuePair, optionsString};

		_logger.logCommand("createCookie", params);

		try {
			_liferaySelenium.createCookie(nameValuePair, optionsString);
		}
		catch (Exception e) {
			_logger.logError("createCookie", params, e);
		}
	}

	public void deleteAllVisibleCookies() {
		String[] params = {};

		_logger.logCommand("deleteAllVisibleCookies", params);

		try {
			_liferaySelenium.deleteAllVisibleCookies();
		}
		catch (Exception e) {
			_logger.logError("deleteAllVisibleCookies", params, e);
		}
	}

	public void deleteCookie(String name, String optionsString) {
		String[] params = {name, optionsString};

		_logger.logCommand("deleteCookie", params);

		try {
			_liferaySelenium.deleteCookie(name, optionsString);
		}
		catch (Exception e) {
			_logger.logError("deleteCookie", params, e);
		}
	}

	public void deselectPopUp() {
		String[] params = {};

		_logger.logCommand("deselectPopUp", params);

		try {
			_liferaySelenium.deselectPopUp();
		}
		catch (Exception e) {
			_logger.logError("deselectPopUp", params, e);
		}
	}

	public void doubleClick(String locator) {
		String[] params = {locator};

		_logger.logCommand("doubleClick", params);

		try {
			_liferaySelenium.doubleClick(locator);
		}
		catch (Exception e) {
			_logger.logError("doubleClick", params, e);
		}
	}

	public void doubleClickAt(String locator, String coordString) {
		String[] params = {locator, coordString};

		_logger.logCommand("doubleClickAt", params);

		try {
			_liferaySelenium.doubleClickAt(locator, coordString);
		}
		catch (Exception e) {
			_logger.logError("doubleClickAt", params, e);
		}
	}

	public void dragAndDrop(String locator, String movementsString) {
		String[] params = {locator, movementsString};

		_logger.logCommand("dragAndDrop", params);

		try {
			_liferaySelenium.dragAndDrop(locator, movementsString);
		}
		catch (Exception e) {
			_logger.logError("dragAndDrop", params, e);
		}
	}

	public void dragAndDropToObject(
		String locatorOfObjectToBeDragged,
		String locatorOfDragDestinationObject) {

		String[] params = {
			locatorOfObjectToBeDragged, locatorOfDragDestinationObject};

		_logger.logCommand("dragAndDropToObject", params);

		try {
			_liferaySelenium.dragAndDropToObject(
				locatorOfObjectToBeDragged, locatorOfDragDestinationObject);
		}
		catch (Exception e) {
			_logger.logError("dragAndDropToObject", params, e);
		}
	}

	public void dragdrop(String locator, String movementsString) {
		String[] params = {locator, movementsString};

		_logger.logCommand("dragdrop", params);

		try {
			_liferaySelenium.dragdrop(locator, movementsString);
		}
		catch (Exception e) {
			_logger.logError("dragdrop", params, e);
		}
	}

	public void echo(String message) {
		String[] params = {message};

		_logger.logCommand("echo", params);

		try {
			_liferaySelenium.echo(message);
		}
		catch (Exception e) {
			_logger.logError("echo", params, e);
		}
	}

	public void fail(String message) {
		String[] params = {message};

		_logger.logCommand("fail", params);

		try {
			_liferaySelenium.fail(message);
		}
		catch (Exception e) {
			_logger.logError("fail", params, e);
		}
	}

	public void fireEvent(String locator, String eventName) {
		String[] params = {locator, eventName};

		_logger.logCommand("fireEvent", params);

		try {
			_liferaySelenium.fireEvent(locator, eventName);
		}
		catch (Exception e) {
			_logger.logError("fireEvent", params, e);
		}
	}

	public void focus(String locator) {
		String[] params = {locator};

		_logger.logCommand("focus", params);

		try {
			_liferaySelenium.focus(locator);
		}
		catch (Exception e) {
			_logger.logError("focus", params, e);
		}
	}

	public String getAlert() {
		String[] params = {};

		_logger.logCommand("getAlert", params);

		try {
			return _liferaySelenium.getAlert();
		}
		catch (Exception e) {
			_logger.logError("getAlert", params, e);

			return null;
		}
	}

	public String[] getAllButtons() {
		String[] params = {};

		_logger.logCommand("getAllButtons", params);

		try {
			return _liferaySelenium.getAllButtons();
		}
		catch (Exception e) {
			_logger.logError("getAllButtons", params, e);

			return null;
		}
	}

	public String[] getAllFields() {
		String[] params = {};

		_logger.logCommand("getAllFields", params);

		try {
			return _liferaySelenium.getAllFields();
		}
		catch (Exception e) {
			_logger.logError("getAllFields", params, e);

			return null;
		}
	}

	public String[] getAllLinks() {
		String[] params = {};

		_logger.logCommand("getAllLinks", params);

		try {
			return _liferaySelenium.getAllLinks();
		}
		catch (Exception e) {
			_logger.logError("getAllLinks", params, e);

			return null;
		}
	}

	public String[] getAllWindowIds() {
		String[] params = {};

		_logger.logCommand("getAllWindowIds", params);

		try {
			return _liferaySelenium.getAllWindowIds();
		}
		catch (Exception e) {
			_logger.logError("getAllWindowIds", params, e);

			return null;
		}
	}

	public String[] getAllWindowNames() {
		String[] params = {};

		_logger.logCommand("getAllWindowNames", params);

		try {
			return _liferaySelenium.getAllWindowNames();
		}
		catch (Exception e) {
			_logger.logError("getAllWindowNames", params, e);

			return null;
		}
	}

	public String[] getAllWindowTitles() {
		String[] params = {};

		_logger.logCommand("getAllWindowTitles", params);

		try {
			return _liferaySelenium.getAllWindowTitles();
		}
		catch (Exception e) {
			_logger.logError("getAllWindowTitles", params, e);

			return null;
		}
	}

	public String getAttribute(String attributeLocator) {
		String[] params = {attributeLocator};

		_logger.logCommand("getAttribute", params);

		try {
			return _liferaySelenium.getAttribute(attributeLocator);
		}
		catch (Exception e) {
			_logger.logError("getAttribute", params, e);

			return null;
		}
	}

	public String[] getAttributeFromAllWindows(String attributeName) {
		String[] params = {attributeName};

		_logger.logCommand("getAttributeFromAllWindows", params);

		try {
			return _liferaySelenium.getAttributeFromAllWindows(attributeName);
		}
		catch (Exception e) {
			_logger.logError("getAttributeFromAllWindows", params, e);

			return null;
		}
	}

	public String getBodyText() {
		String[] params = {};

		_logger.logCommand("getBodyText", params);

		try {
			return _liferaySelenium.getBodyText();
		}
		catch (Exception e) {
			_logger.logError("getBodyText", params, e);

			return null;
		}
	}

	public String getConfirmation() {
		String[] params = {};

		_logger.logCommand("getConfirmation", params);

		try {
			return _liferaySelenium.getConfirmation();
		}
		catch (Exception e) {
			_logger.logError("getConfirmation", params, e);

			return null;
		}
	}

	public String getCookie() {
		String[] params = {};

		_logger.logCommand("getCookie", params);

		try {
			return _liferaySelenium.getCookie();
		}
		catch (Exception e) {
			_logger.logError("getCookie", params, e);

			return null;
		}
	}

	public String getCookieByName(String name) {
		String[] params = {name};

		_logger.logCommand("getCookieByName", params);

		try {
			return _liferaySelenium.getCookieByName(name);
		}
		catch (Exception e) {
			_logger.logError("getCookieByName", params, e);

			return null;
		}
	}

	public Number getCssCount(String css) {
		String[] params = {css};

		_logger.logCommand("getCssCount", params);

		try {
			return _liferaySelenium.getCssCount(css);
		}
		catch (Exception e) {
			_logger.logError("getCssCount", params, e);

			return null;
		}
	}

	public String getCurrentDay() {
		String[] params = {};

		_logger.logCommand("getCurrentDay", params);

		try {
			return _liferaySelenium.getCurrentDay();
		}
		catch (Exception e) {
			_logger.logError("getCurrentDay", params, e);

			return null;
		}
	}

	public String getCurrentMonth() {
		String[] params = {};

		_logger.logCommand("getCurrentMonth", params);

		try {
			return _liferaySelenium.getCurrentMonth();
		}
		catch (Exception e) {
			_logger.logError("getCurrentMonth", params, e);

			return null;
		}
	}

	public String getCurrentYear() {
		String[] params = {};

		_logger.logCommand("getCurrentYear", params);

		try {
			return _liferaySelenium.getCurrentYear();
		}
		catch (Exception e) {
			_logger.logError("getCurrentYear", params, e);

			return null;
		}
	}

	public Number getCursorPosition(String locator) {
		String[] params = {locator};

		_logger.logCommand("getCursorPosition", params);

		try {
			return _liferaySelenium.getCursorPosition(locator);
		}
		catch (Exception e) {
			_logger.logError("getCursorPosition", params, e);

			return null;
		}
	}

	public Number getElementHeight(String locator) {
		String[] params = {locator};

		_logger.logCommand("getElementHeight", params);

		try {
			return _liferaySelenium.getElementHeight(locator);
		}
		catch (Exception e) {
			_logger.logError("getElementHeight", params, e);

			return null;
		}
	}

	public Number getElementIndex(String locator) {
		String[] params = {locator};

		_logger.logCommand("getElementIndex", params);

		try {
			return _liferaySelenium.getElementIndex(locator);
		}
		catch (Exception e) {
			_logger.logError("getElementIndex", params, e);

			return null;
		}
	}

	public Number getElementPositionLeft(String locator) {
		String[] params = {locator};

		_logger.logCommand("getElementPositionLeft", params);

		try {
			return _liferaySelenium.getElementPositionLeft(locator);
		}
		catch (Exception e) {
			_logger.logError("getElementPositionLeft", params, e);

			return null;
		}
	}

	public Number getElementPositionTop(String locator) {
		String[] params = {locator};

		_logger.logCommand("getElementPositionTop", params);

		try {
			return _liferaySelenium.getElementPositionTop(locator);
		}
		catch (Exception e) {
			_logger.logError("getElementPositionTop", params, e);

			return null;
		}
	}

	public Number getElementWidth(String locator) {
		String[] params = {locator};

		_logger.logCommand("getElementWidth", params);

		try {
			return _liferaySelenium.getElementWidth(locator);
		}
		catch (Exception e) {
			_logger.logError("getElementWidth", params, e);

			return null;
		}
	}

	public String getEval(String script) {
		String[] params = {script};

		_logger.logCommand("getEval", params);

		try {
			return _liferaySelenium.getEval(script);
		}
		catch (Exception e) {
			_logger.logError("getEval", params, e);

			return null;
		}
	}

	public String getExpression(String expression) {
		String[] params = {expression};

		_logger.logCommand("getExpression", params);

		try {
			return _liferaySelenium.getExpression(expression);
		}
		catch (Exception e) {
			_logger.logError("getExpression", params, e);

			return null;
		}
	}

	public String getFirstNumber(String locator) {
		String[] params = {locator};

		_logger.logCommand("getFirstNumber", params);

		try {
			return _liferaySelenium.getFirstNumber(locator);
		}
		catch (Exception e) {
			_logger.logError("getFirstNumber", params, e);

			return null;
		}
	}

	public String getFirstNumberIncrement(String locator) {
		String[] params = {locator};

		_logger.logCommand("getFirstNumberIncrement", params);

		try {
			return _liferaySelenium.getFirstNumberIncrement(locator);
		}
		catch (Exception e) {
			_logger.logError("getFirstNumberIncrement", params, e);

			return null;
		}
	}

	public String getHtmlSource() {
		String[] params = {};

		_logger.logCommand("getHtmlSource", params);

		try {
			return _liferaySelenium.getHtmlSource();
		}
		catch (Exception e) {
			_logger.logError("getHtmlSource", params, e);

			return null;
		}
	}

	public String getLocation() {
		String[] params = {};

		_logger.logCommand("getLocation", params);

		try {
			return _liferaySelenium.getLocation();
		}
		catch (Exception e) {
			_logger.logError("getLocation", params, e);

			return null;
		}
	}

	public String getLog() {
		String[] params = {};

		_logger.logCommand("getLog", params);

		try {
			return _liferaySelenium.getLog();
		}
		catch (Exception e) {
			_logger.logError("getLog", params, e);

			return null;
		}
	}

	public Number getMouseSpeed() {
		String[] params = {};

		_logger.logCommand("getMouseSpeed", params);

		try {
			return _liferaySelenium.getMouseSpeed();
		}
		catch (Exception e) {
			_logger.logError("getMouseSpeed", params, e);

			return null;
		}
	}

	public String getNumberDecrement(String value) {
		String[] params = {value};

		_logger.logCommand("getNumberDecrement", params);

		try {
			return _liferaySelenium.getNumberDecrement(value);
		}
		catch (Exception e) {
			_logger.logError("getNumberDecrement", params, e);

			return null;
		}
	}

	public String getNumberIncrement(String value) {
		String[] params = {value};

		_logger.logCommand("getNumberIncrement", params);

		try {
			return _liferaySelenium.getNumberIncrement(value);
		}
		catch (Exception e) {
			_logger.logError("getNumberIncrement", params, e);

			return null;
		}
	}

	public String getPrompt() {
		String[] params = {};

		_logger.logCommand("getPrompt", params);

		try {
			return _liferaySelenium.getPrompt();
		}
		catch (Exception e) {
			_logger.logError("getPrompt", params, e);

			return null;
		}
	}

	public String getSelectedId(String selectLocator) {
		String[] params = {selectLocator};

		_logger.logCommand("getSelectedId", params);

		try {
			return _liferaySelenium.getSelectedId(selectLocator);
		}
		catch (Exception e) {
			_logger.logError("getSelectedId", params, e);

			return null;
		}
	}

	public String[] getSelectedIds(String selectLocator) {
		String[] params = {selectLocator};

		_logger.logCommand("getSelectedIds", params);

		try {
			return _liferaySelenium.getSelectedIds(selectLocator);
		}
		catch (Exception e) {
			_logger.logError("getSelectedIds", params, e);

			return null;
		}
	}

	public String getSelectedIndex(String selectLocator) {
		String[] params = {selectLocator};

		_logger.logCommand("getSelectedIndex", params);

		try {
			return _liferaySelenium.getSelectedIndex(selectLocator);
		}
		catch (Exception e) {
			_logger.logError("getSelectedIndex", params, e);

			return null;
		}
	}

	public String[] getSelectedIndexes(String selectLocator) {
		String[] params = {selectLocator};

		_logger.logCommand("getSelectedIndexes", params);

		try {
			return _liferaySelenium.getSelectedIndexes(selectLocator);
		}
		catch (Exception e) {
			_logger.logError("getSelectedIndexes", params, e);

			return null;
		}
	}

	public String getSelectedLabel(String selectLocator) {
		String[] params = {selectLocator};

		_logger.logCommand("getSelectedLabel", params);

		try {
			return _liferaySelenium.getSelectedLabel(selectLocator);
		}
		catch (Exception e) {
			_logger.logError("getSelectedLabel", params, e);

			return null;
		}
	}

	public String[] getSelectedLabels(String selectLocator) {
		String[] params = {selectLocator};

		_logger.logCommand("getSelectedLabels", params);

		try {
			return _liferaySelenium.getSelectedLabels(selectLocator);
		}
		catch (Exception e) {
			_logger.logError("getSelectedLabels", params, e);

			return null;
		}
	}

	public String getSelectedValue(String selectLocator) {
		String[] params = {selectLocator};

		_logger.logCommand("getSelectedValue", params);

		try {
			return _liferaySelenium.getSelectedValue(selectLocator);
		}
		catch (Exception e) {
			_logger.logError("getSelectedValue", params, e);

			return null;
		}
	}

	public String[] getSelectedValues(String selectLocator) {
		String[] params = {selectLocator};

		_logger.logCommand("getSelectedValues", params);

		try {
			return _liferaySelenium.getSelectedValues(selectLocator);
		}
		catch (Exception e) {
			_logger.logError("getSelectedValues", params, e);

			return null;
		}
	}

	public String[] getSelectOptions(String selectLocator) {
		String[] params = {selectLocator};

		_logger.logCommand("getSelectOptions", params);

		try {
			return _liferaySelenium.getSelectOptions(selectLocator);
		}
		catch (Exception e) {
			_logger.logError("getSelectOptions", params, e);

			return null;
		}
	}

	public String getSpeed() {
		String[] params = {};

		_logger.logCommand("getSpeed", params);

		try {
			return _liferaySelenium.getSpeed();
		}
		catch (Exception e) {
			_logger.logError("getSpeed", params, e);

			return null;
		}
	}

	public String getTable(String tableCellAddress) {
		String[] params = {tableCellAddress};

		_logger.logCommand("getTable", params);

		try {
			return _liferaySelenium.getTable(tableCellAddress);
		}
		catch (Exception e) {
			_logger.logError("getTable", params, e);

			return null;
		}
	}

	public String getText(String locator) {
		String[] params = {locator};

		_logger.logCommand("getText", params);

		try {
			return _liferaySelenium.getText(locator);
		}
		catch (Exception e) {
			_logger.logError("getText", params, e);

			return null;
		}
	}

	public String getTitle() {
		String[] params = {};

		_logger.logCommand("getTitle", params);

		try {
			return _liferaySelenium.getTitle();
		}
		catch (Exception e) {
			_logger.logError("getTitle", params, e);

			return null;
		}
	}

	public String getValue(String locator) {
		String[] params = {locator};

		_logger.logCommand("getValue", params);

		try {
			return _liferaySelenium.getValue(locator);
		}
		catch (Exception e) {
			_logger.logError("getValue", params, e);

			return null;
		}
	}

	public boolean getWhetherThisFrameMatchFrameExpression(
		String currentFrameString, String target) {

		String[] params = {currentFrameString, target};

		_logger.logCommand("getWhetherThisFrameMatchFrameExpression", params);

		try {
			return _liferaySelenium.getWhetherThisFrameMatchFrameExpression(
				currentFrameString, target);
		}
		catch (Exception e) {
			_logger.logError(
				"getWhetherThisFrameMatchFrameExpression", params, e);
			return false;
		}
	}

	public boolean getWhetherThisWindowMatchWindowExpression(
		String currentFrameString, String target) {

		String[] params = {currentFrameString, target};

		_logger.logCommand("getWhetherThisWindowMatchFrameExpression", params);

		try {
			return _liferaySelenium.getWhetherThisWindowMatchWindowExpression(
				currentFrameString, target);
		}
		catch (Exception e) {
			_logger.logError(
				"getWhetherThisWindowMatchFrameExpression", params, e);
			return false;
		}
	}

	public Number getXpathCount(String xpath) {
		String[] params = {xpath};

		_logger.logCommand("getXpathCount", params);

		try {
			return _liferaySelenium.getXpathCount(xpath);
		}
		catch (Exception e) {
			_logger.logError("getXpathCount", params, e);

			return null;
		}
	}

	public void goBack() {
		String[] params = {};

		_logger.logCommand("goBack", params);

		try {
			_liferaySelenium.goBack();
		}
		catch (Exception e) {
			_logger.logError("goBack", params, e);
		}
	}

	public void goBackAndWait() {
		String[] params = {};

		_logger.logCommand("goBackAndWait", params);

		try {
			_liferaySelenium.goBackAndWait();
		}
		catch (Exception e) {
			_logger.logError("goBackAndWait", params, e);
		}
	}

	public void highlight(String locator) {
		String[] params = {locator};

		_logger.logCommand("highlight", params);

		try {
			_liferaySelenium.highlight(locator);
		}
		catch (Exception e) {
			_logger.logError("highlight", params, e);
		}
	}

	public void ignoreAttributesWithoutValue(String ignore) {
		String[] params = {ignore};

		_logger.logCommand("ignoreAttributesWithoutValue", params);

		try {
			_liferaySelenium.ignoreAttributesWithoutValue(ignore);
		}
		catch (Exception e) {
			_logger.logError("ignoreAttributesWithoutValue", params, e);
		}
	}

	public boolean isAlertPresent() {
		String[] params = {};

		_logger.logCommand("isAlertPresent", params);

		try {
			return _liferaySelenium.isAlertPresent();
		}
		catch (Exception e) {
			_logger.logError("isAlertPresent", params, e);
			return false;
		}
	}

	public boolean isChecked(String locator) {
		String[] params = {locator};

		_logger.logCommand("isChecked", params);

		try {
			return _liferaySelenium.isChecked(locator);
		}
		catch (Exception e) {
			_logger.logError("isChecked", params, e);
			return false;
		}
	}

	public boolean isConfirmation(String pattern) {
		String[] params = {pattern};

		_logger.logCommand("isConfirmation", params);

		try {
			return _liferaySelenium.isConfirmation(pattern);
		}
		catch (Exception e) {
			_logger.logError("isConfirmation", params, e);
			return false;
		}
	}

	public boolean isConfirmationPresent() {
		String[] params = {};

		_logger.logCommand("isConfirmationPresent", params);

		try {
			return _liferaySelenium.isConfirmationPresent();
		}
		catch (Exception e) {
			_logger.logError("isConfirmationPresent", params, e);
			return false;
		}
	}

	public boolean isCookiePresent(String name) {
		String[] params = {name};

		_logger.logCommand("isCookiePresent", params);

		try {
			return _liferaySelenium.isCookiePresent(name);
		}
		catch (Exception e) {
			_logger.logError("isCookiePresent", params, e);
			return false;
		}
	}

	public boolean isEditable(String locator) {
		String[] params = {locator};

		_logger.logCommand("isEditable", params);

		try {
			return _liferaySelenium.isEditable(locator);
		}
		catch (Exception e) {
			_logger.logError("isEditable", params, e);
			return false;
		}
	}

	public boolean isElementNotPresent(String locator) {
		String[] params = {locator};

		_logger.logCommand("isElementNotPresent", params);

		try {
			return _liferaySelenium.isElementNotPresent(locator);
		}
		catch (Exception e) {
			_logger.logError("isElementNotPresent", params, e);
			return false;
		}
	}

	public boolean isElementPresent(String locator) {
		String[] params = {locator};

		_logger.logCommand("isElementPresent", params);

		try {
			return _liferaySelenium.isElementPresent(locator);
		}
		catch (Exception e) {
			_logger.logError("isElementPresent", params, e);
			return false;
		}
	}

	public boolean isNotChecked(String locator) {
		String[] params = {locator};

		_logger.logCommand("isNotChecked", params);

		try {
			return _liferaySelenium.isNotChecked(locator);
		}
		catch (Exception e) {
			_logger.logError("isNotChecked", params, e);
			return false;
		}
	}

	public boolean isNotPartialText(String locator, String value) {
		String[] params = {locator, value};

		_logger.logCommand("isNotPartialText", params);

		try {
			return _liferaySelenium.isNotPartialText(locator, value);
		}
		catch (Exception e) {
			_logger.logError("isNotPartialText", params, e);
			return false;
		}
	}

	public boolean isNotSelectedLabel(String selectLocator, String pattern) {
		String[] params = {selectLocator, pattern};

		_logger.logCommand("isNotSelectedLabel", params);

		try {
			return _liferaySelenium.isNotSelectedLabel(selectLocator, pattern);
		}
		catch (Exception e) {
			_logger.logError("isNotSelectedLabel", params, e);
			return false;
		}
	}

	public boolean isNotText(String locator, String value) {
		String[] params = {locator, value};

		_logger.logCommand("isNotText", params);

		try {
			return _liferaySelenium.isNotText(locator, value);
		}
		catch (Exception e) {
			_logger.logError("isNotText", params, e);
			return false;
		}
	}

	public boolean isNotValue(String locator, String value) {
		String[] params = {locator, value};

		_logger.logCommand("isNotValue", params);

		try {
			return _liferaySelenium.isNotValue(locator, value);
		}
		catch (Exception e) {
			_logger.logError("isNotValue", params, e);
			return false;
		}
	}

	public boolean isNotVisible(String locator) {
		String[] params = {locator};

		_logger.logCommand("isNotVisible", params);

		try {
			return _liferaySelenium.isNotVisible(locator);
		}
		catch (Exception e) {
			_logger.logError("isNotVisible", params, e);
			return false;
		}
	}

	public boolean isOrdered(String locator1, String locator2) {
		String[] params = {locator1, locator2};

		_logger.logCommand("isOrdered", params);

		try {
			return _liferaySelenium.isOrdered(locator1, locator2);
		}
		catch (Exception e) {
			_logger.logError("isOrdered", params, e);
			return false;
		}
	}

	public boolean isPartialText(String locator, String value) {
		String[] params = {locator, value};

		_logger.logCommand("isPartialText", params);

		try {
			return _liferaySelenium.isPartialText(locator, value);
		}
		catch (Exception e) {
			_logger.logError("isPartialText", params, e);
			return false;
		}
	}

	public boolean isPromptPresent() {
		String[] params = {};

		_logger.logCommand("isPromptPresent", params);

		try {
			return _liferaySelenium.isPromptPresent();
		}
		catch (Exception e) {
			_logger.logError("isPromptPresent", params, e);
			return false;
		}
	}

	public boolean isSelectedLabel(String selectLocator, String pattern) {
		String[] params = {selectLocator, pattern};

		_logger.logCommand("isSelectedLabel", params);

		try {
			return _liferaySelenium.isSelectedLabel(selectLocator, pattern);
		}
		catch (Exception e) {
			_logger.logError("isSelectedLabel", params, e);
			return false;
		}
	}

	public boolean isSomethingSelected(String selectLocator) {
		String[] params = {selectLocator};

		_logger.logCommand("isSomethingSelected", params);

		try {
			return _liferaySelenium.isSomethingSelected(selectLocator);
		}
		catch (Exception e) {
			_logger.logError("isSomethingSelected", params, e);
			return false;
		}
	}

	public boolean isText(String locator, String value) {
		String[] params = {locator, value};

		_logger.logCommand("isText", params);

		try {
			return _liferaySelenium.isText(locator, value);
		}
		catch (Exception e) {
			_logger.logError("isText", params, e);
			return false;
		}
	}

	public boolean isTextNotPresent(String pattern) {
		String[] params = {pattern};

		_logger.logCommand("isTextNotPresent", params);

		try {
			return _liferaySelenium.isTextNotPresent(pattern);
		}
		catch (Exception e) {
			_logger.logError("isTextNotPresent", params, e);
			return false;
		}
	}

	public boolean isTextPresent(String pattern) {
		String[] params = {pattern};

		_logger.logCommand("isTextPresent", params);

		try {
			return _liferaySelenium.isTextPresent(pattern);
		}
		catch (Exception e) {
			_logger.logError("isTextPresent", params, e);
			return false;
		}
	}

	public boolean isValue(String locator, String value) {
		String[] params = {locator, value};

		_logger.logCommand("isValue", params);

		try {
			return _liferaySelenium.isValue(locator, value);
		}
		catch (Exception e) {
			_logger.logError("isValue", params, e);
			return false;
		}
	}

	public boolean isVisible(String locator) {
		String[] params = {locator};

		_logger.logCommand("isVisible", params);

		try {
			return _liferaySelenium.isVisible(locator);
		}
		catch (Exception e) {
			_logger.logError("isVisible", params, e);
			return false;
		}
	}

	public void keyDown(String locator, String keySequence) {
		String[] params = {locator, keySequence};

		_logger.logCommand("keyDown", params);

		try {
			_liferaySelenium.keyDown(locator, keySequence);
		}
		catch (Exception e) {
			_logger.logError("keyDown", params, e);
		}
	}

	public void keyDownAndWait(String locator, String keySequence) {
		String[] params = {locator, keySequence};

		_logger.logCommand("keyDownAndWait", params);

		try {
			_liferaySelenium.keyDownAndWait(locator, keySequence);
		}
		catch (Exception e) {
			_logger.logError("keyDownAndWait", params, e);
		}
	}

	public void keyDownNative(String keycode) {
		String[] params = {keycode};

		_logger.logCommand("keyDownNative", params);

		try {
			_liferaySelenium.keyDownNative(keycode);
		}
		catch (Exception e) {
			_logger.logError("keyDownNative", params, e);
		}
	}

	public void keyPress(String locator, String keySequence) {
		String[] params = {locator, keySequence};

		_logger.logCommand("keyPress", params);

		try {
			_liferaySelenium.keyPress(locator, keySequence);
		}
		catch (Exception e) {
			_logger.logError("keyPress", params, e);
		}
	}

	public void keyPressAndWait(String locator, String keySequence) {
		String[] params = {locator, keySequence};

		_logger.logCommand("keyPressAndWait", params);

		try {
			_liferaySelenium.keyPressAndWait(locator, keySequence);
		}
		catch (Exception e) {
			_logger.logError("keyPressAndWait", params, e);
		}
	}

	public void keyPressNative(String keycode) {
		String[] params = {keycode};

		_logger.logCommand("keyPressNative", params);

		try {
			_liferaySelenium.keyPressNative(keycode);
		}
		catch (Exception e) {
			_logger.logError("keyPressNative", params, e);
		}
	}

	public void keyUp(String locator, String keySequence) {
		String[] params = {locator, keySequence};

		_logger.logCommand("keyUp", params);

		try {
			_liferaySelenium.keyUp(locator, keySequence);
		}
		catch (Exception e) {
			_logger.logError("keyUp", params, e);
		}
	}

	public void keyUpAndWait(String locator, String keySequence) {
		String[] params = {locator, keySequence};

		_logger.logCommand("keyUpAndWait", params);

		try {
			_liferaySelenium.keyUpAndWait(locator, keySequence);
		}
		catch (Exception e) {
			_logger.logError("keyUpAndWait", params, e);
		}
	}

	public void keyUpNative(String keycode) {
		String[] params = {keycode};

		_logger.logCommand("keyUpNative", params);

		try {
			_liferaySelenium.keyUpNative(keycode);
		}
		catch (Exception e) {
			_logger.logError("keyUpNative", params, e);
		}
	}

	public void makeVisible(String locator) {
		String[] params = {locator};

		_logger.logCommand("makeVisible", params);

		try {
			_liferaySelenium.makeVisible(locator);
		}
		catch (Exception e) {
			_logger.logError("makeVisible", params, e);
		}
	}

	public void metaKeyDown() {
		String[] params = {};

		_logger.logCommand("metaKeyDown", params);

		try {
			_liferaySelenium.metaKeyDown();
		}
		catch (Exception e) {
			_logger.logError("metaKeyDown", params, e);
		}
	}

	public void metaKeyUp() {
		String[] params = {};

		_logger.logCommand("metaKeyUp", params);

		try {
			_liferaySelenium.metaKeyUp();
		}
		catch (Exception e) {
			_logger.logError("metaKeyUp", params, e);
		}
	}

	public void mouseDown(String locator) {
		String[] params = {locator};

		_logger.logCommand("mouseDown", params);

		try {
			_liferaySelenium.mouseDown(locator);
		}
		catch (Exception e) {
			_logger.logError("mouseDown", params, e);
		}
	}

	public void mouseDownAt(String locator, String coordString) {
		String[] params = {locator, coordString};

		_logger.logCommand("mouseDownAt", params);

		try {
			_liferaySelenium.mouseDownAt(locator, coordString);
		}
		catch (Exception e) {
			_logger.logError("mouseDownAt", params, e);
		}
	}

	public void mouseDownRight(String locator) {
		String[] params = {locator};

		_logger.logCommand("mouseDownRight", params);

		try {
			_liferaySelenium.mouseDownRight(locator);
		}
		catch (Exception e) {
			_logger.logError("mouseDownRight", params, e);
		}
	}

	public void mouseDownRightAt(String locator, String coordString) {
		String[] params = {locator, coordString};

		_logger.logCommand("mouseDownRightAt", params);

		try {
			_liferaySelenium.mouseDownRightAt(locator, coordString);
		}
		catch (Exception e) {
			_logger.logError("mouseDownRightAt", params, e);
		}
	}

	public void mouseMove(String locator) {
		String[] params = {locator};

		_logger.logCommand("mouseMove", params);

		try {
			_liferaySelenium.mouseMove(locator);
		}
		catch (Exception e) {
			_logger.logError("mouseMove", params, e);
		}
	}

	public void mouseMoveAt(String locator, String coordString) {
		String[] params = {locator, coordString};

		_logger.logCommand("mouseMoveAt", params);

		try {
			_liferaySelenium.mouseMoveAt(locator, coordString);
		}
		catch (Exception e) {
			_logger.logError("mouseMoveAt", params, e);
		}
	}

	public void mouseOut(String locator) {
		String[] params = {locator};

		_logger.logCommand("mouseOut", params);

		try {
			_liferaySelenium.mouseOut(locator);
		}
		catch (Exception e) {
			_logger.logError("mouseOut", params, e);
		}
	}

	public void mouseOver(String locator) {
		String[] params = {locator};

		try {
			_liferaySelenium.mouseOver(locator);
		}
		catch (Exception e) {

		_logger.logCommand("mouseOver", params);
			_logger.logError("mouseOver", params, e);
		}
	}

	public void mouseUp(String locator) {
		String[] params = {locator};

		_logger.logCommand("mouseUp", params);

		try {
			_liferaySelenium.mouseUp(locator);
		}
		catch (Exception e) {
			_logger.logError("mouseUp", params, e);
		}
	}

	public void mouseUpAt(String locator, String coordString) {
		String[] params = {locator, coordString};

		_logger.logCommand("mouseUpAt", params);

		try {
			_liferaySelenium.mouseUpAt(locator, coordString);
		}
		catch (Exception e) {
			_logger.logError("mouseUpAt", params, e);
		}
	}

	public void mouseUpRight(String locator) {
		String[] params = {locator};

		_logger.logCommand("mouseUpRight", params);

		try {
			_liferaySelenium.mouseUpRight(locator);
		}
		catch (Exception e) {
			_logger.logError("mouseUpRight", params, e);
		}
	}

	public void mouseUpRightAt(String locator, String coordString) {
		String[] params = {locator, coordString};

		_logger.logCommand("mouseUpRightAt", params);

		try {
			_liferaySelenium.mouseUpRightAt(locator, coordString);
		}
		catch (Exception e) {
			_logger.logError("mouseUpRightAt", params, e);
		}
	}

	public void open(String url) {
		url = RuntimeVariables.replace(url);

		String[] params = {url};

		_logger.logCommand("open", params);

		try {
			_liferaySelenium.open(url);
		}
		catch (Exception e) {
			_logger.logError("open", params, e);
		}
	}

	public void open(String url, String ignoreResponseCode) {
		String[] params = {url, ignoreResponseCode};

		_logger.logCommand("open", params);

		try {
			_liferaySelenium.open(url, ignoreResponseCode);
		}
		catch (Exception e) {
			_logger.logError("open", params, e);
		}
	}

	public void openWindow(String url, String windowID) {
		String[] params = {url, windowID};

		_logger.logCommand("openWindow", params);

		try {
			_liferaySelenium.openWindow(url, windowID);
		}
		catch (Exception e) {
			_logger.logError("openWindow", params, e);
		}
	}

	public void paste(String locator) {
		String[] params = {locator};

		_logger.logCommand("paste", params);

		try {
			_liferaySelenium.paste(locator);
		}
		catch (Exception e) {
			_logger.logError("paste", params, e);
		}
	}

	public void pause(String waitTime) throws Exception {
		String[] params = {waitTime};

		_logger.logCommand("pause", params);

		try {
			_liferaySelenium.pause(waitTime);
		}
		catch (Exception e) {
			_logger.logError("pause", params, e);
		}
	}

	public void refresh() {
		String[] params = {};

		_logger.logCommand("refresh", params);

		try {
			_liferaySelenium.refresh();
		}
		catch (Exception e) {
			_logger.logError("refresh", params, e);
		}
	}

	public void refreshAndWait() {
		String[] params = {};

		_logger.logCommand("refreshAndWait", params);

		try {
			_liferaySelenium.refreshAndWait();
		}
		catch (Exception e) {
			_logger.logError("refreshAndWait", params, e);
		}
	}

	public void removeAllSelections(String locator) {
		String[] params = {locator};

		_logger.logCommand("removeAllSelections", params);

		try {
			_liferaySelenium.removeAllSelections(locator);
		}
		catch (Exception e) {
			_logger.logError("removeAllSelections", params, e);
		}
	}

	public void removeScript(String scriptTagId) {
		String[] params = {scriptTagId};

		_logger.logCommand("removeScript", params);

		try {
			_liferaySelenium.removeScript(scriptTagId);
		}
		catch (Exception e) {
			_logger.logError("removeScript", params, e);
		}
	}

	public void removeSelection(String locator, String optionLocator) {
		String[] params = {locator, optionLocator};

		_logger.logCommand("removeSelection", params);

		try {
			_liferaySelenium.removeSelection(locator, optionLocator);
		}
		catch (Exception e) {
			_logger.logError("removeSelection", params, e);
		}
	}

	public String retrieveLastRemoteControlLogs() {
		String[] params = {};

		_logger.logCommand("retrieveLastRemoteControlLogs", params);

		try {
			return _liferaySelenium.retrieveLastRemoteControlLogs();
		}
		catch (Exception e) {
			_logger.logError("retrieveLastRemoteControlLogs", params, e);

			return null;
		}
	}

	public void rollup(String rollupName, String kwargs) {
		String[] params = {rollupName, kwargs};

		_logger.logCommand("rollup", params);

		try {
			_liferaySelenium.rollup(rollupName, kwargs);
		}
		catch (Exception e) {
			_logger.logError("rollup", params, e);
		}
	}

	public void runScript(String script) {
		String[] params = {script};

		_logger.logCommand("runScript", params);

		try {
			_liferaySelenium.runScript(script);
		}
		catch (Exception e) {
			_logger.logError("runScript", params, e);
		}
	}

	public void saveScreenShotAndSource() throws Exception {
		String[] params = {};

		_logger.logCommand("saveScreenShotAndSource", params);

		try {
			_liferaySelenium.saveScreenShotAndSource();
		}
		catch (Exception e) {
			_logger.logError("saveScreenShotAndSource", params, e);
		}
	}

	public void select(String selectLocator, String optionLocator) {
		String[] params = {selectLocator, optionLocator};

		_logger.logCommand("select", params);

		try {
			_liferaySelenium.select(selectLocator, optionLocator);
		}
		catch (Exception e) {
			_logger.logError("select", params, e);
		}
	}

	public void selectAndWait(String selectLocator, String optionLocator) {
		String[] params = {selectLocator, optionLocator};

		_logger.logCommand("selectAndWait", params);

		try {
			_liferaySelenium.selectAndWait(selectLocator, optionLocator);
		}
		catch (Exception e) {
			_logger.logError("selectAndWait", params, e);
		}
	}

	public void selectFrame(String locator) {
		String[] params = {locator};

		_logger.logCommand("selectFrame", params);

		try {
			_liferaySelenium.selectFrame(locator);
		}
		catch (Exception e) {
			_logger.logError("selectFrame", params, e);
		}
	}

	public void selectPopUp(String windowID) {
		String[] params = {windowID};

		_logger.logCommand("selectPopUp", params);

		try {
			_liferaySelenium.selectPopUp(windowID);
		}
		catch (Exception e) {
			_logger.logError("selectPopUp", params, e);
		}
	}

	public void selectWindow(String windowID) {
		String[] params = {windowID};

		_logger.logCommand("selectWindow", params);

		try {
			_liferaySelenium.selectWindow(windowID);
		}
		catch (Exception e) {
			_logger.logError("selectWindow", params, e);
		}
	}

	public void sendKeys(String locator, String value) {
		String[] params = {locator, value};

		_logger.logCommand("sendKeys", params);

		try {
			_liferaySelenium.sendKeys(locator, value);
		}
		catch (Exception e) {
			_logger.logError("sendKeys", params, e);
		}
	}

	public void setBrowserLogLevel(String logLevel) {
		String[] params = {logLevel};

		_logger.logCommand("setBrowserLogLevel", params);

		try {
			_liferaySelenium.setBrowserLogLevel(logLevel);
		}
		catch (Exception e) {
			_logger.logError("setBrowserLogLevel", params, e);
		}
	}

	public void setContext(String context) {
		String[] params = {context};

		_logger.logCommand("setContext", params);

		try {
			_liferaySelenium.setContext(context);
		}
		catch (Exception e) {
			_logger.logError("setContext", params, e);
		}
	}

	public void setCursorPosition(String locator, String position) {
		String[] params = {locator, position};

		_logger.logCommand("setCursorPosition", params);

		try {
			_liferaySelenium.setCursorPosition(locator, position);
		}
		catch (Exception e) {
			_logger.logError("setCursorPosition", params, e);
		}
	}

	public void setDefaultTimeout() {
		String[] params = {};

		_logger.logCommand("setDefaultTimeout", params);

		try {
			_liferaySelenium.setDefaultTimeout();
		}
		catch (Exception e) {
			_logger.logError("setDefaultTimeout", params, e);
		}
	}

	public void setDefaultTimeoutImplicit() {
		String[] params = {};

		_logger.logCommand("setDefaultTimeoutImplicit", params);

		try {
			_liferaySelenium.setDefaultTimeoutImplicit();
		}
		catch (Exception e) {
			_logger.logError("setDefaultTimeoutImplicit", params, e);
		}
	}

	public void setExtensionJs(String extensionJs) {
		String[] params = {extensionJs};

		_logger.logCommand("setExtensionJs", params);

		try {
			_liferaySelenium.setExtensionJs(extensionJs);
		}
		catch (Exception e) {
			_logger.logError("setExtensionJs", params, e);
		}
	}

	public void setMouseSpeed(String pixels) {
		String[] params = {pixels};

		_logger.logCommand("setMouseSpeed", params);

		try {
			_liferaySelenium.setMouseSpeed(pixels);
		}
		catch (Exception e) {
			_logger.logError("setMouseSpeed", params, e);
		}
	}

	public void setSpeed(String value) {
		String[] params = {value};

		_logger.logCommand("setSpeed", params);

		try {
			_liferaySelenium.setSpeed(value);
		}
		catch (Exception e) {
			_logger.logError("setSpeed", params, e);
		}
	}

	public void setTimeout(String timeout) {
		String[] params = {timeout};

		_logger.logCommand("setTimeout", params);

		try {
			_liferaySelenium.setTimeout(timeout);
		}
		catch (Exception e) {
			_logger.logError("setTimeout", params, e);
		}
	}

	public void setTimeoutImplicit(String timeout) {
		String[] params = {timeout};

		_logger.logCommand("setTimeoutImplicit", params);

		try {
			_liferaySelenium.setTimeoutImplicit(timeout);
		}
		catch (Exception e) {
			_logger.logError("setTimeoutImplicit", params, e);
		}
	}

	public void shiftKeyDown() {
		String[] params = {};

		_logger.logCommand("shiftKeyDown", params);

		try {
			_liferaySelenium.shiftKeyDown();
		}
		catch (Exception e) {
			_logger.logError("shiftKeyDown", params, e);
		}
	}

	public void shiftKeyUp() {
		String[] params = {};

		_logger.logCommand("shiftKeyUp", params);

		try {
			_liferaySelenium.shiftKeyUp();
		}
		catch (Exception e) {
			_logger.logError("shiftKeyUp", params, e);
		}
	}

	public void showContextualBanner(String className, String methodName) {
		String[] params = {className, methodName};

		_logger.logCommand("showContextualBanner", params);

		try {
			_liferaySelenium.showContextualBanner(className, methodName);
		}
		catch (Exception e) {
			_logger.logError("showContextualBanner", params, e);
		}
	}

	public void showContextualBanner() {
		String[] params = {};

		_logger.logCommand("showContextualBanner", params);

		try {
			_liferaySelenium.showContextualBanner();
		}
		catch (Exception e) {
			_logger.logError("showContextualBanner", params, e);
		}
	}

	public void shutDownSeleniumServer() {
		String[] params = {};

		_logger.logCommand("shutDownSeleniumServer", params);

		try {
			_liferaySelenium.shutDownSeleniumServer();
		}
		catch (Exception e) {
			_logger.logError("shutDownSeleniumServer", params, e);
		}
	}

	public void start(Object optionsObject) {
		String[] params = {optionsObject.toString()};

		_logger.logCommand("start", params);

		try {
			_liferaySelenium.start(optionsObject);
		}
		catch (Exception e) {
			_logger.logError("start", params, e);
		}
	}

	public void start(String optionsString) {
		String[] params = {optionsString};

		_logger.logCommand("start", params);

		try {
			_liferaySelenium.start(optionsString);
		}
		catch (Exception e) {
			_logger.logError("start", params, e);
		}
	}

	public void start() {
		String[] params = {};

		_logger.logCommand("start", params);

		try {
			_liferaySelenium.start();
		}
		catch (Exception e) {
			_logger.logError("start", params, e);
		}
	}

	public void stop() {
		String[] params = {};

		_logger.logCommand("stop", params);

		try {
			_liferaySelenium.stop();

			_logger.stop();
		}
		catch (Exception e) {
			_logger.logError("stop", params, e);
		}
	}

	public void submit(String formLocator) {
		String[] params = {formLocator};

		_logger.logCommand("submit", params);

		try {
			_liferaySelenium.submit(formLocator);
		}
		catch (Exception e) {
			_logger.logError("submit", params, e);
		}
	}

	public void type(String locator, String value) {
		String[] params = {locator, value};

		_logger.logCommand("type", params);

		try {
			_liferaySelenium.type(locator, value);
		}
		catch (Exception e) {
			_logger.logError("type", params, e);
		}
	}

	public void typeFrame(String locator, String value) {
		String[] params = {locator, value};

		_logger.logCommand("typeFrame", params);

		try {
			_liferaySelenium.typeFrame(locator, value);
		}
		catch (Exception e) {
			_logger.logError("typeFrame", params, e);
		}
	}

	public void typeKeys(String locator, String value) {
		String[] params = {locator, value};

		_logger.logCommand("typeKeys", params);

		try {
			_liferaySelenium.typeKeys(locator, value);
		}
		catch (Exception e) {
			_logger.logError("typeKeys", params, e);
		}
	}

	public void uncheck(String locator) {
		String[] params = {locator};

		_logger.logCommand("uncheck", params);

		try {
			_liferaySelenium.uncheck(locator);
		}
		catch (Exception e) {
			_logger.logError("uncheck", params, e);
		}
	}

	public void uploadCommonFile(String locator, String value) {
		String[] params = {locator, value};

		_logger.logCommand("uploadCommonFile", params);

		try {
			_liferaySelenium.uploadCommonFile(locator, value);
		}
		catch (Exception e) {
			_logger.logError("uploadCommonFile", params, e);
		}
	}

	public void uploadFile(String locator, String value) {
		String[] params = {locator, value};

		_logger.logCommand("uploadFile", params);

		try {
			_liferaySelenium.uploadFile(locator, value);
		}
		catch (Exception e) {
			_logger.logError("uploadFile", params, e);
		}
	}

	public void uploadTempFile(String locator, String value) {
		String[] params = {locator, value};

		_logger.logCommand("uploadTempFile", params);

		try {
			_liferaySelenium.uploadTempFile(locator, value);
		}
		catch (Exception e) {
			_logger.logError("uploadTempFile", params, e);
		}
	}

	public void useXpathLibrary(String libraryName) {
		String[] params = {libraryName};

		_logger.logCommand("useXpathLibrary", params);

		try {
			_liferaySelenium.useXpathLibrary(libraryName);
		}
		catch (Exception e) {
			_logger.logError("useXpathLibrary", params, e);
		}
	}

	public void waitForCondition(String script, String timeout) {
		String[] params = {script, timeout};

		_logger.logCommand("waitForCondition", params);

		try {
			_liferaySelenium.waitForCondition(script, timeout);
		}
		catch (Exception e) {
			_logger.logError("waitForCondition", params, e);
		}
	}

	public void waitForConfirmation(String pattern) throws Exception {
		String[] params = {pattern};

		_logger.logCommand("waitForConfirmation", params);

		try {
			_liferaySelenium.waitForConfirmation(pattern);
		}
		catch (Exception e) {
			_logger.logError("waitForConfirmation", params, e);
		}
	}

	public void waitForElementNotPresent(String locator) throws Exception {
		String[] params = {locator};

		_logger.logCommand("waitForElementNotPresent", params);

		try {
			_liferaySelenium.waitForElementNotPresent(locator);
		}
		catch (Exception e) {
			_logger.logError("waitForElementNotPresent", params, e);
		}
	}

	public void waitForElementPresent(String locator) throws Exception {
		String[] params = {locator};

		_logger.logCommand("waitForElementPresent", params);

		try {
			_liferaySelenium.waitForElementPresent(locator);
		}
		catch (Exception e) {
			_logger.logError("waitForElementPresent", params, e);
		}
	}

	public void waitForFrameToLoad(String frameAddress, String timeout) {
		String[] params = {frameAddress, timeout};

		_logger.logCommand("waitForFrameToLoad", params);

		try {
			_liferaySelenium.waitForFrameToLoad(frameAddress, timeout);
		}
		catch (Exception e) {
			_logger.logError("waitForFrameToLoad", params, e);
		}
	}

	public void waitForNotPartialText(String locator, String value)
		throws Exception {

		String[] params = {locator, value};

		_logger.logCommand("waitForNotPartialText", params);

		try {
			_liferaySelenium.waitForNotPartialText(locator, value);
		}
		catch (Exception e) {
			_logger.logError("waitForNotPartialText", params, e);
		}
	}

	public void waitForNotSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		String[] params = {selectLocator, pattern};

		_logger.logCommand("waitForNotSelectedLabel", params);

		try {
			_liferaySelenium.waitForNotSelectedLabel(selectLocator, pattern);
		}
		catch (Exception e) {
			_logger.logError("waitForNotSelectedLabel", params, e);
		}
	}

	public void waitForNotText(String locator, String value) throws Exception {
		String[] params = {locator, value};

		_logger.logCommand("waitForNotText", params);

		try {
			_liferaySelenium.waitForNotText(locator, value);
		}
		catch (Exception e) {
			_logger.logError("waitForNotText", params, e);
		}
	}

	public void waitForNotValue(String locator, String value) throws Exception {
		String[] params = {locator, value};

		_logger.logCommand("waitForNotValue", params);

		try {
			_liferaySelenium.waitForNotValue(locator, value);
		}
		catch (Exception e) {
			_logger.logError("waitForNotValue", params, e);
		}
	}

	public void waitForNotVisible(String locator) throws Exception {
		String[] params = {locator};

		_logger.logCommand("waitForNotVisible", params);

		try {
			_liferaySelenium.waitForNotVisible(locator);
		}
		catch (Exception e) {
			_logger.logError("waitForNotVisible", params, e);
		}
	}

	public void waitForPageToLoad(String timeout) {
		String[] params = {timeout};

		_logger.logCommand("waitForPageToLoad", params);

		try {
			_liferaySelenium.waitForPageToLoad(timeout);
		}
		catch (Exception e) {
			_logger.logError("waitForPageToLoad", params, e);
		}
	}

	public void waitForPartialText(String locator, String value)
		throws Exception {

		String[] params = {locator, value};

		_logger.logCommand("waitForPartialText", params);

		try {
			_liferaySelenium.waitForPartialText(locator, value);
		}
		catch (Exception e) {
			_logger.logError("waitForPartialText", params, e);
		}
	}

	public void waitForPopUp(String windowID, String timeout) {
		String[] params = {windowID, timeout};

		try {
			_liferaySelenium.waitForPopUp(windowID, timeout);
		}
		catch (Exception e) {

		_logger.logCommand("waitForPopUp", params);
			_logger.logError("waitForPopUp", params, e);
		}
	}

	public void waitForSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		String[] params = {selectLocator, pattern};

		_logger.logCommand("waitForSelectedLabel", params);

		try {
			_liferaySelenium.waitForSelectedLabel(selectLocator, pattern);
		}
		catch (Exception e) {
			_logger.logError("waitForSelectedLabel", params, e);
		}
	}

	public void waitForText(String locator, String value) throws Exception {
		String[] params = {locator, value};

		_logger.logCommand("waitForText", params);

		try {
			_liferaySelenium.waitForText(locator, value);
		}
		catch (Exception e) {
			_logger.logError("waitForText", params, e);
		}
	}

	public void waitForTextNotPresent(String value) throws Exception {
		String[] params = {value};

		_logger.logCommand("waitForTextNotPresent", params);

		try {
			_liferaySelenium.waitForTextNotPresent(value);
		}
		catch (Exception e) {
			_logger.logError("waitForTextNotPresent", params, e);
		}
	}

	public void waitForTextPresent(String value) throws Exception {
		String[] params = {value};

		_logger.logCommand("waitForTextPresent", params);

		try {
			_liferaySelenium.waitForTextPresent(value);
		}
		catch (Exception e) {
			_logger.logError("waitForTextPresent", params, e);
		}
	}

	public void waitForValue(String locator, String value) throws Exception {
		String[] params = {locator, value};

		_logger.logCommand("waitForValue", params);

		try {
			_liferaySelenium.waitForValue(locator, value);
		}
		catch (Exception e) {
			_logger.logError("waitForValue", params, e);
		}
	}

	public void waitForVisible(String locator) throws Exception {
		String[] params = {locator};

		_logger.logCommand("waitForVisible", params);

		try {
			_liferaySelenium.waitForVisible(locator);
		}
		catch (Exception e) {
			_logger.logError("waitForVisible", params, e);
		}
	}

	public void windowFocus() {
		String[] params = {};

		_logger.logCommand("windowFocus", params);

		try {
			_liferaySelenium.windowFocus();
		}
		catch (Exception e) {
			_logger.logError("windowFocus", params, e);
		}
	}

	public void windowMaximize() {
		String[] params = {};

		_logger.logCommand("windowMaximize", params);

		try {
			_liferaySelenium.windowMaximize();
		}
		catch (Exception e) {
			_logger.logError("windowMaximize", params, e);
		}
	}

	public void windowMaximizeAndWait() {
		String[] params = {};

		_logger.logCommand("windowMaximizeAndWait", params);

		try {
			_liferaySelenium.windowMaximizeAndWait();
		}
		catch (Exception e) {
			_logger.logError("windowMaximizeAndWait", params, e);
		}
	}

	private LiferaySelenium _liferaySelenium;
	private Logger _logger;

}