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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Juan Fernández
 * @author Shuyang Zhou
 */
public class AssetLinksTag extends IncludeTag {

	public long getAssetEntryId() {
		return _assetEntryId;
	}

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setAssetEntryId(long assetEntryId) {
		_assetEntryId = assetEntryId;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	@Override
	protected void cleanUp() {
		_assetEntryId = 0;
		_className = StringPool.BLANK;
		_classPK = 0;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		if ((_assetEntryId <= 0) && (_classPK > 0)) {
			try {
				AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
					_className, _classPK);

				if (assetEntry != null) {
					_assetEntryId = assetEntry.getEntryId();
				}
			}
			catch (SystemException se) {
			}
		}

		request.setAttribute(
			"liferay-ui:asset-links:assetEntryId",
			String.valueOf(_assetEntryId));
	}

	private static final String _PAGE = "/html/taglib/ui/asset_links/page.jsp";

	private long _assetEntryId;
	private String _className = StringPool.BLANK;
	private long _classPK;

}