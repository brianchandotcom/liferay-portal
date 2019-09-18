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

import com.liferay.portal.kernel.portletdisplaytemplate.PortletDisplayTemplateManagerUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class BreadcrumbTag extends IncludeTag {

	public long getDdmTemplateGroupId() {
		return _ddmTemplateGroupId;
	}

	public String getDdmTemplateKey() {
		return _ddmTemplateKey;
	}

	public boolean isShowCurrentGroup() {
		return _showCurrentGroup;
	}

	public boolean isShowGuestGroup() {
		return _showGuestGroup;
	}

	public boolean isShowLayout() {
		return _showLayout;
	}

	public boolean isShowParentGroups() {
		return _showParentGroups;
	}

	public boolean isShowPortletBreadcrumb() {
		return _showPortletBreadcrumb;
	}

	public void setDdmTemplateGroupId(long ddmTemplateGroupId) {
		_ddmTemplateGroupId = ddmTemplateGroupId;
	}

	public void setDdmTemplateKey(String ddmTemplateKey) {
		_ddmTemplateKey = ddmTemplateKey;
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
		super.cleanUp();

		_ddmTemplateGroupId = 0;
		_ddmTemplateKey = null;
		_showCurrentGroup = true;
		_showGuestGroup = false;
		_showLayout = true;
		_showParentGroups = true;
		_showPortletBreadcrumb = true;
	}

	protected List<BreadcrumbEntry> getBreadcrumbEntries(
		HttpServletRequest httpServletRequest) {

		List<BreadcrumbEntry> breadcrumbEntries = Collections.emptyList();

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

		try {
			breadcrumbEntries = BreadcrumbUtil.getBreadcrumbEntries(
				httpServletRequest, ArrayUtil.toIntArray(breadcrumbEntryTypes));
		}
		catch (Exception e) {
		}

		return breadcrumbEntries;
	}

	protected String getDisplayStyle() {
		if (Validator.isNotNull(_ddmTemplateKey)) {
			return PortletDisplayTemplateManagerUtil.getDisplayStyle(
				_ddmTemplateKey);
		}

		return null;
	}

	protected long getDisplayStyleGroupId() {
		if (_ddmTemplateGroupId > 0) {
			return _ddmTemplateGroupId;
		}

		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return themeDisplay.getScopeGroupId();
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-ui:breadcrumb:breadcrumbEntries",
			getBreadcrumbEntries(httpServletRequest));
		httpServletRequest.setAttribute(
			"liferay-ui:breadcrumb:displayStyle", getDisplayStyle());
		httpServletRequest.setAttribute(
			"liferay-ui:breadcrumb:displayStyleGroupId",
			getDisplayStyleGroupId());
	}

	private static final String _PAGE = "/html/taglib/ui/breadcrumb/page.jsp";

	private long _ddmTemplateGroupId;
	private String _ddmTemplateKey;
	private boolean _showCurrentGroup = true;
	private boolean _showGuestGroup;
	private boolean _showLayout = true;
	private boolean _showParentGroups = true;
	private boolean _showPortletBreadcrumb = true;

}