/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.stream.hub.types;

/**
 * @author Mahmoud Hussein Tayem
 */
public enum MessageType {

	Event("Event");

	private MessageType(String value) {
		_value = value;
	}

	private final String _value;

}