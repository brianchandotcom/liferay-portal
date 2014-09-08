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
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.TestPropsValues;

import java.io.File;

import java.lang.reflect.Method;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import org.apache.commons.lang3.StringEscapeUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @author Brian Wing Shun Chan
 */
public class Logger {

	public Logger(LiferaySelenium liferaySelenium) {
		_liferaySelenium = liferaySelenium;

		WebDriver.Options options = _webDriver.manage();

		WebDriver.Window window = options.window();

		window.setPosition(new Point(1050, 45));
		window.setSize(new Dimension(850, 950));

		_javascriptExecutor = (JavascriptExecutor)_webDriver;

		_javascriptExecutor.executeScript("window.name = 'Log Window';");
	}

	public void logActionCommand(Object[] arguments) {
		StringBundler sb = new StringBundler();

		String command = (String)arguments[0];
		String[] params = (String[])arguments[1];

		sb.append("<li id=\"");
		sb.append(command);
		sb.append("\">");

		sb.append("<ul onclick=\"toggle(event);\">");

		sb.append("<div class=\"status\">");

		sb.append("<div class=\"expand-toggle\" id=\"action");
		sb.append(_actionCount);
		sb.append("\">+</div>");

		sb.append("<div class=\"expand-line");
		sb.append(_actionCount);
		sb.append("\" id=\"test");
		sb.append(_actionCount);
		sb.append("\">");

		sb.append(getActionCommand(command, params));

		sb.append("</a>");
		sb.append("</div>");
		sb.append("</div>");
		sb.append("</ul>");
		sb.append("</li>");

		log("actionCommandLog", sb.toString(), "action");

		sb = new StringBundler();

		sb.append("<div id=\"image");
		sb.append(_actionCount);
		sb.append("\">");

		sb.append("</div>");

		_actionCount++;

		log("descriptionLog", sb.toString(), "screenShot");
	}

	public void logActionDescription(Object[] arguments) throws Exception {
		StringBundler sb = new StringBundler();

		String command = (String)arguments[0];

		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append(_actionStepCount);
		sb.append(". ");
		sb.append(command);

		_actionStepCount++;

		log("descriptionLog", sb.toString(), "descriptionLog");
	}

	public void logError(Method method, Object[] arguments, Throwable throwable)
		throws Exception {

		send("", "fail");

		_errorCount++;

		String thowableMessage = throwable.getMessage();

		StringBundler sb = new StringBundler();

		sb.append("errorCount = window.document.getElementById('errorCount');");
		sb.append("errorCount.innerHTML = '");
		sb.append(_errorCount);
		sb.append("';");
		sb.append("errorList = window.document.getElementById('errorList');");
		sb.append("var newLine = window.document.createElement('div');");
		sb.append("newLine.innerHTML = '");

		String stackTrace = generateStackTrace(throwable);

		stackTrace = StringEscapeUtils.escapeEcmaScript(stackTrace);
		stackTrace = StringUtil.replace(stackTrace, "\\\\n", "\\n");
		stackTrace = StringUtil.replace(stackTrace, "\\\\t", "\\t");

		sb.append(stackTrace);
		sb.append("';");
		sb.append("errorList.appendChild(newLine);");

		_javascriptExecutor.executeScript(sb.toString());

		sb = new StringBundler();

		sb.append("<font color=\"red\">");
		sb.append("Command failure <b>");
		sb.append(method.getName());
		sb.append("</b>");

		if (arguments != null) {
			if (arguments.length == 1) {
				sb.append(" with parameter ");
			}
			else if (arguments.length > 1) {
				sb.append(" with parameters ");
			}

			for (Object argument : arguments) {
				sb.append("<b>");
				sb.append(String.valueOf(argument));
				sb.append("</b> ");
			}
		}

		sb.append(": ");
		sb.append(thowableMessage);

		log("actionCommandLog", sb.toString(), "selenium");

		_screenshotErrorCount++;

		sb = new StringBundler();

		sb.append("<h3>Screenshot of action before failure</h3>");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("<img alt=\"");
		sb.append(_screenshotCount);
		sb.append("\" height=\"450\" src=\"screenshots/");
		sb.append("ScreenshotBeforeAction");
		sb.append(_screenshotErrorCount - 1);
		sb.append(".jpg\" width=\"630\" />");
		sb.append("<br />");

		log("errorLog", sb.toString(), "errorLog");

		_liferaySelenium.saveScreenshot();

		_screenshotCount++;

		sb = new StringBundler();

		sb.append("<h3>Screenshot of action after failure</h3>");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("<img alt=\"");
		sb.append(_screenshotCount);
		sb.append("\" height=\"450\" src=\"screenshots/");
		sb.append(_screenshotCount);
		sb.append(".jpg\" width=\"630\" />");
		sb.append("<br />");

		log("descriptionLog", sb.toString(), "descriptionLog");

		log("errorLog", sb.toString(), "errorLog");

		WebElement webElement = _webDriver.findElement(By.id("pauseError"));

		String webElementText = webElement.getText();

		while (webElementText.equals("Disable Pause After Error")) {
			webElement = _webDriver.findElement(By.id("pauseError"));

			webElementText = webElement.getText();

			Thread.sleep(1000);
		}

		sb = new StringBundler();

		sb.append("Command failure \"");
		sb.append(method.getName());
		sb.append("\"");

		if (arguments != null) {
			if (arguments.length == 1) {
				sb.append(" with parameter ");
			}
			else if (arguments.length > 1) {
				sb.append(" with parameters ");
			}

			for (Object argument : arguments) {
				sb.append("\"");
				sb.append(HtmlUtil.escape(String.valueOf(argument)));
				sb.append("\" ");
			}
		}

		sb.append(": ");
		sb.append(thowableMessage);

		BaseTestCase.fail(sb.toString());
	}

