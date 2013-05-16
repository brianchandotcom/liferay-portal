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
import com.liferay.portalweb.portal.BaseTestCase;

import java.io.File;

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

	public Logger(LiferaySelenium liferaySelenium) {
		_liferaySelenium = liferaySelenium;

		WebDriver.Options options = _webDriver.manage();

		WebDriver.Window window = options.window();

		window.setPosition(new Point(1000, 50));
		window.setSize(new Dimension(600, 700));

		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)_webDriver;

		javascriptExecutor.executeScript("window.name = 'Log Window';");

		_webDriver.get(
			"file:///" + _liferaySelenium.getProjectDir() +
				"portal-web/test/functional/com/liferay/portalweb/portal/" +
					"util/liferayselenium/dependencies/Logger.html");
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

	public void logError(Method method, Object[] arguments) {
		StringBundler sb = new StringBundler();

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

		log(sb.toString());

		sb = new StringBundler();

		sb.append("Command failure ");
		sb.append(method.getName());

		if (arguments != null) {
			if (arguments.length == 1) {
				sb.append(" with parameter ");
			}
			else if (arguments.length > 1) {
				sb.append(" with parameters ");
			}

			for (Object argument : arguments) {
				sb.append(String.valueOf(argument));
				sb.append(" ");
			}
		}

		BaseTestCase.fail(sb.toString());
	}

	public void stop() {
		String primaryTestSuiteName =
			_liferaySelenium.getPrimaryTestSuiteName();

		if (primaryTestSuiteName != null) {
			JavascriptExecutor javascriptExecutor =
				(JavascriptExecutor)_webDriver;

			String content = (String)javascriptExecutor.executeScript(
				"return window.document.getElementsByTagName('html')[0]." +
					"outerHTML;");

			File file = new File(
				_liferaySelenium.getProjectDir() +
					"portal-web\\test-results\\functional\\TEST-" +
					primaryTestSuiteName + ".html");

			try {
				FileUtil.write(file, content);
			}
			catch (Exception e) {
			}
		}

		_webDriver.quit();
	}

	protected void log(String message) {
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor)_webDriver;

		StringBundler sb = new StringBundler();

		String formattedMessage = StringEscapeUtils.escapeJava(message);

		formattedMessage = formattedMessage.replace("'", "\\'");

		sb.append("logger = window.document.getElementById('log');");
		sb.append("logger.innerHTML += '");
		sb.append(formattedMessage);
		sb.append("<br /><hr />';");
		sb.append("logger.scrollTop = logger.scrollHeight;");

		javascriptExecutor.executeScript(sb.toString());
	}

	private LiferaySelenium _liferaySelenium;
	private boolean _loggerStarted;
	private WebDriver _webDriver = new FirefoxDriver();

}