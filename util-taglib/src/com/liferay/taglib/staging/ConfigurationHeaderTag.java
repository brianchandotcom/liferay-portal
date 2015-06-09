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

package com.liferay.taglib.staging;

import com.liferay.portlet.exportimport.model.ExportImportConfiguration;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Levente Hudák
 */
public class ConfigurationHeaderTag extends IncludeTag {

	public void setExportImportConfiguration(
		ExportImportConfiguration exportImportConfiguration) {

		_exportImportConfiguration = exportImportConfiguration;
	}

	public void setLabel(String label) {
		_label = label;
	}

	@Override
	protected void cleanUp() {
		_exportImportConfiguration = null;
		_label = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-staging:configuration-header:exportImportConfiguration",
			_exportImportConfiguration);
		request.setAttribute(
			"liferay-staging:configuration-header:label", _label);
		request.setAttribute(
			"liferay-staging:configuration-header:liferayPortletRequest",
			pageContext.getAttribute("liferayPortletRequest"));
	}

	private static final String _PAGE =
		"/html/taglib/staging/configuration_header/page.jsp";

	private ExportImportConfiguration _exportImportConfiguration;
	private String _label;

}