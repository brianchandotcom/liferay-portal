/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.util.slf4j;

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

		_log.setLogWrapperClassName(LiferayLoggerAdapter.class.getName());
	}

	public LiferayLoggerAdapter(Log log, String name) {
		this(log);

		this.name = name;
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
	public void debug(String message, Throwable throwable) {
		_log.debug(message, throwable);
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
	public void error(String message, Throwable throwable) {
		_log.error(message, throwable);
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
	public void info(String message, Throwable throwable) {
		_log.info(message, throwable);
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
		Object[] arguments, Throwable throwable) {

		FormattingTuple formattingTuple = MessageFormatter.arrayFormat(
			message, arguments);

		if (level == LocationAwareLogger.DEBUG_INT) {
			_log.debug(formattingTuple.getMessage(), throwable);
		}
		else if (level == LocationAwareLogger.ERROR_INT) {
			_log.error(formattingTuple.getMessage(), throwable);
		}
		else if (level == LocationAwareLogger.TRACE_INT) {
			_log.trace(formattingTuple.getMessage(), throwable);
		}
		else if (level == LocationAwareLogger.WARN_INT) {
			_log.warn(formattingTuple.getMessage(), throwable);
		}
		else {
			_log.info(formattingTuple.getMessage(), throwable);
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
	public void trace(String message, Throwable throwable) {
		_log.trace(message, throwable);
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
	public void warn(String message, Throwable throwable) {
		_log.warn(message, throwable);
	}

	private final transient Log _log;

}