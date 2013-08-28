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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portalweb.portal.BaseTestCase;

import java.io.File;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;
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

		_javascriptExecutor = (JavascriptExecutor)_webDriver;

		WebDriver.Options options = _webDriver.manage();

		WebDriver.Window window = options.window();

		window.setPosition(new Point(1000, 50));
		window.setSize(new Dimension(650, 850));

		_javascriptExecutor.executeScript("window.name = 'Log Window';");
	}

	public void logCommand(Method method, Object[] arguments) {
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
				sb.append(String.valueOf(argument));
				sb.append("</b> ");
			}
		}

		log(sb.toString());
	}

	public void logError(Method method, Object[] arguments, String message) {
		StringBundler display = new StringBundler();

		display.append("<p>");

		display.append(generateStackTrace());

		_errorCount++;

		StringBundler sb = new StringBundler();

		sb.append("errorCount = window.document.getElementById('errorCount');");
		sb.append("errorCount.innerHTML = '");
		sb.append(_errorCount);
		sb.append("';");
		sb.append("errorList = window.document.getElementById('errorList');");
		sb.append("var newLine = window.document.createElement('div');");
		sb.append("newLine.innerHTML = '");

		String formattedMessage = display.toString();

		formattedMessage = formattedMessage.replace("'", "\\'");

		sb.append(formattedMessage);
		sb.append("';");
		sb.append("errorList.appendChild(newLine);");

		_javascriptExecutor.executeScript(sb.toString());

		send("", "fail");

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
		sb.append(message);

		log(sb.toString());

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
				sb.append(String.valueOf(argument));
				sb.append("\" ");
			}
		}

		sb.append(": ");
		sb.append(message);

		BaseTestCase.fail(sb.toString());
	}

	public void send(Object[] arguments) {
		String id = (String)arguments[0];
		String status = (String)arguments[1];

		send(id, status);
	}

	public void send(String id, String status) {
		if (status.equals("pending")) {
			_xpathIdStack.push(id);
		}
		else if (status.equals("start")) {
			_xpathIdStack = new Stack<String>();

			return;
		}

		String xpath = generateXpath(_xpathIdStack);

		List<WebElement> webElements = _webDriver.findElements(By.xpath(xpath));

		if (status.equals("pass")) {
			_xpathIdStack.pop();
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
			"portal-web/test/functional-generated/" +
				StringUtil.replace(primaryTestSuiteName, ".", "/") + ".html";

		if (_loggerStarted) {
			return;
		}

		if (FileUtil.exists(_liferaySelenium.getProjectDir() + htmlFileName)) {
			_webDriver.get(
				"file:///" + _liferaySelenium.getProjectDir() + htmlFileName);
		}
		else {
			_webDriver.get(
				"file:///" + _liferaySelenium.getProjectDir() +
					"portal-web/test/functional/com/liferay/portalweb/portal/" +
						"util/liferayselenium/dependencies/Logger.html");
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
				_liferaySelenium.getProjectDir() +
					"portal-web\\test-results\\functional\\TEST-" +
						primaryTestSuiteName + ".html";

			File file = new File(fileName);

			try {
				FileUtil.write(file, content);
			}
			catch (Exception e) {
			}
		}

		_webDriver.quit();
	}

	protected String generateStackTrace() {
		List<String> xpaths = new ArrayList<String>();

		Stack<String> ids = (Stack)_xpathIdStack.clone();

		String failedLineXpath = generateXpath(ids);

		xpaths.add(failedLineXpath);

		ids.pop();

		int numIds = _xpathIdStack.size() - 1;

		for (int i = 2; i < numIds; i++) {
			Stack<String> parentIds = (Stack)ids.clone();

			for (int j = 2; j < i; j++) {
				parentIds.pop();
			}

			xpaths.add(generateXpath(parentIds) + "/div");
		}

		List<String[]> lineNumbers = new ArrayList<String[]>();

		for (String xpath : xpaths) {
			String failedLine = getLineNumber(xpath);

			String failedCommand = getCommandName(xpath);

			if (Validator.isNotNull(failedLine) &&
				Validator.isNotNull(failedCommand)) {

				failedCommand = failedCommand.replace("\"", "");

				lineNumbers.add(new String[] {failedCommand, failedLine});
			}
		}

		StringBundler sb = new StringBundler();

		for (int i = 0; i < (lineNumbers.size() - 1); i++) {
			String[] currentLine = lineNumbers.get(i);

			String[] parentLine = lineNumbers.get(i + 1);

			String currentCommand = currentLine[0];

			String currentLineNumber = currentLine[1];

			String parentCommand = parentLine[0];

			int x = parentCommand.indexOf("#");

			String parentFile = parentCommand.substring(0, x) + ".macro";

			sb.append("Failed Line: <b>");
			sb.append(currentCommand);
			sb.append("</b> (");
			sb.append(parentFile);
			sb.append(":");
			sb.append(currentLineNumber);
			sb.append(")<br />");
		}

		int length = lineNumbers.size();

		String[] finalLine = lineNumbers.get(length - 1);

		String finalCommand = finalLine[0];

		String finalLineNumber = finalLine[1];

		sb.append("Failed Line: <b>");
		sb.append(finalCommand);
		sb.append("</b> (");

		while (ids.size() > 1) {
			ids.pop();
		}

		String testCaseCommandXpath = generateXpath(ids) + "/div/h3";

		String testCaseCommand = getLogElementText(testCaseCommandXpath);;

		String testClassName = getLogElementText("//h2");

		int x = testClassName.lastIndexOf(".");

		String testName = testClassName.substring(x + 1);

		if (testName.endsWith("TestCase")) {
			sb.append(testName.replace("TestCase", "") + ".testcase");
		}
		else if (testName.endsWith("TestSuite")) {
			sb.append(testName.replace("TestSuite", "") + ".testsuite");
		}

		sb.append(":");
		sb.append(finalLineNumber);
		sb.append(")<br />");
		sb.append("in command ");
		sb.append(testCaseCommand.trim());

		Throwable throwable = new Throwable();

		StackTraceElement[] stackTraceElements = throwable.getStackTrace();

		StringBundler exception = new StringBundler();

		exception.append("<br /><textarea cols='85' rows='7'>");

		for (StackTraceElement stackTraceElement : stackTraceElements) {
			String className = stackTraceElement.getClassName();

			if (className.startsWith("com.liferay")) {
				exception.append(stackTraceElement.toString() + "\\n");
			}
		}

		exception.append("</textarea>");

		sb.append(exception.toString());

		sb.append("</p>");

		return sb.toString();
	}

	protected String generateXpath(Stack<String> ids) {
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

	protected String getCommandName(String xpath) {
		return getLogElementText(xpath + "/span[@class='quote'][1]");
	}

	protected String getCurrentCommandName() {
		String xpath = generateXpath(_xpathIdStack);

		return getCommandName(xpath);
	}

	protected String getCurrentLineNumber() {
		String xpath = generateXpath(_xpathIdStack);

		return getLineNumber(xpath);
	}

	protected String getLineNumber(String xpath) {
		return getLogElementText(xpath + "/div[@class='line-number']");
	}

	protected String getLogElementText(String xpath) {
		try {
			WebElement webElement = _webDriver.findElement(By.xpath(xpath));

			StringBundler sb = new StringBundler();

			sb.append("var element = arguments[0];");
			sb.append("return element.innerHTML;");

			return (String)_javascriptExecutor.executeScript(
				sb.toString(), webElement);
		}
		catch (Exception e) {
			return null;
		}
	}

	protected String getParentCommandName() {
		Stack<String> ids = (Stack)_xpathIdStack.clone();

		while (ids.size() > 3) {
			ids.pop();
		}

		String xpath = generateXpath(ids);

		return getCommandName(xpath);
	}

	protected String getParentLineNumber() {
		Stack<String> ids = (Stack)_xpathIdStack.clone();

		while (ids.size() > 3) {
			ids.pop();
		}

		String xpath = generateXpath(ids);

		return getLineNumber(xpath);
	}

	protected void log(String message) {
		StringBundler sb = new StringBundler();

		String formattedMessage = StringEscapeUtils.escapeJava(message);

		formattedMessage = formattedMessage.replace("'", "\\'");

		sb.append("logger = window.document.getElementById('log');");
		sb.append("var newLine = window.document.createElement('div');");
		sb.append("newLine.setAttribute('class', 'line');");
		sb.append("newLine.innerHTML = '");
		sb.append(formattedMessage);
		sb.append("';");
		sb.append("logger.appendChild(newLine);");
		sb.append("logger.scrollTop = logger.scrollHeight;");

		_javascriptExecutor.executeScript(sb.toString());
	}

	private int _errorCount = 0;
	private JavascriptExecutor _javascriptExecutor;
	private LiferaySelenium _liferaySelenium;
	private boolean _loggerStarted;
	private WebDriver _webDriver = new FirefoxDriver();
	private Stack<String> _xpathIdStack = new Stack<String>();

}