/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public static StaticLoggerBinder getSingleton() {
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
	}

	private static final String _LOGGER_FACTORY_CLASS_NAME =
		LiferayLoggerFactory.class.getName();

	private static final StaticLoggerBinder _SINGLETON =
		new StaticLoggerBinder();

	private final ILoggerFactory _iLoggerFactory = new LiferayLoggerFactory();

}