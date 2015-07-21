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

import com.liferay.portal.kernel.portletdisplaytemplate.PortletDisplayTemplateManagerUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.DDMTemplate;
import com.liferay.taglib.ddm.base.BaseTemplateSelectorTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Juan Fernández
 */
public class TemplateSelectorTag extends BaseTemplateSelectorTag {

	public TemplateSelectorTag() {
		setDefaultDisplayStyle(StringPool.BLANK);
	}

	@Override
	public String getDisplayStyle() {
		String displayStyle = super.getDisplayStyle();

		if (Validator.isNull(displayStyle)) {
			displayStyle = getDefaultDisplayStyle();
		}

		DDMTemplate portletDisplayDDMTemplate = getPortletDisplayDDMTemplate();

		if (Validator.isNull(displayStyle) &&
			(portletDisplayDDMTemplate != null)) {

			displayStyle = PortletDisplayTemplateManagerUtil.getDisplayStyle(
				portletDisplayDDMTemplate.getTemplateKey());
		}

		return displayStyle;
	}

	@Override
	public long getDisplayStyleGroupId() {
		long displayStyleGroupId = super.getDisplayStyleGroupId();

		if (displayStyleGroupId > 0) {
			return displayStyleGroupId;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay.getScopeGroupId();
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		setDefaultDisplayStyle(StringPool.BLANK);
	}

	protected DDMTemplate getPortletDisplayDDMTemplate() {
		String displayStyle = super.getDisplayStyle();

		if (Validator.isNull(displayStyle)) {
			displayStyle = getDefaultDisplayStyle();
		}

		DDMTemplate portletDisplayDDMTemplate =
			PortletDisplayTemplateManagerUtil.getDDMTemplate(
				getDisplayStyleGroupId(),
				PortalUtil.getClassNameId(getClassName()), displayStyle, true);

		return portletDisplayDDMTemplate;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		super.setAttributes(request);

		setNamespacedAttribute(request, "classNameId",
			String.valueOf(PortalUtil.getClassNameId(getClassName())));
		setNamespacedAttribute(request, "portletDisplayDDMTemplate",
			getPortletDisplayDDMTemplate());
	}

}