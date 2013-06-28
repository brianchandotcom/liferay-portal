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

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class AssetTagsSelectorTag extends IncludeTag {

	public void setAutoFocus(boolean autoFocus) {
		_autoFocus = autoFocus;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setContentCallback(String contentCallback) {
		_contentCallback = contentCallback;
	}

	public void setCurTags(String curTags) {
		_curTags = curTags;
	}

	public void setGroupIds(long[] groupIds) {
		_groupIds = groupIds;
	}

	public void setHiddenInput(String hiddenInput) {
		_hiddenInput = hiddenInput;
	}

	public void setId(String id) {
		_id = id;
	}

	@Override
	protected void cleanUp() {
		_autoFocus = false;
		_className = null;
		_classPK = 0;
		_contentCallback = null;
		_curTags = null;
		_groupIds = null;
		_hiddenInput = "assetTagNames";
		_id = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		String id = _id;

		if (Validator.isNull(id)) {
			id = PortalUtil.generateRandomKey(
				request,
				"taglib_ui_asset_tags_selector_page") + StringPool.UNDERLINE;
		}

		request.setAttribute(
			"liferay-ui:asset-tags-selector:autoFocus", String.valueOf(_autoFocus));
		request.setAttribute(
			"liferay-ui:asset-tags-selector:className", _className);
		request.setAttribute(
			"liferay-ui:asset-tags-selector:classPK", String.valueOf(_classPK));
		request.setAttribute(
			"liferay-ui:asset-tags-selector:contentCallback",
			String.valueOf(_contentCallback));
		request.setAttribute(
			"liferay-ui:asset-tags-selector:curTags", _curTags);

		if (_groupIds == null) {
			ThemeDisplay themeDisplay = (ThemeDisplay)pageContext.getAttribute(
				"themeDisplay");

			long[] groupIds = null;

			Group group = themeDisplay.getScopeGroup();

			if (group.isLayout()) {
				groupIds = new long[] {group.getParentGroupId()};
			}
			else {
				groupIds = new long[] {group.getGroupId()};
			}

			if (group.getParentGroupId() != themeDisplay.getCompanyGroupId()) {
				groupIds = ArrayUtil.append(
					groupIds, themeDisplay.getCompanyGroupId());
			}

			_groupIds = groupIds;
		}

		request.setAttribute(
			"liferay-ui:asset-tags-selector:groupIds", _groupIds);

		request.setAttribute(
			"liferay-ui:asset-tags-selector:hiddenInput", _hiddenInput);
		request.setAttribute("liferay-ui:asset-tags-selector:id", id);
	}

	private static final String _PAGE =
		"/html/taglib/ui/asset_tags_selector/page.jsp";

	private boolean _autoFocus;
	private String _className;
	private long _classPK;
	private String _contentCallback;
	private String _curTags;
	private long[] _groupIds;
	private String _hiddenInput = "assetTagNames";
	private String _id;

}