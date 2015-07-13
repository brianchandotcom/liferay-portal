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
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.DDMTemplate;
import com.liferay.taglib.util.IncludeTag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo Garcia
 */
public class DDMTemplateRendererTag extends IncludeTag {

	@Override
	public int processStartTag() throws Exception {
		if (_displayStyleGroupId == 0) {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			_displayStyleGroupId = themeDisplay.getScopeGroupId();
		}

		_portletDisplayDDMTemplate =
			PortletDisplayTemplateManagerUtil.getDDMTemplate(
				_displayStyleGroupId, PortalUtil.getClassNameId(_className),
				_displayStyle, true);

		if (_portletDisplayDDMTemplate != null) {
			return SKIP_BODY;
		}

		return EVAL_BODY_INCLUDE;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setContextObjects(Map<String, Object> contextObjects) {
		_contextObjects = contextObjects;
	}

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setDisplayStyleGroupId(long displayStyleGroupId) {
		_displayStyleGroupId = displayStyleGroupId;
	}

	public void setEntries(List<?> entries) {
		_entries = entries;
	}

	@Override
	protected void cleanUp() {
		_className = null;
		_contextObjects = new HashMap<>();
		_displayStyle = null;
		_displayStyleGroupId = 0;
		_entries = null;
		_portletDisplayDDMTemplate = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:ddm-template-renderer:contextObjects", _contextObjects);
		request.setAttribute(
			"liferay-ui:ddm-template-renderer:entries", _entries);
		request.setAttribute(
			"liferay-ui:ddm-template-renderer:portletDisplayDDMTemplate",
			_portletDisplayDDMTemplate);
	}

	private static final String _PAGE =
		"/html/taglib/ui/ddm_template_renderer/page.jsp";

	private String _className;
	private Map<String, Object> _contextObjects = new HashMap<>();
	private String _displayStyle;
	private long _displayStyleGroupId;
	private List<?> _entries;
	private DDMTemplate _portletDisplayDDMTemplate;

}