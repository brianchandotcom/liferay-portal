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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.breadcrumb.util.BreadcrumbUtil;
import com.liferay.taglib.aui.AUIUtil;
import com.liferay.taglib.util.IncludeTag;

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

	public void setShowCurrentPortlet(boolean showCurrentPortlet) {
		_showCurrentPortlet = showCurrentPortlet;
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

	protected void buildCurrentGroupBreadcrumb(
			ThemeDisplay themeDisplay, StringBundler sb)
		throws Exception {

		BreadcrumbEntry currentGroupEntry =
			BreadcrumbUtil.getCurrentGroupBreadcrumbEntry(themeDisplay);

		if (currentGroupEntry != null) {
			sb.append("<li><a href=\"");
			sb.append(currentGroupEntry.getURL());
			sb.append("\">");
			sb.append(HtmlUtil.escape(currentGroupEntry.getTitle()));
			sb.append("</a><span class=\"divider\">/</span></li>");
		}
	}

	protected void buildGuestGroupBreadcrumb(
			ThemeDisplay themeDisplay, StringBundler sb)
		throws Exception {

		BreadcrumbEntry guestGroupEntry =
			BreadcrumbUtil.getGuestGroupBreadcrumbEntry(themeDisplay);

		if (guestGroupEntry != null) {
			sb.append("<li><a href=\"");
			sb.append(guestGroupEntry.getURL());
			sb.append("\">");
			sb.append(HtmlUtil.escape(guestGroupEntry.getTitle()));
			sb.append("</a><span class=\"divider\">/</span></li>");
		}
	}

	protected void buildLayoutBreadcrumb(
			ThemeDisplay themeDisplay, StringBundler sb)
		throws Exception {

		List<BreadcrumbEntry> layoutEntries =
			BreadcrumbUtil.getLayoutBreadcrumbEntries(themeDisplay);

		for (BreadcrumbEntry layoutEntry : layoutEntries) {
			sb.append("<li><a href=\"");
			sb.append(layoutEntry.getURL());
			sb.append("\" ");

			Layout layout = (Layout)layoutEntry.getEntity();

			String target;

			if (layout.isTypeControlPanel()) {
				target = "target=\"_top\"";
			}
			else {
				target = PortalUtil.getLayoutTarget(layout);
			}

			sb.append(target);

			sb.append(StringPool.GREATER_THAN);
			sb.append(HtmlUtil.escape(layoutEntry.getTitle()));
			sb.append("</a><span class=\"divider\">/</span></li>");
		}
	}

	protected void buildParentGroupsBreadcrumb(
			ThemeDisplay themeDisplay, StringBundler sb)
		throws Exception {

		List<BreadcrumbEntry> parentGroupEntries =
			BreadcrumbUtil.getParentGroupBreadcrumbEntries(themeDisplay);

		for (BreadcrumbEntry parentGroupEntry : parentGroupEntries) {
			sb.append("<li><a href=\"");
			sb.append(parentGroupEntry.getURL());
			sb.append("\">");
			sb.append(HtmlUtil.escape(parentGroupEntry.getTitle()));
			sb.append("</a><span class=\"divider\">/</span></li>");
		}
	}

	protected void buildPortletBreadcrumb(
			HttpServletRequest request, ThemeDisplay themeDisplay,
			StringBundler sb)
		throws Exception {

		List<BreadcrumbEntry> portletEntries =
			BreadcrumbUtil.getPortletBreadcrumbEntries(request);

		for (BreadcrumbEntry portletEntry : portletEntries) {
			if (!_showCurrentGroup) {
				String siteGroupName = themeDisplay.getSiteGroupName();

				if (siteGroupName.equals(portletEntry.getTitle())) {
					continue;
				}
			}

			if (!_showCurrentPortlet) {
				PortletDisplay portletDisplay =
					themeDisplay.getPortletDisplay();

				String portletTitle = PortalUtil.getPortletTitle(
					portletDisplay.getId(), themeDisplay.getUser());

				if (portletTitle.equals(portletEntry.getTitle())) {
					continue;
				}
			}

			sb.append("<li>");

			if (Validator.isNotNull(portletEntry.getURL())) {
				sb.append("<a href=\"");
				sb.append(HtmlUtil.escape(portletEntry.getURL()));
				sb.append("\"");
				sb.append(AUIUtil.buildData(portletEntry.getData()));
				sb.append(StringPool.GREATER_THAN);

				sb.append(HtmlUtil.escape(portletEntry.getTitle()));

				sb.append("</a>");
			}
			else {
				sb.append(HtmlUtil.escape(portletEntry.getTitle()));
			}

			sb.append("<span class=\"divider\">/</span></li>");
		}
	}

	@Override
	protected void cleanUp() {
		_displayStyle = _DISPLAY_STYLE;
		_showCurrentGroup = true;
		_showCurrentPortlet = true;
		_showGuestGroup = _SHOW_GUEST_GROUP;
		_showLayout = true;
		_showParentGroups = null;
		_showPortletBreadcrumb = true;
	}

	protected String getBreadcrumbString(HttpServletRequest request) {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		StringBundler sb = new StringBundler();

		try {
			if (_showGuestGroup) {
				buildGuestGroupBreadcrumb(themeDisplay, sb);
			}

			if (_showParentGroups) {
				buildParentGroupsBreadcrumb(themeDisplay, sb);
			}

			if (_showCurrentGroup) {
				buildCurrentGroupBreadcrumb(themeDisplay, sb);
			}

			if (_showLayout) {
				buildLayoutBreadcrumb(themeDisplay, sb);
			}

			if (_showPortletBreadcrumb) {
				buildPortletBreadcrumb(request, themeDisplay, sb);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return modifyBreadcrumbCssClasses(sb.toString());
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

	protected String modifyBreadcrumbCssClasses(String breadcrumbString) {
		if (Validator.isNull(breadcrumbString)) {
			return StringPool.BLANK;
		}

		String breadcrumbTruncateClass = StringPool.BLANK;

		String[] breadcrumbArray = breadcrumbString.split("<li", -1);

		boolean breadcrumbTruncate = false;

		if (breadcrumbArray.length > 3) {
			breadcrumbTruncate = true;
		}

		if (breadcrumbTruncate) {
			breadcrumbTruncateClass = " breadcrumb-truncate";
		}

		int x = breadcrumbString.indexOf("<li") + 3;
		int y = breadcrumbString.lastIndexOf("<li") + 3;

		if (x == y) {
			breadcrumbString = StringUtil.insert(
				breadcrumbString,
				" class=\"active only" + breadcrumbTruncateClass + "\"", x);
		}
		else {
			breadcrumbString = StringUtil.insert(
				breadcrumbString,
				" class=\"active last" + breadcrumbTruncateClass + "\"", y);

			breadcrumbString = StringUtil.insert(
				breadcrumbString,
				" class=\"first" + breadcrumbTruncateClass + "\"", x);
		}

		if (breadcrumbTruncate) {
			y = breadcrumbString.lastIndexOf("<li");

			int z = breadcrumbString.lastIndexOf("<li", y - 1) + 3;

			breadcrumbString = StringUtil.insert(
				breadcrumbString,
				" class=\"current-parent" + breadcrumbTruncateClass + "\"", z);
		}

		return breadcrumbString;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		initShowParentGroups(request);

		request.setAttribute(
			"liferay-ui:breadcrumb:breadcrumbString",
			getBreadcrumbString(request));

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
			"liferay-ui:breadcrumb:showCurrentPortlet",
			String.valueOf(_showCurrentPortlet));
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

	private static Log _log = LogFactoryUtil.getLog(BreadcrumbTag.class);

	private String _displayStyle = _DISPLAY_STYLE;
	private boolean _showCurrentGroup = true;
	private boolean _showCurrentPortlet = true;
	private boolean _showGuestGroup = _SHOW_GUEST_GROUP;
	private boolean _showLayout = true;
	private Boolean _showParentGroups = null;
	private boolean _showPortletBreadcrumb = true;

}