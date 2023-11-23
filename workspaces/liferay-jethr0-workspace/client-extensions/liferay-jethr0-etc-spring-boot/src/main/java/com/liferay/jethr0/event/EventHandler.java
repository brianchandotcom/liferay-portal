/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event;

import java.io.IOException;

/**
 * @author Michael Hashimoto
 */
public interface EventHandler {

	public String process() throws InvalidJSONException, IOException;

	public class InvalidJSONException extends Exception {

		public InvalidJSONException() {
		}

		public InvalidJSONException(String message) {
			super(message);
		}

		public InvalidJSONException(String message, Throwable throwable) {
			super(message, throwable);
		}

		public InvalidJSONException(Throwable throwable) {
			super(throwable);
		}

	}

}