/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.taglib.util;

import com.liferay.portal.kernel.servlet.DynamicServletRequest;
import com.liferay.portal.kernel.servlet.taglib.BaseBodyTagSupport;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ParamAndPropertyAncestorTagImpl
	extends BaseBodyTagSupport
	implements ParamAncestorTag, PropertyAncestorTag {

	public void addParam(String name, String value) {
		if (_params == null) {
			_params = new LinkedHashMap<String, String[]>();
		}

		// PLT.26.6

		if ((value == null) || (value.length() == 0)) {
			_params.remove(name);

			if (_removedParameterNames == null) {
				_removedParameterNames = new HashSet<String>();
			}

			_removedParameterNames.add(name);

			return;
		}

		String[] values = _params.get(name);

		if (values == null) {
			values = new String[] {value};
		}
		else {
			String[] newValues = new String[values.length + 1];

			System.arraycopy(values, 0, newValues, 0, values.length);

			newValues[newValues.length - 1] = value;

			values = newValues;
		}

		_params.put(name, values);
	}

	public void addProperty(String name, String value) {
		if (_properties == null) {
			_properties = new LinkedHashMap<String, String[]>();
		}

		String[] values = _properties.get(name);

		if (values == null) {
			values = new String[] {value};
		}
		else {
			String[] newValues = new String[values.length + 1];

			System.arraycopy(values, 0, newValues, 0, values.length);

			newValues[newValues.length - 1] = value;

			values = newValues;
		}

		_properties.put(name, values);
	}

	public void clearParams() {
		if (_params != null) {
			_params.clear();
		}

		if (_removedParameterNames != null) {
			_removedParameterNames.clear();
		}
	}

	public void clearProperties() {
		if (_properties != null) {
			_properties.clear();
		}
	}

	public Map<String, String[]> getParams() {
		return _params;
	}

	public Map<String, String[]> getProperties() {
		return _properties;
	}

	public Set<String> getRemovedParameterNames() {
		return _removedParameterNames;
	}

	public ServletContext getServletContext() {
		if (_servletContext != null) {
			return _servletContext;
		}

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		ServletContext servletContext = (ServletContext)request.getAttribute(
			WebKeys.CTX);

		if (servletContext == null) {
			servletContext = pageContext.getServletContext();
		}

		return servletContext;
	}

	public HttpServletRequest getServletRequest() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		if (_params != null) {
			request = new DynamicServletRequest(request, _params);
		}

		return request;
	}

	public HttpServletResponse getServletResponse() {
		HttpServletResponse response =
			(HttpServletResponse)pageContext.getResponse();

		return response;
	}

	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private Map<String, String[]> _params;
	private Map<String, String[]> _properties;
	private Set<String> _removedParameterNames;
	private ServletContext _servletContext;

}