	public void logMacroDescription(Object[] arguments) throws Exception {
		StringBundler sb = new StringBundler();

		String command = (String)arguments[0];

		_actionStepCount = 1;

		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("<b>");
		sb.append(_macroStepCount);
		sb.append(". ");
		sb.append(command);
		sb.append("</b>");

		_macroStepCount++;

		log("descriptionLog", sb.toString(), "descriptionLog");
	}

	public void logScreenShots() throws Exception {
		StringBundler sb = new StringBundler();

		_screenshotCount++;

		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append("<img alt=\"");
		sb.append(_screenshotCount);
		sb.append("\" height=\"450\" src=\"screenshots/");
		sb.append(_screenshotCount);
		sb.append(".jpg\" width=\"630\" />");
		sb.append("<br />");

		log("descriptionLog", sb.toString(), "descriptionLog");
	}

	public void logSeleniumCommand(Method method, Object[] arguments) {
		StringBundler sb = new StringBundler();

		sb.append("Running <b>");
		sb.append(method.getName());
		sb.append("</b>");

		if (arguments != null) {
			if (arguments.length == 1) {
				sb.append(" with parameter ");
			}
			else if (arguments.length > 1) {
				sb.append(" with parameters ");
			}

			for (Object argument : arguments) {
				sb.append("<b>");
				sb.append(HtmlUtil.escape(String.valueOf(argument)));
				sb.append("</b> ");
			}
		}

		log("actionCommandLog", sb.toString(), "selenium");
	}

	public void logTestCaseCommand(Object[] arguments) throws Exception {
		StringBundler sb = new StringBundler();

		String tesCaseCommand = (String)arguments[0];

		sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		sb.append(tesCaseCommand);

		log("actionCommandLog", sb.toString(), "seleniumCommands");
	}

	public void logTestCaseHeader(Object[] arguments) throws Exception {
		StringBundler sb = new StringBundler();

		String tesCaseHeader = (String)arguments[0];

		sb.append("<b>");
		sb.append(tesCaseHeader);
		sb.append("</b>");

		log("actionCommandLog", sb.toString(), "seleniumCommands");

		sb = new StringBundler();

		sb.append("<br />");
		sb.append("<b>");
		sb.append(tesCaseHeader);
		sb.append("</b>");

		log("descriptionLog", sb.toString(), "descriptionLog");
	}

	public void pauseLoggerCheck() throws Exception {
		WebElement webElement = _webDriver.findElement(By.id("pause"));

		String webElementText = webElement.getText();

		while (webElementText.equals("Paused...")) {
			webElement = _webDriver.findElement(By.id("pause"));

			webElementText = webElement.getText();

			Thread.sleep(1000);
		}
	}

