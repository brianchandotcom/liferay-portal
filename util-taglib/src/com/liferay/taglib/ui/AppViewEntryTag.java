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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;

import java.util.Date;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class AppViewEntryTag extends IncludeTag {

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public String getActionJsp() {
		return _actionJsp;
	}

	public String getAssetCategoryClassName() {
		return _assetCategoryClassName;
	}

	public long getAssetCategoryClassPK() {
		return _assetCategoryClassPK;
	}

	public String getAssetTagClassName() {
		return _assetTagClassName;
	}

	public long getAssetTagClassPK() {
		return _assetTagClassPK;
	}

	public String getAuthor() {
		return _author;
	}

	public String getClassTypeName() {
		return _classTypeName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public String getCssClass() {
		return _cssClass;
	}

	public Map<String, Object> getData() {
		return _data;
	}

	public String getDescription() {
		return _description;
	}

	public Date getDisplayDate() {
		return _displayDate;
	}

	public String getDisplayStyle() {
		return _displayStyle;
	}

	public Date getExpirationDate() {
		return _expirationDate;
	}

	public long getGroupId() {
		return _groupId;
	}

	public String getIconCssClass() {
		return _iconCssClass;
	}

	public String getLatestApprovedVersion() {
		return _latestApprovedVersion;
	}

	public String getLatestApprovedVersionAuthor() {
		return _latestApprovedVersionAuthor;
	}

	public String getMarkupView() {
		return _markupView;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public Date getReviewDate() {
		return _reviewDate;
	}

	public String getRowCheckerId() {
		return _rowCheckerId;
	}

	public String getRowCheckerName() {
		return _rowCheckerName;
	}

	public int getStatus() {
		return _status;
	}

	public String getThumbnailDivStyle() {
		return _thumbnailDivStyle;
	}

	public String getThumbnailSrc() {
		return _thumbnailSrc;
	}

	public String getThumbnailStyle() {
		return _thumbnailStyle;
	}

	public String getTitle() {
		return _title;
	}

	public String getUrl() {
		return _url;
	}

	public String getVersion() {
		return _version;
	}

	public boolean isFolder() {
		return _folder;
	}

	public boolean isLocked() {
		return _locked;
	}

	public boolean isShortcut() {
		return _shortcut;
	}

	public boolean isShowCheckbox() {
		return _showCheckbox;
	}

	public boolean isShowLinkTitle() {
		return _showLinkTitle;
	}

	public void setActionJsp(String actionJsp) {
		_actionJsp = actionJsp;
	}

	public void setActionJspServletContext(
		ServletContext actionJspServletContext) {

		_actionJspServletContext = actionJspServletContext;
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
		_description = description;
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

	public void setMarkupView(String markupView) {
		_markupView = markupView;
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
		_title = title;
	}

	public void setUrl(String url) {
		_url = url;
	}

	public void setVersion(String version) {
		_version = version;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_actionJsp = null;
		_actionJspServletContext = null;
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
		_displayStyle = "descriptive";
		_expirationDate = null;
		_folder = false;
		_groupId = 0;
		_iconCssClass = null;
		_latestApprovedVersion = null;
		_latestApprovedVersionAuthor = null;
		_locked = false;
		_markupView = null;
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

	protected ServletContext getActionJspServletContext() {
		if (_actionJspServletContext != null) {
			return _actionJspServletContext;
		}

		return getServletContext();
	}

	@Override
	protected String getPage() {
		if (Validator.isNotNull(_markupView)) {
			return StringBundler.concat(
				"/html/taglib/ui/app_view_entry/", _markupView, "/",
				_displayStyle, ".jsp");
		}

		return "/html/taglib/ui/app_view_entry/" + _displayStyle + ".jsp";
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:actionJsp", _actionJsp);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:actionJspServletContext",
			getActionJspServletContext());
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:assetCategoryClassName",
			_assetCategoryClassName);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:assetCategoryClassPK",
			_assetCategoryClassPK);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:assetTagClassName", _assetTagClassName);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:assetTagClassPK", _assetTagClassPK);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:author", _author);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:classTypeName", _classTypeName);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:createDate", _createDate);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:cssClass", _cssClass);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:data", _data);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:description", _description);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:displayDate", _displayDate);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:displayStyle", _displayStyle);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:expirationDate", _expirationDate);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:folder", _folder);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:groupId", _groupId);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:iconCssClass", _iconCssClass);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:latestApprovedVersion",
			_latestApprovedVersion);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:latestApprovedVersionAuthor",
			_latestApprovedVersionAuthor);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:locked", _locked);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:modifiedDate", _modifiedDate);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:reviewDate", _reviewDate);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:rowCheckerId", _rowCheckerId);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:rowCheckerName", _rowCheckerName);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:shortcut", _shortcut);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:showCheckbox", _showCheckbox);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:showLinkTitle", _showLinkTitle);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:status", _status);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:thumbnailDivStyle", _thumbnailDivStyle);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:thumbnailSrc", _thumbnailSrc);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:thumbnailStyle", _thumbnailStyle);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:title", _title);
		httpServletRequest.setAttribute("liferay-ui:app-view-entry:url", _url);
		httpServletRequest.setAttribute(
			"liferay-ui:app-view-entry:version", _version);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private String _actionJsp;
	private ServletContext _actionJspServletContext;
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
	private String _displayStyle = "descriptive";
	private Date _expirationDate;
	private boolean _folder;
	private long _groupId;
	private String _iconCssClass;
	private String _latestApprovedVersion;
	private String _latestApprovedVersionAuthor;
	private boolean _locked;
	private String _markupView;
	private Date _modifiedDate;
	private Date _reviewDate;
	private String _rowCheckerId;
	private String _rowCheckerName;
	private boolean _shortcut;
	private boolean _showCheckbox;
	private boolean _showLinkTitle = true;
	private int _status;
	private String _thumbnailDivStyle = StringPool.BLANK;
	private String _thumbnailSrc;
	private String _thumbnailStyle;
	private String _title;
	private String _url;
	private String _version;

}