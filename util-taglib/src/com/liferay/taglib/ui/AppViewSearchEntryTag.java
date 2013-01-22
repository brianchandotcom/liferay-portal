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
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class AppViewSearchEntryTag extends IncludeTag {

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public void setActionJsp(String actionJsp) {
		_actionJsp = actionJsp;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setDescription(String description) {
		_description = HtmlUtil.unescape(description);
	}

	public void setFolderName(String folderName) {
		_folderName = folderName;
	}

	public void setLocked(boolean locked) {
		_locked = locked;
	}

	public void setMessages(List<MBMessage> messages) {
		_messages = messages;
	}

	public void setQueryTerms(String[] queryTerms) {
		_queryTerms = queryTerms;
	}

	public void setRowCheckerId(String rowCheckerId) {
		_rowCheckerId = rowCheckerId;
	}

	public void setRowCheckerName(String rowCheckerName) {
		_rowCheckerName = rowCheckerName;
	}

	public void setShowCheckbox(boolean showCheckbox) {
		_showCheckbox = showCheckbox;
	}

	public void setStatus(int status) {
		_status = status;
	}

	public void setThumbnailSrc(String thumbnailSrc) {
		_thumbnailSrc = thumbnailSrc;
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
		_cssClass = null;
		_description = null;
		_folderName = null;
		_locked = false;
		_messages = null;
		_queryTerms = null;
		_rowCheckerId = null;
		_rowCheckerName = null;
		_showCheckbox = false;
		_status = 0;
		_thumbnailSrc = null;
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
		request.setAttribute(
			"liferay-ui:app-view-search-entry:actionJsp", _actionJsp);
		request.setAttribute(
			"liferay-ui:app-view-search-entry:cssClass", _cssClass);
		request.setAttribute(
			"liferay-ui:app-view-search-entry:description", _description);
		request.setAttribute(
			"liferay-ui:app-view-search-entry:folderName", _folderName);
		request.setAttribute(
			"liferay-ui:app-view-search-entry:locked", _locked);
		request.setAttribute(
			"liferay-ui:app-view-search-entry:messages", _messages);
		request.setAttribute(
			"liferay-ui:app-view-search-entry:queryTerms", _queryTerms);
		request.setAttribute(
			"liferay-ui:app-view-search-entry:rowCheckerId", _rowCheckerId);
		request.setAttribute(
			"liferay-ui:app-view-search-entry:rowCheckerName", _rowCheckerName);
		request.setAttribute(
			"liferay-ui:app-view-search-entry:showCheckbox", _showCheckbox);
		request.setAttribute(
			"liferay-ui:app-view-search-entry:status", _status);
		request.setAttribute(
			"liferay-ui:app-view-search-entry:thumbnailSrc", _thumbnailSrc);
		request.setAttribute("liferay-ui:app-view-search-entry:title", _title);
		request.setAttribute("liferay-ui:app-view-search-entry:url", _url);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE =
		"/html/taglib/ui/app_view_search_entry/page.jsp";

	private String _actionJsp;
	private String _cssClass;
	private String _description;
	private String _folderName;
	private boolean _locked;
	private List<MBMessage> _messages;
	private String[] _queryTerms;
	private String _rowCheckerId;
	private String _rowCheckerName;
	private boolean _showCheckbox = false;
	private int _status = 0;
	private String _thumbnailSrc;
	private String _title;
	private String _url;

}