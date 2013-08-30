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

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Account;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.taglib.aui.AUIUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 */
public class BreadcrumbTag extends IncludeTag {

	public void setPortletURL(PortletURL portletURL) {
		_portletURL = portletURL;
	}

	public void setSelLayout(Layout selLayout) {
		_selLayout = selLayout;
	}

	public void setSelLayoutParam(String selLayoutParam) {
		_selLayoutParam = selLayoutParam;
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

	protected void buildGuestGroupBreadcrumb(
			ThemeDisplay themeDisplay, StringBundler sb)
		throws Exception {

		Group group = GroupLocalServiceUtil.getGroup(
			themeDisplay.getCompanyId(), GroupConstants.GUEST);

		if (group.getPublicLayoutsPageCount() == 0) {
			return;
		}

		sb.append("<li><a href=\"");

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
			group.getGroupId(), false);

		String layoutSetFriendlyURL = PortalUtil.getLayoutSetFriendlyURL(
			layoutSet, themeDisplay);

		if (themeDisplay.isAddSessionIdToURL()) {
			layoutSetFriendlyURL = PortalUtil.getURLWithSessionId(
				layoutSetFriendlyURL, themeDisplay.getSessionId());
		}

		sb.append(layoutSetFriendlyURL);

		sb.append("\">");

		Account account = themeDisplay.getAccount();

		sb.append(HtmlUtil.escape(account.getName()));

		sb.append("</a><span class=\"divider\">/</span></li>");
	}

