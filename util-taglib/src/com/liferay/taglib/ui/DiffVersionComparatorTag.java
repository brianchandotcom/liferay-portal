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

import com.liferay.portal.kernel.diff.DiffVersion;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class DiffVersionComparatorTag extends IncludeTag {

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public void setDiffHtmlResults(String diffHtmlResults) {
		_diffHtmlResults = diffHtmlResults;
	}

	public void setIteratorURL(PortletURL iteratorURL) {
		_iteratorURL = iteratorURL;
	}

	public void setNextVersion(double nextVersion) {
		_nextVersion = nextVersion;
	}

	public void setPreviousVersion(double previousVersion) {
		_previousVersion = previousVersion;
	}

	public void setSourceVersion(double sourceVersion) {
		_sourceVersion = sourceVersion;
	}

	public void setTargetVersion(double targetVersion) {
		_targetVersion = targetVersion;
	}

	public void setVersionsInfo(List<DiffVersion> versionsInfo) {
		_versionsInfo = versionsInfo;
	}

	@Override
	protected void cleanUp() {
		_diffHtmlResults = null;
		_iteratorURL = null;
		_nextVersion = 0;
		_previousVersion = 0;
		_sourceVersion = 0;
		_targetVersion = 0;
		_versionsInfo = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:diff-version-comparator:diffHtmlResults",
			_diffHtmlResults);
		request.setAttribute(
			"liferay-ui:diff-version-comparator:iteratorURL", _iteratorURL);
		request.setAttribute(
			"liferay-ui:diff-version-comparator:nextVersion", _nextVersion);
		request.setAttribute(
			"liferay-ui:diff-version-comparator:previousVersion",
			_previousVersion);
		request.setAttribute(
			"liferay-ui:diff-version-comparator:sourceVersion", _sourceVersion);
		request.setAttribute(
			"liferay-ui:diff-version-comparator:targetVersion", _targetVersion);
		request.setAttribute(
			"liferay-ui:diff-version-comparator:versionsInfo", _versionsInfo);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE =
		"/html/taglib/ui/diff_version_comparator/page.jsp";

	private String _diffHtmlResults;
	private PortletURL _iteratorURL;
	private double _nextVersion;
	private double _previousVersion;
	private double _sourceVersion;
	private double _targetVersion;
	private List<DiffVersion> _versionsInfo;

}