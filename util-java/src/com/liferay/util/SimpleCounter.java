/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.util;

/**
 * @author Brian Wing Shun Chan
 */
public class SimpleCounter {

	public SimpleCounter() {
		this(_DEFAULT_COUNTER);
	}

	public SimpleCounter(long counter) {
		_counter = counter;
	}

	public synchronized long get() {
		return _counter++;
	}

	public String getString() {
		return String.valueOf(get());
	}

	private static final long _DEFAULT_COUNTER = 1;

	private long _counter;

}