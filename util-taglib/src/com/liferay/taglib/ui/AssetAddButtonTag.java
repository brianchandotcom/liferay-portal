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

import com.liferay.taglib.util.IncludeTag;

import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class AssetAddButtonTag extends IncludeTag {

	public Map<String, PortletURL> getAddPortletURLs() {
		return _addPortletURLs;
	}

	public long getGroupCount() {
		return _groupCount;
	}

	public long getGroupId() {
		return _groupId;
	}

	public boolean isDefaultAssetPublisher() {
		return _defaultAssetPublisher;
	}

	public void setAddPortletURLs(Map<String, PortletURL> addPortletURLs) {
		_addPortletURLs = addPortletURLs;
	}

	public void setDefaultAssetPublisher(boolean defaultAssetPublisher) {
		_defaultAssetPublisher = defaultAssetPublisher;
	}

	public void setGroupCount(long groupCount) {
		_groupCount = groupCount;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	@Override
	protected void cleanUp() {
		_addPortletURLs = null;
		_defaultAssetPublisher = false;
		_groupCount = 1;
		_groupId = 0;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:asset-add-button:addPortletURLs", _addPortletURLs);
		request.setAttribute(
			"liferay-ui:asset-add-button:defaultAssetPublisher",
			_defaultAssetPublisher);
		request.setAttribute(
			"liferay-ui:asset-add-button:groupCount", _groupCount);
		request.setAttribute("liferay-ui:asset-add-button:groupId", _groupId);
	}

	private static final String _PAGE =
		"/html/taglib/ui/asset_add_button/page.jsp";

	private Map<String, PortletURL> _addPortletURLs;
	private boolean _defaultAssetPublisher;
	private long _groupCount = 1;
	private long _groupId = 0;

}