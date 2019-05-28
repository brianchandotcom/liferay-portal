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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.search.ResultRowSplitter;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchIteratorTag<R> extends SearchPaginatorTag<R> {

	public static final String DEFAULT_DISPLAY_STYLE = "list";

	public String getDisplayStyle() {
		return _displayStyle;
	}

	public String getMarkupView() {
		return _markupView;
	}

	public ResultRowSplitter getResultRowSplitter() {
		return _resultRowSplitter;
	}

	public String getSearchResultCssClass() {
		return _searchResultCssClass;
	}

	public boolean isFixedHeader() {
		return _fixedHeader;
	}

	public boolean isPaginate() {
		return _paginate;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setFixedHeader(boolean fixedHeader) {
		_fixedHeader = fixedHeader;
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

	public void setSearchResultCssClass(String searchResultCssClass) {
		_searchResultCssClass = searchResultCssClass;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_displayStyle = DEFAULT_DISPLAY_STYLE;
		_fixedHeader = false;
		_markupView = null;
		_paginate = true;
		_resultRowSplitter = null;
		_searchResultCssClass = null;
	}

	@Override
	protected String getPage() {
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
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		super.setAttributes(httpServletRequest);

		httpServletRequest.setAttribute(
			"liferay-ui:search-iterator:displayStyle", getDisplayStyle());
		httpServletRequest.setAttribute(
			"liferay-ui:search-iterator:fixedHeader",
			String.valueOf(_fixedHeader));
		httpServletRequest.setAttribute(
			"liferay-ui:search-iterator:markupView", _markupView);
		httpServletRequest.setAttribute(
			"liferay-ui:search-iterator:paginate", String.valueOf(_paginate));
		httpServletRequest.setAttribute(
			"liferay-ui:search-iterator:resultRowSplitter", _resultRowSplitter);
		httpServletRequest.setAttribute(
			"liferay-ui:search-iterator:searchResultCssClass",
			getSearchResultCssClass());
	}

	private String _displayStyle = DEFAULT_DISPLAY_STYLE;
	private boolean _fixedHeader;
	private String _markupView;
	private boolean _paginate = true;
	private ResultRowSplitter _resultRowSplitter;
	private String _searchResultCssClass;

}