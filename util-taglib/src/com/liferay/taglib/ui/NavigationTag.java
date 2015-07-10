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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 * @author Tibor Lipusz
 */
public class NavigationTag extends IncludeTag {

	public void setBulletStyle(String bulletStyle) {
		_bulletStyle = bulletStyle;
	}

	public void setDisplayStyleDefinition(String[] displayStyleDefinition) {
		_displayStyleDefinition = displayStyleDefinition;
	}

	public void setHeaderType(String headerType) {
		_headerType = headerType;
	}

	public void setIncludedLayouts(String includedLayouts) {
		_includedLayouts = includedLayouts;
	}

	public void setNestedChildren(boolean nestedChildren) {
		_nestedChildren = nestedChildren;
	}

	public void setPreview(boolean preview) {
		_preview = preview;
	}

	public void setRootLayoutLevel(int rootLayoutLevel) {
		_rootLayoutLevel = rootLayoutLevel;
	}

	public void setRootLayoutType(String rootLayoutType) {
		_rootLayoutType = rootLayoutType;
	}

	protected void buildNavigation(
			Layout rootLayout, Layout selLayout, List<Layout> branchLayouts,
			ThemeDisplay themeDisplay, int layoutLevel, String includedLayouts,
			boolean nestedChildren, StringBundler sb)
		throws Exception {

		List<Layout> childLayouts = null;

		if (rootLayout != null) {
			childLayouts = rootLayout.getChildren(
				themeDisplay.getPermissionChecker());
		}
		else {
			childLayouts = LayoutLocalServiceUtil.getLayouts(
				selLayout.getGroupId(), selLayout.isPrivateLayout(),
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);
		}

		if (childLayouts.isEmpty()) {
			return;
		}

		StringBundler tailSB = null;

		if (!nestedChildren) {
			tailSB = new StringBundler();
		}

		sb.append("<ul class=\"layouts level-");
		sb.append(layoutLevel);
		sb.append("\">");

		for (Layout childLayout : childLayouts) {
			if (!childLayout.isHidden() &&
				LayoutPermissionUtil.contains(
					themeDisplay.getPermissionChecker(), childLayout,
					ActionKeys.VIEW)) {

				boolean open = false;

				if (includedLayouts.equals("auto") &&
					branchLayouts.contains(childLayout) &&
					!childLayout.getChildren().isEmpty()) {

					open = true;
				}

				if (includedLayouts.equals("all")) {
					open = true;
				}

				String className = StringPool.BLANK;

				if (open) {
					className += "open ";
				}

				if (selLayout.getLayoutId() == childLayout.getLayoutId()) {
					className += "selected ";
				}

				sb.append("<li ");

				if (Validator.isNotNull(className)) {
					sb.append("class=\"");
					sb.append(className);
					sb.append("\" ");
				}

				sb.append("><a ");

				if (Validator.isNotNull(className)) {
					sb.append("class=\"");
					sb.append(className);
					sb.append("\" ");
				}

				sb.append("href=\"");
				sb.append(
					HtmlUtil.escapeHREF(
						PortalUtil.getLayoutURL(childLayout, themeDisplay)));
				sb.append("\" ");
				sb.append(PortalUtil.getLayoutTarget(childLayout));
				sb.append("> ");
				sb.append(
					HtmlUtil.escape(
						childLayout.getName(themeDisplay.getLocale())));
				sb.append("</a>");

				if (open) {
					StringBundler childLayoutSB = null;

					if (nestedChildren) {
						childLayoutSB = sb;
					}
					else {
						childLayoutSB = tailSB;
					}

					buildNavigation(
						childLayout, selLayout, branchLayouts, themeDisplay,
						layoutLevel + 1, includedLayouts, nestedChildren,
						childLayoutSB);
				}

				sb.append("</li>");
			}
		}

		sb.append("</ul>");

		if (!nestedChildren) {
			sb.append(tailSB);
		}
	}

	@Override
	protected void cleanUp() {
		_bulletStyle = "1";
		_displayStyleDefinition = null;
		_headerType = "none";
		_includedLayouts = "auto";
		_nestedChildren = true;
		_preview = false;
		_rootLayoutLevel = 1;
		_rootLayoutType = "absolute";
	}

	protected List<Layout> getBranchLayouts(Layout layout)
		throws PortalException {

		List<Layout> branchLayouts = new ArrayList<>();

		branchLayouts.add(layout);
		branchLayouts.addAll(layout.getAncestors());

		return branchLayouts;
	}

