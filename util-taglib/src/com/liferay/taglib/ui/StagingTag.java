/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class StagingTag extends IncludeTag {

	public void setExtended(boolean extended) {
		_extended = extended;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setLayoutSetBranchId(long layoutSetBranchId) {
		_layoutSetBranchId = layoutSetBranchId;
	}

	public void setPrivateLayout(boolean privateLayout) {
		_privateLayout = privateLayout;
	}

	public void setSelPlid(long selPlid) {
		_selPlid = selPlid;
	}

	public void setShowManageBranches(boolean showManageBranches) {
		_showManageBranches = showManageBranches;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:staging:extended", String.valueOf(_extended));
		request.setAttribute(
			"liferay-ui:staging:groupId", String.valueOf(_groupId));
		request.setAttribute(
			"liferay-ui:staging:layoutSetBranchId",
			String.valueOf(_layoutSetBranchId));
		request.setAttribute(
			"liferay-ui:staging:privateLayout", String.valueOf(_privateLayout));
		request.setAttribute(
			"liferay-ui:staging:selPlid", String.valueOf(_selPlid));
		request.setAttribute(
			"liferay-ui:staging:showManageBranches",
			String.valueOf(_showManageBranches));
	}

	private static final String _PAGE = "/html/taglib/ui/staging/page.jsp";

	private boolean _extended = true;
	private long _groupId;
	private long _layoutSetBranchId;
	private boolean _privateLayout;
	private long _selPlid;
	private boolean _showManageBranches;

}