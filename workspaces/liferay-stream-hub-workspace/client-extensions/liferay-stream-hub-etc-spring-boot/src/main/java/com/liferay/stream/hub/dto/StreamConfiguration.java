/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.stream.hub.dto;

import java.io.Serializable;

/**
 * @author Mahmoud Hussein Tayem
 */
public class StreamConfiguration implements Serializable {

	public String getConfiguration() {
		return _configuration;
	}

	public String getName() {
		return _name;
	}

	public String getObjectDefinitionId() {
		return _objectDefinitionId;
	}

	public void setConfiguration(String configuration) {
		_configuration = configuration;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setObjectDefinitionId(String objectDefinitionId) {
		_objectDefinitionId = objectDefinitionId;
	}

	private String _configuration;
	private String _name;
	private String _objectDefinitionId;

}