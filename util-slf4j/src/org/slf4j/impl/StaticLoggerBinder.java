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

package org.slf4j.impl;

import com.liferay.util.slf4j.LiferayLoggerFactory;

import org.slf4j.ILoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

/**
 * @author Michael C. Han
 */
public class StaticLoggerBinder implements LoggerFactoryBinder {

	// To avoid constant folding by the compiler, this field must not be final
	// as required by the SLF4J API

	public static String REQUESTED_API_VERSION = "1.6.99";

	public static final StaticLoggerBinder getSingleton() {
		return _SINGLETON;
	}

	@Override
	public ILoggerFactory getLoggerFactory() {
		return _iLoggerFactory;
	}

	@Override
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

	private final ILoggerFactory _iLoggerFactory;

}