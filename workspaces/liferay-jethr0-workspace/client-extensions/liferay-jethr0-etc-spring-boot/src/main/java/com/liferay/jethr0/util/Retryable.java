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

package com.liferay.jethr0.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Michael Hashimoto
 */
public abstract class Retryable<T> {

	public Retryable() {
		this(true, _MAX_RETRIES_DEFAULT, _RETRY_PERIOD_DEFAULT);
	}

	public Retryable(
		boolean exceptionOnFail, int maxRetries, long retryPeriod) {

		_exceptionOnFail = exceptionOnFail;
		this.maxRetries = maxRetries;
		_retryPeriod = retryPeriod;
	}

	public abstract T execute();

	public final T executeWithRetries() {
		int retryCount = 0;

		while (true) {
			try {
				return execute();
			}
			catch (Exception exception) {
				retryCount++;

				if (_log.isDebugEnabled()) {
					_log.debug("An error has occurred: " + exception);
				}

				if ((maxRetries >= 0) && (retryCount > maxRetries)) {
					if (_exceptionOnFail) {
						throw exception;
					}

					return null;
				}

				sleep(_retryPeriod);

				String retryMessage = getRetryMessage(retryCount);

				if (!StringUtil.isNullOrEmpty(retryMessage) &&
					_log.isWarnEnabled()) {

					_log.warn(retryMessage);
				}
			}
		}
	}

	public void sleep(long duration) {
		try {
			Thread.sleep(duration);
		}
		catch (InterruptedException interruptedException) {
			throw new RuntimeException(interruptedException);
		}
	}

	protected String getRetryMessage(int retryCount) {
		return StringUtil.combine(
			"Retry attempt ", String.valueOf(retryCount), " of ",
			String.valueOf(maxRetries));
	}

	protected int maxRetries;

	private static final int _MAX_RETRIES_DEFAULT = 3;

	private static final long _RETRY_PERIOD_DEFAULT = 1000;

	private static final Log _log = LogFactory.getLog(Retryable.class);

	private boolean _exceptionOnFail;
	private long _retryPeriod;

}