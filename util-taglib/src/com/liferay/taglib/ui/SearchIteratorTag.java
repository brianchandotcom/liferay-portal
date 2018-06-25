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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.dao.search.ResultRowSplitter;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchIteratorTag<R> extends SearchPaginatorTag<R> {

	public static final String DEFAULT_DISPLAY_STYLE = "list";

	public String getContainerCssClass() {
		return _containerCssClass;
	}

	public String getDisplayStyle() {
		return _displayStyle;
	}

	public ResultRowSplitter getResultRowSplitter() {
		return _resultRowSplitter;
	}

	public void setContainerCssClass(String containerCssClass) {
		_containerCssClass = containerCssClass;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	@Override
	public void setMarkupView(String markupView) {
		_markupView = markupView;
	}

	public void setPaginate(boolean paginate) {
		_paginate = paginate;
	}

	public void setResultRowSplitter(ResultRowSplitter resultRowSplitter) {
		_resultRowSplitter = resultRowSplitter;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_containerCssClass = null;
		_displayStyle = DEFAULT_DISPLAY_STYLE;
		_markupView = null;
		_paginate = true;
		_resultRowSplitter = null;
	}

	@Override
	protected String getPage() {
		String containerCssClass = _containerCssClass;
		String displayStyle = _displayStyle;

		if (Validator.isNull(displayStyle)) {
			displayStyle = DEFAULT_DISPLAY_STYLE;
		}

		if (Validator.isNotNull(_markupView)) {
			return StringBundler.concat(
				"/html/taglib/ui/search_iterator/", _markupView, "/",
				displayStyle, ".jsp");
		}

		return "/html/taglib/ui/search_iterator/" + displayStyle + ".jsp";
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		super.setAttributes(request);

		request.setAttribute(
			"liferay-ui:search-iterator:containerCssClass", getContainerCssClass());
		request.setAttribute(
			"liferay-ui:search-iterator:displayStyle", getDisplayStyle());
		request.setAttribute(
			"liferay-ui:search-iterator:markupView", _markupView);
		request.setAttribute(
			"liferay-ui:search-iterator:paginate", String.valueOf(_paginate));
		request.setAttribute(
			"liferay-ui:search-iterator:resultRowSplitter", _resultRowSplitter);
	}

	private String _containerCssClass;
	private String _displayStyle = DEFAULT_DISPLAY_STYLE;
	private String _markupView;
	private boolean _paginate = true;
	private ResultRowSplitter _resultRowSplitter;

}