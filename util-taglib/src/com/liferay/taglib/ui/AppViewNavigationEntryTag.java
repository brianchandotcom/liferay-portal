/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sergio González
 */
public class AppViewNavigationEntryTag extends IncludeTag {

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public void setActionJsp(String actionJsp) {
		_actionJsp = actionJsp;
	}

	public void setBrowseUp(boolean browseUp) {
		_browseUp = browseUp;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setDataExpand(Map<String, Object> dataExpand) {
		_dataExpand = dataExpand;
	}

	public void setDataView(Map<String, Object> dataView) {
		_dataView = dataView;
	}

	public void setEntryTitle(String entryTitle) {
		_entryTitle = entryTitle;
	}

	public void setExpandURL(String expandURL) {
		_expandURL = expandURL;
	}

	public void setIconImage(String iconImage) {
		_iconImage = iconImage;
	}

	public void setIconSrc(String iconSrc) {
		_iconSrc = iconSrc;
	}

	public void setSelected(boolean selected) {
		_selected = selected;
	}

	public void setShowExpand(boolean showExpand) {
		_showExpand = showExpand;
	}

	public void setViewURL(String viewURL) {
		_viewURL = viewURL;
	}

	@Override
	protected void cleanUp() {
		_actionJsp = null;
		_browseUp = false;
		_cssClass = "folder";
		_dataExpand = null;
		_dataView = null;
		_entryTitle = null;
		_expandURL = null;
		_iconImage = null;
		_iconSrc = null;
		_selected = false;
		_showExpand = false;
		_viewURL = null;
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
			"liferay-ui:app-view-navigation-entry:actionJsp", _actionJsp);
		request.setAttribute(
			"liferay-ui:app-view-navigation-entry:browseUp", _browseUp);
		request.setAttribute(
			"liferay-ui:app-view-navigation-entry:cssClass", _cssClass);
		request.setAttribute(
			"liferay-ui:app-view-navigation-entry:dataExpand", _dataExpand);
		request.setAttribute(
			"liferay-ui:app-view-navigation-entry:dataView", _dataView);
		request.setAttribute(
			"liferay-ui:app-view-navigation-entry:entryTitle", _entryTitle);
		request.setAttribute(
			"liferay-ui:app-view-navigation-entry:expandURL", _expandURL);
		request.setAttribute(
			"liferay-ui:app-view-navigation-entry:iconImage", _iconImage);
		request.setAttribute(
			"liferay-ui:app-view-navigation-entry:iconSrc", _iconSrc);
		request.setAttribute(
			"liferay-ui:app-view-navigation-entry:selected", _selected);
		request.setAttribute(
			"liferay-ui:app-view-navigation-entry:showExpand", _showExpand);
		request.setAttribute(
			"liferay-ui:app-view-navigation-entry:viewURL", _viewURL);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE =
		"/html/taglib/ui/app_view_navigation_entry/page.jsp";

	private String _actionJsp;
	private boolean _browseUp;
	private String _cssClass = "folder";
	private Map<String, Object> _dataExpand;
	private Map<String, Object> _dataView;
	private String _entryTitle;
	private String _expandURL;
	private String _iconImage;
	private String _iconSrc;
	private boolean _selected;
	private boolean _showExpand;
	private String _viewURL;

}