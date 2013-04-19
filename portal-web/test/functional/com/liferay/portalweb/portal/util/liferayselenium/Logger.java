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

		StringBundler code = new StringBundler();

		code.append("window.name = 'log window';");
		code.append("window.document.body.innerHTML");
		code.append(" = '<h3>Webdriver Log</h3><p>';");

		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)_webDriver;

		javascriptExecutor.executeScript(code.toString());
	}

	public void errorMessage(String message) {
		log(message);
		BaseTestCase.fail(message);
	}

	public void errorMessage(String command, String[] params, Exception e) {
		StringBundler message = new StringBundler();

		message.append("<font color=red>");
		message.append("Command <b>");
		message.append(command);
		message.append("</b> ");

		for (String param : params) {
			message.append("<b>");
			message.append(param);
			message.append("</b> ");
		}

		message.append("failed:</font> ");

		String stackTrace = getExceptionString(e);

		String s = message.toString();

		log(s + stackTrace);

		String failMessage = s.replaceAll("\\<.*\\>", "");

		BaseTestCase.fail(failMessage);
	}

	public void logCommand(String command, String[] params) {
		StringBundler message = new StringBundler();

		message.append("Running <b>");
		message.append(command);
		message.append("</b> using parameters(s) ");

		for (String param : params) {
			message.append("<b>");
			message.append(param);
			message.append("</b> ");
		}

		log(message.toString());
	}

	private String getExceptionString(Exception e) {
		StringWriter stackTrace = new StringWriter();

		e.printStackTrace(new PrintWriter(stackTrace));

		String s = stackTrace.toString();

		String[] lines = s.split("\n");

		int count = 0;

		for (String line : lines) {
			if (line.contains("sun.reflect.NativeMethodAccessorImpl")) {
				break;
			}

			count++;
		}

		String failure = lines[0];

		String trace = lines[count - 1].substring(3);

		try {
			String packagePath = trace.split("\\(")[0];

			packagePath = packagePath.trim().replace("\n", "");

			int x = packagePath.lastIndexOf(".");

			packagePath = packagePath.substring(0, x);

			String testClass = trace.split("\\(")[1];

			String lineNum = testClass.split(":")[1];

			lineNum = lineNum.trim().replace(")", "");

			String testName = testClass.split(":")[0];

			StringBundler sb = new StringBundler();

			sb.append(failure);
			sb.append(" - ");
			sb.append(testName);
			sb.append(", line number ");
			sb.append(lineNum);
			sb.append(" - ");
			sb.append(packagePath);

			return sb.toString();
		}
		catch (Exception ex) {
			return trace;
		}
	}

	private void log(String message) {
		WebDriver.TargetLocator targetLocator = _webDriver.switchTo();

		targetLocator.window("log window");

		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)_webDriver;

		StringBundler code = new StringBundler();

		String formattedMessage = StringEscapeUtils.escapeJava(message);

		formattedMessage = formattedMessage.replace("'", "\\'");

		code.append("window.document.body.innerHTML += '");
		code.append(formattedMessage);
		code.append("<br><hr>';");
		code.append("window.scroll(0, document.body.scrollHeight);");

		javascriptExecutor.executeScript(code.toString());
	}

	private static WebDriver _webDriver;

}