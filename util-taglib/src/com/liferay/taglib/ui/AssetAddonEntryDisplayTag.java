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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.AssetAddonEntry;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.servlet.JspWriterHttpServletResponse;
import com.liferay.taglib.util.IncludeTag;

import java.io.IOException;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

/**
 * @author Julio Camarero
 */
public class AssetAddonEntryDisplayTag extends IncludeTag {

	@Override
	public int doEndTag() throws JspException {
		for (AssetAddonEntry assetAddonEntry : _assetAddonEntries) {
			try {
				assetAddonEntry.include(request, getResponse());
			}
			catch (IOException ioe) {
				_log.error(ioe, ioe);
			}
		}

		return super.doEndTag();
	}

	public void setAssetAddonEntries(
		List<? extends AssetAddonEntry> assetAddonEntries) {

		_assetAddonEntries = assetAddonEntries;
	}

	@Override
	protected void cleanUp() {
		_assetAddonEntries = null;
	}

	protected HttpServletResponse getResponse() {
		return new JspWriterHttpServletResponse(pageContext);
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(WebKeys.ASSET_ADDON_ENTRIES, _assetAddonEntries);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetAddonEntryDisplayTag.class);

	private List<? extends AssetAddonEntry> _assetAddonEntries;

}