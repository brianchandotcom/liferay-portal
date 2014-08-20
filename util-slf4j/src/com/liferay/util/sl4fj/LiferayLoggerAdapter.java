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

package com.liferay.util.sl4fj;

import com.liferay.portal.kernel.log.Log;

import java.io.Serializable;

import org.slf4j.Marker;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.spi.LocationAwareLogger;

/**
 * @author Michael C. Han
 */
public class LiferayLoggerAdapter
	extends MarkerIgnoringBase implements LocationAwareLogger, Serializable {

	public LiferayLoggerAdapter(Log log) {
		_log = log;
	}

	@Override
	public void debug(String message) {
		_log.debug(message);
	}

	@Override
	public void debug(String format, Object argument) {
		if (isDebugEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.format(
				format, argument);

			_log.debug(
				formattingTuple.getMessage(), formattingTuple.getThrowable());
		}
	}

	@Override
	public void debug(String format, Object... arguments) {
		if (isDebugEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.arrayFormat(
				format, arguments);

			_log.debug(
				formattingTuple.getMessage(), formattingTuple.getThrowable());
		}
	}

	@Override
	public void debug(String format, Object argument1, Object argument2) {
		if (isDebugEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.format(
				format, argument1, argument2);

			_log.debug(
				formattingTuple.getMessage(), formattingTuple.getThrowable());
		}
	}

	@Override
	public void debug(String message, Throwable t) {
		_log.debug(message, t);
	}

	@Override
	public void error(String message) {
		_log.error(message);
	}

	@Override
	public void error(String format, Object argument) {
		if (isErrorEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.format(
				format, argument);

			_log.error(
				formattingTuple.getMessage(), formattingTuple.getThrowable());
		}
	}

	@Override
	public void error(String format, Object... arguments) {
		if (isErrorEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.arrayFormat(
				format, arguments);

			_log.error(
				formattingTuple.getMessage(), formattingTuple.getThrowable());
		}
	}

	@Override
	public void error(String format, Object argument1, Object argument2) {
		if (isErrorEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.format(
				format, argument1, argument2);

			_log.error(
				formattingTuple.getMessage(), formattingTuple.getThrowable());
		}
	}

	@Override
	public void error(String message, Throwable t) {
		_log.error(message, t);
	}

	@Override
	public void info(String message) {
		_log.info(message);
	}

	@Override
	public void info(String format, Object argument) {
		if (isInfoEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.format(
				format, argument);

			_log.info(
				formattingTuple.getMessage(), formattingTuple.getThrowable());
		}
	}

	@Override
	public void info(String format, Object... arguments) {
		if (isInfoEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.arrayFormat(
				format, arguments);

			_log.info(
				formattingTuple.getMessage(), formattingTuple.getThrowable());
		}
	}

	@Override
	public void info(String format, Object argument1, Object argument2) {
		if (isInfoEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.format(
				format, argument1, argument2);

			_log.info(
				formattingTuple.getMessage(), formattingTuple.getThrowable());
		}
	}

	@Override
	public void info(String message, Throwable t) {
		_log.info(message, t);
	}

	@Override
	public boolean isDebugEnabled() {
		return _log.isDebugEnabled();
	}

	@Override
	public boolean isErrorEnabled() {
		return _log.isErrorEnabled();
	}

	@Override
	public boolean isInfoEnabled() {
		return _log.isInfoEnabled();
	}

	@Override
	public boolean isTraceEnabled() {
		return _log.isTraceEnabled();
	}

	@Override
	public boolean isWarnEnabled() {
		return _log.isWarnEnabled();
	}

	@Override
	public void log(
		Marker marker, String fqcn, int level, String message,
		Object[] arguments, Throwable t) {

		FormattingTuple formattingTuple = MessageFormatter.arrayFormat(
			message, arguments);

		switch (level) {
			case LocationAwareLogger.DEBUG_INT:
				_log.debug(formattingTuple.getMessage(), t);

				break;

			case LocationAwareLogger.ERROR_INT:
				_log.error(formattingTuple.getMessage(), t);

				break;

			case LocationAwareLogger.INFO_INT:
				_log.info(formattingTuple.getMessage(), t);

				break;

			case LocationAwareLogger.TRACE_INT:
				_log.trace(formattingTuple.getMessage(), t);

				break;

			case LocationAwareLogger.WARN_INT:
				_log.warn(formattingTuple.getMessage(), t);

				break;

			default:
				_log.info(formattingTuple.getMessage(), t);
		}
	}

	@Override
	public void trace(String message) {
		_log.trace(message);
	}

	@Override
	public void trace(String format, Object argument) {
		if (isTraceEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.format(
				format, argument);

			_log.trace(
				formattingTuple.getMessage(), formattingTuple.getThrowable());
		}
	}

	@Override
	public void trace(String format, Object... arguments) {
		if (isTraceEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.arrayFormat(
				format, arguments);

			_log.trace(
				formattingTuple.getMessage(), formattingTuple.getThrowable());
		}
	}

	@Override
	public void trace(String format, Object argument1, Object argument2) {
		if (isTraceEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.format(
				format, argument1, argument2);

			_log.trace(
				formattingTuple.getMessage(), formattingTuple.getThrowable());
		}
	}

	@Override
	public void trace(String message, Throwable t) {
		_log.trace(message, t);
	}

	@Override
	public void warn(String message) {
		_log.warn(message);
	}

	@Override
	public void warn(String format, Object argument) {
		if (isWarnEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.format(
				format, argument);

			_log.warn(
				formattingTuple.getMessage(), formattingTuple.getThrowable());
		}
	}

	@Override
	public void warn(String format, Object... arguments) {
		if (isWarnEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.arrayFormat(
				format, arguments);

			_log.warn(
				formattingTuple.getMessage(), formattingTuple.getThrowable());
		}
	}

	@Override
	public void warn(String format, Object argument1, Object argument2) {
		if (isWarnEnabled()) {
			FormattingTuple formattingTuple = MessageFormatter.format(
				format, argument1, argument2);

			_log.warn(
				formattingTuple.getMessage(), formattingTuple.getThrowable());
		}
	}

	@Override
	public void warn(String message, Throwable t) {
		_log.warn(message, t);
	}

	private final transient Log _log;

}