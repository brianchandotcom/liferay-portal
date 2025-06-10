/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.stream.hub.dto;

import java.io.Serializable;

import java.util.List;

/**
 * @author Mahmoud Hussein Tayem
 */
public class StreamActionListItem implements Serializable {

	public StreamActionListItem(
		String name, String type, List<String> roles, List<String> fields,
		Boolean enableOfflineMessageQueue) {

		_name = name;
		_type = type;
		_roles = roles;
		_fields = fields;
		_enableOfflineMessageQueue = enableOfflineMessageQueue;
	}

	public Boolean getEnableOfflineMessageQueue() {
		return _enableOfflineMessageQueue;
	}

	public List<String> getFields() {
		return _fields;
	}

	public String getName() {
		return _name;
	}

	public List<String> getRoles() {
		return _roles;
	}

	public String getType() {
		return _type;
	}

	public void setEnableOfflineMessageQueue(
		Boolean enableOfflineMessageQueue) {

		_enableOfflineMessageQueue = enableOfflineMessageQueue;
	}

	public void setFields(List<String> fields) {
		_fields = fields;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setRoles(List<String> roles) {
		_roles = roles;
	}

	public void setType(String type) {
		_type = type;
	}

	private Boolean _enableOfflineMessageQueue;
	private List<String> _fields;
	private String _name;
	private List<String> _roles;
	private String _type;

}