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

	public Object invoke(Object proxy, Method method, Object[] arguments)
		throws Throwable {

		String methodName = method.getName();

		try {
			if (methodName.equals("stopLogger")) {
				_logger.stop();
			}
			else {
				_logger.logCommand(method, arguments);
			}

			return method.invoke(_liferaySelenium, arguments);
		}
		catch (InvocationTargetException ite) {
			_logger.logError(method, arguments);

			throw ite.getTargetException();
		}
	}

	private LiferaySelenium _liferaySelenium;
	private Logger _logger;

}