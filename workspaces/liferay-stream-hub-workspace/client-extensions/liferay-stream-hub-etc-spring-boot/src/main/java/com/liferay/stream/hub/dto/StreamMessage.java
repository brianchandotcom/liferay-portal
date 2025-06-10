/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.stream.hub.dto;

import com.liferay.stream.hub.types.MessageType;

import java.io.Serializable;

/**
 * @author Mahmoud Hussein Tayem
 */
public class StreamMessage<T> implements Serializable {

	public StreamMessage(MessageType type, String name, T data) {
		_type = type;
		_name = name;
		_data = data;
	}

	public T getData() {
		return _data;
	}

	public String getName() {
		return _name;
	}

	public MessageType getType() {
		return _type;
	}

	public void setData(T data) {
		_data = data;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setType(MessageType type) {
		_type = type;
	}

	private T _data;
	private String _name;
	private MessageType _type;

}