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
public class BaseWebDriverLogging implements LiferaySelenium {

	public BaseWebDriverLogging(LiferaySelenium liferaySelenium) {

		_selenium = liferaySelenium;

		_logger = new Logger();
	}

	public void addCustomRequestHeader(String key, String value) {
		String[] params = {key, value};

		try {
			_selenium.addCustomRequestHeader(key, value);
		}
		catch (Exception e) {
			_logger.errorMessage("addCustomRequestHeader", params, e);
		}
	}

	public void addLocationStrategy(
		String strategyName, String functionDefinition) {

		String[] params = {strategyName, functionDefinition};

		try {
			_selenium.addLocationStrategy(strategyName, functionDefinition);
		}
		catch (Exception e) {
			_logger.errorMessage("addLocationStrategy", params, e);
		}
	}

	public void addScript(String scriptContent, String scriptTagId) {
		String[] params = {scriptContent, scriptTagId};

		try {
			_selenium.addScript(scriptContent, scriptTagId);
		}
		catch (Exception e) {
			_logger.errorMessage("addScript", params, e);
		}
	}

	public void addSelection(String locator, String optionLocator) {
		String[] params = {locator, optionLocator};

		try {
			_selenium.addSelection(locator, optionLocator);
		}
		catch (Exception e) {
			_logger.errorMessage("addSelection", params, e);
		}
	}

	public void allowNativeXpath(String allow) {
		String[] params = {allow};

		try {
			_selenium.allowNativeXpath(allow);
		}
		catch (Exception e) {
			_logger.errorMessage("allowNativeXpath", params, e);
		}
	}

	public void altKeyDown() {
		String[] params = {};

		try {
			_selenium.altKeyDown();
		}
		catch (Exception e) {
			_logger.errorMessage("altKeyDown", params, e);
		}
	}

	public void altKeyUp() {
		String[] params = {};

		try {
			_selenium.altKeyUp();
		}
		catch (Exception e) {
			_logger.errorMessage("altKeyUp", params, e);
		}
	}

	public void answerOnNextPrompt(String answer) {
		String[] params = {answer};

		try {
			_selenium.answerOnNextPrompt(answer);
		}
		catch (Exception e) {
			_logger.errorMessage("answerOnNextPrompt", params, e);
		}
	}

	public void assertAlert(String pattern) {
		String[] params = {pattern};

		try {
			_selenium.assertAlert(pattern);
		}
		catch (Exception e) {
			_logger.errorMessage("assertAlert", params, e);
		}
	}

	public void assertChecked(String pattern) {
		String[] params = {pattern};

		try {
			_selenium.assertChecked(pattern);
		}
		catch (Exception e) {
			_logger.errorMessage("assertChecked", params, e);
		}
	}

	public void assertConfirmation(String pattern) {
		String[] params = {pattern};

		try {
			_selenium.assertConfirmation(pattern);
		}
		catch (Exception e) {
			_logger.errorMessage("assertConfirmation", params, e);
		}
	}

