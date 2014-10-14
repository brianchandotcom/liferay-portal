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

import com.liferay.portalweb.portal.util.RuntimeVariables;

import com.thoughtworks.selenium.Selenium;

/**
 * @author Brian Wing Shun Chan
 */
public class SeleniumWrapper implements Selenium {

	public SeleniumWrapper(Selenium selenium) {
		_selenium = selenium;
	}

	@Override
	public void addCustomRequestHeader(String key, String value) {
		_selenium.addCustomRequestHeader(key, value);
	}

	@Override
	public void addLocationStrategy(
		String strategyName, String functionDefinition) {

		_selenium.addLocationStrategy(strategyName, functionDefinition);
	}

	@Override
	public void addScript(String script, String scriptTagId) {
		_selenium.addScript(script, scriptTagId);
	}

	@Override
	public void addSelection(String locator, String optionLocator) {
		_selenium.addSelection(locator, optionLocator);
	}

	@Override
	public void allowNativeXpath(String allow) {
		_selenium.allowNativeXpath(allow);
	}

	@Override
	public void altKeyDown() {
		_selenium.altKeyDown();
	}

	@Override
	public void altKeyUp() {
		_selenium.altKeyUp();
	}

	@Override
	public void answerOnNextPrompt(String answer) {
		_selenium.answerOnNextPrompt(answer);
	}

	@Override
	public void assignId(String locator, String identifier) {
		_selenium.assignId(locator, identifier);
	}

	@Override
	public void attachFile(String fieldLocator, String fileLocator) {
		_selenium.attachFile(fieldLocator, fileLocator);
	}

	@Override
	public void captureEntirePageScreenshot(String fileName, String kwargs) {
		_selenium.captureEntirePageScreenshot(fileName, kwargs);
	}

	@Override
	public String captureEntirePageScreenshotToString(String kwargs) {
		return _selenium.captureEntirePageScreenshotToString(kwargs);
	}

	@Override
	public String captureNetworkTraffic(String type) {
		return _selenium.captureNetworkTraffic(type);
	}

	@Override
	public void captureScreenshot(String fileName) {
		_selenium.captureScreenshot(fileName);
	}

	@Override
	public String captureScreenshotToString() {
		return _selenium.captureScreenshotToString();
	}

	@Override
	public void check(String locator) {
		_selenium.check(locator);
	}

	@Override
	public void chooseCancelOnNextConfirmation() {
		_selenium.chooseCancelOnNextConfirmation();
	}

	@Override
	public void chooseOkOnNextConfirmation() {
		_selenium.chooseOkOnNextConfirmation();
	}

	@Override
	public void click(String locator) {
		_selenium.click(locator);
	}

	@Override
	public void clickAt(String locator, String coordString) {
		_selenium.clickAt(locator, coordString);
	}

	@Override
	public void close() {
		_selenium.close();
	}

	@Override
	public void contextMenu(String locator) {
		_selenium.contextMenu(locator);
	}

	@Override
	public void contextMenuAt(String locator, String coordString) {
		_selenium.contextMenuAt(locator, coordString);
	}

	@Override
	public void controlKeyDown() {
		_selenium.controlKeyDown();
	}

	@Override
	public void controlKeyUp() {
		_selenium.controlKeyUp();
	}

	@Override
	public void createCookie(String nameValuePair, String optionsString) {
		_selenium.createCookie(nameValuePair, optionsString);
	}

	@Override
	public void deleteAllVisibleCookies() {
		_selenium.deleteAllVisibleCookies();
	}

	@Override
	public void deleteCookie(String name, String optionsString) {
		_selenium.deleteCookie(name, optionsString);
	}

	@Override
	public void deselectPopUp() {
		_selenium.deselectPopUp();
	}

	@Override
	public void doubleClick(String locator) {
		_selenium.doubleClick(locator);
	}

	@Override
	public void doubleClickAt(String locator, String coordString) {
		_selenium.doubleClickAt(locator, coordString);
	}

	@Override
	public void dragAndDrop(String locator, String movementsString) {
		_selenium.dragAndDrop(locator, movementsString);
	}

	@Override
	public void dragAndDropToObject(
		String locatorOfObjectToBeDragged,
		String locatorOfDragDestinationObject) {

		_selenium.dragAndDropToObject(
			locatorOfObjectToBeDragged, locatorOfDragDestinationObject);
	}

	@Override
	public void dragdrop(String locator, String movementsString) {
		_selenium.dragdrop(locator, movementsString);
	}

