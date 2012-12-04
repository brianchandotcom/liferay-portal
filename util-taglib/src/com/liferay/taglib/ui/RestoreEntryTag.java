/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
 * @author Roberto Diaz
 */
public class RestoreEntryTag extends IncludeTag {

	public void setDuplicateEntryAction(String duplicateEntryAction) {
		_duplicateEntryAction = duplicateEntryAction;
	}

	public void setOverrideMessage(String overrideMessage) {
		_overrideMessage = overrideMessage;
	}

	public void setRenameMessage(String renameMessage) {
		_renameMessage = renameMessage;
	}

	public void setRestoreEntryAction(String restoreEntryAction) {
		_restoreEntryAction = restoreEntryAction;
	}

	@Override
	protected void cleanUp() {
		_duplicateEntryAction = _DUPLICATE_ENTRY_ACTION;
		_overrideMessage = _OVERRIDE_MESSAGE;
		_renameMessage = _RENAME_MESSAGE;
		_restoreEntryAction = _RESTORE_ENTRY_ACTION;
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
			"liferay-ui:restore-entry:duplicateEntryAction",
			_duplicateEntryAction);
		request.setAttribute(
			"liferay-ui:restore-entry:overrideMessage", _overrideMessage);
		request.setAttribute(
			"liferay-ui:restore-entry:renameMessage", _renameMessage);
		request.setAttribute(
			"liferay-ui:restore-entry:restoreEntryAction", _restoreEntryAction);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _DUPLICATE_ENTRY_ACTION =
		"/trash/restore_entry";

	private static final String _OVERRIDE_MESSAGE =
		"overwrite-the-existing-entry-with-the-one-from-the-recycle-bin";

	private static final String _PAGE =
		"/html/taglib/ui/restore_entry/page.jsp";

	private static final String _RENAME_MESSAGE =
		"keep-both-entries-and-rename-the-entry-from-the-recycle-bin-as";

	private static final String _RESTORE_ENTRY_ACTION = "/trash/edit_entry";

	private String _duplicateEntryAction = _DUPLICATE_ENTRY_ACTION;
	private String _overrideMessage = _OVERRIDE_MESSAGE;
	private String _renameMessage = _RENAME_MESSAGE;
	private String _restoreEntryAction = _RESTORE_ENTRY_ACTION;

}