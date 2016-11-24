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

import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author     Brian Wing Shun Chan
 * @author     Jorge Ferrer
 * @deprecated As of 7.0.0
 */
@Deprecated
public class AssetCategoriesSelectorTag extends IncludeTag {

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public void setClassTypePK(long classTypePK) {
		_classTypePK = classTypePK;
	}

	public void setCurCategoryIds(String curCategoryIds) {
		_curCategoryIds = curCategoryIds;
	}

	public void setGroupIds(long[] groupIds) {
		_groupIds = groupIds;
	}

	public void setHiddenInput(String hiddenInput) {
		_hiddenInput = hiddenInput;
	}

	public void setIgnoreRequestValue(boolean ignoreRequestValue) {
		_ignoreRequestValue = ignoreRequestValue;
	}

	public void setShowRequiredLabel(boolean showRequiredLabel) {
		_showRequiredLabel = showRequiredLabel;
	}

	@Override
	protected void cleanUp() {
		_className = null;
		_classPK = 0;
		_classTypePK = AssetCategoryConstants.ALL_CLASS_TYPE_PK;
		_curCategoryIds = null;
		_groupIds = null;
		_hiddenInput = "assetCategoryIds";
		_ignoreRequestValue = false;
		_showRequiredLabel = true;
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
			"liferay-ui:asset-categories-selector:classTypePK",
			String.valueOf(_classTypePK));
		request.setAttribute(
			"liferay-ui:asset-categories-selector:curCategoryIds",
			_curCategoryIds);
		request.setAttribute(
			"liferay-ui:asset-categories-selector:groupIds", _groupIds);
		request.setAttribute(
			"liferay-ui:asset-categories-selector:hiddenInput", _hiddenInput);
		request.setAttribute(
			"liferay-ui:asset-categories-selector:ignoreRequestValue",
			_ignoreRequestValue);
		request.setAttribute(
			"liferay-ui:asset-categories-selector:showRequiredLabel",
			String.valueOf(_showRequiredLabel));
	}

	private static final String _PAGE =
		"/html/taglib/ui/asset_categories_selector/page.jsp";

	private String _className;
	private long _classPK;
	private long _classTypePK = AssetCategoryConstants.ALL_CLASS_TYPE_PK;
	private String _curCategoryIds;
	private long[] _groupIds;
	private String _hiddenInput = "assetCategoryIds";
	private boolean _ignoreRequestValue;
	private boolean _showRequiredLabel = true;

}