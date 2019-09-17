/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.BaseBodyTagSupport;
import com.liferay.taglib.servlet.AutoClosePageContextRegistry;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ParamAndPropertyAncestorTagImpl
	extends BaseBodyTagSupport
	implements ParamAncestorTag, PropertyAncestorTag {

	@Override
	public void addParam(String name, String value) {
		if (Validator.isNull(name)) {
			throw new IllegalArgumentException();
		}

		if (_dynamicServletRequest == null) {
			_dynamicServletRequest = new DynamicServletRequest(getRequest());

			request = _dynamicServletRequest;
		}

		Map<String, String[]> params =
			_dynamicServletRequest.getDynamicParameterMap();

		// PLT.26.6

		if (!_allowEmptyParam && ((value == null) || (value.length() == 0))) {
			params.remove(name);

			if (_removedParameterNames == null) {
				_removedParameterNames = new HashSet<>();
			}

			_removedParameterNames.add(name);

			return;
		}

		String[] values = params.get(name);

		if (!_copyCurrentRenderParameters || (values == null)) {
			values = new String[] {value};
		}
		else {
			String[] newValues = new String[values.length + 1];

			System.arraycopy(values, 0, newValues, 0, values.length);

			newValues[newValues.length - 1] = value;

			values = newValues;
		}

		params.put(name, values);
	}

	@Override
	public void addProperty(String name, String value) {
		if (_properties == null) {
			_properties = new LinkedHashMap<>();
		}

		String[] values = _properties.get(name);

		if (!_copyCurrentRenderParameters || (values == null)) {
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
		if (_dynamicServletRequest != null) {
			Map<String, String[]> params =
				_dynamicServletRequest.getDynamicParameterMap();

			params.clear();

			request = (HttpServletRequest)_dynamicServletRequest.getRequest();

			_dynamicServletRequest = null;
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
		if (_dynamicServletRequest != null) {
			return _dynamicServletRequest.getDynamicParameterMap();
		}

		return null;
	}

	public Map<String, String[]> getProperties() {
		return _properties;
	}

	public Set<String> getRemovedParameterNames() {
		return _removedParameterNames;
	}

	@Override
	public HttpServletRequest getRequest() {
		if (_dynamicServletRequest != null) {
			return _dynamicServletRequest;
		}

		return super.getRequest();
	}

	public boolean isAllowEmptyParam() {
		return _allowEmptyParam;
	}

	@Override
	public void release() {
		super.release();

		request = null;
		servletContext = null;

		_allowEmptyParam = false;
		_copyCurrentRenderParameters = true;
		_properties = null;
		_removedParameterNames = null;
	}

	public void setAllowEmptyParam(boolean allowEmptyParam) {
		_allowEmptyParam = allowEmptyParam;
	}

	public void setCopyCurrentRenderParameters(
		boolean copyCurrentRenderParameters) {

		_copyCurrentRenderParameters = copyCurrentRenderParameters;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)pageContext.getRequest();

		request = httpServletRequest;

		servletContext = (ServletContext)httpServletRequest.getAttribute(
			WebKeys.CTX);

		if (servletContext == null) {
			servletContext = pageContext.getServletContext();
		}

		AutoClosePageContextRegistry.registerCloseCallback(
			pageContext,
			() -> _atomicReferenceFieldUpdater.compareAndSet(
				ParamAndPropertyAncestorTagImpl.this, httpServletRequest,
				null));
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	protected ServletContext getServletContext() {
		return servletContext;
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #getRequest()}
	 */
	@Deprecated
	protected volatile HttpServletRequest request;

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #getServletContext()}
	 */
	@Deprecated
	protected ServletContext servletContext;

	private static final AtomicReferenceFieldUpdater
		_atomicReferenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(
			ParamAndPropertyAncestorTagImpl.class, HttpServletRequest.class,
			"request");

	private boolean _allowEmptyParam;
	private boolean _copyCurrentRenderParameters = true;
	private DynamicServletRequest _dynamicServletRequest;
	private Map<String, String[]> _properties;
	private Set<String> _removedParameterNames;

}