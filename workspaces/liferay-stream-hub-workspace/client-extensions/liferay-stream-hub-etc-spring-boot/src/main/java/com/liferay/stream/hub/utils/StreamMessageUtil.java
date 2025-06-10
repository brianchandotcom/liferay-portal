/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.stream.hub.utils;

import com.liferay.stream.hub.dto.StreamMessage;
import com.liferay.stream.hub.types.MessageType;

/**
 * @author Mahmoud Hussein Tayem
 */
public class StreamMessageUtil {

	public static <T> StreamMessage<T> create(
		MessageType type, String name, T data) {

		return new StreamMessage<>(type, name, data);
	}

}