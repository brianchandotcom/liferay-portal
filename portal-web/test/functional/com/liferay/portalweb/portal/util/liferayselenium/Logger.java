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

import java.lang.reflect.Method;

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

	public Logger(String projectDir) {
		_projectDir = projectDir;

		WebDriver.Options options = _webDriver.manage();

		WebDriver.Window window = options.window();

		window.setPosition(new Point(1000, 50));
		window.setSize(new Dimension(600, 700));

		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)_webDriver;

		javascriptExecutor.executeScript("window.name = 'log window';");
	}

	public void logCommand(Method method, Object[] arguments) {
		StringBundler sb = new StringBundler();

		sb.append("Running <b>");
		sb.append(method.getName());
		sb.append("</b> using parameters(s) ");

		if (arguments != null) {
			for (Object argument : arguments) {
				sb.append("<b>");
				sb.append((String)argument);
				sb.append("</b> ");
			}
		}

		_log(sb.toString());
	}

	public void logError(Method method, Object[] arguments) {
		StringBundler logMessage = new StringBundler();

		logMessage.append("<font color=red>");
		logMessage.append("Command failure <b>");
		logMessage.append(method.getName());
		logMessage.append("</b> using parameters(s) ");

		if (arguments != null) {
			for (Object argument : arguments) {
				logMessage.append("<b>");
				logMessage.append((String)argument);
				logMessage.append("</b> ");
			}
		}

		_log(logMessage.toString());

		StringBundler failMessage = new StringBundler();

		failMessage.append("Command failure ");
		failMessage.append(method.getName());
		failMessage.append(" using parameters(s) ");

		if (arguments != null) {
			for (Object argument : arguments) {
				failMessage.append((String)argument);
				failMessage.append(" ");
			}
		}

		BaseTestCase.fail(failMessage.toString());
	}

	public void start() {
		if (_loggerStarted) {
			return;
		}

		_loggerStarted = true;

		_webDriver.get(
			"file:///" + _projectDir +
				"portal-web/test/functional/com/liferay/portalweb/portal/" +
					"util/liferayselenium/dependencies/Logger.html");
	}

	public void stop() {
		_webDriver.quit();
	}

	private void _log(String message) {
		WebDriver.TargetLocator targetLocator = _webDriver.switchTo();

		targetLocator.window("log window");

		StringBundler sb = new StringBundler();

		String formattedMessage = StringEscapeUtils.escapeJava(message);

		formattedMessage = formattedMessage.replace("'", "\\'");

		sb.append("logger = window.document.getElementById('log');");
		sb.append("logger.innerHTML += '");
		sb.append(formattedMessage);
		sb.append("<br /><hr />';");
		sb.append("logger.scrollTop = logger.scrollHeight;");

		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)_webDriver;

		javascriptExecutor.executeScript(sb.toString());
	}

	private boolean _loggerStarted = false;
	private String _projectDir;
	private WebDriver _webDriver = new FirefoxDriver();

}