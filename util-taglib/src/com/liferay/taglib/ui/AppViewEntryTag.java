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

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.taglib.util.IncludeTag;

import java.util.Date;
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

	public void setAuthor(String author) {
		_author = author;
	}

	public void setClassTypeName(String classTypeName) {
		_classTypeName = classTypeName;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setData(Map<String, Object> data) {
		_data = data;
	}

	public void setDescription(String description) {
		_description = HtmlUtil.unescape(description);
	}

	public void setDisplayDate(Date displayDate) {
		_displayDate = displayDate;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setExpirationDate(Date expirationDate) {
		_expirationDate = expirationDate;
	}

	public void setFolder(boolean folder) {
		_folder = folder;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setIconCssClass(String iconCssClass) {
		_iconCssClass = iconCssClass;
	}

	public void setLatestApprovedVersion(String latestApprovedVersion) {
		_latestApprovedVersion = latestApprovedVersion;
	}

	public void setLatestApprovedVersionAuthor(
		String latestApprovedVersionAuthor) {

		_latestApprovedVersionAuthor = latestApprovedVersionAuthor;
	}

	public void setLocked(boolean locked) {
		_locked = locked;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public void setReviewDate(Date reviewDate) {
		_reviewDate = reviewDate;
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

	public void setShowLinkTitle(boolean showLinkTitle) {
		_showLinkTitle = showLinkTitle;
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

	public void setVersion(String version) {
		_version = version;
	}

	@Override
	protected void cleanUp() {
		_actionJsp = null;
		_assetCategoryClassName = null;
		_assetCategoryClassPK = 0;
		_assetTagClassName = null;
		_assetTagClassPK = 0;
		_author = null;
		_classTypeName = null;
		_createDate = null;
		_cssClass = null;
		_data = null;
		_description = null;
		_displayDate = null;
		_displayStyle = null;
		_expirationDate = null;
		_folder = false;
		_groupId = 0;
		_iconCssClass = null;
		_latestApprovedVersion = null;
		_latestApprovedVersionAuthor = null;
		_locked = false;
		_modifiedDate = null;
		_reviewDate = null;
		_rowCheckerId = null;
		_rowCheckerName = null;
		_shortcut = false;
		_showCheckbox = false;
		_showLinkTitle = true;
		_status = 0;
		_thumbnailDivStyle = StringPool.BLANK;
		_thumbnailSrc = null;
		_thumbnailStyle = null;
		_title = null;
		_url = null;
		_version = null;
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
		request.setAttribute("liferay-ui:app-view-entry:author", _author);
		request.setAttribute(
			"liferay-ui:app-view-entry:classTypeName", _classTypeName);
		request.setAttribute(
			"liferay-ui:app-view-entry:createDate", _createDate);
		request.setAttribute("liferay-ui:app-view-entry:cssClass", _cssClass);
		request.setAttribute("liferay-ui:app-view-entry:data", _data);
		request.setAttribute(
			"liferay-ui:app-view-entry:description", _description);
		request.setAttribute(
			"liferay-ui:app-view-entry:displayDate", _displayDate);
		request.setAttribute(
			"liferay-ui:app-view-entry:displayStyle", _displayStyle);
		request.setAttribute(
			"liferay-ui:app-view-entry:expirationDate", _expirationDate);
		request.setAttribute("liferay-ui:app-view-entry:folder", _folder);
		request.setAttribute("liferay-ui:app-view-entry:groupId", _groupId);
		request.setAttribute(
			"liferay-ui:app-view-entry:iconCssClass", _iconCssClass);
		request.setAttribute(
			"liferay-ui:app-view-entry:latestApprovedVersion",
			_latestApprovedVersion);
		request.setAttribute(
			"liferay-ui:app-view-entry:latestApprovedVersionAuthor",
			_latestApprovedVersionAuthor);
		request.setAttribute("liferay-ui:app-view-entry:locked", _locked);
		request.setAttribute(
			"liferay-ui:app-view-entry:modifiedDate", _modifiedDate);
		request.setAttribute(
			"liferay-ui:app-view-entry:reviewDate", _reviewDate);
		request.setAttribute(
			"liferay-ui:app-view-entry:rowCheckerId", _rowCheckerId);
		request.setAttribute(
			"liferay-ui:app-view-entry:rowCheckerName", _rowCheckerName);
		request.setAttribute("liferay-ui:app-view-entry:shortcut", _shortcut);
		request.setAttribute(
			"liferay-ui:app-view-entry:showCheckbox", _showCheckbox);
		request.setAttribute(
			"liferay-ui:app-view-entry:showLinkTitle", _showLinkTitle);
		request.setAttribute("liferay-ui:app-view-entry:status", _status);
		request.setAttribute(
			"liferay-ui:app-view-entry:thumbnailDivStyle", _thumbnailDivStyle);
		request.setAttribute(
			"liferay-ui:app-view-entry:thumbnailSrc", _thumbnailSrc);
		request.setAttribute(
			"liferay-ui:app-view-entry:thumbnailStyle", _thumbnailStyle);
		request.setAttribute("liferay-ui:app-view-entry:title", _title);
		request.setAttribute("liferay-ui:app-view-entry:version", _version);
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
	private String _author;
	private String _classTypeName;
	private Date _createDate;
	private String _cssClass;
	private Map<String, Object> _data;
	private String _description;
	private Date _displayDate;
	private String _displayStyle;
	private Date _expirationDate;
	private boolean _folder;
	private long _groupId;
	private String _iconCssClass;
	private String _latestApprovedVersion;
	private String _latestApprovedVersionAuthor;
	private boolean _locked;
	private Date _modifiedDate;
	private Date _reviewDate;
	private String _rowCheckerId;
	private String _rowCheckerName;
	private boolean _shortcut;
	private boolean _showCheckbox = false;
	private boolean _showLinkTitle = true;
	private int _status = 0;
	private String _thumbnailDivStyle = StringPool.BLANK;
	private String _thumbnailSrc;
	private String _thumbnailStyle;
	private String _title;
	private String _url;
	private String _version;

}