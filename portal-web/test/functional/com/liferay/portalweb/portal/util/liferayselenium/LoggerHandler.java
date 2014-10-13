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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Brian Wing Shun Chan
 */
public class LoggerHandler implements InvocationHandler {

	public LoggerHandler(LiferaySelenium liferaySelenium) {
		_liferaySelenium = liferaySelenium;

		_logger = new Logger(liferaySelenium);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] arguments)
		throws Throwable {

		String methodName = method.getName();

		try {
			if (methodName.equals("getPrimaryTestSuiteName") ||
				methodName.equals("setPrimaryTestSuiteName")) {
			}
			else if (methodName.equals("pauseLoggerCheck")) {
				_logger.pauseLoggerCheck();
			}
			else if (methodName.equals("saveScreenshot")) {
				_logger.logScreenShots();
			}
			else if (methodName.equals("sendActionDescriptionLogger")) {
				_logger.logActionDescription(arguments);
			}
			else if (methodName.equals("sendActionLogger")) {
				_logger.logActionCommand(arguments);
			}
			else if (methodName.equals("sendMacroDescriptionLogger")) {
				_logger.logMacroDescription(arguments);
			}
			else if (methodName.equals("sendLogger")) {
				_logger.send(arguments);
			}
			else if (methodName.equals("sendTestCaseCommandLogger")) {
				_logger.logTestCaseCommand(arguments);
			}
			else if (methodName.equals("sendTestCaseHeaderLogger")) {
				_logger.logTestCaseHeader(arguments);
			}
			else if (methodName.equals("startLogger")) {
				_logger.start();
			}
			else if (methodName.equals("stopLogger")) {
				_logger.stop();
			}
			else {
				_logger.logSeleniumCommand(method, arguments);
			}

			return method.invoke(_liferaySelenium, arguments);
		}
		catch (InvocationTargetException ite) {
			Throwable throwable = ite.getCause();

			if (methodName.equals("stop") || methodName.equals("stopLogger")) {
				System.out.println("Unable to stop " + throwable.getMessage());

				return null;
			}

			_liferaySelenium.saveScreenshotBeforeAction(true);

			_logger.logError(method, arguments, throwable);

			throw throwable;
		}
	}

	private final LiferaySelenium _liferaySelenium;
	private final Logger _logger;

}