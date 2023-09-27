/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseRetryable<T> implements Retryable<T> {

	public BaseRetryable() {
		this(true, _MAX_RETRIES_DEFAULT, _RETRY_PERIOD_DEFAULT);
	}

	public BaseRetryable(
		boolean exceptionOnFail, int maxRetries, long retryPeriod) {

		_exceptionOnFail = exceptionOnFail;
		this.maxRetries = maxRetries;
		_retryPeriod = retryPeriod;
	}

	@Override
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

				ThreadUtil.sleep(_retryPeriod);

				String retryMessage = getRetryMessage(retryCount);

				if (!StringUtil.isNullOrEmpty(retryMessage) &&
					_log.isWarnEnabled()) {

					_log.warn(retryMessage);
				}
			}
		}
	}

	protected String getRetryMessage(int retryCount) {
		return StringUtil.combine(
			"Retry attempt ", retryCount, " of ", String.valueOf(maxRetries));
	}

	protected int maxRetries;

	private static final int _MAX_RETRIES_DEFAULT = 3;

	private static final long _RETRY_PERIOD_DEFAULT = 1000;

	private static final Log _log = LogFactory.getLog(Retryable.class);

	private boolean _exceptionOnFail;
	private long _retryPeriod;

}