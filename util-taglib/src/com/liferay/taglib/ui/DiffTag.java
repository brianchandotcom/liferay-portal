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

import com.liferay.portal.kernel.diff.DiffResult;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Bruno Farache
 */
public class DiffTag extends IncludeTag {

	public void setDiffResults(List<DiffResult>[] diffResults) {
		_diffResults = diffResults;
	}

	public void setSourceName(String sourceName) {
		_sourceName = sourceName;
	}

	public void setTargetName(String targetName) {
		_targetName = targetName;
	}

	@Override
	protected void cleanUp() {
		_diffResults = null;
		_sourceName = null;
		_targetName = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-ui:diff:diffResults", _diffResults);
		request.setAttribute("liferay-ui:diff:sourceName", _sourceName);
		request.setAttribute("liferay-ui:diff:targetName", _targetName);
	}

	private static final String _PAGE = "/html/taglib/ui/diff/page.jsp";

	private List<DiffResult>[] _diffResults;
	private String _sourceName;
	private String _targetName;

}