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

import com.liferay.portal.kernel.servlet.taglib.ui.AssetAddonEntry;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 */
public class AssetAddonEntrySelectorTag extends IncludeTag {

	public void setAssetAddonEntries(List<AssetAddonEntry> assetAddonEntries) {
		_assetAddonEntries = assetAddonEntries;
	}

	public void setHiddenInput(String hiddenInput) {
		_hiddenInput = hiddenInput;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setSelectedAssetAddonEntries(
		List<AssetAddonEntry> selectedAssetAddonEntries) {

		_selectedAssetAddonEntries = selectedAssetAddonEntries;
	}

	public void setTitle(String title) {
		_title = title;
	}

	@Override
	protected void cleanUp() {
		_assetAddonEntries = null;
		_hiddenInput = null;
		_id = null;
		_selectedAssetAddonEntries = null;
		_title = "select-entries";
	}

	protected String getId() {
		if (Validator.isNotNull(_id)) {
			return _id;
		}

		String id = PortalUtil.generateRandomKey(
			request, "taglib_ui_asset_addon_entry_selector_page");

		return id + StringPool.UNDERLINE;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(WebKeys.ASSET_ADDON_ENTRIES, _assetAddonEntries);
		request.setAttribute(
			"liferay-ui:asset-addon-entry-selector:hiddenInput", _hiddenInput);
		request.setAttribute(
			"liferay-ui:asset-addon-entry-selector:id", getId());
		request.setAttribute(
			"liferay-ui:asset-addon-entry-selector:selectedAssetAddonEntries",
			_selectedAssetAddonEntries);
		request.setAttribute(
			"liferay-ui:asset-addon-entry-selector:title", _title);
	}

	private static final String _PAGE =
		"/html/taglib/ui/asset_addon_entry_selector/page.jsp";

	private List<AssetAddonEntry> _assetAddonEntries;
	private String _hiddenInput;
	private String _id;
	private List<AssetAddonEntry> _selectedAssetAddonEntries;
	private String _title = "select-entries";

}