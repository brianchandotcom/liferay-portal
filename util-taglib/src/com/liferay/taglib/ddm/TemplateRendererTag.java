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

package com.liferay.taglib.ddm;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import com.liferay.portal.kernel.portletdisplaytemplate.PortletDisplayTemplateManagerUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.dynamicdatamapping.DDMTemplate;

import com.liferay.taglib.FileAvailabilityUtil;
import com.liferay.taglib.ddm.base.BaseTemplateRendererTag;

/**
 * @author Eduardo Garcia
 */
public class TemplateRendererTag extends BaseTemplateRendererTag {

	public TemplateRendererTag() {
		setContextObjects(new HashMap<String, Object>());
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			String page = getStartPage();

			setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

			callSetAttributes();

			if (themeResourceExists(page)) {
				doIncludeTheme(page);

				return EVAL_BODY_INCLUDE;
			}

			if (!FileAvailabilityUtil.isAvailable(servletContext, page)) {
				logUnavailablePage(page);
			}

			doInclude(page, true);

			if (_portletDisplayDDMTemplate != null) {
				return SKIP_BODY;
			}

			return EVAL_BODY_INCLUDE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		setContextObjects(new HashMap<String, Object>());
		_portletDisplayDDMTemplate = null;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		super.setAttributes(request);

		setPortletDisplayDDMTemplate();

		setNamespacedAttribute(
			request, "portletDisplayDDMTemplate", _portletDisplayDDMTemplate);
	}

	protected void setPortletDisplayDDMTemplate() {
		if (getDisplayStyleGroupId() == 0) {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			setDisplayStyleGroupId(themeDisplay.getScopeGroupId());
		}

		_portletDisplayDDMTemplate =
			PortletDisplayTemplateManagerUtil.getDDMTemplate(
				getDisplayStyleGroupId(),
				PortalUtil.getClassNameId(getClassName()), getDisplayStyle(),
				true);
	}

	private DDMTemplate _portletDisplayDDMTemplate;

}