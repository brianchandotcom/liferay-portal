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

package com.liferay.util.sl4fj;

import com.liferay.portal.kernel.log.Log;
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
				Log log = LogFactoryUtil.getLog(name);

				logger = new LiferayLoggerAdapter(log);

				_loggers.put(name, logger);
			}
			finally {
				_writeLock.unlock();
			}

		}

		return logger;
	}

	private Map<String, Logger> _loggers = new HashMap<String, Logger>();
	private Lock _readLock;
	private Lock _writeLock;

}