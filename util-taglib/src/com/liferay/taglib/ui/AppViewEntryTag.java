/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class AppViewEntryTag extends IncludeTag {

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public void setActionJsp(String actionJsp) {
		_actionJsp = actionJsp;
	}

	public void setAssetCategoryClassName(String assetCategoryClassName) {
		_assetCategoryClassName = assetCategoryClassName;
	}

	public void setAssetCategoryClassPK(long assetCategoryClassPK) {
		_assetCategoryClassPK = assetCategoryClassPK;
	}

	public void setAssetTagClassName(String assetTagClassName) {
		_assetTagClassName = assetTagClassName;
	}

	public void setAssetTagClassPK(long assetTagClassPK) {
		_assetTagClassPK = assetTagClassPK;
	}

	public void setData(Map<String, Object> data) {
		_data = data;
	}

	public void setDescription(String description) {
		_description = HtmlUtil.unescape(description);
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setFolder(boolean folder) {
		_folder = folder;
	}

	public void setLocked(boolean locked) {
		_locked = locked;
	}

	public void setRowCheckerId(String rowCheckerId) {
		_rowCheckerId = rowCheckerId;
	}

	public void setRowCheckerName(String rowCheckerName) {
		_rowCheckerName = rowCheckerName;
	}

	public void setShortcut(boolean shortcut) {
		_shortcut = shortcut;
	}

	public void setShowCheckbox(boolean showCheckbox) {
		_showCheckbox = showCheckbox;
	}

	public void setStatus(int status) {
		_status = status;
	}

	public void setThumbnailDivStyle(String thumbnailDivStyle) {
		_thumbnailDivStyle = thumbnailDivStyle;
	}

	public void setThumbnailSrc(String thumbnailSrc) {
		_thumbnailSrc = thumbnailSrc;
	}

	public void setThumbnailStyle(String thumbnailStyle) {
		_thumbnailStyle = thumbnailStyle;
	}

	public void setTitle(String title) {
		_title = HtmlUtil.unescape(title);
	}

	public void setUrl(String url) {
		_url = url;
	}

	@Override
	protected void cleanUp() {
		_actionJsp = null;
		_assetCategoryClassName = null;
		_assetCategoryClassPK = 0;
		_assetTagClassName = null;
		_assetTagClassPK = 0;
		_data = null;
		_description = null;
		_displayStyle = null;
		_folder = false;
		_locked = false;
		_rowCheckerId = null;
		_rowCheckerName = null;
		_shortcut = false;
		_showCheckbox = false;
		_status = 0;
		_thumbnailDivStyle = null;
		_thumbnailSrc = null;
		_thumbnailStyle = null;
		_title = null;
		_url = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-ui:app-view-entry:actionJsp", _actionJsp);
		request.setAttribute(
			"liferay-ui:app-view-entry:assetCategoryClassName",
			_assetCategoryClassName);
		request.setAttribute(
			"liferay-ui:app-view-entry:assetCategoryClassPK",
			_assetCategoryClassPK);
		request.setAttribute(
			"liferay-ui:app-view-entry:assetTagClassName", _assetTagClassName);
		request.setAttribute(
			"liferay-ui:app-view-entry:assetTagClassPK", _assetTagClassPK);
		request.setAttribute("liferay-ui:app-view-entry:data", _data);
		request.setAttribute(
			"liferay-ui:app-view-entry:description", _description);
		request.setAttribute(
			"liferay-ui:app-view-entry:displayStyle", _displayStyle);
		request.setAttribute("liferay-ui:app-view-entry:folder", _folder);
		request.setAttribute("liferay-ui:app-view-entry:locked", _locked);
		request.setAttribute(
			"liferay-ui:app-view-entry:rowCheckerId", _rowCheckerId);
		request.setAttribute(
			"liferay-ui:app-view-entry:rowCheckerName", _rowCheckerName);
		request.setAttribute("liferay-ui:app-view-entry:shortcut", _shortcut);
		request.setAttribute(
			"liferay-ui:app-view-entry:showCheckbox", _showCheckbox);
		request.setAttribute("liferay-ui:app-view-entry:status", _status);
		request.setAttribute(
			"liferay-ui:app-view-entry:thumbnailDivStyle", _thumbnailDivStyle);
		request.setAttribute(
			"liferay-ui:app-view-entry:thumbnailSrc", _thumbnailSrc);
		request.setAttribute(
			"liferay-ui:app-view-entry:thumbnailStyle", _thumbnailStyle);
		request.setAttribute("liferay-ui:app-view-entry:title", _title);
		request.setAttribute("liferay-ui:app-view-entry:url", _url);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE =
		"/html/taglib/ui/app_view_entry/page.jsp";

	private String _actionJsp;
	private String _assetCategoryClassName;
	private long _assetCategoryClassPK;
	private String _assetTagClassName;
	private long _assetTagClassPK;
	private Map<String, Object> _data;
	private String _description;
	private String _displayStyle;
	private boolean _folder;
	private boolean _locked;
	private String _rowCheckerId;
	private String _rowCheckerName;
	private boolean _shortcut;
	private boolean _showCheckbox = false;
	private int _status = 0;
	private String _thumbnailDivStyle;
	private String _thumbnailSrc;
	private String _thumbnailStyle;
	private String _title;
	private String _url;

}