	@Override
	public void fireEvent(String locator, String eventName) {
		_selenium.fireEvent(locator, eventName);
	}

	@Override
	public void focus(String locator) {
		_selenium.focus(locator);
	}

	@Override
	public String getAlert() {
		return _selenium.getAlert();
	}

	@Override
	public String[] getAllButtons() {
		return _selenium.getAllButtons();
	}

	@Override
	public String[] getAllFields() {
		return _selenium.getAllFields();
	}

	@Override
	public String[] getAllLinks() {
		return _selenium.getAllLinks();
	}

	@Override
	public String[] getAllWindowIds() {
		return _selenium.getAllWindowIds();
	}

	@Override
	public String[] getAllWindowNames() {
		return _selenium.getAllWindowNames();
	}

	@Override
	public String[] getAllWindowTitles() {
		return _selenium.getAllWindowTitles();
	}

	@Override
	public String getAttribute(String attributeLocator) {
		return _selenium.getAttribute(attributeLocator);
	}

	@Override
	public String[] getAttributeFromAllWindows(String attributeName) {
		return _selenium.getAttributeFromAllWindows(attributeName);
	}

	@Override
	public String getBodyText() {
		return _selenium.getBodyText();
	}

	@Override
	public String getConfirmation() {
		return _selenium.getConfirmation();
	}

	@Override
	public String getCookie() {
		return _selenium.getCookie();
	}

	@Override
	public String getCookieByName(String name) {
		return _selenium.getCookieByName(name);
	}

	@Override
	public Number getCssCount(String css) {
		return _selenium.getCssCount(css);
	}

	@Override
	public Number getCursorPosition(String locator) {
		return _selenium.getCursorPosition(locator);
	}

	@Override
	public Number getElementHeight(String locator) {
		return _selenium.getElementHeight(locator);
	}

	@Override
	public Number getElementIndex(String locator) {
		return _selenium.getElementIndex(locator);
	}

	@Override
	public Number getElementPositionLeft(String locator) {
		return _selenium.getElementPositionLeft(locator);
	}

	@Override
	public Number getElementPositionTop(String locator) {
		return _selenium.getElementPositionTop(locator);
	}

	@Override
	public Number getElementWidth(String locator) {
		return _selenium.getElementWidth(locator);
	}

	@Override
	public String getEval(String script) {
		return _selenium.getEval(script);
	}

	@Override
	public String getExpression(String expression) {
		return _selenium.getExpression(expression);
	}

	@Override
	public String getHtmlSource() {
		return _selenium.getHtmlSource();
	}

	@Override
	public String getLocation() {
		return _selenium.getLocation();
	}

	@Override
	public String getLog() {
		return _selenium.getLog();
	}

	@Override
	public Number getMouseSpeed() {
		return _selenium.getMouseSpeed();
	}

	@Override
	public String getPrompt() {
		return _selenium.getPrompt();
	}

	@Override
	public String getSelectedId(String selectLocator) {
		return _selenium.getSelectedId(selectLocator);
	}

	@Override
	public String[] getSelectedIds(String selectLocator) {
		return _selenium.getSelectedIds(selectLocator);
	}

	@Override
	public String getSelectedIndex(String selectLocator) {
		return _selenium.getSelectedIndex(selectLocator);
	}

	@Override
	public String[] getSelectedIndexes(String selectLocator) {
		return _selenium.getSelectedIndexes(selectLocator);
	}

	@Override
	public String getSelectedLabel(String selectLocator) {
		return _selenium.getSelectedLabel(selectLocator);
	}

	@Override
	public String[] getSelectedLabels(String selectLocator) {
		return _selenium.getSelectedLabels(selectLocator);
	}

	@Override
	public String getSelectedValue(String selectLocator) {
		return _selenium.getSelectedValue(selectLocator);
	}

	@Override
	public String[] getSelectedValues(String selectLocator) {
		return _selenium.getSelectedValues(selectLocator);
	}

	@Override
	public String[] getSelectOptions(String selectLocator) {
		return _selenium.getSelectOptions(selectLocator);
	}

	@Override
	public String getSpeed() {
		return _selenium.getSpeed();
	}

	@Override
	public String getTable(String tableCellAddress) {
		return _selenium.getTable(tableCellAddress);
	}

	@Override
	public String getText(String locator) {
		return _selenium.getText(locator);
	}

	@Override
	public String getTitle() {
		return _selenium.getTitle();
	}

