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

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.model.UserGroup;
import com.liferay.taglib.util.IncludeTag;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Drew Brokke
 */
public class UserGroupSearchResultsTag extends IncludeTag {

	public void setResults(List<UserGroup> results) {
		_results = results;
	}

	public void setSearchContainer(SearchContainer searchContainer) {
		_searchContainer = searchContainer;
	}

	public void setSearchTerms(DisplayTerms searchTerms) {
		_searchTerms = searchTerms;
	}

	public void setSearchWithIndex(boolean searchWithIndex) {
		_searchWithIndex = searchWithIndex;
	}

	public void setTotal(int total) {
		_total = total;
	}

	public void setUserGroupParams(
		LinkedHashMap<String, Object> userGroupParams) {

		_userGroupParams = userGroupParams;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:user-group-search-results:results", _results);
		request.setAttribute(
			"liferay-ui:user-group-search-results:searchContainer",
			_searchContainer);
		request.setAttribute(
			"liferay-ui:user-group-search-results:searchTerms", _searchTerms);
		request.setAttribute(
			"liferay-ui:user-group-search-results:searchWithIndex",
			_searchWithIndex);
		request.setAttribute(
			"liferay-ui:user-group-search-results:total", _total);
		request.setAttribute(
			"liferay-ui:user-group-search-results:userGroupParams",
			_userGroupParams);
	}

	private static final String _PAGE =
		"/html/taglib/ui/user_group_search_results/page.jsp";

	private List<UserGroup> _results;
	private SearchContainer _searchContainer;
	private DisplayTerms _searchTerms;
	private boolean _searchWithIndex;
	private int _total;
	private LinkedHashMap<String, Object> _userGroupParams;

}