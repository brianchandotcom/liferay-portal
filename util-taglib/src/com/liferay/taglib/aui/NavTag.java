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

package com.liferay.taglib.aui;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.taglib.aui.base.BaseNavTag;

import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Eduardo Lundgren
 * @author Bruno Basto
 * @author Nathan Cavanaugh
 * @author Julio Camarero
 */
public class NavTag extends BaseNavTag implements BodyTag {

	@Override
	public int doStartTag() throws JspException {
		NavBarTag navBarTag = (NavBarTag)findAncestorWithClass(
			this, NavBarTag.class);

		if ((navBarTag != null) &&
			(!_calledCollapsibleSetter || getCollapsible())) {

			setCollapsible(true);

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			StringBundler sb = navBarTag.getResponsiveButtonsSB();

			sb.append("<a class=\"btn navbar-btn navbar-toggle");

			String cssClass = getCssClass();

			if (Validator.isNotNull(cssClass)) {
				String[] cssClassParts = StringUtil.split(
					cssClass, CharPool.SPACE);

				for (int i = 0; i < cssClassParts.length; i++) {
					sb.append(StringPool.SPACE);
					sb.append(cssClassParts[i]);
					sb.append("-btn");
				}
			}

			if (_hasSearchResults()) {
				sb.append(" hide");
			}

			sb.append("\" id=\"");
			sb.append(_getNamespacedId());
			sb.append("NavbarBtn\" ");
			sb.append("data-navId=\"");
			sb.append(_getNamespacedId());
			sb.append("\" tabindex=\"0\">");

			String icon = getIcon();

			if (Validator.isNull(icon)) {
				sb.append("<i class=\"icon-reorder\"></i>");
			}
			else if (icon.equals("user") && themeDisplay.isSignedIn()) {
				try {
					User user = themeDisplay.getUser();

					sb.append("<img alt=\"");
					sb.append(LanguageUtil.get(request, "my-account"));
					sb.append("\" class=\"user-avatar-image\" ");
					sb.append("src=\"");
					sb.append(user.getPortraitURL(themeDisplay));
					sb.append("\">");
				}
				catch (Exception e) {
					throw new JspException(e);
				}
			}
			else {
				sb.append("<i class=\"icon-");
				sb.append(icon);
				sb.append("\"></i>");
			}

			sb.append("</a>");
		}

		return super.doStartTag();
	}

	@Override
	public void setCollapsible(boolean collapsible) {
		super.setCollapsible(collapsible);

		_calledCollapsibleSetter = true;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_calledCollapsibleSetter = false;
		_namespacedId = null;
	}

	@Override
	protected int processStartTag() throws Exception {
		return EVAL_BODY_BUFFERED;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		super.setAttributes(request);

		setNamespacedAttribute(request, "id", _getNamespacedId());
	}

	private String _getNamespacedId() {
		if (Validator.isNotNull(_namespacedId)) {
			return _namespacedId;
		}

		_namespacedId = getId();

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		if (Validator.isNull(_namespacedId)) {
			_namespacedId = PortalUtil.getUniqueElementId(
				request, StringPool.BLANK, AUIUtil.normalizeId("navTag"));
		}

		PortletResponse portletResponse = (PortletResponse)request.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);

		if ((portletResponse != null) && getUseNamespace()) {
			_namespacedId = portletResponse.getNamespace() + _namespacedId;
		}

		return _namespacedId;
	}

	private boolean _hasSearchResults() {
		SearchContainer<?> searchContainer = getSearchContainer();

		if (searchContainer == null) {
			return false;
		}

		DisplayTerms displayTerms = searchContainer.getDisplayTerms();

		String keywords = displayTerms.getKeywords();

		if (displayTerms.isAdvancedSearch() ||
			!keywords.equals(StringPool.BLANK)) {

			return true;
		}

		return false;
	}

	private boolean _calledCollapsibleSetter;
	private String _namespacedId;

}