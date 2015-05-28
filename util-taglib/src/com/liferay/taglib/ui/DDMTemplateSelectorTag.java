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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplateUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Juan Fernández
 */
public class DDMTemplateSelectorTag extends IncludeTag {

	public void setClassName(String className) {
		_className = className;
	}

	public void setDefaultDisplayStyle(String defaultDisplayStyle) {
		_defaultDisplayStyle = defaultDisplayStyle;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setDisplayStyleGroupId(long displayStyleGroupId) {
		_displayStyleGroupId = displayStyleGroupId;
	}

	public void setDisplayStyles(List<String> displayStyles) {
		_displayStyles = displayStyles;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setRefreshURL(String refreshURL) {
		_refreshURL = refreshURL;
	}

	public void setShowEmptyOption(boolean showEmptyOption) {
		_showEmptyOption = showEmptyOption;
	}

	@Override
	protected void cleanUp() {
		_className = null;
		_defaultDisplayStyle = StringPool.BLANK;
		_displayStyle = null;
		_displayStyleGroupId = 0;
		_displayStyles = null;
		_icon = null;
		_label = "display-template";
		_refreshURL = null;
		_showEmptyOption = false;
	}

	protected String getDisplayStyle() {
		if (Validator.isNull(_displayStyle)) {
			return _defaultDisplayStyle;
		}

		return _displayStyle;
	}

	protected long getDisplayStyleGroupId() {
		if (_displayStyleGroupId > 0) {
			return _displayStyleGroupId;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.getScopeGroupId();
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		String displayStyle = getDisplayStyle();

		long displayStyleGroupId = getDisplayStyleGroupId();

		DDMTemplate portletDisplayDDMTemplate =
			PortletDisplayTemplateUtil.getPortletDisplayTemplateDDMTemplate(
				displayStyleGroupId, PortalUtil.getClassNameId(_className),
				displayStyle, true);

		if (Validator.isNull(displayStyle) &&
			(portletDisplayDDMTemplate != null)) {

			displayStyle = PortletDisplayTemplateUtil.getDisplayStyle(
				portletDisplayDDMTemplate.getTemplateKey());
		}

		request.setAttribute(
			"liferay-ui:ddm-template-select:classNameId",
			String.valueOf(PortalUtil.getClassNameId(_className)));
		request.setAttribute(
			"liferay-ui:ddm-template-select:displayStyle", displayStyle);
		request.setAttribute(
			"liferay-ui:ddm-template-select:displayStyleGroupId",
			String.valueOf(displayStyleGroupId));
		request.setAttribute(
			"liferay-ui:ddm-template-select:displayStyles", _displayStyles);
		request.setAttribute("liferay-ui:ddm-template-select:icon", _icon);
		request.setAttribute("liferay-ui:ddm-template-select:label", _label);
		request.setAttribute(
			"liferay-ui:ddm-template-select:portletDisplayDDMTemplate",
			portletDisplayDDMTemplate);
		request.setAttribute(
			"liferay-ui:ddm-template-select:refreshURL", _refreshURL);
		request.setAttribute(
			"liferay-ui:ddm-template-select:showEmptyOption",
			String.valueOf(_showEmptyOption));
	}

	private static final String _PAGE =
		"/html/taglib/ui/ddm_template_selector/page.jsp";

	private String _className;
	private String _defaultDisplayStyle = StringPool.BLANK;
	private String _displayStyle;
	private long _displayStyleGroupId;
	private List<String> _displayStyles;
	private String _icon;
	private String _label = "display-template";
	private String _refreshURL;
	private boolean _showEmptyOption;

}