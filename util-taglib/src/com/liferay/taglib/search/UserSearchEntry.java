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
import com.liferay.taglib.servlet.PipingServletResponse;

import java.io.Writer;

import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eudaldo Alonso
 */
public class UserSearchEntry extends TextSearchEntry {

	@Override
	public Object clone() {
		UserSearchEntry userSearchEntry = new UserSearchEntry();

		BeanPropertiesUtil.copyProperties(this, userSearchEntry);

		return userSearchEntry;
	}

	public Date getDate() {
		return _date;
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

	public long getUserId() {
		return _userId;
	}

	public boolean isShowDetails() {
		return _showDetails;
	}

	@Override
	public void print(
			Writer writer, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		httpServletRequest.setAttribute(
			"liferay-ui:search-container-column-user:cssClass", getCssClass());
		httpServletRequest.setAttribute(
			"liferay-ui:search-container-column-user:date", _date);
		httpServletRequest.setAttribute(
			"liferay-ui:search-container-column-user:showDetails",
			_showDetails);
		httpServletRequest.setAttribute(
			"liferay-ui:search-container-column-user:userId", _userId);

		if (_servletContext != null) {
			RequestDispatcher requestDispatcher =
				DirectRequestDispatcherFactoryUtil.getRequestDispatcher(
					_servletContext, _PAGE);

			requestDispatcher.include(
				httpServletRequest,
				new PipingServletResponse(httpServletResponse, writer));
		}
		else {
			RequestDispatcher requestDispatcher =
				httpServletRequest.getRequestDispatcher(_PAGE);

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
	}

	public void setDate(Date date) {
		_date = date;
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

	public void setShowDetails(boolean showDetails) {
		_showDetails = showDetails;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	private static final String _PAGE =
		"/html/taglib/ui/search_container/user.jsp";

	private Date _date;
	private HttpServletRequest _httpServletRequest;
	private HttpServletResponse _httpServletResponse;
	private ServletContext _servletContext;
	private boolean _showDetails = true;
	private long _userId;

}