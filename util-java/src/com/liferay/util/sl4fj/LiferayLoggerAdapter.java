/*
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import java.io.Serializable;

import org.slf4j.Marker;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.spi.LocationAwareLogger;

/**
 * @author Michael C. Han
 */
public class LiferayLoggerAdapter extends MarkerIgnoringBase
	implements LocationAwareLogger, Serializable {

	LiferayLoggerAdapter(Log log) {
		_log = log;
	}

	public void debug(String message) {
		_log.debug(message);
	}

	public void debug(String format, Object arg) {
		if (isDebugEnabled()) {
			String message = MessageFormatter.format(format, arg);

			_log.debug(message);
		}
	}

	public void debug(String format, Object arg1, Object arg2) {
		if (isDebugEnabled()) {
			String message = MessageFormatter.format(format, arg1, arg2);

			_log.debug(message);
		}
	}

	public void debug(String format, Object[] argArray) {
		if (isDebugEnabled()) {
			String message = MessageFormatter.format(format, argArray);

			_log.debug(message);
		}
	}

	public void debug(String message, Throwable t) {
		_log.debug(message, t);
	}

	public void error(String message) {
		_log.error(message);
	}

	public void error(String format, Object arg) {
		if (isErrorEnabled()) {
			String message = MessageFormatter.format(format, arg);

			_log.error(message);
		}
	}

	public void error(String format, Object arg1, Object arg2) {
		if (isErrorEnabled()) {
			String message = MessageFormatter.format(format, arg1, arg2);

			_log.error(message);
		}
	}

	public void error(String format, Object[] argArray) {
		if (isErrorEnabled()) {
			String message = MessageFormatter.format(format, argArray);

			_log.error(message);
		}
	}

	public void error(String message, Throwable t) {
		_log.error(message, t);
	}

	public boolean isDebugEnabled() {
		return _log.isDebugEnabled();
	}

	public boolean isErrorEnabled() {
		return _log.isErrorEnabled();
	}

	public boolean isInfoEnabled() {
		return _log.isInfoEnabled();
	}

	public boolean isTraceEnabled() {
		return _log.isTraceEnabled();
	}

	public boolean isWarnEnabled() {
		return _log.isWarnEnabled();
	}

	public void info(String message) {
		_log.info(message);
	}

	public void info(String format, Object arg) {
		if (isInfoEnabled()) {
			String message = MessageFormatter.format(format, arg);

			_log.info(message);
		}
	}

	public void info(String format, Object arg1, Object arg2) {
		if (isInfoEnabled()) {
			String message = MessageFormatter.format(format, arg1, arg2);

			_log.info(message);
		}
	}

	public void info(String format, Object[] argArray) {
		if (isInfoEnabled()) {
			String message = MessageFormatter.format(format, argArray);

			_log.info(message);
		}
	}

	public void info(String message, Throwable t) {
		_log.info(message, t);
	}

	public void log(
		Marker marker, String fqcn, int level, String message, Throwable t) {
		throw new UnsupportedOperationException();

	}

	public void trace(String message) {
		_log.trace(message);
	}

	public void trace(String format, Object arg) {
		if (isTraceEnabled()) {
			String message = MessageFormatter.format(format, arg);

			_log.trace(message);
		}
	}

	public void trace(String format, Object arg1, Object arg2) {
		if (isTraceEnabled()) {
			String message = MessageFormatter.format(format, arg1, arg2);

			_log.trace(message);
		}
	}

	public void trace(String format, Object[] argArray) {
		if (isTraceEnabled()) {
			String message = MessageFormatter.format(format, argArray);

			_log.trace(message);
		}
	}

	public void trace(String message, Throwable t) {
		_log.trace(message, t);
	}

	public void warn(String message) {
		_log.warn(message);
	}

	public void warn(String format, Object arg) {
		if (isWarnEnabled()) {
			String message = MessageFormatter.format(format, arg);

			_log.warn(message);
		}
	}

	public void warn(String format, Object[] argArray) {
		if (isWarnEnabled()) {
			String message = MessageFormatter.format(format, argArray);

			_log.warn(message);
		}
	}

	public void warn(String format, Object arg1, Object arg2) {
		if (isWarnEnabled()) {
			String message = MessageFormatter.format(format, arg1, arg2);

			_log.warn(message);
		}
	}

	public void warn(String message, Throwable t) {
		_log.warn(message, t);
	}

	private transient Log _log;

}