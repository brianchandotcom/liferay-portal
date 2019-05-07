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

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author     Brian Wing Shun Chan
 */
public class SearchFormTag<R> extends IncludeTag {

	public SearchContainer<?> getSearchContainer() {
		return _searchContainer;
	}

	public boolean isShowAddButton() {
		return _showAddButton;
	}

	public void setSearchContainer(SearchContainer<?> searchContainer) {
		_searchContainer = searchContainer;
	}

	public void setShowAddButton(boolean showAddButton) {
		_showAddButton = showAddButton;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_searchContainer = null;
		_showAddButton = false;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		SearchContainerTag<R> searchContainerTag =
			(SearchContainerTag<R>)findAncestorWithClass(
				this, SearchContainerTag.class);

		if (searchContainerTag != null) {
			_searchContainer = searchContainerTag.getSearchContainer();

			httpServletRequest.setAttribute(
				"liferay-ui:search:compactEmptyResultsMessage",
				String.valueOf(
					searchContainerTag.isCompactEmptyResultsMessage()));
		}

		httpServletRequest.setAttribute(
			"liferay-ui:search:searchContainer", _searchContainer);
		httpServletRequest.setAttribute(
			"liferay-ui:search:showAddButton", String.valueOf(_showAddButton));
	}

	private SearchContainer<?> _searchContainer;
	private boolean _showAddButton;

}