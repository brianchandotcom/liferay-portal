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

import com.liferay.portlet.portletdisplaytemplate.util.PortletDisplayTemplateUtil;
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
		_portletDisplayDDMTemplateId =
			PortletDisplayTemplateUtil.getPortletDisplayTemplateDDMTemplateId(
				_displayStyleGroupId, _displayStyle);

		if (_portletDisplayDDMTemplateId > 0) {
			return SKIP_BODY;
		}

		return EVAL_BODY_INCLUDE;
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
		_contextObjects = new HashMap<String, Object>();
		_displayStyle = null;
		_displayStyleGroupId = 0;
		_entries = null;
		_portletDisplayDDMTemplateId = 0;
	}

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
			"liferay-ui:ddm-template-renderer:portletDisplayDDMTemplateId",
			String.valueOf(_portletDisplayDDMTemplateId));
	}

	private static final String _PAGE =
		"/html/taglib/ui/ddm_template_renderer/page.jsp";

	private Map<String, Object> _contextObjects = new HashMap<String, Object>();
	private String _displayStyle;
	private long _displayStyleGroupId;
	private List<?> _entries;
	private long _portletDisplayDDMTemplateId;

}