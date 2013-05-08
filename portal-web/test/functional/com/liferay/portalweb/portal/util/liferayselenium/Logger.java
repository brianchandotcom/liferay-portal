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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portalweb.portal.BaseTestCase;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.StringEscapeUtils;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @author Brian Wing Shun Chan
 */
public class Logger {

	public Logger() {
		_webDriver = new FirefoxDriver();

		WebDriver.Options options = _webDriver.manage();

		WebDriver.Window window = options.window();

		window.setPosition(new Point(1000, 50));
		window.setSize(new Dimension(600, 700));

		StringBundler sb = new StringBundler();

		sb.append("window.name = 'log window';");
		sb.append("window.document.body.innerHTML");
		sb.append(" = '<h3>Webdriver Log</h3><p>';");

		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)_webDriver;

		javascriptExecutor.executeScript(sb.toString());
	}

	public void logCommand(String command, String[] params) {
		StringBundler sb = new StringBundler();

		sb.append("Running <b>");
		sb.append(command);
		sb.append("</b> using parameters(s) ");

		for (String param : params) {
			sb.append("<b>");
			sb.append(param);
			sb.append("</b> ");
		}

		_log(sb.toString());
	}

	public void logError(String command, String[] params, Exception e) {
		StringBundler sb = new StringBundler();

		sb.append("<font color=red>");
		sb.append("Command <b>");
		sb.append(command);
		sb.append("</b> ");

		for (String param : params) {
			sb.append("<b>");
			sb.append(param);
			sb.append("</b> ");
		}

		sb.append("failed:</font> ");

		String message = sb.toString();

		_log(message + _getExceptionString(e));

		String failMessage = message.replaceAll("\\<.*\\>", "");

		BaseTestCase.fail(failMessage);
	}

	public void stop() {
		_webDriver.quit();
	}

	private String _getExceptionString(Exception e) {
		StringWriter stringWriter = new StringWriter();

		e.printStackTrace(new PrintWriter(stringWriter));

		String stackTrace = stringWriter.toString();

		String[] lines = stackTrace.split("\n");

		int count = 0;

		for (String line : lines) {
			if (line.contains("sun.reflect.NativeMethodAccessorImpl")) {
				break;
			}

			count++;
		}

		String failure = lines[0];

		StringBundler sb = new StringBundler();

		sb.append(failure);

		String trace = lines[count - 1].substring(3);

		try {
			String testClass = trace.split("\\(")[1];

			String testName = testClass.split(":")[0];

			sb.append(" - ");
			sb.append(testName);

			String lineNumber = testClass.split(":")[1];

			lineNumber = lineNumber.replace(")", "");
			lineNumber = lineNumber.trim();

			sb.append(", line number ");
			sb.append(lineNumber);

			String packagePath = trace.split("\\(")[0];

			packagePath = packagePath.replace("\n", "");
			packagePath = packagePath.trim();

			int x = packagePath.lastIndexOf(".");

			packagePath = packagePath.substring(0, x);

			sb.append(" - ");
			sb.append(packagePath);

			return sb.toString();
		}
		catch (Exception ex) {
			return trace;
		}
	}

	private void _log(String message) {
		WebDriver.TargetLocator targetLocator = _webDriver.switchTo();

		targetLocator.window("log window");

		StringBundler sb = new StringBundler();

		String formattedMessage = StringEscapeUtils.escapeJava(message);

		formattedMessage = formattedMessage.replace("'", "\\'");

		sb.append("window.document.body.innerHTML += '");
		sb.append(formattedMessage);
		sb.append("<br /><hr />';");
		sb.append("window.scroll(0, document.body.scrollHeight);");

		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)_webDriver;

		javascriptExecutor.executeScript(sb.toString());
	}

	private WebDriver _webDriver;

}