	public void send(Object[] arguments) {
		String id = (String)arguments[0];
		String status = (String)arguments[1];

		send(id, status);
	}

	public void send(String id, String status) {
		if (status.equals("pending")) {
			_xPathIdStack.push(id);
		}
		else if (status.equals("start")) {
			_xPathIdStack = new Stack<String>();

			return;
		}

		String xPath = generateXPath(_xPathIdStack);

		List<WebElement> webElements = _webDriver.findElements(By.xpath(xPath));

		if (status.equals("pass")) {
			_xPathIdStack.pop();
		}

		StringBundler sb = new StringBundler();

		sb.append("var element = arguments[0];");
		sb.append("element.className = \"");
		sb.append(status);
		sb.append("\";");

		for (WebElement webElement : webElements) {
			_javascriptExecutor.executeScript(sb.toString(), webElement);
		}
	}

	public void start() {
		String primaryTestSuiteName =
			_liferaySelenium.getPrimaryTestSuiteName();

		String htmlFileName =
			_TEST_BASE_DIR_NAME + "/test/functional-generated/" +
				StringUtil.replace(primaryTestSuiteName, ".", "/") + ".html";

		if (_loggerStarted) {
			return;
		}

		if (FileUtil.exists(htmlFileName)) {
			_webDriver.get("file:///" + htmlFileName);
		}
		else {
			_webDriver.get(
				"file:///" + _TEST_BASE_DIR_NAME + "/test/functional/com/" +
					"liferay/portalweb/portal/util/liferayselenium/" +
						"dependencies/Logger.html");
		}

		_loggerStarted = true;
	}

	public void stop() {
		String primaryTestSuiteName =
			_liferaySelenium.getPrimaryTestSuiteName();

		if (primaryTestSuiteName != null) {
			String content = (String)_javascriptExecutor.executeScript(
				"return window.document.getElementsByTagName('html')[0]." +
					"outerHTML;");

			String fileName =
				_TEST_BASE_DIR_NAME + "/test-results/functional/report.html";

			File file = new File(fileName);

			try {
				FileUtil.write(file, content);
			}
			catch (Exception e) {
			}
		}

		_webDriver.quit();
	}

	protected String generateStackTrace(Throwable throwable) {
		Stack<String> xPathIdStack = (Stack<String>)_xPathIdStack.clone();

		StringBundler sb = new StringBundler();

		sb.append("<p>");

		Queue<String> xPathQueue = new LinkedList<String>();

		while (xPathIdStack.size() > 1) {
			String xPath = generateXPath(xPathIdStack);

			xPathIdStack.pop();

			String commandTag = getLogElementText(
				xPath + "/div/span[@class='tag'][1]");

			if (!commandTag.equals("echo") && !commandTag.equals("execute") &&
				!commandTag.equals("fail")) {

				continue;
			}

			xPathQueue.add(xPath);
		}

		while (xPathQueue.peek() != null) {
			String xPath = xPathQueue.poll();

			String command = getLogElementText(
				xPath + "/div/span[@class='quote'][1]");

			command = StringUtil.replace(command, "\"", "");

			String commandType = getLogElementText(
				xPath + "/div/span[@class='attribute'][1]");

			sb.append("Failed Line: ");

			sb.append(commandType);
			sb.append(": <b>");
			sb.append(command);
			sb.append("</b> ");

			if (xPathQueue.peek() != null) {
				String parentXPath = xPathQueue.peek();

				String parentCommand = getLogElementText(
					parentXPath + "/div/span[@class='quote'][1]");

				parentCommand = StringUtil.replace(parentCommand, "\"", "");

				String parentCommandType = getLogElementText(
					parentXPath + "/div/span[@class='attribute'][1]");

				int pos = parentCommand.indexOf("#");

				sb.append("(from ");
				sb.append(parentCommand.substring(0, pos));
				sb.append(".");
				sb.append(parentCommandType);
				sb.append(") ");
			}

			String lineNumber = getLogElementText(
				xPath + "/div/div[@class='line-number']");

			sb.append("at line ");
			sb.append(lineNumber);
			sb.append("<br />");
		}

		String testCaseCommand = getLogElementText(
			generateXPath(xPathIdStack) + "/div/h3");

		sb.append("in test case command <b>");
		sb.append(testCaseCommand.trim());
		sb.append("</b><br />");
		sb.append("<textarea cols=\"85\" rows=\"7\" wrap=\"off\">");
		sb.append(throwable.getMessage());

		StackTraceElement[] stackTraceElements = throwable.getStackTrace();

		for (StackTraceElement stackTraceElement : stackTraceElements) {
			String className = stackTraceElement.getClassName();

			if (className.startsWith("com.liferay")) {
				sb.append("&#10;&#9;");
				sb.append(stackTraceElement.toString());
			}
		}

		sb.append("</textarea>");
		sb.append("</p>");

		return sb.toString();
	}

