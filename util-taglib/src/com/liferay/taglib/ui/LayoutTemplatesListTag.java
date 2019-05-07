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

import com.liferay.portal.kernel.model.LayoutTemplate;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class LayoutTemplatesListTag extends IncludeTag {

	public String getLayoutTemplateId() {
		return _layoutTemplateId;
	}

	public String getLayoutTemplateIdPrefix() {
		return _layoutTemplateIdPrefix;
	}

	public List<LayoutTemplate> getLayoutTemplates() {
		return _layoutTemplates;
	}

	public void setLayoutTemplateId(String layoutTemplateId) {
		_layoutTemplateId = layoutTemplateId;
	}

	public void setLayoutTemplateIdPrefix(String layoutTemplateIdPrefix) {
		_layoutTemplateIdPrefix = layoutTemplateIdPrefix;
	}

	public void setLayoutTemplates(List<LayoutTemplate> layoutTemplates) {
		_layoutTemplates = layoutTemplates;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_layoutTemplateId = null;
		_layoutTemplateIdPrefix = null;
		_layoutTemplates = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-ui:layout-templates-list:layoutTemplateId",
			_layoutTemplateId);
		httpServletRequest.setAttribute(
			"liferay-ui:layout-templates-list:layoutTemplateIdPrefix",
			_layoutTemplateIdPrefix);
		httpServletRequest.setAttribute(
			"liferay-ui:layout-templates-list:layoutTemplates",
			_layoutTemplates);
	}

	private static final String _PAGE =
		"/html/taglib/ui/layout_templates_list/page.jsp";

	private String _layoutTemplateId;
	private String _layoutTemplateIdPrefix;
	private List<LayoutTemplate> _layoutTemplates;

}