	protected void buildLayoutBreadcrumb(
			Layout selLayout, String selLayoutParam, boolean selectedLayout,
			PortletURL portletURL, ThemeDisplay themeDisplay, StringBundler sb)
		throws Exception {

		if (selLayout.getParentLayoutId() !=
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {

			Layout parentLayout = LayoutLocalServiceUtil.getParentLayout(
				selLayout);

			buildLayoutBreadcrumb(
				parentLayout, selLayoutParam, false, portletURL, themeDisplay,
				sb);
		}

		sb.append("<li><a href=\"");

		String layoutURL = getBreadcrumbLayoutURL(
			selLayout, selLayoutParam, portletURL, themeDisplay);

		if (themeDisplay.isAddSessionIdToURL()) {
			layoutURL = PortalUtil.getURLWithSessionId(
				layoutURL, themeDisplay.getSessionId());
		}

		if (selLayout.isTypeControlPanel()) {
			layoutURL = HttpUtil.removeParameter(
				layoutURL, "controlPanelCategory");
		}

		sb.append(layoutURL);

		sb.append("\" ");

		String layoutName = selLayout.getName(themeDisplay.getLocale());

		if (selLayout.isTypeControlPanel()) {
			sb.append("target=\"_top\"");

			if (layoutName.equals(LayoutConstants.NAME_CONTROL_PANEL_DEFAULT)) {
				layoutName = LanguageUtil.get(
					themeDisplay.getLocale(), "control-panel");
			}
		}
		else {
			String target = PortalUtil.getLayoutTarget(selLayout);

			sb.append(target);
		}

		sb.append(StringPool.GREATER_THAN);
		sb.append(HtmlUtil.escape(layoutName));
		sb.append("</a><span class=\"divider\">/</span></li>");
	}

	protected void buildParentGroupsBreadcrumb(
			LayoutSet layoutSet, PortletURL portletURL,
			ThemeDisplay themeDisplay, StringBundler sb)
		throws Exception {

		Group group = layoutSet.getGroup();

		if (group.isControlPanel()) {
			return;
		}

		if (group.isSite()) {
			Group parentSite = group.getParentGroup();

			if (parentSite != null) {
				LayoutSet parentLayoutSet =
					LayoutSetLocalServiceUtil.getLayoutSet(
						parentSite.getGroupId(), layoutSet.isPrivateLayout());

				buildParentGroupsBreadcrumb(
					parentLayoutSet, portletURL, themeDisplay, sb);
			}
		}
		else if (group.isUser()) {
			User groupUser = UserLocalServiceUtil.getUser(group.getClassPK());

			List<Organization> organizations =
				OrganizationLocalServiceUtil.getUserOrganizations(
					groupUser.getUserId());

			if (!organizations.isEmpty()) {
				Organization organization = organizations.get(0);

				Group parentGroup = organization.getGroup();

				LayoutSet parentLayoutSet =
					LayoutSetLocalServiceUtil.getLayoutSet(
						parentGroup.getGroupId(), layoutSet.isPrivateLayout());

				buildParentGroupsBreadcrumb(
					parentLayoutSet, portletURL, themeDisplay, sb);
			}
		}

		int layoutsPageCount = 0;

		if (layoutSet.isPrivateLayout()) {
			layoutsPageCount = group.getPrivateLayoutsPageCount();
		}
		else {
			layoutsPageCount = group.getPublicLayoutsPageCount();
		}

		if ((layoutsPageCount > 0) && !group.isGuest()) {
			String layoutSetFriendlyURL = PortalUtil.getLayoutSetFriendlyURL(
				layoutSet, themeDisplay);

			if (themeDisplay.isAddSessionIdToURL()) {
				layoutSetFriendlyURL = PortalUtil.getURLWithSessionId(
					layoutSetFriendlyURL, themeDisplay.getSessionId());
			}

			sb.append("<li><a href=\"");
			sb.append(layoutSetFriendlyURL);
			sb.append("\">");
			sb.append(HtmlUtil.escape(group.getDescriptiveName()));
			sb.append("</a><span class=\"divider\">/</span></li>");
		}
	}

	protected void buildPortletBreadcrumb(
			HttpServletRequest request, boolean showCurrentGroup,
			boolean showCurrentPortlet, ThemeDisplay themeDisplay,
			StringBundler sb)
		throws Exception {

		List<BreadcrumbEntry> breadcrumbEntries =
			PortalUtil.getPortletBreadcrumbs(request);

		if (breadcrumbEntries == null) {
			return;
		}

		for (int i = 0; i < breadcrumbEntries.size(); i++) {
			BreadcrumbEntry breadcrumbEntry = breadcrumbEntries.get(i);

			Map<String, Object> data = breadcrumbEntry.getData();

			String breadcrumbTitle = breadcrumbEntry.getTitle();
			String breadcrumbURL = breadcrumbEntry.getURL();

			if (!showCurrentGroup) {
				String siteGroupName = themeDisplay.getSiteGroupName();

				if (siteGroupName.equals(breadcrumbTitle)) {
					continue;
				}
			}

			if (!showCurrentPortlet) {
				PortletDisplay portletDisplay =
					themeDisplay.getPortletDisplay();

				String portletTitle = PortalUtil.getPortletTitle(
					portletDisplay.getId(), themeDisplay.getUser());

				if (portletTitle.equals(breadcrumbTitle)) {
					continue;
				}
			}

			if (!CookieKeys.hasSessionId(request) &&
				Validator.isNotNull(breadcrumbURL)) {

				HttpSession session = request.getSession();

				breadcrumbURL = PortalUtil.getURLWithSessionId(
					breadcrumbURL, session.getId());
			}

			sb.append("<li>");

			if (i < (breadcrumbEntries.size() - 1)) {
				if (Validator.isNotNull(breadcrumbURL)) {
					sb.append("<a href=\"");
					sb.append(HtmlUtil.escape(breadcrumbURL));
					sb.append("\"");
					sb.append(AUIUtil.buildData(data));
					sb.append(">");
				}

				sb.append(HtmlUtil.escape(breadcrumbTitle));

				if (Validator.isNotNull(breadcrumbURL)) {
					sb.append("</a>");
				}

				sb.append("<span class=\"divider\">/</span>");
			}
			else {
				sb.append(HtmlUtil.escape(breadcrumbTitle));
			}

			sb.append("</li>");
		}
	}

	@Override
	protected void cleanUp() {
		_portletURL = null;
		_selLayout = null;
		_selLayoutParam = null;
		_showCurrentGroup = true;
		_showCurrentPortlet = true;
		_showGuestGroup = _SHOW_GUEST_GROUP;
		_showLayout = true;
		_showParentGroups = _SHOW_PARENT_GROUPS;
		_showPortletBreadcrumb = true;
	}

	protected String getBreadcrumbLayoutURL(
			Layout selLayout, String selLayoutParam, PortletURL portletURL,
			ThemeDisplay themeDisplay)
		throws Exception {

		if (portletURL == null) {
			return PortalUtil.getLayoutFullURL(selLayout, themeDisplay);
		}

		portletURL.setParameter(
			selLayoutParam, String.valueOf(selLayout.getPlid()));

		if (selLayout.isTypeControlPanel()) {
			if (themeDisplay.getDoAsGroupId() > 0) {
				portletURL.setParameter(
					"doAsGroupId",
					String.valueOf(themeDisplay.getDoAsGroupId()));
			}

			if (themeDisplay.getRefererPlid() != LayoutConstants.DEFAULT_PLID) {
				portletURL.setParameter(
					"refererPlid",
					String.valueOf(themeDisplay.getRefererPlid()));
			}
		}

		return portletURL.toString();
	}

	protected String getBreadcrumbString(HttpServletRequest request) {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		StringBundler sb = new StringBundler();

		try {
			if (Validator.isNull(_selLayout)) {
				setSelLayout(themeDisplay.getLayout());
			}

			Group group = _selLayout.getGroup();

			if (_showGuestGroup) {
				buildGuestGroupBreadcrumb(themeDisplay, sb);
			}

			if (_showParentGroups) {
				buildParentGroupsBreadcrumb(
					_selLayout.getLayoutSet(), _portletURL, themeDisplay, sb);
			}

			if (_showLayout && !group.isLayoutPrototype()) {
				buildLayoutBreadcrumb(
					_selLayout, _selLayoutParam, true, _portletURL,
					themeDisplay, sb);
			}

			if (_showPortletBreadcrumb) {
				buildPortletBreadcrumb(
					request, _showCurrentGroup, _showCurrentPortlet,
					themeDisplay, sb);
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		String breadcrumbString = sb.toString();

		if (Validator.isNull(breadcrumbString)) {
			return StringPool.BLANK;
		}

		boolean showEllipsis = breadcrumbString.split("<li", -1).length > 3;
		String showEllipsisClass = showEllipsis ? " show-ellipsis" : StringPool.BLANK;

		int x = breadcrumbString.indexOf("<li") + 3;
		int y = breadcrumbString.lastIndexOf("<li") + 3;

		if (x == y) {
			breadcrumbString = StringUtil.insert(
				breadcrumbString, " class=\"active only" + showEllipsisClass + "\"", x);
		}
		else {
			breadcrumbString = StringUtil.insert(
				breadcrumbString, " class=\"active last" + showEllipsisClass + "\"", y);
			breadcrumbString = StringUtil.insert(
				breadcrumbString, " class=\"first" + showEllipsisClass + "\"", x);
		}

		if (showEllipsis) {
			y = breadcrumbString.lastIndexOf("<li");

			int z = breadcrumbString.lastIndexOf("<li", y - 1) + 3;

			breadcrumbString = StringUtil.insert(
				breadcrumbString, " class=\"second-to-last" + showEllipsisClass + "\"", z);
		}

		return breadcrumbString;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:breadcrumb:breadcrumbString",
			getBreadcrumbString(request));
		request.setAttribute("liferay-ui:breadcrumb:portletURL", _portletURL);
		request.setAttribute("liferay-ui:breadcrumb:selLayout", _selLayout);
		request.setAttribute(
			"liferay-ui:breadcrumb:selLayoutParam", _selLayoutParam);
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

	private static final String _PAGE = "/html/taglib/ui/breadcrumb/page.jsp";

	private static final boolean _SHOW_GUEST_GROUP = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.BREADCRUMB_SHOW_GUEST_GROUP));

	private static final boolean _SHOW_PARENT_GROUPS = GetterUtil.getBoolean(
		PropsUtil.get(PropsKeys.BREADCRUMB_SHOW_PARENT_GROUPS));

	private static Log _log = LogFactoryUtil.getLog(BreadcrumbTag.class);

	private PortletURL _portletURL;
	private Layout _selLayout;
	private String _selLayoutParam;
	private boolean _showCurrentGroup = true;
	private boolean _showCurrentPortlet = true;
	private boolean _showGuestGroup = _SHOW_GUEST_GROUP;
	private boolean _showLayout = true;
	private boolean _showParentGroups = _SHOW_PARENT_GROUPS;
	private boolean _showPortletBreadcrumb = true;

}