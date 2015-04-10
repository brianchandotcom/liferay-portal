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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.SelectableEntry;
import com.liferay.portal.util.PortalUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
public class EntrySelectorTag extends IncludeTag {

	public void setEntries(List<SelectableEntry> entries) {
		_entries = entries;
	}

	public void setHiddenInput(String hiddenInput) {
		_hiddenInput = hiddenInput;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setSelectedEntries(List<SelectableEntry> selectedEntries) {
		_selectedEntries = selectedEntries;
	}

	public void setTitle(String title) {
		_title = title;
	}

	@Override
	protected void cleanUp() {
		_entries = null;
		_hiddenInput = null;
		_id = null;
		_selectedEntries = null;
		_title = null;
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
				"taglib_ui_entry_selector_page") + StringPool.UNDERLINE;
		}

		request.setAttribute("liferay-ui:entry-selector:entries", _entries);
		request.setAttribute(
			"liferay-ui:entry-selector:hiddenInput", _hiddenInput);
		request.setAttribute("liferay-ui:entry-selector:id", id);
		request.setAttribute(
			"liferay-ui:entry-selector:selectedEntries", _selectedEntries);
		request.setAttribute("liferay-ui:entry-selector:title", _title);
	}

	private static final String _PAGE =
		"/html/taglib/ui/entry_selector/page.jsp";

	private List<SelectableEntry> _entries;
	private String _hiddenInput;
	private String _id;
	private List<SelectableEntry> _selectedEntries;
	private String _title = "select-entries";

}