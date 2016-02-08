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
import com.liferay.taglib.util.IncludeTag;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

/**
 * @author Pei-Jung Lan
 */
public class UserSearchContainerResultsTag<R> extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		SearchContainerTag<R> searchContainerTag =
			(SearchContainerTag<R>)findAncestorWithClass(
				this, SearchContainerTag.class);

		if (searchContainerTag == null) {
			throw new JspTagException("Requires liferay-ui:search-container");
		}

		return super.doStartTag();
	}

	public void setUseIndexer(boolean useIndexer) {
		_useIndexer = useIndexer;
	}

	public void setUserParams(LinkedHashMap<String, Object> userParams) {
		_userParams = userParams;
	}

	@Override
	protected void cleanUp() {
		_searchContainer = null;
		_searchTerms = null;
		_useIndexer = false;
		_userParams = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		SearchContainerTag<R> searchContainerTag =
			(SearchContainerTag<R>)findAncestorWithClass(
				this, SearchContainerTag.class);

		_searchContainer = searchContainerTag.getSearchContainer();

		_searchTerms = _searchContainer.getSearchTerms();

		request.setAttribute(
			"liferay-ui:user-search-container-results:useIndexer", _useIndexer);
		request.setAttribute(
			"liferay-ui:user-search-container-results:searchContainer",
			_searchContainer);
		request.setAttribute(
			"liferay-ui:user-search-container-results:searchTerms",
			_searchTerms);
		request.setAttribute(
			"liferay-ui:user-search-container-results:userParams", _userParams);
	}

	private static final String _PAGE =
		"/html/taglib/ui/user_search_container_results/page.jsp";

	private SearchContainer<R> _searchContainer;
	private DisplayTerms _searchTerms;
	private boolean _useIndexer = false;
	private LinkedHashMap<String, Object> _userParams;

}