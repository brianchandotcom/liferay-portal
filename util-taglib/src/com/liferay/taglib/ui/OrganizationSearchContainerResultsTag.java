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

/**
 * @author Pei-Jung Lan
 */
public class OrganizationSearchContainerResultsTag<R> extends IncludeTag {

	public void setOrganizationParams(
		LinkedHashMap<String, Object> organizationParams) {

		_organizationParams = organizationParams;
	}

	public void setParentOrganizationId(long parentOrganizationId) {
		_parentOrganizationId = parentOrganizationId;
	}

	public void setUseIndexer(boolean useIndexer) {
		_useIndexer = useIndexer;
	}

	@Override
	protected void cleanUp() {
		_organizationParams = null;
		_parentOrganizationId = 0;
		_searchTerms = null;
		_useIndexer = false;
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

		SearchContainer<R> searchContainer =
			searchContainerTag.getSearchContainer();

		_searchTerms = searchContainer.getSearchTerms();

		request.setAttribute(
			"liferay-ui:organization-search-container-results:useIndexer",
			_useIndexer);
		request.setAttribute(
			"liferay-ui:organization-search-container-results:searchContainer",
			searchContainer);
		request.setAttribute(
			"liferay-ui:organization-search-container-results:searchTerms",
			_searchTerms);
		request.setAttribute(
			"liferay-ui:organization-search-container-results:" +
				"organizationParams",
			_organizationParams);
		request.setAttribute(
			"liferay-ui:organization-search-container-results:" +
				"parentOrganizationId",
			_parentOrganizationId);
	}

	private static final String _PAGE =
		"/html/taglib/ui/organization_search_container_results/page.jsp";

	private LinkedHashMap<String, Object> _organizationParams;
	private long _parentOrganizationId;
	private DisplayTerms _searchTerms;
	private boolean _useIndexer = false;

}