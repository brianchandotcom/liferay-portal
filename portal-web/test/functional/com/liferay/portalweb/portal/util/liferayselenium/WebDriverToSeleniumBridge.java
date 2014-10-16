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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portalweb.portal.BaseTestCase;

import com.thoughtworks.selenium.Selenium;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import java.io.StringReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.WrapsDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;

/**
 * @author Brian Wing Shun Chan
 */
public class WebDriverToSeleniumBridge
	extends WebDriverWrapper implements Selenium {

	public WebDriverToSeleniumBridge(WebDriver webDriver) {
		super(webDriver);

		initKeys();
		initKeysSpecialChars();

		WebDriverHelper.setDefaultWindowHandle(webDriver.getWindowHandle());
		WebDriverHelper.setNavigationBarHeight(120);
	}

	@Override
	public void addCustomRequestHeader(String key, String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addLocationStrategy(
		String strategyName, String functionDefinition) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void addScript(String script, String scriptTagId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addSelection(String locator, String optionLocator) {
		Select select = new Select(getWebElement(locator));

		if (optionLocator.startsWith("index=")) {
			select.selectByIndex(
				GetterUtil.getInteger(optionLocator.substring(6)));
		}
		else if (optionLocator.startsWith("label=")) {
			select.selectByVisibleText(optionLocator.substring(6));
		}
		else if (optionLocator.startsWith("value=")) {
			select.selectByValue(optionLocator.substring(6));
		}
		else {
			select.selectByVisibleText(optionLocator);
		}
	}

	@Override
	public void allowNativeXpath(String allow) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void altKeyDown() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void altKeyUp() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void answerOnNextPrompt(String answer) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void assignId(String locator, String identifier) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void attachFile(String fieldLocator, String fileLocator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void captureEntirePageScreenshot(String fileName, String kwargs) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String captureEntirePageScreenshotToString(String kwargs) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String captureNetworkTraffic(String type) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void captureScreenshot(String fileName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String captureScreenshotToString() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void check(String locator) {
		WebElement webElement = getWebElement(locator);

		if (!webElement.isSelected()) {
			webElement.click();
		}
	}

	@Override
	public void chooseCancelOnNextConfirmation() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void chooseOkOnNextConfirmation() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void click(String locator) {
		if (locator.contains("x:")) {
			String url = getHtmlNodeHref(locator);

			open(url);
		}
		else {
			WebDriverHelper.click(this, locator);
		}
	}

	@Override
	public void clickAt(String locator, String coordString) {
		clickAt(locator, coordString, true);
	}

	public void clickAt(
		String locator, String coordString, boolean scrollIntoView) {

		int offsetX = 0;
		int offsetY = 0;

		if (coordString.contains(",")) {
			String[] coords = coordString.split(",");

			offsetX = GetterUtil.getInteger(coords[0]);
			offsetY = GetterUtil.getInteger(coords[1]);
		}

		if ((offsetX == 0) && (offsetY == 0)) {
			click(locator);
		}
		else {
			WebElement bodyWebElement = getWebElement("//body");

			WrapsDriver wrapsDriver = (WrapsDriver)bodyWebElement;

			WebDriver webDriver = wrapsDriver.getWrappedDriver();

			WebDriver.Options options = webDriver.manage();

			WebDriver.Window window = options.window();

			Point windowPoint = window.getPosition();

			WebElement webElement = getWebElement(locator);

			Point webElementPoint = webElement.getLocation();

			int clickDestinationX = 0;
			int clickDestinationY = 0;

			if (scrollIntoView) {
				scrollWebElementIntoView(webElement);

				clickDestinationX =
					windowPoint.getX() + webElementPoint.getX() + offsetX;
				clickDestinationY = windowPoint.getY() + offsetY;
			}
			else {
				clickDestinationX =
					windowPoint.getX() + webElementPoint.getX() + offsetX;
				clickDestinationY =
					windowPoint.getY() + webElementPoint.getY() + offsetY;
			}

			try {
				Robot robot = new Robot();

				robot.mouseMove(clickDestinationX, clickDestinationY);

				robot.mousePress(KeyEvent.BUTTON1_MASK);

				robot.delay(1500);

				robot.mouseRelease(KeyEvent.BUTTON1_MASK);

				robot.delay(1500);
			}
			catch (Exception e) {
			}
		}
	}

	@Override
	public void close() {
		super.close();
	}

	@Override
	public void contextMenu(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void contextMenuAt(String locator, String coordString) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void controlKeyDown() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void controlKeyUp() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void createCookie(String nameValuePair, String optionsString) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteAllVisibleCookies() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteCookie(String name, String optionsString) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deselectPopUp() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void doubleClick(String locator) {
		WebElement webElement = getWebElement(locator);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		actions.doubleClick(webElement);

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void doubleClickAt(String locator, String coordString) {
		WebElement webElement = getWebElement(locator);

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		if (coordString.contains(",")) {
			String[] coords = coordString.split(",");

			int x = GetterUtil.getInteger(coords[0]);
			int y = GetterUtil.getInteger(coords[1]);

			actions.moveToElement(webElement, x, y);

			actions.doubleClick();
		}
		else {
			actions.doubleClick(webElement);
		}

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void dragAndDrop(String locator, String coordString) {
		try {
			int x = WebDriverHelper.getElementPositionCenterX(this, locator);

			x += WebDriverHelper.getFramePositionLeft(this);
			x += WebDriverHelper.getWindowPositionLeft(this);
			x -= WebDriverHelper.getScrollOffsetX(this);

			int y = WebDriverHelper.getElementPositionCenterY(this, locator);

			y += WebDriverHelper.getFramePositionTop(this);
			y += WebDriverHelper.getNavigationBarHeight();
			y += WebDriverHelper.getWindowPositionTop(this);
			y -= WebDriverHelper.getScrollOffsetY(this);

			Robot robot = new Robot();

			robot.mouseMove(x, y);

			robot.delay(1500);

			robot.mousePress(InputEvent.BUTTON1_MASK);

			robot.delay(1500);

			String[] coords = coordString.split(",");

			x += GetterUtil.getInteger(coords[0]);
			y += GetterUtil.getInteger(coords[1]);

			robot.mouseMove(x, y);

			robot.delay(1500);

			robot.mouseRelease(InputEvent.BUTTON1_MASK);
		}
		catch (Exception e) {
		}
	}

	@Override
	public void dragAndDropToObject(
		String locatorOfObjectToBeDragged,
		String locatorOfDragDestinationObject) {

		WebElement objectToBeDraggedWebElement = getWebElement(
			locatorOfObjectToBeDragged);

		WrapsDriver wrapsDriver = (WrapsDriver)objectToBeDraggedWebElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		WebElement dragDestinationObjectWebElement = getWebElement(
			locatorOfDragDestinationObject);

		actions.dragAndDrop(
			objectToBeDraggedWebElement, dragDestinationObjectWebElement);

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void dragdrop(String locator, String movementsString) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void fireEvent(String locator, String eventName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void focus(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getAlert() {
		switchTo();

		WebDriverWait webDriverWait = new WebDriverWait(this, 1);

		Alert alert = webDriverWait.until(ExpectedConditions.alertIsPresent());

		return alert.getText();
	}

	@Override
	public String[] getAllButtons() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String[] getAllFields() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String[] getAllLinks() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String[] getAllWindowIds() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String[] getAllWindowNames() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String[] getAllWindowTitles() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getAttribute(String attributeLocator) {
		return WebDriverHelper.getAttribute(this, attributeLocator);
	}

	@Override
	public String[] getAttributeFromAllWindows(String attributeName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getBodyText() {
		WebElement webElement = findElement(By.tagName("body"));

		return webElement.getText();
	}

	@Override
	public String getConfirmation() {
		switchTo();

		WebDriverWait webDriverWait = new WebDriverWait(this, 1);

		try {
			Alert alert = webDriverWait.until(
				ExpectedConditions.alertIsPresent());

			String confirmation = alert.getText();

			alert.accept();

			return confirmation;
		}
		catch (Exception e) {
			throw new WebDriverException();
		}
	}

	@Override
	public String getCookie() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getCookieByName(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Number getCssCount(String css) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Number getCursorPosition(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Number getElementHeight(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Number getElementIndex(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Number getElementPositionLeft(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Number getElementPositionTop(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Number getElementWidth(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getEval(String script) {
		return WebDriverHelper.getEval(this, script);
	}

	@Override
	public String getExpression(String expression) {
		throw new UnsupportedOperationException();
	}

	public Node getHtmlNode(String locator) {
		try {
			XPathFactory xPathFactory = XPathFactory.newInstance();

			XPath xPath = xPathFactory.newXPath();

			locator = StringUtil.replace(locator, "x:", "");

			XPathExpression xPathExpression = xPath.compile(locator);

			DocumentBuilderFactory documentBuilderFactory =
				DocumentBuilderFactory.newInstance();

			DocumentBuilder documentBuilder =
				documentBuilderFactory.newDocumentBuilder();

			String htmlSource = getHtmlSource();

			htmlSource = htmlSource.substring(htmlSource.indexOf("<html"));

			StringReader stringReader = new StringReader(htmlSource);

			InputSource inputSource = new InputSource(stringReader);

			Document document = documentBuilder.parse(inputSource);

			NodeList nodeList = (NodeList)xPathExpression.evaluate(
				document, XPathConstants.NODESET);

			if (nodeList.getLength() < 1) {
				throw new Exception(locator + " is not present");
			}

			return nodeList.item(0);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	public String getHtmlNodeHref(String locator) {
		Node elementNode = getHtmlNode(locator);

		NamedNodeMap namedNodeMap = elementNode.getAttributes();

		Node attributeNode = namedNodeMap.getNamedItem("href");

		return attributeNode.getTextContent();
	}

	public String getHtmlNodeText(String locator) {
		Node node = getHtmlNode(locator);

		return node.getTextContent();
	}

	@Override
	public String getHtmlSource() {
		return getPageSource();
	}

	@Override
	public String getLocation() {
		return WebDriverHelper.getLocation(this);
	}

	@Override
	public String getLog() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Number getMouseSpeed() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getPrompt() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSelectedId(String selectLocator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String[] getSelectedIds(String selectLocator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSelectedIndex(String selectLocator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String[] getSelectedIndexes(String selectLocator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSelectedLabel(String selectLocator) {
		return getSelectedLabel(selectLocator, null);
	}

	public String getSelectedLabel(String selectLocator, String timeout) {
		try {
			WebElement selectLocatorWebElement = getWebElement(
				selectLocator, timeout);

			Select select = new Select(selectLocatorWebElement);

			WebElement firstSelectedOptionWebElement =
				select.getFirstSelectedOption();

			return firstSelectedOptionWebElement.getText();
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	public String[] getSelectedLabels(String selectLocator) {
		WebElement selectLocatorWebElement = getWebElement(selectLocator);

		Select select = new Select(selectLocatorWebElement);

		List<WebElement> allSelectedOptionsWebElements =
			select.getAllSelectedOptions();

		return StringUtil.split(
			ListUtil.toString(allSelectedOptionsWebElements, "text"));
	}

	@Override
	public String getSelectedValue(String selectLocator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String[] getSelectedValues(String selectLocator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String[] getSelectOptions(String selectLocator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSpeed() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getTable(String tableCellAddress) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getText(String locator) {
		return getText(locator, null);
	}

	public String getText(String locator, String timeout) {
		if (locator.contains("x:")) {
			return getHtmlNodeText(locator);
		}

		return WebDriverHelper.getText(this, locator, timeout);
	}

	@Override
	public String getTitle() {
		return super.getTitle();
	}

	@Override
	public String getValue(String locator) {
		return getValue(locator, null);
	}

	public String getValue(String locator, String timeout) {
		WebElement webElement = getWebElement(locator, timeout);

		if (!webElement.isDisplayed()) {
			scrollWebElementIntoView(webElement);
		}

		return webElement.getAttribute("value");
	}

	@Override
	public boolean getWhetherThisFrameMatchFrameExpression(
		String currentFrameString, String target) {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean getWhetherThisWindowMatchWindowExpression(
		String currentWindowString, String target) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Number getXpathCount(String xPath) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void goBack() {
		WebDriver.Navigation navigation = navigate();

		navigation.back();
	}

	@Override
	public void highlight(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void ignoreAttributesWithoutValue(String ignore) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isAlertPresent() {
		boolean alertPresent = false;

		switchTo();

		try {
			WebDriverWait webDriverWait = new WebDriverWait(this, 1);

			webDriverWait.until(ExpectedConditions.alertIsPresent());

			alertPresent = true;
		}
		catch (Exception e) {
			alertPresent = false;
		}

		return alertPresent;
	}

	@Override
	public boolean isChecked(String locator) {
		WebElement webElement = getWebElement(locator, "1");

		if (!webElement.isDisplayed()) {
			scrollWebElementIntoView(webElement);
		}

		return webElement.isSelected();
	}

	@Override
	public boolean isConfirmationPresent() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCookiePresent(String name) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isEditable(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isElementPresent(String locator) {
		return WebDriverHelper.isElementPresent(this, locator);
	}

	@Override
	public boolean isOrdered(String locator1, String locator2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isPromptPresent() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isSomethingSelected(String selectLocator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isTextPresent(String pattern) {
		WebElement webElement = findElement(By.tagName("body"));

		String text = webElement.getText();

		return text.contains(pattern);
	}

	@Override
	public boolean isVisible(String locator) {
		return WebDriverHelper.isVisible(this, locator);
	}

	@Override
	public void keyDown(String locator, String keySequence) {
		WebElement webElement = getWebElement(locator);

		if (keySequence.startsWith("\\")) {
			int index = GetterUtil.getInteger(keySequence.substring(1));

			Keys keys = _keysArray[index];

			if ((index >= 48) && (index <= 90)) {
				webElement.sendKeys(StringPool.ASCII_TABLE[index]);
			}
			else if ((index == 16) || (index == 17) || (index == 18)) {
				WrapsDriver wrapsDriver = (WrapsDriver)webElement;

				WebDriver webDriver = wrapsDriver.getWrappedDriver();

				Actions actions = new Actions(webDriver);

				actions.keyDown(webElement, keys);

				Action action = actions.build();

				action.perform();
			}
			else {
				webElement.sendKeys(keys);
			}
		}
		else {
			webElement.sendKeys(keySequence);
		}
	}

	@Override
	public void keyDownNative(String keycode) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void keyPress(String locator, String keySequence) {
		WebElement webElement = getWebElement(locator);

		if (keySequence.startsWith("\\")) {
			int index = GetterUtil.getInteger(keySequence.substring(1));

			Keys keys = _keysArray[index];

			if ((index >= 48) && (index <= 90)) {
				webElement.sendKeys(StringPool.ASCII_TABLE[index]);
			}
			else if ((index == 16) || (index == 17) || (index == 18)) {
				WrapsDriver wrapsDriver = (WrapsDriver)webElement;

				WebDriver webDriver = wrapsDriver.getWrappedDriver();

				Actions actions = new Actions(webDriver);

				actions = actions.keyDown(webElement, keys);
				actions = actions.keyUp(webElement, keys);

				Action action = actions.build();

				action.perform();
			}
			else {
				webElement.sendKeys(keys);
			}
		}
		else {
			webElement.sendKeys(keySequence);
		}
	}

	@Override
	public void keyPressNative(String keycode) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void keyUp(String locator, String keySequence) {
		WebElement webElement = getWebElement(locator);

		if (keySequence.startsWith("\\")) {
			int index = GetterUtil.getInteger(keySequence.substring(1));

			Keys keys = _keysArray[index];

			if ((index >= 48) && (index <= 90)) {
				webElement.sendKeys(StringPool.ASCII_TABLE[index]);
			}
			else if ((index == 16) || (index == 17) || (index == 18)) {
				WrapsDriver wrapsDriver = (WrapsDriver)webElement;

				WebDriver webDriver = wrapsDriver.getWrappedDriver();

				Actions actions = new Actions(webDriver);

				actions.keyUp(webElement, keys);

				Action action = actions.build();

				action.perform();
			}
			else {
				webElement.sendKeys(keys);
			}
		}
		else {
			webElement.sendKeys(keySequence);
		}
	}

	@Override
	public void keyUpNative(String keycode) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void metaKeyDown() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void metaKeyUp() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void mouseDown(String locator) {
		WebElement webElement = getWebElement(locator);

		if (!webElement.isDisplayed()) {
			scrollWebElementIntoView(webElement);
		}

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		actions.moveToElement(webElement);

		actions.clickAndHold(webElement);

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void mouseDownAt(String locator, String coordString) {
		WebElement webElement = getWebElement(locator);

		if (!webElement.isDisplayed()) {
			scrollWebElementIntoView(webElement);
		}

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		if (coordString.contains(",")) {
			String[] coords = coordString.split(",");

			int x = GetterUtil.getInteger(coords[0]);
			int y = GetterUtil.getInteger(coords[1]);

			actions.moveToElement(webElement, x, y);

			actions.clickAndHold();
		}
		else {
			actions.moveToElement(webElement);

			actions.clickAndHold(webElement);
		}

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void mouseDownRight(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void mouseDownRightAt(String locator, String coordString) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void mouseMove(String locator) {
		WebElement webElement = getWebElement(locator);

		if (!webElement.isDisplayed()) {
			scrollWebElementIntoView(webElement);
		}

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		actions.moveToElement(webElement);

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void mouseMoveAt(String locator, String coordString) {
		WebElement webElement = getWebElement(locator);

		if (!webElement.isDisplayed()) {
			scrollWebElementIntoView(webElement);
		}

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		if (coordString.contains(",")) {
			String[] coords = coordString.split(",");

			int x = GetterUtil.getInteger(coords[0]);
			int y = GetterUtil.getInteger(coords[1]);

			actions.moveToElement(webElement, x, y);
		}
		else {
			actions.moveToElement(webElement);
		}

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void mouseOut(String locator) {
		WebElement webElement = getWebElement(locator);

		if (!webElement.isDisplayed()) {
			scrollWebElementIntoView(webElement);
		}

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		actions.moveToElement(webElement);
		actions.moveByOffset(10, 10);

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void mouseOver(String locator) {
		WebElement webElement = getWebElement(locator);

		if (!webElement.isDisplayed()) {
			scrollWebElementIntoView(webElement);
		}

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		actions.moveToElement(webElement);

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void mouseUp(String locator) {
		WebElement webElement = getWebElement(locator);

		if (!webElement.isDisplayed()) {
			scrollWebElementIntoView(webElement);
		}

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		actions.release(webElement);

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void mouseUpAt(String locator, String coordString) {
		WebElement webElement = getWebElement(locator);

		if (!webElement.isDisplayed()) {
			scrollWebElementIntoView(webElement);
		}

		WrapsDriver wrapsDriver = (WrapsDriver)webElement;

		WebDriver webDriver = wrapsDriver.getWrappedDriver();

		Actions actions = new Actions(webDriver);

		if (coordString.contains(",")) {
			String[] coords = coordString.split(",");

			int x = GetterUtil.getInteger(coords[0]);
			int y = GetterUtil.getInteger(coords[1]);

			actions.moveToElement(webElement, x, y);
			actions.release();
		}
		else {
			actions.moveToElement(webElement);
			actions.release(webElement);
		}

		Action action = actions.build();

		action.perform();
	}

	@Override
	public void mouseUpRight(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void mouseUpRightAt(String locator, String coordString) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void open(String url) {
		WebDriverHelper.open(this, url);
	}

	@Override
	public void open(String url, String ignoreResponseCode) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void openWindow(String url, String windowID) {
		open(url);
	}

	@Override
	public void refresh() {
		WebDriverHelper.refresh(this);
	}

	@Override
	public void removeAllSelections(String locator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeScript(String scriptTagId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeSelection(String locator, String optionLocator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String retrieveLastRemoteControlLogs() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void rollup(String rollupName, String kwargs) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void runScript(String script) {
		getEval(script);
	}

	@Override
	public void select(String selectLocator, String optionLocator) {
		WebElement webElement = getWebElement(selectLocator);

		Select select = new Select(webElement);

		String label = optionLocator;

		if (optionLocator.startsWith("index=")) {
			String indexString = optionLocator.substring(6);

			int index = GetterUtil.getInteger(indexString);

			select.selectByIndex(index - 1);
		}
		else if (optionLocator.startsWith("value=")) {
			String value = optionLocator.substring(6);

			if (value.startsWith("regexp:")) {
				String regexp = value.substring(7);

				selectByRegexpValue(selectLocator, regexp);
			}
			else {
				List<WebElement> optionWebElements = select.getOptions();

				for (WebElement optionWebElement : optionWebElements) {
					String optionWebElementValue =
						optionWebElement.getAttribute("value");

					if (optionWebElementValue.equals(value)) {
						label = optionWebElement.getText();

						break;
					}
				}

				select.selectByValue(label);
			}
		}
		else {
			if (optionLocator.startsWith("label=")) {
				label = optionLocator.substring(6);
			}

			if (label.startsWith("regexp:")) {
				String regexp = label.substring(7);

				selectByRegexpText(selectLocator, regexp);
			}
			else {
				select.selectByVisibleText(label);
			}
		}
	}

	@Override
	public void selectFrame(String locator) {
		WebDriverHelper.selectFrame(this, locator);
	}

	@Override
	public void selectPopUp(String windowID) {
		Set<String> windowHandles = getWindowHandles();

		if (windowID.equals("") || windowID.equals("null")) {
			String title = getTitle();

			for (String windowHandle : windowHandles) {
				WebDriver.TargetLocator targetLocator = switchTo();

				targetLocator.window(windowHandle);

				if (!title.equals(getTitle())) {
					return;
				}
			}
		}
		else {
			selectWindow(windowID);
		}
	}

	@Override
	public void selectWindow(String windowID) {
		WebDriverHelper.selectWindow(this, windowID);
	}

	@Override
	public void setBrowserLogLevel(String logLevel) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setContext(String context) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCursorPosition(String locator, String position) {
		throw new UnsupportedOperationException();
	}

	public void setDefaultTimeoutImplicit() {
		WebDriverHelper.setDefaultTimeoutImplicit(this);
	}

	@Override
	public void setExtensionJs(String extensionJs) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setMouseSpeed(String pixels) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setSpeed(String value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setTimeout(String timeout) {
	}

	public void setTimeoutImplicit(String timeout) {
		WebDriverHelper.setTimeoutImplicit(this, timeout);
	}

	@Override
	public void shiftKeyDown() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void shiftKeyUp() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void showContextualBanner() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void showContextualBanner(String className, String methodName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void shutDownSeleniumServer() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void start() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void start(Object optionsObject) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void start(String optionsString) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void stop() {
		quit();
	}

	@Override
	public void submit(String formLocator) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void type(String locator, String value) {
		WebDriverHelper.type(this, locator, value);
	}

	@Override
	public void typeKeys(String locator, String value) {
		WebElement webElement = getWebElement(locator);

		if (!webElement.isEnabled()) {
			return;
		}

		if (value.contains("line-number=")) {
			value = value.replaceAll("line-number=\"\\d+\"", "");
		}

		int i = 0;

		Set<Integer> specialCharIndexes = getSpecialCharIndexes(value);

		for (int specialCharIndex : specialCharIndexes) {
			webElement.sendKeys(value.substring(i, specialCharIndex));

			webElement.sendKeys(Keys.ESCAPE);

			String specialChar = String.valueOf(value.charAt(specialCharIndex));

			if (specialChar.equals("-")) {
				webElement.sendKeys(Keys.SUBTRACT);
			}
			else {
				webElement.sendKeys(
					Keys.SHIFT, _keysSpecialChars.get(specialChar));
			}

			i = specialCharIndex + 1;
		}

		webElement.sendKeys(value.substring(i, value.length()));
	}

	@Override
	public void uncheck(String locator) {
		WebElement webElement = getWebElement(locator);

		if (webElement.isSelected()) {
			webElement.click();
		}
	}

	@Override
	public void useXpathLibrary(String libraryName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void waitForCondition(String script, String timeout) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void waitForFrameToLoad(String frameAddress, String timeout) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void waitForPageToLoad(String timeout) {
	}

	@Override
	public void waitForPopUp(String windowID, String timeout) {
		int wait = 0;

		if (timeout.equals("")) {
			wait = 30;
		}
		else {
			wait = GetterUtil.getInteger(timeout) / 1000;
		}

		if (windowID.equals("") || windowID.equals("null")) {
			for (int i = 0; i <= wait; i++) {
				Set<String> windowHandles = getWindowHandles();

				if (windowHandles.size() > 1) {
					return;
				}

				try {
					Thread.sleep(1000);
				}
				catch (Exception e) {
				}
			}
		}
		else {
			String targetWindowTitle = windowID;

			if (targetWindowTitle.startsWith("title=")) {
				targetWindowTitle = targetWindowTitle.substring(6);
			}

			for (int i = 0; i <= wait; i++) {
				for (String windowHandle : getWindowHandles()) {
					WebDriver.TargetLocator targetLocator = switchTo();

					targetLocator.window(windowHandle);

					if (targetWindowTitle.equals(getTitle())) {
						targetLocator.window(
							WebDriverHelper.getDefaultWindowHandle());

						return;
					}
				}

				try {
					Thread.sleep(1000);
				}
				catch (Exception e) {
				}
			}
		}

		BaseTestCase.fail("Unable to find the window ID \"" + windowID + "\"");
	}

	@Override
	public void windowFocus() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void windowMaximize() {
		throw new UnsupportedOperationException();
	}

	protected void acceptConfirmation() {
		WebDriver.TargetLocator targetLocator = switchTo();

		Alert alert = targetLocator.alert();

		alert.accept();
	}

	protected Set<Integer> getSpecialCharIndexes(String value) {
		Set<Integer> specialCharIndexes = new TreeSet<Integer>();

		while (value.contains("-")) {
			specialCharIndexes.add(value.indexOf("-"));

			value = StringUtil.replaceFirst(value, "-", " ");
		}

		for (String specialChar : _keysSpecialChars.keySet()) {
			while (value.contains(specialChar)) {
				specialCharIndexes.add(value.indexOf(specialChar));

				value = StringUtil.replaceFirst(value, specialChar, " ");
			}
		}

		return specialCharIndexes;
	}

	protected WebElement getWebElement(String locator) {
		return WebDriverHelper.getWebElement(this, locator);
	}

	protected WebElement getWebElement(String locator, String timeout) {
		return WebDriverHelper.getWebElement(this, locator, timeout);
	}

	protected List<WebElement> getWebElements(String locator) {
		return WebDriverHelper.getWebElements(this, locator);
	}

	protected List<WebElement> getWebElements(String locator, String timeout) {
		return WebDriverHelper.getWebElements(this, locator, timeout);
	}

	protected void initKeys() {

		// ASCII to WebDriver

		_keysArray[107] = Keys.ADD;
		_keysArray[18] = Keys.ALT;
		_keysArray[40] = Keys.ARROW_DOWN;
		_keysArray[37] = Keys.ARROW_LEFT;
		_keysArray[39] = Keys.ARROW_RIGHT;
		_keysArray[38] = Keys.ARROW_UP;
		_keysArray[8] = Keys.BACK_SPACE;
		//keyTable[] = Keys.CANCEL;
		//keyTable[] = Keys.CLEAR;
		//keyTable[] = Keys.COMMAND;
		_keysArray[17] = Keys.CONTROL;
		_keysArray[110] = Keys.DECIMAL;
		_keysArray[46] = Keys.DELETE;
		_keysArray[111] = Keys.DIVIDE;
		//keyTable[] = Keys.DOWN;
		//keyTable[] = Keys.END;
		_keysArray[13] = Keys.RETURN;
		//keyTable[] = Keys.EQUALS;
		_keysArray[27] = Keys.ESCAPE;
		_keysArray[112] = Keys.F1;
		_keysArray[121] = Keys.F10;
		_keysArray[122] = Keys.F11;
		_keysArray[123] = Keys.F12;
		_keysArray[113] = Keys.F2;
		_keysArray[114] = Keys.F3;
		_keysArray[115] = Keys.F4;
		_keysArray[116] = Keys.F5;
		_keysArray[117] = Keys.F6;
		_keysArray[118] = Keys.F7;
		_keysArray[119] = Keys.F8;
		_keysArray[120] = Keys.F9;
		//keyTable[] = Keys.HELP;
		_keysArray[36] = Keys.HOME;
		_keysArray[45] = Keys.INSERT;
		//keyTable[] = Keys.LEFT;
		//keyTable[] = Keys.LEFT_ALT;
		//keyTable[] = Keys.LEFT_CONTROL;
		//keyTable[] = Keys.LEFT_SHIFT;
		//keyTable[] = Keys.META;
		//keyTable[] = Keys.NULL;
		_keysArray[96] = Keys.NUMPAD0;
		_keysArray[97] = Keys.NUMPAD1;
		_keysArray[98] = Keys.NUMPAD2;
		_keysArray[99] = Keys.NUMPAD3;
		_keysArray[100] = Keys.NUMPAD4;
		_keysArray[101] = Keys.NUMPAD5;
		_keysArray[102] = Keys.NUMPAD6;
		_keysArray[103] = Keys.NUMPAD7;
		_keysArray[104] = Keys.NUMPAD8;
		_keysArray[105] = Keys.NUMPAD9;
		_keysArray[34] = Keys.PAGE_DOWN;
		_keysArray[33] = Keys.PAGE_UP;
		_keysArray[19] = Keys.PAUSE;
		//keyTable[] = Keys.RETURN;
		//keyTable[] = Keys.RIGHT;
		//keyTable[] = Keys.SEMICOLON;
		//keyTable[] = Keys.SEPARATOR;
		_keysArray[16] = Keys.SHIFT;
		_keysArray[32] = Keys.SPACE;
		_keysArray[109] = Keys.SUBTRACT;
		_keysArray[9] = Keys.TAB;
		//keyTable[] = Keys.UP;
	}

	protected void initKeysSpecialChars() {
		_keysSpecialChars.put("!", "1");
		_keysSpecialChars.put("#", "3");
		_keysSpecialChars.put("$", "4");
		_keysSpecialChars.put("%", "5");
		_keysSpecialChars.put("&", "7");
		_keysSpecialChars.put("(", "9");
		_keysSpecialChars.put(")", "0");
		_keysSpecialChars.put("<", ",");
		_keysSpecialChars.put(">", ".");
	}

	protected void scrollWebElementIntoView(WebElement webElement) {
		WebDriverHelper.scrollWebElementIntoView(this, webElement);
	}

	protected void selectByRegexpText(String selectLocator, String regexp) {
		WebElement webElement = getWebElement(selectLocator);

		Select select = new Select(webElement);

		List<WebElement> optionWebElements = select.getOptions();

		Pattern pattern = Pattern.compile(regexp);

		int index = -1;

		for (WebElement optionWebElement : optionWebElements) {
			String optionWebElementText = optionWebElement.getText();

			Matcher matcher = pattern.matcher(optionWebElementText);

			if (matcher.matches()) {
				index = optionWebElements.indexOf(optionWebElement);

				break;
			}
		}

		select.selectByIndex(index);
	}

	protected void selectByRegexpValue(String selectLocator, String regexp) {
		WebElement webElement = getWebElement(selectLocator);

		Select select = new Select(webElement);

		List<WebElement> optionWebElements = select.getOptions();

		Pattern pattern = Pattern.compile(regexp);

		int index = -1;

		for (WebElement optionWebElement : optionWebElements) {
			String optionWebElementValue = optionWebElement.getAttribute(
				"value");

			Matcher matcher = pattern.matcher(optionWebElementValue);

			if (matcher.matches()) {
				index = optionWebElements.indexOf(optionWebElement);

				break;
			}
		}

		select.selectByIndex(index);
	}

	private static Log _log = LogFactoryUtil.getLog(
		WebDriverToSeleniumBridge.class);

	private Keys[] _keysArray = new Keys[128];
	private Map<String, String> _keysSpecialChars =
		new HashMap<String, String>();

}