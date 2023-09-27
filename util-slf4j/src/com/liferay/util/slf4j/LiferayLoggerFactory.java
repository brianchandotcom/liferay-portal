/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.util.slf4j;

import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

/**
 * @author Michael C. Han
 */
public class LiferayLoggerFactory implements ILoggerFactory {

	public LiferayLoggerFactory() {
		ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

		_readLock = readWriteLock.readLock();
		_writeLock = readWriteLock.writeLock();
	}

	@Override
	public Logger getLogger(String name) {
		Logger logger = null;

		_readLock.lock();

		try {
			logger = _loggers.get(name);
		}
		finally {
			_readLock.unlock();
		}

		if (logger == null) {
			_writeLock.lock();

			try {
				logger = new LiferayLoggerAdapter(
					LogFactoryUtil.getLog(name), name);

				_loggers.put(name, logger);
			}
			finally {
				_writeLock.unlock();
			}
		}

		return logger;
	}

	private final Map<String, Logger> _loggers = new HashMap<>();
	private final Lock _readLock;
	private final Lock _writeLock;

}