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

	public LinkedHashMap<String, Object> getOrganizationParams() {
		return _organizationParams;
	}

	public long getParentOrganizationId() {
		return _parentOrganizationId;
	}

	public boolean isForceDatabase() {
		return _forceDatabase;
	}

	public void setForceDatabase(boolean forceDatabase) {
		_forceDatabase = forceDatabase;
	}

	public void setOrganizationParams(
		LinkedHashMap<String, Object> organizationParams) {

		_organizationParams = organizationParams;
	}

	public void setParentOrganizationId(long parentOrganizationId) {
		_parentOrganizationId = parentOrganizationId;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_forceDatabase = false;
		_organizationParams = null;
		_parentOrganizationId = 0;
		_searchTerms = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		SearchContainerTag<R> searchContainerTag =
			(SearchContainerTag<R>)findAncestorWithClass(
				this, SearchContainerTag.class);

		SearchContainer<R> searchContainer =
			searchContainerTag.getSearchContainer();

		_searchTerms = searchContainer.getSearchTerms();

		httpServletRequest.setAttribute(
			"liferay-ui:organization-search-container-results:forceDatabase",
			_forceDatabase);
		httpServletRequest.setAttribute(
			"liferay-ui:organization-search-container-results:" +
				"organizationParams",
			_organizationParams);
		httpServletRequest.setAttribute(
			"liferay-ui:organization-search-container-results:" +
				"parentOrganizationId",
			_parentOrganizationId);
		httpServletRequest.setAttribute(
			"liferay-ui:organization-search-container-results:searchContainer",
			searchContainer);
		httpServletRequest.setAttribute(
			"liferay-ui:organization-search-container-results:searchTerms",
			_searchTerms);
	}

	private static final String _PAGE =
		"/html/taglib/ui/organization_search_container_results/page.jsp";

	private boolean _forceDatabase;
	private LinkedHashMap<String, Object> _organizationParams;
	private long _parentOrganizationId;
	private DisplayTerms _searchTerms;

}