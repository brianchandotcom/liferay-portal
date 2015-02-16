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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class BreadcrumbTag extends IncludeTag {

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setShowCurrentGroup(boolean showCurrentGroup) {
		_showCurrentGroup = showCurrentGroup;
	}

	public void setShowGuestGroup(boolean showGuestGroup) {
		_showGuestGroup = showGuestGroup;
	}

	public void setShowLayout(boolean showLayout) {
		_showLayout = showLayout;
	}

	public void setShowParentGroups(boolean showParentGroups) {
		_showParentGroups = showParentGroups;
	}

	public void setShowPortletBreadcrumb(boolean showPortletBreadcrumb) {
		_showPortletBreadcrumb = showPortletBreadcrumb;
	}

	@Override
	protected void cleanUp() {
		_displayStyle = _DISPLAY_STYLE;
		_showCurrentGroup = true;
		_showGuestGroup = _SHOW_GUEST_GROUP;
		_showLayout = true;
		_showParentGroups = null;
		_showPortletBreadcrumb = true;
	}

	protected List<BreadcrumbEntry> getBreadcrumbEntries(
		HttpServletRequest request) {

		List<Integer> breadcrumbEntryTypes = new ArrayList<>();

		if (_showCurrentGroup) {
			breadcrumbEntryTypes.add(BreadcrumbUtil.ENTRY_TYPE_CURRENT_GROUP);
		}

		if (_showGuestGroup) {
			breadcrumbEntryTypes.add(BreadcrumbUtil.ENTRY_TYPE_GUEST_GROUP);
		}

		if (_showLayout) {
			breadcrumbEntryTypes.add(BreadcrumbUtil.ENTRY_TYPE_LAYOUT);
		}

		if (_showParentGroups) {
			breadcrumbEntryTypes.add(BreadcrumbUtil.ENTRY_TYPE_PARENT_GROUP);
		}

		if (_showPortletBreadcrumb) {
			breadcrumbEntryTypes.add(BreadcrumbUtil.ENTRY_TYPE_PORTLET);
		}

		List<BreadcrumbEntry> breadcrumbEntries = Collections.emptyList();

		try {
			breadcrumbEntries = BreadcrumbUtil.getBreadcrumbEntries(
				request, ArrayUtil.toIntArray(breadcrumbEntryTypes));
		}
		catch (Exception e) {
		}

		return breadcrumbEntries;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	protected void initShowParentGroups(HttpServletRequest request) {
		if (_showParentGroups != null) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			Layout layout = themeDisplay.getLayout();

			Group group = layout.getGroup();

			UnicodeProperties typeSettingsProperties =
				group.getTypeSettingsProperties();

			_showParentGroups = GetterUtil.getBoolean(
				typeSettingsProperties.getProperty(
					"breadcrumbShowParentGroups"),
				_SHOW_PARENT_GROUPS);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		initShowParentGroups(request);

		request.setAttribute(
			"liferay-ui:breadcrumb:breadcrumbEntries",
			getBreadcrumbEntries(request));

		String displayStyle = _displayStyle;

		if (!ArrayUtil.contains(_DISPLAY_STYLE_OPTIONS, displayStyle)) {
			displayStyle = _DISPLAY_STYLE_OPTIONS[0];
		}

		request.setAttribute(
			"liferay-ui:breadcrumb:displayStyle", displayStyle);
		request.setAttribute(
			"liferay-ui:breadcrumb:showCurrentGroup",
			String.valueOf(_showCurrentGroup));
		request.setAttribute(
			"liferay-ui:breadcrumb:showGuestGroup",
			String.valueOf(_showGuestGroup));
		request.setAttribute(
			"liferay-ui:breadcrumb:showLayout", String.valueOf(_showLayout));
		request.setAttribute(
			"liferay-ui:breadcrumb:showParentGroups",
			String.valueOf(_showParentGroups));
		request.setAttribute(
			"liferay-ui:breadcrumb:showPortletBreadcrumb",
			String.valueOf(_showPortletBreadcrumb));
	}

	private static final String _DISPLAY_STYLE = GetterUtil.getString(
		PropsUtil.get(PropsKeys.BREADCRUMB_DISPLAY_STYLE_DEFAULT));

	private static final String[] _DISPLAY_STYLE_OPTIONS = PropsUtil.getArray(
		PropsKeys.BREADCRUMB_DISPLAY_STYLE_OPTIONS);

	private static final String _PAGE = "/html/taglib/ui/breadcrumb/page.jsp";

	private static final boolean _SHOW_GUEST_GROUP = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.BREADCRUMB_SHOW_GUEST_GROUP));

	private static final boolean _SHOW_PARENT_GROUPS = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.BREADCRUMB_SHOW_PARENT_GROUPS));

	private static final Log _log = LogFactoryUtil.getLog(BreadcrumbTag.class);

	private String _displayStyle = _DISPLAY_STYLE;
	private boolean _showCurrentGroup = true;
	private boolean _showGuestGroup = _SHOW_GUEST_GROUP;
	private boolean _showLayout = true;
	private Boolean _showParentGroups = null;
	private boolean _showPortletBreadcrumb = true;

}