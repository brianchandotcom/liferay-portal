/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.graphql.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

/**
 * @author Leslie Wong
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GraphQLError {

	public Map<String, Object> getExtensions() {
		return _extensions;
	}

	public String getMessage() {
		return _message;
	}

	public List<Object> getPath() {
		return _path;
	}

	public void setExtensions(Map<String, Object> extensions) {
		_extensions = extensions;
	}

	public void setMessage(String message) {
		_message = message;
	}

	public void setPath(List<Object> path) {
		_path = path;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("{message=");
		sb.append(_message);

		if (_path != null) {
			sb.append(", path=");
			sb.append(_path);
		}

		sb.append("}");

		return sb.toString();
	}

	private Map<String, Object> _extensions;
	private String _message;
	private List<Object> _path;

}