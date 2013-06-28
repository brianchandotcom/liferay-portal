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

import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class AssetCategoriesSelectorTag extends IncludeTag {

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setContentCallback(String contentCallback) {
		_contentCallback = contentCallback;
	}

	public void setCurCategoryIds(String curCategoryIds) {
		_curCategoryIds= curCategoryIds;
	}

	public void setHiddenInput(String hiddenInput) {
		_hiddenInput = hiddenInput;
	}

	@Override
	protected void cleanUp() {
		_className = null;
		_classPK = 0;
		_contentCallback = null;
		_curCategoryIds = null;
		_hiddenInput = "assetCategoryIds";
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:asset-categories-selector:className", _className);
		request.setAttribute(
			"liferay-ui:asset-categories-selector:classPK",
			String.valueOf(_classPK));
		request.setAttribute(
			"liferay-ui:asset-categories-selector:contentCallback",
			String.valueOf(_contentCallback));
		request.setAttribute(
			"liferay-ui:asset-categories-selector:curCategoryIds",
			_curCategoryIds);
		request.setAttribute(
			"liferay-ui:asset-categories-selector:hiddenInput", _hiddenInput);
	}

	private static final String _PAGE =
		"/html/taglib/ui/asset_categories_selector/page.jsp";

	private String _className;
	private long _classPK;
	private String _contentCallback;
	private String _curCategoryIds;
	private String _hiddenInput = "assetCategoryIds";

}