	protected String generateXPath(Stack<String> ids) {
		StringBundler sb = new StringBundler();

		sb.append("/");

		for (String id : ids) {
			sb.append("/ul/li[@id='");
			sb.append(id);
			sb.append("']");
		}

		sb.append("/div");

		return sb.toString();
	}

	protected String getActionCommand(String command, String[] params) {
		StringBundler sb = new StringBundler();

		sb.append("Running <b>");
		sb.append(command);
		sb.append("</b>");

		int paramsLength = params.length / 3;

		for (int i = 0; i < paramsLength; i++) {
			String locator = params[i];

			if (Validator.isNotNull(locator)) {
				sb.append(" with locator <b>");
				sb.append(locator);
				sb.append("</b>");
			}

			String locatorKey = params[i + 1];

			if (Validator.isNotNull(locatorKey)) {
				sb.append(" with locator-key <b>");
				sb.append(locatorKey);
				sb.append("</b>");
			}

			String value = params[i + 2];

			if (Validator.isNotNull(value)) {
				sb.append(" value <b>");
				sb.append(HtmlUtil.escape(value));
				sb.append("</b>");
			}
		}

		return sb.toString();
	}

	protected String getLogElementText(String xPath) {
		try {
			WebElement webElement = _webDriver.findElement(By.xpath(xPath));

			return (String)_javascriptExecutor.executeScript(
				"var element = arguments[0]; return element.innerHTML;",
				webElement);
		}
		catch (Exception e) {
			return null;
		}
	}

	protected void log(String log, String message, String type) {
		StringBundler sb = new StringBundler();

		sb.append("logger = window.document.getElementById('");
		sb.append(log);
		sb.append("');");

		if (type.equals("selenium")) {
			if (_actionCount >= _seleniumCount) {
				_seleniumCount++;

				sb.append(
					"var newAction = window.document.createElement('ul');");
				sb.append("newAction.setAttribute('class', 'collapse');");
				sb.append("newAction.setAttribute('id', 'collapseAction");
				sb.append(_actionCount - 1);
				sb.append("'); logger.appendChild(newAction);");
			}

			sb.append("var actionLog = window.document.getElementById('");
			sb.append("collapseAction");
			sb.append(_actionCount - 1);
			sb.append("'); var newLine = window.document.createElement(");
			sb.append("'div'); newLine.setAttribute('class', 'line'); ");
			sb.append("newLine.innerHTML = '");
			sb.append(StringEscapeUtils.escapeEcmaScript(message));
			sb.append("'; actionLog.appendChild(newLine);");
			sb.append("actionLog.scrollTop = logger.scrollHeight;");
		}
		else {
			sb.append("var newLine = window.document.createElement('div');");
			sb.append("newLine.setAttribute('class', 'line');");
			sb.append("newLine.innerHTML = '");
			sb.append(StringEscapeUtils.escapeEcmaScript(message));
			sb.append("'; logger.appendChild(newLine); logger.scrollTop = ");
			sb.append("logger.scrollHeight;");
		}

		_javascriptExecutor.executeScript(sb.toString());
	}

	private static final String _TEST_BASE_DIR_NAME =
		TestPropsValues.TEST_BASE_DIR_NAME;

	private int _actionCount;
	private int _actionStepCount = 1;
	private int _errorCount;
	private JavascriptExecutor _javascriptExecutor;
	private LiferaySelenium _liferaySelenium;
	private boolean _loggerStarted;
	private int _macroStepCount = 1;
	private int _screenshotCount;
	private int _screenshotErrorCount;
	private int _seleniumCount = 1;
	private WebDriver _webDriver = new FirefoxDriver();
	private Stack<String> _xPathIdStack = new Stack<String>();

}