	protected String getHeaderType() {
		String headerType = _headerType;

		if ((_displayStyleDefinition != null) &&
			(_displayStyleDefinition.length != 0)) {

			headerType = _displayStyleDefinition[0];
		}

		return headerType;
	}

	protected String getIncludedLayouts() {
		String includedLayouts = _includedLayouts;

		if ((_displayStyleDefinition != null) &&
			(_displayStyleDefinition.length != 0)) {

			includedLayouts = _displayStyleDefinition[3];
		}

		return includedLayouts;
	}

	protected String getNavigationString(ThemeDisplay themeDisplay) {
		StringBundler sb = new StringBundler();

		try {
			Layout layout = themeDisplay.getLayout();

			List<Layout> branchLayouts = getBranchLayouts(layout);

			Layout rootLayout = getRootLayout(branchLayouts);

			if ((branchLayouts.size() - getRootLayoutLevel()) > 0) {
				buildNavigation(
					rootLayout, layout, branchLayouts, themeDisplay, 1,
					getIncludedLayouts(), getNestedChildren(), sb);
			}
		}
		catch (Exception e) {
			_log.error(e);
		}

		return sb.toString();
	}

	protected boolean getNestedChildren() {
		boolean nestedChildren = _nestedChildren;

		if ((_displayStyleDefinition != null) &&
			(_displayStyleDefinition.length > 4)) {

			nestedChildren = GetterUtil.getBoolean(_displayStyleDefinition[4]);
		}

		return nestedChildren;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	protected Layout getRootLayout(List<Layout> branchLayouts)
		throws PortalException {

		Layout rootLayout = null;

		String rootLayoutType = getRootLayoutType();
		int rootLayoutLevel = getRootLayoutLevel();

		if (rootLayoutType.equals("relative")) {
			if ((rootLayoutLevel >= 0) &&
				(rootLayoutLevel < branchLayouts.size())) {

				rootLayout = branchLayouts.get(rootLayoutLevel);
			}
			else {
				rootLayout = null;
			}
		}
		else if (rootLayoutType.equals("absolute")) {
			int ancestorIndex = branchLayouts.size() - rootLayoutLevel;

			if ((ancestorIndex >= 0) &&
				(ancestorIndex < branchLayouts.size())) {

				rootLayout = branchLayouts.get(ancestorIndex);
			}
			else if (ancestorIndex == branchLayouts.size()) {
				rootLayout = null;
			}
		}

		return rootLayout;
	}

	protected int getRootLayoutLevel() {
		int rootLayoutLevel = _rootLayoutLevel;

		if ((_displayStyleDefinition != null) &&
			(_displayStyleDefinition.length > 0)) {

			rootLayoutLevel = GetterUtil.getInteger(_displayStyleDefinition[2]);
		}

		return rootLayoutLevel;
	}

	protected String getRootLayoutType() {
		String rootLayoutType = _rootLayoutType;

		if ((_displayStyleDefinition != null) &&
			(_displayStyleDefinition.length > 0)) {

			rootLayoutType = _displayStyleDefinition[1];
		}

		return rootLayoutType;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		request.setAttribute("liferay-ui:navigation:bulletStyle", _bulletStyle);
		request.setAttribute(
			"liferay-ui:navigation:headerType", getHeaderType());
		request.setAttribute(
			"liferay-ui:navigation:preview", String.valueOf(_preview));
		request.setAttribute(
			"liferay-ui:navigation:navigationString",
			getNavigationString(themeDisplay));

		try {
			List<Layout> branchLayouts = getBranchLayouts(
				themeDisplay.getLayout());

			request.setAttribute(
				"liferay-ui:navigation:rootLayout",
				getRootLayout(branchLayouts));
		}
		catch (PortalException e) {
			_log.error(e);
		}
	}

	private static final String _PAGE = "/html/taglib/ui/navigation/page.jsp";

	private static final Log _log = LogFactoryUtil.getLog(NavigationTag.class);

	private String _bulletStyle = "1";
	private String[] _displayStyleDefinition;
	private String _headerType = "none";
	private String _includedLayouts = "auto";
	private boolean _nestedChildren = true;
	private boolean _preview;
	private int _rootLayoutLevel = 1;
	private String _rootLayoutType = "absolute";

}