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

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roberto Díaz
 */
public class ItemSelectorBrowserTag extends IncludeTag {

	public void setDisplayStyle(String displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setIdPrefix(String idPrefix) {
		_idPrefix = idPrefix;
	}

	public void setItemSearchContainer(SearchContainer itemSearchContainer) {
		_itemSearchContainer = itemSearchContainer;
	}

	public void setTabName(String tabName) {
		_tabName = tabName;
	}

	public void setUploadMessage(String uploadMessage) {
		_uploadMessage = uploadMessage;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_displayStyle = "icon";
		_idPrefix = null;
		_itemSearchContainer = null;
		_tabName = null;
		_uploadMessage = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:item-selector-browser:displayStyle", _displayStyle);
		request.setAttribute(
			"liferay-ui:item-selector-browser:idPrefix", _idPrefix);
		request.setAttribute(
			"liferay-ui:item-selector-browser:itemSearchContainer",
			_itemSearchContainer);
		request.setAttribute(
			"liferay-ui:item-selector-browser:tabName", _tabName);

		if (Validator.isNull(_uploadMessage)) {
			_uploadMessage = LanguageUtil.get(
				request,
				"upload-a-document-by-dropping-it-right-here-or-by-pressing-" +
					"plus-icon");
		}

		request.setAttribute(
			"liferay-ui:item-selector-browser:uploadMessage", _uploadMessage);
	}

	private static final String _PAGE = "/html/taglib/ui/item_browser/page.jsp";

	private String _displayStyle;
	private String _idPrefix;
	private SearchContainer _itemSearchContainer;
	private String _tabName;
	private String _uploadMessage;

}