	@Override
	public String getValue(String locator) {
		return _selenium.getValue(locator);
	}

	@Override
	public boolean getWhetherThisFrameMatchFrameExpression(
		String currentFrameString, String target) {

		return _selenium.getWhetherThisFrameMatchFrameExpression(
			currentFrameString, target);
	}

	@Override
	public boolean getWhetherThisWindowMatchWindowExpression(
		String currentWindowString, String target) {

		return _selenium.getWhetherThisWindowMatchWindowExpression(
			currentWindowString, target);
	}

	public Selenium getWrappedSelenium() {
		return _selenium;
	}

	@Override
	public Number getXpathCount(String xpath) {
		return _selenium.getXpathCount(xpath);
	}

	@Override
	public void goBack() {
		_selenium.goBack();
	}

	@Override
	public void highlight(String locator) {
		_selenium.highlight(locator);
	}

	@Override
	public void ignoreAttributesWithoutValue(String ignore) {
		_selenium.ignoreAttributesWithoutValue(ignore);
	}

	@Override
	public boolean isAlertPresent() {
		return _selenium.isAlertPresent();
	}

	@Override
	public boolean isChecked(String locator) {
		return _selenium.isChecked(locator);
	}

	@Override
	public boolean isConfirmationPresent() {
		return _selenium.isConfirmationPresent();
	}

	@Override
	public boolean isCookiePresent(String name) {
		return _selenium.isCookiePresent(name);
	}

	@Override
	public boolean isEditable(String locator) {
		return _selenium.isEditable(locator);
	}

	@Override
	public boolean isElementPresent(String locator) {
		return _selenium.isElementPresent(locator);
	}

	@Override
	public boolean isOrdered(String locator1, String locator2) {
		return _selenium.isOrdered(locator1, locator2);
	}

	@Override
	public boolean isPromptPresent() {
		return _selenium.isPromptPresent();
	}

	@Override
	public boolean isSomethingSelected(String selectLocator) {
		return _selenium.isSomethingSelected(selectLocator);
	}

	@Override
	public boolean isTextPresent(String pattern) {
		return _selenium.isTextPresent(pattern);
	}

	@Override
	public boolean isVisible(String locator) {
		return _selenium.isVisible(locator);
	}

	@Override
	public void keyDown(String locator, String keySequence) {
		_selenium.keyDown(locator, keySequence);
	}

	@Override
	public void keyDownNative(String keycode) {
		_selenium.keyDownNative(keycode);
	}

	@Override
	public void keyPress(String locator, String keySequence) {
		_selenium.keyPress(locator, keySequence);
	}

	@Override
	public void keyPressNative(String keycode) {
		_selenium.keyPressNative(keycode);
	}

	@Override
	public void keyUp(String locator, String keySequence) {
		_selenium.keyUp(locator, keySequence);
	}

	@Override
	public void keyUpNative(String keycode) {
		_selenium.keyUpNative(keycode);
	}

	@Override
	public void metaKeyDown() {
		_selenium.metaKeyDown();
	}

	@Override
	public void metaKeyUp() {
		_selenium.metaKeyUp();
	}

	@Override
	public void mouseDown(String locator) {
		_selenium.mouseDown(locator);
	}

	@Override
	public void mouseDownAt(String locator, String coordString) {
		_selenium.mouseDownAt(locator, coordString);
	}

	@Override
	public void mouseDownRight(String locator) {
		_selenium.mouseDownRight(locator);
	}

	@Override
	public void mouseDownRightAt(String locator, String coordString) {
		_selenium.mouseDownRightAt(locator, coordString);
	}

	@Override
	public void mouseMove(String locator) {
		_selenium.mouseMove(locator);
	}

	@Override
	public void mouseMoveAt(String locator, String coordString) {
		_selenium.mouseMoveAt(locator, coordString);
	}

	@Override
	public void mouseOut(String locator) {
		_selenium.mouseOut(locator);
	}

	@Override
	public void mouseOver(String locator) {
		_selenium.mouseOver(locator);
	}

	@Override
	public void mouseUp(String locator) {
		_selenium.mouseUp(locator);
	}

	@Override
	public void mouseUpAt(String locator, String coordString) {
		_selenium.mouseUpAt(locator, coordString);
	}

	@Override
	public void mouseUpRight(String locator) {
		_selenium.mouseUpRight(locator);
	}

	@Override
	public void mouseUpRightAt(String locator, String coordString) {
		_selenium.mouseUpRightAt(locator, coordString);
	}