	public void assertElementNotPresent(String locator) {
		String[] params = {locator};

		try {
			_selenium.assertElementNotPresent(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("assertElementNotPresent", params, e);
		}
	}

	public void assertElementPresent(String locator) {
		String[] params = {locator};

		Throwable t = new Throwable();

		String methodToLog = decideMethodToLog(t, params);

		try {
			_selenium.assertElementPresent(locator);
		}
		catch (Exception e) {
			_logger.errorMessage(methodToLog, params, e);
		}
	}

	public void assertLocation(String pattern) {
		String[] params = {pattern};

		try {
			_selenium.assertLocation(pattern);
		}
		catch (Exception e) {
			_logger.errorMessage("assertLocation", params, e);
		}
	}

	public void assertNotAlert(String pattern) {
		String[] params = {pattern};

		try {
			_selenium.assertNotAlert(pattern);
		}
		catch (Exception e) {
			_logger.errorMessage("assertNotAlert", params, e);
		}
	}

	public void assertNotChecked(String locator) {
		String[] params = {locator};

		try {
			_selenium.assertNotChecked(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("assertNotChecked", params, e);
		}
	}

	public void assertNotLocation(String pattern) {
		String[] params = {pattern};

		try {
			_selenium.assertNotLocation(pattern);
		}
		catch (Exception e) {
			_logger.errorMessage("assertNotLocation", params, e);
		}
	}

	public void assertNotPartialText(String locator, String pattern) {
		String[] params = {locator, pattern};

		try {
			_selenium.assertNotPartialText(locator, pattern);
		}
		catch (Exception e) {
			_logger.errorMessage("assertNotPartialText", params, e);
		}
	}

	public void assertNotSelectedLabel(String selectLocator, String pattern) {
		String[] params = {selectLocator, pattern};

		try {
			_selenium.assertNotSelectedLabel(selectLocator, pattern);
		}
		catch (Exception e) {
			_logger.errorMessage("assertNotSelectedLabel", params, e);
		}
	}

	public void assertNotText(String locator, String pattern) {
		String[] params = {locator, pattern};

		try {
			_selenium.assertNotText(locator, pattern);
		}
		catch (Exception e) {
			_logger.errorMessage("assertNotText", params, e);
		}
	}

	public void assertNotValue(String locator, String pattern) {
		String[] params = {locator, pattern};

		try {
			_selenium.assertNotValue(locator, pattern);
		}
		catch (Exception e) {
			_logger.errorMessage("assertNotValue", params, e);
		}
	}

	public void assertNotVisible(String locator) {
		String[] params = {locator};

		try {
			_selenium.assertNotVisible(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("assertNotVisible", params, e);
		}
	}

	public void assertPartialText(String locator, String pattern) {
		String[] params = {locator, pattern};

		try {
			_selenium.assertPartialText(locator, pattern);
		}
		catch (Exception e) {
			_logger.errorMessage("assertPartialText", params, e);
		}
	}

	public void assertSelectedLabel(String selectLocator, String pattern) {
		String[] params = {selectLocator, pattern};

		try {
			_selenium.assertSelectedLabel(selectLocator, pattern);
		}
		catch (Exception e) {
			_logger.errorMessage("assertSelectedLabel", params, e);
		}
	}

	public void assertText(String locator, String pattern) {
		String[] params = {locator, pattern};

		try {
			_selenium.assertText(locator, pattern);
		}
		catch (Exception e) {
			_logger.errorMessage("assertText", params, e);
		}
	}

	public void assertTextNotPresent(String pattern) {
		String[] params = {pattern};

		try {
			_selenium.assertTextNotPresent(pattern);
		}
		catch (Exception e) {
			_logger.errorMessage("assertTextNotPresent", params, e);
		}
	}

	public void assertTextPresent(String pattern) {
		String[] params = {pattern};

		try {
			_selenium.assertTextPresent(pattern);
		}
		catch (Exception e) {
			_logger.errorMessage("assertTextPresent", params, e);
		}
	}

	public void assertValue(String locator, String pattern) {
		String[] params = {locator, pattern};

		try {
			_selenium.assertValue(locator, pattern);
		}
		catch (Exception e) {
			_logger.errorMessage("assertValue", params, e);
		}
	}

	public void assertVisible(String locator) {
		String[] params = {locator};

		Throwable t = new Throwable();

		String methodToLog = decideMethodToLog(t, params);

		try {
			_selenium.assertVisible(locator);
		}
		catch (Exception e) {
			_logger.errorMessage(methodToLog, params, e);
		}
	}

	public void assignId(String locator, String identifier) {
		String[] params = {locator, identifier};

		try {
			_selenium.assignId(locator, identifier);
		}
		catch (Exception e) {
			_logger.errorMessage("assignId", params, e);
		}
	}

	public void attachFile(String fieldLocator, String fileLocator) {
		String[] params = {fieldLocator, fileLocator};

		try {
			_selenium.attachFile(fieldLocator, fileLocator);
		}
		catch (Exception e) {
			_logger.errorMessage("attachFile", params, e);
		}
	}

	public void captureEntirePageScreenshot(String fileName, String kwargs) {
		String[] params = {fileName, kwargs};

		try {
			_selenium.captureEntirePageScreenshot(fileName, kwargs);
		}
		catch (Exception e) {
			_logger.errorMessage("captureEntirePageScreenshot", params, e);
		}
	}

	public String captureEntirePageScreenshotToString(String kwargs) {
		String[] params = {kwargs};

		try {
			return _selenium.captureEntirePageScreenshotToString(kwargs);
		}
		catch (Exception e) {
			_logger.errorMessage(
				"captureEntirePageScreenshotToString", params, e);
			return null;
		}
	}

	public String captureNetworkTraffic(String type) {
		String[] params = {type};

		try {
			return _selenium.captureNetworkTraffic(type);
		}
		catch (Exception e) {
			_logger.errorMessage("captureNetworkTraffic", params, e);
			return null;
		}
	}

	public void captureScreenshot(String fileName) {
		String[] params = {fileName};

		try {
			_selenium.captureScreenshot(fileName);
		}
		catch (Exception e) {
			_logger.errorMessage("captureScreenshot", params, e);
		}
	}

	public String captureScreenshotToString() {
		String[] params = {};

		try {
			return _selenium.captureScreenshotToString();
		}
		catch (Exception e) {
			_logger.errorMessage("captureScreenshotToString", params, e);
			return null;
		}
	}

	public void check(String locator) {
		String[] params = {locator};

		try {
			_selenium.check(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("check", params, e);
		}
	}

	public void chooseCancelOnNextConfirmation() {
		String[] params = {};

		try {
			_selenium.chooseCancelOnNextConfirmation();
		}
		catch (Exception e) {
			_logger.errorMessage("chooseCancelOnNextConfirmation", params, e);
		}
	}

	public void chooseOkOnNextConfirmation() {
		String[] params = {};

		try {
			_selenium.chooseOkOnNextConfirmation();
		}
		catch (Exception e) {
			_logger.errorMessage("chooseOkOnNextConfirmation", params, e);
		}
	}

	public void click(String locator) {
		String[] params = {locator};

		try {
			_selenium.click(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("click", params, e);
		}
	}

	public void clickAndWait(String locator) {
		String[] params = {locator};

		try {
			_selenium.clickAndWait(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("clickAndWait", params, e);
		}
	}

	public void clickAt(String locator, String coordString) {
		String[] params = {locator, coordString};

		try {
			_selenium.clickAt(locator, coordString);
		}
		catch (Exception e) {
			_logger.errorMessage("clickAt", params, e);
		}
	}

	public void clickAtAndWait(String locator, String coordString) {
		String[] params = {locator, coordString};

		try {
			_selenium.clickAtAndWait(locator, coordString);
		}
		catch (Exception e) {
			_logger.errorMessage("clickAtAndWait", params, e);
		}
	}

	public void close() {
		String[] params = {};

		try {
			_selenium.close();
		}
		catch (Exception e) {
			_logger.errorMessage("close", params, e);
		}
	}

	public void contextMenu(String locator) {
		String[] params = {locator};

		try {
			_selenium.contextMenu(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("contextMenu", params, e);
		}
	}

	public void contextMenuAt(String locator, String coordString) {
		String[] params = {locator, coordString};

		try {
			_selenium.contextMenuAt(locator, coordString);
		}
		catch (Exception e) {
			_logger.errorMessage("contextMenuAt", params, e);
		}
	}

	public void controlKeyDown() {
		String[] params = {};

		try {
			_selenium.controlKeyDown();
		}
		catch (Exception e) {
			_logger.errorMessage("controlKeyDown", params, e);
		}
	}

	public void controlKeyUp() {
		String[] params = {};

		try {
			_selenium.controlKeyUp();
		}
		catch (Exception e) {
			_logger.errorMessage("controlKeyUp", params, e);
		}
	}

	public void copyText(String locator) {
		String[] params = {locator};

		try {
			_selenium.copyText(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("copyText", params, e);
		}
	}

	public void copyValue(String locator) {
		String[] params = {locator};

		try {
			_selenium.copyValue(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("copyValue", params, e);
		}
	}

	public void createCookie(String nameValuePair, String optionsString) {
		String[] params = {nameValuePair, optionsString};

		try {
			_selenium.createCookie(nameValuePair, optionsString);
		}
		catch (Exception e) {
			_logger.errorMessage("createCookie", params, e);
		}
	}

	public void deleteAllVisibleCookies() {
		String[] params = {};

		try {
			_selenium.deleteAllVisibleCookies();
		}
		catch (Exception e) {
			_logger.errorMessage("deleteAllVisibleCookies", params, e);
		}
	}

	public void deleteCookie(String name, String optionsString) {
		String[] params = {name, optionsString};

		try {
			_selenium.deleteCookie(name, optionsString);
		}
		catch (Exception e) {
			_logger.errorMessage("deleteCookie", params, e);
		}
	}

	public void deselectPopUp() {
		String[] params = {};

		try {
			_selenium.deselectPopUp();
		}
		catch (Exception e) {
			_logger.errorMessage("deselectPopUp", params, e);
		}
	}

	public void doubleClick(String locator) {
		String[] params = {locator};

		try {
			_selenium.doubleClick(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("doubleClick", params, e);
		}
	}

	public void doubleClickAt(String locator, String coordString) {
		String[] params = {locator, coordString};

		try {
			_selenium.doubleClickAt(locator, coordString);
		}
		catch (Exception e) {
			_logger.errorMessage("doubleClickAt", params, e);
		}
	}

	public void dragAndDrop(String locator, String movementsString) {
		String[] params = {locator, movementsString};

		try {
			_selenium.dragAndDrop(locator, movementsString);
		}
		catch (Exception e) {
			_logger.errorMessage("dragAndDrop", params, e);
		}
	}

	public void dragAndDropToObject(
		String locatorOfObjectToBeDragged,
		String locatorOfDragDestinationObject) {

		String[] params = {
			locatorOfObjectToBeDragged, locatorOfDragDestinationObject};

		try {
			_selenium.dragAndDropToObject(
			locatorOfObjectToBeDragged, locatorOfDragDestinationObject);
		}
		catch (Exception e) {
			_logger.errorMessage("dragAndDropToObject", params, e);
		}
	}

	public void dragdrop(String locator, String movementsString) {
		String[] params = {locator, movementsString};

		try {
			_selenium.dragdrop(locator, movementsString);
		}
		catch (Exception e) {
			_logger.errorMessage("dragdrop", params, e);
		}
	}

	public void echo(String message) {
		String[] params = {message};

		try {
			_selenium.echo(message);
		}
		catch (Exception e) {
			_logger.errorMessage("echo", params, e);
		}
	}

	public void fireEvent(String locator, String eventName) {
		String[] params = {locator, eventName};

		try {
			_selenium.fireEvent(locator, eventName);
		}
		catch (Exception e) {
			_logger.errorMessage("fireEvent", params, e);
		}
	}

	public void focus(String locator) {
		String[] params = {locator};

		try {
			_selenium.focus(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("focus", params, e);
		}
	}

	public String getAlert() {
		String[] params = {};

		try {
			return _selenium.getAlert();
		}
		catch (Exception e) {
			_logger.errorMessage("getAlert", params, e);
			return null;
		}
	}

	public String[] getAllButtons() {
		String[] params = {};

		try {
			return _selenium.getAllButtons();
		}
		catch (Exception e) {
			_logger.errorMessage("getAllButtons", params, e);
			return null;
		}
	}

	public String[] getAllFields() {
		String[] params = {};

		try {
			return _selenium.getAllFields();
		}
		catch (Exception e) {
			_logger.errorMessage("getAllFields", params, e);
			return null;
		}
	}

	public String[] getAllLinks() {
		String[] params = {};

		try {
			return _selenium.getAllLinks();
		}
		catch (Exception e) {
			_logger.errorMessage("getAllLinks", params, e);
			return null;
		}
	}

	public String[] getAllWindowIds() {
		String[] params = {};

		try {
			return _selenium.getAllWindowIds();
		}
		catch (Exception e) {
			_logger.errorMessage("getAllWindowIds", params, e);
			return null;
		}
	}

	public String[] getAllWindowNames() {
		String[] params = {};

		try {
			return _selenium.getAllWindowNames();
		}
		catch (Exception e) {
			_logger.errorMessage("getAllWindowNames", params, e);
			return null;
		}
	}

	public String[] getAllWindowTitles() {
		String[] params = {};

		try {
			return _selenium.getAllWindowTitles();
		}
		catch (Exception e) {
			_logger.errorMessage("getAllWindowTitles", params, e);
			return null;
		}
	}

	public String getAttribute(String attributeLocator) {
		String[] params = {attributeLocator};

		try {
			return _selenium.getAttribute(attributeLocator);
		}
		catch (Exception e) {
			_logger.errorMessage("getAttribute", params, e);
			return null;
		}
	}

	public String[] getAttributeFromAllWindows(String attributeName) {
		String[] params = {attributeName};

		try {
			return _selenium.getAttributeFromAllWindows(attributeName);
		}
		catch (Exception e) {
			_logger.errorMessage("getAttributeFromAllWindows", params, e);
			return null;
		}
	}

	public String getBodyText() {
		String[] params = {};

		try {
			return _selenium.getBodyText();
		}
		catch (Exception e) {
			_logger.errorMessage("getBodyText", params, e);
			return null;
		}
	}

	public String getConfirmation() {
		String[] params = {};

		try {
			return _selenium.getConfirmation();
		}
		catch (Exception e) {
			_logger.errorMessage("getConfirmation", params, e);
			return null;
		}
	}

	public String getCookie() {
		String[] params = {};

		try {
			return _selenium.getCookie();
		}
		catch (Exception e) {
			_logger.errorMessage("getCookie", params, e);
			return null;
		}
	}

	public String getCookieByName(String name) {
		String[] params = {name};

		try {
			return _selenium.getCookieByName(name);
		}
		catch (Exception e) {
			_logger.errorMessage("getCookieByName", params, e);
			return null;
		}
	}

	public Number getCssCount(String css) {
		String[] params = {css};

		try {
			return _selenium.getCssCount(css);
		}
		catch (Exception e) {
			_logger.errorMessage("getCssCount", params, e);
			return null;
		}
	}

	public String getCurrentDay() {
		String[] params = {};

		try {
			return _selenium.getCurrentDay();
		}
		catch (Exception e) {
			_logger.errorMessage("getCurrentDay", params, e);
			return null;
		}
	}

	public String getCurrentMonth() {
		String[] params = {};

		try {
			return _selenium.getCurrentMonth();
		}
		catch (Exception e) {
			_logger.errorMessage("getCurrentMonth", params, e);
			return null;
		}
	}

	public String getCurrentYear() {
		String[] params = {};

		try {
			return _selenium.getCurrentYear();
		}
		catch (Exception e) {
			_logger.errorMessage("getCurrentYear", params, e);
			return null;
		}
	}

	public Number getCursorPosition(String locator) {
		String[] params = {locator};

		try {
			return _selenium.getCursorPosition(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("getCursorPosition", params, e);
			return null;
		}
	}

	public Number getElementHeight(String locator) {
		String[] params = {locator};

		try {
			return _selenium.getElementHeight(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("getElementHeight", params, e);
			return null;
		}
	}

	public Number getElementIndex(String locator) {
		String[] params = {locator};

		try {
			return _selenium.getElementIndex(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("getElementIndex", params, e);
			return null;
		}
	}

	public Number getElementPositionLeft(String locator) {
		String[] params = {locator};

		try {
			return _selenium.getElementPositionLeft(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("getElementPositionLeft", params, e);
			return null;
		}
	}

	public Number getElementPositionTop(String locator) {
		String[] params = {locator};

		try {
			return _selenium.getElementPositionTop(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("getElementPositionTop", params, e);
			return null;
		}
	}

	public Number getElementWidth(String locator) {
		String[] params = {locator};

		try {
			return _selenium.getElementWidth(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("getElementWidth", params, e);
			return null;
		}
	}

	public String getEval(String script) {
		String[] params = {script};

		try {
			return _selenium.getEval(script);
		}
		catch (Exception e) {
			_logger.errorMessage("getEval", params, e);
			return null;
		}
	}

	public String getExpression(String expression) {
		String[] params = {expression};

		try {
			return _selenium.getExpression(expression);
		}
		catch (Exception e) {
			_logger.errorMessage("getExpression", params, e);
			return null;
		}
	}

	public String getFirstNumber(String locator) {
		String[] params = {locator};

		try {
			return _selenium.getFirstNumber(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("getFirstNumber", params, e);
			return null;
		}
	}

	public String getFirstNumberIncrement(String locator) {
		String[] params = {locator};

		try {
			return _selenium.getFirstNumberIncrement(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("getFirstNumberIncrement", params, e);
			return null;
		}
	}

	public String getHtmlSource() {
		String[] params = {};

		try {
			return _selenium.getHtmlSource();
		}
		catch (Exception e) {
			_logger.errorMessage("getHtmlSource", params, e);
			return null;
		}
	}

	public String getLocation() {
		String[] params = {};

		try {
			return _selenium.getLocation();
		}
		catch (Exception e) {
			_logger.errorMessage("getLocation", params, e);
			return null;
		}
	}

	public String getLog() {
		String[] params = {};

		try {
			return _selenium.getLog();
		}
		catch (Exception e) {
			_logger.errorMessage("getLog", params, e);
			return null;
		}
	}

	public Number getMouseSpeed() {
		String[] params = {};

		try {
			return _selenium.getMouseSpeed();
		}
		catch (Exception e) {
			_logger.errorMessage("getMouseSpeed", params, e);
			return null;
		}
	}

	public String getNumberDecrement(String value) {
		String[] params = {value};

		try {
			return _selenium.getNumberDecrement(value);
		}
		catch (Exception e) {
			_logger.errorMessage("getNumberDecrement", params, e);
			return null;
		}
	}

	public String getNumberIncrement(String value) {
		String[] params = {value};

		try {
			return _selenium.getNumberIncrement(value);
		}
		catch (Exception e) {
			_logger.errorMessage("getNumberIncrement", params, e);
			return null;
		}
	}

	public String getPrompt() {
		String[] params = {};

		try {
			return _selenium.getPrompt();
		}
		catch (Exception e) {
			_logger.errorMessage("getPrompt", params, e);
			return null;
		}
	}

	public String getSelectedId(String selectLocator) {
		String[] params = {selectLocator};

		try {
			return _selenium.getSelectedId(selectLocator);
		}
		catch (Exception e) {
			_logger.errorMessage("getSelectedId", params, e);
			return null;
		}
	}

	public String[] getSelectedIds(String selectLocator) {
		String[] params = {selectLocator};

		try {
			return _selenium.getSelectedIds(selectLocator);
		}
		catch (Exception e) {
			_logger.errorMessage("getSelectedIds", params, e);
			return null;
		}
	}

	public String getSelectedIndex(String selectLocator) {
		String[] params = {selectLocator};

		try {
			return _selenium.getSelectedIndex(selectLocator);
		}
		catch (Exception e) {
			_logger.errorMessage("getSelectedIndex", params, e);
			return null;
		}
	}

	public String[] getSelectedIndexes(String selectLocator) {
		String[] params = {selectLocator};

		try {
			return _selenium.getSelectedIndexes(selectLocator);
		}
		catch (Exception e) {
			_logger.errorMessage("getSelectedIndexes", params, e);
			return null;
		}
	}

	public String getSelectedLabel(String selectLocator) {
		String[] params = {selectLocator};

		try {
			return _selenium.getSelectedLabel(selectLocator);
		}
		catch (Exception e) {
			_logger.errorMessage("getSelectedLabel", params, e);
			return null;
		}
	}

	public String[] getSelectedLabels(String selectLocator) {
		String[] params = {selectLocator};

		try {
			return _selenium.getSelectedLabels(selectLocator);
		}
		catch (Exception e) {
			_logger.errorMessage("getSelectedLabels", params, e);
			return null;
		}
	}

	public String getSelectedValue(String selectLocator) {
		String[] params = {selectLocator};

		try {
			return _selenium.getSelectedValue(selectLocator);
		}
		catch (Exception e) {
			_logger.errorMessage("getSelectedValue", params, e);
			return null;
		}
	}

	public String[] getSelectedValues(String selectLocator) {
		String[] params = {selectLocator};

		try {
			return _selenium.getSelectedValues(selectLocator);
		}
		catch (Exception e) {
			_logger.errorMessage("getSelectedValues", params, e);
			return null;
		}
	}

	public String[] getSelectOptions(String selectLocator) {
		String[] params = {selectLocator};

		try {
			return _selenium.getSelectOptions(selectLocator);
		}
		catch (Exception e) {
			_logger.errorMessage("getSelectOptions", params, e);
			return null;
		}
	}

	public String getSpeed() {
		String[] params = {};

		try {
			return _selenium.getSpeed();
		}
		catch (Exception e) {
			_logger.errorMessage("getSpeed", params, e);
			return null;
		}
	}

	public String getTable(String tableCellAddress) {
		String[] params = {tableCellAddress};

		try {
			return _selenium.getTable(tableCellAddress);
		}
		catch (Exception e) {
			_logger.errorMessage("getTable", params, e);
			return null;
		}
	}

	public String getText(String locator) {
		String[] params = {locator};

		try {
			return _selenium.getText(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("getText", params, e);
			return null;
		}
	}

	public String getTitle() {
		String[] params = {};

		try {
			return _selenium.getTitle();
		}
		catch (Exception e) {
			_logger.errorMessage("getTitle", params, e);
			return null;
		}
	}

	public String getValue(String locator) {
		String[] params = {locator};

		try {
			return _selenium.getValue(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("getValue", params, e);
			return null;
		}
	}

	public boolean getWhetherThisFrameMatchFrameExpression(
		String currentFrameString, String target) {

		String[] params = {currentFrameString, target};

		try {
			return _selenium.getWhetherThisFrameMatchFrameExpression(
				currentFrameString, target);
		}
		catch (Exception e) {
			_logger.errorMessage(
				"getWhetherThisFrameMatchFrameExpression", params, e);
			return false;
		}
	}

	public boolean getWhetherThisWindowMatchWindowExpression(
		String currentFrameString, String target) {

		String[] params = {currentFrameString, target};

		try {
			return _selenium.getWhetherThisWindowMatchWindowExpression(
				currentFrameString, target);
		}
		catch (Exception e) {
			_logger.errorMessage(
				"getWhetherThisWindowMatchFrameExpression", params, e);
			return false;
		}
	}

	public Number getXpathCount(String xpath) {
		String[] params = {xpath};

		try {
			return _selenium.getXpathCount(xpath);
		}
		catch (Exception e) {
			_logger.errorMessage("getXpathCount", params, e);
			return null;
		}
	}

	public void goBack() {
		String[] params = {};

		try {
			_selenium.goBack();
		}
		catch (Exception e) {
			_logger.errorMessage("goBack", params, e);
		}
	}

	public void goBackAndWait() {
		String[] params = {};

		try {
			_selenium.goBackAndWait();
		}
		catch (Exception e) {
			_logger.errorMessage("goBackAndWait", params, e);
		}
	}

	public void highlight(String locator) {
		String[] params = {locator};

		try {
			_selenium.highlight(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("highlight", params, e);
		}
	}

	public void ignoreAttributesWithoutValue(String ignore) {
		String[] params = {ignore};

		try {
			_selenium.ignoreAttributesWithoutValue(ignore);
		}
		catch (Exception e) {
			_logger.errorMessage("ignoreAttributesWithoutValue", params, e);
		}
	}

	public boolean isAlertPresent() {
		String[] params = {};

		try {
			return _selenium.isAlertPresent();
		}
		catch (Exception e) {
			_logger.errorMessage("isAlertPresent", params, e);
			return false;
		}
	}

	public boolean isChecked(String locator) {
		String[] params = {locator};

		try {
			return _selenium.isChecked(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("isChecked", params, e);
			return false;
		}
	}

	public boolean isConfirmation(String pattern) {
		String[] params = {pattern};

		try {
			return _selenium.isConfirmation(pattern);
		}
		catch (Exception e) {
			_logger.errorMessage("isConfirmation", params, e);
			return false;
		}
	}

	public boolean isConfirmationPresent() {
		String[] params = {};

		try {
			return _selenium.isConfirmationPresent();
		}
		catch (Exception e) {
			_logger.errorMessage("isConfirmationPresent", params, e);
			return false;
		}
	}

	public boolean isCookiePresent(String name) {
		String[] params = {name};

		try {
			return _selenium.isCookiePresent(name);
		}
		catch (Exception e) {
			_logger.errorMessage("isCookiePresent", params, e);
			return false;
		}
	}

	public boolean isEditable(String locator) {
		String[] params = {locator};

		try {
			return _selenium.isEditable(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("isEditable", params, e);
			return false;
		}
	}

	public boolean isElementNotPresent(String locator) {
		String[] params = {locator};

		try {
			return _selenium.isElementNotPresent(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("isElementNotPresent", params, e);
			return false;
		}
	}

	public boolean isElementPresent(String locator) {
		String[] params = {locator};

		try {
			return _selenium.isElementPresent(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("isElementPresent", params, e);
			return false;
		}
	}

	public boolean isNotChecked(String locator) {
		String[] params = {locator};

		try {
			return _selenium.isNotChecked(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("isNotChecked", params, e);
			return false;
		}
	}

	public boolean isNotPartialText(String locator, String value) {
		String[] params = {locator, value};

		try {
			return _selenium.isNotPartialText(locator, value);
		}
		catch (Exception e) {
			_logger.errorMessage("isNotPartialText", params, e);
			return false;
		}
	}

	public boolean isNotSelectedLabel(String selectLocator, String pattern) {
		String[] params = {selectLocator, pattern};

		try {
			return _selenium.isNotSelectedLabel(selectLocator, pattern);
		}
		catch (Exception e) {
			_logger.errorMessage("isNotSelectedLabel", params, e);
			return false;
		}
	}

	public boolean isNotText(String locator, String value) {
		String[] params = {locator, value};

		try {
			return _selenium.isNotText(locator, value);
		}
		catch (Exception e) {
			_logger.errorMessage("isNotText", params, e);
			return false;
		}
	}

	public boolean isNotValue(String locator, String value) {
		String[] params = {locator, value};

		try {
			return _selenium.isNotValue(locator, value);
		}
		catch (Exception e) {
			_logger.errorMessage("isNotValue", params, e);
			return false;
		}
	}

	public boolean isNotVisible(String locator) {
		String[] params = {locator};

		try {
			return _selenium.isNotVisible(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("isNotVisible", params, e);
			return false;
		}
	}

	public boolean isOrdered(String locator1, String locator2) {
		String[] params = {locator1, locator2};

		try {
			return _selenium.isOrdered(locator1, locator2);
		}
		catch (Exception e) {
			_logger.errorMessage("isOrdered", params, e);
			return false;
		}
	}

	public boolean isPartialText(String locator, String value) {
		String[] params = {locator, value};

		try {
			return _selenium.isPartialText(locator, value);
		}
		catch (Exception e) {
			_logger.errorMessage("isPartialText", params, e);
			return false;
		}
	}

	public boolean isPromptPresent() {
		String[] params = {};

		try {
			return _selenium.isPromptPresent();
		}
		catch (Exception e) {
			_logger.errorMessage("isPromptPresent", params, e);
			return false;
		}
	}

	public boolean isSelectedLabel(String selectLocator, String pattern) {
		String[] params = {selectLocator, pattern};

		try {
			return _selenium.isSelectedLabel(selectLocator, pattern);
		}
		catch (Exception e) {
			_logger.errorMessage("isSelectedLabel", params, e);
			return false;
		}
	}

	public boolean isSomethingSelected(String selectLocator) {
		String[] params = {selectLocator};

		try {
			return _selenium.isSomethingSelected(selectLocator);
		}
		catch (Exception e) {
			_logger.errorMessage("isSomethingSelected", params, e);
			return false;
		}
	}

	public boolean isText(String locator, String value) {
		String[] params = {locator, value};

		try {
			return _selenium.isText(locator, value);
		}
		catch (Exception e) {
			_logger.errorMessage("isText", params, e);
			return false;
		}
	}

	public boolean isTextNotPresent(String pattern) {
		String[] params = {pattern};

		try {
			return _selenium.isTextNotPresent(pattern);
		}
		catch (Exception e) {
			_logger.errorMessage("isTextNotPresent", params, e);
			return false;
		}
	}

	public boolean isTextPresent(String pattern) {
		String[] params = {pattern};

		try {
			return _selenium.isTextPresent(pattern);
		}
		catch (Exception e) {
			_logger.errorMessage("isTextPresent", params, e);
			return false;
		}
	}

	public boolean isValue(String locator, String value) {
		String[] params = {locator, value};

		try {
			return _selenium.isValue(locator, value);
		}
		catch (Exception e) {
			_logger.errorMessage("isValue", params, e);
			return false;
		}
	}

	public boolean isVisible(String locator) {
		String[] params = {locator};

		try {
			return _selenium.isVisible(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("isVisible", params, e);
			return false;
		}
	}

	public void keyDown(String locator, String keySequence) {
		String[] params = {locator, keySequence};

		try {
			_selenium.keyDown(locator, keySequence);
		}
		catch (Exception e) {
			_logger.errorMessage("keyDown", params, e);
		}
	}

	public void keyDownAndWait(String locator, String keySequence) {
		String[] params = {locator, keySequence};

		try {
			_selenium.keyDownAndWait(locator, keySequence);
		}
		catch (Exception e) {
			_logger.errorMessage("keyDownAndWait", params, e);
		}
	}

	public void keyDownNative(String keycode) {
		String[] params = {keycode};

		try {
			_selenium.keyDownNative(keycode);
		}
		catch (Exception e) {
			_logger.errorMessage("keyDownNative", params, e);
		}
	}

	public void keyPress(String locator, String keySequence) {
		String[] params = {locator, keySequence};

		try {
			_selenium.keyPress(locator, keySequence);
		}
		catch (Exception e) {
			_logger.errorMessage("keyPress", params, e);
		}
	}

	public void keyPressAndWait(String locator, String keySequence) {
		String[] params = {locator, keySequence};

		try {
			_selenium.keyPressAndWait(locator, keySequence);
		}
		catch (Exception e) {
			_logger.errorMessage("keyPressAndWait", params, e);
		}
	}

	public void keyPressNative(String keycode) {
		String[] params = {keycode};

		try {
			_selenium.keyPressNative(keycode);
		}
		catch (Exception e) {
			_logger.errorMessage("keyPressNative", params, e);
		}
	}

	public void keyUp(String locator, String keySequence) {
		String[] params = {locator, keySequence};

		try {
			_selenium.keyUp(locator, keySequence);
		}
		catch (Exception e) {
			_logger.errorMessage("keyUp", params, e);
		}
	}

	public void keyUpAndWait(String locator, String keySequence) {
		String[] params = {locator, keySequence};

		try {
			_selenium.keyUpAndWait(locator, keySequence);
		}
		catch (Exception e) {
			_logger.errorMessage("keyUpAndWait", params, e);
		}
	}

	public void keyUpNative(String keycode) {
		String[] params = {keycode};

		try {
			_selenium.keyUpNative(keycode);
		}
		catch (Exception e) {
			_logger.errorMessage("keyUpNative", params, e);
		}
	}

	public void makeVisible(String locator) {
		String[] params = {locator};

		try {
			_selenium.makeVisible(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("makeVisible", params, e);
		}
	}

	public void metaKeyDown() {
		String[] params = {};

		try {
			_selenium.metaKeyDown();
		}
		catch (Exception e) {
			_logger.errorMessage("metaKeyDown", params, e);
		}
	}

	public void metaKeyUp() {
		String[] params = {};

		try {
			_selenium.metaKeyUp();
		}
		catch (Exception e) {
			_logger.errorMessage("metaKeyUp", params, e);
		}
	}

	public void mouseDown(String locator) {
		String[] params = {locator};

		try {
			_selenium.mouseDown(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("mouseDown", params, e);
		}
	}

	public void mouseDownAt(String locator, String coordString) {
		String[] params = {locator, coordString};

		try {
			_selenium.mouseDownAt(locator, coordString);
		}
		catch (Exception e) {
			_logger.errorMessage("mouseDownAt", params, e);
		}
	}

	public void mouseDownRight(String locator) {
		String[] params = {locator};

		try {
			_selenium.mouseDownRight(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("mouseDownRight", params, e);
		}
	}

	public void mouseDownRightAt(String locator, String coordString) {
		String[] params = {locator, coordString};

		try {
			_selenium.mouseDownRightAt(locator, coordString);
		}
		catch (Exception e) {
			_logger.errorMessage("mouseDownRightAt", params, e);
		}
	}

	public void mouseMove(String locator) {
		String[] params = {locator};

		try {
			_selenium.mouseMove(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("mouseMove", params, e);
		}
	}

	public void mouseMoveAt(String locator, String coordString) {
		String[] params = {locator, coordString};

		try {
			_selenium.mouseMoveAt(locator, coordString);
		}
		catch (Exception e) {
			_logger.errorMessage("mouseMoveAt", params, e);
		}
	}

	public void mouseOut(String locator) {
		String[] params = {locator};

		try {
			_selenium.mouseOut(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("mouseOut", params, e);
		}
	}

	public void mouseOver(String locator) {
		String[] params = {locator};

		try {
			_selenium.mouseOver(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("mouseOver", params, e);
		}
	}

	public void mouseUp(String locator) {
		String[] params = {locator};

		try {
			_selenium.mouseUp(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("mouseUp", params, e);
		}
	}

	public void mouseUpAt(String locator, String coordString) {
		String[] params = {locator, coordString};

		try {
			_selenium.mouseUpAt(locator, coordString);
		}
		catch (Exception e) {
			_logger.errorMessage("mouseUpAt", params, e);
		}
	}

	public void mouseUpRight(String locator) {
		String[] params = {locator};

		try {
			_selenium.mouseUpRight(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("mouseUpRight", params, e);
		}
	}

	public void mouseUpRightAt(String locator, String coordString) {
		String[] params = {locator, coordString};

		try {
			_selenium.mouseUpRightAt(locator, coordString);
		}
		catch (Exception e) {
			_logger.errorMessage("mouseUpRightAt", params, e);
		}
	}

	public void open(String url) {
		url = RuntimeVariables.replace(url);

		String[] params = {url};

		try {
			_selenium.open(url);
		}
		catch (Exception e) {
			_logger.errorMessage("open", params, e);
		}
	}

	public void open(String url, String ignoreResponseCode) {
		String[] params = {url, ignoreResponseCode};

		try {
			_selenium.open(url, ignoreResponseCode);
		}
		catch (Exception e) {
			_logger.errorMessage("open", params, e);
		}
	}

	public void openWindow(String url, String windowID) {
		String[] params = {url, windowID};

		try {
			_selenium.openWindow(url, windowID);
		}
		catch (Exception e) {
			_logger.errorMessage("openWindow", params, e);
		}
	}

	public void paste(String locator) {
		String[] params = {locator};

		try {
			_selenium.paste(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("paste", params, e);
		}
	}

	public void pause(String waitTime) throws Exception {
		String[] params = {waitTime};

		try {
			_selenium.pause(waitTime);
		}
		catch (Exception e) {
			_logger.errorMessage("pause", params, e);
		}
	}

	public void refresh() {
		String[] params = {};

		try {
			_selenium.refresh();
		}
		catch (Exception e) {
			_logger.errorMessage("refresh", params, e);
		}
	}

	public void refreshAndWait() {
		String[] params = {};

		try {
			_selenium.refreshAndWait();
		}
		catch (Exception e) {
			_logger.errorMessage("refreshAndWait", params, e);
		}
	}

	public void removeAllSelections(String locator) {
		String[] params = {locator};

		try {
			_selenium.removeAllSelections(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("removeAllSelections", params, e);
		}
	}

	public void removeScript(String scriptTagId) {
		String[] params = {scriptTagId};

		try {
			_selenium.removeScript(scriptTagId);
		}
		catch (Exception e) {
			_logger.errorMessage("removeScript", params, e);
		}
	}

	public void removeSelection(String locator, String optionLocator) {
		String[] params = {locator, optionLocator};

		try {
			_selenium.removeSelection(locator, optionLocator);
		}
		catch (Exception e) {
			_logger.errorMessage("removeSelection", params, e);
		}
	}

	public String retrieveLastRemoteControlLogs() {
		String[] params = {};

		try {
			return _selenium.retrieveLastRemoteControlLogs();
		}
		catch (Exception e) {
			_logger.errorMessage("retrieveLastRemoteControlLogs", params, e);
			return null;
		}
	}

	public void rollup(String rollupName, String kwargs) {
		String[] params = {rollupName, kwargs};

		try {
			_selenium.rollup(rollupName, kwargs);
		}
		catch (Exception e) {
			_logger.errorMessage("rollup", params, e);
		}
	}

	public void runScript(String script) {
		String[] params = {script};

		try {
			_selenium.runScript(script);
		}
		catch (Exception e) {
			_logger.errorMessage("runScript", params, e);
		}
	}

	public void saveScreenShotAndSource() throws Exception {
		String[] params = {};

		try {
			_selenium.saveScreenShotAndSource();
		}
		catch (Exception e) {
			_logger.errorMessage("saveScreenShotAndSource", params, e);
		}
	}

	public void select(String selectLocator, String optionLocator) {
		String[] params = {selectLocator, optionLocator};

		try {
			_selenium.select(selectLocator, optionLocator);
		}
		catch (Exception e) {
			_logger.errorMessage("select", params, e);
		}
	}

	public void selectAndWait(String selectLocator, String optionLocator) {
		String[] params = {selectLocator, optionLocator};

		try {
			_selenium.selectAndWait(selectLocator, optionLocator);
		}
		catch (Exception e) {
			_logger.errorMessage("selectAndWait", params, e);
		}
	}

	public void selectFrame(String locator) {
		String[] params = {locator};

		try {
			_selenium.selectFrame(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("selectFrame", params, e);
		}
	}

	public void selectPopUp(String windowID) {
		String[] params = {windowID};

		try {
			_selenium.selectPopUp(windowID);
		}
		catch (Exception e) {
			_logger.errorMessage("selectPopUp", params, e);
		}
	}

	public void selectWindow(String windowID) {
		String[] params = {windowID};

		try {
			_selenium.selectWindow(windowID);
		}
		catch (Exception e) {
			_logger.errorMessage("selectWindow", params, e);
		}
	}

	public void sendKeys(String locator, String value) {
		String[] params = {locator, value};

		try {
			_selenium.sendKeys(locator, value);
		}
		catch (Exception e) {
			_logger.errorMessage("sendKeys", params, e);
		}
	}

	public void setBrowserLogLevel(String logLevel) {
		String[] params = {logLevel};

		try {
			_selenium.setBrowserLogLevel(logLevel);
		}
		catch (Exception e) {
			_logger.errorMessage("setBrowserLogLevel", params, e);
		}
	}

	public void setContext(String context) {
		String[] params = {context};

		try {
			_selenium.setContext(context);
		}
		catch (Exception e) {
			_logger.errorMessage("setContext", params, e);
		}
	}

	public void setCursorPosition(String locator, String position) {
		String[] params = {locator, position};

		try {
			_selenium.setCursorPosition(locator, position);
		}
		catch (Exception e) {
			_logger.errorMessage("setCursorPosition", params, e);
		}
	}

	public void setDefaultTimeout() {
		String[] params = {};

		try {
			_selenium.setDefaultTimeout();
		}
		catch (Exception e) {
			_logger.errorMessage("setDefaultTimeout", params, e);
		}
	}

	public void setDefaultTimeoutImplicit() {
		String[] params = {};

		try {
			_selenium.setDefaultTimeoutImplicit();
		}
		catch (Exception e) {
			_logger.errorMessage("setDefaultTimeoutImplicit", params, e);
		}
	}

	public void setExtensionJs(String extensionJs) {
		String[] params = {extensionJs};

		try {
			_selenium.setExtensionJs(extensionJs);
		}
		catch (Exception e) {
			_logger.errorMessage("setExtensionJs", params, e);
		}
	}

	public void setMouseSpeed(String pixels) {
		String[] params = {pixels};

		try {
			_selenium.setMouseSpeed(pixels);
		}
		catch (Exception e) {
			_logger.errorMessage("setMouseSpeed", params, e);
		}
	}

	public void setSpeed(String value) {
		String[] params = {value};

		try {
			_selenium.setSpeed(value);
		}
		catch (Exception e) {
			_logger.errorMessage("setSpeed", params, e);
		}
	}

	public void setTimeout(String timeout) {
		String[] params = {timeout};

		try {
			_selenium.setTimeout(timeout);
		}
		catch (Exception e) {
			_logger.errorMessage("setTimeout", params, e);
		}
	}

	public void setTimeoutImplicit(String timeout) {
		String[] params = {timeout};

		try {
			_selenium.setTimeoutImplicit(timeout);
		}
		catch (Exception e) {
			_logger.errorMessage("setTimeoutImplicit", params, e);
		}
	}

	public void shiftKeyDown() {
		String[] params = {};

		try {
			_selenium.shiftKeyDown();
		}
		catch (Exception e) {
			_logger.errorMessage("shiftKeyDown", params, e);
		}
	}

	public void shiftKeyUp() {
		String[] params = {};

		try {
			_selenium.shiftKeyUp();
		}
		catch (Exception e) {
			_logger.errorMessage("shiftKeyUp", params, e);
		}
	}

	public void showContextualBanner(String className, String methodName) {
		String[] params = {className, methodName};

		try {
			_selenium.showContextualBanner(className, methodName);
		}
		catch (Exception e) {
			_logger.errorMessage("showContextualBanner", params, e);
		}
	}

	public void showContextualBanner() {
		String[] params = {};

		try {
			_selenium.showContextualBanner();
		}
		catch (Exception e) {
			_logger.errorMessage("showContextualBanner", params, e);
		}
	}

	public void shutDownSeleniumServer() {
		String[] params = {};

		try {
			_selenium.shutDownSeleniumServer();
		}
		catch (Exception e) {
			_logger.errorMessage("shutDownSeleniumServer", params, e);
		}
	}

	public void start(Object optionsObject) {
		String[] params = {optionsObject.toString()};

		try {
			_selenium.start(optionsObject);
		}
		catch (Exception e) {
			_logger.errorMessage("start", params, e);
		}
	}

	public void start(String optionsString) {
		String[] params = {optionsString};

		try {
			_selenium.start(optionsString);
		}
		catch (Exception e) {
			_logger.errorMessage("start", params, e);
		}
	}

	public void start() {
		String[] params = {};

		try {
			_selenium.start();
		}
		catch (Exception e) {
			_logger.errorMessage("start", params, e);
		}
	}

	public void stop() {
		String[] params = {};

		try {
			_selenium.stop();
		}
		catch (Exception e) {
			_logger.errorMessage("stop", params, e);
		}
	}

	public void submit(String formLocator) {
		String[] params = {formLocator};

		try {
			_selenium.submit(formLocator);
		}
		catch (Exception e) {
			_logger.errorMessage("submit", params, e);
		}
	}

	public void type(String locator, String value) {
		String[] params = {locator, value};

		try {
			_selenium.type(locator, value);
		}
		catch (Exception e) {
			_logger.errorMessage("type", params, e);
		}
	}

	public void typeFrame(String locator, String value) {
		String[] params = {locator, value};

		try {
			_selenium.typeFrame(locator, value);
		}
		catch (Exception e) {
			_logger.errorMessage("typeFrame", params, e);
		}
	}

	public void typeKeys(String locator, String value) {
		String[] params = {locator, value};

		try {
			_selenium.typeKeys(locator, value);
		}
		catch (Exception e) {
			_logger.errorMessage("typeKeys", params, e);
		}
	}

	public void uncheck(String locator) {
		String[] params = {locator};

		try {
			_selenium.uncheck(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("uncheck", params, e);
		}
	}

	public void uploadCommonFile(String locator, String value) {
		String[] params = {locator, value};

		try {
			_selenium.uploadCommonFile(locator, value);
		}
		catch (Exception e) {
			_logger.errorMessage("uploadCommonFile", params, e);
		}
	}

	public void uploadFile(String locator, String value) {
		String[] params = {locator, value};

		try {
			_selenium.uploadFile(locator, value);
		}
		catch (Exception e) {
			_logger.errorMessage("uploadFile", params, e);
		}
	}

	public void uploadTempFile(String locator, String value) {
		String[] params = {locator, value};

		try {
			_selenium.uploadTempFile(locator, value);
		}
		catch (Exception e) {
			_logger.errorMessage("uploadTempFile", params, e);
		}
	}

	public void useXpathLibrary(String libraryName) {
		String[] params = {libraryName};

		try {
			_selenium.useXpathLibrary(libraryName);
		}
		catch (Exception e) {
			_logger.errorMessage("useXpathLibrary", params, e);
		}
	}

	public void waitForCondition(String script, String timeout) {
		String[] params = {script, timeout};

		try {
			_selenium.waitForCondition(script, timeout);
		}
		catch (Exception e) {
			_logger.errorMessage("waitForCondition", params, e);
		}
	}

	public void waitForConfirmation(String pattern) throws Exception {
		String[] params = {pattern};

		try {
			_selenium.waitForConfirmation(pattern);
		}
		catch (Exception e) {
			_logger.errorMessage("waitForConfirmation", params, e);
		}
	}

	public void waitForElementNotPresent(String locator) throws Exception {
		String[] params = {locator};

		try {
			_selenium.waitForElementNotPresent(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("waitForElementNotPresent", params, e);
		}
	}

	public void waitForElementPresent(String locator) throws Exception {
		String[] params = {locator};

		try {
			_selenium.waitForElementPresent(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("waitForElementPresent", params, e);
		}
	}

	public void waitForFrameToLoad(String frameAddress, String timeout) {
		String[] params = {frameAddress, timeout};

		try {
			_selenium.waitForFrameToLoad(frameAddress, timeout);
		}
		catch (Exception e) {
			_logger.errorMessage("waitForFrameToLoad", params, e);
		}
	}

	public void waitForNotPartialText(String locator, String value)
		throws Exception {

		String[] params = {locator, value};

		try {
			_selenium.waitForNotPartialText(locator, value);
		}
		catch (Exception e) {
			_logger.errorMessage("waitForNotPartialText", params, e);
		}
	}

	public void waitForNotSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		String[] params = {selectLocator, pattern};

		try {
			_selenium.waitForNotSelectedLabel(selectLocator, pattern);
		}
		catch (Exception e) {
			_logger.errorMessage("waitForNotSelectedLabel", params, e);
		}
	}

	public void waitForNotText(String locator, String value) throws Exception {
		String[] params = {locator, value};

		try {
			_selenium.waitForNotText(locator, value);
		}
		catch (Exception e) {
			_logger.errorMessage("waitForNotText", params, e);
		}
	}

	public void waitForNotValue(String locator, String value) throws Exception {
		String[] params = {locator, value};

		try {
			_selenium.waitForNotValue(locator, value);
		}
		catch (Exception e) {
			_logger.errorMessage("waitForNotValue", params, e);
		}
	}

	public void waitForNotVisible(String locator) throws Exception {
		String[] params = {locator};

		try {
			_selenium.waitForNotVisible(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("waitForNotVisible", params, e);
		}
	}

	public void waitForPageToLoad(String timeout) {
		String[] params = {timeout};

		try {
			_selenium.waitForPageToLoad(timeout);
		}
		catch (Exception e) {
			_logger.errorMessage("waitForPageToLoad", params, e);
		}
	}

	public void waitForPartialText(String locator, String value)
		throws Exception {

		String[] params = {locator, value};

		try {
			_selenium.waitForPartialText(locator, value);
		}
		catch (Exception e) {
			_logger.errorMessage("waitForPartialText", params, e);
		}
	}

	public void waitForPopUp(String windowID, String timeout) {
		String[] params = {windowID, timeout};

		try {
			_selenium.waitForPopUp(windowID, timeout);
		}
		catch (Exception e) {
			_logger.errorMessage("waitForPopUp", params, e);
		}
	}

	public void waitForSelectedLabel(String selectLocator, String pattern)
		throws Exception {

		String[] params = {selectLocator, pattern};

		try {
			_selenium.waitForSelectedLabel(selectLocator, pattern);
		}
		catch (Exception e) {
			_logger.errorMessage("waitForSelectedLabel", params, e);
		}
	}

	public void waitForText(String locator, String value) throws Exception {
		String[] params = {locator, value};

		try {
			_selenium.waitForText(locator, value);
		}
		catch (Exception e) {
			_logger.errorMessage("waitForText", params, e);
		}
	}

	public void waitForTextNotPresent(String value) throws Exception {
		String[] params = {value};

		try {
			_selenium.waitForTextNotPresent(value);
		}
		catch (Exception e) {
			_logger.errorMessage("waitForTextNotPresent", params, e);
		}
	}

	public void waitForTextPresent(String value) throws Exception {
		String[] params = {value};

		try {
			_selenium.waitForTextPresent(value);
		}
		catch (Exception e) {
			_logger.errorMessage("waitForTextPresent", params, e);
		}
	}

	public void waitForValue(String locator, String value) throws Exception {
		String[] params = {locator, value};

		try {
			_selenium.waitForValue(locator, value);
		}
		catch (Exception e) {
			_logger.errorMessage("waitForValue", params, e);
		}
	}

	public void waitForVisible(String locator) throws Exception {
		String[] params = {locator};

		try {
			_selenium.waitForVisible(locator);
		}
		catch (Exception e) {
			_logger.errorMessage("waitForVisible", params, e);
		}
	}

	public void windowFocus() {
		String[] params = {};

		try {
			_selenium.windowFocus();
		}
		catch (Exception e) {
			_logger.errorMessage("windowFocus", params, e);
		}
	}

	public void windowMaximize() {
		String[] params = {};

		try {
			_selenium.windowMaximize();
		}
		catch (Exception e) {
			_logger.errorMessage("windowMaximize", params, e);
		}
	}

	public void windowMaximizeAndWait() {
		String[] params = {};

		try {
			_selenium.windowMaximizeAndWait();
		}
		catch (Exception e) {
			_logger.errorMessage("windowMaximizeAndWait", params, e);
		}
	}

	private String decideMethodToLog(Throwable t, String[] params) {
		StackTraceElement[] elements = t.getStackTrace();

		String invokedMethod = elements[0].getMethodName();
		String invokerMethod = elements[1].getMethodName();

		int count = 0;

		for (StackTraceElement element : elements) {
			String methodName = element.getMethodName();

			if (methodName.startsWith("test")) {
				break;
			}
			else {
				count++;
			}
		}

		String rootCommand = elements[count - 1].getMethodName();

		String methodToLog = invokedMethod;

		if (rootCommand.contains("waitFor")) {
			methodToLog = rootCommand;
		}

		return methodToLog;
	}

	private Logger _logger;
	private LiferaySelenium _selenium;

}