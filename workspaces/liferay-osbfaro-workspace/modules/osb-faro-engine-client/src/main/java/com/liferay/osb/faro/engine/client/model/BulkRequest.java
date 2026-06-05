/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

import java.util.List;

/**
 * @author Matthew Kong
 */
public class BulkRequest {

	public List<Operation> getOperations() {
		return _operations;
	}

	public void setOperations(List<Operation> operations) {
		_operations = operations;
	}

	public static class Operation {

		public Operation() {
		}

		public Operation(Object body, String method, String url) {
			_body = body;
			_method = method;
			_url = url;
		}

		public Object getBody() {
			return _body;
		}

		public String getMethod() {
			return _method;
		}

		public String getUrl() {
			return _url;
		}

		public void setBody(Object body) {
			_body = body;
		}

		public void setMethod(String method) {
			_method = method;
		}

		public void setUrl(String url) {
			_url = url;
		}

		private Object _body;
		private String _method;
		private String _url;

	}

	private List<Operation> _operations;

}