	@Override
	public void open(String url) {
		url = RuntimeVariables.replace(url);

		_selenium.open(url);
	}

	@Override
	public void open(String url, String ignoreResponseCode) {
		_selenium.open(url, ignoreResponseCode);
	}

	@Override
	public void openWindow(String url, String windowID) {
		_selenium.openWindow(url, windowID);
	}

	@Override
	public void refresh() {
		_selenium.refresh();
	}

	@Override
	public void removeAllSelections(String locator) {
		_selenium.removeAllSelections(locator);
	}

	@Override
	public void removeScript(String scriptTagId) {
		_selenium.removeScript(scriptTagId);
	}

	@Override
	public void removeSelection(String locator, String optionLocator) {
		_selenium.removeSelection(locator, optionLocator);
	}

	@Override
	public String retrieveLastRemoteControlLogs() {
		return _selenium.retrieveLastRemoteControlLogs();
	}

	@Override
	public void rollup(String rollupName, String kwargs) {
		_selenium.rollup(rollupName, kwargs);
	}

	@Override
	public void runScript(String script) {
		_selenium.runScript(script);
	}

	@Override
	public void select(String selectLocator, String optionLocator) {
		_selenium.select(selectLocator, optionLocator);
	}

	@Override
	public void selectFrame(String locator) {
		_selenium.selectFrame(locator);
	}

	@Override
	public void selectPopUp(String windowID) {
		_selenium.selectPopUp(windowID);
	}

	@Override
	public void selectWindow(String windowID) {
		_selenium.selectWindow(windowID);
	}

	@Override
	public void setBrowserLogLevel(String logLevel) {
		_selenium.setBrowserLogLevel(logLevel);
	}

	@Override
	public void setContext(String context) {
		_selenium.setContext(context);
	}

	@Override
	public void setCursorPosition(String locator, String position) {
		_selenium.setCursorPosition(locator, position);
	}

	@Override
	public void setExtensionJs(String extensionJs) {
		_selenium.setExtensionJs(extensionJs);
	}

	@Override
	public void setMouseSpeed(String pixels) {
		_selenium.setMouseSpeed(pixels);
	}

	@Override
	public void setSpeed(String value) {
		_selenium.setSpeed(value);
	}

	@Override
	public void setTimeout(String timeout) {
		_selenium.setTimeout(timeout);
	}

	@Override
	public void shiftKeyDown() {
		_selenium.shiftKeyDown();
	}

	@Override
	public void shiftKeyUp() {
		_selenium.shiftKeyUp();
	}

	@Override
	public void showContextualBanner() {
		_selenium.showContextualBanner();
	}

	@Override
	public void showContextualBanner(String className, String methodName) {
		_selenium.showContextualBanner(className, methodName);
	}

	@Override
	public void shutDownSeleniumServer() {
		_selenium.shutDownSeleniumServer();
	}

	@Override
	public void start() {
		_selenium.start();
	}

	@Override
	public void start(Object optionsObject) {
		_selenium.start(optionsObject);
	}

	@Override
	public void start(String optionsString) {
		_selenium.start(optionsString);
	}

	@Override
	public void stop() {
		_selenium.stop();
	}

	@Override
	public void submit(String formLocator) {
		_selenium.submit(formLocator);
	}

	@Override
	public void type(String locator, String value) {
		_selenium.type(locator, value);
	}

	@Override
	public void typeKeys(String locator, String value) {
		_selenium.typeKeys(locator, value);
	}

	@Override
	public void uncheck(String locator) {
		_selenium.uncheck(locator);
	}

	@Override
	public void useXpathLibrary(String libraryName) {
		_selenium.useXpathLibrary(libraryName);
	}

	@Override
	public void waitForCondition(String script, String timeout) {
		_selenium.waitForCondition(script, timeout);
	}

	@Override
	public void waitForFrameToLoad(String frameAddress, String timeout) {
		_selenium.waitForFrameToLoad(frameAddress, timeout);
	}

	@Override
	public void waitForPageToLoad(String timeout) {
		_selenium.waitForPageToLoad(timeout);
	}

	@Override
	public void waitForPopUp(String windowID, String timeout) {
		_selenium.waitForPopUp(windowID, timeout);
	}

	@Override
	public void windowFocus() {
		_selenium.windowFocus();
	}

	@Override
	public void windowMaximize() {
		_selenium.windowMaximize();
	}

	private final Selenium _selenium;

}