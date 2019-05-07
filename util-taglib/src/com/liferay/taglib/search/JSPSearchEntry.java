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

package com.liferay.taglib.search;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.servlet.DirectRequestDispatcherFactoryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.io.Writer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class JSPSearchEntry extends SearchEntry {

	@Override
	public Object clone() {
		JSPSearchEntry jspSearchEntry = new JSPSearchEntry();

		BeanPropertiesUtil.copyProperties(this, jspSearchEntry);

		return jspSearchEntry;
	}

	public String getHref() {
		return _href;
	}

	public String getPath() {
		return _path;
	}

	public HttpServletRequest getRequest() {
		return _httpServletRequest;
	}

	public HttpServletResponse getResponse() {
		return _httpServletResponse;
	}

	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public void print(
			Writer writer, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		httpServletRequest.setAttribute(WebKeys.SEARCH_ENTRY_HREF, getHref());

		if (_servletContext != null) {
			RequestDispatcher requestDispatcher =
				DirectRequestDispatcherFactoryUtil.getRequestDispatcher(
					_servletContext, _path);

			requestDispatcher.include(
				_httpServletRequest,
				new PipingServletResponse(httpServletResponse, writer));
		}
		else {
			RequestDispatcher requestDispatcher =
				httpServletRequest.getRequestDispatcher(_path);

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}

		httpServletRequest.removeAttribute(WebKeys.SEARCH_ENTRY_HREF);
	}

	public void setHref(String href) {
		_href = href;
	}

	public void setPath(String path) {
		_path = path;
	}

	public void setRequest(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;
	}

	public void setResponse(HttpServletResponse httpServletResponse) {
		_httpServletResponse = httpServletResponse;
	}

	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private String _href;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private String _path;
	private ServletContext _servletContext;

}