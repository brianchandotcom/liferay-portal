/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.pubsub;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Amos Fong
 * @author Kyle Bischof
 */
public class Message {

	public Message(
		Map<String, String> attributes, String payload, String topic) {

		setAttributes(attributes);
		setPayload(payload);
		setTopic(topic);
	}

	public String get(String key) {
		if (_attributes == null) {
			return null;
		}

		return _attributes.get(key);
	}

	public Map<String, String> getAttributes() {
		if (_attributes == null) {
			return Collections.emptyMap();
		}

		return Collections.unmodifiableMap(_attributes);
	}

	public String getPayload() {
		return _payload;
	}

	public String getTopic() {
		return _topic;
	}

	public void put(String key, String value) {
		if (_attributes == null) {
			_attributes = new HashMap<>();
		}

		_attributes.put(key, value);
	}

	public void setAttributes(Map<String, String> attributes) {
		if (attributes == null) {
			_attributes = null;
		}
		else {
			_attributes = new HashMap<>(attributes);
		}
	}

	public void setPayload(String payload) {
		_payload = payload;
	}

	public void setTopic(String topic) {
		_topic = topic;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{topic=");
		sb.append(_topic);
		sb.append(", attributes=");
		sb.append(_attributes);
		sb.append(", payload=");
		sb.append(_payload);
		sb.append("}");

		return sb.toString();
	}

	private Map<String, String> _attributes;
	private String _payload;
	private String _topic;

}