/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package org.slf4j.impl;

import com.liferay.util.sl4fj.LiferayLoggerFactory;

import org.slf4j.ILoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

/**
 * @author Michael C. Han
 */
public class StaticLoggerBinder implements LoggerFactoryBinder {

	public static String REQUESTED_API_VERSION = "1.6.4";

	public static final StaticLoggerBinder getSingleton() {
		return _SINGLETON;
	}

	public ILoggerFactory getLoggerFactory() {
		return _iLoggerFactory;
	}

	public String getLoggerFactoryClassStr() {
		return _LOGGER_FACTORY_CLASS_NAME;
	}

	private StaticLoggerBinder() {
		_iLoggerFactory = new LiferayLoggerFactory();
	}

	private static final String _LOGGER_FACTORY_CLASS_NAME =
		LiferayLoggerFactory.class.getName();

	private static final StaticLoggerBinder _SINGLETON =
		new StaticLoggerBinder();

	private ILoggerFactory _iLoggerFactory;

}